package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Mostpopulair;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.Salon_services;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class ExploreAdaptor extends RecyclerView.Adapter<ExploreAdaptor.ViewHolder> {

    Context context;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Mostpopulair> itemList;

    // Constructor of the class
    public ExploreAdaptor(ArrayList<Mostpopulair> itemList, Context context) {
        this.context = context;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_category_liste, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        if (itemList.get(listPosition).getType().equals("promotion"))
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + itemList.get(listPosition).getImage()).into(holder.imageView);
        else
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + itemList.get(listPosition).getImageUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemList.get(listPosition).getType().equals("promotion")) {
                    if (holder.promotion.getAlpha() == 0) {
                        if (itemList.get(listPosition).getType().equals("promotion")) {
                            holder.promotion.animate().alpha(1f).setDuration(1000);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(itemList.get(listPosition).getId(), 1));

                                    holder.promotion.animate().alpha(0.0f).setDuration(1000);
                                }
                            }, 0);
                        }
                    } else
                        ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(itemList.get(listPosition).getId(), 1));
                } else {
                    if (holder.service.getAlpha() == 0) {
                        holder.service.animate().alpha(1f).setDuration(1000);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((HomeActivityDrawer) context).setFragment(Salon_services.newInstance(itemList.get(listPosition).getId()));
                                holder.service.animate().alpha(0.0f).setDuration(1000);
                            }
                        }, 0);
                    } else
                        ((HomeActivityDrawer) context).setFragment(Salon_services.newInstance(itemList.get(listPosition).getId()));
                }

            }
        });

    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView, imagep, images;
        TextView text;
        LinearLayout promotion, service;
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imagep = (ImageView) itemView.findViewById(R.id.imagep);
            images = (ImageView) itemView.findViewById(R.id.images);
            imagep.setColorFilter(Color.parseColor("#646464"));
            images.setColorFilter(Color.parseColor("#646464"));
            service = (LinearLayout) itemView.findViewById(R.id.service);
            promotion = (LinearLayout) itemView.findViewById(R.id.promotion);

        }

        @Override
        public void onClick(View view) {
        }
    }
}