package koemdzhiev.com.blinkmessage.adapters;

/**
 * Created by koemdzhiev on 27/05/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import koemdzhiev.com.blinkmessage.R;
import koemdzhiev.com.blinkmessage.ui.FriendsFragment;
import koemdzhiev.com.blinkmessage.ui.InboxFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    protected Context mContext;
    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:return new InboxFragment();
            case 1:return new FriendsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }

    public int getIcon(int position){
        switch (position) {
            case 0:
                return R.mipmap.ic_tab_inbox;
            case 1:
                return R.mipmap.ic_tab_friends;
        }

        return R.mipmap.ic_tab_inbox;
    }
}
