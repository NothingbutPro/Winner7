package android.ics.com.winner7.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.ics.com.winner7.Model.PriceModel;
import android.ics.com.winner7.Model.TestModel;
import android.ics.com.winner7.R;
import android.ics.com.winner7.Utils.FileDownloader;
import android.ics.com.winner7.ViewPdfActivity;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private static final String TAG = "TestAdapter";
    private ArrayList<TestModel> tstList;
    public Context context;
    String resId = "";
    String finalStatus = "";
    String Image;
    String Title;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_book_title,tv_book_view,tv_book_download;
        //  LinearLayout card;
        ImageView idProductImage;
        LinearLayout mainButton;
        int pos;

        public ViewHolder(View view) {
            super(view);

            tv_book_title = (TextView) view.findViewById(R.id.tv_book_title);
            tv_book_view = (TextView) view.findViewById(R.id.tv_book_view);
            tv_book_download = (TextView) view.findViewById(R.id.tv_book_download);
        }
    }

    public static Context mContext;

    public TestAdapter(Context mContext, ArrayList<TestModel> test_list) {
        context = mContext;
        tstList = test_list;

    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_row, parent, false);

        return new TestAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestAdapter.ViewHolder viewHolder, final int position) {
        TestModel testModel = tstList.get(position);
        viewHolder.tv_book_title.setText(testModel.getPdf());
    //    viewHolder.amt.setText(priceModel.getAmount());
        Title = testModel.getPdf();
        // viewHolder.card.setTag(viewHolder);

        viewHolder.tv_book_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(context, ViewPdfActivity.class);
               // viewIntent.putExtra("book_pdf", pdf_url);
                viewIntent.putExtra("title", Title);
                context.startActivity(viewIntent);
            }
        });
        viewHolder.tv_book_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File SDCardRoot = Environment.getExternalStorageDirectory();
                // create a new file, to save the downloaded file
                File file = new File(SDCardRoot + "/Winner_PDF/" + Title);
                if (file.exists()) {
                    Toast.makeText(context, "This file is already downloaded", Toast.LENGTH_SHORT).show();
                } else {
                    new DownloadFile().execute("http://ihisaab.in/winnerseven/uploads/pdf/"+ Title, Title);
                }
            }
        });
        viewHolder.pos = position;

    }

    @Override
    public int getItemCount() {
        return tstList.size();
    }

    //-----------------------------------------

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        ProgressDialog barProgressDialog;

        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Winner_PDF");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);

            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            return file.getAbsolutePath();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            barProgressDialog = new ProgressDialog(context);
            barProgressDialog.setTitle("Downloading File ...");
            barProgressDialog.setMessage("Download in progress ...");
            barProgressDialog.show();
            barProgressDialog.setCancelable(false);
            barProgressDialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        protected void setMaxProgrss(int maxval) {
            barProgressDialog.setMax(maxval);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            barProgressDialog.setMessage("Total  File size  : "
                    + " KB\n\nDownloading  " + (int) values[0]
                    + "% complete");


            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            barProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "PDF downloaded", Toast.LENGTH_SHORT).show();
        }

    }

}
