package nyc.c4q.ashiquechowdhury.auxx.nonmaster;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;

public class NonMasterPlaylistAdapter extends RecyclerView.Adapter<NonMasterPlaylistViewHolder> {
    private List<PlaylistTrack> trackList;

    public NonMasterPlaylistAdapter(){
        trackList = new ArrayList<>();
    }

    @Override
    public NonMasterPlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.playlist_track_item_view, parent, false);
        return new NonMasterPlaylistViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(NonMasterPlaylistViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
