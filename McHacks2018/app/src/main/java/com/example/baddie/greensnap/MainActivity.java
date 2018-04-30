package com.example.baddie.greensnap;

import android.Manifest;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Entity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.api.client.util.IOUtils;
import com.google.common.collect.ArrayListMultimap;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Bitmap res;
    static String rec;
    static final int REQUEST_IMAGE_CAPTURE=1;
    private static final String SERVER_ADDRESS= "http://142.157.97.243:8888/McHacks/";

    String[] types= {"Cardboard", "Glass","Metal", "Paper", "Plastic", "Trash"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.recycling);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePic();
            }
        });

    }

    private void takePic(){
        Intent takePicIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePicIntent.resolveActivity(getPackageManager()) !=null ){
            startActivityForResult(takePicIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            res = imageBitmap;
        }
        new SendImage(res, "pic").execute();

    }

    private class SendImage extends AsyncTask<Void, Void,Void> {

        Bitmap img;
        String name;
        public SendImage(Bitmap image, String name){
            this.img=image;
            this.name=name;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend;
            dataToSend= new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name","pic"));

            HttpParams httpReqParams = getHttpRequestParams();

            HttpClient client= new DefaultHttpClient(httpReqParams);
            HttpPost post= new HttpPost(SERVER_ADDRESS + "SavePicture.php");

            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse response =client.execute(post);
                //Log.d("yes",response.toString());
                //String res= response.getEntity().getContent().toString();
                //InputStream resu= new BufferedInputStream(response.getEntity().getContent());
                //String res= resu.toString();
                HttpEntity ent= response.getEntity();
                rec= EntityUtils.toString(ent);
                Log.d("hey", rec);


            }catch (Exception e){
                e.printStackTrace();
            }

            sendMessage(findViewById(android.R.id.content));

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "saved",Toast.LENGTH_SHORT).show() ;

        }

        private HttpParams getHttpRequestParams(){
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, 30000);
            HttpConnectionParams.setSoTimeout(httpRequestParams, 30000);
            return httpRequestParams;
        }
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        String message= "";
        try{

             message = types[Character.getNumericValue(rec.charAt(0))];

             Log.d("today", message);
        }catch(Exception e){
            e.printStackTrace();
        }
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
