package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.ListeCover;

/**
 * Created by Marwen octadev on 7/14/2017.
 */

public class GalleryCoverAdapter extends RecyclerView.Adapter<GalleryCoverAdapter.MyViewHolder> {

    private List<salon.octadevtn.sa.salon.Models.Activity.Object> images;
    private Context mContext;

    public GalleryCoverAdapter(Context context, List<salon.octadevtn.sa.salon.Models.Activity.Object> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final salon.octadevtn.sa.salon.Models.Activity.Object image = images.get(position);
        //  Toast.makeText(mContext, image.getImageUrl()+ "", Toast.LENGTH_SHORT).show();
        Glide.with(mContext).load(UrlStatic.pathImag + image.getImageUrl())
                .thumbnail(0.5f)
                .crossFade()


                .override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Cover> IMAGES = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    Cover cover = new Cover();
                    cover.setId(images.get(i).getId());
                    cover.setCreatedAt(images.get(i).getCreatedAt());
                    cover.setImageUrl(images.get(i).getImageUrl());
                    cover.setIdSalon(images.get(i).getIdSalon());
                    IMAGES.add(cover);
                }
                Bundle bundle = new Bundle();

                bundle.putSerializable("images", IMAGES);
                bundle.putInt("position", position);
                bundle.putString("salonname", "jj");
                ((HomeActivityDrawer) mContext).setFragment(new ListeCover(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
}