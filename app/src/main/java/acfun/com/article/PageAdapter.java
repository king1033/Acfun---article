package acfun.com.article;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4.
 */
public  class PageAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;

    public PageAdapter(FragmentManager fragmentManager, String[] titles, List<Fragment> fragments){
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.titles = titles;
        this.fragments = fragments;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
