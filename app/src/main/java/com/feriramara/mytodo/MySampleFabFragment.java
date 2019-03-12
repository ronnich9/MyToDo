package com.feriramara.mytodo;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.feriramara.mytodo.fragments.Fragment_Someday;
import com.feriramara.mytodo.fragments.Fragment_Today;
import com.feriramara.mytodo.fragments.Fragment_Tomorrow;

import java.util.Calendar;
import java.util.Date;

import me.tankery.lib.circularseekbar.CircularSeekBar;


public class MySampleFabFragment extends AAH_FabulousFragment {

    private static final String TAG = "MySampleFabFragment";

    private EditText editTextTitle;
    private TextView textView;
    private float myProgress;

    public String title;

    public static MySampleFabFragment newInstance() {
        MySampleFabFragment f = new MySampleFabFragment();
        return f;

    }

    @Override

    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_dialog, null);
        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        editTextTitle = contentView.findViewById(R.id.new_edittext);
        textView = contentView.findViewById(R.id.seek_bar_text);

        CircularSeekBar seekBar = contentView.findViewById(R.id.seek_bar);
        seekBar.setMax(9.9f);

        seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                if (progress < 0) {
                    circularSeekBar.setProgress(0);
                }

                textView.setText(String.format("%.2f", progress));
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                myProgress = seekBar.getProgress();

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });


        contentView.findViewById(R.id.button).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        contentView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTextTitle.getText().toString();
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //String date = simpleDateFormat.format(new Date());
                Date newDate = Calendar.getInstance().getTime();
                if (getParentFragment() instanceof Fragment_Today) {
                    String priority = String.format("%.1f", myProgress);
                    Note note = new Note(title, newDate, priority, 1);
                    closeFilter(note);

                } else if (getParentFragment() instanceof Fragment_Tomorrow) {
                    String priority = String.format("%.1f", myProgress);
                    Note note = new Note(title, newDate, priority, 2);
                    closeFilter(note);

                    } else if (getParentFragment() instanceof Fragment_Someday) {
                    String priority = String.format("%.1f", myProgress);
                    Note note = new Note(title, newDate, priority, 3);
                    closeFilter(note);
                    }



            }
        });

        contentView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter("closed");
            }
        });


        //params to set
        setAnimationDuration(250); //optional; default 500ms
        setPeekHeight(500); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
//        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
//        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
//        setViewPager(vp_types); //optional; if you use viewpager that has scrollview
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }



}