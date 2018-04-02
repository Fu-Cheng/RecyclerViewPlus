package com.chengfu.recyclerviewplus.samples.headerandfooter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chengfu.recyclerviewplus.samples.R;
import com.chengfu.recyclerviewplus.samples.headerandfooter.fragment.SampleHeaderAndFooterGridFragment;
import com.chengfu.recyclerviewplus.samples.headerandfooter.fragment.SampleHeaderAndFooterListFragment;
import com.chengfu.recyclerviewplus.samples.headerandfooter.fragment.SampleHeaderAndFooterStaggeredFragment;

/**
 * Created by ChengFu on 2017/8/18.
 */

public class HeaderAndFooterActivity extends AppCompatActivity {
    public static final int FRAGMENT_COUNT = 3;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_and_footer);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new RecyclerFragmentPagerAdapter(getSupportFragmentManager()));
    }

    private class RecyclerFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public RecyclerFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SampleHeaderAndFooterListFragment.newInstance();
                case 1:
                    return SampleHeaderAndFooterGridFragment.newInstance();
                case 2:
                    return SampleHeaderAndFooterStaggeredFragment.newInstance();
                default:
                    return SampleHeaderAndFooterListFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
