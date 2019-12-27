package com.example.kiki.timemanagementtask;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VC1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VC1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VC1Fragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private FirebaseFirestore db;
    private String TAG = "VC1Fragment_tag";
    private ArrayList<QueryDocumentSnapshot> goalList;
    public static String mName = "vc1";


    public VC1Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        goalList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vc1, null);
        return view;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String collectionsName = MainActivity.collectionsName;
        CollectionReference ref = db.collection(collectionsName);
        final ProgressDialog progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Fetching data ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
        fetchData(ref, progressBar);

    }

    public void fetchData(CollectionReference ref, final ProgressDialog progressBar) {
        ref.orderBy("ddl", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.dismiss();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                goalList.add(document);

                            }
                            GoalItemAdapter goalItemAdapter = new GoalItemAdapter(getActivity(), goalList);
                            ListView listView = getView().findViewById(R.id.listview);
                            listView.setAdapter(goalItemAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView tvSubgoalsList = getView().findViewById(R.id.tvSubgoalsList);
                                    QueryDocumentSnapshot document = goalList.get(position);
                                    String res = "";
                                    HashMap<String, Object> goalMap = (HashMap<String, Object>) document.getData();
                                    ArrayList<HashMap<String, Object>> subGoalsList = (ArrayList<HashMap<String, Object>>) goalMap.get("subGoals");
                                    for (HashMap<String, Object> map : subGoalsList) {
                                        res += (String) map.get("subgoal");
                                        res += "  ";
                                        res += Goal.computeHowManyDaysLeft((String) map.get("startDate"), (String) map.get("ddl"));
                                        res += "\n\n";
                                    }
                                    tvSubgoalsList.setText(res);
                                }
                            });
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

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
