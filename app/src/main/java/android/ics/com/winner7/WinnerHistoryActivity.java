package android.ics.com.winner7;

import android.ics.com.winner7.Adapter.MyListAdapter;
import android.ics.com.winner7.Model.MyListData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class WinnerHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_history);

        MyListData[] myListData = new MyListData[]{
                new MyListData("Email", R.drawable.prf),
                new MyListData("Info", R.drawable.prf),
                new MyListData("Delete", R.drawable.prf),
                new MyListData("Dialer", R.drawable.prf),
                new MyListData("Alert", R.drawable.prf),
                new MyListData("Map", R.drawable.prf),
                new MyListData("Email", R.drawable.prf),
                new MyListData("Info", R.drawable.prf),
                new MyListData("Delete", R.drawable.prf),
                new MyListData("Dialer", R.drawable.prf),
                new MyListData("Alert", R.drawable.prf),
                new MyListData("Map", R.drawable.prf),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
