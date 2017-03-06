package com.tangxiaolv.lib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.VPromise;

public class RemoteActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = getIntent().getStringExtra("tag");
                final VPromise promise = AndroidRouter.findPromiseByTag(tag);
                assert promise != null;
                //async
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        promise.resolve("","I'm from remote");
                    }
                }).start();
                finish();
            }
        });
    }
}
