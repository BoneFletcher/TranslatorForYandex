package frosteagle.com.yandextranslator.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import frosteagle.com.yandextranslator.fragmentsHistory.FavoritesFragment;
import frosteagle.com.yandextranslator.fragmentsHistory.HistoryFragment;

/**
 * Created by FrostEagle on 20.04.2017.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new HistoryFragment();
            case 1:
                return new FavoritesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
