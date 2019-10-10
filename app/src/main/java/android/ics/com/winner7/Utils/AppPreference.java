package android.ics.com.winner7.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.ics.com.winner7.KotlinActivities.WinnerHistoryActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class AppPreference {

    public static final String SHARED_PREFERENCE_NAME = "SATSUNG";
    public static final String ID = "id";
    public static final String IS_LOGIN = "isLogin";
    public static final String TYPE = "type";
    public static final String EMAIL = "user_email";
    public static final String MOBILE = "mobile";
    public static final String SUBSERVICE = "user_subservice";

    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;


    public static void setId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID, value);
        editor.commit();
    }

    public static String getId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(ID, "");
    }

    //--------------------------------
    public static void setType(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TYPE, value);
        editor.commit();
    }

    public static String getType(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(TYPE, "");
    }

    //-------------------------------------
    public static void setEmail(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, value);
        editor.commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(EMAIL, "");
    }

    //------------------------------
    public static void setMobile(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MOBILE, value);
        editor.commit();
    }

    public static String getMobile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(MOBILE, "");
    }

    //-----------------------------------
    public static void setSubservice(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SUBSERVICE, value);
        editor.commit();
    }

    public static String getSubservice(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(SUBSERVICE, "");
    }




    public void cleardatetime(){
        editor2.clear();
        editor2.commit();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }


}
