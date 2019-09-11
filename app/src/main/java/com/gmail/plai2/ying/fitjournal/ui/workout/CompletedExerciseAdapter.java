package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;

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
        private CircleImageView mCompletedExerciseTypeIcon;
        private TextView mCompletedExerciseName;
        private TextView mCompletedExerciseDescription;

        // View holder constructor
        public CompletedExerciseHolder(View itemView) {
             super(itemView);
             mCompletedExerciseTypeIcon = itemView.findViewById(R.id.completed_exercise_type_icon_civ);
             mCompletedExerciseName = itemView.findViewById(R.id.completed_exercise_name_tv);
             mCompletedExerciseDescription = itemView.findViewById(R.id.completed_exercise_description_tv);
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
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.runner);
            String intensity = "";
            switch(currentCompletedExerciseItem.getIntensity()) {
                case LOW:
                    intensity = "Low";
                    break;
                case MEDIUM:
                    intensity = "Medium";
                    break;
                case HIGH:
                    intensity = "High";
                    break;
            }
            description = "Duration: " + currentCompletedExerciseItem.getDuration() + " Minutes, Intensity: " + intensity;
        } else if (currentCompletedExerciseItem.getExerciseType() == ExerciseType.STRENGTH) {
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.weight_lifting);
            String reps = "";
            int minRep = currentCompletedExerciseItem.getMinRep(currentCompletedExerciseItem.getListOfSets());
            int maxRep = currentCompletedExerciseItem.getMaxRep(currentCompletedExerciseItem.getListOfSets());
            if (minRep != maxRep) {
                reps = minRep + " - " + maxRep;
            } else {
                reps = minRep + "";
            }
            description = currentCompletedExerciseItem.getListOfSets().size() + " Sets of " + reps + " Reps";
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
