package nyc.c4q.ashiquechowdhury.auxx;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Item> itemList;
    private SongClickListener songClickListener;

    public SearchAdapter(List<Item> musicItemList) {
        this.itemList = musicItemList;
    }

    public SearchAdapter(List<Item> itemList, SongClickListener searchFragment) {
        songClickListener = searchFragment;
        this.itemList = itemList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item_view, parent, false);
        return new SearchViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position) {
        holder.bind(itemList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songClickListener.songClicked(itemList.get(holder.getAdapterPosition()));
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
}
