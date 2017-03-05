package nyc.c4q.ashiquechowdhury.auxx;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ashiquechowdhury on 3/5/17.
 */
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
}
