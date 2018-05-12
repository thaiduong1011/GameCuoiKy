package vntd.demo.gamehdh.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vntd.demo.gamehdh.Config;
import vntd.demo.gamehdh.R;

public class SignupActivity extends Activity implements View.OnClickListener {

    EditText edtUsername, edtPassword, edtConfirmPass, edtEmail;
    Button btnSignup;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Anhxa();

        txtLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    void Anhxa(){
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
        edtConfirmPass = findViewById(R.id.edtComfirmPass);
        btnSignup = findViewById(R.id.btnSignup);
        txtLogin = findViewById(R.id.link_login);
    }

    @Override
    public void onClick(View view) {
        if (view == txtLogin){
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(view == btnSignup){
            if(edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("") ||
                    edtEmail.getText().equals("") || edtConfirmPass.getText().equals("")){
                Toast.makeText(this, "Please input full required infomation", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!edtPassword.getText().toString().equals(edtConfirmPass.getText().toString())){
                Toast.makeText(this, "Please check password and comfirm password again", Toast.LENGTH_SHORT).show();
                return;
            }

            checkLogin();
        }
    }

    public void checkLogin() {

        // Get text from email and passord field
        final String username = edtUsername.getText().toString();
        final String password = LoginActivity.convertPassMd5(edtPassword.getText().toString());
        final String email = edtEmail.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AsyncSignup().execute(username, password, email);

    }

    private class AsyncSignup extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            URL url;
            HttpURLConnection conn = null;
            try {
                String url_name = Config.IPADDRESS + "user/InsertUser";

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_name", params[0])
                        .appendQueryParameter("password", params[1])
                        .appendQueryParameter("email", params[2]);
                String query = builder.build().getEncodedQuery();
                url_name += "?" + query;

                url = new URL(url_name);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");

                int response_code = conn.getResponseCode();
                // Check if successful connection made
                Log.d("AA", "Ko OK ne 2 " + response_code + url_name );

                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.d("AA", "OK ne 2 " + result.toString() + "-" + url_name);

                    return(result.toString());
                } else {
                    return("unsuccessful");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("AA", "error 1 => " + e.getMessage().toString());
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    // conn.disconnect();
                }
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("AA", "result = " + result);

            if(result.equals("1"))
            {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("username", edtUsername.getText().toString());
                startActivity(intent);
                finish();

            } else if (result.equals("3")){
                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "\n" + "Username already exists", Toast.LENGTH_LONG).show();

            } else if (result.equals("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong. Connection Problem." + result, Toast.LENGTH_LONG).show();
            }
        }
    }
}
