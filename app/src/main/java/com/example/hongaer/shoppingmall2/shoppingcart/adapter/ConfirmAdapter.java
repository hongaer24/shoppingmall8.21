package com.example.hongaer.shoppingmall2.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.util.List;

/**
 * Created by hongaer on 2017/9/8.
 */

public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.MyViewHolder> {

    private final List<GoodsBean> datas;
    private Context mContext;

    public ConfirmAdapter(Context context, List<GoodsBean> goodsBeanList) {
              this.mContext=context;
              this.datas=goodsBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.confirm_order_list_item, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

                 GoodsBean goodsBean = datas.get(position);
                  Log.i("123456","=========="+goodsBean.isSelected());

                  if(goodsBean.isSelected()){

                      Glide.with(mContext).load(Constans.BASE_URL + goodsBean.getFigure()).into(holder.ivGov);
                      holder.tvDescGov.setText(goodsBean.getName());
                      holder.tvPriceGov.setText("¥" + goodsBean.getCover_price());
                      holder.shopView.setText(goodsBean.getSellername());
                      holder.tvCountGov.setText("x"+goodsBean.getNumber());
                      holder.tvPriceOrder.setText("¥"+goodsBean.getCover_price());
                  }








    }

    @Override
    public int getItemCount() {
        return 1;
    }
    class   MyViewHolder extends RecyclerView.ViewHolder{
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
    }
}
