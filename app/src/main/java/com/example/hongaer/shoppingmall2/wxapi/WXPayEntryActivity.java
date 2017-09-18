package com.example.hongaer.shoppingmall2.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by hongaer on 2017/9/12.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private String WX_APP_ID = "wx79b032a2d6f1fc0a";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        try {
            api.handleIntent(getIntent(),this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }


    public void onReq(BaseReq req) {
    }


    public void onResp(BaseResp resp) {
        int result = 0;
        if (resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            if (resp.errCode==0){
                Toast.makeText(this,"支付成功",Toast.LENGTH_LONG).show();
            }else if (resp.errCode==-2){
                Toast.makeText(this,"取消支付",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"支付失败",Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }


}
