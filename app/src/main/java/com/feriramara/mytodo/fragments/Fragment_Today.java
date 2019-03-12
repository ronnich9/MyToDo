package com.feriramara.mytodo.fragments;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feriramara.mytodo.MainFragment;
import com.feriramara.mytodo.MySampleFabFragment;
import com.feriramara.mytodo.Note;
import com.feriramara.mytodo.adaptors.NoteAdaptor;
import com.feriramara.mytodo.NoteViewModel;
import com.feriramara.mytodo.R;
import com.feriramara.mytodo.Settings_fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tyrantgit.explosionfield.ExplosionField;


import static android.content.Context.VIBRATOR_SERVICE;

public class Fragment_Today extends Fragment{

    private static final String TAG = "Fragment_Today";
    private NoteViewModel noteViewModel;
    private int quantity = 0;
    MediaPlayer mediaPlayer;
    MySampleFabFragment dialogFrag;
    public boolean sortByDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_today, container, false);

        sortByDate = MainFragment.sortByDate;

        PreferenceManager.setDefaultValues(getContext(), R.xml.preferences_layout, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final Boolean switchPref = sharedPreferences.getBoolean(Settings_fragment.PREF_VIBRATION_MODE, false);


        mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.error);


        final ImageView imageView = v.findViewById(R.id.lazy_cat);


        final ExplosionField ef = ExplosionField.attach2Window(getActivity());

        final FloatingActionButton buttonAddNote = v.findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFrag = MySampleFabFragment.newInstance();
                dialogFrag.setParentFab(buttonAddNote);
                dialogFrag.show(getChildFragmentManager(), dialogFrag.getTag());


            }
        });

        final RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final NoteAdaptor adaptor = new NoteAdaptor();
        recyclerView.setAdapter(adaptor);



        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        Log.d(TAG, "onOptions: 3" + sortByDate);

        if (sortByDate) {
            noteViewModel.getTodayNotesByDate().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    adaptor.submitList(notes);
                    quantity = notes.size();
                }
            });

        } else {

            noteViewModel.getTodayNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    adaptor.submitList(notes);
                    quantity = notes.size();
                }
            });
        }

        if (quantity != 0) {
            imageView.setVisibility(View.INVISIBLE);
            Log.d(TAG, "quantity: " + quantity);
        }



        adaptor.setOnItemClickListener(new NoteAdaptor.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                if (recyclerView.findViewHolderForAdapterPosition(position).itemView != null) {
                    ef.explode(recyclerView.findViewHolderForAdapterPosition(position).itemView);

                    noteViewModel.delete(adaptor.getNoteAt(position));
                    if (switchPref) {
                        if (Build.VERSION.SDK_INT >= 26) {
                            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(50);
                        }
                    }


                }



            }
        });


        return v;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getFragmentManager().executePendingTransactions();
        if (dialogFrag != null &&  dialogFrag.isAdded()) {
            dialogFrag.dismiss();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}


