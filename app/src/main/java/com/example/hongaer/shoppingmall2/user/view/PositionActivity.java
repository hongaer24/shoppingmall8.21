package com.example.hongaer.shoppingmall2.user.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MainActivity;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.VirtualkeyboardHeight;
import com.example.hongaer.shoppingmall2.shoppingcart.view.AddSubView;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PositionActivity extends AppCompatActivity {

    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.tv_xinxi)
    TextView tvXinxi;
    @BindView(R.id.tv_IDsafe)
    TextView tvIDsafe;
    @BindView(R.id.bt_push1)
    ToggleButton btPush1;
    @BindView(R.id.bt_push)
    ToggleButton btPush;
    @BindView(R.id.tv_flesh)
    TextView tvFlesh;
    @BindView(R.id.tv_drawback)
    TextView tvDrawback;
    @BindView(R.id.tv_shaerapp)
    TextView tvShaerapp;
    @BindView(R.id.tv_for_us)
    TextView tvForUs;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.bt_loingback)
    Button btLoingback;
    private GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        ButterKnife.bind(this);
        goodsBean= (GoodsBean) getIntent().getSerializableExtra("goodsBean");
        if(goodsBean!=null){

            //Toast.makeText(this,"goodsBean666=="+goodsBean.toString(),Toast.LENGTH_SHORT).show();
            Log.i("goodsBean66666","======="+goodsBean.toString());
        }

    }

    @OnClick({R.id.ib_goods_back, R.id.tv_IDsafe, R.id.bt_loingback,R.id.tv_xinxi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.tv_IDsafe:
               // showPopwindow();
                //CartStorage.getInstance().addData(goodsBean);
               /* Intent intent1=new Intent(PositionActivity.this,IDSafeActivity.class);
                startActivity(intent1);*/
                break;
            case R.id.bt_loingback:
                CacheUtils.deleteString(MyApplication.getContex());
               Intent intent=new Intent(PositionActivity.this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("checkid",R.id.rb_user);
                startActivity(intent);
                break;
            case R.id.tv_xinxi:
                //showPopwindow();
               CartStorage.getInstance().addData(goodsBean);
                break;
        }
    }
    private void showPopwindow() {

        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        // 2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 4 控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(PositionActivity.this).load(Constans.BASE_URL_IMAGES + goodsBean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(goodsBean.getName());
        // 显示价格
        tv_goodinfo_price.setText(goodsBean.getCover_price());

        // 设置最大值和当前值
        //  nas_goodinfo_num.setMaxValue(8);
        nas_goodinfo_num.setValue(goodsBean.getNumber());


        nas_goodinfo_num.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                goodsBean.setNumber(value);
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                //添加购物车
                CartStorage.getInstance().addData(goodsBean);
                Log.e("TAG", "66:" + goodsBean.toString());
                Toast.makeText(PositionActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(PositionActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(PositionActivity.this));

    }

}
