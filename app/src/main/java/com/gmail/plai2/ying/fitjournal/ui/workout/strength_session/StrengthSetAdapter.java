package com.gmail.plai2.ying.fitjournal.ui.workout.strength_session;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class StrengthSetAdapter extends RecyclerView.Adapter<StrengthSetAdapter.StrengthSetHolder> {

    // Adaptor fields
    private List<Set> mListOfSets = new ArrayList<>();

    class StrengthSetHolder extends RecyclerView.ViewHolder {

        // View holder fields
        private MaterialTextView mSetMTV;
        private TextInputEditText mRepTIET;
        private TextInputEditText mWeightTIET;
        private RepEditTextListener mRepEditTextListener;
        private WeightEditTextListener mWeightEditTextListener;

        // View holder constructor
        public StrengthSetHolder(View itemView, RepEditTextListener repEditTextListener, WeightEditTextListener weightEditTextListener) {
            super(itemView);
            mSetMTV = itemView.findViewById(R.id.strength_set_mtv);
            mRepTIET = itemView.findViewById(R.id.strength_rep_tiet);
            mWeightTIET = itemView.findViewById(R.id.strength_weight_tiet);
            mRepEditTextListener = repEditTextListener;
            mWeightEditTextListener = weightEditTextListener;
            mRepTIET.addTextChangedListener(mRepEditTextListener);
            mWeightTIET.addTextChangedListener(mWeightEditTextListener);
        }
    }

    @NonNull
    @Override
    public StrengthSetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_strength_session_set, parent, false);
        return new StrengthSetHolder(itemView, new RepEditTextListener(), new WeightEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull StrengthSetHolder holder, int position) {
        // Update position for text listener
        holder.mRepEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.mWeightEditTextListener.updatePosition(holder.getAdapterPosition());
        // Set values for view
        Set currentSet = mListOfSets.get(holder.getAdapterPosition());
        String set = "" + (holder.getAdapterPosition()+1);
        holder.mSetMTV.setText(set);
        if (!currentSet.isEmpty()) {
            String reps = currentSet.getReps() + "";
            String weight = currentSet.getWeight()+"";
            holder.mRepTIET.setText(reps);
            holder.mWeightTIET.setText(weight);
        } else {
            holder.mRepTIET.getText().clear();
            holder.mWeightTIET.getText().clear();
        }
    }

    @Override
    public int getItemCount() {
        return mListOfSets.size();
    }

    // Text listener classes for reps and weight
    private class RepEditTextListener implements TextWatcher {

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
                mListOfSets.get(mPosition).setEmpty(true);
            } else {
                mListOfSets.get(mPosition).setReps(Integer.parseInt(charSequence.toString()));
                mListOfSets.get(mPosition).setEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class WeightEditTextListener implements TextWatcher {

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
                mListOfSets.get(mPosition).setEmpty(true);
            } else {
                mListOfSets.get(mPosition).setWeight(Integer.parseInt(charSequence.toString()));
                mListOfSets.get(mPosition).setEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    // Other adaptor methods
    public void addListOfSets(List<Set> sets) {
        this.mListOfSets = sets;
        notifyDataSetChanged();
    }

    public void addIndividualSet(Set set) {
        mListOfSets.add(set);
        notifyDataSetChanged();
    }

    public List<Set> getSets() {
        return mListOfSets;
    }
}