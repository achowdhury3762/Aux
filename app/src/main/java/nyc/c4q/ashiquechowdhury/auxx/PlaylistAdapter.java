package nyc.c4q.ashiquechowdhury.auxx;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;

/**
 * Created by jordansmith on 3/8/17.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<PlaylistTrack> trackList;
    private Context context;
    private InfoSlideListener infoSlideListener;


    public PlaylistAdapter(List<PlaylistTrack> trackList, Context context, InfoSlideListener infoSlideListener) {
        this.trackList = trackList;
        this.context = context;
        this.infoSlideListener = infoSlideListener;

    }


    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_track_item_view, parent, false);
        return new PlaylistViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(final PlaylistViewHolder holder, int position) {
        holder.bind(trackList.get(position));
        holder.moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(context, trackList.get(holder.getAdapterPosition()));

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoSlideListener.slidePanelWithInfo(trackList.get(holder.getAdapterPosition()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public void setData(List<PlaylistTrack> data) {
        this.trackList = data;
    }

    public void showDialog(Context context, PlaylistTrack track){
        android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
        PlaylistItemDialogFragment dialog = PlaylistItemDialogFragment.newInstance(track);
        dialog.show(fm, "fragment_edit_name" );

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

        public void bind(PlaylistTrack track){
            artistName.setText(track.getArtistName());
            songName.setText(track.getTrackName());
            Glide.with(itemView.getContext()).load(track.getAlbumArt()).into(albumArt);


        }

    }
}


