package com.example.v3;


import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v3.plan_adapter.AddPlan;
import com.example.v3.plan_adapter.PlanAdapter;
import com.example.v3.plan_adapter.PlanItem;
import com.example.v3.plan_adapter.dto.AddPlanDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PlanFragment extends Fragment {
    static final String TAG = "PlanRecyclerview";

    RecyclerView recyclerView;
    PlanAdapter adapter;
    Button add_plan;

    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    private TextView cur_weight;
    private TextView max_weight;
    private TextView min_weight;
    private TextView bmi;
    private TextView height;

    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        add_plan = v.findViewById(R.id.plan_addbutton);

        prefs = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit = prefs.edit();

        cur_weight = (TextView) v.findViewById(R.id.cur_weight);
        max_weight = (TextView) v.findViewById(R.id.max_weight);
        min_weight = (TextView) v.findViewById(R.id.min_weight);
        bmi = (TextView) v.findViewById(R.id.bmi);
        height = (TextView) v.findViewById(R.id.height);

        recyclerView = v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlanAdapter();

        fillStatus();

        /**
         * 모든 운동 기록 가져온다.
         */

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://117.16.137.115:8080/plan/user")
                .addHeader("Authorization", prefs.getString("token",""))
                .build();

        System.out.println(prefs.getString("token",""));

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // 응답 실패
                    Log.i("tag", "응답실패");
                } else {
                    // 응답 성공
                    Log.i("tag", "응답 성공");
                    final String responseData = response.body().string();
                    // 서브 스레드 Ui 변경 할 경우 에러
                    // 메인스레드 Ui 설정

                    Log.d("userInfo : ", responseData);

                    JSONObject jsonObject;
                    String data = null;
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    try {
                        jsonObject = new JSONObject(responseData);
                        data = jsonObject.getString("data");
                        data = gson.toJson(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("data : ", data);
                    System.out.println(data);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(getActivity(), "응답" + responseData, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        // 이후 adapter에 넣어준다.

        adapter.addItem(new PlanItem("2021-05-15","90","행잉-레그라이즈","15"));
        adapter.addItem(new PlanItem("2022-06-20","80","레그라이즈","30"));
        adapter.addItem(new PlanItem("2022-11-31","70","푸쉬업","40"));
        recyclerView.setAdapter(adapter);

        /**
         * 현재, 최고, 최저, BMI, 신장 상황 가져와야 한다.
         * 추가버튼으로 추가하면 갱신 할 것인가?
         */

        /**
         * 액티비티 콜백 함수
         */
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        System.out.println(result.getResultCode());
                        if(result.getResultCode() == RESULT_OK){
                            Intent intent = result.getData();
                            AddPlanDto addPlanDto = (AddPlanDto) intent.getSerializableExtra("addPlanDto");
                            System.out.println("yes");
                            cur_weight.setText(addPlanDto.getSaveWeight());
                            System.out.println("maxWeight: " + Integer.parseInt((String) max_weight.getText()));
                            System.out.println("addPlanDto.saveWeight : " + addPlanDto.getSaveWeight());
                            if(Integer.parseInt((String) max_weight.getText()) < Integer.parseInt(addPlanDto.getSaveWeight())){
                                edit.putString("max_weight", addPlanDto.getSaveWeight());
                                max_weight.setText(addPlanDto.getSaveWeight());
                            }
                            if(Integer.parseInt((String) min_weight.getText()) > Integer.parseInt(addPlanDto.getSaveWeight())){
                                edit.putString("min_weight", addPlanDto.getSaveWeight());
                                min_weight.setText(addPlanDto.getSaveWeight());
                            }
                            double b = Math.pow(BigDecimal.valueOf(Integer.parseInt(prefs.getString("height", "1"))).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN).doubleValue(),2);
                            double bmiResult = BigDecimal.valueOf(Integer.parseInt(addPlanDto.getSaveWeight())).divide(new BigDecimal(b),2,RoundingMode.HALF_EVEN).doubleValue();
                            edit.putString("bmi", String.valueOf(bmiResult));
                            bmi.setText(String.valueOf(bmiResult));
                            System.out.println("end");
                            edit.commit();
                        }
                    }
                });
        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPlan.class);
                resultLauncher.launch(intent);
            }
        });

        return v;
    }

    private void fillStatus() {
        cur_weight.setText(prefs.getString("weight", "None"));
        max_weight.setText(prefs.getString("max_weight", prefs.getString("weight", "None")));
        min_weight.setText(prefs.getString("min_weight", prefs.getString("weight", "None")));
        double b = Math.pow(BigDecimal.valueOf(Integer.parseInt(prefs.getString("height", "1"))).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN).doubleValue(),2);
        double result = BigDecimal.valueOf(Integer.parseInt(prefs.getString("weight", "1"))).divide(new BigDecimal(b),2,RoundingMode.HALF_EVEN).doubleValue();
        bmi.setText(String.valueOf(result));
        height.setText(prefs.getString("height", "None"));
    }

}