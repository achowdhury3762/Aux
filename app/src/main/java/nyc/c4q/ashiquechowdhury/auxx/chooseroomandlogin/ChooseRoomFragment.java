package nyc.c4q.ashiquechowdhury.auxx.chooseroomandlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.joinandcreate.JoinRoomActivity;
import nyc.c4q.ashiquechowdhury.auxx.joinandcreate.PlaylistActivity;
import nyc.c4q.ashiquechowdhury.auxx.util.CreateRoomDialog;
import nyc.c4q.ashiquechowdhury.auxx.util.JoinRoomDialog;
import nyc.c4q.ashiquechowdhury.auxx.util.SingleLineTextView;

public class ChooseRoomFragment extends Fragment implements CreateRoomDialog.SubmitRoomNameListener, JoinRoomDialog.SubmitRoomNameListener {
    public static final String ROOMNAMEKEY = "nyc.c4q.ChooseRoomFragment.ROOMNAMEKEY";
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private CardView createRoomButton;
    private CardView joinRoomButton;
    private CardView viewProfileButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_room, container, false);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        createRoomButton = (CardView) view.findViewById(R.id.create_room_button);
        joinRoomButton = (CardView) view.findViewById(R.id.join_room_button);
        viewProfileButton = (CardView) view.findViewById(R.id.view_profile_button);
        SingleLineTextView singleLineTV = (SingleLineTextView) view.findViewById(R.id.aux_custom_tv);
        Typeface skinnyMarkerFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/skinny_marker.ttf");
        singleLineTV.setTypeface(skinnyMarkerFont);
        joinRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startJoinRoomPrompt();
            }
        });

        createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateRoomPrompt();
            }
        });
        createRoomButton.setBackgroundResource(R.color.background);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    private void startCreateRoomPrompt() {
        CreateRoomDialog createRoomDialog = new CreateRoomDialog();
        createRoomDialog.setSubmitListener(this);
        createRoomDialog.show(getFragmentManager(), "CreateRoomDialog");
    }

    private void startJoinRoomPrompt() {
        JoinRoomDialog joinRoomDialog = new JoinRoomDialog();
        joinRoomDialog.setSubmitListener(this);
        joinRoomDialog.show(getFragmentManager(), "Join Room Dialog");
    }

    @Override
    public void onSubmitRoomName(final String roomNameInput) {
        if (roomNameInput.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter a Valid Room Name", Toast.LENGTH_LONG).show();
            return;
        }
        else
            disableButtonsAndDisplayProgressBar();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(roomNameInput).hasChildren()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Room Already Exists", Toast.LENGTH_LONG).show();
                    enableButtonsAndRemoveProgressBar();
                } else {
                    reference.child(roomNameInput).push().setValue("First Empty Value");
                    Intent intent = new Intent(getActivity(), PlaylistActivity.class);
                    intent.putExtra(ROOMNAMEKEY, roomNameInput);
                    startActivity(intent);
                    enableButtonsAndRemoveProgressBar();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                enableButtonsAndRemoveProgressBar();
            }
        });
    }

    @Override
    public void onSubmitRoomName(final String roomNameInput, final String password) {
        if (password.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Password Not Entered", Toast.LENGTH_LONG).show();
            return;
        }
        else if(roomNameInput.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter a Valid Room", Toast.LENGTH_LONG).show();
            return;
        }
        else
            disableButtonsAndDisplayProgressBar();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(roomNameInput).hasChildren()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Room Already Exists", Toast.LENGTH_LONG).show();
                    enableButtonsAndRemoveProgressBar();
                } else {
                    reference.child(roomNameInput).child("password").setValue(password);
                    Intent intent = new Intent(getActivity(), PlaylistActivity.class);
                    intent.putExtra(ROOMNAMEKEY, roomNameInput);
                    startActivity(intent);
                    enableButtonsAndRemoveProgressBar();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                enableButtonsAndRemoveProgressBar();
            }
        });
    }

    @Override
    public void onJoinRoomPrompt(final String roomNameInput) {
        if(roomNameInput.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter a Valid Room", Toast.LENGTH_LONG).show();
            return;
        }
        else
            disableButtonsAndDisplayProgressBar();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(roomNameInput).hasChildren()) {
                    if (dataSnapshot.child(roomNameInput).child("password").exists()) {
                        Toast.makeText(getActivity().getApplicationContext(), "This is a private room", Toast.LENGTH_LONG).show();
                        enableButtonsAndRemoveProgressBar();
                        return;
                    }
                    Intent intent = new Intent(getActivity(), JoinRoomActivity.class);
                    intent.putExtra(ROOMNAMEKEY, roomNameInput);
                    startActivity(intent);
                    enableButtonsAndRemoveProgressBar();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Room Does Not Exists", Toast.LENGTH_LONG).show();
                    enableButtonsAndRemoveProgressBar();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                enableButtonsAndRemoveProgressBar();
            }
        });
    }

    @Override
    public void onJoinRoomPrompt(final String roomNameInput, final String passwordInput) {
        if (passwordInput.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Password Not Entered", Toast.LENGTH_LONG).show();
            enableButtonsAndRemoveProgressBar();
            return;
        }
        else
            disableButtonsAndDisplayProgressBar();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(roomNameInput).hasChildren()) {
                    if (passwordInput.equals(dataSnapshot.child(roomNameInput).child("password").getValue())) {
                        Intent intent = new Intent(getActivity(), JoinRoomActivity.class);
                        intent.putExtra(ROOMNAMEKEY, roomNameInput);
                        startActivity(intent);
                        enableButtonsAndRemoveProgressBar();
                    } else
                        Toast.makeText(getActivity().getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                    enableButtonsAndRemoveProgressBar();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Room Does Not Exists", Toast.LENGTH_LONG).show();
                    enableButtonsAndRemoveProgressBar();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                enableButtonsAndRemoveProgressBar();
            }
        });
    }

    private void disableButtonsAndDisplayProgressBar() {
        progressDialog.show();
        progressDialog.setCancelable(false);
        createRoomButton.setEnabled(false);
        joinRoomButton.setEnabled(false);
        viewProfileButton.setEnabled(false);
    }

    private void enableButtonsAndRemoveProgressBar() {
        progressDialog.dismiss();
        createRoomButton.setEnabled(true);
        joinRoomButton.setEnabled(true);
        viewProfileButton.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}