package salon.octadevtn.sa.salon.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.Models.User;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.ViewHolder> implements Filterable {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<User> itemList;
    private ArrayList<User> itemList2 = new ArrayList<User>();
    ;
    private CustomFilter mFilter;
    private List<User> filteredList;
    private RecyclerClickListener recyclerClickListener;

    // Constructor of the class
    public UserAdaptor(ArrayList<User> itemList) {
        this.itemList = itemList;
        this.itemList2.addAll(itemList);
        mFilter = new CustomFilter(UserAdaptor.this);
        filteredList = new ArrayList<User>();
        filteredList.addAll(itemList);


    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList2.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Glide
                .with(MyApplication.getAppContext())
                .load(UrlStatic.pathImag + itemList2.get(listPosition).getPhoto())
                .override(200, 200)
                .centerCrop()
                .into(holder.imageView);
        if (itemList.get(listPosition).getGenre().equals("men"))
            holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
        else
            holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

        holder.title.setText(itemList2.get(listPosition).getUsername());
        holder.content.setText(itemList2.get(listPosition).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onClick(itemList2.get(listPosition));
                }
            }
        });
        holder.title.setTypeface(MyApplication.type_jf_medium);
        holder.content.setTypeface(MyApplication.type_jf_regular);

    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

    }

    public interface RecyclerClickListener {
        void onClick(User item);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        CircleImageView imageView, edit, delete;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            imageView = (CircleImageView) itemView.findViewById(R.id.image);
        }

    }

    public class CustomFilter extends Filter {
        private UserAdaptor mAdapter;

        private CustomFilter(UserAdaptor mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(itemList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final User mWords : itemList) {
                    if (mWords.getUsername().toLowerCase().startsWith(filterPattern) || mWords.getName().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(mWords);
                    }
                }
            }
            System.out.println("Count Number " + filteredList.size());

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            System.out.println("Count Number 2 " + ((List<User>) results.values).size());
            itemList2.clear();
            itemList2.addAll(((List<User>) results.values));
            this.mAdapter.notifyDataSetChanged();
        }
    }


}