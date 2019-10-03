package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class AvailableExerciseAdapter extends ListAdapter<AvailableExerciseItem, AvailableExerciseAdapter.AvailableExerciseHolder> implements Filterable {

    // Adaptor fields
    private List<AvailableExerciseItem> mAvailableExerciseItemsFull = new ArrayList<>();
    private OnItemClickListener mListener;

    // Adaptor constructor
    AvailableExerciseAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
    }

    private static final DiffUtil.ItemCallback<AvailableExerciseItem> DIFF_CALLBACK= new DiffUtil.ItemCallback<AvailableExerciseItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull AvailableExerciseItem oldItem, @NonNull AvailableExerciseItem newItem) {
            return oldItem.getMExerciseName().equals(newItem.getMExerciseName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull AvailableExerciseItem oldItem, @NonNull AvailableExerciseItem newItem) {
            return oldItem.getMExerciseName().equals(newItem.getMExerciseName()) && oldItem.isChecked() == newItem.isChecked();
        }
    };

    class AvailableExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        // View holder fields
        private TextView mAvailableExerciseName;
        private ImageView mAvailableExerciseFavorite;
        private MaterialCardView mAvailableExerciseCardView;

        // View holder constructor
        AvailableExerciseHolder(View itemView) {
            super(itemView);
            mAvailableExerciseName = itemView.findViewById(R.id.available_exercise_name_tv);
            mAvailableExerciseFavorite = itemView.findViewById(R.id.available_exercise_favorited_iv);
            mAvailableExerciseCardView = itemView.findViewById(R.id.available_exercise_cv);
            mAvailableExerciseName.setOnLongClickListener(this);
            mAvailableExerciseName.setOnClickListener(this);
            mAvailableExerciseFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) mListener.onClick(view, getAdapterPosition());
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
    public AvailableExerciseAdapter.AvailableExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_available_exercise, parent, false);
        return new AvailableExerciseAdapter.AvailableExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableExerciseAdapter.AvailableExerciseHolder holder, int position) {
        AvailableExerciseItem currentAvailableExerciseItem = getItem(position);
        holder.mAvailableExerciseName.setText(currentAvailableExerciseItem.getMExerciseName());
        if (currentAvailableExerciseItem.isCustom()) {
            holder.mAvailableExerciseCardView.setCheckable(true);
            if (currentAvailableExerciseItem.isChecked()) {
                holder.mAvailableExerciseCardView.setChecked(true);
            } else {
                holder.mAvailableExerciseCardView.setChecked(false);
            }
        }
        if (currentAvailableExerciseItem.isFavorited()) {
            holder.mAvailableExerciseFavorite.setImageResource(R.drawable.ic_favorite_pressed);
        } else {
            holder.mAvailableExerciseFavorite.setImageResource(R.drawable.ic_favorite_unpressed);
        }
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    // Other adapter methods
    AvailableExerciseItem getExerciseItem(int position) {
        return getItem(position);
    }

    void setFullList(List<AvailableExerciseItem> fullList) {
        mAvailableExerciseItemsFull = fullList;
    }

    // Search filter methods
    @Override
    public Filter getFilter() {
        return availableExerciseFilter;
    }

    private Filter availableExerciseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AvailableExerciseItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mAvailableExerciseItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (AvailableExerciseItem item: mAvailableExerciseItemsFull) {
                    if (item.getMExerciseName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            List<AvailableExerciseItem> filteredList = new ArrayList<>();
            if (results.values instanceof List){
                for(int i = 0; i < ((List<?>)results.values).size(); i++){
                    Object item = ((List<?>) results.values).get(i);
                    if(item instanceof AvailableExerciseItem){
                        filteredList.add((AvailableExerciseItem) item);
                    }
                }
            }
            submitList(filteredList);
        }
    };

    // On click interface
    public interface OnItemClickListener {
        void onClick(View view, int position);
        boolean onLongClick(View view, int position);
    }
}