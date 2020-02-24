package kr.co.taksoft.cyclealarm;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Page3Fragment_Stopwatch extends Fragment {

    final  static int IDLE = 0, RUNNING = 1, PAUSE = 2;
    int status;
    int timeRecordNum, milliSecond, second, minute, hour, subMilliSecond, subSecond, subMinute, subHour;
    long baseTime, subBaseTime, pauseTime;

    View view;
    Toolbar toolbar;
    LinearLayout timeSection;
    TextView rootTimer, subTimer;
    Button startBtn, continueBtn, pauseBtn, segmentRecordBtn;
    ObjectAnimator sectionAnimator1, sectionAnimator2, animator0, animator1, animator2, animator3, animator4;
    Handler handler;
    RecyclerView recyclerView;
    ArrayList<TimeRecordElement> datas = new ArrayList<>();
    TimeRecordAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page3_stopwatch, container, false);

        timeSection = view.findViewById(R.id.timer_section);
        recyclerView = view.findViewById(R.id.timer_recycler);
        adapter = new TimeRecordAdapter(getActivity(), datas);

        rootTimer = view.findViewById(R.id.root_timer);
        subTimer = view.findViewById(R.id.sub_timer);

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                rootTimer.setText(getTime());
                subTimer.setText(getTime2());
                handler.sendEmptyMessage(0);
            }
        };

        startBtn = view.findViewById(R.id.btn_start);
        pauseBtn = view.findViewById(R.id.btn_pause);
        continueBtn = view.findViewById(R.id.btn_continue);
        segmentRecordBtn = view.findViewById(R.id.btn_segment_record);
        startBtn.setBackgroundResource(R.drawable.timer_button_shape);
        pauseBtn.setBackgroundResource(R.drawable.timer_button_shape);
        continueBtn.setBackgroundResource(R.drawable.timer_button_shape);
        segmentRecordBtn.setBackgroundResource(R.drawable.timer_button_shape);
        startBtn.setOnClickListener(startBtnClickListener);
        pauseBtn.setOnClickListener(pauseBtnClickListener);
        continueBtn.setOnClickListener(pauseBtnClickListener);
        segmentRecordBtn.setOnClickListener(segmentRecordBtnClickListener);

        sectionAnimator1 = ObjectAnimator.ofFloat(timeSection, "translationY", -400f);
        sectionAnimator2 = ObjectAnimator.ofFloat(timeSection, "translationY", 0);
        animator0 = ObjectAnimator.ofFloat(continueBtn, "translationX", -250f);
        animator1 = ObjectAnimator.ofFloat(pauseBtn, "translationX", -250f);
        animator2 = ObjectAnimator.ofFloat(segmentRecordBtn, "translationX", 250f);
        animator3 = ObjectAnimator.ofFloat(continueBtn, "translationX", 0);
        animator4 = ObjectAnimator.ofFloat(segmentRecordBtn, "translationX", 0);
        sectionAnimator1.setDuration(300);
        sectionAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }
            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) { }
            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        sectionAnimator2.setDuration(300);
        animator0.setDuration(500);
        animator1.setDuration(500);
        animator2.setDuration(500);
        animator3.setDuration(500);
        animator4.setDuration(500);
        animator4.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }
            @Override
            public void onAnimationEnd(Animator animation) {
                continueBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.GONE);
                segmentRecordBtn.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                status = IDLE;
            }
            @Override
            public void onAnimationCancel(Animator animation) { }
            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        recyclerView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onDestroy() {
        handler.removeMessages(0);
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_items3, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    View.OnClickListener startBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timeRecordNum = 1;
            segmentRecordBtn.setText(R.string.segment_record);
            startBtn.setVisibility(View.INVISIBLE);
            pauseBtn.setVisibility(View.VISIBLE);
            segmentRecordBtn.setVisibility(View.VISIBLE);
            //타이머 시작
            animator0.start();
            animator1.start();
            animator2.start();
            baseTime = SystemClock.elapsedRealtime();
            handler.sendEmptyMessage(0);
            status = RUNNING;
        }
    };
    View.OnClickListener pauseBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(status == RUNNING){
                // 중지 클릭시
                handler.removeMessages(0);
                pauseTime = SystemClock.elapsedRealtime();
                pauseBtn.setVisibility(View.GONE);
                continueBtn.setVisibility(View.VISIBLE);
                segmentRecordBtn.setText(R.string.reset);
                status = PAUSE;
            }else{
                // 계속 클릭시
                long now = SystemClock.elapsedRealtime();
                baseTime += (now - pauseTime);
                subBaseTime += (now - pauseTime);
                handler.sendEmptyMessage(0);
                pauseBtn.setVisibility(View.VISIBLE);
                continueBtn.setVisibility(View.GONE);
                segmentRecordBtn.setText(R.string.segment_record);
                status = RUNNING;
            }
        }
    };
    View.OnClickListener segmentRecordBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(status == RUNNING){
                // 구간기록
                subTimer.setVisibility(View.VISIBLE);
                subBaseTime = SystemClock.elapsedRealtime();
                if(timeRecordNum==1){
                    sectionAnimator1.start();
                    datas.add(new TimeRecordElement(timeRecordNum, rootTimer.getText().toString(), rootTimer.getText().toString()));
                }else{
                    datas.add(new TimeRecordElement(timeRecordNum, rootTimer.getText().toString(), subTimer.getText().toString()));
                }
                timeRecordNum++;
                Collections.sort(datas, new Comparator<TimeRecordElement>() {
                    @Override
                    public int compare(TimeRecordElement o1, TimeRecordElement o2) {
                        return o1.getNum() > o2.getNum() ? -1 : (o1.getNum() < o2.getNum() ? 1: 0);
                    }
                });
                adapter.notifyDataSetChanged();
            }else{
                // 초기화
                handler.removeMessages(0);
                timeReset();
                subTimer.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                if(timeRecordNum>1) sectionAnimator2.start();
                animator3.start();
                animator4.start();
                rootTimer.setText("00:00.00");
                datas.clear();
                adapter.notifyDataSetChanged();
            }
        }
    };

    public void timeReset(){
        rootTimeReset();
        subTimeReset();
    }
    public void rootTimeReset(){
        milliSecond = 0;
        second = 0;
        minute = 0;
        hour = 0;
    }
    public void subTimeReset(){
        subMilliSecond = 0;
        subSecond = 0;
        subMinute = 0;
        subHour = 0;
    }
    public String getTime(){
        long now = SystemClock.elapsedRealtime();
        long time = now - baseTime;
        second = (int) (time/1000);
        minute = second/60;
        hour = minute/60;
        minute %= 60;
        second %= 60;
        milliSecond = (int) (time%1000);
        milliSecond /= 10;
        String record;
        if(hour==0){
            record = String.format("%02d"+":"+"%02d"+"."+"%02d", minute, second, milliSecond);
        }else{
            record = String.format("%02d"+":"+"%02d"+":"+"%02d"+"."+"%02d", hour, minute, second, milliSecond);
        }
        return record;
    }
    public String getTime2(){
        long now = SystemClock.elapsedRealtime();
        if(subBaseTime==0) return "00:00.00";
        long time = now - subBaseTime;
        subSecond = (int) (time/1000);
        subMinute = subSecond/60;
        subHour = subMinute/60;
        subMinute %= 60;
        subSecond %= 60;
        subMilliSecond = (int) (time%1000);
        subMilliSecond /= 10;
        String record;
        if(hour==0){
            record = String.format("%02d"+":"+"%02d"+"."+"%02d", subMinute, subSecond, subMilliSecond);
        }else{
            record = String.format("%02d"+":"+"%02d"+":"+"%02d"+"."+"%02d", subHour, subMinute, subSecond, subMilliSecond);
        }
        return record;
    }
}
