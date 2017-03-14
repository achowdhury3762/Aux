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
import android.widget.LinearLayout;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import nyc.c4q.ashiquechowdhury.auxx.InfoSlideListener;
import nyc.c4q.ashiquechowdhury.auxx.PlaylistAdapter;
import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.SearchFragment;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;
import nyc.c4q.ashiquechowdhury.auxx.util.SpotifyUtil;

import static android.R.id.message;

public class PlaylistFragment extends Fragment implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, Player.OperationCallback {


    private static final String FRAGMENT_TAG = PlaylistFragment.class.getSimpleName();
    RecyclerView recyclerView;
    SpotifyUtil spotify;
    private FloatingActionButton floatingSearchBtn;
    private LinearLayout emptyLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        spotify = SpotifyUtil.getInstance();
        spotify.createPlayer(getContext());

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        floatingSearchBtn = (FloatingActionButton) view.findViewById(R.id.fab);
        emptyLayout = (LinearLayout) view.findViewById(R.id.empty_recyclerview_playlist_layout);

        PlaylistAdapter adapter = new PlaylistAdapter(SongListHelper.getSongList(), getContext(), (InfoSlideListener) getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        if(SongListHelper.getSongList().isEmpty()){
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            emptyLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

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
        Log.d("SearchAndChooseActivity", "Playback event received: " + playerEvent.name());

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
        super.onDestroy();

    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Error error) {

    }

}
