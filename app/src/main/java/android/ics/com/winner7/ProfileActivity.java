package android.ics.com.winner7;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.ics.com.winner7.Adapter.TestAdapter;
import android.ics.com.winner7.Model.TestModel;
import android.ics.com.winner7.Utils.AppPreference;
import android.ics.com.winner7.Utils.CameraUtils;
import android.ics.com.winner7.Utils.CommonUtils;
import android.ics.com.winner7.Utils.Connectivity;
import android.ics.com.winner7.Utils.HttpHandler;
import android.ics.com.winner7.Utils.Utilities;
import android.ics.com.winner7.Utils.Utility;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {
    EditText profileName, profileEmail, banker_Name, bankNmae, branch, ifsc, account, bankCity;
    String ProfileName, ProfileEmail, Banker_Name, BankNmae, Branch, Ifsc, Account, BankCity;
    Button profile_submit;
    private String userChoosenTask;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int BITMAP_SAMPLE_SIZE = 8;
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final String IMAGE_EXTENSION = "jpg";
    private static String imageStoragePath;
    private Bundle savedInstanceState;
    int Gallery_view = 2;
    String image;
    ImageView badge_notification_1;
    CircleImageView profileimage;
    String server_url;
    String communStr = "http://ihisaab.in/winnerseven/uploads/userprofile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        profileName = (EditText) findViewById(R.id.profileName);
        profileEmail = (EditText) findViewById(R.id.profileEmail);
        banker_Name = (EditText) findViewById(R.id.banker_Name);
        bankNmae = (EditText) findViewById(R.id.bankNmae);
        branch = (EditText) findViewById(R.id.branch);
        ifsc = (EditText) findViewById(R.id.ifsc);
        account = (EditText) findViewById(R.id.account);
        bankCity = (EditText) findViewById(R.id.bankCity);
        profileimage = (CircleImageView) findViewById(R.id.profileimage);
        badge_notification_1 = (ImageView) findViewById(R.id.badge_notification_1);
        profile_submit = (Button) findViewById(R.id.profile_submit);

        //ifsc.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ifsc.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(11)});

        Picasso.with(ProfileActivity.this)
                .load(communStr + image)
                .placeholder(R.drawable.prf)
                .into(profileimage);

        badge_notification_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        profile_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileName = profileName.getText().toString();
                ProfileEmail = profileEmail.getText().toString();
                Banker_Name = banker_Name.getText().toString();
                BankNmae = bankNmae.getText().toString();
                Branch = branch.getText().toString();
                Ifsc = ifsc.getText().toString();
                Account = account.getText().toString();
                BankCity = bankCity.getText().toString();

                if (Connectivity.isNetworkAvailable(ProfileActivity.this)) {
                    new PostProfile().execute();
                } else {
                    Toast.makeText(ProfileActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Connectivity.isNetworkAvailable(ProfileActivity.this)) {
            new GetProfileDetails().execute();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    //----------------------------------------------------

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        if (CameraUtils.checkPermissions(getApplicationContext())) {
                            captureImage();
                            restoreFromBundle(savedInstanceState);
                        } else {
                            requestCameraPermission(MEDIA_TYPE_IMAGE);
                        }
                    //captureImage();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //------------ private void galleryIntent()
    private void galleryIntent() {

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, Gallery_view);
    }

 /*   private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }

    /**
     * Restoring image path from saved instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }
    //--------------------------------------------------------------------

    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    }
                }
            }
        }
    }

    private void previewCapturedImage() {

        try {
            // hide video preview
            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
            new ImageCompression().execute(imageStoragePath);

            profileimage.setImageBitmap(bitmap);
            File imgFile = new File(imageStoragePath);
/*
            if (imgFile.exists()) {
                //  imgPolicyNo.setImageURI(Uri.fromFile(imgFile));
                //  show(imgFile);
                new ImageUploadTask(imgFile).execute();
                //  Toast.makeText(ClaimActivity.this,"data:="+imgFile,Toast.LENGTH_LONG).show();
            }*/

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(ProfileActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    //-------------------------------------------------------------

    class ImageUploadTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String result = "";
        File Image;

        public ImageUploadTask(File imgFile) {
            this.Image = imgFile;

        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ProfileActivity.this);
            dialog.setMessage("Processing");

            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... params) {
            try {

                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                entity.addPart("user_id", new StringBody(AppPreference.getId(ProfileActivity.this)));
                entity.addPart("profile_image", new FileBody(Image));


                result = Utilities.postEntityAndFindJson("https://winner7quiz.com/api/updateprofile", entity);

                return result;

            } catch (Exception e) {
                // something went wrong. connection with the server error
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // Log.v("profile",result);

            String result1 = result;
            if (result != null) {

                dialog.dismiss();
                Log.e("result_Image", result);
                try {
                    JSONObject object = new JSONObject(result);
                    String responce = object.getString("responce");
                    String massage = object.getString("massage");

                    if (responce.equals("true")) {
//                        if (!image.isEmpty()){
//                            AppPreference.setProfileImage(ProfileActivity.this,image);
//                        }

                        Toast.makeText(ProfileActivity.this, massage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, massage, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(ProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            } else {
                dialog.dismiss();
                //  Toast.makeText(Registration.this, "No Response From Server", Toast.LENGTH_LONG).show();
            }
        }
    }

    //.............................................................................

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                previewCapturedImage();

                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == Gallery_view && data != null) {
            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            imageStoragePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            profileimage.setImageBitmap(BitmapFactory.decodeFile(imageStoragePath));
            if (imageStoragePath != null) {
                new ImageCompression().execute(imageStoragePath);
            }

            cursor.close();
        }
    }

    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    //--------------------------------------------------------

    public class ImageCompression extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length == 0 || strings[0] == null)
                return null;

            return CommonUtils.compressImage(strings[0]);
        }

        protected void onPostExecute(String imagePath) {
            // imagePath is path of new compressed image.
//            mivImage.setImageBitmap(BitmapFactory.decodeFile(new File(imagePath).getAbsolutePath()));
            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    new ImageUploadTask(imgFile).execute();
                }
            } else {
                //  AlertDialogCreate();
            }

        }
    }

    //-------------------------------------------------------

    public class PostProfile extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(ProfileActivity.this);
            dialog.show();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://winner7quiz.com/api/updateinfo");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", AppPreference.getId(ProfileActivity.this));
                postDataParams.put("name", ProfileName);
                postDataParams.put("email", ProfileEmail);
                postDataParams.put("banker_name", Banker_Name);
                postDataParams.put("bank_name", BankNmae);
                postDataParams.put("bank_barch", Branch);
                postDataParams.put("ifsc", Ifsc);
                postDataParams.put("account_no", Account);
                postDataParams.put("bank_city", BankCity);

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                String s = result.toString();
                try {
                    jsonObject = new JSONObject(result);
                    String responce = jsonObject.getString("responce");
                    String massage = jsonObject.getString("massage");

                    if (responce.equals("true")) {
                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Oops! Some Problem!", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    //------------------------------------------------------------------

    class GetProfileDetails extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ProfileActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "https://winner7quiz.com/api/getuserdetails?user_id=" + AppPreference.getId(ProfileActivity.this);
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
                            JSONObject c = data_array.getJSONObject(i);
                            String user_id = c.getString("user_id");
                            String name = c.getString("name");
                            String email = c.getString("email");
                            String password = c.getString("password");
                            String mobile = c.getString("mobile");
                            String address = c.getString("address");
                            String type = c.getString("type");
                            String reffercode = c.getString("reffercode");
                            String bankstatus = c.getString("bankstatus");
                            String walletbal = c.getString("walletbal");
                            image = c.getString("image");
                            String status = c.getString("status");

                            profileName.setText(name);
                            profileEmail.setText(email);


                        }


                    } else {
                        Toast.makeText(ProfileActivity.this, "Some Problem!!", Toast.LENGTH_SHORT).show();
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
