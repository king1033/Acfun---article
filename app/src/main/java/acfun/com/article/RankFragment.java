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



    static final String[] typesStr = {"最多评论", "最新回复", "最新发布", "最多收藏"};
    static final int[] sorts = {2, 5, 4, 3};

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
            fragments = new ArrayList<>();
            for (String a : typesStr){
                tabs.addTab(tabs.newTab().setText(a));
            }
            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int a : sorts) {
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
