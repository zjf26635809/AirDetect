package detect.air.com.airdetect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import detect.air.com.airdetect.R;

/**
 * Created by p06687 on 2016/11/21.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHoler> {

    private List<String> arrays;
    private LayoutInflater layoutInflater;


    public MyRecyclerAdapter(Context context, List<String> arrays) {
        this.arrays = arrays;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHoler holder, int position) {

        holder.aa.setText("111");

    }

    @Override
    public int getItemCount() {
        return arrays.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public MyRecyclerAdapter.MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHoler(layoutInflater.inflate(R.layout.row_item, parent, false));
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {

        private TextView aa;

        public MyViewHoler(View itemView) {
            super(itemView);
            aa = (TextView) itemView.findViewById(R.id.row_item_tv);
        }


    }

}
