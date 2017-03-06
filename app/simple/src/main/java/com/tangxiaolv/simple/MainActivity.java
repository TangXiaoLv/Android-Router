
package com.tangxiaolv.simple;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.Reject;
import com.tangxiaolv.router.Resolve;
import com.tangxiaolv.simple.entity.A;
import com.tangxiaolv.simple.entity.B;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView title;

    @SuppressWarnings("all")
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
        final TextView router6 = (TextView) findViewById(R.id.router6);
        final TextView router7 = (TextView) findViewById(R.id.router7);
        final TextView router8 = (TextView) findViewById(R.id.router8);
        final TextView router9 = (TextView) findViewById(R.id.router9);
        final EditText input = (EditText) findViewById(R.id.input);
        final Button go = (Button) findViewById(R.id.go);

        /*android://main*/
        router1.setText("android://main");
        router1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router1.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(String type, Object result) {
                        title.setText(result.toString());
                    }
                }, new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        /*android://activity/localActivity*/
        router2.setText("android://main/activity/localActivity");
        router2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router2.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(String type, Object result) {
                        title.setText(result.toString());
                    }
                }, new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        /*android://main/params/basis?params={'f':1,'i':2,'l':3,'d':4,'b':true}*/
        router3.setText("android://main/params/basis?params={'f':1,'i':2,'l':3,'d':4,'b':true}");
        router3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router3.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(String type, Object result) {
                        title.setText(result.toString());
                    }
                }, new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        /*android://main/params/complex?params={'b':{},'listC':[]}*/
        router4.setText("android://main/params/complex?params=" +
                "{'b':{" +
                "'key1':'Hi','key2':1,'key3':'true','key4':2," +
                "key5:[{'key1':'haha'},{'key1':hehe},{'key1':'wawa'}],key6:{'key1':'Hello','key2':1}}," +
                "'listC':[{'key1':'haha'},{'key1':hehe},{'key1':'wawa'}]}");
        router4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router4.getText().toString())
                        .showTime()
                        .call(new Resolve() {
                            @Override
                            public void call(String type, Object result) {
                                title.setText(result.toString());
                            }
                        }, new Reject() {
                            @Override
                            public void call(Exception e) {
                                title.setText(e.toString());
                            }
                        });
            }
        });

        /*android://main/jsonObject?params={'f':1,'i':2,'l':3,'d':4,'b':true,'b':{},'listC':[]}*/
        router5.setText("android://main/jsonObject?params=" +
                "{'f':1,'i':2,'l':3,'d':4,'b':true," +
                "'b':{" +
                "'key1':'Hi','key2':1,'key3':'true','key4':2," +
                "key5:[1,2,3],key6:{'key1':'Hello','key2':1}}," +
                "'listC':[{'key1':'haha'},{'key1':hehe},{'key1':'wawa'}]}");
        router5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router5.getText().toString())
                        .showTime()
                        .call(new Resolve() {
                            @Override
                            public void call(String type, Object result) {
                                title.setText(result.toString());
                            }
                        }, new Reject() {
                            @Override
                            public void call(Exception e) {
                                title.setText(e.toString());
                            }
                        });
            }
        });


        /*android://main/jsonArray?params=[1,2,3]}*/
        router6.setText("android://main/jsonArray?params=[{'key1':'haha'},{'key1':hehe},{'key1':'wawa'}]");
        router6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router6.getText().toString())
                        .showTime()
                        .call(new Resolve() {
                            @Override
                            public void call(String type, Object result) {
                                title.setText(result.toString());
                            }
                        }, new Reject() {
                            @Override
                            public void call(Exception e) {
                                title.setText(e.toString());
                            }
                        });
            }
        });

        /*android://main/differentTypes?params=innerObj}*/
        router7.setText("android://main/differentTypes?params=innerObj");
        router7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first params
                B b = new B("Hi", 1, true, 2);
                ArrayList<A> key5 = new ArrayList<>();
                key5.add(new A("Hi", 1, true, 2));
                key5.add(new A("Hi", 1, true, 2));
                key5.add(new A("Hi", 1, true, 2));
                b.setKey5(key5);

                //second params
                ArrayList<B> listB = new ArrayList<>();
                listB.add(new B("Hi", 1, true, 2));
                listB.add(new B("Hi", 1, true, 2));
                listB.add(new B("Hi", 1, true, 2));

                HashMap<String, Object> map = new HashMap<>();
                map.put("listA", listB);
                map.put("a", b);
                AndroidRouter.open("android", "main", "/differentTypes", map)
                        .showTime()
                        .call(new Resolve() {
                            @Override
                            public void call(String type, Object result) {
                                title.setText(result.toString());
                            }
                        }, new Reject() {
                            @Override
                            public void call(Exception e) {
                                title.setText(e.toString());
                            }
                        });
            }
        });

        /*android://main/differentTypes?params=innerObj}*/
        router8.setText("remote://lib/openRemoteActivity");
        router8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router8.getText().toString()).call(new Resolve() {
                    @Override
                    public void call(String type, Object result) {
                        Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        /*android://main/throwError*/
        router9.setText("android://main/throwError");
        router9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidRouter.open(router9.getText().toString()).call(new Reject() {
                    @Override
                    public void call(Exception e) {
                        title.setText(e.toString());
                    }
                });
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String url = input.getText().toString();
                AndroidRouter.open(url).call(new Resolve() {
                    @Override
                    public void call(String type, Object object) {
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
    }
}
