package nyc.c4q.ashiquechowdhury.auxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class JoinRoomActivity extends AppCompatActivity {

    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);


        TextView tv = (TextView) findViewById(R.id.toolbar_TV);
        AlterToolBar alter = new AlterToolBar("Home", tv);
        alter.changeToolBarName();

        imgLogo = (ImageView) findViewById(R.id.logo_img_view);

        Glide.with(this).load(R.drawable.lasso4).into(imgLogo);

        CardView createRoomButton = (CardView) findViewById(R.id.create_room_button);
        CardView joinRoomButton = (CardView) findViewById(R.id.join_room_button);
        CardView viewProfileButton = (CardView) findViewById(R.id.view_profile_button);


        joinRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchAndChooseActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickCreateRoom(View view){
        Intent intent = new Intent(this, SearchAndChooseActivity.class);
        startActivity(intent);
    }

    public void onClickJoinRoom(View view){
        Intent intent = new Intent(this, SearchFragment.class);
        startActivity(intent);
    }
}
