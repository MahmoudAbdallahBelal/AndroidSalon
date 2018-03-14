package salon.octadevtn.sa.salon.fragment.profileUpdate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Api.getCurrency;
import salon.octadevtn.sa.salon.Models.Currency;
import salon.octadevtn.sa.salon.Models.Promotion;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor.PromotionAdaptor;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Liste_Promotion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Liste_Promotion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String start_date, end_date;
    Bitmap selectedImage;
    List<Currency> currencyList = new ArrayList<>();
    View v;
    PromotionAdaptor adapter;
    ArrayList<Promotion> activityList;
    LinearLayout tryagain;
    LinearLayout progress;
    ArrayAdapter<String> adapterSpiner;
    Dialog dialog;
    String devise;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Liste_Promotion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Liste_Categorie.
     */
    // TODO: Rename and change types and number of parameters
    public static Liste_Promotion newInstance(String param1, String param2) {
        Liste_Promotion fragment = new Liste_Promotion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_liste_promotion, container, false);
        ((TextView) v.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);

        ((TextView) v.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_medium);
        tryagain = (LinearLayout) v.findViewById(R.id.tryagain);
        progress = (LinearLayout) v.findViewById(R.id.progress);
        getCurrency();
        ImageView imagetry = (ImageView) v.findViewById(R.id.imagetry);
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        v.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        rotate.setDuration(2000);
        rotate.setRepeatCount(Animation.INFINITE);
        imagetry.setAnimation(rotate);

        if (v != null) {
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
            activityList = new ArrayList<>();

            v.findViewById(R.id.addcat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_ad_promotion);
                    ((EditText) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.discount_val)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.type_val)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.priceval)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.duration_val)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.customer_dur_val)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.description)).setTypeface(MyApplication.type_jf_regular);
                    ((Button) dialog.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_regular);
                    ((Button) dialog.findViewById(R.id.cancel)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.discount)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.type)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.price)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.before)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.currency)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.duration)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.unite)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.costomer_dur)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
                    dialog.findViewById(R.id.pike).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, 125);


                        }
                    });
                    final TextView from = (TextView) dialog.findViewById(R.id.from);
                    from.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerFragment newFragment = new DatePickerFragment();
                            newFragment.setView(from, 0);
                            newFragment.show(getChildFragmentManager(), "datePicker");

                        }
                    });
                    final TextView to = (TextView) dialog.findViewById(R.id.to);
                    to.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerFragment newFragment = new DatePickerFragment();
                            newFragment.setView(to, 1);
                            newFragment.show(getChildFragmentManager(), "datePicker");

                        }
                    });
                    final TextView typeval = (TextView) dialog.findViewById(R.id.type_val);
                 /*   typeval.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupMenu popup = new PopupMenu(getActivity(), typeval);
                            //Inflating the Popup using xml file
                            popup.getMenuInflater().inflate(R.menu.discount_type, popup.getMenu());

                            //registering popup with OnMenuItemClickListener
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {
                                    typeval.setText(item.getTitle());
                                    return true;
                                }
                            });

                            popup.show();//showing popup menu
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        }
                    });*/
                    dialog.findViewById(R.id.pike).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, 125);


                        }
                    });
                    dialog.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, 125);
                        }
                    });
                    dialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.findViewById(R.id.imageseded).setVisibility(View.GONE);
                            dialog.findViewById(R.id.pike).setVisibility(View.VISIBLE);
                            selectedImage = null;
                        }
                    });

                    Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);

                    final ArrayList<String> s = new ArrayList<String>();
                    for (int i = 0; i < currencyList.size(); i++) {
                        if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
                            s.add(currencyList.get(i).getNameEn());
                        else
                            s.add(currencyList.get(i).getNameAr());
                    }
                    dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                            Object item = parent.getItemAtPosition(pos);
                            devise = s.get(pos) + "";
                        }

                        public void onNothingSelected(AdapterView<?> parent) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        }
                    });
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
                    ArrayAdapter<String> adapterSpiner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, s);
