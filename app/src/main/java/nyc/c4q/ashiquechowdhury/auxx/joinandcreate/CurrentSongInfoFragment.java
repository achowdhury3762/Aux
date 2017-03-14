package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;

/**
 * Created by SACC on 3/6/17.
 */

public class CurrentSongInfoFragment extends Fragment {

    private ImageView albumArtWorkIv;
    private TextView songNameTv;
    private TextView artistNameTv;
    private TextView albumNameTv;
    private final String CHOSEN_TRACK_KEY = "chosen track";
    private PlaylistTrack track;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currentsong_info, container, false);

        albumArtWorkIv = (ImageView) view.findViewById(R.id.album_art);
        songNameTv = (TextView) view.findViewById(R.id.song_name);
        artistNameTv = (TextView) view.findViewById(R.id.artist_name);
        albumNameTv = (TextView) view.findViewById(R.id.album_name);
        Bundle bundle = getArguments();
        Log.d("afterBundle","what is bundle");
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
