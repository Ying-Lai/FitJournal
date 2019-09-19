package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.Session;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;


public class CompletedExerciseAdapter extends ListAdapter<CompletedExerciseItem, CompletedExerciseAdapter.CompletedExerciseHolder> {

    // Adaptor fields
    private OnItemClickListener mOnClickListener;


    // Adaptor constructor
    public CompletedExerciseAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        mOnClickListener = listener;
    }

    private static final DiffUtil.ItemCallback<CompletedExerciseItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CompletedExerciseItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CompletedExerciseItem oldItem, @NonNull CompletedExerciseItem newItem) {
            return oldItem.getMId() == newItem.getMId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CompletedExerciseItem oldItem, @NonNull CompletedExerciseItem newItem) {
            return oldItem.compareListOfSessions(newItem.getListOfSessions());
        }
    };



    class CompletedExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        // View holder fields
        private ImageView mCompletedExerciseTypeIcon;
        private MaterialTextView mCompletedExerciseName;
        private MaterialTextView mCompletedExerciseDescription;
        private MaterialTextView mCompletedExerciseHasNote;
        private MaterialCardView mCardView;
        private View mDivider;

        // View holder constructor
        public CompletedExerciseHolder(View itemView) {
             super(itemView);
             mCompletedExerciseTypeIcon = itemView.findViewById(R.id.completed_exercise_type_icon_civ);
             mCompletedExerciseName = itemView.findViewById(R.id.completed_exercise_name_tv);
             mCompletedExerciseDescription = itemView.findViewById(R.id.completed_exercise_description_tv);
             mCompletedExerciseHasNote = itemView.findViewById(R.id.has_note_tv);
             mDivider = itemView.findViewById(R.id.divider);
             mCardView = itemView.findViewById(R.id.completed_exercise_cv);
             mCardView.setOnLongClickListener(this);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) mOnClickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mOnClickListener != null) {
                return mOnClickListener.onLongClick(view, getAdapterPosition());
            }
            return false;
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
        CompletedExerciseItem currentCompletedExerciseItem = getItem(position);
        String description = "";
        List<Session> listOfSessions = currentCompletedExerciseItem.getListOfSessions();
        if (currentCompletedExerciseItem.getExerciseType() == ExerciseType.CARDIO) {
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.ic_cardio_session);
            for (int i=0; i<listOfSessions.size(); i++) {
                if (i == listOfSessions.size() -1) {
                    description += listOfSessions.get(i).getDuration() + " min x " + listOfSessions.get(i).getIntensity()+"%";
                } else {
                    description += listOfSessions.get(i).getDuration() + " min x " + listOfSessions.get(i).getIntensity() + "%, ";
                }
            }
        } else if (currentCompletedExerciseItem.getExerciseType() == ExerciseType.STRENGTH) {
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.ic_strength_session);
            for (int i=0; i<listOfSessions.size(); i++) {
                if (i == listOfSessions.size() -1) {
                    description += listOfSessions.get(i).getReps() + " reps x " + listOfSessions.get(i).getWeight()+" lbs.";
                } else {
                    description += listOfSessions.get(i).getReps() + " reps x " + listOfSessions.get(i).getWeight() + " lbs, ";
                }
            }
        } else if (currentCompletedExerciseItem.getExerciseType() == ExerciseType.CALISTHENICS) {
            holder.mCompletedExerciseTypeIcon.setImageResource(R.drawable.ic_calistenics_session);
            for (int i=0; i<listOfSessions.size(); i++) {
                if (i == listOfSessions.size() -1) {
                    description += listOfSessions.get(i).getReps() + " reps";
                } else {
                    description += listOfSessions.get(i).getReps() + " reps, ";
                }
            }
        }
        if (currentCompletedExerciseItem.getNote() != null && !currentCompletedExerciseItem.getNote().equals("")) {
            holder.mDivider.setVisibility(View.VISIBLE);
            holder.mCompletedExerciseHasNote.setVisibility(View.VISIBLE);
        } else {
            holder.mDivider.setVisibility(View.GONE);
            holder.mCompletedExerciseHasNote.setVisibility((View.GONE));
        }
        CharSequence note = "Note: " + currentCompletedExerciseItem.getNote();
        SpannableStringBuilder formattedNote = new SpannableStringBuilder(note);
        formattedNote.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mCompletedExerciseHasNote.setText(formattedNote);
        holder.mCompletedExerciseName.setText(currentCompletedExerciseItem.getExerciseName());
        holder.mCompletedExerciseDescription.setText(description);
        currentCompletedExerciseItem.setChecked(false);
        holder.mCardView.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    // Other adaptor methods
    public CompletedExerciseItem getExerciseItem(int position) {
        return getItem(position);
    }

    // On click interface
    public interface OnItemClickListener {
        public void onClick(View view, int position);
        public boolean onLongClick(View view, int position);
    }
}
