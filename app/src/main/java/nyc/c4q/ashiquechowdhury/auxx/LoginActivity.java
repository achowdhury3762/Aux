package nyc.c4q.ashiquechowdhury.auxx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by SACC on 3/4/17.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView logoIv = (ImageView) findViewById(R.id.aux_logo);
        Glide.with(getApplicationContext()).load(R.drawable.lasso4).into(logoIv);
    }
}
