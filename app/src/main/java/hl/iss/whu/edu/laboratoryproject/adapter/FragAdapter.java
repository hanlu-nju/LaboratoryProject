package hl.iss.whu.edu.laboratoryproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

import hl.iss.whu.edu.laboratoryproject.ui.fragment.StudyInfoFragment;

/**
 * Created by mrwen on 2017/3/5.
 */

public class FragAdapter extends FragmentPagerAdapter {

    private ArrayList<StudyInfoFragment> fragments;


    public FragAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragAdapter(FragmentManager fm, ArrayList<StudyInfoFragment> fragments) {
        super(fm);
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
}