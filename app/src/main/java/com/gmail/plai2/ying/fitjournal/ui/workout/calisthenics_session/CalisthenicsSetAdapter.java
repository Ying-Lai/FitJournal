package com.gmail.plai2.ying.fitjournal.ui.workout.calisthenics_session;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class CalisthenicsSetAdapter extends ListAdapter<Set, CalisthenicsSetAdapter.CalisthenicsSetHolder> {

    // Adapter Fields
    private OnItemLongClickListener mListener;

    // Adapter constructor
    public CalisthenicsSetAdapter(OnItemLongClickListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;

    }

    private static final DiffUtil.ItemCallback<Set> DIFF_CALLBACK = new DiffUtil.ItemCallback<Set>() {
        @Override
        public boolean areItemsTheSame(@NonNull Set oldItem, @NonNull Set newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Set oldItem, @NonNull Set newItem) {
            return oldItem.getWeight() == newItem.getWeight() && oldItem.getReps() == newItem.getReps();
        }
    };

    class CalisthenicsSetHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        // View holder fields
        private MaterialTextView mSetMTV;
        private TextInputEditText mRepTIET;
        private RepEditTextListener mRepEditTextListener;
        private MaterialCardView mCardView;

        // View holder constructor
        public CalisthenicsSetHolder(View itemView, RepEditTextListener repEditTextListener) {
            super(itemView);
            mSetMTV = itemView.findViewById(R.id.calisthenics_set_mtv);
            mRepTIET = itemView.findViewById(R.id.calisthenics_rep_tiet);
            mRepEditTextListener = repEditTextListener;
            mRepTIET.addTextChangedListener(mRepEditTextListener);
            mCardView = itemView.findViewById(R.id.calisthenics_cv);
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
        Set currentSet = getItem(holder.getAdapterPosition());
        String set = "" + (holder.getAdapterPosition()+1);
        holder.mSetMTV.setText(set);
        if (!currentSet.isEmpty()) {
            String reps = currentSet.getReps() + "";
            holder.mRepTIET.setText(reps);
        } else {
            holder.mRepTIET.getText().clear();
        }
        if (holder.getAdapterPosition() == getCurrentList().size()-1) {
            holder.mRepTIET.requestFocus();
        }
        holder.mCardView.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
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
                getItem(mPosition).setEmpty(true);
            } else {
                getItem(mPosition).setReps(Integer.parseInt(charSequence.toString()));
                getItem(mPosition).setEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    // Other adaptor methods
    public Set getSetItem(int position) {
        return getItem(position);
    }

    // On click interface
    public interface OnItemLongClickListener {
        public boolean onSetLongClick(View view, int position);
    }
}