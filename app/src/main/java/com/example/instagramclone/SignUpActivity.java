package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    EditText boxerName, punchSpeed, punchPower, kickSpeed, kickpower;
    Button saveButton;
    TextView txtGetData;
    String allKickBoxers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);

        boxerName = findViewById(R.id.edt_boxer_name);
        punchSpeed = findViewById(R.id.edt_punch_speed);
        punchPower = findViewById(R.id.edt_punch_power);
        kickSpeed = findViewById(R.id.edt_kick_speed);
        kickpower = findViewById(R.id.edt_kick_power);
        txtGetData = findViewById(R.id.txt_get_data);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                       if(objects.size() > 0 && e == null)  {
                           for(ParseObject parseObject : objects)   {
                               allKickBoxers = allKickBoxers + parseObject.get("name") + "\n";
                           }
                           Toast.makeText(SignUpActivity.this, allKickBoxers, Toast.LENGTH_LONG).show();
                       }    else    {
                           Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                       }
                    }
                });
            }
        });
    }

//    public void helloWorldTapped(View view)  {
////        ParseObject boxer = new ParseObject("Boxer");
////        boxer.put("punch_speed", 200);
////        boxer.saveInBackground(new SaveCallback() {
////            @Override
////            public void done(ParseException e) {
////                if(e == null)   {
////                    Toast.makeText(SignUpActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
////                }   else    {
////                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
//        final ParseObject kickBoxer = new ParseObject("KickBoxer");
//        kickBoxer.put("name", "Ayush");
//        kickBoxer.put("punch_speed", 1000);
//        kickBoxer.put("punch_power", 2000);
//        kickBoxer.put("kick_speed", 3000);
//        kickBoxer.put("kick_power", 4000);
//        kickBoxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null)   {
//                    Toast.makeText(SignUpActivity.this, kickBoxer.get("name") + "is saved", Toast.LENGTH_SHORT).show();
//                }   else    {
//                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
    public void btnSaveClicked(View view)   {
        ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("name", boxerName.getText().toString());
        kickBoxer.put("punch_speed", Integer.parseInt(punchSpeed.getText().toString()));
        kickBoxer.put("punch_power", Integer.parseInt(punchPower.getText().toString()));
        kickBoxer.put("kick_speed", Integer.parseInt(kickSpeed.getText().toString()));
        kickBoxer.put("kick_power", Integer.parseInt(kickpower.getText().toString()));
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)   {
                    Toast.makeText(SignUpActivity.this, boxerName.getText().toString() + " is saved", Toast.LENGTH_SHORT).show();
                }
                else    {
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}