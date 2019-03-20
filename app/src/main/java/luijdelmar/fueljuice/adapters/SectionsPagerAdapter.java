package luijdelmar.fueljuice.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import luijdelmar.fueljuice.fragments.CentralFragment;
import luijdelmar.fueljuice.fragments.LeftFragment;
import luijdelmar.fueljuice.fragments.RightFragment;

/**
 * If this becomes too memory intensive, it
 * may be best to switch to a
 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
 */

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LeftFragment.newInstance(position + 1);
        } else if (position == 1) {
            return CentralFragment.newInstance(position + 1);
        } else {
            return RightFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Left";
            case 1:
                return "Main";
            case 2:
                return "Right";
        }
        return null;
    }
}
