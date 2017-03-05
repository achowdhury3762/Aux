package nyc.c4q.ashiquechowdhury.auxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by SACC on 3/4/17.
 */

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView logoIv = (ImageView) findViewById(R.id.aux_logo);
        Glide.with(getApplicationContext()).load(R.drawable.lasso4).into(logoIv);

        CardView auxSignInButton = (CardView) findViewById(R.id.aux_signin_button);
        CardView googleSignInButton = (CardView) findViewById(R.id.google_signin_button);
        usernameEditText = (EditText) findViewById(R.id.username_login_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_login_edittext);


        auxSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Add Intent to Next Activity", Toast.LENGTH_SHORT).show();

                String enteredUsername = getUsernameText();
                String enteredPassword = getPasswordText();

                handleLogin(enteredUsername, enteredPassword);
            }
        });


        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Hangle Google Sign In", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleLogin(String enteredUsername, String enteredPassword) {
        if (enteredUsername.isEmpty()) {
            Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
        } else if (enteredPassword.isEmpty()) {
            Toast.makeText(this, "Enter a password", Toast.LENGTH_SHORT).show();
        } else {
//            Intent intent = new Intent(getApplicationContext(), JoinRoomActivity.class);
//            startActivity(intent);
        }
    }

    private String getUsernameText() {
        return usernameEditText.getText().toString();
    }

    private String getPasswordText() {
        return passwordEditText.getText().toString();
    }

    public void onClickSignIn(View view){
        Intent intent = new Intent(this, JoinRoomActivity.class);
        startActivity(intent);
    }
}
