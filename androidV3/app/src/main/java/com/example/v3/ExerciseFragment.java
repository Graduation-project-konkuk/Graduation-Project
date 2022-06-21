package com.example.v3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.v3.exerciseList.ExerciseAbList;
import com.example.v3.exerciseList.ExerciseEntireList;
import com.example.v3.exerciseList.ExerciseLowerList;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExerciseFragment extends Fragment implements View.OnClickListener{
    Button exercise_list_entire_btn;
    Button exercise_list_lower_btn;
    Button exercise_list_ab_btn;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise, container, false);

        prefs = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit = prefs.edit();

        exercise_list_entire_btn = v.findViewById(R.id.exercise_list_entire_btn);
        exercise_list_lower_btn = v.findViewById(R.id.exercise_list_lower_btn);
        exercise_list_ab_btn = v.findViewById(R.id.exercise_list_ab_btn);

//        exercise_list_entire_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ExerciseEntireList.class);
//                startActivity(intent);
//            }
//        });
//
//        exercise_list_lower_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ExerciseLowerList.class);
//                startActivity(intent);
//            }
//        });
//        exercise_list_ab_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url("http://117.16.137.115:8080/user/info")
//                        .addHeader("Authorization", prefs.getString("token",""))
//                        .build();
//
//                System.out.println(prefs.getString("token",""));
//
////                    Response response = null;
////                    try {
////                        response = client.newCall(request).execute();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    System.out.println(response.body().toString());
//
//                // 응답 콜백
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//                        if (!response.isSuccessful()) {
//                            // 응답 실패
//                            Log.i("tag", "응답실패");
//                        } else {
//                            // 응답 성공
//                            Log.i("tag", "응답 성공");
//                            final String responseData = response.body().string();
//                            // 서브 스레드 Ui 변경 할 경우 에러
//                            // 메인스레드 Ui 설정
//
//                            Log.d("userInfo : ", responseData);
//
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Toast.makeText(getActivity(), "응답" + responseData, Toast.LENGTH_SHORT).show();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
//                Intent intent = new Intent(getActivity(), ExerciseAbList.class);
//                startActivity(intent);
//
//            }
//        });

        exercise_list_entire_btn.setOnClickListener(this);
        exercise_list_lower_btn.setOnClickListener(this);
        exercise_list_ab_btn.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_list_entire_btn:
                Intent intent1 = new Intent(getActivity(), ExerciseEntireList.class);
                startActivity(intent1);
                break;
            case R.id.exercise_list_lower_btn:
                Intent intent2 = new Intent(getActivity(), ExerciseLowerList.class);
                startActivity(intent2);
                break;
            case R.id.exercise_list_ab_btn:
                /** 이쪽부분 때메 메인페이지로 다시 돌아감
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://117.16.137.115:8080/user/info")
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
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                **/
                Intent intent3 = new Intent(getActivity(), ExerciseAbList.class);
                startActivity(intent3);
                break;

        }

    }

}