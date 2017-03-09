package nyc.c4q.ashiquechowdhury.auxx;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends AppCompatActivity {


    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_btn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity.this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.action_bar_to_replace, new SearchToolbarFragment())
                        .commit();
            }
        });
    }
}
