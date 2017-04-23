package joketelling.jd.com.displayjokealib;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chuondao.myapplication.backend.jokeGeneratorApi.JokeGeneratorApi;

import butterknife.BindView;

public class JokeDisplayActivity extends AppCompatActivity {

    TextView jokeContentText;
    private static final String JOKE_KEY = "joke_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        jokeContentText = (TextView) findViewById(R.id.jokeTextView);
        Intent intent = getIntent();
        String joke = intent.getStringExtra(JOKE_KEY);
        // set the joke here if it's available
        if (intent != null
                && joke != null){
            setJokeForTextView(joke);
        }
    }

    private void setJokeForTextView(String joke){
         if (jokeContentText != null){
             jokeContentText.setText(joke);
         }
    }
}
