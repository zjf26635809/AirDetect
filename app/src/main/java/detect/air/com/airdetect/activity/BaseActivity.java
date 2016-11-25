package detect.air.com.airdetect.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import detect.air.com.airdetect.R;
import detect.air.com.airdetect.tools.DensityUtil;

public class BaseActivity extends Activity {

    @SuppressLint("InlinedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    protected void ajustHeight(Activity activity) {
        if (activity != null)
            activity.findViewById(R.id.activity_parent).setPadding(0, DensityUtil.dip2px(activity, 25), 0, 0);
    }
}
