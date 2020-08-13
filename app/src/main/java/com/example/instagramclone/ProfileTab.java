package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies, edtProfileFavSport;
    private Button btnUpdateProfile;


    public ProfileTab() {
        // Required empty public constructor
    }


    public static ProfileTab newInstance() {

        return new ProfileTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edt_profile_name_profile);
        edtProfileBio = view.findViewById(R.id.edt_bio_profile);
        edtProfileFavSport = view.findViewById(R.id.edt_favorite_sport_profile);
        edtProfileHobbies = view.findViewById(R.id.edt_hobbies_profile);
        edtProfileProfession = view.findViewById(R.id.edt_profession_profile);
        btnUpdateProfile = view.findViewById(R.id.btn_update_info_profile);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        getDataFromServer(parseUser);

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profile_name", edtProfileName.getText().toString());
                parseUser.put("profile_bio", edtProfileBio.getText().toString());
                parseUser.put("profile_profession", edtProfileProfession.getText().toString());
                parseUser.put("profile_hobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profile_fav_sports", edtProfileFavSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null)  {
                            Toast.makeText(getContext(), "Profile is updated successfully", Toast.LENGTH_SHORT).show();
                        }   else    {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

    private void getDataFromServer(ParseUser user) {

        if (user.get("profile_name") == null)       {
            edtProfileName.setText("");
        }   else    {
            edtProfileName.setText(user.get("profile_name") + "");
        }

        if (user.get("profile_bio") == null)    {
            edtProfileBio.setText("");
        }   else    {
            edtProfileBio.setText(user.get("profile_bio") + "");
        }

        if (user.get("profile_profession") == null) {
            edtProfileProfession.setText("");
        }   else    {
            edtProfileProfession.setText(user.get("profile_profession") + "");
        }

        if (user.get("profile_hobbies") == null)    {
            edtProfileHobbies.setText("");
        }   else    {
            edtProfileHobbies.setText(user.get("profile_hobbies") + "");
        }

        if (user.get("profile_fav_sports") == null) {
            edtProfileFavSport.setText("");
        }   else    {
            edtProfileFavSport.setText(user.get("profile_fav_sports") + "");
        }

    }
}