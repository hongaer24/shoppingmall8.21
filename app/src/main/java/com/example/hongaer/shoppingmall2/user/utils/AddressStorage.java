package com.example.hongaer.shoppingmall2.user.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongaer on 2017/7/6.
 */

public class AddressStorage {
    public static final String JSON_CART = "json_cart";
    public static final String JSONBEAN = "jsonbean";
    private static AddressStorage instance;
    private final   Context context;
  public SparseArray<SPConsigneeAddressBean> sparseArray;
    private SPConsigneeAddressBean consignee;

    public AddressStorage(Context context){
              this.context=context;
           //把之前的数据读取出来放到sparseArray内存
                sparseArray=new SparseArray<>(100);

            listToSparseArray();
    }
    public static AddressStorage getInstance() {
        if (instance ==null) {
            instance = new AddressStorage(MyApplication.getContex());
        }
        return  instance;
    }
     //从本地中读取数据加入SparseArray
    private void listToSparseArray() {

        List<SPConsigneeAddressBean> spConsigneeAddressBeanList=getAllData();
        //把list数据转换成sparseArray
        if(spConsigneeAddressBeanList!=null) {
            for (int i = 0; i < spConsigneeAddressBeanList.size(); i++) {

                consignee = spConsigneeAddressBeanList.get(i);
                Log.i("6666","空指针的地方=="+spConsigneeAddressBeanList.get(i));
                if(consignee.getMobile()!=null){
                    sparseArray.put((int) Long.parseLong(consignee.getMobile()), consignee);

                }
                //sparseArray.put(Integer.parseInt(consignee.getProduct_id()), consignee);

            }
        }
    }
    //获取本地所有的数据
    public List<SPConsigneeAddressBean> getAllData() {
        List<SPConsigneeAddressBean> consigneeList=new ArrayList<>();
         //1.从本地从获取
         String json= CacheUtils.getString(context,JSONBEAN);
        Log.i("777","空指针的地方=="+json);
         //用gson转换为列表
         //判断不为空时执行
        if (!TextUtils.isEmpty(json)) {
            consigneeList = new Gson().fromJson(json, new TypeToken<List<SPConsigneeAddressBean>>() {}.getType());
            //Log.i("666","空指针的地方=="+consigneeList);
        }
          return consigneeList;

    }

    public void addData(SPConsigneeAddressBean consignee) {
        //1.添加到内存中 SparseArray
        Log.i("110110","空指针的地方=="+consignee);
       // consignee tempData = sparseArray.get(Integer.parseInt(consignee.getProduct_id()));
      try {
          SPConsigneeAddressBean tempData = sparseArray.get((int) Long.parseLong(consignee.getMobile()));
          if (tempData != null) {
              // 内存中有了这条数据
              //  tempData.setNumber(tempData.getNumber() + 1);
          } else {
              tempData = consignee;
              // tempData.setNumber(1);

          }
          //同步到内存中
          //sparseArray.put(Integer.parseInt(tempData.getProduct_id()), tempData);
          sparseArray.put((int) Long.parseLong(tempData.getMobile()), tempData);

          //2.同步到本地
          saveLocal();
      }catch (NumberFormatException e){}

        }
    //删除数据
    public void deleteData(SPConsigneeAddressBean consignee) {
        //1.从内存中删除
     // sparseArray.delete(Integer.parseInt( consignee.getProduct_id()));
        sparseArray.delete((int) Long.parseLong(consignee.getMobile()));
        //2.把内存数据保存到本地
        saveLocal();
    }
    //修改数据
    public void updataData(SPConsigneeAddressBean consignee) {

      sparseArray.put((int) Long.parseLong(consignee.getMobile()), consignee);
       saveLocal();
    }

    private void saveLocal() {
        //把 parseArray 转换成 list
        List<SPConsigneeAddressBean> spConsigneeAddressBeanList = sparsesToList();
         //把转换成 String
        String json = new Gson().toJson(spConsigneeAddressBeanList);
        Log.i("999","空指针的地方=="+json);
          // 保存
        CacheUtils.saveString(context, JSONBEAN, json);

    }


    private List<SPConsigneeAddressBean> sparsesToList() {
              List<SPConsigneeAddressBean> consigneeList=new ArrayList<>();
             if(sparseArray!=null&&sparseArray.size()>0) {
            for (int i = 0; i < sparseArray.size(); i++) {
               SPConsigneeAddressBean consignee = sparseArray.valueAt(i);
                consigneeList.add(consignee);
            }
        }
            return consigneeList;
    }


}
