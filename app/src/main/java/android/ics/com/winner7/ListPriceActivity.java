package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.ics.com.winner7.Adapter.PriceAdapter;
import android.ics.com.winner7.Model.PriceModel;
import android.ics.com.winner7.Utils.Connectivity;
import android.ics.com.winner7.Utils.HttpHandler;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListPriceActivity extends AppCompatActivity {
    RecyclerView priceLIstre;
    ArrayList<PriceModel> price_list;
    private PriceAdapter priceAdapter;
    String server_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_price);

        priceLIstre = (RecyclerView)findViewById(R.id.priceLIstre);

        price_list = new ArrayList<>();

        if (Connectivity.isNetworkAvailable(this)){
            new GetToplist().execute();
        }else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    //----------------------------------------------

    class GetToplist extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ListPriceActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "https://winner7quiz.com/api/getwinnerprice";
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
                        String amount = c.getString("amount");
                        String remark = c.getString("remark");
                        String winners = c.getString("winners");
                        String status = c.getString("status");
                        price_list.add(new PriceModel(id, amount, remark, winners, status));
                    }

                    priceAdapter = new PriceAdapter(ListPriceActivity.this, price_list);
//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ListPriceActivity.this, 2);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(ListPriceActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    priceLIstre.setLayoutManager(mLayoutManager);
                    priceLIstre.setItemAnimator(new DefaultItemAnimator());
                    priceLIstre.setAdapter(priceAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }
}
