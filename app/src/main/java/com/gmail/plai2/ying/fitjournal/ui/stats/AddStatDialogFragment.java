package com.gmail.plai2.ying.fitjournal.ui.stats;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.StatType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddStatDialogFragment extends AppCompatDialogFragment {

    // Input fields
    private String mWeightInput;
    private String mFatInput;
    private String mStatType;

    // UI fields
    private TextInputEditText mStatTIET;
    private Button mSaveButton;
    private Button mCancelButton;

    // Listener
    private StatListener mListener;

    // Empty constructor
    public AddStatDialogFragment() {
    }

    // New instance constructor
    public static AddStatDialogFragment newInstance(int weight, int fat, StatType statType) {
        AddStatDialogFragment fragment = new AddStatDialogFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> statInfo = new ArrayList<>();
        if (weight == MainActivity.EMPTY) {
            statInfo.add("");
        } else {
            statInfo.add("" + weight);
        }
        if (fat == MainActivity.EMPTY) {
            statInfo.add("");
        } else {
            statInfo.add(fat+"");
        }
        statInfo.add(TypeConverters.statTypeToString(statType));
        bundle.putStringArrayList(MainActivity.STAT_INFO, statInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Initialize fields and variables
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_stat_dialog,null);
        if (getArguments() != null) {
            ArrayList<String> statInfo = getArguments().getStringArrayList(MainActivity.STAT_INFO);
            mWeightInput = statInfo.get(0);
            mFatInput = statInfo.get(1);
            mStatType = statInfo.get(2);
        }
        mStatTIET = view.findViewById(R.id.add_stat_tiet);
        mSaveButton = view.findViewById(R.id.stat_save_btn);
        mCancelButton = view.findViewById(R.id.stat_cancel_btn);

        // Update note if there is already input
        if (mStatType.equals(StatType.WEIGHT.getCategoryName()) && !mWeightInput.equals("")) {
            mStatTIET.setText(mWeightInput);
        } else if (mStatType.equals(StatType.BODYFAT.getCategoryName()) && !mFatInput.equals("")) {
            mStatTIET.setText(mFatInput);
        }

        // On click listeners
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int data;
                if (mStatTIET.getText().toString().equals("")) {
                    data = MainActivity.EMPTY;
                } else {
                    data = Integer.parseInt(mStatTIET.getText().toString());
                }
                mListener.sendStat(TypeConverters.stringToStatType(mStatType), data);
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
                .setTitle(mStatType.equals(StatType.WEIGHT.getCategoryName())?getResources().getString(R.string.todays_body_weight):getResources().getString(R.string.todays_body_fat));
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (StatListener) getTargetFragment();
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
    public interface StatListener {
        void sendStat(StatType type, int data);
    }
}
