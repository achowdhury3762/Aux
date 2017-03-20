package nyc.c4q.ashiquechowdhury.auxx.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.auxx.master.MasterSearchFragment;
import nyc.c4q.ashiquechowdhury.auxx.model.Item;
import nyc.c4q.ashiquechowdhury.auxx.model.PlaylistTrack;
import nyc.c4q.ashiquechowdhury.auxx.model.artistModel.Track;

public class SongListHelper {
    public static int trackCounter = 0;
    public static boolean isSongPlaying = false;
    public static boolean isPlaylistPlaying = false;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    public static List<PlaylistTrack> songList = new ArrayList<>();

    private static PlaylistTrack currentlyPlayingSong;

    public static String roomName;

    static SpotifyUtil spotify = SpotifyUtil.getInstance();

    public static List<PlaylistTrack> getSongList() {
        return songList;
    }

    public static void setSongList(List<PlaylistTrack> songList) {
        SongListHelper.songList = songList;
    }

    public static PlaylistTrack getCurrentlyPlayingSong() {
        return currentlyPlayingSong;
    }

    public static void setCurrentlyPlayingSong(PlaylistTrack currentlyPlayingSong) {
        SongListHelper.currentlyPlayingSong = currentlyPlayingSong;
    }

    public static void playNextTrack() {
        if (trackCounter + 1 >= SongListHelper.getSongList().size()) {
        } else {
            trackCounter++;
            PlaylistTrack track = SongListHelper.getSongList().get(trackCounter);
            setCurrentlyPlayingSong(track);
            spotify.spotifyPlayer.playUri(null, track.getTrackUri(), 0, 0);
            spotify.getTracklistener().updateCurrentlyPlayingText(formatPlayerInfo(track));
            Log.d(String.valueOf(trackCounter) + "next", currentlyPlayingSong.getTrackName());
        }
    }

    public static void playPreviousTrack(Context context) {
        PlaylistTrack track;
        if (trackCounter - 1 < 0) {
            Toast.makeText(context, "Start of playlist", Toast.LENGTH_SHORT).show();
            track = SongListHelper.getSongList().get(trackCounter);
            setCurrentlyPlayingSong(track);
            spotify.spotifyPlayer.playUri(null, track.getTrackUri(), 0, 0);
            spotify.getTracklistener().updateCurrentlyPlayingText(formatPlayerInfo(track));
        } else {
            trackCounter--;
            track = SongListHelper.getSongList().get(trackCounter);
            setCurrentlyPlayingSong(track);
            spotify.spotifyPlayer.playUri(null, track.getTrackUri(), 0, 0);
            spotify.getTracklistener().updateCurrentlyPlayingText(formatPlayerInfo(track));
        }
    }

    public static PlaylistTrack transformAndAdd(Item item) {
        PlaylistTrack track = new PlaylistTrack.Builder(item.getName())
                .trackUri(item.getUri())
                .albumName(item.getAlbum().getName())
                .artistName(item.getArtists().get(0).getName())
                .artistId(item.getArtists().get(0).getId())
                .build();

        if (item.getAlbum().getImages().isEmpty()) {
            track.setAlbumArt("https://www.tunefind.com/i/new/album-art-empty.png");
        } else {
            track.setAlbumArt(item.getAlbum().getImages().get(0).getUrl());
        }

        return track;
    }

    public static PlaylistTrack transformAndAdd(Track track) {
        PlaylistTrack playlistTrack = new PlaylistTrack.Builder(track.getName())
                .trackUri(track.getUri())
                .albumName(track.getAlbum().getName())
                .artistName(track.getArtists().get(0).getName())
                .artistId(track.getArtists().get(0).getId())
                .build();

        if (track.getAlbum().getImages().isEmpty()) {
            playlistTrack.setAlbumArt("https://www.tunefind.com/i/new/album-art-empty.png");
        } else {
            playlistTrack.setAlbumArt(track.getAlbum().getImages().get(0).getUrl());
        }
        return playlistTrack;
    }

    public static void removeSongAfterVeto(PlaylistTrack track) {
        if (currentlyPlayingSong != null && SongListHelper.currentlyPlayingSong.equals(track)) {
            playNextTrack();
            SongListHelper.getSongList().remove(track);
            trackCounter = songList.indexOf(currentlyPlayingSong);
            Log.d(String.valueOf(trackCounter) + "if", currentlyPlayingSong.getTrackName());
        } else {
            SongListHelper.getSongList().remove(track);
            if (currentlyPlayingSong != null) {
                trackCounter = songList.indexOf(currentlyPlayingSong);
                Log.d(String.valueOf(trackCounter) + "else", currentlyPlayingSong.getTrackName());
            }
        }
    }

    public static String formatPlayerInfo(PlaylistTrack track) {
        StringBuilder sb = new StringBuilder();
        sb.append(track.getArtistName());
        sb.append(" ");
        sb.append("-");
        sb.append(" ");
        sb.append(track.getTrackName());
        return sb.toString();
    }

//    public void checkVeto() {
//        if (trackCounter + 1 >= SongListHelper.getSongList().size()) {
//        }
//        else {
//            int tempTrackCounter = trackCounter + 1;
//            PlaylistTrack track = SongListHelper.getSongList().get(tempTrackCounter);
//            if(track.getVetos() < 3){
//                playNextTrack();
//            }
//            else{
//                removeSongAfterVeto(track);
//                database = FirebaseDatabase.getInstance();
//                reference = database.getReference().child(MasterSearchFragment.MUSIC_LIST);
//                Query removedMusicQuery = reference.orderByChild("trackName").equalTo(track.getTrackName());
//                removedMusicQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                            appleSnapshot.getRef().removeValue();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//                playNextTrack();
//            }
//        }
//}

}