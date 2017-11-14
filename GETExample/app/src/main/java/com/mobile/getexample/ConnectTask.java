package com.mobile.getexample;

import android.os.AsyncTask;
import android.widget.TextView;

import com.mobile.getexample.ConnectTask.Param;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;

import android.widget.TextView;

/**
 * Created by MAURICE on 29/03/2017.
 */

public class ConnectTask extends AsyncTask<Param, Void, String> {

    String resource;
    TextView resultText;

    public ConnectTask(String resource, TextView resultText){
        this.resource = resource;
        this.resultText = resultText;
    }
    @Override
    protected String doInBackground(Param ... params){
        String queryString = makeQueryString(params);
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(resource + "?" + queryString);
            URLConnection urlConnection = url.openConnection();
            ((HttpURLConnection)urlConnection).setRequestMethod("GET");
            inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String result = reader.readLine();

            return result;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                // Sorry guys
            }
        }
      return null;
    }

    public String makeQueryString(Param ... params){
        StringBuilder queryString = new StringBuilder();
        boolean first = true;
        for(Param p: params){
           if(first)
               first = false;
            else
               queryString.append("&");
            queryString.append(p.name+"="+p.value);
        }
        return queryString.toString();
    }

    @Override
    protected void onPostExecute(String result){
        resultText.setText(result);
    }

    public static class Param{
        String name;
        String value;
        public Param(String name, String value){
            this.name = name;
            this.value = value;
        }
    }
}
