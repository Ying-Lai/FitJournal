package com.gmail.plai2.ying.fitjournal.ui.workout.session;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.Session;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class SessionAdapter extends ListAdapter<Session, SessionAdapter.SessionHolder> {

    // Adapter Fields
    private OnItemLongClickListener mListener;
    private ExerciseType mExerciseType;

    // Adapter constructor
    public SessionAdapter(ExerciseType exerciseType, OnItemLongClickListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
        mExerciseType = exerciseType;
    }

    private static final DiffUtil.ItemCallback<Session> DIFF_CALLBACK = new DiffUtil.ItemCallback<Session>() {

        @Nullable
        @Override
        public Object getChangePayload(@NonNull Session oldItem, @NonNull Session newItem) {
            return super.getChangePayload(oldItem, newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull Session oldItem, @NonNull Session newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Session oldItem, @NonNull Session newItem) {
            return oldItem.compareSession(newItem);
        }
    };

    class SessionHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        // View holder fields
        private TextInputEditText mRepTIET;
        private TextInputEditText mWeightTIET;
        private TextInputEditText mDurationTIET;
        private TextInputEditText mIntensityTIET;
        private MaterialCardView mCardView;
        private FirstEditTextListener mFirstEditTextListener;
        private SecondEditTextListener mSecondEditTextListener;


        // View holder constructor
        public SessionHolder(View itemView, FirstEditTextListener firstEditTextListener, SecondEditTextListener secondEditTextListener) {
            super(itemView);
            // Initialize variables based on exercise type
            switch (mExerciseType) {
                case CALISTHENICS:
                    mFirstEditTextListener = firstEditTextListener;
                    mRepTIET = itemView.findViewById(R.id.calisthenics_rep_tiet);
                    mRepTIET.addTextChangedListener(mFirstEditTextListener);
                    mCardView = itemView.findViewById(R.id.calisthenics_cv);
                    mCardView.setOnLongClickListener(this);
                    break;
                case CARDIO:
                    mFirstEditTextListener = firstEditTextListener;
                    mSecondEditTextListener = secondEditTextListener;
                    mDurationTIET = itemView.findViewById(R.id.cardio_duration_tiet);
                    mIntensityTIET = itemView.findViewById(R.id.cardio_intensity_tiet);
                    mDurationTIET.addTextChangedListener(mFirstEditTextListener);
                    mIntensityTIET.addTextChangedListener(mSecondEditTextListener);
                    mCardView = itemView.findViewById(R.id.cardio_cv);
                    mCardView.setOnLongClickListener(this);
                    break;
                case STRENGTH:
                    mFirstEditTextListener = firstEditTextListener;
                    mSecondEditTextListener = secondEditTextListener;
                    mRepTIET = itemView.findViewById(R.id.strength_rep_tiet);
                    mWeightTIET = itemView.findViewById(R.id.strength_weight_tiet);
                    mRepTIET.addTextChangedListener(mFirstEditTextListener);
                    mWeightTIET.addTextChangedListener(mSecondEditTextListener);
                    mCardView = itemView.findViewById(R.id.strength_cv);
                    mCardView.setOnLongClickListener(this);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mListener != null) {
                return mListener.onLongClick(view, getAdapterPosition());
            }
            return false;
        }
    }

    @NonNull
    @Override
    public SessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resource;
        switch (mExerciseType) {
            case CARDIO:
                resource = R.layout.item_cardio_session;
                break;
            case STRENGTH:
                resource = R.layout.item_strength_session_set;
                break;
            default:
                resource = R.layout.item_calisthenics_session_set;
                break;
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);
        return new SessionHolder(itemView, new FirstEditTextListener(), new SecondEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull SessionHolder holder, int position) {
        // Update position for text listener
        holder.mFirstEditTextListener.updatePosition(holder.getAdapterPosition());
        if (mExerciseType == ExerciseType.CARDIO || mExerciseType == ExerciseType.STRENGTH) {
            holder.mSecondEditTextListener.updatePosition(holder.getAdapterPosition());
        }
        // Set values for view
        Session currentSession = getItem(holder.getAdapterPosition());
        if (!currentSession.isEmpty()) {
            switch (mExerciseType) {
                case CALISTHENICS:
                    String calisthenicsReps = currentSession.getReps() + "";
                    holder.mRepTIET.setText(calisthenicsReps);
                    break;
                case CARDIO:
                    String duration = currentSession.getDuration() + "";
                    holder.mDurationTIET.setText(duration);
                    String intensity = currentSession.getIntensity() + "";
                    holder.mIntensityTIET.setText(intensity);
                    break;
                case STRENGTH:
                    String strengthReps = currentSession.getReps() + "";
                    holder.mRepTIET.setText(strengthReps);
                    String weight = currentSession.getWeight() + "";
                    holder.mWeightTIET.setText(weight);
                    break;
            }
        } else {
            switch (mExerciseType) {
                case CALISTHENICS:
                    holder.mRepTIET.getText().clear();
                    break;
                case CARDIO:
                    if (currentSession.getDuration() <1) {
                        holder.mDurationTIET.getText().clear();
                    }
                    if (currentSession.getIntensity() <1) {
                        holder.mIntensityTIET.getText().clear();
                    }
                    break;
                case STRENGTH:
                    if (currentSession.getReps() <1) {
                        holder.mRepTIET.getText().clear();
                    }
                    if (currentSession.getWeight() <1) {
                        holder.mWeightTIET.getText().clear();
                    }
                    break;
            }
        }
        // Request focus on initial exercise creation or new set
        if (holder.getAdapterPosition() == getCurrentList().size()-1) {
            switch (mExerciseType) {
                case STRENGTH:
                case CALISTHENICS:
                    holder.mRepTIET.requestFocus();
                    break;
                case CARDIO:
                    holder.mDurationTIET.requestFocus();
                    break;
            }
        }
        currentSession.setChecked(false);
        holder.mCardView.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    // Text listener classes for reps and weight
    private class FirstEditTextListener implements TextWatcher {

        private int mPosition;

        public void updatePosition(int position) {
            mPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.length() == 0) {
                getItem(mPosition).setEmpty(true);
                switch (mExerciseType) {
                    case CALISTHENICS:
                    case STRENGTH:
                        getItem(mPosition).setReps(-1);
                        break;
                    case CARDIO:
                        getItem(mPosition).setDuration(-1);
                        break;
                }
            } else {
                switch (mExerciseType) {
                    case CALISTHENICS:
                        getItem(mPosition).setReps(Integer.parseInt(charSequence.toString()));
                        getItem(mPosition).setEmpty(false);
                        break;
                    case STRENGTH:
                        getItem(mPosition).setReps(Integer.parseInt(charSequence.toString()));
                        if(getItem(mPosition).getWeight() > 0) getItem(mPosition).setEmpty(false);
                        break;
                    case CARDIO:
                        getItem(mPosition).setDuration(Integer.parseInt(charSequence.toString()));
                        if(getItem(mPosition).getIntensity() > 0) getItem(mPosition).setEmpty(false);
                        break;
                }

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class SecondEditTextListener implements TextWatcher {

        private int mPosition;

        public void updatePosition(int position) {
            mPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.length() == 0) {
                getItem(mPosition).setEmpty(true);
                switch (mExerciseType) {
                    case STRENGTH:
                        getItem(mPosition).setWeight(-1);
                        break;
                    case CARDIO:
                        getItem(mPosition).setIntensity(-1);
                        break;
                }
            } else {
                switch (mExerciseType) {
                    case STRENGTH:
                        getItem(mPosition).setWeight(Integer.parseInt(charSequence.toString()));
                        if(getItem(mPosition).getReps() > 0) getItem(mPosition).setEmpty(false);
                        break;
                    case CARDIO:
                        getItem(mPosition).setIntensity(Integer.parseInt(charSequence.toString()));
                        if(getItem(mPosition).getDuration() > 0) getItem(mPosition).setEmpty(false);
                        break;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    // Other adaptor methods
    public Session getSessionItem(int position) {
        return getItem(position);
    }

    // On click interface
    public interface OnItemLongClickListener {
        public boolean onLongClick(View view, int position);
    }
}