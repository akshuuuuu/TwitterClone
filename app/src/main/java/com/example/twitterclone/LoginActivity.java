package com.example.twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText enterUser,enterPass;
    Button lognewButton,signewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        enterUser = findViewById(R.id.enterUser);
        enterPass = findViewById(R.id.enterPass);
        lognewButton= findViewById(R.id.lognewButton);
        signewButton = findViewById(R.id.signnewButton);

        lognewButton.setOnClickListener(this);
        signewButton.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view)
    { switch (view.getId())
    {
        case R.id.lognewButton:
            ParseUser.logInInBackground(enterUser.getText().toString(),
                    enterPass.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user != null && e==null){
                                Toast.makeText(LoginActivity.this,
                                        user.getUsername() +
                                                " is logged in successfully",
                                        Toast.LENGTH_SHORT).show();
                                transitionToUserActivity();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "There was an error"+e.getMessage(),
                                        Toast.LENGTH_SHORT).show();}
                        }
                    });

            break;
        case R.id.signnewButton:
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

            break;

    }

    }
    private void transitionToUserActivity(){
        Intent intent= new Intent(LoginActivity.this,UsersActivity.class);
        startActivity(intent);
        finish();
    }


}


