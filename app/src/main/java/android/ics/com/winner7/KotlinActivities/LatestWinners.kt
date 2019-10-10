package android.ics.com.winner7.KotlinActivities;

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.ics.com.winner7.R
import android.ics.com.winner7.Utils.AppPreference
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.winnerhistoryactivity.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class LatestWinners : AppCompatActivity(){
        val myListData=arrayOf<String>()
public lateinit var context:Context
//
//    inline fun <reified T : Activity> Context.startActivity() {
//        startActivity(Intent(this, T::class.java))
//    }
        override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.latestwinners)
        setSupportActionBar(toolbar)
        ButterKnife.bind(this@LatestWinners);
        val context=this@LatestWinners
        GetAllWinners(context).execute();

//        myListData[""]

                }
        public class GetAllWinners(val s: Context) : AsyncTask<Void, String, String>() {
//         public  val context: s.
        val context = this@GetAllWinners.s

         lateinit var dialog: ProgressDialog

                 override fun onPreExecute() {
                 dialog = ProgressDialog(context)

                 dialog.show()

                 }
                 override fun doInBackground(vararg params: Void?): String {

                 try {

                 val url = URL("https://winner7quiz.com/api/getletestrecorde")
               //  Log.e("ference.getId(context)" , ""+AppPreference.getId(context))

              //   val postDataParams = JSONObject()
             //    postDataParams.put("user_id", AppPreference.getId(context))


              //   Log.e("postgetuserwinnerlist", postDataParams.toString())

                 val conn = url.openConnection() as HttpURLConnection
                 conn.readTimeout = 15000
                 conn.connectTimeout = 15000
                 conn.requestMethod = "GET"
                 conn.doInput = true
                 conn.doOutput = true

                 val os = conn.outputStream
                 val writer = BufferedWriter(
                 OutputStreamWriter(os, "UTF-8"))
            //     writer.write(getPostDataString(postDataParams))

                 writer.flush()
                 writer.close()
                 os.close()

                 val responseCode = conn.responseCode

                 if (responseCode == HttpsURLConnection.HTTP_OK) {


                 val r = BufferedReader(InputStreamReader(conn.inputStream))
                 val result = StringBuilder()

                 var line: String;
                 line = r.readLine()
//                     while ((line) != null) {
                 result.append(line)
//                     }
                 r.close()
                 Log.e("result is" , ""+result.toString());
                 return result.toString()

                 } else {
                 return String("false : $responseCode")
                 }
                 } catch (e: Exception) {
                 return String("Exception: " + e.message)
                 }

                 }

private fun String(s: String): String {
        return  s;

        }


        override fun onPostExecute( result: String?) {
        if (result != null) {
        dialog.dismiss()

        // JSONObject jsonObject = null;
        Log.e("HomeJsonDataToServer>>>", result)
        try {

        val jsonObject = JSONObject(result)
        val responce = jsonObject.getString("responce")

        //     amountTxt1.setText(massage);

        if (responce.equals("true", ignoreCase = true)) {
        val massage = jsonObject.getString("data")

        Toast.makeText(context, " Record Found",Toast.LENGTH_LONG).show();
        } else {
        val massage = jsonObject.getString("data")
        if(massage.equals("recorde not found")) {
        Toast.makeText(context, "No Record Found",Toast.LENGTH_LONG).show();
        }else{
        Toast.makeText(context, " Record Found",Toast.LENGTH_LONG).show();
        }
        }

        } catch (e: JSONException) {
        e.printStackTrace()
        }

        }
        }

        @Throws(Exception::class)
                 fun getPostDataString(params: JSONObject): String {

                         val result = StringBuilder()
                         var first = true

                         val itr = params.keys()

                         while (itr.hasNext()) {

                         val key = itr.next()
                         val value = params.get(key)

                         if (first)
                         first = false
                         else
                         result.append("&")

                         result.append(URLEncoder.encode(key, "UTF-8"))
                         result.append("=")
                         result.append(URLEncoder.encode(value.toString(), "UTF-8"))

                         }
                         return result.toString()
                         }
                         }
                         }