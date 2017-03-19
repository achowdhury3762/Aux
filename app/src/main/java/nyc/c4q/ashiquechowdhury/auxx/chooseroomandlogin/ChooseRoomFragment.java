package nyc.c4q.ashiquechowdhury.auxx.chooseroomandlogin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.joinandcreate.JoinRoomActivity;
import nyc.c4q.ashiquechowdhury.auxx.joinandcreate.PlaylistActivity;
import nyc.c4q.ashiquechowdhury.auxx.util.SingleLineTextView;

public class ChooseRoomFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_room, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

//        listener = (ToolBarListener) getActivity();
//        listener.changeToolBarName("ThisWorks");

        CardView createRoomButton = (CardView) view.findViewById(R.id.create_room_button);
        CardView joinRoomButton = (CardView) view.findViewById(R.id.join_room_button);
        CardView viewProfileButton = (CardView) view.findViewById(R.id.view_profile_button);
        SingleLineTextView singleLineTV = (SingleLineTextView) view.findViewById(R.id.aux_custom_tv);
        Typeface skinnyMarkerFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/skinny_marker.ttf");
        singleLineTV.setTypeface(skinnyMarkerFont);

        joinRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JoinRoomActivity.class));
            }
        });

        createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PlaylistActivity.class));

            }
        });

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
