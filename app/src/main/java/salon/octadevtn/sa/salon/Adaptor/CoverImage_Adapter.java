package salon.octadevtn.sa.salon.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

import static salon.octadevtn.sa.salon.HomeActivityDrawer.setFragmentCover;


public class CoverImage_Adapter extends PagerAdapter {


    private ArrayList<Cover> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public CoverImage_Adapter(Context context, ArrayList<Cover> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.cover_images_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        //imageView.setImageResource(IMAGES.get(position));

        Glide.with(context).load(UrlStatic.pathImage + IMAGES.get(position).getImageUrl()).into(imageView);
        Zoomy.Builder builder = new Zoomy.Builder((Activity) context)
                .target(imageView)
                .tapListener(new TapListener() {
                    @Override
                    public void onTap(View v) {
                        Bundle bundle = new Bundle();

                        bundle.putSerializable("images", IMAGES);
                        bundle.putInt("position", position);
                        bundle.putInt("salonname", position);

                        setFragmentCover(bundle);


                    }
                })
                .interpolator(new OvershootInterpolator());
        builder.register();
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}