package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.SearchFragment;
import nyc.c4q.ashiquechowdhury.auxx.model.Item;
import nyc.c4q.ashiquechowdhury.auxx.model.Listener;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;
import nyc.c4q.ashiquechowdhury.auxx.util.SpotifyUtil;

public class PlaylistActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, Listener, Player.OperationCallback {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childListener;
    SlidingUpPanelLayout slidingPanel;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_container);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.playlist_maincontent_frame, new PlaylistFragment())
                .replace(R.id.playlist_panelcontent_frame, new MasterMusicPlayerControlsFragment())
                .commit();

        slidingPanel = (SlidingUpPanelLayout) findViewById(R.id.activity_searchandchoose_container);

        slidingPanel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset > 0.0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.playlist_panelcontent_frame, new CurrentSongInfoFragment()).commit();
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.playlist_panelcontent_frame, new CurrentSongInfoFragment()).commit();
                } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.playlist_panelcontent_frame, new MasterMusicPlayerControlsFragment()).commit();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        setBottomPanelHeight();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(SearchFragment.MUSIC_LIST);
        childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PlaylistTrack myTrack = dataSnapshot.getValue(PlaylistTrack.class);
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
    }

    private void setBottomPanelHeight() {
        final LinearLayout layout = (LinearLayout) findViewById(R.id.bottom_panel_height);
        final ViewTreeObserver observer = layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        slidingPanel.setPanelHeight(layout.getHeight());
                    }
                });
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Error error) {

    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {

    }

    @Override
    public void playSelectedTrack(String uri) {

    }

    @Override
    public void queueSelectedTrack(Item item) {

    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(SpotifyUtil.getInstance().spotifyPlayer);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                } else {
                    fragmentManager.popBackStack();
                }
            }
        });
    }
}