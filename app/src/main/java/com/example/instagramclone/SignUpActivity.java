package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);

        edtEmail = findViewById(R.id.edt_email_signup);
        edtUsername = findViewById(R.id.edt_username_signup);
        edtPassword = findViewById(R.id.edt_password_signup);
        btnSignUp = findViewById(R.id.btn_signup_signup);
        btnLogin = findViewById(R.id.btn_login_signup);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

       if (ParseUser.getCurrentUser() != null)  ParseUser.logOut();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btn_signup_signup :
                final ParseUser user = new ParseUser();
                user.setEmail(edtEmail.getText().toString());
                user.setUsername(edtUsername.getText().toString());
                user.setPassword(edtPassword.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null)  {
                            Toast.makeText(SignUpActivity.this, edtUsername.getText().toString() + " signed up successfully!", Toast.LENGTH_SHORT).show();
                        }   else    {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;

            case R.id.btn_login_signup :
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}