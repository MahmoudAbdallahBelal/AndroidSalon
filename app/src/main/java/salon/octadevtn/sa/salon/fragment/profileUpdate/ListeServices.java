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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.tapadoo.alerter.Alerter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor.ServiceAdaptor;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor.listeAdaptor;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Models.Category;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Models.CategorySalon;

import static android.app.Activity.RESULT_OK;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;


public class ListeServices extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Dialog dialog;
    int id_cat = -1;
    Bitmap selectedImage;
    ArrayList<Category> categories;
    RecyclerView addHeaderRecyclerView;
    LinearLayout tryagain;
    LinearLayout progress;
    View v;
    ServiceAdaptor firstSection;
    CategorySalon salon_cat;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public ListeServices() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ListeServices newInstance(String param1, String param2) {
        ListeServices fragment = new ListeServices();
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
        v = inflater.inflate(R.layout.fragment_liste_services, container, false);
        tryagain = (LinearLayout) v.findViewById(R.id.tryagain);
        progress = (LinearLayout) v.findViewById(R.id.progress);
        ((TextView) v.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) v.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_medium);
        ImageView imagetry = (ImageView) v.findViewById(R.id.imagetry);
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(2000);
        rotate.setRepeatCount(Animation.INFINITE);
        imagetry.setAnimation(rotate);
        v.findViewById(R.id.addcat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categories.size() > 0) {
                    id_cat = -1;
                    dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_addservice);
                    ((TextView) dialog.findViewById(R.id.cate)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
                    ((EditText) dialog.findViewById(R.id.description)).setTypeface(MyApplication.type_jf_regular);
                    ((Button) dialog.findViewById(R.id.cancel)).setTypeface(MyApplication.type_jf_regular);
                    ((Button) dialog.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_regular);

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
                    listeAdaptor adapter = new listeAdaptor(categories);
                    adapter.setMyAdapterListener(new listeAdaptor.OnItemClickListener() {
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
                    dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.findViewById(R.id.update).setVisibility(View.GONE);
                            dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                            dialog.setCancelable(false);
                            TextView text = (TextView) dialog.findViewById(R.id.name);
                            TextView description = (TextView) dialog.findViewById(R.id.description);
                            if ((!text.getText().toString().equals("") && selectedImage != null && !description.getText().toString().equals("")
                                    && id_cat > 0)) {
                                new Service().AddService(text.getText().toString(), description.getText().toString(), id_cat, selectedImage, new UniversalCallBack() {
                                    @Override
                                    public void onResponse(Object result) {
                                        if (result != null) {
                                            dialog.dismiss();

                                            api();
                                        } else {
                                            showError();
                                            dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                            dialog.findViewById(R.id.progress).setVisibility(View.GONE);
                                            dialog.setCancelable(true);

                                        }
                                    }

                                    @Override
                                    public void onFailure(Object result) {
                                        showError();
                                        dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                        dialog.setCancelable(true);

                                        dialog.findViewById(R.id.progress).setVisibility(View.GONE);
                                    }


                                    @Override
                                    public void onFinish() {

                                    }

                                    @Override
                                    public void OnError(String message) {
                                        showError();
                                        dialog.setCancelable(true);

                                        dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                        dialog.findViewById(R.id.progress).setVisibility(View.GONE);

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

                  /*  Alerter.create(getActivity())
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

        addHeaderRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        addHeaderRecyclerView.setLayoutManager(linearLayoutManager);
        addHeaderRecyclerView.setHasFixedSize(true);
        categories = new ArrayList<>();
        api();
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api();
            }
        });


        return v;
    }

    private void api() {
        categories.clear();
        progress.setVisibility(View.VISIBLE);
        tryagain.setVisibility(View.GONE);
        new Service().getService(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    salon_cat = (CategorySalon) result;
                    categories.clear();
                    categories.addAll(salon_cat.getCategory());
                    progress.setVisibility(View.GONE);
                    tryagain.setVisibility(View.GONE);
                    SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).getService().size() > 0) {
                            firstSection = new ServiceAdaptor(categories.get(i).getName(), categories.get(i).getService(), sectionAdapter);
                            sectionAdapter.addSection(firstSection);
                            final int finalI = i;
                            firstSection.setRecyclerClickListener(new ServiceAdaptor.RecyclerClickListener() {
                                @Override
                                public void onClick(final int position) {
                                    id_cat = -1;
                                    dialog = new Dialog(getActivity());
                                    dialog.setContentView(R.layout.dialog_addservice);
                                    ((TextView) dialog.findViewById(R.id.cate)).setTypeface(MyApplication.type_jf_regular);
                                    ((TextView) dialog.findViewById(R.id.cate)).setText(categories.get(position).getName());
                                    ((TextView) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                                    ((TextView) dialog.findViewById(R.id.name)).setText(categories.get(finalI).getService().get(position).getName());
                                    ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
                                    ((EditText) dialog.findViewById(R.id.description)).setTypeface(MyApplication.type_jf_regular);
                                    ((TextView) dialog.findViewById(R.id.description)).setText(categories.get(finalI).getService().get(position).getDescription());
                                    dialog.findViewById(R.id.imageseded).setVisibility(View.VISIBLE);
                                    dialog.findViewById(R.id.pike).setVisibility(View.GONE);

                                    ((ImageView) dialog.findViewById(R.id.image)).setImageBitmap(selectedImage);
                                    Glide
                                            .with(MyApplication.getAppContext())
                                            .load(UrlStatic.pathImag + categories.get(finalI).getService().get(position).getImageUrl())
                                            .override(200, 200)
                                            .centerCrop()
                                            .into((ImageView) dialog.findViewById(R.id.image));
                                    ((Button) dialog.findViewById(R.id.cancel)).setTypeface(MyApplication.type_jf_regular);
                                    ((Button) dialog.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_regular);
                                    ((Button) dialog.findViewById(R.id.add)).setText(getResources().getString(R.string.update));
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
                                    listeAdaptor adapter = new listeAdaptor(categories);
                                    adapter.setMyAdapterListener(new listeAdaptor.OnItemClickListener() {
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
                                    dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.findViewById(R.id.update).setVisibility(View.GONE);
                                            dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                                            dialog.setCancelable(false);
                                            TextView text = (TextView) dialog.findViewById(R.id.name);
                                            TextView description = (TextView) dialog.findViewById(R.id.description);

                                            if ((!text.getText().toString().equals(""))) {
                                                new Service().UpdateService(text.getText().toString(), description.getText().toString(), id_cat, selectedImage, categories.get(finalI).getService().get(position).getId(), new UniversalCallBack() {
                                                    @Override
                                                    public void onResponse(Object result) {

                                                        if (result != null) {
                                                            dialog.dismiss();
                                                            api();
                                                        } else {
                                                            showError();
                                                            dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                                            dialog.findViewById(R.id.progress).setVisibility(View.GONE);
                                                            dialog.setCancelable(true);

                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Object result) {
                                                        showError();
                                                        dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                                        dialog.setCancelable(true);

                                                        dialog.findViewById(R.id.progress).setVisibility(View.GONE);
                                                    }


                                                    @Override
                                                    public void onFinish() {

                                                    }

                                                    @Override
                                                    public void OnError(String message) {
                                                        showError();
                                                        dialog.setCancelable(true);

                                                        dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                                        dialog.findViewById(R.id.progress).setVisibility(View.GONE);

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
                        }

                    }

                    addHeaderRecyclerView.setAdapter(sectionAdapter);
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
            api();


        }
    }

    public void showError() {
        Alerter.create(getActivity())
                .setTitle("Can't load page No internet connection.")
                .setText("Make sure Wi-Fi or cellular data is turned on, then try again.")
                .setIcon(R.drawable.refresh_wifi_signal)
                .setDuration(1000)
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
