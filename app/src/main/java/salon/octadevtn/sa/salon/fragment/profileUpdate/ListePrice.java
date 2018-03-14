package salon.octadevtn.sa.salon.fragment.profileUpdate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import salon.octadevtn.sa.salon.Api.getCurrency;
import salon.octadevtn.sa.salon.Api.getPrice;
import salon.octadevtn.sa.salon.Models.Currency;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor.PriceAdaptor;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor.listeAdaptor2;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;

import static android.app.Activity.RESULT_OK;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;


public class ListePrice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Dialog dialog;
    ArrayAdapter<String> adapterSpiner;
    List<Currency> currencyList = new ArrayList<>();
    int id_cat = -1;
    Bitmap selectedImage;
    LinearLayout tryagain;
    LinearLayout progress;
    SectionedRecyclerViewAdapter sectionAdapter;
    ArrayList<salon.octadevtn.sa.salon.Models.PriceCategory.Category> categories;
    View v;
    String devise;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public ListePrice() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ListePrice newInstance(String param1, String param2) {
        ListePrice fragment = new ListePrice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        v = inflater.inflate(R.layout.fragment_liste_price, container, false);
        ((TextView) v.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);

        ((TextView) v.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) v.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_medium);
        v.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        RecyclerView addHeaderRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        addHeaderRecyclerView.setLayoutManager(linearLayoutManager);
        addHeaderRecyclerView.setHasFixedSize(true);
        categories = new ArrayList<>();
        tryagain = (LinearLayout) v.findViewById(R.id.tryagain);
        progress = (LinearLayout) v.findViewById(R.id.progress);
        getCurrency();
        ImageView imagetry = (ImageView) v.findViewById(R.id.imagetry);
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        rotate.setDuration(2000);
        rotate.setRepeatCount(Animation.INFINITE);
        imagetry.setAnimation(rotate);

        sectionAdapter = new SectionedRecyclerViewAdapter();

        addHeaderRecyclerView.setAdapter(sectionAdapter);
        api();
        v.findViewById(R.id.addcat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categories.size() > 0) {
                    id_cat = -1;
                    dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_addprice);
                    ((TextView) dialog.findViewById(R.id.cate)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.price)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.priceto)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.currency)).setTypeface(MyApplication.type_jf_regular);

                    ((Button) dialog.findViewById(R.id.cancel)).setTypeface(MyApplication.type_jf_regular);
                    ((Button) dialog.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_regular);

                    ((EditText) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.priceval)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.pricevaltp)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.description)).setTypeface(MyApplication.type_jf_regular);


                    dialog.findViewById(R.id.pike).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, 125);


                        }
                    });
                    final ExpandableRelativeLayout expandableLayout2 = (ExpandableRelativeLayout) dialog.findViewById(R.id.expandableLayout1);

                    RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycle);
                    listeAdaptor2 adapter = new listeAdaptor2(categories);


                    adapter.setMyAdapterListener(new listeAdaptor2.OnItemClickListener() {
                        @Override
                        public void onItemClick(int item) {
                            expandableLayout2.toggle();
                            id_cat = Integer.parseInt(categories.get(item).getId());
                            ((TextView) dialog.findViewById(R.id.cate)).setText(categories.get(item).getName());
                        }
                    });
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(adapter);
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
                    dialog.findViewById(R.id.type).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandableLayout2.toggle();

                        }
                    });

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

                    Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);
                    final ArrayList<String> s = new ArrayList<String>();
                    for (int i = 0; i < currencyList.size(); i++) {
                        if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
                            s.add(currencyList.get(i).getNameEn());
                        else
                            s.add(currencyList.get(i).getNameAr());
                    }
                    ArrayAdapter<String> adapterSpiner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, s);
                    dropdown.setAdapter(adapterSpiner);
                    dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            Object item = parent.getItemAtPosition(pos);
                            devise = s.get(pos) + "";
                        }

                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.findViewById(R.id.update).setVisibility(View.GONE);
                            dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);

                            TextView text = (TextView) dialog.findViewById(R.id.name);
                            TextView description = (TextView) dialog.findViewById(R.id.description);
                            if (text.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(text);
                            }
                            if (description.getText().toString().equals("")) {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(description);
                            }
                            if ((!text.getText().toString().equals("") && selectedImage != null && !description.getText().toString().equals("")
                                    && id_cat > 0)) {
                                new Service().AddPrice(text.getText().toString(), description.getText().toString(), id_cat + "", ((EditText) dialog.findViewById(R.id.priceval)).getText().toString(), ((EditText) dialog.findViewById(R.id.pricevaltp)).getText().toString(), devise, selectedImage, new UniversalCallBack() {
                                    @Override
                                    public void onResponse(Object result) {
                                        if (result != null) {
                                            dialog.dismiss();
                                            api();
                                        } else
                                            showError();

                                    }

                                    @Override
                                    public void onFailure(Object result) {
                                        showError();
                                    }


                                    @Override
                                    public void onFinish() {

                                    }

                                    @Override
                                    public void OnError(String message) {
                                        showError();
                                    }
                                });
                            }
                        }
                    });
                    dialog.show();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                } else {
                    int gravity = LEFT;
                    if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                        gravity = RIGHT;

                 /*   Alerter.create(getActivity())
                            .setTitle(getActivity().getResources().getString(R.string.please))
                            .setText(getActivity().getResources().getString(R.string.please1))
                            .setIcon(getResources().getDrawable(R.drawable.type_icon))
                            .setDuration(2000)
                            .setContentGravity(gravity)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Alerter.hide();
                                }

                            })
                            .show();*/


                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getActivity().getResources().getString(R.string.please))
                            .setContentText(getActivity().getResources().getString(R.string.please1))
                            .setConfirmText(getActivity().getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                }
            }
        });
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api();
            }
        });

        return v;
    }

    private void api() {
        progress.setVisibility(View.VISIBLE);
        tryagain.setVisibility(View.GONE);
        categories.clear();
        new getPrice().price(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) throws JSONException {
                if (result != null) {
                    progress.setVisibility(View.GONE);
                    tryagain.setVisibility(View.GONE);
                    categories.clear();
                    sectionAdapter.removeAllSections();
                    final salon.octadevtn.sa.salon.Models.PriceCategory.Category[] categorie = (salon.octadevtn.sa.salon.Models.PriceCategory.Category[]) result;
                    for (int i = 0; i < categorie.length; i++) {
                        categories.add(categorie[i]);
                    }
                    for (int i = 0; i < categories.size(); i++) {
                        PriceAdaptor firstSection = new PriceAdaptor(categories.get(i).getName(), categories.get(i).getPrice(), sectionAdapter);
                        if (categories.get(i).getPrice() != null)
                            if (categories.get(i).getPrice().size() > 0)
                                sectionAdapter.addSection(firstSection);
                        sectionAdapter.notifyDataSetChanged();
                        final int finalI = i;
                        firstSection.setRecyclerClickListener(new PriceAdaptor.RecyclerClickListener() {
                            @Override
                            public void onClick(final int position) {
                                dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.dialog_addprice);

                                ((TextView) dialog.findViewById(R.id.cate)).setText(categories.get(finalI).getName());
                                ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
                                ((TextView) dialog.findViewById(R.id.price)).setTypeface(MyApplication.type_jf_regular);
                                ((TextView) dialog.findViewById(R.id.priceval)).setText(categories.get(finalI).getPrice().get(position).getMinPrice());
                                ((TextView) dialog.findViewById(R.id.pricevaltp)).setText(categories.get(finalI).getPrice().get(position).getMaxPrice());

                                ((TextView) dialog.findViewById(R.id.priceto)).setTypeface(MyApplication.type_jf_regular);
                                ((TextView) dialog.findViewById(R.id.currency)).setTypeface(MyApplication.type_jf_regular);

                                ((Button) dialog.findViewById(R.id.cancel)).setTypeface(MyApplication.type_jf_regular);
                                ((Button) dialog.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_regular);
                                Glide.with(MyApplication.getAppContext())
                                        .load(UrlStatic.pathImage + categories.get(finalI).getPrice().get(position).getImageUrl())
                                        .centerCrop()
                                        .override(150,150)
                                        .into((ImageView) dialog.findViewById(R.id.image));

                                dialog.findViewById(R.id.imageseded).setVisibility(View.VISIBLE);
                                dialog.findViewById(R.id.pike).setVisibility(View.GONE);


                                ((EditText) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                                ((EditText) dialog.findViewById(R.id.name)).setText(categories.get(finalI).getPrice().get(position).getName());
                                ((EditText) dialog.findViewById(R.id.priceval)).setTypeface(MyApplication.type_jf_regular);
                                ((EditText) dialog.findViewById(R.id.pricevaltp)).setTypeface(MyApplication.type_jf_regular);
                                ((EditText) dialog.findViewById(R.id.description)).setTypeface(MyApplication.type_jf_regular);
                                ((EditText) dialog.findViewById(R.id.description)).setText(categories.get(finalI).getPrice().get(position).getDescription());
                                dialog.findViewById(R.id.pike).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                        photoPickerIntent.setType("image/*");
                                        startActivityForResult(photoPickerIntent, 125);
                                    }
                                });
                                final ExpandableRelativeLayout expandableLayout2 = (ExpandableRelativeLayout) dialog.findViewById(R.id.expandableLayout1);
                                RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycle);
                                listeAdaptor2 adapter = new listeAdaptor2(categories);
                                adapter.setMyAdapterListener(new listeAdaptor2.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int item) {
                                        expandableLayout2.toggle();
                                        id_cat = Integer.parseInt(categories.get(item).getId());
                                        ((TextView) dialog.findViewById(R.id.cate)).setText(categories.get(item).getName());
                                    }
                                });
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setAdapter(adapter);
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
                                dialog.findViewById(R.id.type).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        expandableLayout2.toggle();

                                    }
                                });
                                ((Button) dialog.findViewById(R.id.add)).setText(getResources().getString(R.string.update));

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

                                Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner);
                                final ArrayList<String> s = new ArrayList<String>();
                                for (int i = 0; i < currencyList.size(); i++) {
                                    if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
                                        s.add(currencyList.get(i).getNameEn());
                                    else
                                        s.add(currencyList.get(i).getNameAr());
                                }
                                ArrayAdapter<String> adapterSpiner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, s);
                                dropdown.setAdapter(adapterSpiner);
                                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                        Object item = parent.getItemAtPosition(pos);
                                        devise = s.get(pos) + "";
                                    }

                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.findViewById(R.id.update).setVisibility(View.GONE);
                                        dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);

                                        TextView text = (TextView) dialog.findViewById(R.id.name);
                                        TextView description = (TextView) dialog.findViewById(R.id.description);

                                        new Service().UpdatePrice(text.getText().toString(), description.getText().toString(), id_cat + "", ((EditText) dialog.findViewById(R.id.priceval)).getText().toString(), ((EditText) dialog.findViewById(R.id.pricevaltp)).getText().toString(), devise, selectedImage, Integer.parseInt(categories.get(finalI).getPrice().get(position).getId()), new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {

                                                if (result != null) {
                                                    dialog.dismiss();
                                                    api();
                                                } else
                                                    showError();

                                            }

                                            @Override
                                            public void onFailure(Object result) {
                                                showError();
                                            }


                                            @Override
                                            public void onFinish() {

                                            }

                                            @Override
                                            public void OnError(String message) {
                                                showError();
                                            }
                                        });

                                    }
                                });
                                dialog.show();
                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                            }
                        });
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {


        }
    }

    public void showError() {
        Alerter.create(getActivity())
                .setTitle("Can't load page No internet connection.")
                .setText("Make sure Wi-Fi or cellular data is turned on, then try again.")
                .setIcon(R.drawable.refresh_wifi_signal)
                .setDuration(2000)
                .setBackgroundColorRes(R.color.color2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Alerter.hide();
                    }

                })
                .show();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
