package com.example.hongaer.shoppingmall2.user.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonActivity extends Activity {

    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.tv_icon)
    TextView tvIcon;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_sex1)
    TextView tvSex1;
    @BindView(R.id.tv_nick1)
    TextView tvNick1;
    private OptionsPickerView pvOption;
    private String username;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person1);
        ButterKnife.bind(this);
        intData();
    }
    @Override
    public void onResume() {
        super.onResume();
        intData();
    }
    private void intData(){
        Log.i("sex","========="+sex);
        username= CacheUtils.getString(MyApplication.getContex(),"username");
        sex=CacheUtils.getString(MyApplication.getContex(),"sex");

        if(!username.isEmpty()&&!sex.isEmpty()){

            tvNick1.setText(username);
            tvSex1.setText(sex);
        }else {
           tvNick1.setText(" ");
        }
    }

    private void showPickerView() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        list.add("保密");
        pvOption = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String sex = list.get(options1);
                CacheUtils.saveString(MyApplication.getContex(),"sex",sex);
               tvSex1.setText(sex);
            }

        }).setTitleText("请选择性别")
                .setDividerColor(Color.BLACK)
                .setCancelColor(Color.BLACK)
                .setContentTextSize(25)
                .build();
        pvOption.setPicker(list);


    }

    @OnClick({R.id.ib_goods_back, R.id.tv_sex1,R.id.tv_nick1})
    public void onClick(View view) {
        String sex=tvSex1.getText().toString();
      //  Log.i("sex","========="+sex);
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.tv_sex1:

                showPickerView();
                pvOption.show();




                break;
            case R.id.tv_nick1:
                Intent intent=new Intent(this,NickEditActivity.class);
                startActivity(intent);
        }
    }
}