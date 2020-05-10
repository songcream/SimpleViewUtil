# SimpleViewUtil
集成方法：
项目使用的是androidx，如果原项目使用的是support库的话，没办法兼容
必须在项目的gradle.properties中开启androidx，添加以下代码：
android.useAndroidX=true

项目支持最小sdk minSdkVersion 19，必须在Manifest.xml中配置

在项目的build.gradle中添加
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

在使用的module的build.gradle中添加
```
dependencies {
	  implementation 'com.github.songcream:SimpleViewUtil:Tag'
}
```

项目包含的简单使用的view有：
## EmptyView
EmptyView 可以很轻松的实现空布局或者错误提示布局，使用方法仅仅只需要调用attach方法，将这个view绑定到加载不出内容需要显示空布局的View上，并且可以自定义空布局图标、标题，另一个实用的就是实现加载布局，页面刚进来没有数据时，可以一句话实现加载动画。使用方法如下：
##### 实现加载布局
```java
var emptyView=EmptyView(this)
emptyView.attachView(recyclerView)
emptyView.showEmptyLoading()
```

##### 实现空布局
```java
var emptyView=EmptyView(this)
emptyView.attachView(recyclerView)
emptyView.showEmptyView()
```
##### 自定义全局加载动画、空布局提示、空布局图标
```java
EmptyView.initDefault(int loadingDrawable,int emptySrc,String emptyString,int errorSrc)
```
##### 自定义局部空布局提示、空布局图标
```java
emptyView.showEmptyView(int src, String content);
```

