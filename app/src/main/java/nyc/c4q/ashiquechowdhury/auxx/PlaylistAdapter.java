package nyc.c4q.ashiquechowdhury.auxx;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;

/**
 * Created by jordansmith on 3/8/17.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<Item> itemList;


    public PlaylistAdapter(List<Item> itemList) {
        this.itemList = itemList;

    }


    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_track_item_view, parent, false);
        return new PlaylistViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(final PlaylistViewHolder holder, final int position) {
        holder.bind(itemList.get(position));
        holder.moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setView(R.layout.playlist_song_info_dialog_fragment_layout);

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setData(List<Item> data) {
        this.itemList = data;
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder{
        private ImageView albumArt;
        private TextView artistName;
        private TextView songName;
        private ImageButton moreInfoButton;


        public PlaylistViewHolder(View itemView) {
            super(itemView);
            albumArt = (ImageView) itemView.findViewById(R.id.playlist_album_art_text_view);
            artistName = (TextView) itemView.findViewById(R.id.playlist_artist_name_text_view);
            songName = (TextView) itemView.findViewById(R.id.playlist_song_name_text_view);
            moreInfoButton = (ImageButton) itemView.findViewById(R.id.playlist_more_image_button);
        }

        public void bind(Item item){
            artistName.setText(item.getArtists().get(0).getName());
            songName.setText(item.getName());
            Glide.with(itemView.getContext()).load(item.getAlbum().getImages().get(0).getUrl()).into(albumArt);


        }

    }
}


