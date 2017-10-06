package com.example.hongaer.shoppingmall2.app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.home.fragment.HomeFragment;
import com.example.hongaer.shoppingmall2.shoppingcart.fragmet.ShoppingCartFragment;
import com.example.hongaer.shoppingmall2.type.fragmet.TypeFragment;
import com.example.hongaer.shoppingmall2.user.fragmet.UserFragment;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_type)
    RadioButton rbType;
   /* @BindView(R.id.rb_community)*/
    RadioButton rbCommunity;
    @BindView(R.id.rb_cart)
    RadioButton rbCart;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BaseFragment> fragments;
    private int position=0;
    private Fragment tempFragemnt;
    private String username;
    private Context context;
    private Context mContext;
    private static boolean isExit = false;

   Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /* CacheUtils.deleteString(MyApplication.getContex());*/
        Log.i("tag","getstring====="+  CacheUtils.getString(MyApplication.getContex(),"aer1"));
        //SharedPreferences sp=getSharedPreferences("hongeer24", Context.MODE_PRIVATE);
      /*  SharedPreferences sp=getSharedPreferences("aer",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("user","123");
        editor.commit();*/
       // CacheUtils.saveString(MyApplication.getContex(),"aer1","456");
        //SharedPreferences sp=mContext.getSharedPreferences("HONG", Context.MODE_PRIVATE);
/*
        String username=getIntent().getStringExtra("username");*/


        initfragment();
        initListener();

      /*  initData();*/



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
    private void initfragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
       // fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());

    }
    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;

                    case R.id.rb_cart:
                        position = 2;
                        break;
                    case R.id.rb_user:
                        position = 3;
                       /* initData();*/
                        break;
                }
                BaseFragment baseFragment = getFragment(position);
                switchFragment(tempFragemnt, baseFragment);
            }
        });
//默认设置首页
        rgMain.check(R.id.rb_home);
    }
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragemnt != nextFragment) {
            tempFragemnt = nextFragment;
            if (nextFragment != null) {
                      FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
//判断 nextFragment 是否添加
                if (!nextFragment.isAdded()) {
//隐藏当前 Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
//隐藏当前 Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
   /* public  void initData(){

        Bundle bundle=getIntent().getExtras();
        int checkId=bundle.getInt("checkid");
        username=bundle.getString("username");

        switch (checkId){
            case R.id.rb_home:
                //切换到主页面
                rgMain.check(R.id.rb_home);
                break;
            case R.id.rb_cart:
                //切换到购物车
                rgMain.check(R.id.rb_cart);
                break;
            case R.id.rb_user:
                //切换到购物车
                rgMain.check(R.id.rb_user);
                break;
        }
    }*/

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //得到id
        int  checkId = intent.getIntExtra("checkid",R.id.rb_user);
            // username=intent.getStringExtra("username");

      /* Bundle bundle=this.getIntent().getExtras();
             int checkId=bundle.getInt("checkid");
        username=bundle.getString("username");
*/
        switch (checkId){

            case R.id.rb_home:
                //切换到主页面
                rgMain.check(R.id.rb_home);
                break;
            case R.id.rb_cart:
                //切换到购物车
                rgMain.check(R.id.rb_cart);
                break;
            case R.id.rb_user:
                //切换到购物车


                rgMain.check(R.id.rb_user);
                initData();


                break;
        }
    }
    private void initData() {

      /*  username= CacheUtils.getString(MyApplication.getContex(),"username");*/
        UserFragment userFragment=new UserFragment();
     /*   Bundle bundle=new Bundle();
        bundle.putString("str",username);
        userFragment.setArguments(bundle);*/

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.frameLayout,userFragment);
        transaction.commit();
    }

}
