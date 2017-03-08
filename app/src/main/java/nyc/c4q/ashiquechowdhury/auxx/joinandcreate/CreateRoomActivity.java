package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.chooseroomandlogin.AuthenticationToken;
import nyc.c4q.ashiquechowdhury.auxx.chooseroomandlogin.MainActivity;
import nyc.c4q.ashiquechowdhury.auxx.model.Listener;

public class CreateRoomActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, Listener, Player.OperationCallback {

    private static final String CLIENT_ID = "a47e94f21a9649c982f39e72920c1754";
    public static SpotifyPlayer mPlayer;
    public static List<String> trackList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchandchoose_container);

        CreateSpotifyPlayer(AuthenticationToken.accessToken);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.searchandchoose_innerframe, new CreateRoomFragment())
                .replace(R.id.bottom_innerframe, new YellowFragment())
                .commit();


        SlidingUpPanelLayout slidingPanel = (SlidingUpPanelLayout) findViewById(R.id.activity_searchandchoose_container);
        slidingPanel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset > 0.0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottom_innerframe, new RedFragment()).commit();
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottom_innerframe, new RedFragment()).commit();
                } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottom_innerframe, new YellowFragment()).commit();
                }
            }
        });
    }

    private void CreateSpotifyPlayer(String accessToken) {
        Config playerConfig = new Config(this, accessToken, CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer) {
                mPlayer = spotifyPlayer;
                mPlayer.addConnectionStateCallback(CreateRoomActivity.this);
                mPlayer.addNotificationCallback(CreateRoomActivity.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("SearchAndChooseActivity", "Could not initialize player: " + throwable.getMessage());
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
        mPlayer.playUri(null, uri, 0, 0);
    }

    @Override
    public void queueSelectedTrack(String uri) {

    }
}