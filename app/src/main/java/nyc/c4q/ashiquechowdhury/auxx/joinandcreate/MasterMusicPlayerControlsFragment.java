package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.spotify.sdk.android.player.Player;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;
import nyc.c4q.ashiquechowdhury.auxx.util.SpotifyUtil;

public class MasterMusicPlayerControlsFragment extends Fragment implements View.OnClickListener {

    private ImageView upVoteButton;
    private ImageView prevButton;
    private ImageView playButton;
    private ImageView pauseButton;
    private ImageView nextButton;
    private ImageView downVoteButton;
    private SpotifyUtil spotify;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_musicplayer, container, false);

        spotify = SpotifyUtil.getInstance();

        upVoteButton = (ImageView) view.findViewById(R.id.upvotebutton);
        prevButton = (ImageView) view.findViewById(R.id.prevbutton);
        playButton = (ImageView) view.findViewById(R.id.playbutton);
        pauseButton = (ImageView) view.findViewById(R.id.pausebutton);
        nextButton = (ImageView) view.findViewById(R.id.nextbutton);
        downVoteButton = (ImageView) view.findViewById(R.id.downvotebutton);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        upVoteButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
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
            case R.id.prevbutton:
                Toast.makeText(getContext(), "Replay the -ish!", Toast.LENGTH_SHORT).show();
                SongListHelper.playPreviousTrack(getContext());
                break;
            case R.id.playbutton:
                Toast.makeText(getContext(), "Play the song", Toast.LENGTH_SHORT).show();
                playButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
                spotify.spotifyPlayer.playUri(null, song.getTrackUri(), 0, 0);
                break;
            case R.id.pausebutton:
                Toast.makeText(getContext(), "Pause the song", Toast.LENGTH_SHORT).show();
                pauseButton.setVisibility(View.GONE);
                playButton.setVisibility(View.VISIBLE);
                spotify.spotifyPlayer.pause((Player.OperationCallback) getActivity());
                break;
            case R.id.nextbutton:
                Toast.makeText(getContext(), "Skip the song-o", Toast.LENGTH_SHORT).show();
                SongListHelper.playNextTrack();
                break;
            case R.id.downvotebutton:
                Toast.makeText(getContext(), "This song sucks!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
