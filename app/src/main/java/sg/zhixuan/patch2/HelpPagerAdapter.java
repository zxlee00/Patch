package sg.zhixuan.patch2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HelpPagerAdapter extends FragmentStatePagerAdapter {
    int numTabs;

    public HelpPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HelpTabFragments.MatchUpHelpFragment tab1 = new HelpTabFragments.MatchUpHelpFragment();
                return tab1;
            case 1:
                HelpTabFragments.ChatsHelpFragment tab2 = new HelpTabFragments.ChatsHelpFragment();
                return tab2;
            case 2:
                HelpTabFragments.AppointmentsHelpFragment tab3 = new HelpTabFragments.AppointmentsHelpFragment();
                return tab3;
            case 3:
                HelpTabFragments.ProfileHelpFragment tab4 = new HelpTabFragments.ProfileHelpFragment();
                return tab4;
            case 4:
                HelpTabFragments.LanguageHelpFragment tab5 = new HelpTabFragments.LanguageHelpFragment();
                return tab5;
            case 5:
                HelpTabFragments.FriendsHelpFragment tab6 = new HelpTabFragments.FriendsHelpFragment();
                return tab6;
            case 6:
                HelpTabFragments.RequestsHelpFragment tab7 = new HelpTabFragments.RequestsHelpFragment();
                return tab7;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}