package builditbigger.android.test;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.GetChars;

import com.udacity.gradle.builditbigger.GetJokeFromGCETask;
import com.udacity.gradle.builditbigger.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by chuondao on 4/25/17.
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskStringRetrievalTest  {
    private static final int TIME_WAIT_SEC = 10;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testRetreiveStringJoke() throws InterruptedException {

        // singal wait for onPostExecute test to be completed
        CountDownLatch signal = new CountDownLatch(1);
        // initialize test task which is an extends of the task we wanted to test
        final GetJokeFromGCETestTask task = new GetJokeFromGCETestTask(signal);
        final Context context = mainActivityRule.getActivity();
        mainActivityRule.getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        task.execute(context);
                    }
                }
        );
        signal.await(TIME_WAIT_SEC, TimeUnit.SECONDS);
    }



    /**
     * this class is used for testing asynctask
     */

    private class GetJokeFromGCETestTask extends GetJokeFromGCETask{

        CountDownLatch mSignal;

        public GetJokeFromGCETestTask(CountDownLatch mSignal) {
            this.mSignal = mSignal;
        }

        @Override
        protected void onPostExecute(String s) {
            // verify that the string is not null and not empty
            assertTrue (s != null);
            assertTrue (s.length() > 0);
            mSignal.countDown();
            super.onPostExecute(s);
        }
    }


}
