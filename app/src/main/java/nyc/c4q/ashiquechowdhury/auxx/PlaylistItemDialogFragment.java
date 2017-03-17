package nyc.c4q.ashiquechowdhury.auxx;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;

/**
 * Created by jordansmith on 3/8/17.
 */

public class PlaylistItemDialogFragment extends DialogFragment {
    private TextView dialogArtist;
    private TextView dialogSong;
    private TextView dialogAlbum;
    private ImageView albumArt;


    public PlaylistItemDialogFragment(){

    }

    public static PlaylistItemDialogFragment newInstance(PlaylistTrack track) {
        PlaylistItemDialogFragment frag = new PlaylistItemDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("Current Track", track);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_song_info_dialog_fragment_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        dialogArtist = (TextView)view.findViewById(R.id.dialog_artist_text_view);
        dialogSong = (TextView)view.findViewById(R.id.dialog_song_text_view);
        dialogAlbum = (TextView)view.findViewById(R.id.dialog_album_text_view);
        albumArt = (ImageView) view.findViewById(R.id.album_art_dialog_fragment);

        PlaylistTrack track = (PlaylistTrack) getArguments().getSerializable("Current Track");

        dialogArtist.setText(track.getArtistName());
        dialogSong.setText(track.getTrackName());
        dialogAlbum.setText(track.getAlbumName());
        Picasso.with(view.getContext()).load(track.getAlbumArt()).fit().into(albumArt);

    }
}




