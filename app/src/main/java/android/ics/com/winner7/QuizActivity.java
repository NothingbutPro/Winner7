package android.ics.com.winner7;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.ics.com.winner7.Adapter.TestAdapter;
import android.ics.com.winner7.Model.TestModel;
import android.ics.com.winner7.Parsing_Quiz.Quiz;
import android.ics.com.winner7.Utils.Connectivity;
import android.ics.com.winner7.Utils.HttpHandler;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class QuizActivity extends AppCompatActivity {
    String server_url;
   static int  counter=0;
   TextView tv_quiz_prev,tv_quiz_next;
   TextView questxt;
    TextView ttxp;
    int time = 10;
    RelativeLayout rl_ans1,rl_ans2,rl_ans3,rl_ans4;
    TextView tv_quiz_subject, tv_quiz_ans1, tv_quiz_ans2, tv_quiz_ans3, tv_quiz_ans4, tv_quiz_ans5, tv_quiz_ans6;
    String myans1 ,myans2,myans3,myans4 ,myans;
    LinkedList<Quiz> quizLinkedList = new LinkedList<>();

    @Override
    public void onBackPressed() {
        counter=0;
        quizLinkedList.clear();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tv_quiz_subject = (TextView) findViewById(R.id.tv_quiz_subject);
        questxt = (TextView) findViewById(R.id.questxt);
        ttxp = (TextView) findViewById(R.id.ttxp);
        tv_quiz_ans1 = (TextView) findViewById(R.id.tv_quiz_ans1);
        tv_quiz_ans2 = (TextView) findViewById(R.id.tv_quiz_ans2);
        tv_quiz_ans3 = (TextView) findViewById(R.id.tv_quiz_ans3);
        tv_quiz_ans4 = (TextView) findViewById(R.id.tv_quiz_ans4);
        tv_quiz_ans5 = (TextView) findViewById(R.id.tv_quiz_ans5);
        tv_quiz_ans6 = (TextView) findViewById(R.id.tv_quiz_ans6);
        tv_quiz_prev = (TextView) findViewById(R.id.tv_quiz_prev);
        tv_quiz_next = (TextView) findViewById(R.id.tv_quiz_next);

        rl_ans1 = (RelativeLayout)findViewById(R.id.rl_ans1);
        rl_ans2 = (RelativeLayout)findViewById(R.id.rl_ans2);
        rl_ans3 = (RelativeLayout)findViewById(R.id.rl_ans3);
        rl_ans4 = (RelativeLayout)findViewById(R.id.rl_ans4);

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                ttxp.setText("0:" + checkDigit(time));
                time--;
            }

            public void onFinish() {
          //      linTest.setVisibility(View.VISIBLE);
                //  ttt.setText("try again");
            }

        }.start();


        rl_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_ans1.setBackgroundColor(Color.GREEN);
                rl_ans2.setBackgroundColor(Color.GRAY);
                rl_ans3.setBackgroundColor(Color.GRAY);
                rl_ans4.setBackgroundColor(Color.GRAY);
                myans1 = "A";
                myans = "A";
                myans2 = "X";
                myans3 = "X";
                myans4 = "X";
            }
        }); rl_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_ans2.setBackgroundColor(Color.GREEN);
                rl_ans1.setBackgroundColor(Color.GRAY);
                rl_ans3.setBackgroundColor(Color.GRAY);
                rl_ans4.setBackgroundColor(Color.GRAY);
                myans2 = "B";
                myans = "B";
                myans1 = "X";
                myans3 = "X";
                myans4 = "X";
            }
        }); rl_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this, "r13 clicked", Toast.LENGTH_SHORT).show();
                rl_ans3.setBackgroundColor(Color.GREEN);
                rl_ans1.setBackgroundColor(Color.GRAY);
                rl_ans2.setBackgroundColor(Color.GRAY);
                rl_ans4.setBackgroundColor(Color.GRAY);
               // rl_ans3.setBackgroundColor(Color.GRAY);
                myans3 = "C";
                myans = "C";
                myans1 = "X";
                myans2 = "X";
                myans4 = "X";
            }
        }); rl_ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_ans4.setBackgroundColor(Color.GREEN);
                rl_ans1.setBackgroundColor(Color.GRAY);
                rl_ans2.setBackgroundColor(Color.GRAY);
                rl_ans3.setBackgroundColor(Color.GRAY);
                myans4 = "D";
                myans = "D";
                myans1 = "X";
                myans2 = "X";
                myans3 = "X";
            }
        });

        tv_quiz_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("counterwa" , ""+counter);
              //  quizLinkedList.add(new Quiz(id,Que,Ans_1,Ans_2,Ans_3 ,Ans_4,Ans,marks,Createdate,status ,myans));
                try {
                    quizLinkedList.get(counter).setMyeans(myans);
                    try {
                        String mynextans = quizLinkedList.get(1 + counter).getMyeans().toString();
                        if (quizLinkedList.get(1 + counter).getMyeans().toString().length() != 0) {
                            if(mynextans.equals("A"))
                            {
                                counter++;
                                rl_ans1.setBackgroundColor(Color.GREEN);
                                //    rl_ans4.setBackgroundColor(Color.GRAY);
                                rl_ans2.setBackgroundColor(Color.GRAY);
                                rl_ans3.setBackgroundColor(Color.GRAY);
                                rl_ans4.setBackgroundColor(Color.GRAY);
                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at "+counter);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }else if(mynextans.equals("B"))
                            {
                                counter++;
                                rl_ans2.setBackgroundColor(Color.GREEN);
                                rl_ans1.setBackgroundColor(Color.GRAY);
                                rl_ans4.setBackgroundColor(Color.GRAY);
                                rl_ans3.setBackgroundColor(Color.GRAY);
                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at "+counter);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }else if(mynextans.equals("C"))
                            {
                                counter++;
                                rl_ans3.setBackgroundColor(Color.GREEN);
                                rl_ans1.setBackgroundColor(Color.GRAY);
                                rl_ans2.setBackgroundColor(Color.GRAY);
                                rl_ans4.setBackgroundColor(Color.GRAY);
                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at "+counter);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }else if(mynextans.equals("D"))
                            {
                                counter++;
                                rl_ans4.setBackgroundColor(Color.GRAY);
                                rl_ans1.setBackgroundColor(Color.GREEN);
                                rl_ans2.setBackgroundColor(Color.GREEN);
                                rl_ans3.setBackgroundColor(Color.GREEN);
                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at "+counter);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }else {
                                counter++;
                                rl_ans4.setBackgroundColor(Color.GRAY);
                                rl_ans1.setBackgroundColor(Color.GRAY);
                                rl_ans2.setBackgroundColor(Color.GRAY);
                                rl_ans3.setBackgroundColor(Color.GRAY);
                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at "+counter);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }
                        }else {
                            Toast.makeText(QuizActivity.this, "they are not", Toast.LENGTH_SHORT).show();

                            counter++;
                            rl_ans4.setBackgroundColor(Color.GRAY);
                            rl_ans1.setBackgroundColor(Color.GRAY);
                            rl_ans2.setBackgroundColor(Color.GRAY);
                            rl_ans3.setBackgroundColor(Color.GRAY);
                            tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                            questxt.setText("You are at "+counter);
                            tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                            tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                            tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                            tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        counter++;
                        rl_ans4.setBackgroundColor(Color.GRAY);
                        rl_ans1.setBackgroundColor(Color.GRAY);
                        rl_ans2.setBackgroundColor(Color.GRAY);
                        rl_ans3.setBackgroundColor(Color.GRAY);
                        tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                        questxt.setText("You are at "+counter);
                        tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                        tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                        tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                        tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());

                    }

                }catch (Exception e)
                {
                    e.printStackTrace();


                    try {
                        tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                        rl_ans4.setBackgroundColor(Color.GRAY);
                        rl_ans1.setBackgroundColor(Color.GRAY);
                        rl_ans2.setBackgroundColor(Color.GRAY);
                        rl_ans3.setBackgroundColor(Color.GRAY);

                        questxt.setText("You are at "+counter);
                        tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                        tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                        tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                        tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                    }catch (Exception Ex)
                    {
                        Ex.printStackTrace();
//                        counter =0;
                        Toast.makeText(QuizActivity.this, "THat was last question", Toast.LENGTH_SHORT).show();
                    }

                }
             //  new  GetQuizQuestions(myans).execute();


            }
        });
        tv_quiz_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  new  GetQuizQuestions(myans).execute();
                try {
                    Log.e("counterwa" , ""+counter);
                    int coun3 = counter-1;
                    String mynextans = quizLinkedList.get(coun3).getMyeans().toString();
                    if (quizLinkedList.get(coun3).getMyeans().toString().length() != 0) {
                        if(mynextans.equals("A"))
                        {
                            if(counter !=0) {
                                counter--;
                                rl_ans1.setBackgroundColor(Color.GREEN);
                                rl_ans2.setBackgroundColor(Color.GRAY);
                                rl_ans3.setBackgroundColor(Color.GRAY);
                                rl_ans4.setBackgroundColor(Color.GRAY);

                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                String sx = quizLinkedList.get(counter).getQue();
                                Log.e("question " , ""+quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at " + counter);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());

                            }else {
//                                counter =0;
                                Toast.makeText(QuizActivity.this, "you reachead first", Toast.LENGTH_SHORT).show();
                            }
                        }else if(mynextans.equals("B"))
                        {
                            if(counter !=0) {
                                counter--;
                                rl_ans2.setBackgroundColor(Color.GREEN);
                                rl_ans1.setBackgroundColor(Color.GRAY);
                                rl_ans3.setBackgroundColor(Color.GRAY);
                                rl_ans4.setBackgroundColor(Color.GRAY);

                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at " + counter);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }else {
                                Toast.makeText(QuizActivity.this, "You are at first", Toast.LENGTH_SHORT).show();
                            }
                        }else if(mynextans.equals("C"))
                        {
                            if(counter !=0) {
                                counter--;
                                rl_ans3.setBackgroundColor(Color.GREEN);
                                rl_ans1.setBackgroundColor(Color.GRAY);
                                rl_ans2.setBackgroundColor(Color.GRAY);
                                rl_ans4.setBackgroundColor(Color.GRAY);


                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at " + coun3);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }else {
                                Toast.makeText(QuizActivity.this, "fisrt", Toast.LENGTH_SHORT).show();
                            }
                        }else if(mynextans.equals("D"))
                        {
                            if(counter !=0) {
                                counter--;
                                rl_ans4.setBackgroundColor(Color.GREEN);
                                rl_ans1.setBackgroundColor(Color.GRAY);
                                rl_ans2.setBackgroundColor(Color.GRAY);
                                rl_ans3.setBackgroundColor(Color.GRAY);

                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at " + coun3);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }else {
                                Toast.makeText(QuizActivity.this, "at fisrt", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(counter !=0) {
                                counter--;
                                rl_ans1.setBackgroundColor(Color.GRAY);
                                rl_ans2.setBackgroundColor(Color.GRAY);
                                rl_ans3.setBackgroundColor(Color.GRAY);
                                rl_ans4.setBackgroundColor(Color.GRAY);

                                tv_quiz_subject.setText(quizLinkedList.get(counter).getQue());
                                questxt.setText("You are at " + coun3);
                                tv_quiz_ans1.setText(quizLinkedList.get(counter).getAns1());
                                tv_quiz_ans2.setText(quizLinkedList.get(counter).getAns2());
                                tv_quiz_ans3.setText(quizLinkedList.get(counter).getAns3());
                                tv_quiz_ans4.setText(quizLinkedList.get(counter).getAns4());
                            }
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(QuizActivity.this, "You are at first", Toast.LENGTH_SHORT).show();
                    counter =0;
                }
//                rl_ans4.setBackgroundColor(Color.GRAY);

            }
        });

        if (Connectivity.isNetworkAvailable(QuizActivity.this)) {
            new GetQuizQuestions("").execute();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
    //------------------------------------------

    class GetQuizQuestions extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;
        String myans;

        public GetQuizQuestions(String myans) {
            this.myans = myans;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(QuizActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://ihisaab.in/winnerseven/api/getquiz";
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
//                            if(counter >=0 && counter<=data_array.length() && counter==i) {

                                JSONObject c = data_array.getJSONObject(i);
                                String id = c.getString("id");
                                String Que = c.getString("Que");
                                String Ans_1 = c.getString("Ans_1");
                                String Ans_2 = c.getString("Ans_2");
                                String Ans_3 = c.getString("Ans_3");
                                String Ans_4 = c.getString("Ans_4");
                                String Ans = c.getString("Ans");
                                String marks = c.getString("marks");
                                String Createdate = c.getString("Createdate");
                                String status = c.getString("status");
                                quizLinkedList.add(new Quiz(id,Que,Ans_1,Ans_2,Ans_3 ,Ans_4,Ans,marks,Createdate,status ,myans));
                                Log.e("quizLinkedList" , ""+quizLinkedList.get(i).getAns());
                                if(i==0) {
                                    tv_quiz_subject.setText(Que);
                                    questxt.setText("You are at " + counter);
                                    tv_quiz_ans1.setText(Ans_1);
                                    tv_quiz_ans2.setText(Ans_2);
                                    tv_quiz_ans3.setText(Ans_3);
                                    tv_quiz_ans4.setText(Ans_4);
                                }
//                                if(myans.equals(Ans))
//                                {
//                                    Toast.makeText(QuizActivity.this, "Right ans", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Toast.makeText(QuizActivity.this, "Wrong ans", Toast.LENGTH_SHORT).show();
//                                }
                               /*     tv_quiz_ans5.setText();
                            tv_quiz_ans6.setText();*/
//                            }
                            else {
                                Toast.makeText(QuizActivity.this, "LAst Question", Toast.LENGTH_SHORT).show();
//                                counter=0;
                            }
                        }
                    } else {
                        Toast.makeText(QuizActivity.this, "Some Problem!", Toast.LENGTH_SHORT).show();
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
