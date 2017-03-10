package nyc.c4q.ashiquechowdhury.auxx.util;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;

/**
 * Created by shawnspeaks on 3/7/17.
 */

public class SongListHelper {
    public static int trackCounter = 0;

    public static List<PlaylistTrack> songList = new ArrayList<>();

    public static List<PlaylistTrack> getSongList() {
        return songList;
    }


    public static void playNextTrack(){
        if (trackCounter + 1 >= songList.size()) {
        } else {
            trackCounter++;
            SpotifyUtil.getInstance().spotifyPlayer.playUri(null, songList.get(trackCounter).getTrackUri(), 0, 0);
        }
    }

    public static void playPreviousTrack(Context context) {
        if (trackCounter - 1 < 0) {
            Toast.makeText(context, "Start of playlist", Toast.LENGTH_SHORT).show();
            SpotifyUtil.getInstance().spotifyPlayer.playUri(null, songList.get(trackCounter).getTrackUri(), 0, 0);
        } else {
            trackCounter--;
            SpotifyUtil.getInstance().spotifyPlayer.playUri(null, songList.get(trackCounter).getTrackUri(), 0, 0);
        }
    }

    public static void transformAndAdd (Item item){

        PlaylistTrack track = new PlaylistTrack.Builder(item.getName())
                .trackUri(item.getUri())
                .albumName(item.getAlbum().getName())
                .artistName(item.getArtists().get(0).getName())
                .build();

        if(item.getAlbum().getImages().isEmpty()){
            track.setAlbumArt("https://www.tunefind.com/i/new/album-art-empty.png");
        }
        else{
            track.setAlbumArt(item.getAlbum().getImages().get(0).getUrl());
        }




        songList.add(track);

    }


}


