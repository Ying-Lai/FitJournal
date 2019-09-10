package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompletedExerciseAdapter extends RecyclerView.Adapter<CompletedExerciseAdapter.CompletedExerciseHolder> {

    private List<CompletedExerciseItem> completedExerciseItems = new ArrayList<>();

    class CompletedExerciseHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCompletedExerciseTypeIcon;
        private TextView mCompletedExerciseName;
        private TextView mCompletedExerciseDescription;

        public CompletedExerciseHolder(View itemView) {
             super(itemView);
             mCompletedExerciseTypeIcon = itemView.findViewById(R.id.completed_exercise_type_icon_civ);
             mCompletedExerciseName = itemView.findViewById(R.id.completed_exercise_name_tv);
             mCompletedExerciseDescription = itemView.findViewById(R.id.completed_exercise_description_tv);
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
        CompletedExerciseItem currentCompletedExerciseItem = completedExerciseItems.get(position);
        String description = "";
        if (currentCompletedExerciseItem.getExerciseType() == CompletedExerciseItem.ExerciseType.CARDIO) {
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
        } else if (currentCompletedExerciseItem.getExerciseType() == CompletedExerciseItem.ExerciseType.STRENGTH) {
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
        return completedExerciseItems.size();
    }

    public void setCompletedExerciseItems(List<CompletedExerciseItem> completedExerciseItems) {
        this.completedExerciseItems = completedExerciseItems;
        notifyDataSetChanged();
    }
}
