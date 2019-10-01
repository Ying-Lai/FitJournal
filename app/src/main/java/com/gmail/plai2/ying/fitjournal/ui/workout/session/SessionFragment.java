package com.gmail.plai2.ying.fitjournal.ui.workout.session;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.Session;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.NoteDialogFragment;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class SessionFragment extends Fragment implements NoteDialogFragment.NoteListener {

    // Input fields
    private LocalDate mCurrentDateInput;
    private ExerciseType mExerciseTypeInput;
    private String mExerciseNameInput;
    private int mExerciseIdInput;
    private List<Session> mExerciseSessionInput;
    private String mExerciseNoteInput;
    private boolean mShouldUpdate = false;

    // UI fields
    private WorkoutViewModel mViewModel;
    private RecyclerView mSessionRV;
    private Button mNewSessionButton;
    private Button mSaveButton;
    private MaterialToolbar mToolbar;
    private SessionAdapter mAdapter;

    // Action mode fields
    private ActionMode mActionMode;
    private boolean mDeleteUsed;

    // Empty constructor
    public SessionFragment() {
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
            mCurrentDateInput = TypeConverters.stringToDate(getArguments().getString(MainActivity.DATE_INFO));
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
        int resource;
        switch (mExerciseTypeInput) {
            case CARDIO:
                resource = R.layout.fragment_cardio_session;
                break;
            case STRENGTH:
                resource = R.layout.fragment_strength_session;
                break;
            default:
                resource = R.layout.fragment_calisthenics_session;
        }
        View root = inflater.inflate(resource, container, false);
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        switch (mExerciseTypeInput) {
            case CARDIO:
                mToolbar = root.findViewById(R.id.cardio_session_tb);
                mNewSessionButton = root.findViewById(R.id.new_cardio_session_btn);
                mSaveButton = root.findViewById(R.id.save_cardio_exercise_btn);
                mSessionRV = root.findViewById(R.id.cardio_session_rv);
                break;
            case STRENGTH:
                mToolbar = root.findViewById(R.id.strength_session_tb);
                mNewSessionButton = root.findViewById(R.id.new_strength_set_btn);
                mSaveButton = root.findViewById(R.id.save_strength_exercise_btn);
                mSessionRV = root.findViewById(R.id.strength_sets_rv);
                break;
            case CALISTHENICS:
                mToolbar = root.findViewById(R.id.calisthenics_session_tb);
                mNewSessionButton = root.findViewById(R.id.new_calisthenics_set_btn);
                mSaveButton = root.findViewById(R.id.save_calisthenics_exercise_btn);
                mSessionRV = root.findViewById(R.id.calisthenics_sets_rv);
                break;
        }

        // Setup app tool bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
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
        mAdapter = new SessionAdapter(mExerciseTypeInput, new SessionAdapter.OnItemLongClickListener() {

            // Checkable cards ui
            @Override
            public boolean onLongClick(View view, int position) {
                if (mActionMode == null) {
                    mActionMode = ((MainActivity) getActivity()).startSupportActionMode(mActionModeCallBack);
                    mDeleteUsed = false;
                    mNewSessionButton.setVisibility(View.GONE);
                    mSaveButton.setVisibility(View.GONE);

                    // Close keyboard when entering delete mode
                    ((MainActivity) getActivity()).closeKeyboard();
                }
                // Pass list of checked items to list adapter
                Session currentSession = mAdapter.getSessionItem(position);
                List<Session> checkedSessionList = new ArrayList<>();
                for (Session session : mAdapter.getCurrentList()) {
                    Session newSession = new Session(session);
                    newSession.setReadOnly(true);
                    checkedSessionList.add(newSession);
                }
                if (currentSession.isChecked()) {
                    checkedSessionList.get(position).setChecked(false);
                } else {
                    checkedSessionList.get(position).setChecked(true);
                }
                mAdapter.submitList(checkedSessionList);
                return true;
            }
        });
        mSessionRV.setAdapter(mAdapter);

        // Update elements if passed from workout fragment
        if (mShouldUpdate) {
            mAdapter.submitList(mExerciseSessionInput);
        } else {
            List<Session> initialSession = new ArrayList<>();
            switch (mExerciseTypeInput) {
                case CALISTHENICS:
                    initialSession.add(new Session(ExerciseType.CALISTHENICS));
                    break;
                case CARDIO:
                    initialSession.add(new Session(ExerciseType.CARDIO));
                    break;
                case STRENGTH:
                    initialSession.add(new Session(ExerciseType.STRENGTH));
                    break;
            }
            mAdapter.submitList(initialSession);
        }

        // Always show keyboard when opening an exercise
        ((MainActivity) getActivity()).showKeyboard();

        // On click listeners
        mNewSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.mOnNewSession = true;
                List<Session> newSessionList = new ArrayList<>();
                for (Session session : mAdapter.getCurrentList()) {
                    newSessionList.add(new Session(session));
                }
                switch (mExerciseTypeInput) {
                    case CALISTHENICS:
                        newSessionList.add(new Session(ExerciseType.CALISTHENICS));
                        break;
                    case CARDIO:
                        newSessionList.add(new Session(ExerciseType.CARDIO));
                        break;
                    case STRENGTH:
                        newSessionList.add(new Session(ExerciseType.STRENGTH));
                        break;
                }
                mAdapter.submitList(newSessionList);
                // Open keyboard and focus on new set
                ((MainActivity) getActivity()).showKeyboard();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Session> currentListOfSessions = mAdapter.getCurrentList();
                boolean showError = false;
                switch (mExerciseTypeInput) {
                    case CALISTHENICS:
                        for (Session session: currentListOfSessions) {
                            if (session.getReps() < 1) {
                                showError = true;
                                break;
                            }
                        }
                        break;
                    case CARDIO:
                        for (Session session: currentListOfSessions) {
                            if (session.getDuration() < 1 || session.getIntensity() < 1) {
                                showError = true;
                                break;
                            }
                        }
                        break;
                    case STRENGTH:
                        for (Session session: currentListOfSessions) {
                            if (session.getReps() < 1 || session.getWeight() < 1) {
                                showError = true;
                                break;
                            }
                        }
                        break;
                }
                if (showError) {

                    // Create and display error toast
                    Toast errorToast = new Toast(getContext());
                    View errorToastView = getLayoutInflater().inflate(R.layout.error_toast, null);
                    errorToast.setView(errorToastView);
                    errorToast.setDuration(Toast.LENGTH_SHORT);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int screenHeight = displayMetrics.heightPixels;
                    errorToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, screenHeight - mSaveButton.getTop() - mSaveButton.getPaddingTop());
                    errorToast.show();
                    return;
                }
                if (mShouldUpdate) {
                    CompletedExerciseItem updatedItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, mCurrentDateInput, currentListOfSessions, mExerciseNoteInput);
                    updatedItem.setMId(mExerciseIdInput);
                    mViewModel.update(updatedItem);
                } else {
                    CompletedExerciseItem newItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, mCurrentDateInput, currentListOfSessions, mExerciseNoteInput);
                    mViewModel.insert(newItem);
                }

                // Close keyboard when leaving fragment
                ((MainActivity) getActivity()).closeKeyboard();
                if (mCurrentDateInput.equals(LocalDate.now())) {
                    Navigation.findNavController(view).popBackStack(R.id.navigation_to_workout_today, false);
                } else {
                    Navigation.findNavController(view).popBackStack(R.id.navigation_to_workout_another_day, false);
                }

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
                List<Session> uncheckedSessionsList = new ArrayList<>();
                for (int i = 0; i < mAdapter.getCurrentList().size(); i++) {
                    if (!mAdapter.getCurrentList().get(i).isChecked()) {
                        Session uncheckedSession = new Session(mAdapter.getCurrentList().get(i));
                        uncheckedSession.setReadOnly(false);
                        uncheckedSessionsList.add(uncheckedSession);
                    }
                }
                if (uncheckedSessionsList.size() == 0) {
                    switch (mExerciseTypeInput) {
                        case CALISTHENICS:
                            uncheckedSessionsList.add(new Session(ExerciseType.CALISTHENICS));
                            break;
                        case CARDIO:
                            uncheckedSessionsList.add(new Session(ExerciseType.CARDIO));
                            break;
                        case STRENGTH:
                            uncheckedSessionsList.add(new Session(ExerciseType.STRENGTH));
                            break;
                    }
                }
                mAdapter.submitList(uncheckedSessionsList);
                mDeleteUsed = true;
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;

            // If delete icon was not used then uncheck the list before leaving delete action mode
            if (!mDeleteUsed) {
                List<Session> uncheckedSessionList = new ArrayList<>();
                for (Session session : mAdapter.getCurrentList()) {
                    Session editableSession = new Session(session);
                    editableSession.setReadOnly(false);
                    uncheckedSessionList.add(editableSession);
                }
                for (int i=0; i<uncheckedSessionList.size(); i++) {
                    if (uncheckedSessionList.get(i).isChecked()) {
                        uncheckedSessionList.get(i).setChecked(false);
                    }
                }
                mAdapter.submitList(uncheckedSessionList);
            }

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
            Navigation.findNavController(getView()).popBackStack();
            ((MainActivity) getActivity()).closeKeyboard();
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