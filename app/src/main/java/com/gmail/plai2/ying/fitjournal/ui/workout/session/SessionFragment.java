package com.gmail.plai2.ying.fitjournal.ui.workout.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionFragment extends Fragment implements NoteDialogFragment.NoteListener {

    // Input fields
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
    private ActionMode mActionMode;

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
                    mNewSessionButton.setVisibility(View.GONE);
                    mSaveButton.setVisibility(View.GONE);
                    ((MainActivity) getActivity()).closeKeyboard();
                }
                Session currentSession = mAdapter.getSessionItem(position);
                List<Session> newList = new ArrayList<>(mAdapter.getCurrentList());
                if (!currentSession.isChecked()) {
                    newList.get(position).setChecked(true);
                    ((MaterialCardView) view).setChecked(true);
                } else {
                    newList.get(position).setChecked(false);
                    ((MaterialCardView) view).setChecked(false);
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
            List<Session> initialSet = new ArrayList<>();
            switch (mExerciseTypeInput) {
                case CALISTHENICS:
                    initialSet.add(new Session(ExerciseType.CALISTHENICS));
                    break;
                case CARDIO:
                    initialSet.add(new Session(ExerciseType.CARDIO));
                    break;
                case STRENGTH:
                    initialSet.add(new Session(ExerciseType.STRENGTH));
                    break;
            }
            mAdapter.submitList(initialSet);
        }
        // Always show keyboard when opening an exercise
        ((MainActivity) getActivity()).showKeyboard();

        // On click listeners
        mNewSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Session> newList = new ArrayList<>(mAdapter.getCurrentList());
                switch (mExerciseTypeInput) {
                    case CALISTHENICS:
                        newList.add(new Session(ExerciseType.CALISTHENICS));
                        break;
                    case CARDIO:
                        newList.add(new Session(ExerciseType.CARDIO));
                        break;
                    case STRENGTH:
                        newList.add(new Session(ExerciseType.STRENGTH));
                        break;
                }
                mAdapter.submitList(newList);
                // Open keyboard and focus on new set
                ((MainActivity) getActivity()).showKeyboard();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Session> newListOfSession = mAdapter.getCurrentList();
                boolean showError = false;
                switch (mExerciseTypeInput) {
                    case CALISTHENICS:
                        for (Session session: newListOfSession) {
                            if (session.getReps() < 1) {
                                showError = true;
                                break;
                            }
                        }
                        break;
                    case CARDIO:
                        for (Session session: newListOfSession) {
                            if (session.getDuration() < 1 || session.getIntensity() < 1) {
                                showError = true;
                                break;
                            }
                        }
                        break;
                    case STRENGTH:
                        for (Session session: newListOfSession) {
                            if (session.getReps() < 1 || session.getWeight() < 1) {
                                showError = true;
                                break;
                            }
                        }
                        break;
                }
                if (showError) {
                    Toast toast = Toast.makeText(getContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Date today = new Date();
                today.setTime(0);
                if (mShouldUpdate) {
                    CompletedExerciseItem updatedItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, newListOfSession, mExerciseNoteInput);
                    updatedItem.setMId(mExerciseIdInput);
                    mViewModel.update(updatedItem);
                } else {
                    CompletedExerciseItem newItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, newListOfSession, mExerciseNoteInput);
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
                List<Session> newList = new ArrayList<>();
                for (int i = 0; i < mAdapter.getCurrentList().size(); i++) {
                    if (!mAdapter.getCurrentList().get(i).isChecked()) {
                        newList.add(mAdapter.getCurrentList().get(i));
                    }
                }
                if (newList.size() == 0) {
                    switch (mExerciseTypeInput) {
                        case CALISTHENICS:
                            newList.add(new Session(ExerciseType.CALISTHENICS));
                            break;
                        case CARDIO:
                            newList.add(new Session(ExerciseType.CARDIO));
                            break;
                        case STRENGTH:
                            newList.add(new Session(ExerciseType.STRENGTH));
                            break;
                    }
                }
                mAdapter.submitList(newList);
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            mAdapter.notifyDataSetChanged();
            ((MainActivity) getActivity()).showKeyboard();
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