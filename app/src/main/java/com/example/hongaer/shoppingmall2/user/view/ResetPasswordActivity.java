package com.example.hongaer.shoppingmall2.user.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.user.bean.PasswordBean;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.et_origin_password)
    EditText etOriginPassword;
    @BindView(R.id.et_change_password)
    EditText etChangePassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    private String url;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ib_goods_back, R.id.et_origin_password, R.id.et_change_password, R.id.et_new_password, R.id.btn_commit, R.id.tv_forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.et_origin_password:
                break;
            case R.id.et_change_password:
                break;
            case R.id.et_new_password:
                break;
            case R.id.btn_commit:
                token= CacheUtils.getString(MyApplication.getContex(),"token");
                Log.i("token666", "登录成功======" + token);
                String fpassword = etOriginPassword.getText().toString();
                String password = etChangePassword.getText().toString();
                String repassword = etNewPassword.getText().toString();
                if (TextUtils.isEmpty(fpassword) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {

                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }/*else if(password!=repassword){
                    Toast.makeText(this, "重置密码输入不一致", Toast.LENGTH_SHORT).show();
                }*/
                postJson2(fpassword,password,repassword,token);

                break;
            case R.id.tv_forget_password:
                break;
            }
        }

    private void postJson2(String fpassword, final String password, final String repassword, final String token) {

        url = Constans.PASSWORD_URL;


        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("fpassword", fpassword).add("password", password).add("repassword", repassword).add("token",token).build();
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    Log.i("token9999", "登录成功======" + json);


                    //final String usernam= jsonObject.optString("usernam");
                    //final int  status=jsonObject.optInt("status");
                    //Log.i("loging666","登录成功======"+usernam);
                    //  Log.i("loging666", "登录成功======" + username);

                    final PasswordBean passwordBean = JSON.parseObject(json, PasswordBean.class);
                    final int status = passwordBean.getStatus();
                    // Log.i("loging666", "登录成功======" + passwordBean);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status == 1) {
                                Toast.makeText(ResetPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                                   /* Intent intent = new Intent(ResetPasswordActivity.this, UserActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);*/

                                // Log.i("loging66", "登录成功======" + username);
                            } else if(password!=repassword) {

                                Toast.makeText(ResetPasswordActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ResetPasswordActivity.this, "原始密码输入错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        }
    }


