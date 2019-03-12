package com.feriramara.mytodo.adaptors;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feriramara.mytodo.Note;
import com.feriramara.mytodo.R;
import com.feriramara.mytodo.Settings_fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdaptor extends ListAdapter<Note, NoteAdaptor.NoteHolder> {




    private static final String TAG = "NoteAdaptor";


    Boolean switchPref;



    private OnItemClickListener listener;


    public NoteAdaptor() {
        super(DIFF_CALLBACK);
    }



    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }




    };



    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        PreferenceManager.setDefaultValues(parent.getContext(), R.xml.preferences_layout, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        switchPref = sharedPreferences.getBoolean(Settings_fragment.PREF_US_MODE, false);


        return new NoteHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {


        Note currentNote = getItem(position);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (switchPref) {
            df = new SimpleDateFormat("MM/dd/yyyy");
        } else {
            df = new SimpleDateFormat("dd/MM/yyyy");
        }

        Date today = currentNote.getDate();


        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescrition.setText(df.format(today));
        holder.textViewPriority.setText(currentNote.getPriority());

        float[] hsl = {0f,.65f,0.5f};
        hsl[0] = Float.parseFloat(currentNote.getPriority())*36;
        int color = ColorUtils.HSLToColor(hsl);
        holder.textViewPriority.setTextColor(color);

        Log.d(TAG, "onBindViewHolder: " + hsl[0]);


    }






    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {




        private TextView textViewTitle;
        private TextView textViewDescrition;
        private TextView textViewPriority;

        public NoteHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescrition = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.number);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);


                    }
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}











