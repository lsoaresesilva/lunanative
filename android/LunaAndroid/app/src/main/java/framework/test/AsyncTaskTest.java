package framework.test;

import android.os.AsyncTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import framework.rest.RestResponseWrapper;

/**
 * This class offer support to test AsyncTask with jUnit.
 * It will block the test method until an AsyncTask returns (onPostComplete). This return can be tested with jUnit asserts.
 * Usage:
 *
 *
 * public void willDoGETRequest(){
 *  ...
 *  myAsyncTaskInstace.execute( parameters );
 *  AsyncTaskTest.build(myAsyncTask)
 *               .run(new AsyncTest()
 *               {
                        @Override
                        public void test() {
                            Assert.assertEquals(200, myAsyncTask.getResponse().getResponseCode());
                        }
                }
 *  );
 * }
 * Created by Leonardo Soares e Silva on 07/05/17.
 */

public class AsyncTaskTest {

    private class Method<T>{

    }

    private CountDownLatch signal = null;
    private AsyncTask task = null;

    private AsyncTaskTest(){
        signal = new CountDownLatch(1);
    }

    public static AsyncTaskTest build(AsyncTask task){
        AsyncTaskTest instance = new AsyncTaskTest();
        if( task == null ){
            throw new IllegalArgumentException("Invalid task, must not be null.");
        }

        instance.task = task;
        return instance;
    }

    public void run(final AsyncTest test) throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            synchronized public void run() {
                while (true) {
                    if (task.getStatus() == AsyncTask.Status.FINISHED) {
                        try {
                            RestResponseWrapper r = (RestResponseWrapper) task.get();
                            test.test(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } finally{
                            signal.countDown();
                            break;
                        }


                    }
                }
            }
        };
        thread.start();
        signal.await();

    }

}
