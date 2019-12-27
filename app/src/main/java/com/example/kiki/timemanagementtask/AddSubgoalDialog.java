package com.example.kiki.timemanagementtask;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddSubgoalDialog extends DialogFragment implements DatePickerFragment.DatePickerListener {

    private Goal goal;
    public static String mName = "addSubgoalDialog";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        goal = bundle.getParcelable("goal");

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        Dialog dialog = getDialog();
        dialog.getWindow().setLayout(width, height);
        ImageButton declineButton = dialog.findViewById(R.id.close);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dismiss();
            }
        });

        Button saveBtn = dialog.findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dismiss();
            }
        });


        final EditText subGoalnameEdit = dialog.findViewById(R.id.subGoalName);
        final EditText ddlEdit = dialog.findViewById(R.id.subGoalDdl);
        final EditText durationEdit = dialog.findViewById(R.id.subGoalDuration);
        final EditText difficultyEdit = dialog.findViewById(R.id.subGoalDifficulty);
        final EditText startDateEdit = dialog.findViewById(R.id.subGoalStartDate);

        Button addSubgoalBtn = dialog.findViewById(R.id.addgoal);
        addSubgoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subGoalName = subGoalnameEdit.getText().toString();
                String ddl = ddlEdit.getText().toString();
                String duration = durationEdit.getText().toString();
                String difficulty = difficultyEdit.getText().toString();
                String startDate = startDateEdit.getText().toString();
                SubGoal subGoal = new SubGoal(subGoalName, ddl, duration, difficulty, startDate);
                goal.addSubgoal(subGoal);
                VC2Fragment.cleanUpForm(subGoalnameEdit, ddlEdit, durationEdit, difficultyEdit, startDateEdit);
                Toast.makeText(getActivity(), "subgoal added!", Toast.LENGTH_SHORT).show();
            }
        });

        ddlEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog("subgoalDdlDatePicker", mName);
                }
            }
        });
        startDateEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog("subgoalDdlDatePicker", mName);
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(this.getDialog().getCurrentFocus() != null){
            EditText editText = (EditText)this.getDialog().getCurrentFocus();
            editText.setText(year + "-" + month + "-" + dayOfMonth);
        }
    }
    public void showDatePickerDialog(String s, String fragmentName) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", fragmentName);
        newFragment.setArguments(bundle);
        newFragment.show(getActivity().getSupportFragmentManager(), s);
    }
}
