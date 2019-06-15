package com.bit.flickertestproject.view.feed.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.flickertestproject.R;
import com.bit.flickertestproject.data.server.model.Item;
import com.bit.flickertestproject.util.CircleTransform;
import com.bit.flickertestproject.view.feed.adapter.FeedAdapter.FeedViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private List<Item> items = new ArrayList<>();
    private Context context;
    private OnClickRecyclerViewItem onClickRecyclerViewItem;

    public FeedAdapter(final Context context) {
        this.context = context;
        this.onClickRecyclerViewItem = (OnClickRecyclerViewItem) context;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_layout, parent, false);
        return new FeedViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, final int position) {
        Item item = items.get(position);
        holder.feedTitle.setText(item.getTitle());
        holder.authorName.setText(item.getAuthorName());
        Picasso.get().load(context.getString(R.string.profile_url, item.getAuthorId())).transform(new CircleTransform()).into(holder.ivAuthorPic, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(final Exception e) {

            }
        });

        Picasso.get().load(items.get(position).getMedia().getcImage()).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(final Exception e) {

            }
        });

        holder.imageView.setOnClickListener(v -> {
            onClickRecyclerViewItem.onClick(item);
        });
    }

    public void updateFeedView(List<Item> data) {
        int pos = items.size();
        items.addAll(data);
        notifyItemInserted(pos);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    static class FeedViewHolder extends ViewHolder {

        private ImageView imageView;
        private ImageView ivAuthorPic;
        private TextView authorName;
        private TextView feedTitle;

        public FeedViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            ivAuthorPic = itemView.findViewById(R.id.iv_author_pic);
            authorName = itemView.findViewById(R.id.iv_author_name);
            feedTitle = itemView.findViewById(R.id.iv_feed_title);
        }
    }

    public interface OnClickRecyclerViewItem {
        void onClick(Item item);
    }

}
