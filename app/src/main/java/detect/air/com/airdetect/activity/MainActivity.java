package detect.air.com.airdetect.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import detect.air.com.airdetect.R;
import detect.air.com.airdetect.view.AirClean;

public class MainActivity extends BaseActivity implements OnClickListener {
    private AirClean airClean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ajustHeight(this);
        findViewById(R.id.activity_main_iv_right).setOnClickListener(this);

        airClean = (AirClean) findViewById(R.id.activity_main_ariclean);
        airClean.setDate("153", "中", "优", 1, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (airClean != null)
            airClean.startAnimation();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.activity_main_iv_right) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }

    }

}
