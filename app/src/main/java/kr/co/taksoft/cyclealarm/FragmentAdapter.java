package kr.co.taksoft.cyclealarm;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Fragment fragments[] = new Fragment[4];

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = new Page1Fragment_Alarm();
        fragments[1] = new Page2Fragment_WorldClock();
        fragments[2] = new Page3Fragment_Stopwatch();
        fragments[3] = new Page4Fragment_Timer();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
