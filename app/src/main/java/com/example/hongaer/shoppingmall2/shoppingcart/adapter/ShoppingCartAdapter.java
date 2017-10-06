package com.example.hongaer.shoppingmall2.shoppingcart.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.bean.StoreBean;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongaer on 2017/7/9.
 */

public class ShoppingCartAdapter extends BaseExpandableListAdapter /*RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder>*/ {

    public static final int REMOVE_FLAG = 1;
    private final Context mContext;
    private final List<StoreBean> groups;
    //private List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    //private final CheckBox cbAll; //完成状态下删除的全选
    private final Map<String, List<GoodsBean>> children;
     private  int count=0;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private GroupEdtorListener mListener;


    //private final ArrayList<Object> datas;
/*   private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
             switch (msg.what){

                   case REMOVE_FLAG: notifyItemRemoved((Integer) msg.obj);

                       break;
             }
        }
    };*/
    public ShoppingCartAdapter(Context mContext, List<StoreBean> groups, Map<String, List<GoodsBean>> children, TextView tvShopcartTotal, CheckBox checkboxAll) {
        this.mContext = mContext;
        this.groups = groups;
        this.children = children;
        //this.datas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        //showTotalPrice();
        // 设置点击事件
        //setListener();
        //检验是否全选
        //checkAll();
    }
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }
    public void setmListener(GroupEdtorListener mListener) {
        this.mListener = mListener;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }
    public GroupEdtorListener getmListener() {
        return mListener;
    }

    @Override
    public int getGroupCount() {
        //Log.i("33333", "请求数据成功======="+groups.size());
        return groups.size();


    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = groups.get(groupPosition).getName();

        return children.get(groupId).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<GoodsBean> childs = children.get(groups.get(groupPosition).getName());
       // Log.i("33333", "请求数据成功=======" + childs.get(0));
        return childs.get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder gholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shopcart_group, null);
            gholder = new GroupViewHolder(convertView);
            convertView.setTag(gholder);
        } else {
            gholder = (GroupViewHolder) convertView.getTag();
        }
        final StoreBean group = (StoreBean) getGroup(groupPosition);

        gholder.tvSourceName.setText(group.getName());
        gholder.determineChekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
            }
        });
        gholder.determineChekbox.setChecked(group.isChoosed());
        if (group.isEdtor()) {
            gholder.tvStoreEdtor.setText("完成");
        } else {
            gholder.tvStoreEdtor.setText("编辑");
        }
        gholder.tvStoreEdtor.setOnClickListener(new GroupViewClick(groupPosition, gholder.tvStoreEdtor, group));
        notifyDataSetChanged();
        return convertView;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder cholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shop_cart, null);
            cholder = new ChildViewHolder(convertView);
            convertView.setTag(cholder);
        } else {
            cholder = (ChildViewHolder) convertView.getTag();

        }
        if (groups.get(groupPosition).isEdtor() == true) {
            cholder.llEdtor.setVisibility(View.VISIBLE);
            cholder.rlNoEdtor.setVisibility(View.GONE);
        } else {
            cholder.llEdtor.setVisibility(View.GONE);
            cholder.rlNoEdtor.setVisibility(View.VISIBLE);
        }

        final GoodsBean goodsBean = (GoodsBean) getChild(groupPosition, childPosition);

        if (goodsBean != null) {
            Glide.with(mContext).load(Constans.BASE_URL + goodsBean.getFigure()).into(cholder.ivGov);
            cholder.tvDescGov.setText(goodsBean.getName());
            cholder.etNum.setText(goodsBean.getNumber() + "");
            cholder.tvPriceGov.setText("¥" + goodsBean.getCover_price());
            cholder.tvBuyNum.setText("x" + goodsBean.getNumber());
            cholder.checkBox.setChecked(goodsBean.isSelected());
            cholder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsBean.setSelected(((CheckBox) v).isChecked());
                    cholder.checkBox.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });
            cholder.btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露增加接口
                }
            });
            cholder.btReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露删减接口
                }
            });
            cholder.tvGoodsDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = new AlertDialog.Builder(mContext).create();
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
                                    modifyCountInterface.childDelete(groupPosition, childPosition);

                                }
                            });
                    alert.show();

                }
            });
            cholder.etNum.addTextChangedListener(new GoodsNumWatcher(goodsBean));//监听文本输入框的文字变化，并且刷新数据
            notifyDataSetChanged();

          /*  cholder.addSubView.setValue(goodsBean.getNumber());
            cholder.addSubView.setMaxValue(7);
            cholder.addSubView.setMinValue(1);*/
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @OnClick({R.id.check_box, R.id.iv_gov, R.id.tv_desc_gov, R.id.tv_price_gov, R.id.tv_discount_price, R.id.tv_buy_num, R.id.bt_reduce, R.id.et_num, R.id.bt_add, R.id.ll_change_num, R.id.tv_colorsize, R.id.tv_goods_delete, R.id.ll_edtor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_box:
                break;
            case R.id.iv_gov:
                break;
            case R.id.tv_desc_gov:
                break;
            case R.id.tv_price_gov:
                break;
            case R.id.tv_discount_price:
                break;
            case R.id.tv_buy_num:
                break;
            case R.id.bt_reduce:
                break;
            case R.id.et_num:
                break;
            case R.id.bt_add:
                break;
            case R.id.ll_change_num:
                break;
            case R.id.tv_colorsize:
                break;
            case R.id.tv_goods_delete:
                break;
            case R.id.ll_edtor:
                break;
        }
    }

    static class GroupViewHolder {
        @BindView(R.id.determine_chekbox)
        CheckBox determineChekbox;
        @BindView(R.id.tv_source_name)
        TextView tvSourceName;
        @BindView(R.id.tv_store_edtor)
        Button tvStoreEdtor;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.check_box)
        CheckBox checkBox;
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @BindView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @BindView(R.id.tv_discount_price)
        TextView tvDiscountPrice;
        @BindView(R.id.tv_buy_num)
        TextView tvBuyNum;
        @BindView(R.id.bt_reduce)
        Button btReduce;
        @BindView(R.id.et_num)
        EditText etNum;
        @BindView(R.id.bt_add)
        Button btAdd;
        @BindView(R.id.ll_change_num)
        RelativeLayout llChangeNum;
        @BindView(R.id.tv_colorsize)
        TextView tvColorsize;
        @BindView(R.id.tv_goods_delete)
        TextView tvGoodsDelete;
        @BindView(R.id.ll_edtor)
        LinearLayout llEdtor;
        @BindView(R.id.rl_no_edtor)
        RelativeLayout rlNoEdtor;
       /* private CheckBox cbGov;
        private CheckBox cbShopGov;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private AddSubView addSubView;
        private TextView shopView;*/

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param groupPosition
         * @param childPosition
         */
        void childDelete(int groupPosition, int childPosition);
    }

    /**
     * 监听编辑状态
     */
    public interface GroupEdtorListener {
        void groupEdit(int groupPosition);
    }

    /**
     * 使某个组处于编辑状态
     * <p/>
     * groupPosition组的位置
     */
    class GroupViewClick implements View.OnClickListener {
        private int groupPosition;
        private Button edtor;
        private StoreBean group;

        public GroupViewClick(int groupPosition, Button edtor, StoreBean group) {
            this.groupPosition = groupPosition;
            this.edtor = edtor;
            this.group = group;
        }

        @Override
        public void onClick(View v) {
            int groupId = v.getId();
            if (groupId == edtor.getId()) {
                if (group.isEdtor()) {
                    group.setIsEdtor(false);
                } else {
                    group.setIsEdtor(true);

                }
                notifyDataSetChanged();
            }
        }
    }
    /**
     * 购物车的数量修改编辑框的内容监听
     */
    class GoodsNumWatcher implements TextWatcher {
       GoodsBean goodsBean;
        public GoodsNumWatcher(GoodsBean goodsBean) {
            this.goodsBean = goodsBean;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!TextUtils.isEmpty(s.toString())){//当输入的数字不为空时，更新数字
               goodsBean.setNumber(Integer.valueOf(s.toString().trim()));
            }
        }

    }



   /* @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GoodsBean goodsBean = datas.get(position);
        holder.cbGov.setChecked(goodsBean.isSelected());
        holder.cbShopGov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constans.BASE_URL + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText("¥" + goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.shopView.setText(goodsBean.getSellername());
        holder.addSubView.setMaxValue(7);
        holder.addSubView.setMinValue(1);

        //设置商品数量变化的监听
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {

            @Override
            public void onNumberChange(int value) {
                //1、当前列表内存更新
                goodsBean.setNumber(value);
                //2.本地更新
                CartStorage.getInstance().updataData(goodsBean);

                //3.刷新适配器
                notifyItemChanged(position);
                //4.再次计算总计
                showTotalPrice();
            }
        });


    }


    @Override
    public int getItemCount() {

        return datas.size();
    }*/

   /* class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbGov;
        private CheckBox cbShopGov;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private AddSubView addSubView;
        private TextView shopView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            cbShopGov = (CheckBox) itemView.findViewById(R.id.cb_shop_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            addSubView = (AddSubView) itemView.findViewById(R.id.AddSubView);
            shopView=(TextView) itemView.findViewById(R.id.tv_shop);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }*/
  /*  public void showTotalPrice() {
        tvShopcartTotal.setText("合计" + getTotalPrice());
    }*/

   /* public double getTotalPrice() {
        double totalPrice = 0;
        if (datas != null && datas.size() > 0) {

            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()) {
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }*/

  /*  public void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //1.根据位置找到对应bean
                GoodsBean goodsBean = datas.get(position);
                //2.设置取反状态
                CacheUtils.saveInt(MyApplication.getContex(),"position",position);
                goodsBean.setSelected(!goodsBean.isSelected());
             *//*  CartStorage.getInstance().addData(goodsBean);*//*
                CartStorage.getInstance().updataData(goodsBean);*//*datas=CartStorage.getInstance().getAllData();*//*
                Log.i("244", "请求数据成功=======" + datas.get(position).isSelected());
                Log.i("244444", "请求数据成功=======" + goodsBean);
                // Log.i("2444", "请求数据成功=======" + datas.get(1));
                // Log.i("2444", "请求数据成功=======" + datas.get(1).getName());
                //3.刷新状态
                notifyItemChanged(position);
                //4.检查是否全选
                checkAll();
                //5.重新计算总计
                showTotalPrice();
            }
        });
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();
                //2.根据状态设置全选非全选
                checkAll_no(isCheck);
                //3.计算总价
                showTotalPrice();
            }
        });
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = cbAll.isChecked();
                //2.根据状态设置全选非全选
                checkAll_no(isCheck);

            }
        });

    }

    public void checkAll_no(boolean isCheck) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                Log.i("23333333", "请求数据成功=======" + goodsBean.isSelected());
                notifyItemChanged(i);

            }
        }
    }

    public void checkAll() {
        int num = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isSelected()) {
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    //全选
                    num++;
                }
            }
            if (num == datas.size()) {
                checkboxAll.setChecked(true);
                cbAll.setChecked(false);
            }
        } else {

            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }

    }

    public  void removeHttp() {
        // datas = CartStorage.getInstance().getAllData();
        Log.i("26666", "请求数据成功=======" +datas.get(0).isSelected());
        Log.i("26666", "请求数据成功=======" +datas.get(0));
        Log.i("26666", "请求数据成功=======" +datas.get(1));
        new Thread(new Runnable() {
            @Override
            public void run() {
                /// Log.i("加入购物车", "请求数据成功=======" + datas);
                final String token = CacheUtils.getString(MyApplication.getContex(), "token");
                if(datas!=null&&datas.size()>0){
                    for(int i=0;i<datas.size();i++){
                        GoodsBean goodsBean=datas.get(i);

                        if(goodsBean.isSelected()){
                            Log.i("goods", "请求数据成功=======" + goodsBean);

                            String id = String.valueOf(goodsBean.getGoods_id());
                            String type =goodsBean.getType();
                            String url = Constans.REMOVE_URL;
                            String json = null;
                            try {
                                json = Http.post3(id, type, token, url);
                                datas.remove(goodsBean);
                                //在本地中保存
                                CartStorage.getInstance().deleteData(goodsBean);
                                //刷新
                                // notifyItemRemoved(i);

                                Message msg=new Message();
                                msg.what=REMOVE_FLAG;
                                msg.obj=i;
                                mHandler.sendMessage(msg);
                                i--;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.i("删除", "请求数据成功=======" + json);
                        }

                    }
                }

            }
        }).start();
        // deleteData();

    }

    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                //删除选中的
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()) {
                    //内存中删除
                    datas.remove(goodsBean);
                    //在本地中保存
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);

                    i--;

                }

            }
        }

    }
*/

    public interface OnItemClickListener {

        public void OnItemClick(int position);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
