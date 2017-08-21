package com.example.hongaer.shoppingmall2.user.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity {


    @BindView(R.id.ib_user_icon_avator1)
    ImageButton ibUserIconAvator1;
    @BindView(R.id.tv_username1)
    TextView tvUsername1;
    @BindView(R.id.rl_header1)
    RelativeLayout rlHeader1;
    @BindView(R.id.tv_all_order1)
    TextView tvAllOrder1;
    @BindView(R.id.tv_user_pay1)
    TextView tvUserPay1;
    @BindView(R.id.tv_doing_pay1)
    TextView tvDoingPay1;
    @BindView(R.id.tv_user_receive1)
    TextView tvUserReceive1;
    @BindView(R.id.tv_user_finish1)
    TextView tvUserFinish1;
    @BindView(R.id.tv_user_drawback1)
    TextView tvUserDrawback1;
    @BindView(R.id.tv_user_location1)
    TextView tvUserLocation;
    @BindView(R.id.tv_user_collect)
    TextView tvUserCollect;
    @BindView(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.ib_user_message1)
    ImageButton ibUserMessage1;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
       // String id=getIntent().getStringExtra("username");
       /* tvUsername1.setText(id);*/


    }


    @OnClick({R.id.tv_username1, R.id.rl_header1, R.id.tv_all_order1, R.id.tv_user_pay1, R.id.tv_doing_pay1, R.id.tv_user_receive1, R.id.tv_user_finish1, R.id.tv_user_drawback1, R.id.tv_user_location1, R.id.tv_user_collect, R.id.tv_user_coupon, R.id.ib_user_message1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_username1:
                break;
            case R.id.rl_header1:
                break;
            case R.id.tv_all_order1:
                Intent intent2 = new Intent(UserActivity.this,GoodsActivity.class);
                startActivity(intent2);

                break;
            case R.id.tv_user_pay1:
                break;
            case R.id.tv_doing_pay1:
                break;
            case R.id.tv_user_receive1:
                break;
            case R.id.tv_user_finish1:
                break;
            case R.id.tv_user_drawback1:
                break;
            case R.id.tv_user_location1:
                Intent intent1 = new Intent(UserActivity.this,PositionActivity.class);
                this.startActivity(intent1);

                break;
            case R.id.tv_user_collect:
                //地址管理
                break;
            case R.id.tv_user_coupon:
                break;
            case R.id.ib_user_message1:
                break;
        }
    }
}
