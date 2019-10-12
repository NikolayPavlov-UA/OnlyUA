package com.ua.only.models;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.ua.only.R;
import java.util.List;

public class NewsItem extends AbstractItem<NewsItem, NewsItem.ViewHolder> {
	public String title;
	public String desc;
	public String imgUrl;
	
	public NewsItem withImage(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public NewsItem withTitle(String title) {
        this.title = title;
        return this;
    }

    public NewsItem withDescription(String description) {
        this.desc = description;
        return this;
    }

	@Override
	public int getType() {
		return R.id.fastadapter_news_item_id;
	}

	@Override
	public int getLayoutRes() {
		return R.layout.news_item;
	}

	@Override
	public ViewHolder getViewHolder(@NonNull View v) {
		return new ViewHolder(v);
	}

	@Override
	public void bindView(ViewHolder holder, List<Object> payloads) {
		super.bindView(holder, payloads);
		
		Context ctx = holder.itemView.getContext();
		
		holder.newsTitle.setText(title);
		holder.newsDesc.setText(desc);
		holder.newsImg.setImageBitmap(null);
		
		Glide.with(ctx).load(imgUrl).centerCrop().into(holder.newsImg);
	}

	@Override
	public void unbindView(ViewHolder holder) {
		super.unbindView(holder);
		
		Glide.with(holder.itemView.getContext()).clear(holder.newsImg);
		holder.newsImg.setImageDrawable(null);
		holder.newsDesc.setText(null);
	}
	
	protected static class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsDesc;
		AppCompatImageView newsImg;

        public ViewHolder(View view) {
            super(view);
            newsTitle = view.findViewById(R.id.news_title);
			newsDesc = view.findViewById(R.id.news_desc);
			newsImg = view.findViewById(R.id.news_img);
        }
    }
}
