package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import nyc.c4q.ashiquechowdhury.auxx.FloatingActionSearchFragment;
import nyc.c4q.ashiquechowdhury.auxx.PlaylistAdapter;
import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.model.Item;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;
import nyc.c4q.ashiquechowdhury.auxx.util.SpotifyUtil;

import static android.R.id.message;
import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

/**
 * Created by SACC on 3/5/17.
 */

public class CreateRoomFragment extends Fragment implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, Player.OperationCallback {

    RecyclerView recyclerView;
    Button playButton;
    Button pauseButton;
    Button queueButton;
    Button nextButton;
    Button previousButton;
    SpotifyUtil spotify;

    private FloatingActionButton floatingSearchBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchandchoose, container, false);

        spotify = SpotifyUtil.getInstance();
        spotify.authorizeSpotifyInFragment(this);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        playButton = (Button) view.findViewById(R.id.play_button);
        pauseButton = (Button) view.findViewById(R.id.pause_button);
        queueButton = (Button) view.findViewById(R.id.queue_button);
        nextButton = (Button) view.findViewById(R.id.next_button);
        previousButton = (Button) view.findViewById(R.id.previous_button);
        floatingSearchBtn = (FloatingActionButton) view.findViewById(R.id.fab);

        PlaylistAdapter adapter = new PlaylistAdapter(SongListHelper.songList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        floatingSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.searchandchoose_innerframe, new FloatingActionSearchFragment()).commit();
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotify.spotifyPlayer.resume((Player.OperationCallback) getActivity());

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotify.spotifyPlayer.pause((Player.OperationCallback) getActivity());
            }
        });
        queueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item song = SongListHelper.songList.get(0);
                spotify.spotifyPlayer.playUri(null, song.getUri(), 0, 0);
                SongListHelper.trackCounter = 0;
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongListHelper.playNextTrack();
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongListHelper.playPreviousTrack(getContext());
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            spotify.setResponse(AuthenticationClient.getResponse(resultCode, intent));
            if (spotify.getResponse().getType() == AuthenticationResponse.Type.TOKEN) {
                spotify.createPlayer(getContext());
            }
        }
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
//        Log.d("SearchAndChooseActivity", "Playback event received: " + playerEvent.name());
//        switch (playerEvent) {
//            case kSpPlaybackNotifyAudioDeliveryDone:
//                playNextTrack();
//            default:
//                break;
//        }

    }


    @Override
    public void onPlaybackError(Error error) {
        Log.d("SearchAndChooseActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }

    }

    @Override
    public void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
//        Spotify.destroyPlayer(this);
        super.onDestroy();
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Error error) {

    }

}
