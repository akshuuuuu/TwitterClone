package com.example.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText user,pass;
    Button log,sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");
        user = findViewById(R.id.chooseUser);
        pass = findViewById(R.id.choosePass);

        sign = findViewById(R.id.signButton);
        log = findViewById(R.id.logButton);

        sign.setOnClickListener(this);
        log.setOnClickListener(this);
        if(ParseUser.getCurrentUser() != null)
        {
            ParseUser.getCurrentUser().logOut();
            transitionToUserActivity();
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.signButton:
               // final ParseUser appUser = new ParseUser();
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(user.getText().toString());
                appUser.setPassword(pass.getText().toString());

                final ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("Signing up " + user.getText().toString() );
                pd.show();

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e)
                    {
                        if(e==null){
                            Toast.makeText(MainActivity.this, appUser.getUsername()
                                    + " is signed up", Toast.LENGTH_SHORT).show();
                            transitionToUserActivity();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "There was an error"+e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();

                    }

                });
                break;
            case R.id.logButton:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }



    }
    private void transitionToUserActivity(){
        Intent intent= new Intent(MainActivity.this,UsersActivity.class);
        startActivity(intent);
        finish();
    }


}


