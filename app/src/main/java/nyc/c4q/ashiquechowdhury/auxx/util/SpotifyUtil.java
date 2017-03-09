package nyc.c4q.ashiquechowdhury.auxx.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import static android.R.id.message;
import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

/**
 * Created by jordansmith on 3/7/17.
 */

public class SpotifyUtil implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, Player.OperationCallback {
    private static final String CLIENT_ID = "a47e94f21a9649c982f39e72920c1754";
    private static final String REDIRECT_URI = "aux://callback";
    private static SpotifyUtil instance;
    public Player spotifyPlayer;
    private AuthenticationResponse response;

    private SpotifyUtil() {

    }

    public static SpotifyUtil getInstance() {
        if (instance == null) {
            instance = new SpotifyUtil();

        }
        return instance;
    }

    public void authorizeSpotifyInFragment(Fragment fragment) {
        if (response == null || response.getExpiresIn() <= 0) {
            AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                    AuthenticationResponse.Type.TOKEN,
                    REDIRECT_URI);
            builder.setScopes(new String[]{"user-read-private", "streaming"});
            AuthenticationRequest request = builder.build();
            Intent intent = AuthenticationClient.createLoginActivityIntent(fragment.getActivity(), request);
            fragment.startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public void authorizeSpotifyInActivity(AppCompatActivity activity) {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(activity, REQUEST_CODE, request);
    }

    public void setResponse(AuthenticationResponse response) {
        this.response = response;
    }

    public AuthenticationResponse getResponse() {
        return response;
    }

    public void createPlayer(Context activityContext) {
        Config playerConfig = new Config(activityContext, response.getAccessToken(), CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer) {
                instance.spotifyPlayer = spotifyPlayer;
                instance.spotifyPlayer.addConnectionStateCallback(SpotifyUtil.this);
                instance.spotifyPlayer.addNotificationCallback(SpotifyUtil.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("SearchAndChooseActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }


    @Override
    public void onLoggedIn() {
        Log.d(getClass().getName(), "User logged in");

    }

    @Override
    public void onLoggedOut() {
        Log.d(getClass().getName(), "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d(getClass().getName(), "Login failed");

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d(getClass().getName(), "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            case kSpPlaybackNotifyAudioDeliveryDone:
                SongListHelper.playNextTrack();
            default:
                break;
        }

    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d(getClass().getName(), "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }

    }

    @Override
    public void onSuccess() {
        Log.d(getClass().getName(), "Received connection message: " + message);
    }

    @Override
    public void onError(Error error) {

    }

}
