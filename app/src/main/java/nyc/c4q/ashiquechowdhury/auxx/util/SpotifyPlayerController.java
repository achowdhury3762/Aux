package nyc.c4q.ashiquechowdhury.auxx.util;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * Created by jordansmith on 3/8/17.
 */

public interface SpotifyPlayerController {

    void playNextTrack(SpotifyPlayer player);
    void playpreviousTrack(SpotifyPlayer player);
    void play(SpotifyPlayer player, Player.OperationCallback callback);
    void pause(SpotifyPlayer player, Player.OperationCallback callback);

}
