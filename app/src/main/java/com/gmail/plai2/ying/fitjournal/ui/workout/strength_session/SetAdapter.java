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

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetHolder> {

    // Adaptor fields
    private List<Set> mListOfSets = new ArrayList<>();

    class SetHolder extends RecyclerView.ViewHolder {

        // View holder fields
        private MaterialTextView mSetMTV;
        private TextInputEditText mRepTIET;
        private TextInputEditText mWeightTIET;

        // View holder constructor
        public SetHolder(View itemView) {
            super(itemView);
            mSetMTV = itemView.findViewById(R.id.set_mtv);
            mRepTIET = itemView.findViewById(R.id.rep_tiet);
            mWeightTIET = itemView.findViewById(R.id.weight_tiet);
        }
    }

    @NonNull
    @Override
    public SetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_strength_session_set, parent, false);
        return new SetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SetHolder holder, int position) {
        Set currentSet = mListOfSets.get(position);
        String set = "Set " + (position+1);
        holder.mSetMTV.setText(set);
        if (!currentSet.isEmpty()) {
            String reps = currentSet.getReps() + "";
            String weight = currentSet.getWeight()+"";
            holder.mRepTIET.setText(reps);
            holder.mWeightTIET.setText(weight);
        }
        // Text watchers to programmatically update adapter list of sets
        holder.mRepTIET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holder.mRepTIET.getText().length() == 0) {
                    currentSet.setEmpty(true);
                } else {
                    currentSet.setReps(Integer.parseInt(holder.mRepTIET.getText().toString()));
                }
            }
        });
        holder.mWeightTIET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holder.mWeightTIET.getText().length() == 0) {
                    currentSet.setEmpty(true);
                } else {
                    currentSet.setReps(Integer.parseInt(holder.mWeightTIET.getText().toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListOfSets.size();
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