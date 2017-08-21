package com.example.hongaer.shoppingmall2.type.fragmet;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by hongaer on 2017/7/1.
 */

public class TypeFragment extends BaseFragment {

   /* @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @BindView(R.id.fl_type)
    FrameLayout flType;*/
/*
    String[] titls = {"分类","标签"};
    private ArrayList<BaseFragment> fragments;
    private Fragment tempFragemnt;*/
    private WebView webview;


    public View initView() {
       // Log.e(, "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        ButterKnife.bind(this, view);
        webview= (WebView) view.findViewById(R.id.wb);
        WebSettings webSettings=webview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl( "http://killsound888.oicp.net:118/jinque/debug/jqmall/index.php?controller=site&action=sitemap");
        webview.setWebViewClient(new HelloWebViewClient());
        //Web视图


       /* rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);*/
//设置点击事件
        /*initListener();*/
        return view;
    }

    private class HelloWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


   /* public void initData() {
        super.initData();

        tl1.setTabData(titls);
        //设置点击SegmentTabLayout的监听
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));
              //  Toast.makeText(mContext, "position=" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        initFragment();

    }
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());
        //默认显示ListFragment
        switchFragment(fragments.get(0));
    }
    private void switchFragment(Fragment currentFragment) {
        if (tempFragemnt != currentFragment) {

            if (currentFragment != null) {
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!currentFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (tempFragemnt != null) {
                        transaction.hide(tempFragemnt);
                    }
                    //添加Fragment
                    transaction.add(R.id.fl_type, currentFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (tempFragemnt != null) {
                        transaction.hide(tempFragemnt);
                    }
                    transaction.show(currentFragment).commit();
                }
            }
            tempFragemnt = currentFragment;
        }
    }
                @OnClick(R.id.iv_type_search)
    public void onClick() {
        Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
    }*/
}








