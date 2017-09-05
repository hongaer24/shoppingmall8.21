package com.example.hongaer.shoppingmall2.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import com.example.hongaer.shoppingmall2.user.bean.LoginBean;
import com.example.hongaer.shoppingmall2.user.view.FindPasswordActivity;
import com.example.hongaer.shoppingmall2.user.view.RegisterActivity;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.ib_login_visible)
    ImageButton ibLoginVisible;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.ib_weibo)
    ImageButton ibWeibo;
    @BindView(R.id.ib_qq)
    ImageButton ibQq;
    @BindView(R.id.ib_wechat)
    ImageButton ibWechat;
    private static final String TAG = "LoginActivity";
    private boolean isShowPassword;
    public static final String QQ_APP_ID = "1106354818";   // 1106221777
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wx79b032a2d6f1fc0a";
  // public static final String QQ_APP_ID = "101417189";
    //需要腾讯提供的一个Tencent类
    private Tencent mTencent;
    //还需要一个IUiListener 的实现类（LogInListener implements IUiListener）
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private boolean isLogIned = false;
    private String username;
    private String password;
    private String url;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);


            //  Bmob.initialize(LoginActivity.this,"90d09ab48f73eb4f3c73d5fc44dc001d");//一定要初始化，否则登录会空指针

            //首先需要用APP ID 获取到一个Tencent实例
            mTencent = Tencent.createInstance(QQ_APP_ID, this.getApplicationContext());
            //初始化一个IUiListener对象，在IUiListener接口的回调方法中获取到有关授权的某些信息
            // （千万别忘了覆写onActivityResult方法，否则接收不到回调）
            mIUiListener = new BaseUiListener();
            //初始化各控件
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private class BaseUiListener implements IUiListener{

        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void WXLogin() {
        WXapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        WXapi.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);

    }
    @OnClick({R.id.ib_login_back, R.id.ib_login_visible, R.id.btn_login, R.id.tv_login_register, R.id.tv_login_forget_pwd, R.id.ib_weibo, R.id.ib_qq, R.id.ib_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_login_back:
                  finish();
                break;
            case R.id.ib_login_visible:
                isShowPassword = !isShowPassword;
                if(isShowPassword){
                    //显示
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                    etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //光标定位到末尾
                    etLoginPwd.setSelection(etLoginPwd.length());

                }else{
                    //隐藏
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //光标定位到末尾
                    etLoginPwd.setSelection(etLoginPwd.length());
                }

                break;
            case R.id.btn_login:
                username=etLoginPhone.getText().toString();
                password=etLoginPwd.getText().toString();
                if(TextUtils.isEmpty( username)||TextUtils.isEmpty(password)){

                    Toast.makeText(this,"账号和密码不能为空",Toast.LENGTH_SHORT).show();
                }
                postJson(username,password);

            break;

              /*  BmobUser user=new BmobUser();

                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<BmobUser>() {

                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                           finish();
                            } else {
                                //loge(e);
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
*/
               // Toast.makeText(this,"登录",Toast.LENGTH_SHORT).show();

            case R.id.tv_login_register:
                 Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

               // Toast.makeText(this,"注册",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login_forget_pwd:

                Intent intent1=new Intent(LoginActivity.this,FindPasswordActivity.class);
                startActivity(intent1);
               // Toast.makeText(this,"忘记密码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_weibo:
                Toast.makeText(this,"微博",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_qq:
                if (!isLogIned) {
                    isLogIned = true;
                    //调用QQ登录，用IUiListener对象作参数
                    if (!mTencent.isSessionValid()) {
                        mTencent.login(LoginActivity.this, "all",mIUiListener);
                    }
                } else {
                    //登出
                    mTencent.logout(LoginActivity.this);
                    isLogIned = false;
                    Toast.makeText(LoginActivity.this, "登录已注销！", Toast.LENGTH_SHORT).show();
                }
               // mTencent.login(LoginActivity.this,"all", mIUiListener);
                break;
            case R.id.ib_wechat:
                WXLogin();
                Toast.makeText(this,"微信",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void postJson(final String username, final String password) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        url = Constans.HOME_URL;
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("login_info", username).add("password", password).build();
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    //Log.i("loging666777", "登录成功======" + json);

                       /*  try {
                             JSONObject  jsonObject=new JSONObject(json);

                             final String usernam= jsonObject.optString("usernam");
                             final int  status=jsonObject.optInt("status");
                             Log.i("loging666","登录成功======"+usernam);*/

                    LoginBean loginBean = JSON.parseObject(json, LoginBean.class);
                    final String username = loginBean.getUsername();
                     final int status = loginBean.getStatus();
                     final String token=loginBean.getAccess_token();

                    Log.i("loging66688","登录成功======"+token);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status == 1) {
                                CacheUtils.saveString(MyApplication.getContex(),"username",username);
                                CacheUtils.saveString(MyApplication.getContex(),"token",token);

                                //setContentView(R.layout.activity_login);
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                              // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              /*  Bundle b=new Bundle();
                                b.putString("username", username);
                                b.putInt("checkid",R.id.rb_user);
                                intent.putExtras(b);*/
                                /* intent.putExtra("username", username);*/
                                 intent.putExtra("checkid",R.id.rb_user);
                                startActivity(intent);

                                Log.i("loging66", "登录成功======" +username);
                            } else {

                                Toast.makeText(LoginActivity.this, "账号或密码有错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } /*catch (JSONException e) {
                             e.printStackTrace();
                         }
*/

                                  /* final LoginBean loginBean = JSON.parseObject(json, LoginBean.class);
                                        final String  username1=loginBean.getSex();*/


            }


        });

     /*   OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        FormBody body = new FormBody.Builder()
                .add("login_info", username)
                .add("password", password).build();

        Request request = new Request.Builder().url(url).post(body).build();
        try {

            Response response = okHttpClient.newCall(request).execute();
            String json = response.body().string();

            if (response.isSuccessful()) {
                // processData(json);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    }

    private void processData(String json) {
        LoginBean loginBean = JSON.parseObject(json, LoginBean.class);
        Log.i("tag888","解析成功===+"+loginBean.getStatus());
    }

}
