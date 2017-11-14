package com.mobile.postexample;

import android.os.AsyncTask;

import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.MalformedURLException;

import com.mobile.postexample.ConnectTask.Param;
/**
 * Created by MAURICE on 29/03/2017.
 */

public class ConnectTask extends AsyncTask<Param, Void, String> {

    private String resource;
    private TextView resultView;

    public ConnectTask(String resource, TextView resultView) {
        this.resource = resource;
        this.resultView = resultView;
    }

    @Override
    protected String doInBackground(Param ... params){
        String queryString = makeQueryString(params);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader reader = null;

        try{

            /**
             *Opening and writing to an Http based connection through an Output
             *stream. The writing is done using the PrintWriter so as to write
             *strings as in the request line and not binary data as would be the case
             *if a FileOutputStream is used (fos.write(queryString)
             */
            URL url = new URL(resource);
            URLConnection urlConnection = url.openConnection();
            ((HttpURLConnection)urlConnection).setRequestMethod("POST");
            outputStream = urlConnection.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write(queryString);
            writer.flush();
            writer.close();

            /**
             * Upon writing and sending an Http Request, a response is provided.
             * This is then brings up what is known as an InputStream. To reduce the number
             * of io operations performed by the task of receiving the response, the InputStream
             * is then passed to the BufferedReader. Again, the Buffered reader is meant to help
             * read data in the human readable encoding format UTF, instead of Binary data
             */
            inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String result = reader.readLine();

            return result;

        }catch(MalformedURLException e){
           e.printStackTrace();
        }catch(IOException e){
           e.printStackTrace();
        }finally{
            try {
                if (inputStream != null)
                    inputStream.close();
                if (reader != null)
                    reader.close();
            }catch(Exception e){}
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result){
        resultView.setText(result);
    }
    private String makeQueryString(Param... params) {
        StringBuilder queryString = new StringBuilder();
        boolean first = true;
        for (Param p : params) {
            if (first) {
                first = false;
            } else {
                queryString.append("&");
            }
            queryString.append(p.name + "=" + p.value);
        }
        return queryString.toString();
    }

    public static class Param {
        private String name;
        private String value;

        public Param(String name, String value) {
            this.name = name;
            this.value = value;
        }

    }
}
