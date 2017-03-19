package nyc.c4q.ashiquechowdhury.auxx.chooseroomandlogin;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.util.SingleLineTextView;

public class LoginFragment extends Fragment {
    private EditText usernameEditText;
    private EditText passwordEditText;
    String enteredUsername;
    String enteredPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_improved, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageView logoIv = (ImageView) view.findViewById(R.id.aux_logo);
        CardView auxSignInButton = (CardView) view.findViewById(R.id.aux_signin_button);
        CardView googleSignInButton = (CardView) view.findViewById(R.id.google_signin_button);
        SingleLineTextView singleLineTextView = (SingleLineTextView) view.findViewById(R.id.aux_login_tv);
        final Typeface skinnyMarkerFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/skinny_marker.ttf");
        singleLineTextView.setTypeface(skinnyMarkerFont);
        usernameEditText = (EditText) view.findViewById(R.id.username_login_edittext);
        passwordEditText = (EditText) view.findViewById(R.id.password_login_edittext);

        passwordEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    enteredUsername = getUsernameText();
                    enteredPassword = getPasswordText();
                    handleLogin(enteredUsername, enteredPassword);
                    return true;
                }
                return false;
            }
        });

        auxSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredUsername = getUsernameText();
                enteredPassword = getPasswordText();
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
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new ChooseRoomFragment()).commit();
        }
    }
}
