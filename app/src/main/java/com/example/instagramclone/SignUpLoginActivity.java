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
import com.parse.SignUpCallback;

public class SignUpLoginActivity extends AppCompatActivity {

    private EditText edtUsernameSignup, edtPasswordSignup, edtUsernameLogin, edtPasswordLogin;
    private Button btnSignin, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        edtUsernameSignup = findViewById(R.id.edt_username_signup);
        edtPasswordSignup = findViewById(R.id.edt_password_signup);
        edtUsernameLogin = findViewById(R.id.edt_username_login);
        edtPasswordLogin = findViewById(R.id.edt_password_login);
        btnSignin = findViewById(R.id.btn_signup_user);
        btnLogin = findViewById(R.id.btn_login_user);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser parseUser = new ParseUser();
                parseUser.setUsername(edtUsernameSignup.getText().toString());
                parseUser.setPassword(edtPasswordSignup.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null)   {
                            Toast.makeText(SignUpLoginActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        }   else    {
                            Toast.makeText(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(edtUsernameLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null)  {
                            if(user != null)    {
                                Toast.makeText(SignUpLoginActivity.this, edtUsernameLogin.getText().toString() + " User logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpLoginActivity.this, WelcomeActivity.class);
                                startActivity(intent);

                            }   else    {
                                Toast.makeText(SignUpLoginActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                            }
                        }   else    {
                            Toast.makeText(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}