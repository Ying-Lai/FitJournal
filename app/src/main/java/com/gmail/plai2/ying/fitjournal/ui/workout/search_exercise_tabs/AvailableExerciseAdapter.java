package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;

import java.util.ArrayList;
import java.util.List;

public class AvailableExerciseAdapter extends RecyclerView.Adapter<AvailableExerciseAdapter.AvailableExerciseHolder> implements Filterable {

    // Adaptor fields
    private List<AvailableExerciseItem> mAvailableExerciseItems = new ArrayList<>();
    private List<AvailableExerciseItem> mAvailableExerciseItemsFull = new ArrayList<>();
    private OnItemClickListener mOnClickListener;

    // Adaptor constructor
    public AvailableExerciseAdapter(List<AvailableExerciseItem> availableExerciseItems, OnItemClickListener listener) {
        this.mAvailableExerciseItems = availableExerciseItems;
        this.mOnClickListener = listener;
    }

    class AvailableExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // View holder fields
        private TextView mAvailableExerciseName;
        private ImageView mAvailableExerciseFavorite;

        // View holder constructor
        public AvailableExerciseHolder(View itemView) {
            super(itemView);
            mAvailableExerciseName = itemView.findViewById(R.id.available_exercise_name_tv);
            mAvailableExerciseFavorite = itemView.findViewById(R.id.available_exercise_favorited_iv);
            mAvailableExerciseName.setOnClickListener(this);
            mAvailableExerciseFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) mOnClickListener.onClick(view, getAdapterPosition());
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
        AvailableExerciseItem currentAvailableExerciseItem = mAvailableExerciseItems.get(position);
        holder.mAvailableExerciseName.setText(currentAvailableExerciseItem.getExerciseName());
        if (currentAvailableExerciseItem.isFavorited()) {
            holder.mAvailableExerciseFavorite.setImageResource(R.drawable.ic_favorite_pressed);
        } else {
            holder.mAvailableExerciseFavorite.setImageResource(R.drawable.ic_favorite_unpressed);
        }
    }

    @Override
    public int getItemCount() {
        return mAvailableExerciseItems.size();
    }

    // Other adapter methods
    public AvailableExerciseItem getExerciseItem(int position) {
        return mAvailableExerciseItems.get(position);
    }

    public void setAvailableExerciseItems(List<AvailableExerciseItem> availableExerciseItems) {
        this.mAvailableExerciseItemsFull = availableExerciseItems;
        this.mAvailableExerciseItems = new ArrayList<>(availableExerciseItems);
        notifyDataSetChanged();
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
                    if (item.getExerciseName().toLowerCase().contains(filterPattern)) {
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
            mAvailableExerciseItems.clear();
            mAvailableExerciseItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    // On click interface
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}