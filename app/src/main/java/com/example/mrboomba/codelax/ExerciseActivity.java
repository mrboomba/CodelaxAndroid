package com.example.mrboomba.codelax;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ExerciseActivity extends AppCompatActivity {

    private Button get;
    private ImageView pic;
    private static final String SRC = "http://192.168.1.33:8080/api/pic";
    private static final String Tag = "mrboomba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        pic = (ImageView) findViewById(R.id.imageView1);
        new GetImageFromUrl().execute(SRC);


    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        @Override protected Bitmap doInBackground(String... urls) {
            Bitmap map = null; for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }
        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            pic.setImageBitmap(result);
        } // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions); stream.close();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        } // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                { stream = httpConnection.getInputStream();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }


}
