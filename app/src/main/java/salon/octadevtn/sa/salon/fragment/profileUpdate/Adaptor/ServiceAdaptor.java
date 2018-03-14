package salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;

/**
 * Created by Marwen octadev on 7/17/2017.
 */

public class ServiceAdaptor extends StatelessSection {
    private static final String TAG = ServiceAdaptor.class.getSimpleName();
    SectionedRecyclerViewAdapter sectionAdapter;
    RecyclerClickListener recyclerClickListener;
    private String title;
    private List<salon.octadevtn.sa.salon.fragment.profileUpdate.Models.Service> list;

    public ServiceAdaptor(String title, List<salon.octadevtn.sa.salon.fragment.profileUpdate.Models.Service> list, SectionedRecyclerViewAdapter sectionAdapter) {
        super(R.layout.header_layout2, R.layout.item_service_salon);
        this.title = title;
        this.list = list;
        this.sectionAdapter = sectionAdapter;
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder2, final int position) {
        ItemViewHolder holder = (ItemViewHolder) holder2;
        Glide
                .with(MyApplication.getAppContext())
                .load(UrlStatic.pathImag + list.get(position).getImageUrl())
                .override(200, 200)
                .centerCrop()
                .into(holder.imageView);
        holder.title.setText(list.get(position).getName());
        holder.content.setText(list.get(position).getDescription());

        holder.title.setTypeface(MyApplication.type_jf_medium);
        holder.content.setTypeface(MyApplication.type_jf_regular);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onClick(position);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Service().DeleteServices(Integer.parseInt(list.get(position).getId()), new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            list.remove(position);
                            sectionAdapter.notifyItemRemovedFromSection(ServiceAdaptor.this, position);

                            sectionAdapter.notifyItemRangeChangedInSection(ServiceAdaptor.this, position, list.size());
                            int x = sectionAdapter.getHeaderPositionInAdapter(ServiceAdaptor.this);

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
        TextView title, content;
        ImageView imageView, edit, delete;
        ProgressBar progressBar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            title.setTypeface(MyApplication.type_jf_medium);
            content.setTypeface(MyApplication.type_jf_regular);
        }
    }


}