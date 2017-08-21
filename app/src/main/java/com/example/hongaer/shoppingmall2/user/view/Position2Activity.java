package com.example.hongaer.shoppingmall2.user.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Position2Activity extends AppCompatActivity {

    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.bt_push1)
    ToggleButton btPush1;
    @BindView(R.id.tv_flesh)
    TextView tvFlesh;
    @BindView(R.id.tv_drawback)
    TextView tvDrawback;
    @BindView(R.id.tv_shaerapp)
    TextView tvShaerapp;
    @BindView(R.id.tv_for_us)
    TextView tvForUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ib_goods_back, R.id.tv_flesh, R.id.tv_drawback, R.id.tv_shaerapp, R.id.tv_for_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:

                finish();
                break;
            case R.id.tv_flesh:
                break;
            case R.id.tv_drawback:
                break;
            case R.id.tv_shaerapp:
                break;
            case R.id.tv_for_us:
                break;
        }
    }
}
