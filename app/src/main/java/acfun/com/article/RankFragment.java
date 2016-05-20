package acfun.com.article;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RankFragment extends Fragment {



    static final String[] typesStr = {"综合", "工作·情感", "动漫文化", "漫画·小说","游戏"};
    static final int[] channels = {110, 73, 74, 75, 164};

    private List<Fragment> fragments;

    private FloatingActionButton fab;
    private RankListFragment rankListFragment;

    private TabLayout tabs;
    private ViewPager viewPager;
    private Handler handler;
    private PageAdapter adapter;
    private FragmentTransaction transaction;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_rank, container, false);
            tabs = (TabLayout) mView.findViewById(R.id.tabs);
            viewPager = (ViewPager)mView.findViewById(R.id.viewpager);

            handler = new Handler();
            for (String a : typesStr){
                tabs.addTab(tabs.newTab().setText(a));
            }
            new Thread(new Runnable() {
                @Override
                public void run() {

                    fragments = new ArrayList<>();
                    for (int a : channels) {
                        rankListFragment = RankListFragment.newInstance(a);
                        fragments.add(rankListFragment);
                    }
                    adapter = new PageAdapter(getActivity().getSupportFragmentManager(), typesStr, fragments);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setAdapter(adapter);
                            tabs.setupWithViewPager(viewPager);
                        }
                    });
                }
            }).start();

        }
        return mView;
    }
}
