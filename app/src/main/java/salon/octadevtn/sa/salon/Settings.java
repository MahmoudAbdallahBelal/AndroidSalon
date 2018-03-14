package salon.octadevtn.sa.salon;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.appyvet.rangebar.RangeBar;
import com.github.chuross.library.ExpandableLayout;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Adaptor.AdaptorBlockListe;
import salon.octadevtn.sa.salon.Api.ForgetPassword;
import salon.octadevtn.sa.salon.Models.Block;
import salon.octadevtn.sa.salon.Models.InfoUser;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    InfoUser infoUser = null;
    View view;
    ProgressBar progress;
    TextView notice;
    GSONSharedPreferences gsonSharedPrefs1;
    @BindView(R.id.update_password)
    LinearLayout update_password;
    @BindView(R.id.language)
    LinearLayout language;
    @BindView(R.id.maps_search)
    LinearLayout maps_search;
    @BindView(R.id.sound_notif)
    LinearLayout sound_notif;
    @BindView(R.id.Currency)
    LinearLayout Currency;
    @BindView(R.id.Hidden_Profile)
    LinearLayout Hidden_Profile;
    @BindView(R.id.Block_List)
    LinearLayout Block_List;
    @BindView(R.id.Delete_Account)
    LinearLayout Delete_Account;
    @BindView(R.id.notification)
    ToggleButton notification;
    @BindView(R.id.lang)
    TextView lang;
    @BindView(R.id.Currency1)
    TextView Currency1;
    @BindView(R.id.zone_map)
    TextView zone_map;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.t5)
    TextView t5;
    @BindView(R.id.t6)
    TextView t6;
    @BindView(R.id.layout_expandable)
    ExpandableLayout expandableLayout;
    @BindView(R.id.t7)
    TextView t7;
    @BindView(R.id.rangebar)
    RangeBar rangeBar;
    @BindView(R.id.t8)
    TextView t8;
    @BindView(R.id.hiden)
    TextView hiden;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.back)
    TextView back;
    RecyclerView recyclerView;
    AdaptorBlockListe adaptorBlockListe;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        t1.setTypeface(MyApplication.type_jf_medium);
        t2.setTypeface(MyApplication.type_jf_medium);
        t3.setTypeface(MyApplication.type_jf_medium);
        t4.setTypeface(MyApplication.type_jf_medium);
        t5.setTypeface(MyApplication.type_jf_medium);
        t6.setTypeface(MyApplication.type_jf_medium);
        t7.setTypeface(MyApplication.type_jf_medium);
        t8.setTypeface(MyApplication.type_jf_medium);
        lang.setTypeface(MyApplication.type_jf_medium);
        Currency1.setTypeface(MyApplication.type_jf_medium);
        zone_map.setTypeface(MyApplication.type_jf_medium);
        back.setTypeface(MyApplication.type_jf_regular);
        setting.setTypeface(MyApplication.type_jf_medium);
        hiden.setTypeface(MyApplication.type_jf_medium);

        view.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        notification.setChecked(MyApplication.getInstance().getPrefManager().isSound_notification());
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyApplication.getInstance().getPrefManager().setSound_notification(isChecked);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().getPrefManager().setSound_notification(notification.isChecked());

            }
        });
        if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
            lang.setText(getResources().getString(R.string.English));
        } else
            lang.setText(getResources().getString(R.string.Arabic));
        if (MyApplication.getInstance().getPrefManager().getCurrency() != null)
            switch (Integer.parseInt(MyApplication.getInstance().getPrefManager().getCurrency())) {
                case 0: {
                    Currency1.setText(getResources().getString(R.string.Dollar));
                    break;
                }
                case 1: {
                    Currency1.setText(getResources().getString(R.string.Euro));
                    break;
                }
            }
        zone_map.setText(MyApplication.getInstance().getPrefManager().getmaps_searsh() + " " + getResources().getString(R.string.zone_map) + " ");

        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.update_password);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                final EditText new0 = (EditText) dialog.findViewById(R.id.new0);
                final EditText new1 = (EditText) dialog.findViewById(R.id.new1);
                final EditText old = (EditText) dialog.findViewById(R.id.old);
                new0.setTypeface(MyApplication.type_jf_regular);
                new1.setTypeface(MyApplication.type_jf_regular);
                old.setTypeface(MyApplication.type_jf_regular);
                ((Button) dialog.findViewById(R.id.cancel)).setTypeface(MyApplication.type_jf_medium);
                ((Button) dialog.findViewById(R.id.send)).setTypeface(MyApplication.type_jf_medium);
                dialog.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new0.getText().toString().length() < 6)
                            Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_password)
                                    , Toast.LENGTH_SHORT).show();
                        else if (new0.getText().toString().equals(new1.getText().toString()))
                            UpdatePassword(old.getText().toString(), new0.getText().toString(), dialog);
                        else
                            Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_password1)
                                    , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.language);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                dialog.findViewById(R.id.ar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lang.setText(getResources().getString(R.string.Arabic));
                        MyApplication.getInstance().getPrefManager().setLang(1);
                        dialog.dismiss();
                        recreateActivity();

                    }
                });
                dialog.findViewById(R.id.en).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lang.setText(getResources().getString(R.string.English));
                        MyApplication.getInstance().getPrefManager().setLang(0);
                        dialog.dismiss();
                        recreateActivity();

                    }
                });

            }
        });
        maps_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout.expand();

                rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                                      int rightPinIndex,
                                                      String leftPinValue, String rightPinValue) {
                        MyApplication.getInstance().getPrefManager().setmaps_searsh(rightPinValue);
                        zone_map.setText(rightPinValue + " " + getResources().getString(R.string.zone_map) + " ");
                        //     Toast.makeText(getActivity(), rightPinValue+"", Toast.LENGTH_SHORT).show();

                    }
                });
                rangeBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                break;

                            case MotionEvent.ACTION_MOVE:
                                // touch move code
                                break;

                            case MotionEvent.ACTION_UP:
                                expandableLayout.collapse();
                                Toasty.success(getActivity(), getActivity().getResources().getString(R.string.succesmap), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });

             /*   final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.maps_search);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                ((EditText) dialog.findViewById(R.id.km)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        MyApplication.getInstance().getPrefManager().setmaps_searsh(((EditText) dialog.findViewById(R.id.km)).getText().toString());
                        zone_map.setText(((EditText) dialog.findViewById(R.id.km)).getText().toString() + " " + getResources().getString(R.string.zone_map) + " ");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });*/
            }
        });
        Hidden_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.hidden_profile);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                ((TextView) dialog.findViewById(R.id.t1)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.t2)).setTypeface(MyApplication.type_jf_regular);
                ((ToggleButton) dialog.findViewById(R.id.Male)).setChecked(MyApplication.getInstance().getPrefManager().getHidden_male());
                ((ToggleButton) dialog.findViewById(R.id.Female)).setChecked(MyApplication.getInstance().getPrefManager().getHidden_Female());
                ((ToggleButton) dialog.findViewById(R.id.Male)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().getPrefManager().setHidden_male(((ToggleButton) dialog.findViewById(R.id.Male)).isChecked());
                    }
                });

                ((ToggleButton) dialog.findViewById(R.id.Female)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().getPrefManager().setHidden_Female(((ToggleButton) dialog.findViewById(R.id.Female)).isChecked());
                    }
                });


            }
        });
        Block_List.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
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
                progress = (ProgressBar) dialog.findViewById(R.id.progress);
                notice = (TextView) dialog.findViewById(R.id.block);

                ListeBlock();

            }
        });
        Delete_Account.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.delete_account);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                ((Button) dialog.findViewById(R.id.delete_no)).setTypeface(MyApplication.type_jf_regular);
                ((Button) dialog.findViewById(R.id.delete_ok)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.delete)).setTypeface(MyApplication.type_jf_regular);
                dialog.findViewById(R.id.delete_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.delete_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        new ForgetPassword().deleteAcount(new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) throws JSONException {
                                if (result == null) {
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.haveproblem))
                                            .setContentText(getResources().getString(R.string.tryagain))
                                            .setConfirmText(getResources().getString(R.string.ok))
                                            .show();
                                } else {
                                    MyApplication.getInstance().logout();
                                    getActivity().finish();

                                }
                            }

                            @Override
                            public void onFailure(Object result) {

                            }

                            @Override
                            public void onFinish() {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.haveproblem))
                                        .setContentText(getResources().getString(R.string.tryagain))
                                        .setConfirmText(getResources().getString(R.string.ok))
                                        .show();
                            }

                            @Override
                            public void OnError(String message) {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.haveproblem))
                                        .setContentText(getResources().getString(R.string.tryagain))
                                        .setConfirmText(getResources().getString(R.string.ok))
                                        .show();
                            }
                        });

                    }
                });

            }
        });
        Currency.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.currency);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                ((TextView) dialog.findViewById(R.id.dolard)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.euro)).setTypeface(MyApplication.type_jf_regular);
                dialog.findViewById(R.id.dolard).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().getPrefManager().setCurrency(0);
                        Currency1.setText(getResources().getString(R.string.Dollar));
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.euro).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Currency1.setText(getResources().getString(R.string.Euro));
                        MyApplication.getInstance().getPrefManager().setCurrency(1);
                        dialog.dismiss();
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

    public void ListeBlock() {

        new salon.octadevtn.sa.salon.Api.ListeBlock().ListeBlock(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    Block block = (Block) result;
                    progress.setVisibility(View.GONE);

                    adaptorBlockListe = new AdaptorBlockListe(block.getListblock(), getActivity());
                    recyclerView.setAdapter(adaptorBlockListe);
                    adaptorBlockListe.notifyDataSetChanged();
                    if (block.getListblock().size() == 0) {
                        notice.setVisibility(View.VISIBLE);
                        notice.setTypeface(MyApplication.type_jf_regular);

                    }
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

    public void recreateActivity() {
        Intent intent = new Intent(getActivity(), SplachScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    private void UpdatePassword(String old, String new0, final Dialog dialog) {
        new salon.octadevtn.sa.salon.Api.Update().UpdatePassword(old, new0, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    Toasty.success(getActivity(), getActivity().getResources().getString(R.string.passup), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Object result) {

            }

            @Override
            public void OnError(String message) {
            }

            @Override
            public void onFinish() {
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
