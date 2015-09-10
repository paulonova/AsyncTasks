package com.example.paulovilanova.threadingnothreading;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final static String TAG = "ThreadingAsyncTask";

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private int mDelay = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView)findViewById(R.id.imageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadIcon();
                new LoadIconTask().execute(R.drawable.painter);
            }
        });


        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "I´m Working", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class LoadIconTask extends AsyncTask<Integer, Integer, Bitmap>{

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Integer... resId) {
            Bitmap tmp = BitmapFactory.decodeResource(getResources(),resId[0]);

            //simulating long running operation
            for (int i = 1; i < 11 ; i++) {
                sleep();
                publishProgress(i*10);
            }
            return tmp;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mImageView.setImageBitmap(result);
        }
    }

    private void sleep(){
        try {
            Thread.sleep(mDelay);
        } catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }
    }


//    private void loadIcon(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(mDelay);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.painter);
//                mImageView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mImageView.setImageBitmap(mBitmap);
//                    }
//                });
//            }
//        }).start();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
