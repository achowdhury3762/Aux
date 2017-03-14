package nyc.c4q.ashiquechowdhury.auxx.model.artistModel;

import java.util.List;

/**
 * Created by jordansmith on 3/14/17.
 */

public class ArtistResponse {
    private List<Track> tracks = null;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
