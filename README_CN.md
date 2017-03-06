# Android-Router
<img src="img/2.png" width = "660" height = "300"/>

|lib|androidrouter|androidrouter-compiler|androidrouter-annotations|
|---|---|---|---|
|version|[ ![Download](https://api.bintray.com/packages/tangxiaolv/maven/androidrouter/images/download.svg?version=1.0.0) ](https://bintray.com/tangxiaolv/maven/androidrouter/1.0.0/link)|[ ![Download](https://api.bintray.com/packages/tangxiaolv/maven/androidrouter-compiler/images/download.svg?version=1.0.0) ](https://bintray.com/tangxiaolv/maven/androidrouter-compiler/1.0.0/link)|[ ![Download](https://api.bintray.com/packages/tangxiaolv/maven/androidrouter-annotations/images/download.svg?version=1.0.0) ](https://bintray.com/tangxiaolv/maven/androidrouter-annotations/1.0.0/link)|
高性能，灵活，简单易用的轻量级Android组件化协议框架，用来解决复杂工程的互相依赖，解耦出的单个模块有利于独立开发和维护。

目标
---
- 工程解耦
- 模块独立开发独立维护
- 让生活变得美好

特性
---
- 编译时注入
- 路由过程抛出的异常集中处理
- 任意参数类型回传
- 运行时动态参数类型解析，支持不同类型传值
    * From jsonObject => To Object
    * From jsonArray => To List
    * From Object A => To Object A
    * From Object A => To Object B
    * From List< A> => To Object List< B> 

框架架构图
---

<img src="img/1.png" width = "824" height = "528"/>

Gradle
---
```
//需要在各自的application/library 中添加依赖
dependencies {
    compile 'com.library.tangxiaolv:androidrouter:1.0.0'
    annotationProcessor 'com.library.tangxiaolv:androidrouter-compiler:1.0.0
}
```

快速入门
---
注：使用本框架需要遵守标准协议格式：scheme://host/path?params=json
*scheme[1] host[1] path[2] params[2] 1:必须 2:可选*

###第一步:给自定义Module配置注解协议
```java
/**
 * Support parameter types
 *
 * float
 * int
 * long
 * double
 * boolean
 * String
 * List<?>
 * Map<String,Object>
 * custom object
 */
@RouterModule(scheme = "android", host = "main")
public class MainModule implements IRouter {


    //Route => android://main
    //默认传递Application context, String scheme, VPromise promise
    @RouterPath
    public void def(Application context, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: []");
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

    //Take out the value from json object
    //Route => android://main/params/complex?params={'b':{},'listC':[]}
    @RouterPath("/params/complex")
    public void paramsComplex(B b, List<C> listC, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/params/complex]");
    }

    //from json object => to object
    //key name must be "_params_"
    @RouterPath("/jsonObject")
    public void paramsPakege(Package _params_, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/jsonObject]");
    }

    //from json array => to list
    //key name must be "_params_"
    @RouterPath("/jsonArray")
    public void jsonArray(List<A> _params_, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/jsonArray]");
    }

    //eg: from A => to B
    @RouterPath("/differentTypes")
    public void differentTypes(A a, List<A> listA, String scheme, VPromise promise) {
        promise.resolve("","from scheme: [" + scheme + "] " + "path: [/differentTypes]");
    }

    @RouterPath("/throwError")
    public void throwError(VPromise promise) {
        //返回错误推荐使用RouterRemoteException
        promise.reject(new RouterRemoteException("I'm error................."));
    }
}
```
###第二步:调用协议
```
//任意地方调用
//方式一
AndroidRouter.open("android://main/activity/localActivity").call(new Resolve() {
        @Override
        public void call(String type, Object result) {
            //获取返回值
        }
    }, new Reject() {
        @Override
        public void call(Exception e) {
            //所有路由过程中的异常都会回调到这里
        }
    });
    
//方式二
 AndroidRouter.open("android", "main", "/differentTypes", null)
    .showTime()//开启本次路由调用耗时时间
    .call();//忽略返回值和错误
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