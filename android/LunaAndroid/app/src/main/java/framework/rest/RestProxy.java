package framework.rest;

import android.os.AsyncTask;

import org.luaj.vm2.LuaTable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import framework.error.LunaError;
import framework.syntax.data.LunaHashMapAdapter;
import framework.syntax.function.LunaFunctionAdapter;

/**
 * (PT) Realiza uma requisição REST.
 * (EN) Makes a REST request.
 * Created by macbookair on 05/05/17.
 */

public class RestProxy extends AsyncTask<LunaHashMapAdapter, Integer, RestResponseWrapper> {

    private RestResponseWrapper responseWrapper;
    private LunaFunctionAdapter callback;

    public RestResponseWrapper getResponse(){
        return this.responseWrapper;
    }

    @Override
    protected void onPostExecute(RestResponseWrapper response){
        this.callback.execute();

    }

    @Override
    protected RestResponseWrapper doInBackground(LunaHashMapAdapter... properties) {
        LunaHashMapAdapter restProperties = properties[0];
        if( restProperties != null && restProperties.size() != 0){

            if( (restProperties.containsKey("url") != false &&
                (restProperties.get("url") instanceof  String) ) &&
                (restProperties.containsKey("method") != false &&
                (restProperties.get("method") instanceof  String) ) &&
                (restProperties.containsKey("callback") != false &&
                (restProperties.get("callback") instanceof LunaFunctionAdapter) )){

                String url = (String)restProperties.get("url");
                String method = (String)restProperties.get("method");
                LunaFunctionAdapter callback = (LunaFunctionAdapter) restProperties.get("callback");
                LunaHashMapAdapter params = (LunaHashMapAdapter)restProperties.get("params");
                this.callback = callback;

                HttpURLConnection httpConnection = null;
                //ProviderInstaller.installIfNeeded(this);
                if(!method.equals("GET") && !method.equals("POST") &&
                   !method.equals("DELETE") &&!method.equals("PUT")){
                    LunaError.dispatch(3);
                }

                try {
                    URL urlRequest = new URL(url);
                    httpConnection = (HttpURLConnection) urlRequest.openConnection();
                    HttpsURLConnection.setDefaultSSLSocketFactory(new NoSSLv3Factory());
                    httpConnection.setRequestMethod(method);
                    httpConnection.setRequestProperty("User-Agent", "luna");
                    httpConnection.setRequestProperty("Content-Type", "application/json");
                    httpConnection.setRequestProperty("Accept", "application/json");
                    httpConnection.setRequestProperty( "Accept-Encoding", "" );
                    if(method.equals("POST") || method.equals("PUT")) {
                        httpConnection.setDoOutput(true);
                        String json = LunaHashMapAdapterJsonConverter.toJson(params).toString();

                        OutputStream os = httpConnection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(json);
                        writer.flush();
                        writer.close();
                        os.close();
                    }
                    int responseCode = httpConnection.getResponseCode();
                    String response = readInputStreamToString(httpConnection);
                    responseWrapper = new RestResponseWrapper(response, responseCode);

                }catch (MalformedURLException e) {
                    LunaError.dispatch(4);
                }
                catch (Exception e) {
                    responseWrapper = new RestResponseWrapper(e.getMessage(), -1);
                } finally {
                    if(httpConnection != null) {
                        httpConnection.disconnect();
                    }

                    return responseWrapper;
                }
            }
        }else {

            LunaError.dispatch(2);
        }
        return null;
    }

    private String readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch (Exception e) {
            LunaError.dispatch(5);
            result = null;
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    //Log.i(TAG, "Error closing InputStream");
                }
            }
        }

        return result;
    }


}
