package com.h520t.wangyinews.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.h520t.wangyinews.R;
import com.h520t.wangyinews.news.bean.HotDetail;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class HotAdapter extends Adapter<HotAdapter.ViewHolder>{
    private List<HotDetail> mHotDetails;
    private Context mContext;
    public HotAdapter(List<HotDetail> hotDetails,Context context) {
        mHotDetails = hotDetails;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_hot_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HotDetail detail = mHotDetails.get(position);
        Log.i("h520it1", "onBindViewHolder: "+mHotDetails.toString());
        Log.i("h520it1", "onBindViewHolder: "+detail.toString());
        String img_url = detail.getImg();
        Glide.with(mContext)
                .load(img_url)
                .placeholder(R.drawable.xidada )
                .error(R.drawable.xidada)
                .into(holder.img);
        holder.title.setText(detail.getTitle());
        holder.source.setText(detail.getSource());
        holder.replyCount.setText(detail.getReplyCount()+"");
    }



    @Override
    public int getItemCount() {
        return mHotDetails==null?0:mHotDetails.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title;
        TextView replyCount;
        TextView source;
        ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
            title = view.findViewById(R.id.title_news);
            replyCount = view.findViewById(R.id.replyCount);
            source = view.findViewById(R.id.source);
        }
    }
}