//set the spinners adapter to the previously created one.
                    dropdown.setAdapter(adapterSpiner);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.gravity = Gravity.CENTER;
                    dialog.getWindow().setAttributes(layoutParams);
                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.findViewById(R.id.update).setVisibility(View.GONE);
                            dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);

                            TextView title = (TextView) dialog.findViewById(R.id.name);
                            TextView description = (TextView) dialog.findViewById(R.id.description);
                            TextView discount_val = (TextView) dialog.findViewById(R.id.discount_val);
                            TextView duration_val = (TextView) dialog.findViewById(R.id.duration_val);
                            TextView customer_dur_val = (TextView) dialog.findViewById(R.id.customer_dur_val);
                            TextView priceval = (TextView) dialog.findViewById(R.id.priceval);
                            if (title.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(title);
                            }
                            if (description.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(description);
                            }
                            if (discount_val.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(discount_val);
                            }
                            if (duration_val.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(duration_val);
                            }
                            if (customer_dur_val.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(customer_dur_val);
                            }
                            if (priceval.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(priceval);
                            }




                            if ((!title.getText().toString().equals("") && selectedImage != null)) {

                                new Service().AddPromotion(title.getText().toString(),
                                        description.getText().toString(),
                                        start_date,
                                        end_date,
                                        discount_val.getText().toString(),
                                        duration_val.getText().toString(),
                                        customer_dur_val.getText().toString(),
                                        priceval.getText().toString(), devise
                                        , selectedImage,
                                        new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    dialog.dismiss();
                                                    api();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Object result) {
                                                Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFinish() {

                                            }

                                            @Override
                                            public void OnError(String message) {
                                                Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }

                        }
                    });

                    dialog.show();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                }
            });
            adapter = new PromotionAdaptor(activityList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);

        }
        api();
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api();
            }
        });
        adapter.setRecyclerClickListener(new PromotionAdaptor.RecyclerClickListener() {
            @Override
            public void onClick(final int position) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_ad_promotion);
                ((EditText) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                ((EditText) dialog.findViewById(R.id.name)).setText(activityList.get(position).getTitle());

                ((EditText) dialog.findViewById(R.id.discount_val)).setTypeface(MyApplication.type_jf_regular);
                ((EditText) dialog.findViewById(R.id.discount_val)).setText(activityList.get(position).getDiscount());

                ((TextView) dialog.findViewById(R.id.type_val)).setTypeface(MyApplication.type_jf_regular);
                // ((TextView) dialog.findViewById(R.id.type_val)).setText(activityList.get(position).get);

                ((EditText) dialog.findViewById(R.id.priceval)).setTypeface(MyApplication.type_jf_regular);
                ((EditText) dialog.findViewById(R.id.priceval)).setText(activityList.get(position).getPrice());

                ((EditText) dialog.findViewById(R.id.duration_val)).setTypeface(MyApplication.type_jf_regular);
                ((EditText) dialog.findViewById(R.id.duration_val)).setText(activityList.get(position).getTimeService());

                ((EditText) dialog.findViewById(R.id.customer_dur_val)).setTypeface(MyApplication.type_jf_regular);
                ((EditText) dialog.findViewById(R.id.customer_dur_val)).setText(activityList.get(position).getClientTime());

                ((EditText) dialog.findViewById(R.id.description)).setTypeface(MyApplication.type_jf_regular);
                ((EditText) dialog.findViewById(R.id.description)).setText(activityList.get(position).getDescription());


                ((Button) dialog.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_regular);
                ((Button) dialog.findViewById(R.id.cancel)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.discount)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.type)).setTypeface(MyApplication.type_jf_regular);
              //  ((TextView) dialog.findViewById(R.id.type)).setText(activityList.get(position).getCurrency());

                ((TextView) dialog.findViewById(R.id.price)).setTypeface(MyApplication.type_jf_regular);

                ((TextView) dialog.findViewById(R.id.before)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.currency)).setTypeface(MyApplication.type_jf_regular);

                ((TextView) dialog.findViewById(R.id.duration)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.from)).setText(activityList.get(position).getStartDate());
                ((TextView) dialog.findViewById(R.id.to)).setText(activityList.get(position).getEndDate());
                ((TextView) dialog.findViewById(R.id.unite)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.costomer_dur)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
                dialog.findViewById(R.id.imageseded).setVisibility(View.VISIBLE);
                dialog.findViewById(R.id.pike).setVisibility(View.GONE);

                ((ImageView) dialog.findViewById(R.id.image)).setImageBitmap(selectedImage);
                Glide.with(getActivity()).load(UrlStatic.pathImag + activityList.get(position).getImage())
                        .thumbnail(0.5f)
                        .crossFade()
                        .override(100, 100)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into((ImageView) dialog.findViewById(R.id.image));


                dialog.findViewById(R.id.pike).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 125);


                    }
                });
                final TextView from = (TextView) dialog.findViewById(R.id.from);
                from.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerFragment newFragment = new DatePickerFragment();
                        newFragment.setView(from, 0);
                        newFragment.show(getChildFragmentManager(), "datePicker");

                    }
                });
                final TextView to = (TextView) dialog.findViewById(R.id.to);
                to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerFragment newFragment = new DatePickerFragment();
                        newFragment.setView(to, 1);
                        newFragment.show(getChildFragmentManager(), "datePicker");

                    }
                });
                final TextView typeval = (TextView) dialog.findViewById(R.id.type_val);
                final LinearLayout typed = (LinearLayout) dialog.findViewById(R.id.typed);
              /*  typed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(getActivity(), typeval);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.discount_type, popup.getMenu());

                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                typeval.setText(item.getTitle());
                                return true;
                            }
                        });

                        popup.show();//showing popup menu
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                    }
                });*/
                dialog.findViewById(R.id.pike).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 125);


                    }
                });
                dialog.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 125);
                    }
                });
                dialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.findViewById(R.id.imageseded).setVisibility(View.GONE);
                        dialog.findViewById(R.id.pike).setVisibility(View.VISIBLE);
                        selectedImage = null;
                    }
                });

                Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);
