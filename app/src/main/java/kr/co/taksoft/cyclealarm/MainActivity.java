package kr.co.taksoft.cyclealarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TabLayout tabLayout;
    private Page1Fragment_Alarm tab1;
    private Page2Fragment_WorldClock tab2;
    private Page3Fragment_Stopwatch tab3;
    private Page4Fragment_Timer tab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.framelayout);
        tabLayout = findViewById(R.id.tablayout);
        tab1 = new Page1Fragment_Alarm();
        tab2 = new Page2Fragment_WorldClock();
        tab3 = new Page3Fragment_Stopwatch();
        tab4 = new Page4Fragment_Timer();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.alarm_toolbar_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.world_clock_toolbar_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.stopwatch_toolbar_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.timer_toolbar_title));
        tabLayout.setSelectedTabIndicator(R.drawable.dot);
        transaction.add(R.id.framelayout, tab1).commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(position == 0){
                    transaction.replace(R.id.framelayout, tab1).commit();
                }else if(position == 1){
                    transaction.replace(R.id.framelayout, tab2).commit();
                }else if(position == 2){
                    transaction.replace(R.id.framelayout, tab3).commit();
                }else if(position == 3){
                    transaction.replace(R.id.framelayout, tab4).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
