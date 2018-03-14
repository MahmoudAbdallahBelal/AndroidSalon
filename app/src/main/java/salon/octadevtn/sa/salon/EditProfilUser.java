package salon.octadevtn.sa.salon;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Adaptor.AdapterWork;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCity;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCountry;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCoverUser;
import salon.octadevtn.sa.salon.Api.ListeCountry;
import salon.octadevtn.sa.salon.Api.Sign_up;
import salon.octadevtn.sa.salon.Api.VerifUserName;
import salon.octadevtn.sa.salon.Models.City;
import salon.octadevtn.sa.salon.Models.Country;
import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.Models.CoverModel;
import salon.octadevtn.sa.salon.Models.User;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfilUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfilUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int READ_STORAGE_CODE = 1001;
    private static final int WRITE_STORAGE_CODE = 1002;
    int delcover = 0;
    String name0, gendre0, count0, cit0, email0, phone0, bio0;
    Bitmap photo0, cover0;
    @BindView(R.id.recycler_image)
    RecyclerView recycler_image;
    @BindView(R.id.t0)
    TextView t0;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.login_user)
    EditText login_user;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.country0)
    TextView country0;
    @BindView(R.id.phone_user)
    EditText phone_user;
    @BindView(R.id.email_user)
    EditText email_user;
    @BindView(R.id.bio)
    EditText bio;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.genre_user)
    TextView genre_user;
    @BindView(R.id.usernameverif)
    ImageView usernameverif;
    @BindView(R.id.done)
    ImageView done;
    @BindView(R.id.usernameprogress)
    ProgressBar usernameprogress;
    @BindView(R.id.cover)
    LinearLayout cover;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    Dialog dialog;
    RecyclerView recyclerView;
    AdaptorCountry adaptorCountry;
    AdaptorCity adaptorCity;
    boolean username_verifed = false;
    HashMap<Integer, List<City>> map = new HashMap<>();
    Bitmap selectedImage, selectedImage1;
    ArrayList<String> imagesEncodedList;
    AdaptorCoverUser adaptorCover;
    ArrayList<CoverModel> mArrayUri = new ArrayList<>();
    View view;
    User user;
    private List<Country> country1s = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public EditProfilUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment EditProfilUser.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfilUser newInstance(String param1) {
        EditProfilUser fragment = new EditProfilUser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        view = inflater.inflate(R.layout.fragment_edit_profil_user, container, false);
        ((TextView) view.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) view.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);

        ButterKnife.bind(this, view);
        Gson gson = new Gson();
        user = gson.fromJson(mParam1, User.class);
        login_user.setText(user.getName());
        genre_user.setText(user.getGenre());
        city.setText(user.getCity());
        country0.setText(user.getCountry());
        email_user.setText(user.getEmail());
        phone_user.setText(user.getPhone());
        bio.setText(user.getBio());
        view.findViewById(R.id.cov).setVisibility(View.GONE);
        if (user.getCover().equals("") || user.getCover() == null) {
            view.findViewById(R.id.cov).setVisibility(View.GONE);
        } else view.findViewById(R.id.cov).setVisibility(View.VISIBLE);
        if (user.getCover() != null) {
            view.findViewById(R.id.progress).setVisibility(View.GONE);
            recycler_image.setVisibility(View.GONE);
            Glide.with(getActivity()).load(UrlStatic.pathImag + user.getCover())
                    .thumbnail(0.5f)
                    .crossFade()
                    .override(100, 100)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) view.findViewById(R.id.image));
        }
        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.cov).setVisibility(View.GONE);
                delcover = 1;
            }
        });

        Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + user.getPhoto())
                .into(profile_image);
        if (user.getGenre().equals("men"))
            profile_image.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
        else
            profile_image.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));


        CoverModel coverModel = new CoverModel();
        coverModel.setStatus(false);
        Cover cov = new Cover();
        cov.setId(-1);
        cov.setImageUrl(user.getCover());
        coverModel.setCover(cov);
        coverModel.setBitmap(selectedImage1);
        mArrayUri.add(coverModel);

        t0.setTypeface(MyApplication.type_jf_regular);
        t1.setTypeface(MyApplication.type_jf_regular);
        phone_user.setTypeface(MyApplication.type_jf_regular);
        country0.setTypeface(MyApplication.type_jf_regular);
        login_user.setTypeface(MyApplication.type_jf_regular);
        city.setTypeface(MyApplication.type_jf_regular);
        email_user.setTypeface(MyApplication.type_jf_regular);
        bio.setTypeface(MyApplication.type_jf_regular);
        back.setTypeface(MyApplication.type_jf_regular);

        adaptorCountry = new AdaptorCountry(country1s, getActivity());
        adaptorCity = new AdaptorCity(cities, getActivity());
        country0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.block_list);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                ListeCountry(dialog);
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!country0.getText().toString().equals("")) {
                    dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.block_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
                    recyclerView.setItemViewCacheSize(999);
                    GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gl);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setDrawingCacheEnabled(true);
                    adaptorCity.notifyDataSetChanged();
                    recyclerView.setAdapter(adaptorCity);
                } else {
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.notice), Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        genre_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(genre_user);
            }
        });
        login_user.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                new VerifUserName().Costumer(login_user.getText().toString()
                        , new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                Boolean verif = (Boolean) result;
                                if (verif) {
                                    verifusername(100);
                                    username_verifed = true;
                                    user.setUsername(login_user.getText().toString());
                                } else {
                                    verifusername(-1);
                                    username_verifed = false;
                                }
                            }

                            @Override
                            public void onFailure(Object result) {
                                ResponseErrors responseError = (ResponseErrors) result;
                                String Error = "Failure";


                            }

                            @Override
                            public void OnError(String message) {
                            }

                            @Override
                            public void onFinish() {
                            }
                        });


            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                verifusername(50);

            }
        });
        adaptorCountry.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                String locale = java.util.Locale.getDefault().getDisplayName();

                if (locale.equalsIgnoreCase("ar")) {
                    country0.setText(country1s.get(position).getCountry().getNameEn());
                    count0 = country1s.get(position).getCountry().getId() + "";
                } else {
                    country0.setText(country1s.get(position).getCountry().getNameAr());
                }
                count0 = country1s.get(position).getCountry().getNameEn() + "";
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                    user.setCountry(country1s.get(position).getCountry().getNameAr());
                else {
                    user.setCountry(country1s.get(position).getCountry().getNameEn());
                }
                city.setText("");
                cit0 = null;
                cities.clear();
                cities.addAll(country1s.get(position).getCity());
                dialog.dismiss();
            }
        });
        adaptorCity.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                String locale = java.util.Locale.getDefault().getDisplayName();
                if (locale.equalsIgnoreCase("ar")) {
                    city.setText(cities.get(position).getNameEn());
                } else {
                    city.setText(cities.get(position).getNameAr());
                }
                cit0 = cities.get(position).getNameEn() + "";
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                    user.setCity(cities.get(position).getNameAr());
                else {
                    user.setCountry(cities.get(position).getNameEn());
                }
                dialog.dismiss();

            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 125);
            }
        });
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePickerIntent();
            }
        });
        recycler_image.setHasFixedSize(true);
        recycler_image.setItemViewCacheSize(999);
        GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
        recycler_image.setLayoutManager(gl);
        recycler_image.setItemAnimator(new DefaultItemAnimator());
        recycler_image.setDrawingCacheEnabled(true);
        verif();
        adaptorCover = new AdaptorCoverUser(mArrayUri, getActivity());
        recycler_image.setAdapter(adaptorCover);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);

                Gson gson = new Gson();
                String use = gson.toJson(user);

                new Sign_up().UpdateCostomer(photo0, cover0, name0, gendre0, count0, cit0, email0, phone0, bio0, use, delcover, getActivity(), new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            if (result.equals("true")) {
                                Toasty.success(getActivity(), getResources().getString(R.string.succes), Toast.LENGTH_SHORT, true).show();
                            }
                            view.findViewById(R.id.progress).setVisibility(View.GONE);
                            done.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        Toast.makeText(getActivity(), Error, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void OnError(String message) {

                    }

                    @Override
                    public void onFinish() {
                    }
                });

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void openImagePickerIntent() {

        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 120);


        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_CODE);
        }

    }

    void verif() {
        bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bio0 = bio.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                user.setBio(bio.getText().toString());

                bio0 = bio.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                email0 = email_user.getText().toString();
                user.setEmail(email_user.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email0 = email_user.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                phone0 = phone_user.getText().toString();
                user.setPhone(phone_user.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone0 = phone_user.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                name0 = login_user.getText().toString();
                user.setName(login_user.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name0 = login_user.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void verifusername(int key) {
        switch (key) {
            case -1:
                usernameprogress.setVisibility(View.GONE);
                usernameverif.setVisibility(View.VISIBLE);
                usernameverif.setImageDrawable(getResources().getDrawable(R.drawable.error_verif));
                break;
            case 50:
                usernameprogress.setVisibility(View.VISIBLE);
                usernameverif.setVisibility(View.GONE);
                break;
            case 100:
                usernameprogress.setVisibility(View.GONE);
                usernameverif.setVisibility(View.VISIBLE);
                usernameverif.setImageDrawable(getResources().getDrawable(R.drawable.success_verif));
                break;
        }
    }

    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.gendre, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.man:
                        genre_user.setText(item.getTitle());
                        gendre0 = "men";
                        return true;
                    case R.id.women:
                        genre_user.setText(item.getTitle());
                        gendre0 = "women";
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    public void ListeCountry(Dialog dialog) {
        new ListeCountry().ListeCountry(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) throws JSONException {
                if (result != null) {
                    country1s.clear();
                    Country[] country = (Country[]) result;
                    List<City> cities1 = new ArrayList<City>();
                    for (int i = 0; i < country.length; i++) {
                        country1s.add(country[i]);
                    }
                    recyclerView = (RecyclerView) EditProfilUser.this.dialog.findViewById(R.id.recycler);
                    recyclerView.setItemViewCacheSize(999);
                    GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gl);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setDrawingCacheEnabled(true);
                    adaptorCountry.notifyDataSetChanged();
                    recyclerView.setAdapter(adaptorCountry);
                }


            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";


            }

            @Override
            public void OnError(String message) {
            }

            @Override
            public void onFinish() {
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 125: {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        profile_image.setImageBitmap(selectedImage);
                        photo0 = selectedImage;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
            break;
            case 120:
                try {
                    if (resultCode == RESULT_OK) {
                        mArrayUri.clear();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        imagesEncodedList = new ArrayList<String>();
                        String imageEncoded;
                        if (imageReturnedIntent.getData() != null) {
                            Uri mImageUri = imageReturnedIntent.getData();
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(mImageUri);
                            selectedImage1 = BitmapFactory.decodeStream(imageStream);
                            cover0 = selectedImage1;
                            CoverModel coverModel = new CoverModel();
                            coverModel.setStatus(false);
                            coverModel.setBitmap(selectedImage1);
                            mArrayUri.add(coverModel);
                            adaptorCover.notifyDataSetChanged();
                            view.findViewById(R.id.cov).setVisibility(View.GONE);
                            recycler_image.setVisibility(View.VISIBLE);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                                    filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            cursor.close();


                        }
                    } else {
                        Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }


        }
    }

    private boolean isPermissionGranted(String permission) {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getActivity(), permission);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestPermission(String permission, int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, code);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == READ_STORAGE_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openImagePickerIntent();

            }
        } else if (requestCode == WRITE_STORAGE_CODE) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
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