//create a list of items for the spinner.
                final ArrayList<String> s = new ArrayList<String>();
                for (int i = 0; i < currencyList.size(); i++) {
                    if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
                        s.add(currencyList.get(i).getNameEn());
                    else
                        s.add(currencyList.get(i).getNameAr());
                }
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        devise = s.get(pos) + "";
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
                ArrayAdapter<String> adapterSpiner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, s);
//set the spinners adapter to the previously created one.
                dropdown.setAdapter(adapterSpiner);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(layoutParams);
                dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                ((Button) dialog.findViewById(R.id.add)).setText(getResources().getString(R.string.update));
                dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.findViewById(R.id.update).setVisibility(View.GONE);
                        dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);

                        TextView title = (TextView) dialog.findViewById(R.id.name);
                        TextView description = (TextView) dialog.findViewById(R.id.description);
                        TextView discount_val = (TextView) dialog.findViewById(R.id.discount_val);
                        TextView duration_val = (TextView) dialog.findViewById(R.id.duration_val);
                        TextView customer_dur_val = (TextView) dialog.findViewById(R.id.customer_dur_val);
                        TextView priceval = (TextView) dialog.findViewById(R.id.priceval);


                        new Service().UpdatePromotion(title.getText().toString(), description.getText().toString(),
                                start_date,
                                end_date,
                                discount_val.getText().toString(),
                                duration_val.getText().toString(),
                                customer_dur_val.getText().toString(),
                                priceval.getText().toString(), devise
                                , selectedImage, activityList.get(position).getId(), new UniversalCallBack() {
                                    @Override
                                    public void onResponse(Object result) {
                                        if (result != null) {
                                            dialog.dismiss();
                                            api();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Object result) {
                                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFinish() {

                                    }

                                    @Override
                                    public void OnError(String message) {
                                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();


                                    }
                                });

                    }
                });
                dialog.show();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 125:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        dialog.findViewById(R.id.imageseded).setVisibility(View.VISIBLE);
                        dialog.findViewById(R.id.pike).setVisibility(View.GONE);

                        ((ImageView) dialog.findViewById(R.id.image)).setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    public void api() {
        progress.setVisibility(View.VISIBLE);
        tryagain.setVisibility(View.GONE);
        activityList.clear();
        new Service().getpromotion(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    progress.setVisibility(View.GONE);
                    tryagain.setVisibility(View.GONE);
                    Promotion[] salon_cat = (Promotion[]) result;
                    for (int i = 0; i < salon_cat.length; i++) {
                        activityList.add(salon_cat[i]);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    progress.setVisibility(View.GONE);
                    tryagain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Object result) {
                progress.setVisibility(View.GONE);
                tryagain.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                progress.setVisibility(View.GONE);
                tryagain.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        }
    }

    public void getCurrency() {
        new getCurrency().currency(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) throws JSONException {
                if (result != null) {
                    currencyList.clear();
                    Currency[] currencies = (Currency[]) result;
                    for (int i = 0; i < currencies.length; i++) {
                        currencyList.add(currencies[i]);
                        if (adapterSpiner != null) adapterSpiner.notifyDataSetChanged();
                    }
                } else getCurrency();
            }

            @Override
            public void onFailure(Object result) {
                getCurrency();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                getCurrency();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        static TextView textView;
        static int i;

        public static void setView(TextView textView2, int i2) {
            i = i2;
            textView = textView2;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
                Locale locale = new Locale("ar");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getContext().getResources().updateConfiguration(config,
                        getContext().getResources().getDisplayMetrics());
            } else {
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getContext().getResources().updateConfiguration(config,
                        getContext().getResources().getDisplayMetrics());

            }

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
           //dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getContext().getResources().updateConfiguration(config,
                    getContext().getResources().getDisplayMetrics());

            if(i == 1)
                dialog.getDatePicker().setMinDate(c.getTimeInMillis());

            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
                if (i == 0) {
                    textView.setText("From : " + day + " - " + theMonth(month) + " - "
                            + year);
                    start_date = year + "-" + (month + 1) + "-" + day;
                } else {

                    textView.setText("To : " + day + " - " + theMonth(month) + " - "
                            + year);
                    end_date = year + "-" + (month + 1) + "-" + day;

                }
            } else {
                if (i == 0) {
                    textView.setText("من : " + day + " - " + theMonth(month) + " - " + year);
                    start_date = year + "-" + (month + 1) + "-" + day;
                } else {
                    textView.setText("إلى :  " + day + " - " + theMonth(month) + " - " + year);
                    end_date = year + "-" + (month + 1) + "-" + day;


                  }
            }
            if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
                Locale locale = new Locale("ar");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getContext().getResources().updateConfiguration(config,
                        getContext().getResources().getDisplayMetrics());
            } else {
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getContext().getResources().updateConfiguration(config,
                        getContext().getResources().getDisplayMetrics());

            }
        }
    }

}
