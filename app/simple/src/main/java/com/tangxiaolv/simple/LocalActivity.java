package com.tangxiaolv.simple;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.VPromise;

public class LocalActivity extends Activity {

    @SuppressWarnings("all")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        Button btn = (Button) findViewById(R.id.btn);
        TextView tit = (TextView) findViewById(R.id.tit);
        tit.setText("Local");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = getIntent().getStringExtra("tag");
                VPromise promise = AndroidRouter.findPromiseByTag(tag);
                assert promise != null;
                Toast.makeText(LocalActivity.this, "I'm from local", Toast.LENGTH_SHORT).show();
                LocalActivity.this.finish();
            }
        });
    }
}
