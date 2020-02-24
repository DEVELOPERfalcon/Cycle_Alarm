package kr.co.taksoft.cyclealarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeRecordAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<TimeRecordElement> datas = new ArrayList<>();

    public TimeRecordAdapter(Context context, ArrayList<TimeRecordElement> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.timer_list_element, parent, false);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder;
        TimeRecordElement element = datas.get(position);
        int num = element.getNum();
        String number;
        if(num<10) number = "0"+num;
        else number = ""+num;
        vh.num.setText(number);
        vh.mainTime.setText(element.getMainTime());
        vh.subTime.setText(element.getSubTime());
        setAnimation(vh.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView num;
        TextView mainTime;
        TextView subTime;

        public VH(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num);
            mainTime = itemView.findViewById(R.id.main_time);
            subTime = itemView.findViewById(R.id.sub_time);
        }
    }
}
