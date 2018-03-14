package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.InputStream;
import java.util.ArrayList;

import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.Models.CoverModel;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class AdaptorCoverUser extends RecyclerView.Adapter<AdaptorCoverUser.ViewHolder> {

    ArrayList<CoverModel> mArrayUri;
    Context mcontext;
    ViewHolder holder;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;

    // Constructor of the class
    public AdaptorCoverUser(ArrayList<CoverModel> mArrayUri, Context context) {
        this.mArrayUri = mArrayUri;
        mcontext = context;
    }


    // get the size of the list
    @Override
    public int getItemCount() {
        return mArrayUri.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cover, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder hold, final int position) {
        holder = hold;
        if (!mArrayUri.get(position).isStatus()) {
            final InputStream imageStream;
            holder.imagev.setImageBitmap(mArrayUri.get(position).getBitmap());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mArrayUri.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeRemoved(position, mArrayUri.size());
                    notifyDataSetChanged();
                }
            });
        } else {
            Cover cover = mArrayUri.get(position).getCover();
            Glide.with(mcontext).load(UrlStatic.pathImag + cover.getImageUrl())
                    .thumbnail(0.5f)
                    .crossFade()
                    .override(100, 100)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imagev);
        }
        holder.progress.setVisibility(View.GONE);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imagev;
        LinearLayout delete;
        TextView text;
        ProgressBar progress;

        public ViewHolder(View itemView) {
            super(itemView);
            delete = (LinearLayout) itemView.findViewById(R.id.delete);
            imagev = (ImageView) itemView.findViewById(R.id.image);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);

        }

        @Override
        public void onClick(View view) {
        }
    }

}