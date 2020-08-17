package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserPostsActivity extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        linearLayout = findViewById(R.id.linear_layout);

        Intent receivedIntentObject = getIntent();
        final String receiveruserName = receivedIntentObject.getStringExtra("username");
        Toast.makeText(this, "received " + receiveruserName, Toast.LENGTH_SHORT).show();

        setTitle(receiveruserName + "'s posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("photo");
        parseQuery.whereEqualTo("username", receiveruserName);
        parseQuery.orderByDescending("createdAt");
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() > 0 && e == null) {
                    for(ParseObject posts : objects)   {
                        final TextView imageDes = new TextView(UserPostsActivity.this);
                        imageDes.setText(posts.get("image_desc") + "");
                        ParseFile postPicture = (ParseFile) posts.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e == null)   {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(UserPostsActivity.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5, 5, 5, 5);
                                    postImageView.setLayoutParams(params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5, 5, 5, 5);
                                    imageDes.setLayoutParams(des_params);
                                    imageDes.setGravity(Gravity.CENTER);
                                    imageDes.setBackgroundColor(Color.BLUE);
                                    imageDes.setTextColor(Color.WHITE);
                                    imageDes.setTextSize(30f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(imageDes);
                                }
                            }
                        });
                    }
                }   else    {
                    Toast.makeText(UserPostsActivity.this, "No posts for " + receiveruserName, Toast.LENGTH_SHORT).show();
                    finish();
                }
                dialog.dismiss();
            }
        });
    }
}