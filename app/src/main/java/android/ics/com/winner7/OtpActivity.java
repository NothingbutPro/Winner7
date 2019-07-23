package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.ics.com.winner7.Utils.AppPreference;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etOne;
    EditText etTwo;
    EditText etThree;
    EditText etFour;
    EditText etFive;
    Button btnVerify;
    TextView tvMobile;
    private LinearLayout back;
    private String otp1, mobile1;
    TextView tvResend;
    public static final String OTP_REGEX = "[0-9]{1,6}";
    String user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp1 = getIntent().getExtras().getString("OTP");
        mobile1 = getIntent().getExtras().getString("MOBILE");
        // textview.setText(data);

        btnVerify = (Button) findViewById(R.id.btnVerify);


        setUiAction();

    }

    private void setUiAction() {
        // tvResend = findViewById(R.id.tvResend);
        tvMobile = findViewById(R.id.tvMobile);
        tvMobile.setText(mobile1);
        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(OtpActivity.this);
        etOne = findViewById(R.id.etOne);
        etTwo = findViewById(R.id.etTwo);
        etThree = findViewById(R.id.etThree);
        etFour = findViewById(R.id.etFour);
        etFive = findViewById(R.id.etFive);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        //  tvResend.setOnClickListener(this);


        etOne.addTextChangedListener(new GenericTextWatcher(etOne));
        etTwo.addTextChangedListener(new GenericTextWatcher(etTwo));
        etThree.addTextChangedListener(new GenericTextWatcher(etThree));
        etFour.addTextChangedListener(new GenericTextWatcher(etFour));
        etFive.addTextChangedListener(new GenericTextWatcher(etFive));

        showOTP(otp1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvResend:
               /* if (NetworkManager.isConnectToInternet(OtpActivity.this)) {
                    resendotp = getOtp();
                    otp = resendotp;
                 //   Log.e("OTP", otp + "  " + resendotp);
                    //  resendOTP();
                } else {
                    ProjectUtils.showToast(OtpActivity.this, getString(R.string.internet_concation));
                }*/
                break;
            case R.id.btnVerify:
                new SendJsonOtpData().execute();
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
        }
    }

    public void showOTP(String otp) {
        try {


            Log.d("Text", otp);
            //ProjectUtils.showToast(mContext,messageText);
            Pattern pattern = Pattern.compile(OTP_REGEX);
            Matcher matcher = pattern.matcher(otp);
            String otp_one = "";
            while (matcher.find()) {
                otp_one = matcher.group();
                Log.e("While", otp_one);
            }
            Log.e("ONE", otp_one.charAt(0) + "");
            etOne.setText("" + otp_one.charAt(0));
            etTwo.setText("" + otp_one.charAt(1));
            etThree.setText("" + otp_one.charAt(2));
            etFour.setText("" + otp_one.charAt(3));
            etFive.setText("" + otp_one.charAt(4));
            etFive.setSelection(etFive.getText().length());

            // btnSubmit.performClick();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.etOne:
                    if (text.length() == 1)
                        etTwo.requestFocus();
                    break;
                case R.id.etTwo:
                    if (text.length() == 1)
                        etThree.requestFocus();
                    else
                        etOne.requestFocus();
                    break;
                case R.id.etThree:
                    if (text.length() == 1)
                        etFour.requestFocus();
                    else
                        etTwo.requestFocus();
                    break;
                case R.id.etFour:
                    if (text.length() == 1)
                        etThree.requestFocus();
                    break;
                case R.id.etFive:
                    if (text.length() == 0)
                        etThree.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    //-------------------------------------------------

    class SendJsonOtpData extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(OtpActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/winnerseven/api/otpmatch");


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("otp", otp1);
                postDataParams.put("mobile", mobile1);


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
                    JSONObject object = jsonObject.getJSONObject("massage");
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

                    // Log.e(">>>>", jsonObject.toString() );

                    if (responce.equals("true")) {
                        AppPreference.setId(OtpActivity.this, user_id);
                        Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                        AppPreference.setType(OtpActivity.this, "2");
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OtpActivity.this, "Some Problem!!", Toast.LENGTH_SHORT).show();
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
