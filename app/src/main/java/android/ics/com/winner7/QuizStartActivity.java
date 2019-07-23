package android.ics.com.winner7;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuizStartActivity extends AppCompatActivity {
    TextView ttt;
    int time = 10;
    Button strTest;
    LinearLayout linTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        ttt = (TextView) findViewById(R.id.ttt);
        strTest = (Button) findViewById(R.id.strTest);
        linTest = (LinearLayout) findViewById(R.id.linTest);

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                ttt.setText("0:" + checkDigit(time));
                time--;
            }

            public void onFinish() {
                linTest.setVisibility(View.VISIBLE);
              //  ttt.setText("try again");
            }

        }.start();

        strTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizStartActivity.this,QuizActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
