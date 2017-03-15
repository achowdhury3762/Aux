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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> implements RowClickedListener{
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<PlaylistTrack> trackList;
    private Context context;

    public PlaylistAdapter(Context context) {
        trackList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_track_item_view, parent, false);
        return new PlaylistViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(final PlaylistViewHolder holder, int position) {
        holder.bind(trackList.get(position), this);
        holder.moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(context, trackList.get(holder.getAdapterPosition()));
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

    public void add(PlaylistTrack myTrack) {
        trackList.add(myTrack);
        notifyItemInserted(trackList.size() - 1);
    }

    @Override
    public void rowClicked(PlaylistTrack track) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(SearchFragment.MUSIC_LIST);
        Query removedMusicQuery = reference.orderByChild("trackName").equalTo(track.getTrackName());
        removedMusicQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeTrackWithAlbumName(String albumName) {
        int albumposition = 0;
        for(int i=0; i < trackList.size(); i++){
            if(trackList.get(i).getAlbumName().equals(albumName)){
                albumposition = i;
            }
        }
        if(trackList.size() == 0){
            throw new IllegalArgumentException("TrackList Size Cannot Be Zero");
        }
        trackList.remove(albumposition);
        notifyItemRemoved(albumposition);
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

        public void bind(final PlaylistTrack track, final RowClickedListener listener){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.rowClicked(track);
                    return false;
                }
            });
            artistName.setText(track.getArtistName());
            songName.setText(track.getTrackName());
            Glide.with(itemView.getContext()).load(track.getAlbumArt()).into(albumArt);

        }
    }
}


