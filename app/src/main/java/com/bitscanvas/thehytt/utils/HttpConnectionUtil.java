package com.bitscanvas.thehytt.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by THEHYTT on 20/09/15.
 */
public class HttpConnectionUtil {

    public static final String TAG = "HttpConnectionUtil";


    public void executeInBackGround(String uri,Object request){
        DownloadTask downloadTask = new DownloadTask();
        String req = toRequestString(request);
        downloadTask.doInBackground(uri,req);
    }

    public String execute(String uri,Object request){
        String res = null;
        try {
            String req = toRequestString(request);
            res =loadFromNetwork(uri, req);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Implementation of AsyncTask, to fetch the data in the background away from
     * the UI thread.
     */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        private String response;

        @Override
        protected String doInBackground(String... reqDtls) {
            try {
                return loadFromNetwork(reqDtls[0], reqDtls[1]);
            } catch (IOException e) {
                return "";
            }
        }

        /**
         * Once the doInBackground method is complete this will be called
         * Based on the response take appropriate action
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                response = result;

            } catch (Exception e) {
                //todo log
                e.printStackTrace();
            }
        }
    }

    public String loadFromNetwork(String urlStr,String request) throws IOException{
        String response = "";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setChunkedStreamingMode(0);
            byte[] outputInBytes = request.getBytes("UTF-8");
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(outputInBytes);
            out.close();
            conn.connect();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = readStream(in);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return response;
    }

    private String readStream(InputStream stream){
        StringBuilder resp = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = "";
            while((line = reader.readLine()) != null){
                resp.append(line);
            }
        }catch(Exception ex){
            Log.e(TAG, "Exception in readStream" + ex.getMessage());
        }
        return resp.toString();
    }

    private String toRequestString(Object object){
        String request = null;
        try {
             request = JSONConvertor.objectToJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
