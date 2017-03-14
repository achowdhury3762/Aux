package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import es.dmoral.toasty.Toasty;
import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;

/**
 * Created by SACC on 3/6/17.
 */

public class CurrentSongInfoFragment extends Fragment implements View.OnClickListener {

    private ImageView albumArtWorkIv;
    private TextView songNameTv;
    private TextView artistNameTv;
    private TextView albumNameTv;
    private ImageButton likeButton;
    private ImageButton vetoButton;
    private final String CHOSEN_TRACK_KEY = "chosen track";
    private PlaylistTrack track;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currentsong_info, container, false);
        initializeViews();

        Bundle bundle = getArguments();
        if(bundle != null){
            track = (PlaylistTrack) bundle.getSerializable(CHOSEN_TRACK_KEY);
            Glide.with(getContext()).load(track.getAlbumArt()).into(albumArtWorkIv);
            songNameTv.setText(track.getTrackName());
            artistNameTv.setText(track.getArtistName());
            albumNameTv.setText(track.getAlbumName());
        }
        else{
            if(SongListHelper.getCurrentlyPlayingSong() != null) {


                Glide.with(getContext()).load(SongListHelper.getCurrentlyPlayingSong().getAlbumArt()).into(albumArtWorkIv);
                songNameTv.setText(SongListHelper.getCurrentlyPlayingSong().getTrackName());
                artistNameTv.setText(SongListHelper.getCurrentlyPlayingSong().getArtistName());
                albumNameTv.setText(SongListHelper.getCurrentlyPlayingSong().getAlbumName());
            }
            else{
                view = inflater.inflate(R.layout.empty_current_song_layout, container, false);

            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.like_button_info_fragment:
                Toasty.success(v.getContext(), "You liked this Song", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.veto_button_info_fragment:
                Toasty.error(v.getContext(), "You Vetoed This Song.", Toast.LENGTH_SHORT, true).show();
                break;
        }

    }

    public void initializeViews(){
        albumArtWorkIv = (ImageView) view.findViewById(R.id.album_art);
        songNameTv = (TextView) view.findViewById(R.id.song_name);
        artistNameTv = (TextView) view.findViewById(R.id.artist_name);
        albumNameTv = (TextView) view.findViewById(R.id.album_name);
        likeButton = (ImageButton) view.findViewById(R.id.like_button_info_fragment);
        vetoButton = (ImageButton) view.findViewById(R.id.veto_button_info_fragment);
        likeButton.setOnClickListener(this);
        vetoButton.setOnClickListener(this);
    }
}
