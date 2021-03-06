/*
package com.example.hongaer.shoppingmall2.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.DatasBean;
import com.example.hongaer.shoppingmall2.home.datas.Datasdatas;
import com.example.hongaer.shoppingmall2.home.datas.Goodsdatas;
import com.example.hongaer.shoppingmall2.home.datas.ResultDatadatas;
import com.example.hongaer.shoppingmall2.user.view.PositionActivity;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

*/
/**
 * Created by hongaer on 2017/7/3.
 *//*


public class HomeFragmentAdapter extends RecyclerView.Adapter {
    public static final int HOT = 5;
    private static final String GOODS_datas ="goodsdatas" ;
   // private final List<Datasdatas.Datadatas> datas;

    //private final ResultDatadatas.datas datas;
    private LayoutInflater mLayoutInflater;
    private Context mContext;


    */
/**
     * 五种类型
     *//*

    */
/**
     * 横幅广告
     *//*

    public static final int BANNER = 0;
    */
/**
     * 频道
     *//*

    public static final int CHANNEL = 1;
    */
/**
     * 活动
     *//*

    public static final int ACT = 2;
    */
/**
     * 秒杀
     *//*

    public static final int SECKILL = 3;
    */
/**
     * 推荐
     *//*

    public static final int RECOMMEND = 4;
    */
/**
     * 热卖
     *//*


    */
/**
     * 当前类型
     *//*

    public int currentType = BANNER;

  */
