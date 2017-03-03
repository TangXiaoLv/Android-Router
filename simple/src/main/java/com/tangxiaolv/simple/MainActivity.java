
package com.tangxiaolv.simple;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.Reject;
import com.tangxiaolv.router.Resolve;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String url = input.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    A a = new A("1", 2, false, 4);
                    ArrayList<B> list = new ArrayList<>();
                    list.add(new B("1", 2, false, 4));
                    list.add(new B("1", 2, false, 4));
                    list.add(new B("1", 2, false, 4));
                    a.setKey5(list);

                    C c = new C("1", 2, false, 4);
                    c.setKey5(list);
                    a.setKey6(c);

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("entity", a);
                    url = "toon://one/entity?params=" + new Gson().toJson(map);
                    // url = "toon://one/list?params=" + new Gson().toJson(list);
                    input.setText(url);
                }

                AndroidRouter.open(url).call(new Resolve() {
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

                B b = new B("1", 2, false, 4);
                ArrayList<A> list = new ArrayList<>();
                list.add(new A("1", 2, false, 4));
                list.add(new A("1", 2, false, 4));
                list.add(new A("1", 2, false, 4));
                b.setKey5(list);
                b.setKey6(new D("1", 2, false, 4));
                params.put("entity", b);
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
