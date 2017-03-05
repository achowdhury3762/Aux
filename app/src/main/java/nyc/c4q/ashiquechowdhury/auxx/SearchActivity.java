package nyc.c4q.ashiquechowdhury.auxx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class SearchActivity extends AppCompatActivity {
    private List<Item> musicItemList = new ArrayList<>();
    private RecyclerView musicList;
    private SearchAdapter mAdapter;
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        musicList = (RecyclerView) findViewById(R.id.search_recycler);
        musicList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter(musicItemList);
        musicList.setAdapter(mAdapter);
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
