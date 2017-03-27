# Android-Router
English | [中文](https://github.com/TangXiaoLv/Android-Router/blob/master/README_CN.md)

<img src="img/2.png" width = "660" height = "300"/>

|lib|androidrouter|androidrouter-compiler|androidrouter-annotations|
|---|---|---|---|
|Latest Version|2.0.1|1.0.1|1.0.0|

High-performance, flexible, easy-to-use lightweight Android component-based framework, Used to solve the interdependence of complex projects, A single module is conducive to independent development and maintenance.

Update Log
---
```
2.0.1: Support auto cast on return value, fix array cast exception. 
2.0.0: Resolve callback support generic, Support reactive programming, Remove resolve.call 'type' param. 
-----2.0+ new feature dont support 1.0+
1.0.7: Fix system params passed exception.
1.0.6: Fix known issues.
1.0.5: Support array params and extend params.
1.0.4: Support await the result return.It will block thread.
1.0.3: Support auto promise.resolve when return type not eq void.
1.0.2: Support thread switching.
1.0.1: Optimizing performance.
1.0.0: First push.
```

Goal
---
- Project decoupling
- Stand-alone develop,Stand-alone maintenance.
- Make life better

Characteristics
---
- Uses annotation processing to generate boilerplate code for you.
- Exception centralized processing.
- Any parameter type return.
- Runtime parameter parse，Support different types of delivery.
    * From jsonObject => To Object
    * From jsonArray => To List
    * From Object A => To Object A
    * From Object A => To Object B
    * From List< A> => To Object List< B> 

Modular architecture diagram
---

<img src="img/1.png" width = "824" height = "528"/>

Gradle
---
```
//Add dependencies inside application/library.
//android plugin version > 2.2+
dependencies {
    compile 'com.library.tangxiaolv:androidrouter:x.x.x'
    annotationProcessor 'com.library.tangxiaolv:androidrouter-compiler:x.x.x'
}

//android plugin version < 2.2
apply plugin: 'com.neenbedankt.android-apt'

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
    }
}

dependencies {
    compile 'com.library.tangxiaolv:androidrouter:x.x.x'
    apt 'com.library.tangxiaolv:androidrouter-compiler:x.x.x'
}
```

Getting Started
---
Note：Android-Router Protocol Format: scheme://host/path?params=json 

*scheme[1] host[1] path[2] params[2] 1:required 2:option*

**Step 1:Setup router module**
```java
@RouterModule(scheme = "android", host = "main")
public class MainModule implements IRouter {

    //Route => android://main
    //default parameter: Application context, String scheme, VPromise promise
    @RouterPath
    public void def(Application context, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: []");
    }
    
    //if return type not eq void, The promise will be auto called.
    @RouterPath("/autoReturn")
    public String autoReturn(String scheme) {
        return "I'm auto return!!!!! ";
    }

    //Route => android://main/activity/localActivity
    @RouterPath("/activity/localActivity")
    public void openLocalActivityAndReturnResult(Application context, VPromise promise) {
        String tag = promise.getTag();
        Intent intent = new Intent(context, LocalActivity.class);
        intent.putExtra("tag", tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //Take out the value from json object
    //Route => android://main/params/basis?params={'f':1,'i':2,'l':3,'d':4,'b':true}
    @RouterPath("/params/basis")
    public void paramsBasis(float f, int i, long l, double d, boolean b,
                            String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/params/basis]");
    }

    //Take out the complex value from json object
    //Route => android://main/params/complex?params={'b':{},'listC':[]}
    @RouterPath("/params/complex")
    public void paramsComplex(B b, List<C> listC, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/params/complex]");
    }

    //From json object => to object
    //The key must be "_params_"
    @RouterPath("/jsonObject")
    public void paramsPakege(Package _params_, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/jsonObject]");
    }

    //From json array => to List
    //The key must be "_params_"
    @RouterPath("/jsonArray")
    public void jsonArray(List<A> _params_, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/jsonArray]");
    }

    //eg: from A => to B
    //Note:The primitive params name and type must be the same.
    //Note:Both need implements IRouter and have empty constructor.
    @RouterPath("/differentTypes")
    public void differentTypes(A a, List<A> listA, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/differentTypes]");
    }

    //If you want to return an error. Recommend use RouterRemoteException
    @RouterPath("/throwError")
    public void throwError(VPromise promise) {
        promise.reject(new RouterRemoteException("I'm error................."));
    }
    
    @RouterPath("/reactive")
    public void reactive(VPromise promise) {
        promise.resolve("I'm from reactive!!!!!");
    }
}

//Support multiple scheme.
@RouterModule(scheme = "android|remote", host = "lib")
public class RemoteModule implements IRouter {

    @RouterPath("/openRemoteActivity")
    public void openRemoteActivity(Application context, String scheme, VPromise promise) {
        Intent intent = new Intent(context, RemoteActivity.class);
        intent.putExtra("tag", promise.getTag());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
```

**Supported parameter**

|from|-|to|desc|
|:---|:---:|:---|:---|
|float|⇌|float|`1.0.0+`|
|int|⇌|int|`1.0.0+`|
|long|⇌|long|`1.0.0+`|
|double|⇌|double|`1.0.0+`|
|boolean|⇌|boolean|`1.0.0+`|
|String|⇌|String|`1.0.0+`|
|Object A|⇌|Object A|`1.0.0+` |
|Object A|⇌|Object B|`1.0.0+` A and B object must implement IRouter and keep empty constructor|
|A[]|⇌|A[]|`2.0.1+`|
|A[]|⇌|B[]|`2.0.1+` A and B object must implement IRouter and keep empty constructor|
|A[]|→|Varargs A|`2.0.1+` [1,2,3] → add(int... i)|
|List< A>|⇌|List< A>|`1.0.0+` Receiver must be defined as List<?> interface|
|List< A>|⇌|List< B>|`1.0.0+` A and B object must implement IRouter and keep empty constructor|
|Json Object|⇌|Object|`1.0.0+`|
|Json Object|→|Map< String,String>|`2.0.1+`|
|Json Array|⇌|List< ?>|`1.0.0+`|

**Step 2:Invoke**
```
AndroidRouter
    .open("android://main/activity/localActivity")
    .callOnSubThread()
    .returnOnMainThread()
    .call(new Resolve() {
        @Override
        public void call(String type, Object result) {
            //Receive result
        }
    }, new Reject() {
        @Override
        public void call(Exception e) {
            //All routing errors are callback here.
        }
});

//or
AndroidRouter
    .open("android", "main", "/differentTypes")
    .showTime()//Show time
    .call();//Igone result and error.
    
//or
//Await the result returned.It will block thread.
Object value = AndroidRouter.open("android://main/getValue").getValue();

//or
AndroidRouter.open("android://main/reactive")
    .call(new Func<String, Integer>() {
        @Override
        public Integer call(String s) {
            return 1;
        }
    })
    .then(new Func<Integer, Double>() {
        @Override
        public Double call(Integer integer) {
            return 2.0;
        }
    })
    .then(new Func<Double, String>() {
        @Override
        public String call(Double ddouble) {
            return "I'm reactive!";
        }
    })
    .done(new Resolve<String>() {
        @Override
        public void call(String result) {
            title.setText(result);
        }
    }, new Reject() {
        @Override
        public void call(Exception e) {
            title.setText(e.toString());
        }
    });
```

**Proguard**
```
//Add proguard-rules
-keep class * implements com.tangxiaolv.router.interfaces.IMirror{*;}
-keep class * implements com.tangxiaolv.router.interfaces.IRouter{*;}
```

License
---
    Copyright 2017 TangXiaoLv
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
