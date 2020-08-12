package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edt_email_login);
        edtPassword = findViewById(R.id.edt_password_login);
        btnLogin = findViewById(R.id.btn_login_login);
        btnSignUp = findViewById(R.id.btn_signup_login);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btn_login_login :
                ParseUser.logInInBackground(edtEmail.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null)  {
                            if(user != null)    {
                                Toast.makeText(LoginActivity.this, ParseUser.getCurrentUser().getUsername() + " is logged in successfully", Toast.LENGTH_SHORT).show();
                            }   else    {
                                Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }   else    {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;

            case R.id.btn_signup_login :
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}