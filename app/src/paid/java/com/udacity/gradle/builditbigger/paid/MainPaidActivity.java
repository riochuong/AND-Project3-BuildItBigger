package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jokegce.JokeGenerator;
import com.udacity.gradle.builditbigger.GetJokeFromGCETask;
import com.udacity.gradle.builditbigger.R;
import com.wang.avi.AVLoadingIndicatorView;

import joketelling.jd.com.displayjokealib.JokeDisplayActivity;
import timber.log.Timber;


public class MainPaidActivity extends AppCompatActivity implements GetJokeFromGCETask.JokeTaskCallBack {

    private  JokeGenerator jokeGenerator;
    private static final String JOKE_KEY = "joke_key";
    private AVLoadingIndicatorView mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.v("On Create JokeBean Telling ");
        jokeGenerator = JokeGenerator.getInstance();
        mSpinner = (AVLoadingIndicatorView) findViewById(R.id.spinner);
        mSpinner.hide();
    }


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

    /* no pressing the button will trigger the async task*/
    public void tellJoke(View view) {
        mSpinner.show();
        new GetJokeFromGCETask().execute(this);
    }

    public void displayJoke(String joke){
        if (joke != null){
            Intent intent = new Intent(this, JokeDisplayActivity.class);
            intent.putExtra(JOKE_KEY, joke);
            startActivity(intent);
        }
    }


    @Override
    public void onJokeFinishLoading(String s) {
          displayJoke(s);
          mSpinner.hide();
    }
}
