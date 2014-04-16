package com.example.cs4720;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class WebServiceTask extends AsyncTask<String, Void, JSONObject> {

	@Override
	protected JSONObject doInBackground(String... params) {
		String url = params[0];
		
		InputStream is = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			
			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			for (int i = 1; i < params.length-1; i+=2)
			{
				post.add(new BasicNameValuePair(params[i], params[i+1]));
			}
			
			httppost.setEntity(new UrlEncodedFormEntity(post, "UTF-8"));
			
			HttpResponse response = httpClient.execute(httppost); 
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                    sb.append(line + "\n");
            }
            is.close();

            String result=sb.toString();
            
			if (result.length() > 0)
			{
				return new JSONObject(result);
			}
			else
			{
				return null;
			}
		} catch (Exception e)
		{
			Log.e("ERROR CALLING WEB SERVICE", e.toString());
		}
		
		return null;
	}
}
