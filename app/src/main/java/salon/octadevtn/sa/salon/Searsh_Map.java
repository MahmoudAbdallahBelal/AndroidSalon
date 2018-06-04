package salon.octadevtn.sa.salon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lypeer.fcpermission.FcPermissionsB;
import com.lypeer.fcpermission.impl.OnPermissionsDeniedListener;
import com.lypeer.fcpermission.impl.OnPermissionsGrantedListener;

import java.util.ArrayList;
import java.util.List;

import salon.octadevtn.sa.salon.Models.Salon;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.fragment.SalonProfile;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Searsh_Map#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searsh_Map extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    double latitude, longitude;
    GoogleApiClient mGoogleApiClient;
    boolean verif = true;
    int pos;
    int cout = 0;
    View view;
    boolean m_iAmVisible=true;
    private GoogleMap mMap;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Searsh_Map() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searsh_MAp.
     */
    // TODO: Rename and change types and number of parameters
    public static Searsh_Map newInstance(String param1, String param2) {
        Searsh_Map fragment = new Searsh_Map();
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
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_searsh__map, container, false);
        } catch (Exception e) {

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!AppUtils.isLocationEnabled(getActivity())) {
            // notify user
            android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
            dialog.setMessage(getResources().getString(R.string.nolocation));
            dialog.setPositiveButton(getResources().getString(R.string.opensetting), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            //  dialog.show();
        } else {
            if (mMap != null) {
                try {
                    LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
                    mMap.animateCamera(cameraUpdate);
                } catch (Exception e) {

                }
            }

        }

        buildGoogleApiClient();

        /*
        FcPermissionsB mFcPermissionsB = new FcPermissionsB.Builder(getActivity())
                .onGrantedListener(new OnPermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(int requestCode, List<String> perms) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mMap.setMyLocationEnabled(true);

                    }
                })
                .onDeniedListener(new OnPermissionsDeniedListener() {
                    @Override
                    public void onPermissionsDenied(int requestCode, List<String> perms) {

                    }
                })
                .positiveBtn4ReqPer(android.R.string.ok)
                .negativeBtn4ReqPer(R.string.cancel)
                .positiveBtn4NeverAskAgain(R.string.setting)
                .negativeBtn4NeverAskAgain(R.string.cancel)
                .rationale4ReqPer(getString(R.string.prompt_request_camara))//必需
                .rationale4NeverAskAgain(getString(R.string.prompt_we_need_camera))//必需
                .requestCode(200)//必需
                .build();
                */
       // mFcPermissionsB.requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION);//request permissions


//        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
//                if (latitude != 0.0) {
//                    if (verif) {
//                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude,
//                                longitude)).zoom(12).build();
//                        verif = false;
//                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                    }
//                }
//            }
//        };
        for (int i = 0; i < Searsh_request.searsh.getSalons().size(); i++) {
            Salon salon = Searsh_request.searsh.getSalons().get(i);
          //  if (calculedistance(salon.getLatitude(), salon.getLongutide())) {
                try {
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(salon.getLatitude(), salon.getLongutide()))
                            .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.marker)))
                            .snippet(i + "")
                    );
                    cout++;
                } catch (Exception e) {
                }
          //  }

        }
        if (Searsh_request.searsh.getSalons().size() > 0 && cout == 0) {
            if (m_iAmVisible) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.mapinfo);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                ((Button) dialog.findViewById(R.id.ok)).setTypeface(MyApplication.type_jf_regular);
                ((TextView) dialog.findViewById(R.id.info)).setTypeface(MyApplication.type_jf_regular);
                dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                pos = Integer.parseInt(marker.getSnippet());
                return false;
            }
        });

        //mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       // mMap.setMyLocationEnabled(true);


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ((HomeActivityDrawer) getActivity()).setFragment(SalonProfile.newInstance(Integer.parseInt(String.valueOf(Searsh_request.searsh.getSalons().get(pos).getId()))));
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {



            @SuppressLint("RestrictedApi")
            ContextThemeWrapper cw = new ContextThemeWrapper(
                    MyApplication.getAppContext(), R.style.Transparent);
            // AlertDialog.Builder b = new AlertDialog.Builder(cw);
            @SuppressLint("RestrictedApi")
            LayoutInflater inflater = (LayoutInflater) cw
                    .getSystemService(LAYOUT_INFLATER_SERVICE);

            private final View view = inflater.inflate(R.layout.fragment_form, null);


            @Override

            public View getInfoWindow(Marker marker) {
                String work = "";
                Salon salon = Searsh_request.searsh.getSalons().get(pos);
                String first = salon.getWork().get(0).getName();
                String houropen = salon.getWork().get(0).getStartHour();
                String hourend = salon.getWork().get(0).getEndHours();
                boolean status = salon.getWork().get(0).getStatus();
                for (int i = 1; i < salon.getWork().size(); i++) {
                    if (salon.getWork().get(i).getStatus() != status) {
                        if (i == salon.getWork().size() - 1) {
                            if (status) {
                                if (salon.getWork().get(i).getName().equals(first)) {
                                    work += first + " " + houropen + " - " + hourend;

                                } else {
                                    work += first + " - " + salon.getWork().get(i).getName() + " " + houropen + " - " + hourend;
                                }
                                status = salon.getWork().get(i).getStatus();
                                first = salon.getWork().get(i).getName();

                            } else {
                                if (salon.getWork().get(i).getName().equals(first)) {

                                    work += first + " IS CLOSED\n";

                                } else
                                    work += first + " - " + salon.getWork().get(i).getName() + " IS CLOSED\n";
                                status = salon.getWork().get(i).getStatus();
                                first = salon.getWork().get(i).getName();
                            }
                        }
                        if (status) {
                            if (salon.getWork().get(i - 1).getName().equals(first)) {
                                work += first + " " + houropen + " - " + hourend + "\n";
                            } else
                                work += first + " - " + salon.getWork().get(i - 1).getName() + " " + houropen + " - " + hourend + "\n";

                            status = salon.getWork().get(i).getStatus();
                            first = salon.getWork().get(i).getName();
                        } else {

                            if (salon.getWork().get(i - 1).getName().equals(first)) {
                                work += first + " IS CLOSED" + "\n";
                            } else
                                work += first + " - " + salon.getWork().get(i - 1).getName() + " IS CLOSED" + "\n";
                            status = salon.getWork().get(i).getStatus();
                            first = salon.getWork().get(i).getName();
                        }


                    }
                }

                ((TextView) view.findViewById(R.id.text_from)).setText(work);
                ((TextView) view.findViewById(R.id.titre)).setText(salon.getSalonName());
                ((TextView) view.findViewById(R.id.titre)).setTypeface(MyApplication.type_jf_medium);
                ((TextView) view.findViewById(R.id.text_from)).setTypeface(MyApplication.type_jf_regular);
//chiheb
                String work1 = "";
                String first1 = salon.getWork().get(0).getName();
                String houropen1 = salon.getWork().get(0).getStartHour();
                String hourend1 = salon.getWork().get(0).getEndHours();
                boolean status1 = salon.getWork().get(0).getStatus();
                for (int i = 1; i < salon.getWork().size(); i++) {
                    if (salon.getWork().get(i).getStatus() != status) {
                        if (i == salon.getWork().size() - 1) {
                            if (status) {
                                if (salon.getWork().get(i).getName().equals(first)) {
                                    work1 += first1 + " " + houropen1 + " - " + hourend1;

                                } else {
                                    work1 += first1 + " - " + salon.getWork().get(i).getName() + " " + houropen1 + " - " + hourend1;
                                }
                                status1 = salon.getWork().get(i).getStatus();
                                first1 = salon.getWork().get(i).getName();

                            } else {
                                if (salon.getWork().get(i).getName().equals(first)) {

                                    work1 += first1 + " IS CLOSED\n";

                                } else
                                    work1 += first1 + " - " + salon.getWork().get(i).getName() + " IS CLOSED\n";
                                status1 = salon.getWork().get(i).getStatus();
                                first1 = salon.getWork().get(i).getName();
                            }
                        }
                        if (status1) {
                            if (salon.getWork().get(i - 1).getName().equals(first)) {
                                work1 += first1 + " " + houropen1 + " - " + hourend1 + "\n";
                            } else
                                work1 += first1 + " - " + salon.getWork().get(i - 1).getName() + " " + houropen1 + " - " + hourend1 + "\n";

                            status1 = salon.getWork().get(i).getStatus();
                            first1 = salon.getWork().get(i).getName();
                        } else {

                            if (salon.getWork().get(i - 1).getName().equals(first)) {
                                work1 += first1 + " IS CLOSED" + "\n";
                            } else
                                work1 += first1 + " - " + salon.getWork().get(i - 1).getName() + " IS CLOSED" + "\n";
                            status1 = salon.getWork().get(i).getStatus();
                            first1 = salon.getWork().get(i).getName();
                        }


                    }

                    ((TextView) view.findViewById(R.id.text_from)).setText(work);
                }
                return view;


            }


            @Override

            public View getInfoContents(Marker marker) {


                return null;

            }

        });


    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean calculedistance(double lat, double lon) {
        ArrayList<Float> array = new ArrayList<>();
        Location loc2 = new Location("");
        loc2.setLatitude(latitude);
        loc2.setLongitude(longitude);
        Location loc1 = new Location("");
        loc1.setLatitude(lat);
        loc1.setLongitude(lon);
        if (loc1 != null && loc2 != null) {
            float distanceInMeters = loc1.distanceTo(loc2) / 1000;
            return distanceInMeters > (Double.parseDouble(MyApplication.getInstance().getPrefManager().getmaps_searsh()) * 1000);
        }
        return false;
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        m_iAmVisible = isVisibleToUser;


    }

}
