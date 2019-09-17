package com.gmail.plai2.ying.fitjournal.ui.workout.cardio_session;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CardioSession;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class CardioSessionAdapter extends ListAdapter<CardioSession, CardioSessionAdapter.CardioSessionHolder> {

    // Adapter fields
    private OnItemLongClickListener mListener;

    public CardioSessionAdapter(OnItemLongClickListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
    }

    private static final DiffUtil.ItemCallback<CardioSession> DIFF_CALLBACK = new DiffUtil.ItemCallback<CardioSession>() {
        @Override
        public boolean areItemsTheSame(@NonNull CardioSession oldItem, @NonNull CardioSession newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CardioSession oldItem, @NonNull CardioSession newItem) {
            return oldItem.getIntensity() == newItem.getIntensity() && oldItem.getDuration() == newItem.getDuration();
        }
    };

    class CardioSessionHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        // View holder fields
        private MaterialTextView mSessionMTV;
        private TextInputEditText mDurationTIET;
        private TextInputEditText mIntensityTIET;
        private DurationEditTextListener mDurationEditTextListener;
        private IntensityEditTextListener mIntensityEditTextListener;
        private MaterialCardView mCardView;

        // View holder constructor
        public CardioSessionHolder(View itemView, DurationEditTextListener durationEditTextListener, IntensityEditTextListener intensityEditTextListener) {
            super(itemView);
            mSessionMTV = itemView.findViewById(R.id.cardio_session_mtv);
            mDurationTIET = itemView.findViewById(R.id.cardio_duration_tiet);
            mIntensityTIET = itemView.findViewById(R.id.cardio_intensity_tiet);
            mDurationEditTextListener = durationEditTextListener;
            mIntensityEditTextListener = intensityEditTextListener;
            mDurationTIET.addTextChangedListener(mDurationEditTextListener);
            mIntensityTIET.addTextChangedListener(mIntensityEditTextListener);
            mCardView = itemView.findViewById(R.id.cardio_cv);
            mCardView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (mListener != null) {
                return mListener.onSetLongClick(view, getAdapterPosition());
            }
            return false;
        }
    }

    @NonNull
    @Override
    public CardioSessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardio_session, parent, false);
        return new CardioSessionHolder(itemView, new DurationEditTextListener(), new IntensityEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull CardioSessionHolder holder, int position) {
        // Update position for text listener
        holder.mDurationEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.mIntensityEditTextListener.updatePosition(holder.getAdapterPosition());
        // Set values for view
        CardioSession currentSession = getItem(holder.getAdapterPosition());
        String session = "" + (holder.getAdapterPosition()+1);
        holder.mSessionMTV.setText(session);
        if (!currentSession.isEmpty()) {
            String duration = currentSession.getDuration() + "";
            String intensity = currentSession.getIntensity() +"";
            holder.mDurationTIET.setText(duration);
            holder.mIntensityTIET.setText(intensity);
        } else {
            holder.mDurationTIET.getText().clear();
            holder.mIntensityTIET.getText().clear();
        }
        // Request focus on initial exercise creation or new set
        if (holder.getAdapterPosition() == getCurrentList().size()-1) {
            holder.mDurationTIET.requestFocus();
        }
        holder.mCardView.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    // Text listener classes for reps and weight
    private class DurationEditTextListener implements TextWatcher {

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
            } else {
                getItem(mPosition).setDuration(Integer.parseInt(charSequence.toString()));
                getItem(mPosition).setEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class IntensityEditTextListener implements TextWatcher {

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
            } else {
                getItem(mPosition).setIntensity(Integer.parseInt(charSequence.toString()));
                getItem(mPosition).setEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    // Other adaptor methods
    public CardioSession getCardioSessionItem(int position) {
        return getItem(position);
    }

    // On click interface
    public interface OnItemLongClickListener {
        public boolean onSetLongClick(View view, int position);
    }
}