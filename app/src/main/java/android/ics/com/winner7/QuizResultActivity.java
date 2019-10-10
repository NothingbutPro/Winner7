package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.ics.com.winner7.Utils.AppPreference;
import android.ics.com.winner7.Utils.SessionManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class QuizResultActivity extends AppCompatActivity {
    TextView attemp,unattepmt,totmark,timewa,wrontxt,gainmark,rigth;
    SessionManager sessionManager;
    Handler handler;
    String endtime;
    private String currentTime;
     Runnable r;
    int maxmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        attemp = findViewById(R.id.attemp);
        gainmark = findViewById(R.id.gainmark);
        rigth = findViewById(R.id.rigth);
        unattepmt = findViewById(R.id.unattepmt);
        totmark = findViewById(R.id.totmark);
        timewa = findViewById(R.id.timewa);
        wrontxt = findViewById(R.id.wrontxt);
        sessionManager = new SessionManager(this);
        endtime = getIntent().getStringExtra("endtime");
        Log.e("add pre" , ""+AppPreference.getId(this));
        handler = new Handler();
         maxmin=0;
        try {
            String endtime = getIntent().getStringExtra("endtime");
              r = new Runnable() {
                public void run() {
                    currentTime = new SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(new Date());

                    int currentval = Integer.valueOf(currentTime.substring(3, 5));
                    int endtimeval = Integer.valueOf(endtime.substring(3, 5));
                    if (maxmin > 3000) {
                        Log.e("They", "are  equal"+currentval+" "+endtimeval);
                        handler.removeCallbacks(r);
                       new Submitfinaltext().execute();
                    } else {
                        Log.e("They", "are not equal"+maxmin);
                        maxmin = maxmin +1000;
                    }
                    //  tv.append("Hello World");
//                if(currentTime.equals())
                    handler.postDelayed(this, 1000);
                }
            };

            handler.postDelayed(r, 1000);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        new GETALlRESULTS(AppPreference.getId(this)).execute();
    }

    private class GETALlRESULTS  extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String u_id;

        public GETALlRESULTS(String id) {
            this.u_id = id;
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(QuizResultActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://winner7quiz.com/api/getresultbyuser");


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", u_id);


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

                    if(responce.equals("true"))
                    {
                        JSONObject object = jsonObject.getJSONObject("score");
                        totmark.setText(object.get("totalmark").toString());
                        attemp.setText(object.get("attempted").toString());
                        unattepmt.setText(object.get("unattempted").toString());
                        rigth.setText(object.get("rigth").toString());
                        gainmark.setText(object.get("gainmark").toString());
                        wrontxt.setText(object.get("wrong").toString());
                        timewa.setText(object.get("datetime").toString());
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

    private class Submitfinaltext extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String id ,ques,date,datetime;
        int totalmarks,attempted,rightcount,wrongcount,unattempted ;



        protected void onPreExecute() {
            dialog = new ProgressDialog(QuizResultActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://winner7quiz.com/api/generateresult");


                JSONObject postDataParams = new JSONObject();

          //      postDataParams.put("user_id", AppPreference.getId(QuizResultActivity.this));


                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("GET");
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
                    if(responce.equals("true"))
                    {
                        handler.removeCallbacks(r);
                        Intent intent = new Intent(QuizResultActivity.this , MainActivity.class);
//                        intent.putExtra("endtime" , quizLinkedList.get(0).getEndtime());
                        startActivity(intent);
                        finish();
                    }
//                    JSONObject object = jsonObject.getJSONObject("massage");
//                    if(object.getString("id").length() !=0)
//                    {
//                        Toast.makeText(QuizActivity.this, "Test Success", Toast.LENGTH_SHORT).show();
//                    }


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
