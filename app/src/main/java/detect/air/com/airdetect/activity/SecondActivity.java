package detect.air.com.airdetect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import detect.air.com.airdetect.R;

public class SecondActivity extends BaseActivity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ajustHeight(this);
        findViewById(R.id.activity_second_iv_left).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.activity_second_iv_left) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
