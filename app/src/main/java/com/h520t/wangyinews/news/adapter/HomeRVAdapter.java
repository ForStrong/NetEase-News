package com.h520t.wangyinews.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.h520t.wangyinews.R;
import com.h520t.wangyinews.news.bean.HomeDetail;
import com.h520t.wangyinews.news.bean.ImgNewExtra;
import com.h520t.wangyinews.news.bean.VideoInfo;
import com.h520t.wangyinews.util.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;


public class HomeRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<HomeDetail> dataRV;
    private Context mContext;
    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private final int TYPE_3 = 3;
    public HomeRVAdapter(ArrayList<HomeDetail> dataRV) {
        this.dataRV = dataRV;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if (viewType==TYPE_1) {
            view = inflater.inflate(R.layout.adapter_home_item1, parent, false);
            return new HomeViewHolder(view);
        }else if (viewType==TYPE_2){
            view = inflater.inflate(R.layout.adapter_home_item2,parent,false);
            return new HomeViewHolder(view);
        }else{
            view = inflater.inflate(R.layout.adapter_home_item3,parent,false);
            return new HomeViewHolderOne(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeDetail detail = dataRV.get(position);

        bindView(detail,holder, position);


    }

    private void bindView(HomeDetail detail, @NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position)!=TYPE_3) {
           HomeViewHolder holder1 = (HomeViewHolder) holder;
            String imgUrl = detail.getImg();
            loadImgByGlide(imgUrl,holder1.newsImg);
            holder1.newsTitle.setText(detail.getTitle());
            holder1.newsSource.setText(detail.getSource());
            holder1.newsCount.setText(detail.getReplyCount()+"跟帖");
        }else{
            HomeViewHolderOne holder1 = (HomeViewHolderOne) holder;
            String imgUrl = detail.getImg();
            loadImgByGlide(imgUrl,holder1.newsImg);
            holder1.newsTitle.setText(detail.getTitle());
            holder1.newsSource.setText(detail.getSource());
            holder1.newsCount.setText(detail.getReplyCount()+"跟帖");
            List<ImgNewExtra> imgnewextra = detail.getImgnewextra();
            imgUrl = imgnewextra.get(0).getImgsrc();
            loadImgByGlide(imgUrl,holder1.newsImg1);
            imgUrl = imgnewextra.get(1).getImgsrc();
            loadImgByGlide(imgUrl,holder1.newsImg2);
        }

    }

    private void loadImgByGlide(String imgUrl, ImageView newsImg) {
        Glide.with(mContext).load(imgUrl).bitmapTransform(new GlideRoundTransform(mContext)).into(newsImg);
    }

    @Override
    public int getItemCount() {
        return dataRV==null?0:dataRV.size();
    }

    @Override
    public int getItemViewType(int position) {
        HomeDetail detail = dataRV.get(position);
        VideoInfo videoinfo = detail.getVideoinfo();
        List<ImgNewExtra> imgnewextra = detail.getImgnewextra();
        if (videoinfo==null&&imgnewextra==null){
            return TYPE_1;
        }else if (imgnewextra==null){
            return TYPE_2;
        }else {
            return TYPE_3;
        }
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{
        protected ImageView newsImg;
        protected TextView newsTitle;
        protected TextView newsSource;
        protected TextView newsCount;
        public HomeViewHolder(View itemView) {
            super(itemView);
            newsImg = itemView.findViewById(R.id.img_news);
            newsTitle = itemView.findViewById(R.id.title_news);
            newsSource = itemView.findViewById(R.id.source_news);
            newsCount = itemView.findViewById(R.id.reply_count_news);
        }
    }

    class HomeViewHolderOne extends HomeViewHolder{

        private ImageView newsImg1;
        private ImageView newsImg2;

        public HomeViewHolderOne(View itemView) {
            super(itemView);
            newsImg1 = itemView.findViewById(R.id.img1_news);
            newsImg2 = itemView.findViewById(R.id.img2_news);
        }


    }
}
