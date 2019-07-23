package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.ics.com.winner7.Utils.AppPreference;
import android.ics.com.winner7.Utils.Connectivity;
import android.ics.com.winner7.Utils.HttpHandler;
import android.ics.com.winner7.Utils.SessionManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout btn_play;
    Button btPrice, bt_winner, bt_latest, bt_test;
    CircleImageView profile_image1;
    SessionManager manager;
    TextView amountTxt, amountTxt1;
    LinearLayout volet, upcomming;
    TextView tstDate, tstTime;
    String server_url;
    TextView peoName;
    String strType;
    LinearLayout linear1, linear2;
    Button refer_btn;
    String communStr = "http://ihisaab.in/winnerseven/uploads/userprofile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        strType = getIntent().getExtras().getString("Type");

        manager = new SessionManager(this);

        try {
            Log.e("type is" , ""+strType);
            strType = getIntent().getExtras().getString("Type");
        }catch (Exception e)
        {
            strType=  AppPreference.getType(HomeActivity.this);
            e.printStackTrace();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_play = (LinearLayout) findViewById(R.id.btn_play);
        btPrice = (Button) findViewById(R.id.btPrice);
        bt_winner = (Button) findViewById(R.id.bt_winner);
        bt_latest = (Button) findViewById(R.id.bt_latest);
        bt_test = (Button) findViewById(R.id.bt_test);
        refer_btn = (Button) findViewById(R.id.refer_btn);
        amountTxt = (TextView) findViewById(R.id.amountTxt);
        amountTxt1 = (TextView) findViewById(R.id.amountTxt1);
        tstDate = (TextView) findViewById(R.id.tstDate);
        tstTime = (TextView) findViewById(R.id.tstTime);
        peoName = (TextView) findViewById(R.id.peoName);
        volet = (LinearLayout) findViewById(R.id.volet);
        upcomming = (LinearLayout) findViewById(R.id.upcomming);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        profile_image1 = (CircleImageView) findViewById(R.id.profile_image1);
        FloatingActionButton fab = findViewById(R.id.fab);

        if (strType.equals("1")) {
            linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.GONE);
        } else if (strType.equals("2")) {
            linear2.setVisibility(View.VISIBLE);
            linear1.setVisibility(View.GONE);
        }

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        tstDate.setText(formattedDate);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuizStartActivity.class);
                startActivity(intent);
            }
        });

        btPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListPriceActivity.class);
                startActivity(intent);
            }
        });

        bt_winner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WinnerHistoryActivity.class);
                startActivity(intent);
            }
        });

        bt_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WinnerHistoryActivity.class);
                startActivity(intent);
            }
        });

        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TestSeriesActivity.class);
                startActivity(intent);
            }
        });

        profile_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        volet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, VoletActivity.class);
                startActivity(intent);
            }
        });

        upcomming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(HomeActivity.this, TestSeriesActivity.class);
                startActivity(intent);*/
            }
        });

        refer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this,RefferUserActivity.class);
                startActivity(intent);
            }
        });

        if (Connectivity.isNetworkAvailable(this)) {
            new SendJsonServer().execute();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

        if (Connectivity.isNetworkAvailable(this)) {
            new GetExamDetails().execute();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

        if (Connectivity.isNetworkAvailable(this)) {
            new GetProfilDetails().execute();
        } else {
            Toast.makeText(this, "NO Internet", Toast.LENGTH_SHORT).show();
        }

     /*   final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tstTime.setText(new SimpleDateFormat("HH:mm", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            manager.logoutUser();
            Intent intent2 = new Intent(HomeActivity.this, ChoiceActivity.class);
            startActivity(intent2);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //-----------------------------------------------------

    class SendJsonServer extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(HomeActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/winnerseven/api/getamountuser");


                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", AppPreference.getId(HomeActivity.this));


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
                    amountTxt.setText(massage);
                    //     amountTxt1.setText(massage);

                    if (responce.equalsIgnoreCase("true")) {
                    } else {
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

    //--------------------------------------------------------

    class GetExamDetails extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(HomeActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://ihisaab.in/winnerseven/api/getquiztime";
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("sever_url>>>>>>>>>", server_url);
            output = HttpHandler.makeServiceCall(server_url);
            System.out.println("getcomment_url" + output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if (output == null) {
                dialog.dismiss();
            } else {
                try {
                    dialog.dismiss();
                    JSONObject obj = new JSONObject(output);
                    String responce = obj.getString("responce");
                    if (responce.equals("true")) {
                        JSONArray data_array = obj.getJSONArray("massage");
                        for (int i = 0; i < data_array.length(); i++) {
                            JSONObject c = data_array.getJSONObject(i);
                            String id = c.getString("id");
                            String quiztime = c.getString("quiztime");
                            String type = c.getString("type");

                            tstTime.setText(quiztime);
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "Some Problem!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }

    //-------------------------------------------------------

    class GetProfilDetails extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(HomeActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://ihisaab.in/winnerseven/api/getuserdetails?user_id=" + AppPreference.getId(HomeActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("sever_url>>>>>>>>>", server_url);
            output = HttpHandler.makeServiceCall(server_url);
            System.out.println("getcomment_url" + output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if (output == null) {
                dialog.dismiss();
            } else {
                try {
                    dialog.dismiss();
                    JSONObject obj = new JSONObject(output);
                    String responce = obj.getString("responce");
                    if (responce.equals("true")) {
                        JSONArray data_array = obj.getJSONArray("data");
                        for (int i = 0; i < data_array.length(); i++) {
                            JSONObject c = data_array.getJSONObject(i);
                            String user_id = c.getString("user_id");
                            String name = c.getString("name");
                            String email = c.getString("email");
                            String password = c.getString("password");
                            String mobile = c.getString("mobile");
                            String address = c.getString("address");
                            String type = c.getString("type");
                            String reffercode = c.getString("reffercode");
                            String bankstatus = c.getString("bankstatus");
                            String walletbal = c.getString("walletbal");
                            String image = c.getString("image");
                            String status = c.getString("status");

                            peoName.setText(name);

                            Picasso.with(HomeActivity.this)
                                    .load(communStr + image)
                                    .placeholder(R.drawable.prf)
                                    .into(profile_image1);
                        }

                    } else {
                        Toast.makeText(HomeActivity.this, "Some Problem!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }
}
