package com.gmail.plai2.ying.fitjournal.ui.workout.calisthenics_session;


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

public class CalisthenicsSetAdapter extends RecyclerView.Adapter<CalisthenicsSetAdapter.CalisthenicsSetHolder> {

    // Adaptor fields
    private List<Set> mListOfSets = new ArrayList<>();

    class CalisthenicsSetHolder extends RecyclerView.ViewHolder {

        // View holder fields
        private MaterialTextView mSetMTV;
        private TextInputEditText mRepTIET;
        private RepEditTextListener mRepEditTextListener;

        // View holder constructor
        public CalisthenicsSetHolder(View itemView, RepEditTextListener repEditTextListener) {
            super(itemView);
            mSetMTV = itemView.findViewById(R.id.calisthenics_set_mtv);
            mRepTIET = itemView.findViewById(R.id.calisthenics_rep_tiet);
            mRepEditTextListener = repEditTextListener;
            mRepTIET.addTextChangedListener(mRepEditTextListener);
        }
    }

    @NonNull
    @Override
    public CalisthenicsSetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calisthenics_session_set, parent, false);
        return new CalisthenicsSetHolder(itemView, new RepEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull CalisthenicsSetHolder holder, int position) {
        // Update position for text listener
        holder.mRepEditTextListener.updatePosition(holder.getAdapterPosition());
        // Set values for view
        Set currentSet = mListOfSets.get(holder.getAdapterPosition());
        String set = "Set " + (holder.getAdapterPosition()+1);
        holder.mSetMTV.setText(set);
        if (!currentSet.isEmpty()) {
            String reps = currentSet.getReps() + "";
            holder.mRepTIET.setText(reps);
        } else {
            holder.mRepTIET.getText().clear();
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