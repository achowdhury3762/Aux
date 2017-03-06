package nyc.c4q.ashiquechowdhury.auxx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Example;
import nyc.c4q.ashiquechowdhury.auxx.model.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment {
    private List<Item> musicItemList = new ArrayList<>();
    private RecyclerView musicList;
    private SearchAdapter mAdapter;
    Button searchButton;
    private EditText search_editT;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search_editT = (EditText) view.findViewById(R.id.search_edit_text);
        musicList = (RecyclerView) view.findViewById(R.id.search_recycler);
        musicList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SearchAdapter(musicItemList);
        musicList.setAdapter(mAdapter);

        searchButton = (Button) view.findViewById(R.id.search_buttn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSongData(search_editT.getText().toString());
            }
        });
    }


    void getSongData(String query) {
        query = query.replace(" ", "+");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpotifyService spotifyService = retrofit.create(SpotifyService.class);
        Call<Example> httpRequest = spotifyService.getOtherResults(query, "track");
        httpRequest.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    if (response.isSuccessful()) {
                        musicItemList = response.body().getTracks().getItems();
                        refreshMusicList();
                    } else {
                        Log.d(TAG, "Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("failure", "no connection");

            }
        });
    }

    private void refreshMusicList() {
        mAdapter.setData(musicItemList);
    }
}
