package android.ics.com.winner7;

import android.content.Intent;
import android.ics.com.winner7.Utils.SessionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity {
    Button bt_user,bt_vender;
    SessionManager manager;
    String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        manager = new SessionManager(this);

        if (manager.isLoggedIn()) {
            Intent intent = new Intent(ChoiceActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        bt_user = (Button) findViewById(R.id.bt_user);
        bt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                intent.putExtra("Type","1");
                startActivity(intent);
                finish();
            }
        });

        bt_vender = (Button) findViewById(R.id.bt_vender);
        bt_vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                intent.putExtra("Type","2");
                startActivity(intent);
                finish();
            }
        });
    }
}
