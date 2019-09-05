package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.Exercise;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<Exercise> exercises = new ArrayList<>();

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private CircleImageView mExerciseTypeIcon;
        private TextView mExerciseName;
        private TextView mExerciseDescription;

        public ExerciseHolder(View itemView) {
             super(itemView);
             mExerciseTypeIcon = itemView.findViewById(R.id.exercise_type_icon_civ);
             mExerciseName = itemView.findViewById(R.id.exercise_name_tv);
             mExerciseDescription = itemView.findViewById(R.id.exercise_description_tv);
        }
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        String description = "";
        if (currentExercise.getExerciseType() == Exercise.ExerciseType.CARDIO) {
            holder.mExerciseTypeIcon.setImageResource(R.drawable.runner);
            String intensity = "";
            switch(currentExercise.getIntensity()) {
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
            description = "Duration: " + currentExercise.getDuration() + " Minutes, Intensity: " + intensity;
        } else if (currentExercise.getExerciseType() == Exercise.ExerciseType.STRENGTH) {
            holder.mExerciseTypeIcon.setImageResource(R.drawable.weight_lifting);
            String reps = "";
            int minRep = currentExercise.getMinRep(currentExercise.getListOfSets());
            int maxRep = currentExercise.getMaxRep(currentExercise.getListOfSets());
            if (minRep != maxRep) {
                reps = minRep + " - " + maxRep;
            } else {
                reps = minRep + "";
            }
            description = currentExercise.getListOfSets().size() + " Sets of " + reps + " Reps";
        }
        holder.mExerciseName.setText(currentExercise.getExerciseName());
        holder.mExerciseDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }
}
