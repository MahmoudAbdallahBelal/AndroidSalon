package salon.octadevtn.sa.salon.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import salon.octadevtn.sa.salon.Models.Screen;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;


public class ColorFragment extends Fragment {

    private static final String EXTRA_COLOR = "salon.octadevtn.sa.salon.fragment.ColorFragment.EXTRA_COLOR";
    private static final String EXTRA_position = "salon.octadevtn.sa.salon.fragment.ColorFragment.EXTRA_position";
    private static String json;
    private static Screen[] screen;

    LinearLayout mMainLayout;

    public static ColorFragment newInstance(int backgroundColor, int id) throws IOException, JSONException {
        json = AssetJSONFile("screen.json", MyApplication.getAppContext());
        JSONObject jsonObject = new JSONObject(json);
        Log.d("jsonObject", jsonObject.toString());
        Gson gson = new Gson();
        screen = gson.fromJson(String.valueOf(jsonObject.getJSONArray("screen")), Screen[].class);
        Log.d("jsonObject23", screen.length + "");
        ColorFragment fragment = new ColorFragment();
        Bundle bdl = new Bundle();
        bdl.putInt(EXTRA_COLOR, backgroundColor);
        bdl.putInt(EXTRA_position, id);

        fragment.setArguments(bdl);

        return fragment;
    }

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dummy, container, false);
        Bundle bdl = getArguments();

        mMainLayout = (LinearLayout) v.findViewById(R.id.main_layout);

        LayerDrawable bgDrawable = (LayerDrawable) mMainLayout.getBackground();
        GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.background_shape);
        shape.setColor(bdl.getInt(EXTRA_COLOR));
        String local = Locale.getDefault().getLanguage();
        Log.d("language", local);
        ((TextView) mMainLayout.findViewById(R.id.description)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) mMainLayout.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_regular);

        if (local.toLowerCase().contains("ar")) {
            ((TextView) mMainLayout.findViewById(R.id.description)).setText(screen[bdl.getInt(EXTRA_position)].getDescriptionAr());
            ((TextView) mMainLayout.findViewById(R.id.title)).setText(screen[bdl.getInt(EXTRA_position)].getTitleAr());
        } else {
            ((TextView) mMainLayout.findViewById(R.id.description)).setText(screen[bdl.getInt(EXTRA_position)].getDescriptionEn());
            ((TextView) mMainLayout.findViewById(R.id.title)).setText(screen[bdl.getInt(EXTRA_position)].getTitleEn());
        }

        ((ImageView) mMainLayout.findViewById(R.id.image)).setImageResource(Static.screen[bdl.getInt(EXTRA_position)]);

        return v;
    }
}
