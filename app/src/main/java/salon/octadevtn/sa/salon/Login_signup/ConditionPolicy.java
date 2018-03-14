package salon.octadevtn.sa.salon.Login_signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;

public class ConditionPolicy extends AppCompatActivity {
    LinearLayout back;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_policy);
        //ButterKnife.bind(this);

        webView= (WebView) findViewById(R.id.webview1);
        back=findViewById(R.id.bac);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("http://salon.com.sa/Terms.htm");
        }
        if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("http://salon.com.sa/TermsAr.htm");
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        ConditionPolicy.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();

    }


}
