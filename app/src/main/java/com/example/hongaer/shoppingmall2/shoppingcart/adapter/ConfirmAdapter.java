package com.example.hongaer.shoppingmall2.shoppingcart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.bean.StoreBean;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongaer on 2017/9/8.
 */

public class ConfirmAdapter extends BaseExpandableListAdapter {


    private final List<StoreBean> groups;
    private final Map<String, List<GoodsBean>> children;



    private Context mContext;
    private int num;
    private TextView tvShopcartTotal;

    public ConfirmAdapter(Context context, List<StoreBean> groups, Map<String, List<GoodsBean>> children, TextView tvShopcartTotal) {
        this.mContext = context;
        this.groups = groups;
        this.children = children;
        this.tvShopcartTotal = tvShopcartTotal;

    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("¥" + getTotalPrice());
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        List<GoodsBean> datas= CartStorage.getInstance().getAllData();
        if (datas != null && datas.size() > 0) {

            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()) {
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }

    @Override
    public int getGroupCount() {
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder gholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_comfirm_group, null);
            gholder = new GroupViewHolder(convertView);
            convertView.setTag(gholder);
        } else {
            gholder = (GroupViewHolder) convertView.getTag();
        }
        final StoreBean group = (StoreBean) getGroup(groupPosition);
        gholder.tvSourceName.setText(group.getName());

        return convertView;
    }

    @OnClick(R.id.et_remark)
    public void onClick() {
    }

    static class GroupViewHolder {

        @BindView(R.id.tv_source_name)
        TextView tvSourceName;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder cholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_comfirm_child, null);
            cholder = new ChildViewHolder(convertView);
            convertView.setTag(cholder);
        } else {
            cholder = (ChildViewHolder) convertView.getTag();

        }
        final GoodsBean goodsBean = (GoodsBean) getChild(groupPosition, childPosition);
        if (goodsBean != null) {
            Glide.with(mContext).load(Constans.BASE_URL + goodsBean.getFigure()).into(cholder.ivGov);
            cholder.tvDescGov.setText(goodsBean.getName());
          /*  cholder.tvPriceGov.setText("¥" + goodsBean.getCover_price());
            cholder.tvCountGov.setText("x"+goodsBean.getNumber());
            cholder.tvOrderPrice1.setText("¥"+goodsBean.getCover_price());*/

        }
           return convertView;
    }

    static class ChildViewHolder {
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

/*

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.confirm_order_list_item, null);
        return new MyViewHolder(itemView);
    }
*/

  /*  @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //int Newpositon= CacheUtils.getInt(MyApplication.getContex(),"position");
        //Log.i("123456","=========="+Newpositon);

                         GoodsBean goodsBean = datas.get(position);
                       Log.i("123456","=========="+goodsBean.isSelected());
                         //goodsBean.setSelected(true);
                       if(!goodsBean.isSelected()){
                         goodsBean = datas.get(position);
                           Glide.with(mContext).load(Constans.BASE_URL + goodsBean.getFigure()).into(holder.ivGov);
                           holder.tvDescGov.setText(goodsBean.getName());
                           holder.tvPriceGov.setText("¥" + goodsBean.getCover_price());
                           holder.shopView.setText(goodsBean.getSellername());
                           holder.tvCountGov.setText("x"+goodsBean.getNumber());
                           holder.tvPriceOrder.setText("¥"+Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price()));
                           holder. tvCountOrder.setText("共计"+goodsBean.getNumber()+"件商品");

                       }else {

                           Glide.with(mContext).load(Constans.BASE_URL + goodsBean.getFigure()).into(holder.ivGov);
                           holder.tvDescGov.setText(goodsBean.getName());
                           holder.tvPriceGov.setText("¥" + goodsBean.getCover_price());
                           holder.shopView.setText(goodsBean.getSellername());
                           holder.tvCountGov.setText("x"+goodsBean.getNumber());
                           holder.tvPriceOrder.setText("¥"+Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price()));
                           holder. tvCountOrder.setText("共计"+goodsBean.getNumber()+"件商品");

                       }


    }
*/
  /*  @Override
    public int getItemCount() {
       *//*  num=0;
        if(datas!=null&&datas.size()>0){
               for(int i=0;i<datas.size();i++){
                     GoodsBean goodsBean=datas.get(i);
                     if(goodsBean.isSelected()){
                         num++;
                     }

               }
        }
        return num;*//*
       return 4;
    }*/
    /*class   MyViewHolder extends RecyclerView.ViewHolder{
        private TextView shopView;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private TextView tvCountGov;
        private TextView tvCountOrder;
        private TextView tvPriceOrder;


        public MyViewHolder(View itemView) {
            super(itemView);
             shopView= (TextView) itemView.findViewById(R.id.tv_shop);
              ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
             tvDescGov= (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov= (TextView) itemView.findViewById(R.id.tv_price_gov);
            tvCountGov= (TextView) itemView.findViewById(R.id.tv_count_gov);
            tvDescGov= (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvDescGov= (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvCountOrder=(TextView) itemView.findViewById(R.id.tv_order_cout_goods);
            tvPriceOrder=(TextView) itemView.findViewById(R.id.tv_order_price1);

        }
    }*/
}
