# 概况
一个AppStore，基于 Material Design + MVP + Retrofit +Dagger2+Rx全家桶，基本涵盖了当前 Android 端开发最常用的主流框架，基于此框架可以快速开发一个app。
# 分包情况
![image](http://pd4brty72.bkt.clouddn.com/PackageDirectory.png)
# 项目描述
《我的手机助手》是一个App应用商店，主要包括应用列表（推荐、排行、游戏、分类），下载（断点续传、下载管理），安装（静默安装、自动安装），搜索（历时搜索，关键字配对搜索），管理（下载记录，app升级、本地app、卸载），系统设置等。
# 项目地址
https://github.com/laibinzhi/MyAppPlay
# 安装地址
https://fir.im/g27t

![image](http://pd4brty72.bkt.clouddn.com/app_erweima.png)
# 应用技术
- 基于Google官方出品的Mvp架构,实现解耦，基类Activity,Fragment,Presenter高度封装。
- 使用Dagger2依赖注入，通过Apt编译时生成代码，解耦并且更好避免对象的重复实例化。
- Rxjava+Retrofit+RxCache实现的网络请求框架，优雅的响应式Api解决异步请求，缓存，线程调度以及http响应的预处理和统一错误处理。
- 基于Material Design设计风格，DrawerLayout + NavigationView实现侧滑菜单；CoordinatorLayout和CollapsingToolbarLayout互动实现拉伸滚动效果；RecyclerView和GridView的嵌套实现复杂列表等...
- 自定义下载按钮控件和控制器配合RxAndroid实现列表item的状态处理和交互。
- 支持多线程下载和断点续传。实现APK的普通安装，智能安装，静默安装。
- 封装RxBus,优化的发布/订阅事件总线,处理组件间的通信。
- 使用Rxbinding，优雅的处理View的响应事件。（防抖，表单验证等）
- 图片加载框架Glide和Picasso以及封装图片加载类 ImageLoader, 使用建造者模式,轻松切换图片加载框架,方便功能扩展。
- ButterKnife取代findviewbyid实现view的注入，简化代码
- GreenDao轻量级数据库实现数据缓存。

# 截图

![image](http://pd4brty72.bkt.clouddn.com/app1.gif) 
    ![image](http://pd4brty72.bkt.clouddn.com/app2.gif)    ![image](http://pd4brty72.bkt.clouddn.com/app3.gif)    ![image](http://pd4brty72.bkt.clouddn.com/app4.gif)    ![image](http://pd4brty72.bkt.clouddn.com/app5.gif)
