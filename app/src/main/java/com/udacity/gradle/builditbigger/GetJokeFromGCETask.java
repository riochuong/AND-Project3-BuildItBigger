package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.example.chuondao.myapplication.backend.jokeGeneratorApi.JokeGeneratorApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import timber.log.Timber;


/**
 * Created by chuondao on 4/22/17.
 */

public class GetJokeFromGCETask extends AsyncTask<Context, Void, String> {
    private static JokeGeneratorApi jokeGeneratorApiService = null;
    private Context context = null;

    public interface JokeTaskCallBack {
        void onJokeFinishLoading(String joke);
    }


    @Override
    protected String doInBackground(Context... params) {
         // initialize service here
        String result;
        if (jokeGeneratorApiService == null){
             JokeGeneratorApi.Builder builder = new JokeGeneratorApi.Builder(
                     AndroidHttp.newCompatibleTransport(),
                     new AndroidJsonFactory(),null
             ).setRootUrl("http://10.0.2.2:8080/_ah/api")
               .setGoogleClientRequestInitializer(
                       new GoogleClientRequestInitializer() {
                           @Override
                           public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                               request.setDisableGZipContent(true);
                           }
                       }
               );

           // now build the service
            jokeGeneratorApiService = builder.build();
        }
        context = params[0];

        try {
            result = jokeGeneratorApiService.getRandomJoke().execute().getJoke();
        } catch (IOException e) {
            Timber.e("Failed to retreive joke from cloud end point");
            e.printStackTrace();
            return null;
        }


        return result;
    }

    @Override
    protected void onPostExecute(String s) {
         Timber.v("OnPostExecute: Display Joke through Android Library");
         try{
             JokeTaskCallBack callBack = (JokeTaskCallBack) context;
             callBack.onJokeFinishLoading(s);
         } catch (ClassCastException e){
             e.printStackTrace();
             Timber.e("Main activity must implement JokeTaskCallBack ");
         }

    }
}
