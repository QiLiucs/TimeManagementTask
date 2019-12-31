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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GoalItemAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<JSONObject> mArrayList;

    public GoalItemAdapter(Activity activity, ArrayList<JSONObject> goalList) {
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
        JSONObject document = mArrayList.get(position);
        Goal goal = null;
        try {
            goal = new Goal(document.getString("goalName"), document.getString("ddl"), document.getString("goalType"), document.getString("startDate"));
            JSONArray subgoals = document.getJSONArray("subGoals");
            for(int i = 0; i < subgoals.length(); i++){
                JSONObject subgoalObj = subgoals.getJSONObject(i);
                SubGoal subGoal = new SubGoal(subgoalObj.getString("subgoal"), subgoalObj.getString("ddl"),subgoalObj.getString("duration"), subgoalObj.getString("difficulty"), subgoalObj.getString("startDate"));
                goal.addSubgoal(subGoal);
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
