package nyc.c4q.ashiquechowdhury.auxx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class JoinRoomActivity extends AppCompatActivity {

    ImageView imgLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);


        TextView tv = (TextView) findViewById(R.id.toolbar_TV);
        AlterToolBar alter = new AlterToolBar("Home", tv);
        alter.changeToolBarName();

        imgLogo = (ImageView) findViewById(R.id.logo_img_view);

        Glide.with(this).load(R.drawable.lasso4).into(imgLogo);



    }
}
