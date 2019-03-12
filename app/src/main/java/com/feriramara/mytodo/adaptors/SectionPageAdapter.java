package com.feriramara.mytodo.adaptors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import com.feriramara.mytodo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPageAdapter extends FragmentPagerAdapter {

    private static final String TAG = "SectionPageAdapter";


    private final List<Fragment> mFragmentsList = new ArrayList<>();
    private String tabTitles[] = new String[] { "Today", "Tomorrow", "Someday"};
    private Context context;
    private List<String> miniArray = new ArrayList<>();

    private int[] imageResId = {
            R.drawable.ic_zero,
            R.drawable.ic_one,
            R.drawable.ic_two,
            R.drawable.ic_three,
            R.drawable.ic_four,
            R.drawable.ic_five,
            R.drawable.ic_six,
            R.drawable.ic_seven,
            R.drawable.ic_eight,
            R.drawable.ic_nine,
            R.drawable.ic_ten,
            R.drawable.ic_endless

    };

    public void addFragments(Fragment fragment, String number) {
        mFragmentsList.add(fragment);
        miniArray.add(number);

    }


    public SectionPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        Drawable image;

        if (Integer.parseInt(miniArray.get(position)) > 10) {
            image = context.getResources().getDrawable(imageResId[11]);
        } else {
            // Generate title based on item position
            image = context.getResources().getDrawable(imageResId[Integer.parseInt(miniArray.get(position))]);
        }


        Log.d(TAG, "getPageTitle: " + Integer.parseInt(miniArray.get(position)));

        image.setBounds(0,0, image.getIntrinsicWidth(), image.getIntrinsicHeight() );
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString(tabTitles[position] + "   " );
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, tabTitles[position].length() + 2, tabTitles[position].length()+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }
}
