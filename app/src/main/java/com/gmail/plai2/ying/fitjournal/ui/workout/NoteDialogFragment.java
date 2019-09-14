package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs.AddCustomDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

public class NoteDialogFragment extends AppCompatDialogFragment {

    // Input fields
    private String mNoteInput;

    // UI fields
    private WorkoutViewModel mViewModel;
    private TextInputEditText mExerciseNoteTIET;
    private Button mSaveButton;
    private Button mCancelButton;

    // Listener
    private NoteListener mListener;

    // Empty constructor
    public NoteDialogFragment() {
    }

    // New instance constructor
    public static NoteDialogFragment newInstance(String note) {
        NoteDialogFragment fragment = new NoteDialogFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> exerciseInfo = new ArrayList<>();
        exerciseInfo.add(note);
        bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Initialize fields and variables
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_note_dialog,null);
        if (getArguments() != null) {
            ArrayList<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            mNoteInput = exerciseInfo.get(0);
        }
        mExerciseNoteTIET = view.findViewById(R.id.add_note_tiet);
        mSaveButton = view.findViewById(R.id.save_note_btn);
        mCancelButton = view.findViewById(R.id.cancel_note_btn);

        // Update note if there is already input
        if (mNoteInput != null) {
            mExerciseNoteTIET.setText(mNoteInput);
        }

        // On click listeners
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoteInput = mExerciseNoteTIET.getText().toString();
                mListener.sendNote(mNoteInput);
                dismiss();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setView(view)
                .setTitle(getResources().getString(R.string.add_note));
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (NoteListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface NoteListener {
        void sendNote(String note);
    }
}