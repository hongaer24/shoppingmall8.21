package com.example.hongaer.shoppingmall2.shoppingcart.fragmet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baoyz.widget.PullRefreshLayout;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.home.bean.DatasBean;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.home.bean.ResultDataBean;
import com.example.hongaer.shoppingmall2.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.hongaer.shoppingmall2.shoppingcart.bean.StoreBean;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;
import com.example.hongaer.shoppingmall2.shoppingcart.view.ConfirmOrderActivity;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.example.hongaer.shoppingmall2.utils.Http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by hongaer on 2017/7/1.
 */

public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener, ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface, ShoppingCartAdapter.GroupEdtorListener {
    public static final int JSON_FLAG = 2;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 3;
    @BindView(R.id.tv_title1)
    TextView tvTitle;
    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.exListView)
    ExpandableListView exListView;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_shar)
    LinearLayout llShar;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    Unbinder unbinder;
    @BindView(R.id.layout_cart_empty)
    LinearLayout cart_empty;
    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout swipeRefreshLayout;

    private ShoppingCartAdapter adapter;
    private ResultDataBean.ResultBean resultbean;
    //private RecyclerView recyclerView;
    private GoodsBean goodsBean;
    private List<String> storeNameList = new ArrayList<>();
    private List<StoreBean> groups = new ArrayList<StoreBean>();
    private Map<String, List<GoodsBean>> children = new HashMap<String, List<GoodsBean>>();
    private List<DatasBean.DataBean> bean;
    private List<GoodsBean> goodsBeanList;
    private StoreBean tempData;
    private int tempbean;
    private SparseArray<StoreBean> sparseArray;
    Set<String> storeSet = new HashSet<String>();
    private StoreBean storeBean;
    private int flag = 0;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private boolean flag2 = true;
    private BaseBundle bundle;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.bind(this, view);
        String token = CacheUtils.getString(MyApplication.getContex(), "token");
        if (!token.isEmpty()) {
            initData();
        } else {
            clearCart();
        }


        return view;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case JSON_FLAG:
                    showData();
                    break;
            }
        }

    };

    public void initData() {
        super.initData();

        //Log.e("6666", "请求数据成功=======888888" );
        String reg_url = Constans.SHOPPINGCART_URL;
        final String token = CacheUtils.getString(MyApplication.getContex(), "token");
        Log.e("6666888", "请求数据成功=======" + token);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("token", token).build();
        Request request = new Request.Builder().url(reg_url).post(body).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.i("tag666", "请求数据成功=======" + json);

                    // Log.i("tag22233333", "请求数据成功=======" + token);
                    // processData(json);
                    //List<GoodsBean> goodsBeanList = processData(json);
                    processData(json);
                    intDatas();
                    mHandler.sendEmptyMessage(JSON_FLAG);
                    Message msg = new Message();
                    msg.what = JSON_FLAG;
                    msg.obj = goodsBeanList;

                }
            }
        });


    }

    private void processData(String json) {


        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            // Log.i("22233333", "请求数据成功=======" + jsonArray);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject nameObject = jsonArray.optJSONObject(i);
                    String storeName = nameObject.getString("sellername");
                    storeSet.add(storeName);
                    Log.i("22233333", "请求数据成功=======" + storeSet.size());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatasBean dataBean = JSON.parseObject(json, DatasBean.class);
        bean = dataBean.getData();
        if (bean != null && bean.size() > 0) {
            for (int i = 0; i < bean.size(); i++) {
                DatasBean.DataBean datasbean = bean.get(i);
                goodsBean = new GoodsBean();
                goodsBean.setName(datasbean.getName());
                goodsBean.setSellername(datasbean.getSellername());
                goodsBean.setCover_price(datasbean.getSellprice());
                goodsBean.setFigure(datasbean.getImg());
                goodsBean.setGoods_id(datasbean.getId());
                goodsBean.setType(datasbean.getType());

                Log.i("tag22233333", "请求数据成功=======" + goodsBean);
                CartStorage.getInstance().addData(goodsBean);

            }
        }

    }

    private void intDatas() {
        Set<StoreBean> hashSet = new HashSet<StoreBean>();
        Iterator it = storeSet.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            storeBean = new StoreBean(obj.toString());
            hashSet.add(storeBean);
        }
        groups = new ArrayList<>(hashSet);
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < goodsBeanList.size(); i++) {
            Map map = new HashMap();
            GoodsBean goodsBean = goodsBeanList.get(i);
            map.put(goodsBean.getSellername(), goodsBean);
            list.add(map);

        }
        Map<String, List<GoodsBean>> m = mapCombine(list);
        // Log.i("888", "请求数据成功=======" + newList);
        Set<String> keyset = m.keySet();
        for (String key : keyset) {
            children.put(key, m.get(key));

        }
    }

    public Map mapCombine(List<Map> list) {
        Map<Object, List> map = new HashMap<>();
        for (Map m : list) {
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (!map.containsKey(key)) {
                    List newList = new ArrayList<>();
                    newList.add(m.get(key));
                    map.put(key, newList);
                } else {
                    map.get(key).add(m.get(key));
                }
            }

        }

        return map;
    }

    private void showData() {
        if (groups != null && children != null && groups.size() > 0 && children.size() > 0) {
            adapter = new ShoppingCartAdapter(mContext, groups, children, tvShopcartTotal, checkboxAll);
            adapter.setCheckInterface(this);// 关键步骤1,设置复选框接口
            adapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
            adapter.setmListener(this);
            exListView.setAdapter(adapter);
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
            }
            swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initData();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 3000);
                }
            });
        }
    }


    /**
     * 设置购物车产品数量
     */
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(checkboxAll.isChecked());
            StoreBean group = groups.get(i);
            List<GoodsBean> childs = children.get(group.getName());
            for (GoodsBean goodsBean : childs) {
                count += 1;
            }
        }

        //购物车已清空
        if (count == 0) {
            clearCart();
        } else {
            tvTitle.setText("购物车" + "(" + count + ")");
        }
    }

    private void clearCart() {
        tvTitle.setText("购物车" + "(" + 0 + ")");
        tvShopcartEdit.setVisibility(View.GONE);
        llCart.setVisibility(View.GONE);
        cart_empty.setVisibility(View.VISIBLE);
    }

    protected void doDelete() {
        List<StoreBean> toBeDeleteGroups = new ArrayList<StoreBean>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            StoreBean group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsBean> toBeDeleteProducts = new ArrayList<GoodsBean>();// 待删除的子元素列表
            List<GoodsBean> childs = children.get(group.getName());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isSelected()) {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            childs.removeAll(toBeDeleteProducts);
        }
        groups.removeAll(toBeDeleteGroups);
        //记得重新设置购物车
        setCartNum();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition,
                           View showCountView, boolean isChecked) {
        GoodsBean product = (GoodsBean) adapter.getChild(groupPosition,
                childPosition);
        int currentCount = product.getNumber();
        currentCount++;
        product.setNumber(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculate();
        addPostHttp(product);
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition,
                           View showCountView, boolean isChecked) {

        GoodsBean product = (GoodsBean) adapter.getChild(groupPosition,
                childPosition);
        int currentCount = product.getNumber();
        if (currentCount == 1)
            return;
        currentCount--;
        product.setNumber(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculate();
        subPostHttp(product);
    }

    @Override
    public void childDelete(int groupPosition, int childPosition) {

        GoodsBean product = (GoodsBean) adapter.getChild(groupPosition,
                childPosition);
        children.get(groups.get(groupPosition).getName()).remove(childPosition);

        StoreBean group = groups.get(groupPosition);
        List<GoodsBean> childs = children.get(group.getName());

        if (childs.size() == 0) {
            groups.remove(groupPosition);
        }
        adapter.notifyDataSetChanged();
        // handler.sendEmptyMessage(0);
        calculate();
        removeHttp(product);

    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        StoreBean group = groups.get(groupPosition);
        List<GoodsBean> childs = children.get(group.getName());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setSelected(isChecked);
        }
        if (isAllCheck())
            checkboxAll.setChecked(true);
        else
            checkboxAll.setChecked(false);
        adapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosiTion,
                           boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        StoreBean group = groups.get(groupPosition);
        List<GoodsBean> childs = children.get(group.getName());
        for (int i = 0; i < childs.size(); i++) {
            // 不全选中
            if (childs.get(i).isSelected() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        //获取店铺选中商品的总金额
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck()) {
            checkboxAll.setChecked(true);// 全选
        } else {
            checkboxAll.setChecked(false);// 反选
        }
        adapter.notifyDataSetChanged();
        calculate();

    }

    @Override
    public void groupEdit(int groupPosition) {
        groups.get(groupPosition).setIsEdtor(true);
        adapter.notifyDataSetChanged();
    }

    private boolean isAllCheck() {

        for (StoreBean group : groups) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(checkboxAll.isChecked());
            StoreBean group = groups.get(i);
            List<GoodsBean> childs = children.get(group.getName());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setSelected(checkboxAll.isChecked());
            }
        }
        adapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            StoreBean group = groups.get(i);
            List<GoodsBean> childs = children.get(group.getName());
            for (int j = 0; j < childs.size(); j++) {
                GoodsBean product = childs.get(j);
                if (product.isSelected()) {
                    totalCount++;
                    totalPrice += Double.valueOf(product.getCover_price()) * Double.valueOf(product.getNumber());
                }
            }
        }

        tvShopcartTotal.setText("￥" + totalPrice);
        btnCheckOut.setText("去支付(" + totalCount + ")");
        //计算购物车的金额为0时候清空购物车的视图
        if (totalCount == 0) {
            setCartNum();
        } else {
            tvTitle.setText("购物车" + "(" + totalCount + ")");
        }
    }

    private void addPostHttp(final GoodsBean goodsBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /// Log.i("加入购物车", "请求数据成功=======" + datas);
                final String token = CacheUtils.getString(MyApplication.getContex(), "token");
                String id = String.valueOf(goodsBean.getGoods_id());
                // Log.i("加入购物车id", "请求数据成功=======" + id);
                String number = String.valueOf(1);
                // Log.i("加入购物车num", "请求数据成功=======" + number);
                String type = goodsBean.getType();
                String url = Constans.JOIN_CART_URL;
                try {
                    String json = Http.post4(id, type, number, token, url);
                    Log.i("加入购物车", "请求数据成功=======" + json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void subPostHttp(final GoodsBean goodsBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /// Log.i("加入购物车", "请求数据成功=======" + datas);
                final String token = CacheUtils.getString(MyApplication.getContex(), "token");
                String id = String.valueOf(goodsBean.getGoods_id());
                // Log.i("加入购物车id", "请求数据成功=======" + id);
                String number = String.valueOf(-1);
                // Log.i("加入购物车num", "请求数据成功=======" + number);
                String type = goodsBean.getType();
                String url = Constans.JOIN_CART_URL;
                try {
                    String json = Http.post4(id, type, number, token, url);
                    Log.i("6968", "请求数据成功=======" + json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void removeHttp(final GoodsBean goodsBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                /// Log.i("加入购物车", "请求数据成功=======" + datas);
                final String token = CacheUtils.getString(MyApplication.getContex(), "token");
                String id = String.valueOf(goodsBean.getGoods_id());
                String type = goodsBean.getType();
                String url = Constans.REMOVE_URL;

                try {
                    String json = Http.post3(id, type, token, url);
                    Log.i("888888", "请求数据成功=======" + json);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();


    }

    private void SubmitOrderHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                /// Log.i("加入购物车", "请求数据成功=======" + datas);
                final String token = CacheUtils.getString(MyApplication.getContex(), "token");
                String id = "{\"goods\":{\"43\":1},\"product\":{}}";
                // Log.i("加入购物车id", "请求数据成功=======" + id);
                String number = "任意";
                // Log.i("加入购物车num", "请求数据成功=======" + number);
                String type = String.valueOf(1);
                String url = Constans.SUBMIT_ORDER_URL;
                try {
                    String json = Http.post4(id, type, number, token, url);
                    Log.i("提交订单", "请求数据成功=======" + json);
                    Log.i("提交订单", "请求数据成功=======" + token);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void orderHttp() {
        String token = CacheUtils.getString(MyApplication.getContex(), "token");
        String url = Constans.SUBMIT_ORDER_URL;
        int id = goodsBean.getGoods_id();
        //int number = goodsBean
        Map<String, String> map = new HashMap<>();
       /* map.put("id","1");
        map.put("acceptname","小吴");
        map.put("province","海南");
        map.put("city","海南");
        map.put("area","美兰");
        map.put("tel","110");
        map.put("mobie","110");
        map.put("isdefault","1");*/
        map.put("payment", "9");
        map.put("radio_address", "1");
        map.put("cartinfo", "{\"goods\":{\"39\":1},\"product\":{}}");
        map.put("delivery_id", "1");
        map.put("accept_time", "任意");
        map.put("token", token);
        Http.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String json = response.body().string();
                    Log.i("address", "请求数据成功=======" + json);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //initData();
        //setCartNum();

    }

    @OnClick({R.id.tv_title1, R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.tv_shopcart_total, R.id.btn_check_out, R.id.ll_info, R.id.tv_save, R.id.tv_delete, R.id.ll_shar, R.id.ll_cart})
    public void onClick(View view) {
        AlertDialog alert;
        switch (view.getId()) {
            case R.id.tv_title1:
                break;
            case R.id.tv_shopcart_edit:
                if (flag == 0) {
                    llInfo.setVisibility(View.GONE);
                    btnCheckOut.setVisibility(View.GONE);
                    llShar.setVisibility(View.VISIBLE);
                    tvShopcartEdit.setText("完成");
                } else if (flag == 1) {
                    llInfo.setVisibility(View.VISIBLE);
                    btnCheckOut.setVisibility(View.VISIBLE);
                    llShar.setVisibility(View.GONE);
                    tvShopcartEdit.setText("编辑");
                }
                flag = (flag + 1) % 2;//其余得到循环执行上面2个不同的功能
                break;

            case R.id.checkbox_all:
                doCheckAll();
                break;
            case R.id.tv_shopcart_total:
                break;
            case R.id.btn_check_out:
                orderHttp();
                // SubmitOrderHttp();
                Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                //intent.putExtra("goodsbean", goodsBean);
                intent.putExtra("groups", (Serializable) groups);
                intent.putExtra("children", (Serializable) children);
                startActivity(intent);

                break;
            case R.id.ll_info:
                break;
            case R.id.tv_save:
                break;
            case R.id.tv_delete:
                if (totalCount == 0) {
                    Toast.makeText(mContext, "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(mContext).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete();
                            }
                        });
                alert.show();

                break;
            case R.id.ll_shar:
                break;
            case R.id.ll_cart:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        groups.clear();
        totalPrice = 0;
        totalCount = 0;
        children.clear();
    }



}


