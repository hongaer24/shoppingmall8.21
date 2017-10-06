package com.example.hongaer.shoppingmall2.user.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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
import com.example.hongaer.shoppingmall2.app.LoginActivity;
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


public class RegisterActivity extends Activity {

    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @BindView(R.id.et_sign_phone)
    EditText etSignPhone;
    @BindView(R.id.et_sign_pwd)
    EditText etSignPwd;
    @BindView(R.id.ib_login_visible)
    ImageButton ibLoginVisible;
    @BindView(R.id.btn_sign)
    Button btnSign;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.password_et)
    TextView passWorrd_et;
    @BindView(R.id.Message_btn)
    TextView messager_btn;
  //  private String url= Constans.BASE_URL_JQ_MSG+phoneNumber;
     public String url=Constans.MSG_URL;
      private String phoneNumber;

    private int status;
    // private  String url=Constans.BASE_URL_JQ_MSG+phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //Bmob.initialize(RegisterActivity.this,"90d09ab48f73eb4f3c73d5fc44dc001d");


}
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                     Toast.makeText(RegisterActivity.this, "验证码已经发送，请尽快使用", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            //Message_btn.setBackgroundResource(R.drawable.button_shape02);
                            messager_btn.setText("重新发送" + millisUntilFinished / 1000 + "秒");
                        }

                        @Override
                        public void onFinish() {
                            messager_btn.setClickable(true);
                            //Message_btn.setBackgroundResource(R.drawable.button_shape);
                            messager_btn.setText("重新发送");
                        }


                    }.start();

                    break;
                case 0:
                      Toast.makeText(RegisterActivity.this,"此号已经注册过",Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };


    @OnClick({R.id.Message_btn,R.id.ib_login_back, R.id.ib_login_visible, R.id.btn_sign, R.id.tv_register})
    public void onClick(View view) {
        phoneNumber = etSignPhone.getText().toString();
        String newpassword = etSignPwd.getText().toString();
        String verify = passWorrd_et.getText().toString();

        switch (view.getId()) {
            case R.id.ib_login_back:
                finish();
                break;

            case R.id.ib_login_visible:
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
            case R.id.btn_sign:

                //非空验证
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

            case R.id.tv_register:
                Intent intent =new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
                 /* BmobSMS.requestSMSCode(phoneNumber, "注册模板", new QueryListener<Integer>() {
                      @Override
                      public void done(Integer integer, BmobException e) {
                          if(e==null){//验证码发送成功
                              messager_btn.setClickable(true);
                              messager_btn.setBackgroundColor(Color.parseColor("#FFD4D4D7"));

                              Toast.makeText(RegisterActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();

                              new CountDownTimer(30000, 1000) {
                                  @Override
                                  public void onTick(long millisUntilFinished) {
                                      //Message_btn.setBackgroundResource(R.drawable.button_shape02);
                                      messager_btn.setText("重新发送"+millisUntilFinished / 1000 + "秒");
                                  }

                                  @Override
                                  public void onFinish() {
                                      messager_btn.setClickable(true);
                                      //Message_btn.setBackgroundResource(R.drawable.button_shape);
                                      messager_btn.setText("重新发送");
                                  }
                              }.start();
                          }
                      else{
                              Toast.makeText(RegisterActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                          }
                      }

                  });*/

                // getHttp();
                /*   BmobSMS.verifySmsCode(phoneNumber,verify, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                //验证码正确 添加用户信息
                                String phoneNumber=etSignPhone.getText().toString();
                                String  newpassword=etSignPwd.getText().toString();
                                BmobUser user=new BmobUser();
                                user.setUsername(phoneNumber);
                                user.setPassword(newpassword);
                                user.signUp(new SaveListener< BmobUser>() {
                                    public void done( BmobUser s, BmobException e) {
                                        if(e==null){
                                           *//* Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            finish();*//*

                                        }else{

                                            //loge(e);
                                        *//*    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();*//*
                                        }
                                    }
                                });
                                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                                  startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });*/



                /*BmobUser user=new BmobUser();
                user.setUsername(phoneNumber);
                user.setPassword(newpassword);
                user.signUp(new SaveListener<User>() {
                    public void done( User s, BmobException e) {
                        if(e==null){
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            //loge(e);
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
               /* MyUser myUser=new MyUser();
                myUser.setNumber(phoneNumber);
                myUser.setPassnumber(newpassword);
                myUser.signUp(new SaveListener<MyUser>() {
                    public void done( MyUser s, BmobException e) {
                        if(e==null){
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }else{
                            //loge(e);
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                });*/
               /* myUser.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            RegisterActivity.this.finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
               /* myUser.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                });
*/



    private void postJson(String mobile,String password,String code) {
      String  reg_url = Constans.reg_URL;
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("mobile", mobile).add("password", password).add("mobile_code",code).build();
        Request request = new Request.Builder().url(reg_url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                   if(response.isSuccessful()){
                          String json=response.body().string();
                       Log.i("hong66", "注册成功======" + json);

                       LoginBean loginBean=JSON.parseObject(json,LoginBean.class);
                           final int staus=loginBean.getStatus();
                           String username=loginBean.getUsername();
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                      if(staus==1){
                                          Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                      }else {

                                          Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });

                   }

            }
        });

    }

    private void getHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s=url+phoneNumber;
                    String msg_data_json = Http.get(s);
                    Log.i("msg888999", "请求数据成功=======" + msg_data_json);
                   /* Log.i("url888999", "请求数据成功=======" +phoneNumber);
                    Log.i("url888999", "请求数据成功=======" +url);*/
                    //parseJson(province_data_json);
                   // getJson(province_data_json);
                PasswordBean passwordBean= JSON.parseObject(msg_data_json, PasswordBean.class);
                      status=passwordBean.getStatus();
                       Message msg=new Message();
                       msg.what=status;

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


