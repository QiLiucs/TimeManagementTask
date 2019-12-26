package com.example.kiki.timemanagementtask;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VC2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VC2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VC2Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FirebaseFirestore db;
    private final String TAG = "VC2Fragment_tag";
    private Goal goal;

    public VC2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VC2Fragment.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        goal = new Goal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vc2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText goalNameEdit = getView().findViewById(R.id.goal);
        final EditText ddlEdit = getView().findViewById(R.id.ddl);
        final EditText goalTypeEdit = getView().findViewById(R.id.goaltype);
        final EditText startDateEdit = getView().findViewById(R.id.startdate);

        final Button addSubGoalBtn = getView().findViewById(R.id.btn1);

        addSubGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog);
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                ImageButton declineButton = dialog.findViewById(R.id.close);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });

                Button saveBtn = dialog.findViewById(R.id.save);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });

                final EditText subGoalnameEdit = dialog.findViewById(R.id.subGoalName);
                final EditText ddlEdit = dialog.findViewById(R.id.subGoalDdl);
                final EditText durationEdit = dialog.findViewById(R.id.subGoalDuration);
                final EditText difficultyEdit = dialog.findViewById(R.id.subGoalDifficulty);
                final EditText startDateEdit = dialog.findViewById(R.id.subGoalStartDate);

//                ddlEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if(hasFocus){
//                            showDatePickerDialog(getView(), "subgoalDdlDatePicker");
//                        }
//                    }
//                });
//
//                startDateEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if(hasFocus){
//                            showDatePickerDialog(getView(), "subgoalStartDatePicker");
//                        }
//                    }
//                });

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
                        cleanUpForm(subGoalnameEdit, ddlEdit, durationEdit, difficultyEdit, startDateEdit);
                        Toast.makeText(getActivity(), "subgoal added!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button createGoalBtn = getView().findViewById(R.id.btn2);

        createGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalName = goalNameEdit.getText().toString();
                String ddl = ddlEdit.getText().toString();
                String goalType = goalTypeEdit.getText().toString();
                String startDate = startDateEdit.getText().toString();
                goal.setDdl(ddl);
                goal.setGoalName(goalName);
                goal.setGoalType(goalType);
                goal.setStartDate(startDate);

                Log.d(TAG, "send date");
                db.collection("goalItems2")
                        .add(goal)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Toast.makeText(getActivity(), "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show();
                                cleanUpForm(goalNameEdit, ddlEdit, goalTypeEdit, startDateEdit);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                Toast.makeText(getActivity(), "Error writing document", Toast.LENGTH_SHORT).show();
                                cleanUpForm(goalNameEdit, ddlEdit, goalTypeEdit, startDateEdit);
                            }
                        });


            }
        });


        ddlEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(getView(), "ddlDatePicker");

                }
            }
        });


        startDateEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(getView(), "startDatePicker");

                }
            }
        });
    }


    public void showDatePickerDialog(View v, String s) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", s);
        newFragment.setArguments(bundle);
        newFragment.show(getActivity().getSupportFragmentManager(), s);
    }

    public void cleanUpForm(TextView ... textViews){
        for(TextView textView: textViews)
            textView.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}