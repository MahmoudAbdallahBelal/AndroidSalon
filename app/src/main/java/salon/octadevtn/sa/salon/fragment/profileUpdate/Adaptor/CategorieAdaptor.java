package salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Models.Category;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class CategorieAdaptor extends RecyclerView.Adapter<CategorieAdaptor.ViewHolder> {

    RecyclerClickListener recyclerClickListener;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Category> itemList;

    // Constructor of the class
    public CategorieAdaptor(ArrayList<Category> itemList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_salon, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }


    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Glide
                .with(MyApplication.getAppContext())
                .load(UrlStatic.pathImag + itemList.get(listPosition).getImageUrl())
                .override(200, 200)
                .centerCrop()
                .into(holder.imageView);
        holder.title.setText(itemList.get(listPosition).getName());
        holder.content.setText(MyApplication.getAppContext().getResources().getString(R.string.contains) + " " + itemList.get(listPosition).getService().size() + " " + MyApplication.getAppContext().getResources()
                .getString(R.string.sercices));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onClick(listPosition);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Service().DeleteService(Integer.parseInt(itemList.get(listPosition).getId()), new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            itemList.remove(listPosition);
                            notifyItemRemoved(listPosition);
                            notifyItemRangeChanged(listPosition, itemList.size());

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
        holder.title.setTypeface(MyApplication.type_jf_medium);
        holder.content.setTypeface(MyApplication.type_jf_regular);

    }


    public interface RecyclerClickListener {
        void onClick(int position);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        ImageView imageView, edit, delete;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }

    }
}