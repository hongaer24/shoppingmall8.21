package com.example.hongaer.shoppingmall2.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.user.utils.AddressStorage;

import java.util.List;

/**
 * Created by hongaer on 2017/8/18.
 */

public class SPAddressListAdapter extends BaseAdapter implements View.OnClickListener  {

    private List<SPConsigneeAddressBean> datas ;
    private Context mContext ;
    private AddressListListener mAddressListListener;
    public SPAddressListAdapter(Context context, AddressListListener addressListListener, List<SPConsigneeAddressBean> spConsigneeAddressBeanList) {
        this.mContext = context;
        this.mAddressListListener = addressListListener;
        this.datas = spConsigneeAddressBeanList;
    }

    @Override
    public int getCount() {
        if(datas == null)return 0;
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        if(datas == null) return null;
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        if(datas == null) return -1;
        return Long.valueOf(datas.get(position).getMobile());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.person_address_list_item, parent, false);
            //使用减少findView的次数
            holder = new ViewHolder();
            holder.consigneeTxtv = ((TextView) convertView.findViewById(R.id.address_consignee_txtv)) ;
            holder.mobileTxtv = ((TextView) convertView.findViewById(R.id.address_mobile_txtv)) ;
            holder.addressTxtv = ((TextView) convertView.findViewById(R.id.address_detail_txtv)) ;
            holder.setDefaultBtn= ((Button) convertView.findViewById(R.id.address_setdefault_btn)) ;
            holder.editBtn= ((Button) convertView.findViewById(R.id.address_edit_btn)) ;
            holder.deleteBtn = ((Button) convertView.findViewById(R.id.address_delete_btn)) ;
            //设置标记
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.editBtn.setOnClickListener(this);
        holder.editBtn.setTag(position);

        holder.deleteBtn.setOnClickListener(this);
        holder.deleteBtn.setTag(position);

        holder.setDefaultBtn.setOnClickListener(this);
        holder.setDefaultBtn.setTag(position);

        //获取该行数据
           SPConsigneeAddressBean  consignee=  datas.get(position);

        //设置数据到View
        holder.consigneeTxtv.setText( consignee.getConsignee());
        holder.mobileTxtv.setText( consignee.getMobile());
        holder.addressTxtv.setText( consignee.getFullAddress()+consignee.getAddress());


        if("1".equals( consignee.getIsDefault())){
            holder.setDefaultBtn.setBackgroundResource(R.drawable.icon_checked);
        }else{
            holder.setDefaultBtn.setBackgroundResource(R.drawable.icon_checkno);
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = Integer.valueOf(v.getTag().toString());
       SPConsigneeAddressBean  consignee=datas.get(position);

        switch (v.getId()){
            case R.id.address_delete_btn:
                AddressStorage.getInstance().deleteData(consignee);
                if (mAddressListListener!= null)
                    mAddressListListener.onItemDelete( consignee);


                break;
            case R.id.address_edit_btn:
                if (mAddressListListener!=null)mAddressListListener.onItemEdit( consignee);
                break;
            case R.id.address_setdefault_btn:
                if (mAddressListListener!=null)mAddressListListener.onItemSetDefault( consignee);
                break;
        }

    }
    class ViewHolder{
        TextView consigneeTxtv;
        TextView mobileTxtv;
        TextView addressTxtv;
        Button  setDefaultBtn;
        Button  editBtn;
        Button  deleteBtn;
        TextView setDefaultTxtv;
    }


    public interface AddressListListener{
        public void onItemDelete(SPConsigneeAddressBean  consignee);
        public void onItemEdit(SPConsigneeAddressBean  consignee);
        public void onItemSetDefault(SPConsigneeAddressBean  consignee);
    }
}