package nyc.c4q.ashiquechowdhury.auxx;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private ImageView albumArt;
    private TextView artistName;
    private TextView songName;

    public SearchViewHolder(View itemView) {
        super(itemView);
        albumArt = (ImageView) itemView.findViewById(R.id.album_art_text_view);
        artistName = (TextView) itemView.findViewById(R.id.artist_name_text_view);
        songName = (TextView) itemView.findViewById(R.id.song_name_text_view);
    }

    public void bind(Item item) {
        artistName.setText(item.getArtists().get(0).getName());
        songName.setText(item.getName());
        if(!item.getAlbum().getImages().isEmpty()) {
            Glide.with(itemView.getContext()).load(item.getAlbum().getImages().get(0).getUrl()).into(albumArt);
        }
        else{
            Glide.with(itemView.getContext()).load("https://www.tunefind.com/i/new/album-art-empty.png").into(albumArt);
        }
    }
}
