package com.example.v3.poseCamera;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.v3.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class PoseResult extends AppCompatActivity {

    private TextView pose_result_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pose_result);

        pose_result_txt = (TextView) findViewById(R.id.pose_result_txt);

        Intent poseResult = getIntent();
        String exName = poseResult.getStringExtra("exName");
        String modelResult = poseResult.getStringExtra("modelResult");

        JSONObject jsonResult = null;

        try {
            jsonResult = new JSONObject(modelResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Iterator<String> jsonIter = jsonResult.keys();
        StringBuilder sb = new StringBuilder();
        while(jsonIter.hasNext()){
            String key = jsonIter.next();
            try {
                if(jsonResult.get(key) instanceof  JSONObject) {
                    sb.append(key).append(" : ").append(jsonResult.get(key)).append("\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.length()-1);

        pose_result_txt.setText(sb.toString());

    }
}
