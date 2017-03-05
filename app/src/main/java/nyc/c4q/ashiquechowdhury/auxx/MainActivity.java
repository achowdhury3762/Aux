package nyc.c4q.ashiquechowdhury.auxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Example;
import nyc.c4q.ashiquechowdhury.auxx.model.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.message;
import static android.content.ContentValues.TAG;
import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, Listener, Player.OperationCallback{

    private static final String CLIENT_ID = "a47e94f21a9649c982f39e72920c1754";

    private static final String REDIRECT_URI = "aux://callback";

    private Player mPlayer;
    private String accessCode;
    AuthenticationResponse response;
    List<Item> itemList;
    RecyclerView recyclerView;
    EditText editText;
    Button searchButton;
    Button playButton;
    Button pauseButton;
    Button queueButton;
    Button nextButton;
    Button previousButton;
    TextView playingTitle;
    TextView playingArtist;
    TextView playingAlbum;
    public static int trackCounter = 0;
    public static List<String> trackList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        editText = (EditText) findViewById(R.id.edit_text);
        searchButton = (Button) findViewById(R.id.search_button);
        playButton = (Button) findViewById(R.id.play_button);
        pauseButton = (Button) findViewById(R.id.pause_button);
        queueButton = (Button) findViewById(R.id.queue_button);
        nextButton = (Button) findViewById(R.id.next_button);
        previousButton = (Button) findViewById(R.id.previous_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                getSongData(input);
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.resume(MainActivity.this);

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause(MainActivity.this);
            }
        });
        queueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.playUri(null,trackList.get(0),0,0);
                trackCounter = 0;

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextTrack();
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPreviousTrack();
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addNotificationCallback(MainActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
        accessCode = " Bearer " + response.getAccessToken();

    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");

    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("MainActivity", "Login failed");

    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");

    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("MainActivity", "Received connection message: " + message);

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            case kSpPlaybackNotifyAudioDeliveryDone:
                playNextTrack();
            default:
                break;
        }

    }

    private void playNextTrack() {
        if(trackCounter + 1 >= trackList.size()){
            Toast.makeText(this,"end of playlist",Toast.LENGTH_SHORT).show();
        }
        else{
            trackCounter++;
            mPlayer.playUri(null,trackList.get(trackCounter),0,0);
        }
    }

    private void playPreviousTrack(){
        if(trackCounter - 1 < 0){
            Toast.makeText(this,"Start of playlist",Toast.LENGTH_SHORT).show();
            mPlayer.playUri(null,trackList.get(trackCounter),0,0);
        }
        else{
            trackCounter--;
            mPlayer.playUri(null,trackList.get(trackCounter),0,0);
        }

    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void playSelectedTrack(String uri) {
        mPlayer.playUri(null, uri, 0, 0);
    }

    @Override
    public void queueSelectedTrack(String uri) {
        Toast.makeText(this,"Track added to queue",Toast.LENGTH_SHORT).show();
        trackList.add(uri);
    }


    void findItems(){
        SearchAdapter searchAdapter = new SearchAdapter(itemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);

    }

    void getSongData(String query){
        query = query.replace(" ", "+");
//        String market = "US\" -H \"Accept: application/json\" -H \"Authorization: Bearer " + accessCode;
        String market = "from_token";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d(TAG, "onCreate: hello");

        SpotifyService spotifyService = retrofit.create(SpotifyService.class);
//        Call<Example> httpRequest = spotifyService.getResults(accessCode, query, "track", market);
        Call<Example> httpRequest = spotifyService.getOtherResults(query,"track");
        httpRequest.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    if (response.isSuccessful()) {
                        itemList = response.body().getTracks().getItems();
                        findItems();



                    } else {
                        Log.d(TAG, "Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("failure", "no connection");

            }
        });




    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Error error) {

    }
}
