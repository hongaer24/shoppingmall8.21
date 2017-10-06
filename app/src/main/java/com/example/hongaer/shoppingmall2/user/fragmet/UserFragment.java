package com.example.hongaer.shoppingmall2.user.fragmet;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.LoginActivity;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.user.view.GoodsActivity;
import com.example.hongaer.shoppingmall2.user.view.PersonActivity;
import com.example.hongaer.shoppingmall2.user.view.Position2Activity;
import com.example.hongaer.shoppingmall2.user.view.PositionActivity;
import com.example.hongaer.shoppingmall2.user.view.address.AddressListActivity;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hongaer on 2017/7/1.
 */

public class UserFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_all_order)
    TextView tvAllOrder;
    @BindView(R.id.tv_user_pay)
    TextView tvUserPay;
    @BindView(R.id.tv_doing_pay)
    TextView tvDoingPay;
    @BindView(R.id.tv_user_receive)
    TextView tvUserReceive;
    @BindView(R.id.tv_user_finish)
    TextView tvUserFinish;
    @BindView(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @BindView(R.id.ib_user_message)
    ImageButton ibUserMessage;
    private String username;
    @Nullable

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);

        ButterKnife.bind(this, view);

        username= CacheUtils.getString(MyApplication.getContex(),"username");


        if(!username.isEmpty()){

            tvUsername.setText(username);
        }else {
            tvUsername.setText("登录/注册");
        }


        return view;
    }

    public void initData() {
        super.initData();

    }

  @OnClick({R.id.ib_user_icon_avator, R.id.tv_username, R.id.rl_header, R.id.tv_all_order, R.id.tv_user_pay, R.id.tv_doing_pay, R.id.tv_user_receive, R.id.tv_user_finish, R.id.tv_user_drawback, R.id.tv_position, R.id.ib_user_message,R.id.tv_address})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_username:
                Toast.makeText(mContext, "登录注册", Toast.LENGTH_SHORT).show();
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, PersonActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext,LoginActivity.class);
                    startActivity(intent7);
                }
                break;
            case R.id.tv_position:
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, PositionActivity.class);
                     startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext, Position2Activity.class);
                    startActivity(intent7);
                }
                Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_address:
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, AddressListActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext,LoginActivity.class);
                    startActivity(intent7);
                }

                Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_user_message:
                Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_all_order:
               // Toast.makeText(mContext, "查看全部订单", Toast.LENGTH_SHORT).show();
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, GoodsActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent7);
                }
                break;
            case R.id.tv_user_pay:
                // Toast.makeText(mContext,"待付款",Toast.LENGTH_SHORT).show();
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, GoodsActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent7);
                }
                break;
            case R.id.tv_user_receive:
                // Toast.makeText(mContext,"待收货",Toast.LENGTH_SHORT).show();
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, GoodsActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent7);
                }
                break;
            case R.id.tv_user_finish:
                // Toast.makeText(mContext,"待评价",Toast.LENGTH_SHORT).show();
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, GoodsActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent7);
                }
                break;
            case R.id.tv_user_drawback:
                // Toast.makeText(mContext,"退款",Toast.LENGTH_SHORT).show();
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, GoodsActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent7);
                }
                break;
            case R.id.tv_doing_pay:
                // Toast.makeText(mContext,"生产中",Toast.LENGTH_SHORT).show();
                if(!username.isEmpty()){
                    Intent intent7 = new Intent(mContext, GoodsActivity.class);
                    startActivity(intent7);
                }else{
                    Intent intent7 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent7);
                }
                break;

        }
    }

}
