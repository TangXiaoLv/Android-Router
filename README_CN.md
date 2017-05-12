# Android-Router
[English](https://github.com/TangXiaoLv/Android-Router/blob/master/README.md) | 中文

<img src="img/2.png" width = "660" height = "300"/>

|Lib|androidrouter|androidrouter-compiler|androidrouter-annotations|
|:---:|:---|:---|:---|
|最新版本|2.0.6|1.0.1|1.0.0|

高性能，灵活，简单易用的轻量级Android组件化协议框架，用来解决复杂工程的互相依赖，解耦出的单个模块有利于独立开发和维护。

Update Log
---
```
2.0.6: 修复空list解析异常
2.0.5: 修复使用getValue拿返回值当返回值是void类型时引发的ANR异常。
2.0.4: 优化代码。
2.0.3: 修复继承类互相转化成不同类值取不全，支持getValue方法强制类型转换(但不支持不同类之间转换,适用于基本类型取值,复杂类型请使用回调的方式并且支持不同类型转换)
2.0.2: 修复context参数覆盖问题。
2.0.1: 支持返回值对象不一致时自动转换，修复数组类型解析异常。
2.0.0: 支持泛型返回值，支持响应式处理回调函数。
-----2.0+新特性不支持1.0+
1.0.7: 修复系统类参数传递异常。
1.0.6: 修复一些已知问题。
1.0.5: 支持数组类型参数和可变参数。
1.0.4: 支持阻塞式返回值。
1.0.3: 支持当有返回值时自动回调promise返回。
1.0.2: 支持线程切换。
1.0.1: 性能优化。
1.0.0: 第一次推送。
```

目标
---
- 工程解耦
- 模块独立开发独立维护
- 让生活变得美好

特性
---
- 编译时处理注解生成模板代码
- 路由过程抛出的异常集中处理
- 任意参数类型回传
- 运行时动态参数类型解析，支持不同类型传值
    * From jsonObject => To Object
    * From jsonArray => To List
    * From Object A => To Object A
    * From Object A => To Object B
    * From List< A> => To Object List< B> 

组件化路由图
---

<img src="img/1.png" width = "824" height = "528"/>

Gradle
---
```gradle
//需要在各自的application/library 中添加依赖
//android plugin version >= 2.2+
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

快速入门
---
注：使用本框架需要遵守标准协议格式：scheme://host/path?params=json

*scheme[1] host[1] path[2] params[2] 1:必须 2:可选*

**第一步:给自定义Module配置注解协议**
```java
@RouterModule(scheme = "android", host = "main")
public class MainModule implements IRouter {

    //Route => android://main
    //默认传递Application context, String scheme, VPromise promise
    @RouterPath
    public void def(Application context, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: []");
    }
    
    //如果返回值不是void类型，当return时会自动调用promise返回
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

    //从json object中取值
    //Route => android://main/params/basis?params={'f':1,'i':2,'l':3,'d':4,'b':true}
    @RouterPath("/params/basis")
    public void paramsBasis(float f, int i, long l, double d, boolean b,
                            String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/params/basis]");
    }

    //从json object中取值
    //Route => android://main/params/complex?params={'b':{},'listC':[]}
    //自定义对象需要实现IRouter,并且需要空参数构造函数
    @RouterPath("/params/complex")
    public void paramsComplex(B b, List<C> listC, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/params/complex]");
    }

    //将json对象中的全部数据转化成自定义对象,key必须是_params_
    //自定义对象需要实现IRouter,并且需要空参数构造函数
    @RouterPath("/jsonObject")
    public void paramsPakege(Package _params_, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/jsonObject]");
    }

    //将json数组中的全部数据转化成List,key必须是_params_
    //自定义对象需要实现IRouter,并且需要空参数构造函数
    @RouterPath("/jsonArray")
    public void jsonArray(List<A> _params_, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/jsonArray]");
    }

    //eg: from A => to B
    //注意：不同类型对象传递,对象中的基本类型参数key和类型必须一致
    //注意：2边对象需要实现IRouter,并且需要空参数构造函数
    @RouterPath("/differentTypes")
    public void differentTypes(A a, List<A> listA, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/differentTypes]");
    }

    //返回错误推荐使用RouterRemoteException
    @RouterPath("/throwError")
    public void throwError(VPromise promise) {
        promise.reject(new RouterRemoteException("I'm error................."));
    }
    
    @RouterPath("/reactive")
    public void reactive(VPromise promise) {
        promise.resolve("I'm from reactive!!!!!");
    }
}

//支持接收多scheme
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

**支持的类型转换**

|来源类型|-|去向类型|描述|
|:---|:---:|:---|:---|
|float|⇌|float|`1.0.0+`|
|int|⇌|int|`1.0.0+`|
|long|⇌|long|`1.0.0+`|
|double|⇌|double|`1.0.0+`|
|boolean|⇌|boolean|`1.0.0+`|
|String|⇌|String|`1.0.0+`|
|Object A|⇌|Object A|`1.0.0+`|
|Object A|⇌|Object B|`1.0.0+` 2边对象必须实现IRouter并且需要有空参数constructor|
|A[]|⇌|A[]|`2.0.1+`|
|A[]|⇌|B[]|`2.0.1+` 数组内对象2边必须实现IRouter并且需要有空参数constructor|
|A[]|→|Varargs A|`2.0.1+` [1,2,3] → add(int... i)|
|List< A>|⇌|List< A>|`1.0.0+` 接收者定义必须是List<?>接口类型 eg:List< A>|
|List< A>|⇌|List< B>|`1.0.0+` 集合内对象2边必须实现IRouter并且需要有空参数constructor|
|Json Object|⇌|Object|`1.0.0+`|
|Json Object|→|Map< String,String>|`2.0.1+`|
|Json Array|⇌|List< ?>|`1.0.0+`|

**第二步:调用协议**
```java
//方式一
//异步拿返回值
AndroidRouter.open("android://main/activity/localActivity")
    .callOnSubThread()//调用在子线程
    .returnOnMainThread()//回调在主线程
    .call(new Resolve<String>() {
        @Override
        public void call(String result) {
            //获取返回值
        }
    }, new Reject() {
        @Override
        public void call(Exception e) {
            //所有路由过程中的异常都会回调到这里
    }
});
    
//方式二
 AndroidRouter.open("android", "main", "/differentTypes")
    .showTime()//显示本次调用时间
    .call();//忽略返回值和错误
    
//方式三
//同步拿返回值
boolean value = AndroidRouter.open("android://main/getValue").getValue();

//方式四:响应式处理
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

**混淆**
```
//配置混淆
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
