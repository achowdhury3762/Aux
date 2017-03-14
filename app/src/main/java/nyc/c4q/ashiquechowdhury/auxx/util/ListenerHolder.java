package nyc.c4q.ashiquechowdhury.auxx.util;

import nyc.c4q.ashiquechowdhury.auxx.ArtistSongSelectedListener;

/**
 * Created by jordansmith on 3/14/17.
 */

public class ListenerHolder {

    public static ArtistSongSelectedListener artistSongSelectedListener;

    public static ArtistSongSelectedListener getArtistSongSelectedListener() {
        return artistSongSelectedListener;
    }

    public static void setArtistSongSelectedListener(ArtistSongSelectedListener artistSongSelectedListener) {
        ListenerHolder.artistSongSelectedListener = artistSongSelectedListener;
    }
}
