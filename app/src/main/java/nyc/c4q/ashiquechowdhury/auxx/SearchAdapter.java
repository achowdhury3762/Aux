package nyc.c4q.ashiquechowdhury.auxx;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;
import nyc.c4q.ashiquechowdhury.auxx.util.SongListHelper;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Item> itemList;



    public SearchAdapter(List<Item> musicItemList) {
        this.itemList = musicItemList;
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
                SongListHelper.transformAndAdd(itemList.get(holder.getAdapterPosition()));
                Toast.makeText(holder.itemView.getContext(),"Added to Playlist",Toast.LENGTH_SHORT).show();
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
