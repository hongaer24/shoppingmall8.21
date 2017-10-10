package com.example.hongaer.shoppingmall2.user.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MainActivity;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.example.hongaer.shoppingmall2.utils.Http;
import com.example.hongaer.shoppingmall2.utils.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wx79b032a2d6f1fc0a";
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        ButterKnife.bind(this);
        WXapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        WXapi.registerApp(WX_APP_ID);


    }

    @OnClick({R.id.ib_goods_back, R.id.tv_IDsafe, R.id.bt_loingback,R.id.tv_xinxi,R.id.tv_for_us,R.id.tv_shaerapp,R.id.tv_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.tv_IDsafe:
                Intent intent1 = new Intent(PositionActivity.this, IDSafeActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_loingback:
                CacheUtils.deleteString(MyApplication.getContex());
                Intent intent = new Intent(PositionActivity.this, MainActivity.class);
                intent.putExtra("checkid", R.id.rb_user);
                startActivity(intent);
                break;
            case R.id.tv_xinxi:
                Intent intent2 = new Intent(this, PersonActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_for_us:
                Intent intent3 = new Intent(this, ForUsActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_shaerapp:
                showPopWindow();
                //shareText2WX("好人",1);
                break;
            case R.id.tv_check:
                checkVersion();
                try {
                    Toast.makeText(this, "版本号"+getVersionName(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //shareText2WX("好人",1);
                break;
        }
    }
    private void showPopWindow() {
        //获取view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.share_popwindow, null);

        //获取宽高
        final PopupWindow popupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        //设置popWindow弹出窗体可点击
        popupWindow.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.share_popWindow_anim_style);

        //在底部显示
        popupWindow.showAtLocation(PositionActivity.this.findViewById(R.id.ll_position),
                Gravity.BOTTOM,0,0);
        ImageButton weichat = (ImageButton) view.findViewById(R.id.ib_weixin);
        ImageButton friend = (ImageButton) view.findViewById(R.id.ib_friend);
        TextView cancel=(TextView) view.findViewById(R.id.tv_cancel);
            weichat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      shareUrl(1);
                }
            });

        friend.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                      shareUrl(0);
               }
           });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 popupWindow.dismiss();
            }
        });

        //popWindow消失监听方法
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }
    public  void  shareUrl(int flag){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.jinquemall.com/";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "金雀钢城,你值得拥有";
        msg.description = "金雀钢城，你值得拥有";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene =flag==0 ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        boolean fla = WXapi.sendReq(req);
        api.sendReq(req);
    }
    private String buildTransaction(final String type){
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    //检测本程序的版本，这里假设从服务器中获取到最新的版本号为3
    public void checkVersion() {
        String url= Constans.VERSION_URL;
        Map<String, String> map = new HashMap<>();
        map.put("apptype","0");
        Http.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                            if(response.isSuccessful()){
                               String json=response.body().string();
                                Log.i("66688","登录成功======"+json);
                                //LoginBean loginBean = JSON.parseObject(json, LoginBean.class);




                            }
            }
        });
        //如果检测本程序的版本号小于服务器的版本号，那么提示用户更新
        if (getVersionCode() < 3) {
            showDialogUpdate();//弹出提示版本更新的对话框

        }else{
            //否则吐司，说现在是最新的版本
            Toast.makeText(this,"当前已经是最新的版本",Toast.LENGTH_SHORT).show();

        }
    }
    /**
     * 提示版本更新的对话框
     */
    private void showDialogUpdate() {
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                // 设置提示框的图标
                        setIcon(R.mipmap.ic_launcher).
                // 设置要显示的信息
                        setMessage("发现新版本！请及时更新").
                // 设置确定按钮
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "选择确定哦", 0).show();
                        loadNewVersionProgress();//下载最新的版本程序
                    }
                }).

                // 设置取消按钮,null是什么都不做，并关闭对话框
                        setNegativeButton("取消", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();


    }
    private void loadNewVersionProgress() {
        final   String uri="http://appdl.hicloud.com/dl/appdl/application/apk/e1/e13984450dc34a0a92d957fdf02fdb71/com.nbjinque.jqmall.1709031502.apk?sign=portal@portal1505390973626&source=portalsite";
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }}.start();
    }
    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }
    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(uri);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time= System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time+"updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }
    /*
   * 获取当前程序的版本名
   */
    private String getVersionName() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
       Log.i("8888", "版本号" + packInfo.versionCode);
        Log.i("8888", "版本名" + packInfo.versionName);
        return packInfo.versionName;

    }


    /*
     * 获取当前程序的版本号
     */
    private int getVersionCode() {
        try {

            //获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            Log.e("TAG", "版本号" + packInfo.versionCode);
            Log.e("TAG", "版本名" + packInfo.versionName);
            return packInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return  1;
    }
   /* private void showPopwindow() {

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

    }*/

}
