package nyc.c4q.ashiquechowdhury.auxx.chooseroomandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.joinandcreate.JoinRoomActivity;
import nyc.c4q.ashiquechowdhury.auxx.joinandcreate.PlaylistActivity;

public class ChooseRoomFragment extends Fragment {
    private ToolBarListener listener;
    private ImageView imgLogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_room, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imgLogo = (ImageView) view.findViewById(R.id.logo_img_view);

        Glide.with(this).load(R.drawable.lasso4).into(imgLogo);

        CardView createRoomButton = (CardView) view.findViewById(R.id.create_room_button);
        CardView joinRoomButton = (CardView) view.findViewById(R.id.join_room_button);
        CardView viewProfileButton = (CardView) view.findViewById(R.id.view_profile_button);

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
//                Intent intent = new Intent(getActivity(), TestActivity.class);
//                startActivity(intent);
            }
        });
    }

    interface ToolBarListener {
        void changeToolBarName(String name);
    }
}
