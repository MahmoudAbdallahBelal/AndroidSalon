package salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor;

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

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import salon.octadevtn.sa.salon.Models.PriceCategory.Price;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;

/**
 * Created by Marwen octadev on 7/17/2017.
 */

public class PriceAdaptor extends StatelessSection {
    private static final String TAG = PriceAdaptor.class.getSimpleName();
    SectionedRecyclerViewAdapter sectionAdapter;
    RecyclerClickListener recyclerClickListener;
    private String title;
    private List<Price> list;

    public PriceAdaptor(String title, List<Price> list, SectionedRecyclerViewAdapter sectionAdapter) {
        super(R.layout.header_layout, R.layout.item_layout_price);
        this.title = title;
        this.list = list;
        this.sectionAdapter = sectionAdapter;

    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

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
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
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
                .override(150,150)
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
        iHolder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onClick(position);
                }
            }
        });
        iHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Service().DeletePrice(Integer.parseInt(list.get(position).getId()), new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {

                            list.remove(position);
                            sectionAdapter.notifyItemRemovedFromSection(PriceAdaptor.this, position);

                            sectionAdapter.notifyItemRangeChangedInSection(PriceAdaptor.this, position, list.size());
                            int x = sectionAdapter.getHeaderPositionInAdapter(PriceAdaptor.this);
                            Section s = sectionAdapter.getSectionForPosition(x);
                            if (s.getSectionItemsTotal() == 0) {
                                sectionAdapter.notifyItemRemoved(x);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Object result) {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void OnError(String message) {

                    }
                });
            }
        });


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

    public interface RecyclerClickListener {
        void onClick(int position);
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
        ImageView imageView, delete_button, edit_button;
        ProgressBar progressBar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            delete_button = (ImageView) itemView.findViewById(R.id.delete);
            edit_button = (ImageView) itemView.findViewById(R.id.edit);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);

        }
    }


}