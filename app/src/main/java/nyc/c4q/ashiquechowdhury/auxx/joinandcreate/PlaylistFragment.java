package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import nyc.c4q.ashiquechowdhury.auxx.PlaylistAdapter;
import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.SearchFragment;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;
import nyc.c4q.ashiquechowdhury.auxx.util.SpotifyUtil;

import static android.R.id.message;

public class PlaylistFragment extends Fragment implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, Player.OperationCallback {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childListener;
    private RecyclerView recyclerView;
    private SpotifyUtil spotify;
    private static final String FRAGMENT_TAG = PlaylistFragment.class.getSimpleName();
    private FloatingActionButton floatingSearchBtn;
    private PlaylistAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        spotify = SpotifyUtil.getInstance();
        spotify.createPlayer(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingSearchBtn = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction
                        .replace(R.id.playlist_maincontent_frame, new SearchFragment())
                        .addToBackStack(FRAGMENT_TAG)
                        .commit();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(SearchFragment.MUSIC_LIST);
        childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PlaylistTrack myTrack = dataSnapshot.getValue(PlaylistTrack.class);
                myAdapter.add(myTrack);
                SongListHelper.songList.add(myTrack);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference.addChildEventListener(childListener);
        myAdapter = new PlaylistAdapter(getContext());
    }

    @Override
    public void onLoggedIn() {
        Log.d("SearchAndChooseActivity", "User logged in");

    }

    @Override
    public void onLoggedOut() {
        Log.d("SearchAndChooseActivity", "User logged out");

    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("SearchAndChooseActivity", "Login failed");

    }

    @Override
    public void onTemporaryError() {
        Log.d("SearchAndChooseActivity", "Temporary error occurred");

    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("SearchAndChooseActivity", "Received connection message: " + message);

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }


    @Override
    public void onPlaybackError(Error error) {
        Log.d("SearchAndChooseActivity", "Playback error received: " + error.name());
        switch (error) {
            default:
                break;
        }

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Error error) {

    }

}
