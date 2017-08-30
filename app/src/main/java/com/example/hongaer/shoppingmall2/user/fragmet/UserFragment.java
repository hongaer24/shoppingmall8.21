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
import com.example.hongaer.shoppingmall2.user.view.Position2Activity;
import com.example.hongaer.shoppingmall2.user.view.PositionActivity;
import com.example.hongaer.shoppingmall2.user.view.address.AddressListActivity;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;

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
    /*@BindView(R.id.scrollview)
    GradationScrollView scrollview;*/
   /* @BindView(R.id.tv_usercenter)
    TextView tvUsercenter;*/
   /* @BindView(R.id.ib_user_setting)
    ImageButton ibUserSetting;*/
    @BindView(R.id.ib_user_message)
    ImageButton ibUserMessage;
    Unbinder unbinder1;
    private int height;
    private String str;
    private String username;
    private String url = Constans.PASSWORD_URL;
    @Nullable

  /*  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user,null);
        ButterKnife.bind(this, view);
        String str= getArguments().getString("str");
        tvUsername.setText(str);
        return view;
    }*/

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);

        ButterKnife.bind(this, view);
      /*  Bundle bundle=getArguments();*/
       /* if(bundle!=null){
           tvUsername.setText(bundle.getString("str"));
        }*/
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
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json= Http.get(url);
                    Log.i("tag88888", "请求数据成功======="+json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
*/


        /*Http.get(url);
        Log.i("tag1234","请求数据成功====="+ json);
*/

    }
   /* private void initListeners() {

        ViewTreeObserver vto = rlHeader.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                //得到头部相对布局的高度


                height = rlHeader.getHeight();

                //����ScrollView��������
                scrollview.setScrollViewListener(UserFragment.this);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        tvUsercenter.setTextColor(Color.argb((int)0, 255,255,255));
        tvUsercenter.setBackgroundColor(Color.argb((int)0, 0,0,255));

        return rootView;
    }
*/

  /*  public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
*/
  @OnClick({R.id.ib_user_icon_avator, R.id.tv_username, R.id.rl_header, R.id.tv_all_order, R.id.tv_user_pay, R.id.tv_doing_pay, R.id.tv_user_receive, R.id.tv_user_finish, R.id.tv_user_drawback, R.id.tv_position, R.id.ib_user_message,R.id.tv_address})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_username:
                Toast.makeText(mContext, "登录注册", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_position:
               /* Intent intent8 = new Intent(mContext, ResetPasswordActivity.class);
                startActivity(intent8);*/
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

  /*  @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //���ñ���ı�����ɫ  -͸��
            tvUsercenter.setBackgroundColor(Color.argb((int) 0, 0,0,255));
        } else if (y > 0 && y <= height) { //��������С��bannerͼ�ĸ߶�ʱ�����ñ�����������ɫ��ɫ͸���Ƚ���
            float scale = (float) y / height;
            float alpha = (255 * scale);
            //�������� �� �ܾ��� = �ı��͸���� �� ��͸����
            //�ı��͸���� = (�������� ���ܾ���) *��͸����

            tvUsercenter.setTextColor(Color.argb((int) alpha, 255,255,255));
            tvUsercenter.setBackgroundColor(Color.argb((int) alpha, 0,0,255));
        } else {
            //������banner����������ͨ��ɫ - ��͸��
            tvUsercenter.setBackgroundColor(Color.argb((int) 255, 0,0,255));
        }
    }*/
}
