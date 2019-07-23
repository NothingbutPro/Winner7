package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.ics.com.winner7.Adapter.RefferAdapter;
import android.ics.com.winner7.Model.RefferModel;
import android.ics.com.winner7.Utils.AppPreference;
import android.ics.com.winner7.Utils.Connectivity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class RefferUserActivity extends AppCompatActivity {
    RecyclerView refferList;
    ArrayList<RefferModel> reff_list;
    private RefferAdapter refferAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reffer_user);

        refferList = (RecyclerView)findViewById(R.id.refferList);

        reff_list = new ArrayList<>();

        if (Connectivity.isNetworkAvailable(RefferUserActivity.this)){
            new PostRefferHistory().execute();
        }else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    //----------------------------------------

    public class PostRefferHistory extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(RefferUserActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/winnerseven/api/getuserrefferby");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", AppPreference.getId(RefferUserActivity.this));

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

                    BufferedReader in = new BufferedReader(new
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
                    return sb.toString();

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

                JSONObject jsonObject = null;
                String s = result.toString();
                try {
                    jsonObject = new JSONObject(result);
                    String responce = jsonObject.getString("responce");
                    if (responce.equals("true")) {
                        JSONArray massageArray = jsonObject.getJSONArray("massage");
                        for (int i = 0; i < massageArray.length(); i++) {
                            JSONObject dataObj = massageArray.getJSONObject(i);
                            String user_id = dataObj.getString("user_id");
                            String name = dataObj.getString("name");
                            String email = dataObj.getString("email");
                            String password = dataObj.getString("password");
                            String mobile = dataObj.getString("mobile");
                            String address = dataObj.getString("address");
                            String type = dataObj.getString("type");
                            String reffercode = dataObj.getString("reffercode");
                            String refferby = dataObj.getString("refferby");
                            String bankstatus = dataObj.getString("bankstatus");
                            String walletbal = dataObj.getString("walletbal");
                            String image = dataObj.getString("image");
                            String status = dataObj.getString("status");
                            reff_list.add(new RefferModel(user_id, name, email, password, mobile, address, type,reffercode,refferby,
                                    bankstatus,walletbal,image,status));
                        }

                        refferAdapter = new RefferAdapter(RefferUserActivity.this, reff_list);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RefferUserActivity.this);
                        refferList.setLayoutManager(mLayoutManager);
                        refferList.setItemAnimator(new DefaultItemAnimator());
                        refferList.setAdapter(refferAdapter);

                    } else {
                        Toast.makeText(RefferUserActivity.this, "Oops! No Data.", Toast.LENGTH_LONG).show();
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
