package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class UserTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    private TextView txtLoadingData;
    public UserTab() {
        // Required empty public constructor
    }


    public static UserTab newInstance(String param1, String param2) {
       return new UserTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_tab, container, false);
        txtLoadingData = view.findViewById(R.id.textView_user);

        listView = view.findViewById(R.id.list_view_user);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setOnItemClickListener(UserTab.this);
        listView.setOnItemLongClickListener(UserTab.this);
        listView.setVisibility(View.INVISIBLE);
        populateListView();
        return view;
    }

    private void populateListView() {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null)  {
                    if (objects.size() > 0) {
                        for (ParseUser user : objects)  {
                            arrayList.add(user.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);
                        txtLoadingData.animate().alpha(0).setDuration(1000);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      Intent intent = new Intent(getContext(), UserPostsActivity.class);
      intent.putExtra("username", arrayList.get(position));
      startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null && e == null)  {
                    final PrettyDialog pDialog = new PrettyDialog(getContext());
                    try {
                        pDialog
                                .setTitle("User Info")
                                .setMessage(String.valueOf(parseQuery.get("profe_bio")) +
                                            parseQuery.get("profile_profession") +
                                            parseQuery.get("profile_hobbies") +
                                            parseQuery.get("profile_fav_sports"))
                                .addButton(
                                        "Cancel",
                                        R.color.pdlg_color_white,
                                        R.color.pdlg_color_red,
                                        new PrettyDialogCallback() {
                                            @Override
                                            public void onClick() {
                                                pDialog.dismiss();
                                            }
                                        }
                                )
                                .show();
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        return false;
    }
}