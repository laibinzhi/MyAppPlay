# 分包情况
![image](http://i2.bvimg.com/656323/0a2d5df0d5952dff.png)
# 项目描述
《我的手机助手》是一个App应用商店，主要包括应用列表（推荐、排行、游戏、分类），下载（断点续传、下载管理），安装（静默安装、自动安装），搜索（历时搜索，关键字配对搜索），管理（下载记录，app升级、本地app、卸载），系统设置等。
# 项目地址
https://github.com/laibinzhi/MyAppPlay
# 安装地址
https://fir.im/g27t

![image](http://i2.bvimg.com/656323/9a0d7070163407fe.png)
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
![image](http://i2.bvimg.com/656323/c3853841bda557d4.gif) 
    ![image](http://i2.bvimg.com/656323/1015710ed2ee2033.gif)    ![image](http://i2.bvimg.com/656323/ff7bd14697f9440d.gif)    ![image](http://i2.bvimg.com/656323/3f70a3e6ac330f25.gif)    ![image](http://i2.bvimg.com/656323/f0ee370099af5cf6.gif)