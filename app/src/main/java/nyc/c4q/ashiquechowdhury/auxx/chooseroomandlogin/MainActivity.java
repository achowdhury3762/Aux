package nyc.c4q.ashiquechowdhury.auxx.chooseroomandlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import nyc.c4q.ashiquechowdhury.auxx.R;
import nyc.c4q.ashiquechowdhury.auxx.util.AlterToolBar;

public class MainActivity extends AppCompatActivity implements ChooseRoomFragment.ToolBarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new LoginFragment()).commit();
    }

    @Override
    public void changeToolBarName(String name) {
        TextView tv = (TextView) findViewById(R.id.toolbar_TV);
        AlterToolBar alter = new AlterToolBar(name, tv);
        alter.changeToolBarName();
    }
}