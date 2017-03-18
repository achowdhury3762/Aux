package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.sdk.android.player.Player;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;
import nyc.c4q.ashiquechowdhury.auxx.util.SpotifyUtil;
import nyc.c4q.ashiquechowdhury.auxx.util.TrackListener;

public class MasterMusicPlayerControlsFragment extends Fragment implements View.OnClickListener, TrackListener {

    private FrameLayout upVoteButton;
    private FrameLayout playButton;
    private FrameLayout pauseButton;
    private FrameLayout downVoteButton;
    private SpotifyUtil spotify;
    private TextView currentTrackInfoTextView;
    private boolean isPlaylistPlaying = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_musicplayer, container, false);

        spotify = SpotifyUtil.getInstance();

        upVoteButton = (FrameLayout) view.findViewById(R.id.upvotebutton);
        playButton = (FrameLayout) view.findViewById(R.id.playbutton);
        pauseButton = (FrameLayout) view.findViewById(R.id.pausebutton);
        downVoteButton = (FrameLayout) view.findViewById(R.id.downvotebutton);
        currentTrackInfoTextView = (TextView) view.findViewById(R.id.current_playing_song_textview);
        currentTrackInfoTextView.setSelected(true);
        currentTrackInfoTextView.setSingleLine(true);
        SpotifyUtil.getInstance().setTracklistener(this);

        if(SongListHelper.getCurrentlyPlayingSong() != null){
            currentTrackInfoTextView.setText(SongListHelper.formatPlayerInfo(SongListHelper.getCurrentlyPlayingSong()));
        }
        else{
            currentTrackInfoTextView.setText("Click Play To Start Playlist");
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        upVoteButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        downVoteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        PlaylistTrack song;
        if (SongListHelper.getSongList().isEmpty()) {
            Toast.makeText(getContext(), "Add a song to the playlist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            song = SongListHelper.getSongList().get(0);
        }
        switch (view.getId()) {
            case R.id.upvotebutton:
                Toast.makeText(getContext(), "This song is great!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.playbutton:
                if(!isPlaylistPlaying){
                    showAlert(song);
                }
                else{
                    spotify.spotifyPlayer.resume((Player.OperationCallback) getActivity());
                    playButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.VISIBLE);

                }

//                Toast.makeText(getContext(), "Play the song", Toast.LENGTH_SHORT).show();
//                playButton.setVisibility(View.GONE);
//                pauseButton.setVisibility(View.VISIBLE);
//                spotify.spotifyPlayer.playUri(null, song.getTrackUri(), 0, 0);
//                SongListHelper.setCurrentlyPlayingSong(song);
//                SpotifyUtil.getInstance().getTracklistener().updateCurrentlyPlayingText(SongListHelper.formatPlayerInfo(song));
                break;
            case R.id.pausebutton:
                pauseButton.setVisibility(View.GONE);
                playButton.setVisibility(View.VISIBLE);
                spotify.spotifyPlayer.pause((Player.OperationCallback) getActivity());
                break;

            case R.id.downvotebutton:
                SongListHelper.removeSongAfterVeto(SongListHelper.getCurrentlyPlayingSong());
                Toast.makeText(getContext(), "This song sucks!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void updateCurrentlyPlayingText(String trackName) {
        currentTrackInfoTextView.setText(trackName);
    }

    public void showAlert(final PlaylistTrack song){
        new AlertDialog.Builder(getContext())
                .setTitle("Start Your Room's Playlist")
                .setMessage("Are you sure you're ready to Jam?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        playButton.setVisibility(View.GONE);
                        pauseButton.setVisibility(View.VISIBLE);
                        spotify.spotifyPlayer.playUri(null, song.getTrackUri(), 0, 0);
                        SongListHelper.setCurrentlyPlayingSong(song);
                        SpotifyUtil.getInstance().getTracklistener().updateCurrentlyPlayingText(SongListHelper.formatPlayerInfo(song));
                        isPlaylistPlaying = true;


                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void changePlayButton(){

    }


}
