package vntd.demo.gamehdh.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vntd.demo.gamehdh.R;
import vntd.demo.gamehdh.async.AsyncChangePass;

public class ChangePassActivity extends Activity implements View.OnClickListener{

    EditText edtUsername, edtOldPass, edtNewPass, edtConfirm;
    Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        AnhXa();

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }



    void AnhXa(){
        edtUsername = findViewById(R.id.edtUsername);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtOldPass = findViewById(R.id.edtOldPass);
        edtConfirm = findViewById(R.id.edtConfirmNewPass);

        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            String username = edtUsername.getText().toString();
            String oldPass = edtOldPass.getText().toString();
            String newPass = edtNewPass.getText().toString();
            String confirmPass = edtConfirm.getText().toString();

            if (username.equals("") || oldPass.equals("") || newPass.equals("") || confirmPass.equals("")) {
                Toast.makeText(this, "Please input full required infomation", Toast.LENGTH_SHORT).show();
            } else if (oldPass.equals(newPass)) {
                Toast.makeText(this, "Please input new password", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Please check password and comfirm password again", Toast.LENGTH_SHORT).show();
            } else {
                oldPass = LoginActivity.convertPassMd5(oldPass);
                newPass = LoginActivity.convertPassMd5(oldPass);

                new AsyncChangePass(this).execute(username, oldPass, newPass);
            }
        }else if (v == btnCancel){
            finish();
        }
    }
}
