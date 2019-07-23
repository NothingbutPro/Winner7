package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.ics.com.winner7.Adapter.PriceAdapter;
import android.ics.com.winner7.Adapter.TestAdapter;
import android.ics.com.winner7.Model.PriceModel;
import android.ics.com.winner7.Model.TestModel;
import android.ics.com.winner7.Utils.Connectivity;
import android.ics.com.winner7.Utils.HttpHandler;
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

import java.util.ArrayList;

public class TestSeriesActivity extends AppCompatActivity {
    RecyclerView testList;
    ArrayList<TestModel> test_list;
    private TestAdapter testAdapter;
    String server_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series);

        testList = (RecyclerView)findViewById(R.id.testList);

        test_list = new ArrayList<>();

        if (Connectivity.isNetworkAvailable(this)) {
            new GetPdflist().execute();
        }else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    //---------------------------------

    class GetPdflist extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(TestSeriesActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://ihisaab.in/winnerseven/api/getpdf";
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("sever_url>>>>>>>>>", server_url);
            output = HttpHandler.makeServiceCall(server_url);
            //   Log.e("getcomment_url", output);
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
                    JSONArray massage_array = obj.getJSONArray("massage");
                    for (int i = 0; i < massage_array.length(); i++) {
                        JSONObject c = massage_array.getJSONObject(i);
                        String id = c.getString("id");
                        String pdf = c.getString("pdf");
                        String date = c.getString("date");
                        String type = c.getString("type");
                        test_list.add(new TestModel(id, pdf, date, type));
                    }

                    testAdapter = new TestAdapter(TestSeriesActivity.this, test_list);
//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ListPriceActivity.this, 2);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(TestSeriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    testList.setLayoutManager(mLayoutManager);
                    testList.setItemAnimator(new DefaultItemAnimator());
                    testList.setAdapter(testAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }
}
