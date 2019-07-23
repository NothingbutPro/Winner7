package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.ics.com.winner7.Utils.AppPreference;
import android.ics.com.winner7.Utils.Connectivity;
import android.ics.com.winner7.Utils.SessionManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    EditText etMob, etPass;
    String EtMob, EtPass;
    Button btn_next;
    TextView regid, bt_forget;
    String user_id = "";
    SessionManager manager;
    String strType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etMob = (EditText) findViewById(R.id.etMob);
        etPass = (EditText) findViewById(R.id.etPass);
        btn_next = (Button) findViewById(R.id.btn_next);
        regid = (TextView) findViewById(R.id.regid);
        bt_forget = (TextView) findViewById(R.id.bt_forget);

        strType = getIntent().getExtras().getString("Type");

        manager = new SessionManager(this);

        manager = new SessionManager(this);
        if (manager.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            strType = getIntent().getExtras().getString("Type");
            startActivity(intent);
            finish();
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtMob = etMob.getText().toString();
                EtPass = etPass.getText().toString();
                if (!EtMob.equals("")) {
                    if (!EtPass.equals("")) {
                        if (Connectivity.isNetworkAvailable(LoginActivity.this)) {
                            new SendJsonDataToServer().execute();
                        } else {
                            Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        etPass.setError("Please Enter Password");
                        etPass.requestFocus();
                    }
                } else {
                    etMob.setError("Please Enter Mobile No");
                    etMob.requestFocus();
                }
            }
        });

        regid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                intent.putExtra("Type", strType);
                startActivity(intent);
                finish();
            }
        });

        bt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //---------------------------------------------------------

    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/winnerseven/api/login");


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mobile", EtMob);
                postDataParams.put("password", EtPass);


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
                    JSONObject object = jsonObject.getJSONObject("data");
                    user_id = object.getString("user_id");
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String password = object.getString("password");
                    String mobile = object.getString("mobile");
                    String address = object.getString("address");
                    String type = object.getString("type");
                    String reffercode = object.getString("reffercode");
                    String bankstatus = object.getString("bankstatus");
                    String walletbal = object.getString("walletbal");
                    String image = object.getString("image");
                    String status = object.getString("status");


                    if (responce.equalsIgnoreCase("true")) {
                        manager.malegaonLogin(user_id);
                        AppPreference.setId(LoginActivity.this, user_id);
                       AppPreference.setType(LoginActivity.this, strType);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("Type", strType);
                        startActivity(intent);
                        finish();
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
