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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor.CategorieAdaptor;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Models.Category;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Models.CategorySalon;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Liste_Categorie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Liste_Categorie extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Bitmap selectedImage;
    CategorieAdaptor adapter;
    LinearLayout tryagain;
    LinearLayout progress;
    View v;
    ImageView imagetry;
    Dialog dialog;
    ArrayList<Category> activityList;
    boolean imagesele = false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Liste_Categorie() {
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
    public static Liste_Categorie newInstance(String param1, String param2) {
        Liste_Categorie fragment = new Liste_Categorie();
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
        v = inflater.inflate(R.layout.fragment_liste__categorie, container, false);
        ((TextView) v.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) v.findViewById(R.id.add)).setTypeface(MyApplication.type_jf_medium);
        tryagain = (LinearLayout) v.findViewById(R.id.tryagain);
        progress = (LinearLayout) v.findViewById(R.id.progress);
        imagetry = (ImageView) v.findViewById(R.id.imagetry);
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(2000);
        rotate.setRepeatCount(Animation.INFINITE);
        imagetry.setAnimation(rotate);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        activityList = new ArrayList<>();


        v.findViewById(R.id.addcat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_ad_category);
                ((TextView) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
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

                        TextView text = (TextView) dialog.findViewById(R.id.name);
                        if ((!text.getText().toString().equals("") && selectedImage != null)) {
                            dialog.setCancelable(false);
                            new Service().AddCategory2(text.getText().toString(), selectedImage, new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    if (result != null) {
                                        dialog.dismiss();
                                        dialog.setCancelable(true);
                                        api();
                                    } else {
                                        dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                        dialog.findViewById(R.id.progress).setVisibility(View.GONE);
                                    }

                                }

                                @Override
                                public void onFailure(Object result) {
                                    dialog.setCancelable(true);
                                    dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                    dialog.findViewById(R.id.progress).setVisibility(View.GONE);


                                }

                                @Override
                                public void onFinish() {
                                    dialog.setCancelable(true);


                                }

                                @Override
                                public void OnError(String message) {
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
        adapter = new CategorieAdaptor(activityList);
        adapter.setRecyclerClickListener(new CategorieAdaptor.RecyclerClickListener() {
            @Override
            public void onClick(final int position) {
                selectedImage = null;
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_ad_category);
                ((TextView) dialog.findViewById(R.id.name)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.name)).setText(activityList.get(position).getName());
                dialog.findViewById(R.id.imageseded).setVisibility(View.VISIBLE);
                dialog.findViewById(R.id.pike).setVisibility(View.GONE);
                Glide
                        .with(MyApplication.getAppContext())
                        .load(UrlStatic.pathImag + activityList.get(position).getImageUrl())
                        .override(200, 200)
                        .centerCrop()
                        .into((ImageView) dialog.findViewById(R.id.image));
                ((TextView) dialog.findViewById(R.id.pike)).setTypeface(MyApplication.type_jf_regular);
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

                        TextView text = (TextView) dialog.findViewById(R.id.name);
                        if ((!text.getText().toString().equals(""))) {
                            dialog.setCancelable(false);
                            new Service().UpdateCategory(text.getText().toString(), selectedImage, Integer.parseInt(activityList.get(position).getId()), new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    if (result != null) {
                                        dialog.dismiss();
                                        dialog.setCancelable(true);
                                        api();
                                    } else {
                                        dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                        dialog.findViewById(R.id.progress).setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Object result) {
                                    dialog.setCancelable(true);
                                    dialog.findViewById(R.id.update).setVisibility(View.VISIBLE);
                                    dialog.findViewById(R.id.progress).setVisibility(View.GONE);
                                }

                                @Override
                                public void onFinish() {
                                    dialog.setCancelable(true);
                                }

                                @Override
                                public void OnError(String message) {
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

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api();
            }
        });
        api();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void api() {
        activityList.clear();
        adapter.notifyDataSetChanged();
        progress.setVisibility(View.VISIBLE);
        tryagain.setVisibility(View.GONE);
        new Service().getService(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    progress.setVisibility(View.GONE);
                    tryagain.setVisibility(View.GONE);
                    CategorySalon salon_cat = (CategorySalon) result;
                    activityList.addAll(salon_cat.getCategory());
                    adapter.notifyDataSetChanged();
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
            if (v != null) api();
        }

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
