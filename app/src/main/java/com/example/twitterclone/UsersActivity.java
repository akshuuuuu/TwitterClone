package com.example.twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    ArrayList<String > tUsers;
    ArrayAdapter adapter;
    String followedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        listView= findViewById(R.id.usersList);
        tUsers= new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,tUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);

        ParseQuery<ParseUser> query=ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null){

                    if(users.size()>0){
                        for (ParseUser user: users){
                            tUsers.add(user.getUsername());
                        }
                        listView.setAdapter(adapter);
                       // listView.setVisibility(View.VISIBLE);
                        for(String twitterUser: tUsers){
                            if(ParseUser.getCurrentUser().getList("fanOf") != null) {

                                if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser))
                                    followedUser = followedUser+twitterUser +"";
                                    listView.setItemChecked(tUsers.indexOf(twitterUser), true);
                                    FancyToast.makeText(UsersActivity.this,ParseUser.getCurrentUser().getUsername()
                                    + " is following " + followedUser, Toast.LENGTH_LONG,FancyToast.INFO,true).show();

                            }

                        }

                    }

                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.logoutUserItem:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent intent= new Intent(UsersActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
            case R.id.send:
                Intent intent = new Intent(UsersActivity.this,SendTweetActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        CheckedTextView checkedTextView= (CheckedTextView) view;

        if(checkedTextView.isChecked()){
            FancyToast.makeText(UsersActivity.this,tUsers.get(position)+"is now followed",
                    Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
            ParseUser.getCurrentUser().add("fanOf",tUsers.get(position));
        }
        else{
            FancyToast.makeText(UsersActivity.this,tUsers.get(position)+"is now unfollowed",
                    Toast.LENGTH_SHORT,FancyToast.INFO,true).show();

            ParseUser.getCurrentUser().getList("fanOf").remove(tUsers.get(position));
            // after removing user
            List currentUserFanOfList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentUserFanOfList);
        }

        //for updating backend
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    FancyToast.makeText(UsersActivity.this,"Saved",
                            Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                }
            }
        });


    }
}