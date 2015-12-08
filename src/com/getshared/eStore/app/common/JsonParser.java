package com.getshared.eStore.app.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    final String TAG = "JsonParser.java";
    final String affiliateId = "57185";

    final String affiliateToken = "3a268fc7a003b88900c676b9ba4199";


    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONObject getJSONFromUrl(String url) throws URISyntaxException {

        // make HTTP request
        try {
            URI uri = new URI(url);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(uri);
            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader("Snapdeal-Affiliate-Id", affiliateId);
            httpGet.addHeader("Snapdeal-Token-Id", affiliateToken);


            HttpResponse httpResponse = httpClient.execute(httpGet);           
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

        } catch (Exception e) {
          
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
           
        }

        // return JSON String
        return jObj;
    }
}
