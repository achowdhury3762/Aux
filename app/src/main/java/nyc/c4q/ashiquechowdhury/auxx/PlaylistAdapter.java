package nyc.c4q.ashiquechowdhury.auxx;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;

/**
 * Created by jordansmith on 3/8/17.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Item> itemList;


    public PlaylistAdapter(List<Item> itemList) {
        this.itemList = itemList;

    }


    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item_view, parent, false);
        return new SearchViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {
        holder.bind(itemList.get(position));
        

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setData(List<Item> data) {
        this.itemList = data;
    }
}
