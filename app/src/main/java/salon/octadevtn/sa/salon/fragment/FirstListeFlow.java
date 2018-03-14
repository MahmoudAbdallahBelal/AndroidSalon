package salon.octadevtn.sa.salon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.Adaptor.AdaptorFirstFlow;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Follow;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;


public class FirstListeFlow extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String type;
    private String FoF;
    private int id;

    public FirstListeFlow() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_liste_flow);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Inflate the layout for this fragment
        ((TextView) findViewById(R.id.back)).setText(getResources().getString(R.string.skip));
        ((TextView) findViewById(R.id.back)).setTypeface(MyApplication.type_jf_medium);
        findViewById(R.id.back1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(FirstListeFlow.this, HomeActivityDrawer.class));
                        finish();
                    }
                });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final ArrayList<Follow> activityList = new ArrayList<>();


        final AdaptorFirstFlow adapter = new AdaptorFirstFlow(activityList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        ((TextView) findViewById(R.id.title)).setText(getResources().getString(R.string.start_folow));
        ((TextView) findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
        new salon.octadevtn.sa.salon.Api.Follow().FirstFolow(new UniversalCallBack() {

            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    Follow[] liste = (Follow[]) result;
                    for (int i = 0; i < liste.length; i++) {
                        activityList.add(liste[i]);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Object result) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {

            }
        });
    }

}



