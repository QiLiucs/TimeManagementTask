package com.example.kiki.timemanagementtask;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class GoalItemAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<QueryDocumentSnapshot> mArrayList;

    public GoalItemAdapter(Activity activity, ArrayList<QueryDocumentSnapshot> goalList) {
        this.mActivity = activity;
        this.mArrayList = goalList;
    }

    @Override
    public int getCount() {
        Log.d("GoalItemAdapter", mArrayList.size()+"");
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        QueryDocumentSnapshot document = mArrayList.get(position);
        HashMap<String, Object> data = (HashMap<String, Object>) document.getData();
        Goal goal = new Goal((String)data.get("goalName"), (String)data.get("ddl"), (String)data.get("goalType"), (String)data.get("startDate"));
        ArrayList<HashMap<String, Object>> arrayList = (ArrayList<HashMap<String, Object>>) data.get("subGoals");
        for(HashMap<String, Object> map: arrayList){
            SubGoal subGoal = new SubGoal((String)map.get("subgoal"), (String)map.get("ddl"),(String)map.get("duration"), (String)map.get("difficulty"), (String)map.get("startDate"));
            goal.addSubgoal(subGoal);
        }
        return goal;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_goalitem, parent, false);
        }
        ImageView imvAvatar = convertView.findViewById(R.id.imvAvatar);
        TextView tvGoalName = convertView.findViewById(R.id.tvGoalName);
        ImageView imvFunnel = convertView.findViewById(R.id.imvFunnel);
        TextView tvDaysLeft = convertView.findViewById(R.id.tvDaysLeft);

        imvAvatar.setImageResource(R.drawable.avatar);
        imvFunnel.setImageResource(R.drawable.funnel);

        Goal goal = (Goal) getItem(position);
        tvGoalName.setText(goal.getGoalName());
        tvDaysLeft.setText(Goal.computeHowManyDaysLeft(goal.getStartDate(), goal.getDdl()));
        return convertView;
    }
}