/*  public HomeFragmentAdapter(Context mContext, List<DatasBean.DataBean> datas) {
                 this.mContext=mContext;
                 this.datas=datas;
    }*//*



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {

            return new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.channel_item, null));

        }else if(viewType==ACT){
            return new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.act_item, null));

        }else if(viewType==SECKILL){
            return new SeckillViewHolde(mContext, mLayoutInflater.inflate(R.layout.seckill_item, null));

        }else if (viewType==RECOMMEND){
            return new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.recommend_item, null));
        }else if(viewType==HOT){
            return new HotViewHolder(mContext, mLayoutInflater.inflate(R.layout.hot_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(datas.getBanner_info());

        }else if(getItemViewType(position) == CHANNEL){
             ChannelViewHolder channelViewHolder= (ChannelViewHolder) holder;
             channelViewHolder.setData(datas.getChannel_info());

        }else if (getItemViewType(position)==ACT){
           ActViewHolder actViewHolder= (ActViewHolder) holder;
           actViewHolder.setData(datas.getAct_info());
        } else if(getItemViewType(position)==SECKILL){
            SeckillViewHolde seckillViewHolde= (SeckillViewHolde) holder;
            seckillViewHolde.setData(datas.getSeckill_info());

        }else  if (getItemViewType(position)==RECOMMEND){
            RecommendViewHolder recommendViewHolder= (RecommendViewHolder) holder;
           recommendViewHolder.setData(datas.getRecommend_info());
        }else if (getItemViewType(position)==HOT){
            HotViewHolder  hotViewHolder= (HotViewHolder) holder;
            hotViewHolder.setData(datas.getHot_info());
        }


    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultDatadatas.datas.BannerInfodatas> banner_info) {
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                String imageUrl = banner_info.get(i).getImage();
                imagesUrl.add(imageUrl);
            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imagesUrl, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {

                    Glide.with(mContext).load(Constans.BASE_URL_IMAGES + url).into(view);
                }
            });
             banner.setOnBannerClickListener(new OnBannerClickListener() {
                 @Override
                 public void OnBannerClick(int position) {
                     Toast.makeText(mContext,"position=="+position,Toast.LENGTH_SHORT).show();
                    //   startGoodsInfoActivity(goodsdatas);
                 }
             });

        }
    }
         //跳转商品详情页面
    private void startGoodsInfoActivity(Goodsdatas goodsdatas) {
        Intent intent=new Intent(mContext, PositionActivity.class);
        intent.putExtra(GOODS_datas,goodsdatas);
         mContext.startActivity(intent);
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

          private Context mContext;
          private GridView gvChannel;
          private ChannelAdapter adapter;

          public ChannelViewHolder(final Context mContext, View itemView) {
              super(itemView);
              this.mContext=mContext;
              gvChannel= (GridView) itemView.findViewById(R.id.gv_channel);
              gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      Toast.makeText(mContext,"position"+position,Toast.LENGTH_SHORT).show();
                  }
              });
          }

          public void setData(List<ResultDatadatas.datas.ChannelInfodatas> channel_info) {
              // 得到数据，设置适配器
                        adapter=new ChannelAdapter(mContext,channel_info);
                        gvChannel.setAdapter(adapter);
          }

      }
    class ActViewHolder extends RecyclerView.ViewHolder{
               private Context mContext;
               private ViewPager actViewPager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
             this.mContext=mContext;
              actViewPager= (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }


        public void setData(final List<ResultDatadatas.datas.ActInfodatas> act_info) {
               actViewPager.setPageMargin(20);
               actViewPager.setOffscreenPageLimit(3);
               actViewPager.setPageTransformer(true,new ScaleInTransformer());
               actViewPager.setAdapter(new PagerAdapter() {
                   @Override
                   public Object instantiateItem(ViewGroup container, final int position) {
                       ImageView view = new ImageView(mContext);
                       view.setScaleType(ImageView.ScaleType.FIT_XY);
                       Glide.with(mContext).load(Constans.BASE_URL_IMAGES + act_info.get(position).getIcon_url()).into(view);
                       view.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Toast.makeText(mContext,"position"+position,Toast.LENGTH_SHORT).show();
                           }
                       });
                       container.addView(view);

                       return view;
                   }

                   @Override
                   public void destroyItem(ViewGroup container, int position, Object object) {
                       container.removeView((View) object);
                   }

                   @Override
                   public int getCount() {
                       return act_info.size();
                   }

                   @Override
                   public boolean isViewFromObject(View view, Object object) {
                       return view==object;
                   }
               });
        }
    }
       private long dt=0;
      class SeckillViewHolde extends RecyclerView.ViewHolder{
          private TextView tvMore;
          private TextView tvTime;
          private RecyclerView recyclerView;
          public Context mContext;
          private SeckillRecyclerViewAdapter adapter;

          private Handler handler=new Handler(){
              @Override
              public void handleMessage(Message msg) {
                  super.handleMessage(msg);
                  dt=dt-1000;
                  SimpleDateFormat formatter=new SimpleDateFormat("hh:mm:ss");
                  String time=formatter.format(new Date(dt));
                  tvTime.setText(time);

                  handler.removeMessages(0);
                  handler.sendEmptyMessageDelayed(0,1000);
                  if(dt<=0){
                      handler.removeCallbacksAndMessages(null);
                  }
              }
          };
          public SeckillViewHolde(Context mContext, View itemView) {
              super(itemView);
              tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
              tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
              recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
              this.mContext = mContext;
          }

          public void setData(final ResultDatadatas.datas.SeckillInfodatas seckill_info) {

              //得到数据，设置数据，设置适配器
              */
/*adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info.getList());
              recyclerView.setAdapter(adapter);
              recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));*//*

             */
/* ResultDatadatas.datas.SeckillInfodatas.Listdatas listdatas = seckill_info.getList().get(1);
              Goodsdatas goodsdatas = new Goodsdatas();
              goodsdatas.setName(listdatas.getName());
              goodsdatas.setCover_price(listdatas.getCover_price());
              goodsdatas.setFigure(listdatas.getFigure());
              goodsdatas.setProduct_id(listdatas.getProduct_id());
              startGoodsInfoActivity(goodsdatas);*//*

                     */
/* adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                          @Override
                          public void onItemClick(int position) {
                              Toast.makeText(mContext, "秒杀=" + position, Toast.LENGTH_SHORT).show();
                              ResultDatadatas.datas.SeckillInfodatas.Listdatas listdatas = seckill_info.getList().get(position);
                              Goodsdatas goodsdatas = new Goodsdatas();
                              goodsdatas.setName(listdatas.getName());
                              goodsdatas.setCover_price(listdatas.getCover_price());
                              goodsdatas.setFigure(listdatas.getFigure());
                              goodsdatas.setProduct_id(listdatas.getProduct_id());
                              startGoodsInfoActivity(goodsdatas);
                     }
                 });*//*

                 dt=Integer.valueOf(seckill_info.getEnd_time())-Integer.valueOf(seckill_info.getStart_time());

                handler.sendEmptyMessageDelayed(0,1000);
          }
      }
      class RecommendViewHolder extends RecyclerView.ViewHolder{
          private Context mContext;
          private TextView tv_more_recommend;
          private GridView gv_recommend;
          private RecommendGridViewAdapter adapter;

          public RecommendViewHolder(final Context mContext, View itemView) {
              super(itemView);
              this.mContext=mContext;
              tv_more_recommend= (TextView) itemView.findViewById(R.id.tv_more_recommend);
              gv_recommend= (GridView) itemView.findViewById(R.id.gv_recommend);

          }

          public void setData(final List<ResultDatadatas.datas.RecommendInfodatas> recommend_info) {
              //得到数据，设置适配器
                adapter=new RecommendGridViewAdapter(mContext,recommend_info);
                 gv_recommend.setAdapter(adapter);
              Goodsdatas goodsdatas=new Goodsdatas();
              ResultDatadatas.datas.RecommendInfodatas  recommendInfodatas=recommend_info.get(0);
              goodsdatas.setName( recommendInfodatas.getName());
              goodsdatas.setCover_price( recommendInfodatas.getCover_price());
              goodsdatas.setFigure( recommendInfodatas.getFigure());
              goodsdatas.setProduct_id( recommendInfodatas.getProduct_id());
              startGoodsInfoActivity(goodsdatas);
            */
/*  gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      Toast.makeText(mContext,"推荐=="+position,Toast.LENGTH_SHORT).show();
                      ResultDatadatas.datas.RecommendInfodatas  recommendInfodatas=recommend_info.get(position);
                      Goodsdatas goodsdatas=new Goodsdatas();
                      goodsdatas.setName( recommendInfodatas.getName());
                      goodsdatas.setCover_price( recommendInfodatas.getCover_price());
                      goodsdatas.setFigure( recommendInfodatas.getFigure());
                      goodsdatas.setProduct_id( recommendInfodatas.getProduct_id());
                      startGoodsInfoActivity(goodsdatas);
                  }
              });
*//*

          }
      }
        class HotViewHolder extends RecyclerView.ViewHolder{
                private Context mContext;
                TextView tv_more_hot;
                GridView gv_hot;
            private HotGridViewAdapter adapter;


            public HotViewHolder(final Context mContext, View itemView) {
                super(itemView);
                this.mContext=mContext;
                tv_more_hot= (TextView) itemView.findViewById(R.id.tv_more_hot);
                gv_hot= (GridView) itemView.findViewById(R.id.gv_hot);

            }


            public void setData(final List<ResultDatadatas.datas.HotInfodatas> hot_info) {
                adapter = new HotGridViewAdapter(mContext, hot_info);
                gv_hot.setAdapter(adapter);

                gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(mContext, "热卖==" + position, Toast.LENGTH_SHORT).show();
                        ResultDatadatas.datas.HotInfodatas hotInfodatas=hot_info.get(position);
                        Goodsdatas goodsdatas=new Goodsdatas();
                        goodsdatas.setName(hotInfodatas.getName());
                        goodsdatas.setCover_price(hotInfodatas.getCover_price());
                        goodsdatas.setFigure(hotInfodatas.getFigure());
                        goodsdatas.setProduct_id(hotInfodatas.getProduct_id());
                        startGoodsInfoActivity(goodsdatas);
                    }
                });
            }
        }
}

*/
