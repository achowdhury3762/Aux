package nyc.c4q.ashiquechowdhury.auxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class LoginFragment extends Fragment {
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageView logoIv = (ImageView) view.findViewById(R.id.aux_logo);
        Glide.with(getContext()).load(R.drawable.lasso4).into(logoIv);

        CardView auxSignInButton = (CardView) view.findViewById(R.id.aux_signin_button);
        CardView googleSignInButton = (CardView) view.findViewById(R.id.google_signin_button);
        usernameEditText = (EditText) view.findViewById(R.id.username_login_edittext);
        passwordEditText = (EditText) view.findViewById(R.id.password_login_edittext);


        auxSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = getUsernameText();
                String enteredPassword = getPasswordText();

                handleLogin(enteredUsername, enteredPassword);
            }
        });


        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Handle Google Sign In", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUsernameText() {
        return usernameEditText.getText().toString();
    }

    private String getPasswordText() {
        return passwordEditText.getText().toString();
    }

    private void handleLogin(String enteredUsername, String enteredPassword) {
        if (enteredUsername.isEmpty()) {
            Toast.makeText(getActivity(), "Enter a username", Toast.LENGTH_SHORT).show();
        } else if (enteredPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Enter a password", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), JoinRoomActivity.class);
            startActivity(intent);
        }
    }
}
