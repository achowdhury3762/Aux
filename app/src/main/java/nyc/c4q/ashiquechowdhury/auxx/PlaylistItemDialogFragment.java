package nyc.c4q.ashiquechowdhury.auxx;

import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import nyc.c4q.ashiquechowdhury.auxx.model.Item;

/**
 * Created by jordansmith on 3/8/17.
 */

public class PlaylistItemDialogFragment extends DialogFragment {
    TextView dialogArtist;
    TextView dialogSong;
    TextView dialogAlbum;
    TextView dialogYear;
    ImageView albumArt;


    public PlaylistItemDialogFragment(){

    }

    public static PlaylistItemDialogFragment newInstance(Item item) {
        PlaylistItemDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


}
