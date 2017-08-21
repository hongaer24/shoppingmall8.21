package com.example.hongaer.shoppingmall2.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IDSafeActivity extends AppCompatActivity {

    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.tv_iponenumber)
    TextView tvIponenumber;
    @BindView(R.id.tv_weixin)
    TextView tvWeixin;
    @BindView(R.id.tv_QQ)
    TextView tvQQ;
    @BindView(R.id.tv_changepassword)
    TextView tvChangepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idsafe);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ib_goods_back, R.id.tv_iponenumber, R.id.tv_weixin, R.id.tv_QQ, R.id.tv_changepassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.tv_iponenumber:
                break;
            case R.id.tv_weixin:
                break;
            case R.id.tv_QQ:
                break;
            case R.id.tv_changepassword:
                Intent intent =new Intent(IDSafeActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
