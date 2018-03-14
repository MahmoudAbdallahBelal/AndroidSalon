package salon.octadevtn.sa.salon.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import salon.octadevtn.sa.salon.Models.Promotion;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class PromotionAdaptorfilter extends RecyclerView.Adapter<PromotionAdaptorfilter.ViewHolder> implements Filterable {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Promotion> itemList = new ArrayList<Promotion>();
    ;
    private RecyclerClickListener recyclerClickListener;
    private ArrayList<Promotion> itemList2 = new ArrayList<Promotion>();
    ;
    private CustomFilter mFilter;
    private List<Promotion> filteredList;

    // Constructor of the class
    public PromotionAdaptorfilter(ArrayList<Promotion> itemList) {
        this.itemList.addAll(itemList);

        this.itemList2.addAll(itemList);
        mFilter = new CustomFilter(PromotionAdaptorfilter.this);
        filteredList = new ArrayList<Promotion>();
        filteredList.addAll(itemList);

    }

    public static String theMonth(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] monthNamesAr = {"يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو",
                "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"};
        if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
            return monthNames[month];
        else
            return monthNamesAr[month];

    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion_salon_filtere, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

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
                .load(UrlStatic.pathImag + itemList.get(listPosition).getImage())
                .override(200, 200)
                .centerCrop()
                .into(holder.imageView);
        holder.title.setText(itemList.get(listPosition).getTitle());
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
            try {
                Date date1 = dt.parse(itemList.get(listPosition).getStartDate());
                Date date2 = dt.parse(itemList.get(listPosition).getEndDate());
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(date2);
                holder.date.setText("From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                );


            } catch (ParseException e) {
                e.printStackTrace();
            }


            NumberFormat formatter = new DecimalFormat("#0.0");
            Double discount = Double.parseDouble(itemList.get(listPosition).getPrice()) - ((Double.parseDouble(itemList.get(listPosition).getPrice()) / 100) * (Double.parseDouble(itemList.get(listPosition).getDiscount())));
            holder.discount.setText("discount %" + itemList.get(listPosition).getDiscount());
            holder.price.setText(formatter.format(discount) + " instead of " + itemList.get(listPosition).getPrice());
        } else {
            try {
                Date date1 = dt.parse(itemList.get(listPosition).getStartDate());
                Date date2 = dt.parse(itemList.get(listPosition).getEndDate());
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(date2);
                holder.date.setText("من " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " الى " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                );


            } catch (ParseException e) {
                e.printStackTrace();
            }


            NumberFormat formatter = new DecimalFormat("#0.0");
            Double discount = Double.parseDouble(itemList.get(listPosition).getPrice()) - ((Double.parseDouble(itemList.get(listPosition).getPrice()) / 100) * (Double.parseDouble(itemList.get(listPosition).getDiscount())));
            holder.discount.setText("خصم  %" + itemList.get(listPosition).getDiscount());
            holder.price.setText(formatter.format(discount) + " بدل من " + itemList.get(listPosition).getPrice());

        }


        holder.price.setTypeface(MyApplication.type_jf_regular);
        holder.discount.setTypeface(MyApplication.type_jf_regular);
        holder.title.setTypeface(MyApplication.type_jf_medium);
        holder.date.setTypeface(MyApplication.type_jf_regular);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onClick(itemList.get(listPosition));
                }
            }
        });

    }

    public interface RecyclerClickListener {
        void onClick(Promotion item);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, discount, price;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            discount = (TextView) itemView.findViewById(R.id.discount);
            price = (TextView) itemView.findViewById(R.id.price);


        }

    }

    public class CustomFilter extends Filter {
        private PromotionAdaptorfilter mAdapter;

        private CustomFilter(PromotionAdaptorfilter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(itemList2);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Promotion mWords : itemList2) {
                    if (mWords.getTitle().toLowerCase().startsWith(filterPattern)) {
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
            System.out.println("Count Number 2 " + ((List<Promotion>) results.values).size());
            itemList.clear();
            itemList.addAll(((List<Promotion>) results.values));
            this.mAdapter.notifyDataSetChanged();
        }
    }
}