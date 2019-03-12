package com.feriramara.mytodo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.feriramara.mytodo.adaptors.SectionPageAdapter;
import com.feriramara.mytodo.fragments.Fragment_Someday;
import com.feriramara.mytodo.fragments.Fragment_Today;
import com.feriramara.mytodo.fragments.Fragment_Tomorrow;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class MainFragment extends Fragment {

    private static final String TAG = "MainActivity";


    private NoteViewModel noteViewModel;

    private SectionPageAdapter adapter;
    private ViewPager viewPager;
    static int todaySize;
    static int tomorrowSize;
    static int somedaySize;
    private int pagerState;


    public ImageView imageView;

    public static boolean sortByDate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_test, container, false);

        setHasOptionsMenu(true);


        Log.d(TAG, "MainFragment: " + sortByDate);


        imageView = view.findViewById(R.id.lazy_cat);

        adapter = new SectionPageAdapter(getActivity().getSupportFragmentManager(), getContext());

        viewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(viewPager);


        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupViewPager(viewPager);

                    }
                }, 400);


            }
        });


        tomorrowNotesToToday();

        return view;
    }

    public void setupViewPager(final ViewPager viewPager) {

        adapter = new SectionPageAdapter(getActivity().getSupportFragmentManager(), getContext());



        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getTodayNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                todaySize = notes.size();


            }
        });

        noteViewModel.getTomorrowNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                tomorrowSize = notes.size();



            }
        });

        noteViewModel.getSomedayNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                somedaySize = notes.size();

            }
        });

        adapter.addFragments(new Fragment_Today(), String.valueOf(todaySize));
        adapter.addFragments(new Fragment_Tomorrow(), String.valueOf(tomorrowSize));
        adapter.addFragments(new Fragment_Someday(), String.valueOf(somedaySize));


        pagerState = viewPager.getCurrentItem();


        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pagerState);




    }

    public void tomorrowNotesToToday() {
        noteViewModel.getTomorrowNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                int i = 0;
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //String date = simpleDateFormat.format(new Date());

                for (i = 0; i < notes.size(); i++) {

                    Date date = Calendar.getInstance().getTime();

                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String newDate = df.format(date);

                    String oldDate = df.format(notes.get(i).getDate());

                    if (!oldDate.equals(newDate)) {


                        String title = notes.get(i).getTitle();
                        Date ddate = notes.get(i).getDate();
                        String priority = notes.get(i).getPriority();
                        int section = 1;

                        noteViewModel.delete(notes.get(i));

                        Note note = new Note(title, ddate, priority, section);
                        noteViewModel.insert(note);
                    }

                    Log.d(TAG, "onChanged: 1 = " + oldDate);
                    Log.d(TAG, "onChanged: 2 = " + newDate);
                }



            }
        });
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sample, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sort_by_date) {
            sortByDate = true;
            Toast.makeText(getContext(), "Sorted by Date", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onOptionsItemSelected: " + sortByDate);
            setupViewPager(viewPager);
            return true;
        } else if (id == R.id.action_sort_by_prior) {
            sortByDate = false;
            Toast.makeText(getContext(), "Sorted by Priority", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onOptionsItemSelected: " + sortByDate);
            setupViewPager(viewPager);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
