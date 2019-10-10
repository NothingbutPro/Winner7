package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.ics.com.winner7.Utils.Connectivity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class RegistrationActivity extends AppCompatActivity {
    EditText etName, etEmail, etMob, etPass, etReff;
    String EtName, EtEmail, EtMob = "", EtPass, EtReff;
    Button btn_Signup;
    String strType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMob = (EditText) findViewById(R.id.etMob);
        etPass = (EditText) findViewById(R.id.etPass);
        etReff = (EditText) findViewById(R.id.etReff);
        btn_Signup = (Button) findViewById(R.id.btn_Signup);

        etReff.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(8)});

        strType = getIntent().getExtras().getString("Type");

        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtName = etName.getText().toString();
                EtEmail = etEmail.getText().toString();
                EtMob = etMob.getText().toString();
                EtReff = etReff.getText().toString();
                EtPass = etPass.getText().toString();

                if (!EtName.equals("")) {
                    if (!EtEmail.equals("")) {
                        if (!EtMob.equals("")) {
                          /*  if (!EtReff.equals("")) {*/
                                if (!EtPass.equals("")) {
                                    if (Connectivity.isNetworkAvailable(RegistrationActivity.this)) {
                                        new SendJsonData().execute();
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    etPass.setError("Please Enter Password");
                                    etPass.requestFocus();
                                }
                           /* } else {
                                etReff.setError("Please Enter Password");
                                etReff.requestFocus();
                            }*/
                        } else {
                            etMob.setError("Please Enter Mobile No.");
                            etMob.requestFocus();
                        }
                    } else {
                        etEmail.setError("Please Enter Email");
                        etEmail.requestFocus();
                    }
                } else {
                    etName.setError("Please Enter Name");
                    etName.requestFocus();
                }
            }
        });
    }

    //------------------------------------------------

    class SendJsonData extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(RegistrationActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://winner7quiz.com/api/ragistration");


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", EtName);
                postDataParams.put("mobile", EtMob);
                postDataParams.put("email", EtEmail);
                postDataParams.put("password", EtPass);
                postDataParams.put("type", strType);
                postDataParams.put("refferby", EtReff);


                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    /*BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString(); */

                    BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        result.append(line);
                    }
                    r.close();
                    return result.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                // JSONObject jsonObject = null;
                Log.e("SendJsonDataToServer>>>", result.toString());
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String responce = jsonObject.getString("responce");
                    String massage = jsonObject.getString("massage");

                    // Log.e(">>>>", jsonObject.toString() );

                    if (responce.equals("true")) {
                        Intent intent = new Intent(RegistrationActivity.this, OtpActivity.class);
                        intent.putExtra("MOBILE", EtMob);
                        intent.putExtra("OTP", massage);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Some Problem!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

    }
}
