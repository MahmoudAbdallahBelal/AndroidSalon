package salon.octadevtn.sa.salon.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import salon.octadevtn.sa.salon.Models.Price;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * Created by Marwen octadev on 7/17/2017.
 */

public class PriceAdaptor extends StatelessSection {
    private static final String TAG = PriceAdaptor.class.getSimpleName();
    private String title;
    private List<Price> list;

    public PriceAdaptor(String title, List<Price> list) {
        super(R.layout.header_layout, R.layout.item_layout);
        this.title = title;
        this.list = list;
    }

    @Override
    public int getContentItemsTotal() {
        if (list == null)
            return 0;
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder iHolder = (ItemViewHolder) holder;
        iHolder.title.setText(list.get(position).getName()
        );
        iHolder.price.setText(list.get(position).getCurrency() + " " + list.get(position).getMinPrice() + " - " + list.get(position).getCurrency() + " " + list.get(position).getMaxPrice()
        );
        iHolder.description.setText(list.get(position).getDescription()
        );
        Glide.with(MyApplication.getAppContext())

                .load(UrlStatic.pathImage + list.get(position).getImageUrl())
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        iHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        iHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(iHolder.imageView);
        iHolder.description.setTypeface(MyApplication.type_jf_light);
        iHolder.price.setTypeface(MyApplication.type_jf_regular);
        iHolder.title.setTypeface(MyApplication.type_jf_medium);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder hHolder = (HeaderViewHolder) holder;
        hHolder.headerTitle.setText(title);
        hHolder.headerTitle.setTypeface(MyApplication.type_jf_medium);
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (TextView) itemView.findViewById(R.id.header_id);
        }

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, price;
        ImageView imageView;
        ProgressBar progressBar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);

        }
    }


}