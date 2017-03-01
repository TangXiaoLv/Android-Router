
package com.tangxiaolv.simple;

import java.util.HashMap;
import java.util.Map;

import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.Reject;
import com.tangxiaolv.router.Resolve;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title);
        final TextView router1 = (TextView) findViewById(R.id.router1);
        final TextView router2 = (TextView) findViewById(R.id.router2);
        final TextView router3 = (TextView) findViewById(R.id.router3);
        final TextView router4 = (TextView) findViewById(R.id.router4);
        final TextView router5 = (TextView) findViewById(R.id.router5);
        final EditText input = (EditText) findViewById(R.id.input);
        final Button go = (Button) findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(input.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(Object object) {
                        title.setText(object.toString());
                    }
                }, new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        router1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router1.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(Object object) {
                        title.setText(object.toString());
                    }
                }, new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        router2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("activity", MainActivity.this);
                AndroidRouter.open("toon", "one", "/openOne2", params).call(new Resolve() {
                    @Override
                    public void call(Object object) {
                        title.setText(object.toString());
                    }
                }, new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        router3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router3.getText().toString()).call();
            }
        });

        router4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router4.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(Object object) {
                        title.setText(object.toString());
                    }
                });

            }
        });

        router5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router5.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(Object object) {
                        title.setText(object.toString());
                    }
                });
            }
        });
    }
}
