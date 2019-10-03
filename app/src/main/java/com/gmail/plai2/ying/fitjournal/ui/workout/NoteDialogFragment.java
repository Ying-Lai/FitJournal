package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.View;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class NoteDialogFragment extends AppCompatDialogFragment {

    // Input fields
    private Context mContext;
    private String mNoteInput;

    // UI fields
    private TextInputEditText mExerciseNoteTIET;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.fragment_note_dialog,null);
        if (getArguments() != null) {
            ArrayList<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            if (exerciseInfo != null) mNoteInput = exerciseInfo.get(0);
        }
        mExerciseNoteTIET = view.findViewById(R.id.add_note_tiet);
        MaterialButton saveButton = view.findViewById(R.id.save_note_btn);
        MaterialButton cancelButton = view.findViewById(R.id.cancel_note_btn);

        // Update note if there is already input
        if (mNoteInput != null) {
            mExerciseNoteTIET.setText(mNoteInput);
        }

        // On click listeners
        saveButton.setOnClickListener((View saveButtonView) -> {
            mNoteInput = mExerciseNoteTIET.getText().toString();
            mListener.sendNote(mNoteInput);
            dismiss();
        });
        cancelButton.setOnClickListener((View cancelButtonView) -> dismiss());

        // Create view
        builder.setView(view)
                .setTitle(getResources().getString(R.string.add_note));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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

    // Note listener interface
    public interface NoteListener {
        void sendNote(String note);
    }
}