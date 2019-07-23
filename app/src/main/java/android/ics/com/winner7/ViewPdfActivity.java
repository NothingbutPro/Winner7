package android.ics.com.winner7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewPdfActivity extends AppCompatActivity {
    WebView book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        String title = getIntent().getStringExtra("title");

        //  getSupportActionBar().setTitle(title);
/*
        WebView book = (WebView) findViewById(R.id.wv_book);
        book.getSettings().setJavaScriptEnabled(true);
        book.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);



        String getExtension = pdf.substring(pdf.lastIndexOf("."));
        if (getExtension.contentEquals(".pdf") || getExtension.contentEquals(".pptx")) {
            book.loadUrl("https://docs.google.com/viewer?url=" + "http://ihisaab.in/winnerseven/uploads/pdf/" + pdf);
        } else {
            book.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    view.loadUrl(url);
                    return false; // then it is not handled by default action
                }
            });
            book.loadUrl(pdf);
        }

    }*/
        String pdf = getIntent().getStringExtra("title");
        book = (WebView) findViewById(R.id.wv_book);
        book.setWebViewClient(new WebViewClient());
        book.getSettings().setSupportZoom(true);
        book.getSettings().setJavaScriptEnabled(true);
      //  String url = "http://ihisaab.in/winnerseven/uploads/pdf/ + pdf";
        book.loadUrl("https://docs.google.com/gview?embedded=true&url="+"http://ihisaab.in/winnerseven/uploads/pdf/"+ pdf);
     //   book.loadUrl("https://docs.google.com/gview?embedded=" + "http://ihisaab.in/winnerseven/uploads/pdf/" + pdf);
    }
}
