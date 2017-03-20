package nyc.c4q.ashiquechowdhury.auxx.nonmaster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;

public class NonMasterPlaylistViewHolder extends RecyclerView.ViewHolder {

    private ImageView albumArt;
    private TextView artistName;
    private TextView songName;

    public NonMasterPlaylistViewHolder(View itemView) {
        super(itemView);
        albumArt = (ImageView) itemView.findViewById(R.id.playlist_album_art_text_view);
        artistName = (TextView) itemView.findViewById(R.id.playlist_artist_name_text_view);
        songName = (TextView) itemView.findViewById(R.id.playlist_song_name_text_view);
    }

    public void bind(PlaylistTrack track) {
        artistName.setText(track.getArtistName());
        songName.setText(track.getTrackName());
        Glide.with(itemView.getContext()).load(track.getAlbumArt()).into(albumArt);
    }
}
