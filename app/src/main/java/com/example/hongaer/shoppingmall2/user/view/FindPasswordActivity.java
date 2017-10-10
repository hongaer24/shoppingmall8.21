package com.example.hongaer.shoppingmall2.user.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.user.bean.LoginBean;
import com.example.hongaer.shoppingmall2.user.bean.PasswordBean;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.example.hongaer.shoppingmall2.utils.Http;

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

public class FindPasswordActivity extends AppCompatActivity {

    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @BindView(R.id.et_sign_phone)
    EditText etSignPhone;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.Message_btn)
    Button MessageBtn;
    @BindView(R.id.et_sign_pwd)
    EditText etSignPwd;
    @BindView(R.id.ib_login_visible)
    ImageButton ibLoginVisible;
    @BindView(R.id.btn_sign)
    Button btnSign;
    private String phoneNumber;
    private String url = Constans.SEND_MESSAGE__URL;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(FindPasswordActivity.this, "验证码已经发送，请尽快使用", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            //Message_btn.setBackgroundResource(R.drawable.button_shape02);
                            MessageBtn.setText("重新发送" + millisUntilFinished / 1000 + "秒");
                        }

                        @Override
                        public void onFinish() {
                            MessageBtn.setClickable(true);
                            //Message_btn.setBackgroundResource(R.drawable.button_shape);
                            MessageBtn.setText("重新发送");
                        }


                    }.start();

                    break;
                case 0:
                    Toast.makeText(FindPasswordActivity.this, "申请验证码时间过短，请稍后尝试", Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };

    @OnClick({R.id.et_sign_phone, R.id.password_et, R.id.Message_btn, R.id.et_sign_pwd, R.id.btn_sign,R.id.ib_login_back})
    public void onClick(View view) {
        phoneNumber = etSignPhone.getText().toString();
        String newpassword = etSignPwd.getText().toString();
        String verify = passwordEt.getText().toString();
        switch (view.getId()) {
            case R.id.ib_login_back:
                finish();
                break;
            case R.id.et_sign_phone:
                break;
            case R.id.password_et:
                break;
            case R.id.Message_btn:
                if (phoneNumber.length() == 0) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneNumber.length() != 11) {
                    Toast.makeText(this, "请输入有效手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                getHttp();
                break;
            case R.id.et_sign_pwd:
                break;
            case R.id.btn_sign:
                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(newpassword) || TextUtils.isEmpty(verify)) {

                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (phoneNumber.length() < 11) {

                    Toast.makeText(this, "请输入有效手机号", Toast.LENGTH_SHORT).show();
                    return;


                }
                postJson(phoneNumber, newpassword, verify);
                break;
        }
    }

    private void postJson(String phoneNumber, String newpassword, String verify) {
        String reg_url = Constans.FIND_PASSWOED__URL;
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("mobile", phoneNumber).add("password", newpassword).add("mobile_code", verify).build();
        Request request = new Request.Builder().url(reg_url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.i("hong888", "重置成功======" + json);
                    LoginBean loginBean = JSON.parseObject(json, LoginBean.class);
                    final int staus = loginBean.getStatus();
                    // String username=loginBean.getUsername();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (staus == 1) {
                                Toast.makeText(FindPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if(staus==0){
                                Toast.makeText(FindPasswordActivity.this, "验证码时间间隔过短", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                Toast.makeText(FindPasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }

    private void getHttp() {
        final String new_url=url+phoneNumber;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg_data_json = Http.get(new_url);
                    Log.i("msg666", "请求数据成功=======" + msg_data_json);

                    PasswordBean passwordBean = JSON.parseObject(msg_data_json, PasswordBean.class);
                    status = passwordBean.getStatus();
                    Message msg = new Message();
                    msg.what = status;

                    mHandler.sendMessage(msg);

                   /* if(status==1){
                        Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(RegisterActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                    }
*/

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}

