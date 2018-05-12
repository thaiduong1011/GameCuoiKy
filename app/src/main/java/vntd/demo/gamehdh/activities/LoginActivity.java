package vntd.demo.gamehdh.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import vntd.demo.gamehdh.Config;
import vntd.demo.gamehdh.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    public static int CONNECTION_TIMEOUT=10000;
    public static int READ_TIMEOUT=15000;

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtSignUp;
    CheckBox checkBoxRememberUser;

    //luu trang thai dang nhap
    // SharedPreferences sharedPreferences;

    //xóa mọi lưu trữ trước đó
    //editor.clear();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Anhxa();

        txtSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    void Anhxa(){
        // sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.link_signup);
        checkBoxRememberUser = findViewById(R.id.checkboxRememberUser);

        //getDataFromSharedPre();
    }

    @Override
    public void onClick(View view) {
        if (view == txtSignUp){
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }else if (view == btnLogin){
            if(edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")){
                Toast.makeText(this, "Please input full required infomation", Toast.LENGTH_SHORT).show();
                return;
            }
            checkLogin();
        }
    }

    public void checkLogin() {

        // Get text from email and passord field
        final String username = edtUsername.getText().toString();
        final String password = convertPassMd5(edtPassword.getText().toString());

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(username,password);

    }

    /*void getDataFromSharedPre(){
        edtUsername.setText(sharedPreferences.getString("user", ""));
        edtPassword.setText(sharedPreferences.getString("pass", ""));
        checkBoxRememberUser.setChecked(sharedPreferences.getBoolean("checkbox", false));
    }*/

    private class AsyncLogin extends AsyncTask<String, String, String>
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
                String url_name = Config.IPADDRESS + "user/CheckLogin";

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("user_name", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();
                url_name += "?" + query;
                Log.d("AA", url_name);

                url = new URL(url_name);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setDoOutput(true);

                int response_code = conn.getResponseCode();
                Log.d("AA", response_code + "");

                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return(result.toString());
                } else {
                    return("unsuccessful");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("AA","1 " +e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("AA","2 " +e.getMessage());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            Log.d("AA", result + " aa");

            if(!result.equals("-1") && !result.equals("") && !result.equals("null"))
            {
//                //nếu check thì lưu giá trị
//                if (checkBoxRememberUser.isChecked()){
//                    SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
//                    editor.putInt("id", Integer.parseInt(result));
//                    editor.putString("user", edtUsername.getText().toString());
//                    editor.putString("pass", edtPassword.getText().toString());
//                    editor.putBoolean("checkbox", true);
//                    editor.commit();
//                    Log.d("AAA", edtUsername.getText().toString() + " " + edtPassword.getText().toString());
//                }else{
//                    SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
//                    editor.remove("id");
//                    editor.remove("user");
//                    editor.remove("pass");
//                    editor.remove("checkbox");
//                    editor.commit();
//                }

                //finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("user_id", Integer.parseInt(result));
                intent.putExtra("user_name", edtUsername.getText().toString());
                startActivity(intent);
                finish();

            } else if (result.equals("-1")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equals("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong. Connection Problem." + result, Toast.LENGTH_LONG).show();

            }
        }
    }

    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }
}
