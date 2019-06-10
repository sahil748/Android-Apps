package com.example.assignment4.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.assignment4.helper.Constants;
import com.example.assignment4.R;
import com.example.assignment4.fragment.StudentDetailsFragment;
import com.example.assignment4.fragment.StudentsListFragment;
import com.example.assignment4.adapter.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentsListActivity extends AppCompatActivity implements StudentsListFragment.StudentListFragmentListener, StudentDetailsFragment.OnAddFragmentListener {
    /**
     * @param mPageAdapter is the object of HomePageAdapter
     * @param mFragmentsList for adding the various fragments in the pageadapter.
     * @param mViewPager is the object of ViewPager
     * @param mTabNames for giving the names to the tab titles
     * @param constant is the object of class constant declared in Util package for fetching constant values
     */

    private PagerAdapter mPageAdapter;
    private List<Fragment> mFragmentsList=new ArrayList<>();
    private ViewPager mViewPager;
    private ArrayList<String> mTabNames=new ArrayList<>();
    private TabLayout tabLayout;
    private int tabPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        tabLayout = findViewById(R.id.tab_layout);
        mTabNames.add(getString(R.string.tab_layout_list));
        mTabNames.add(getString(R.string.tab_layout_add));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //to get position of tab
                tabPosition = tab.getPosition();
                if (tabPosition == Constants.FRAGMENT_TWO) {
                    StudentDetailsFragment fragmentDetail =(StudentDetailsFragment) mFragmentsList.get(Constants.FRAGMENT_TWO);
                    fragmentDetail.clearEditText();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager = findViewById(R.id.studentListActivity_viewPager);
        addPagesToFragmentList();

        mPageAdapter = new HomePageAdapter(getSupportFragmentManager(),mFragmentsList,mTabNames);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(Constants.OFF_SCREEN_PAGE_LIMIT);
        mViewPager.setCurrentItem(Constants.FRAGMENT_ONE);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        tabLayout.setupWithViewPager(mViewPager);
    }
    /**
     * adds different fragments to an arraylist of fragments
     */

    public void addPagesToFragmentList(){
        StudentsListFragment fragment = new StudentsListFragment();
        fragment.instantiateListener(this);
        mFragmentsList.add(fragment);
        StudentDetailsFragment fragment2 = new StudentDetailsFragment();
        fragment2.instantiateAddListener(this);
        mFragmentsList.add(fragment2);
    }
    //clear and set data to edit test as per add or edit function is selected
    @Override
    public void studentFragment(Bundle bundle) {
        mViewPager.setCurrentItem(Constants.FRAGMENT_TWO);
        if (bundle.getString(Constants.KEY).equals(getString(R.string.value_add))) {
            StudentDetailsFragment fragment = (StudentDetailsFragment) mFragmentsList.get(Constants.FRAGMENT_TWO);
            fragment.clearEditText();
        } else if (bundle.getString(Constants.KEY).equals(getString(R.string.value_edit))) {
            StudentDetailsFragment fragment = (StudentDetailsFragment) mFragmentsList.get(Constants.FRAGMENT_TWO);
            fragment.setEditText(bundle);
        }
    }
    //this is the method of interface onAddFragmentListener declared in class AddStudentActivity
    @Override
    public void addFragmentListener(Bundle bundle) {
        StudentsListFragment studentFragment = (StudentsListFragment)mFragmentsList.get(0);
        mViewPager.setCurrentItem(Constants.FRAGMENT_ONE);
        studentFragment.sendData(bundle);
    }
}
