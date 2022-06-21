package com.example.v3.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.v3.MainActivity;
import com.example.v3.R;
import com.example.v3.ExerciseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    private Button loginBtn;
    private Button signupViewBtn;
    private EditText loginId;
    private EditText loginPwd;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit = prefs.edit();

        loginBtn = (Button) findViewById(R.id.loginBtn);
        signupViewBtn = (Button) findViewById(R.id.signupViewBtn);

        loginId = (EditText) findViewById(R.id.loginId);
        loginPwd = (EditText) findViewById(R.id.loginPwd);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = loginId.getText().toString().trim();
                String password = loginPwd.getText().toString().trim();

                System.out.println("here");
                if (id.length() > 0 || password.length() > 0) {


                    // get방식 파라미터 추가
//                    HttpUrl.Builder urlBuilder = HttpUrl.parse("117.16.137.155:8080/user/signUp").newBuilder();
//                    urlBuilder.addQueryParameter("v", "1.0"); // 예시
//                    String url = urlBuilder.build().toString();
                    String json;
                    String flag;
                    if(prefs.getString("height", null) == null || prefs.getString("weight", null) == null){
                        flag = "no";
                        json = loginJson(id, password, flag);
                    } else{
                        flag = "yes";
                        json = loginJson(id, password, flag);
                    }
                    System.out.println(json);
                    RequestBody body = RequestBody.create(json, JSON);
                    // POST 파라미터 추가
//                    RequestBody formBody = new FormBody.Builder()
//                            .add("email", id)
//                            .add("password", password)
//                            .add("name", name)
//                            .build();

//                    Log.d("signup json: ", formBody.toString());
                    // 요청 만들기
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://117.16.137.115:8080/user/signIn")
                            .post(body)
                            .build();

//                    Response response = null;
//                    try {
//                        response = client.newCall(request).execute();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(response.body().toString());

                    // 응답 콜백
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
                                /**
                                 * 응답 어떻게 올지 정해야 한다.
                                 */
                                JSONObject jsonObject;
                                String JWToken = null;
                                try {
                                    jsonObject = new JSONObject(responseData);
                                    JWToken = jsonObject.getString("data");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("JWToken!:" + JWToken);
                                if(JWToken != null){
                                    edit.putString("token", JWToken);
                                    edit.commit();
                                }

                                System.out.println("token!: " + prefs.getString("token",""));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(getApplicationContext(), "응답" + responseData, Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }


            }
        });

        signupViewBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });
    }

    public String loginJson(String id, String password, String flag){
        return "{\"email\":\""+ id +"\","
                + "\"password\":\"" + password + "\","
                + "\"flag\":\"" + flag + "\"}";
    }

}
