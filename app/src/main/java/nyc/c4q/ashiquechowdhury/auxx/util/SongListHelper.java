package nyc.c4q.ashiquechowdhury.auxx.util;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;

/**
 * Created by shawnspeaks on 3/7/17.
 */

public class SongListHelper {
    public static int trackCounter = 0;

    public static List<Item> songList = new ArrayList<>();

    public static List<Item> getSongList() {
        return songList;
    }


    public static void playNextTrack(){
        if (trackCounter + 1 >= songList.size()) {
        } else {
            trackCounter++;
            SpotifyUtil.getInstance().spotifyPlayer.playUri(null, songList.get(trackCounter).getUri(), 0, 0);
        }
    }

    public static void playPreviousTrack(Context context) {
        if (trackCounter - 1 < 0) {
            Toast.makeText(context, "Start of playlist", Toast.LENGTH_SHORT).show();
            SpotifyUtil.getInstance().spotifyPlayer.playUri(null, songList.get(trackCounter).getUri(), 0, 0);
        } else {
            trackCounter--;
            SpotifyUtil.getInstance().spotifyPlayer.playUri(null, songList.get(trackCounter).getUri(), 0, 0);
        }
    }



}


