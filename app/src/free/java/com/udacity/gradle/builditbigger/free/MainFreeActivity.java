package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jokegce.JokeGenerator;
import com.udacity.gradle.builditbigger.GetJokeFromGCETask;
import com.udacity.gradle.builditbigger.R;

import joketelling.jd.com.displayjokealib.JokeDisplayActivity;
import timber.log.Timber;


public class MainFreeActivity extends AppCompatActivity implements GetJokeFromGCETask.JokeTaskCallBack {

    private  JokeGenerator jokeGenerator;
    private static final String JOKE_KEY = "joke_key";
    private InterstitialAd mInterAd;
    private String mCurrentJoke = null;
    private boolean isAdClose = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.v("On Create JokeBean Telling ");
        jokeGenerator = JokeGenerator.getInstance();
        initializeInterAd();
    }

    private void initializeInterAd (){
        // initialize interstitial ad
        mInterAd = new InterstitialAd(this);
        mInterAd.setAdUnitId(getString(R.string.ad_id));
        mInterAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterAd();
                isAdClose = true;
                displayJoke(mCurrentJoke);
            }
        });
        requestNewInterAd();
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
        // reset string to null
        mCurrentJoke = null;
        isAdClose = true;
        new GetJokeFromGCETask().execute(this);
        // display ad here
        if (mInterAd.isLoaded()){
            mInterAd.show();
            isAdClose = false;
        }
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
        mCurrentJoke = s;
        // only call display joke if ad already closed
        if (mInterAd.isLoading() || isAdClose){
            displayJoke(mCurrentJoke);
        }
    }

    /**
     * request new ad to be loaded
     */
    private void requestNewInterAd(){
        AdRequest req = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterAd.loadAd(req);
    }
}
