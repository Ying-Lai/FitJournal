package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CardioSession;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompletedExerciseAdapter extends RecyclerView.Adapter<CompletedExerciseAdapter.CompletedExerciseHolder> {

    // Adaptor fields
    private List<CompletedExerciseItem> mCompletedExerciseItems;
    private OnItemClickListener mOnClickListener;

    // Adaptor constructor
    public CompletedExerciseAdapter(List<CompletedExerciseItem> completedExerciseItems, OnItemClickListener listener) {

        this.mCompletedExerciseItems = completedExerciseItems;
        this.mOnClickListener = listener;
    }

    class CompletedExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // View holder fields
        private ImageView mCompletedExerciseTypeIcon;
        private MaterialTextView mCompletedExerciseName;
        private MaterialTextView mCompletedExerciseDescription;
        private ImageView mCompletedExerciseHasNote;

        // View holder constructor
        public CompletedExerciseHolder(View itemView) {
             super(itemView);
             mCompletedExerciseTypeIcon = itemView.findViewById(R.id.completed_exercise_type_icon_civ);
             mCompletedExerciseName = itemView.findViewById(R.id.completed_exercise_name_tv);
             mCompletedExerciseDescription = itemView.findViewById(R.id.completed_exercise_description_tv);
             mCompletedExerciseHasNote = itemView.findViewById(R.id.has_note_iv);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) mOnClickListener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public CompletedExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_completed_exercise, parent, false);
        return new CompletedExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedExerciseHolder holder, int position) {
        CompletedExerciseItem currentCompletedExerciseItem = mCompletedExerciseItems.get(position);
        String description = "";
        if (currentCompletedExerciseItem.getExerciseType() == ExerciseType.CARDIO) {
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.ic_cardio_session);
            List<CardioSession> listOfCardioSessions = currentCompletedExerciseItem.getListOfCardioSessions();
            for (int i=0; i<listOfCardioSessions.size(); i++) {
                if (i == listOfCardioSessions.size() -1) {
                    description += listOfCardioSessions.get(i).getDuration() + " min @ " + listOfCardioSessions.get(i).getIntensity()+"%";
                } else {
                    description += listOfCardioSessions.get(i).getDuration() + " min @ " + listOfCardioSessions.get(i).getIntensity() + "%, ";
                }
            }
        } else if (currentCompletedExerciseItem.getExerciseType() == ExerciseType.STRENGTH) {
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.ic_strength_session);
            List<Set> listOfSet = currentCompletedExerciseItem.getListOfSets();
            for (int i=0; i<listOfSet.size(); i++) {
                if (i == listOfSet.size() -1) {
                    description += listOfSet.get(i).getReps() + " reps @ " + listOfSet.get(i).getWeight()+"lbs.";
                } else {
                    description += listOfSet.get(i).getReps() + " reps @ " + listOfSet.get(i).getWeight() + "lbs, ";
                }
            }
        } else if (currentCompletedExerciseItem.getExerciseType() == ExerciseType.CALISTHENICS) {
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.ic_calistenics_session);
            List<Set> listOfSet = currentCompletedExerciseItem.getListOfSets();
            for (int i=0; i<listOfSet.size(); i++) {
                if (i == listOfSet.size() -1) {
                    description += listOfSet.get(i).getReps() + " reps";
                } else {
                    description += listOfSet.get(i).getReps() + " reps, ";
                }
            }
        }
        if (currentCompletedExerciseItem.getNote() != null && !currentCompletedExerciseItem.getNote().equals("")) {
            holder.mCompletedExerciseHasNote.setVisibility(View.VISIBLE);
        } else {
            holder.mCompletedExerciseHasNote.setVisibility((View.INVISIBLE));
        }
        holder.mCompletedExerciseName.setText(currentCompletedExerciseItem.getExerciseName());
        holder.mCompletedExerciseDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return mCompletedExerciseItems.size();
    }

    // Other adaptor methods
    public CompletedExerciseItem getExerciseItem(int position) {
        return mCompletedExerciseItems.get(position);
    }

    public void setCompletedExerciseItems(List<CompletedExerciseItem> completedExerciseItems) {
        this.mCompletedExerciseItems = completedExerciseItems;
        notifyDataSetChanged();
    }

    // On click interface
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}
