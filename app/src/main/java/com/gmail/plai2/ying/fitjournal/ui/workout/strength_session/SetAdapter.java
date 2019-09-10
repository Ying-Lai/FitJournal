package com.gmail.plai2.ying.fitjournal.ui.workout.strength_session;

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

    private List<Set> sets = new ArrayList<>();

    class SetHolder extends RecyclerView.ViewHolder {
        private MaterialTextView mSetMTV;
        private TextInputEditText mRepTIET;
        private TextInputEditText mWeightTIET;

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
        Set currentSet = sets.get(position);
        String set = "Set " + (position+1);
        holder.mSetMTV.setText(set);
        if (currentSet.getReps() != -1) {
            String reps = currentSet.getReps() + "";
            holder.mRepTIET.setText(reps);
        }
        if (currentSet.getWeight() != -1) {
            String weight = currentSet.getWeight()+"";
            holder.mWeightTIET.setText(weight);
        }
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    public void addSet(List<Set> sets) {
        this.sets = sets;
        notifyDataSetChanged();
    }
}