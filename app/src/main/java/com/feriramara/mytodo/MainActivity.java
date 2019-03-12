package com.feriramara.mytodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.feriramara.mytodo.fragments.Feedback_fragment;
import com.feriramara.mytodo.fragments.Fragment_Tomorrow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AAH_FabulousFragment.Callbacks {

    private static final String TAG = "MainActivity";


    private NoteViewModel noteViewModel;


    DuoDrawerLayout duoDrawerLayout;

    private MyMenuAdapter mMenuAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       final ArrayList<String> mTitles = new ArrayList<>();

        mTitles.add("ToDo");
        mTitles.add("Settings");
        mTitles.add("Rate us");







        duoDrawerLayout = findViewById(R.id.drawe_layout);
        final DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this, duoDrawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        duoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();



        final DuoMenuView duoMenuView = (DuoMenuView) findViewById(R.id.drawer_menu_view);
        mMenuAdapter = new MyMenuAdapter(mTitles);
        duoMenuView.setAdapter(mMenuAdapter);

        goToFragment(new MainFragment(), false);
        mMenuAdapter.setViewSelected(0, true);

        toolbar.setTitle(mTitles.get(0));

        duoMenuView.setOnMenuClickListener(new DuoMenuView.OnMenuClickListener() {
            @Override
            public void onFooterClicked() {
                //Toast.makeText(MainActivity.this, "footer", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FooterActivity.class);
                startActivity(intent);

            }

            @Override
            public void onHeaderClicked() {
                //Toast.makeText(MainActivity.this, "header", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOptionClicked(int position, Object objectClicked) {

                toolbar.setTitle(mTitles.get(position));


                mMenuAdapter.setViewSelected(position, true);
                switch (position) {
                    case 0:
                        goToFragment(new MainFragment(), false);
                        break;
                    case 1:
                        goToFragment(new Settings_fragment(), false);
                        break;
                    case 2:
                        goToFragment(new Feedback_fragment(), false);
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "pos 4", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(MainActivity.this, "pos 5", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        goToFragment(new Fragment_Tomorrow(), false);
                        break;
                }

                duoDrawerLayout.closeDrawer();

            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.replace(R.id.container_2, fragment).commit();
    }




    @Override
    public void onResult(Object result) {

        //Log.d("k9res", "onResult: " + result.toString());
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //Toast.makeText(this, "result for nothing1", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onResult: " + result.toString());
        } else if (result.toString().equalsIgnoreCase("closed")) {

        } else {


            Note myNote = (Note) result;
            noteViewModel.insert(myNote);
            //Toast.makeText(this, "result for nothing3", Toast.LENGTH_SHORT).show();


        }
    }



}


