package detect.air.com.airdetect.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import detect.air.com.airdetect.R;
import detect.air.com.airdetect.adapter.MyRecyclerAdapter;

public class NewActivity extends BaseActivity {


    private RecyclerView recyclerView;

    private MyRecyclerAdapter myRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ajustHeight(this);
        initUI();
    }

    private void initUI() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_new_recyclerview);

       /* recyclerView.setItemAnimator(new);*/

        List<String> names = new ArrayList<String>();
        names.add("");
        names.add("");
        names.add("");

        myRecyclerAdapter = new MyRecyclerAdapter(this, names);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
