package com.gmail.plai2.ying.fitjournal.ui.workout.cardio_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CardioSession;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.NoteDialogFragment;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardioSessionFragment extends Fragment implements NoteDialogFragment.NoteListener {

    // Input fields
    private ExerciseType mExerciseTypeInput;
    private String mExerciseNameInput;
    private int mExerciseIdInput;
    private List<CardioSession> mExerciseSessionInput;
    private String mExerciseNoteInput;
    private boolean mShouldUpdate = false;

    // UI fields
    private WorkoutViewModel mViewModel;
    private RecyclerView mSessionRV;
    private Button mNewSessionButton;
    private Button mSaveButton;
    private MaterialToolbar mToolbar;
    private ActionMode mActionMode;
    private CardioSessionAdapter mAdapter;

    public CardioSessionFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        // Parse through bundle
        if (getArguments() != null) {
            List<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            mExerciseTypeInput = TypeConverters.intToExerciseType(Integer.parseInt(exerciseInfo.get(0)));
            mExerciseNameInput = exerciseInfo.get(1);
            mExerciseNoteInput = "";
            if (exerciseInfo.size() > 2) {
                mExerciseIdInput = Integer.parseInt(exerciseInfo.get(2));
                mExerciseSessionInput = TypeConverters.stringToSessionList(exerciseInfo.get(3));
                mExerciseNoteInput = exerciseInfo.get(4);
                mShouldUpdate = true;
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_cardio_session, container, false);
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        mToolbar = root.findViewById(R.id.cardio_session_tb);
        mNewSessionButton = root.findViewById(R.id.new_cardio_session_btn);
        mSaveButton = root.findViewById(R.id.save_cardio_exercise_btn);
        mSessionRV = root.findViewById(R.id.cardio_session_rv);

        // Setup app tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mExerciseNameInput);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup adaptor
        mSessionRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mSessionRV.setHasFixedSize(true);
        mAdapter = new CardioSessionAdapter(new CardioSessionAdapter.OnItemLongClickListener() {

            // Checkable cards ui
            @Override
            public boolean onSetLongClick(View view, int position) {
                if (mActionMode == null) {
                    mActionMode = ((MainActivity)getActivity()).startSupportActionMode(mActionModeCallBack);
                    mNewSessionButton.setVisibility(View.GONE);
                    mSaveButton.setVisibility(View.GONE);
                }
                CardioSession currentSession = mAdapter.getCardioSessionItem(position);
                List<CardioSession> newList = new ArrayList<>(mAdapter.getCurrentList());
                if (!currentSession.isChecked()) {
                    newList.get(position).setChecked(true);
                    ((MaterialCardView)view).setChecked(true);
                } else {
                    newList.get(position).setChecked(false);
                    ((MaterialCardView)view).setChecked(false);
                }
                mAdapter.submitList(newList);
                return true;
            }
        });
        mSessionRV.setAdapter(mAdapter);

        // Update elements if passed from workout fragment
        if (mShouldUpdate) {
            mAdapter.submitList(mExerciseSessionInput);
        } else {
            List<CardioSession> initialSession = new ArrayList<>();
            initialSession.add(new CardioSession());
            mAdapter.submitList(initialSession);
            // Always show keyboard when opening an exercise
            ((MainActivity) getActivity()).showKeyboard();
        }

        // On click listeners
        mNewSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CardioSession> newList = new ArrayList<>(mAdapter.getCurrentList());
                newList.add(new CardioSession());
                mAdapter.submitList(newList);
                // Open keyboard and focus on new session
                ((MainActivity) getActivity()).showKeyboard();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CardioSession> newListOfSessions = mAdapter.getCurrentList();
                Date today = new Date();
                today.setTime(0);
                if (mShouldUpdate) {
                    CompletedExerciseItem updatedItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, mExerciseNoteInput, newListOfSessions);
                    updatedItem.setMId(mExerciseIdInput);
                    mViewModel.update(updatedItem);
                } else {
                    CompletedExerciseItem newItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, mExerciseNoteInput, newListOfSessions);
                    mViewModel.insert(newItem);
                }
                // Close keyboard when leaving fragment
                ((MainActivity) getActivity()).closeKeyboard();
                Navigation.findNavController(view).popBackStack(R.id.navigation_to_workout, false);
            }
        });
    }

    // Setup action mode
    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.delete_menu, menu);
            mode.setTitle(getResources().getString(R.string.delete));
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete_menu_item) {
                List<CardioSession> newList = new ArrayList<>();
                for (int i=0; i<mAdapter.getCurrentList().size(); i++) {
                    if (!mAdapter.getCurrentList().get(i).isChecked()) {
                        newList.add(mAdapter.getCurrentList().get(i));
                    }
                }
                if (newList.size() == 0) {
                    newList.add(new CardioSession());
                }
                mAdapter.submitList(newList);
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.notifyDataSetChanged();
            mActionMode = null;
            // Hide other buttons in delete action mode
            mNewSessionButton.setVisibility(View.VISIBLE);
            mSaveButton.setVisibility(View.VISIBLE);
        }
    };

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.session_set_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Close keyboard when leaving fragment
            ((MainActivity) getActivity()).closeKeyboard();
            Navigation.findNavController(getView()).popBackStack();
        }
        if (item.getItemId() == R.id.note_menu_item) {
            NoteDialogFragment noteDialogFragment = NoteDialogFragment.newInstance(mExerciseNoteInput);
            noteDialogFragment.setTargetFragment(this, 1);
            noteDialogFragment.show(getFragmentManager(), "note");
        }
        return true;
    }

    // Note listener
    @Override
    public void sendNote(String note) {
        mExerciseNoteInput = note;
    }
}