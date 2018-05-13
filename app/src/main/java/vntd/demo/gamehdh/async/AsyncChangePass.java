package vntd.demo.gamehdh.async;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vntd.demo.gamehdh.Config;
import vntd.demo.gamehdh.activities.LoginActivity;

public class AsyncChangePass extends AsyncTask<String, Void, String> {
    Context context;

    public AsyncChangePass(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "user/ChangePassword";

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("user_name", params[0])
                    .appendQueryParameter("old_password", params[1])
                    .appendQueryParameter("new_password", params[2]);
            String query = builder.build().getEncodedQuery();
            url_name += "?" + query;
            Log.d("AA", url_name);

            url = new URL(url_name);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            int response_code = conn.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return (result.toString());
            } else {
                return ("unsuccessful");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("AA", "1 " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("AA", "2 " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("AA", result + " aa");
        //this method will be running on UI thread
        if (result.equals("1")){
            Toast.makeText(context, "Account successfully created", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }else {
            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }
}
