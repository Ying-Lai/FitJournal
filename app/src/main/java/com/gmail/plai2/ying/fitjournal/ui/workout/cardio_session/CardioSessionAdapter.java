package com.gmail.plai2.ying.fitjournal.ui.workout.cardio_session;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CardioSession;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class CardioSessionAdapter extends RecyclerView.Adapter<CardioSessionAdapter.CardioSessionHolder> {

    // Adaptor fields
    private List<CardioSession> mListOfCardioSession = new ArrayList<>();

    class CardioSessionHolder extends RecyclerView.ViewHolder {

        // View holder fields
        private MaterialTextView mSessionMTV;
        private TextInputEditText mDurationTIET;
        private TextInputEditText mIntensityTIET;
        private DurationEditTextListener mDurationEditTextListener;
        private IntensityEditTextListener mIntensityEditTextListener;

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
        CardioSession currentSession = mListOfCardioSession.get(holder.getAdapterPosition());
        String session = "Session " + (holder.getAdapterPosition()+1);
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
    }

    @Override
    public int getItemCount() {
        return mListOfCardioSession.size();
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
                mListOfCardioSession.get(mPosition).setEmpty(true);
            } else {
                mListOfCardioSession.get(mPosition).setDuration(Integer.parseInt(charSequence.toString()));
                mListOfCardioSession.get(mPosition).setEmpty(false);
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
                mListOfCardioSession.get(mPosition).setEmpty(true);
            } else {
                mListOfCardioSession.get(mPosition).setIntensity(Integer.parseInt(charSequence.toString()));
                mListOfCardioSession.get(mPosition).setEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    // Other adaptor methods
    public void addListOfSessions(List<CardioSession> sessions) {
        this.mListOfCardioSession = sessions;
        notifyDataSetChanged();
    }

    public void addIndividualSession(CardioSession session) {
        mListOfCardioSession.add(session);
        notifyDataSetChanged();
    }

    public List<CardioSession> getSessions() {
        return mListOfCardioSession;
    }
}