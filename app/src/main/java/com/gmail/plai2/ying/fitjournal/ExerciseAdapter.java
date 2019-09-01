package com.gmail.plai2.ying.fitjournal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<Exercise> exercises = new ArrayList<>();

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private ImageView exerciseImage;
        private TextView exerciseName;
        private TextView exerciseDescription;

        public ExerciseHolder(View itemView) {
             super(itemView);
             exerciseImage = itemView.findViewById(R.id.exercise_image); // Placeholders +3
             exerciseName = itemView.findViewById(R.id.exercise_name);
             exerciseDescription = itemView.findViewById(R.id.exercise_description);
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
        holder.exerciseImage.setImageResource(R.drawable.ic_cardio_session); // Placeholders +3
        holder.exerciseName.setText(currentExercise.getExerciseName());
        holder.exerciseDescription.setText(currentExercise.getTypeOfExercise());
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
