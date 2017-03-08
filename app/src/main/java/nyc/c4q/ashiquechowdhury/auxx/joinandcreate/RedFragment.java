package nyc.c4q.ashiquechowdhury.auxx.joinandcreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nyc.c4q.ashiquechowdhury.auxx.R;

/**
 * Created by SACC on 3/6/17.
 */

public class RedFragment extends Fragment {

    private ImageView albumArtWorkIv;
    private TextView songNameTv;
    private TextView artistNameTv;
    private TextView albumNameTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.red_layout, container, false);

        albumArtWorkIv = (ImageView) view.findViewById(R.id.album_artwork);
        songNameTv = (TextView) view.findViewById(R.id.song_name);
        artistNameTv = (TextView) view.findViewById(R.id.artist_name);
        albumNameTv = (TextView) view.findViewById(R.id.album_name);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getContext()).load("https://i.scdn.co/image/b6e762dcce1502ce63eb2c68798843eb2ed53c51").into(albumArtWorkIv);
        songNameTv.setText("Starboy");
        artistNameTv.setText("The Weeknd");
        albumNameTv.setText("Starboy (The Album)");
    }
}
