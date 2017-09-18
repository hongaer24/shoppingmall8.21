package com.example.hongaer.shoppingmall2.user.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NickEditActivity extends Activity {

    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.et_nick)
    EditText etNick;
    @BindView(R.id.bt_save)
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_edit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ib_goods_back, R.id.et_nick, R.id.bt_save})
    public void onClick(View view) {
          String nick=etNick.getText().toString();
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.et_nick:
                break;
            case R.id.bt_save:
                CacheUtils.saveString(MyApplication.getContex(),"username",nick);
                finish();
                break;
        }
    }
}
