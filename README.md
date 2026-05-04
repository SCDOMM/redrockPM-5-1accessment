# redrockPM-5·1accessment
红岩移动部门五一考核
## 一.简要介绍 ##
这是一个简简单单的移动项目，一个任务清单类小app。由于时间问题，该项目的UI和部分功能有待改进，并且可能存在一些潜在的BUG需要处理。
项目名称为“XiwangNotepad”，即“西王记事”，因为实在想不到应该用什么名字了（
项目的图标是由豆包AI生成的。
### 1.技术栈 ###
- Android API26和它的动物朋友们
- Kotlin2.2.10和它的动物朋友们
- 依赖库：
1. Anroidx和它的动物朋友们
2. retrofit2-convert-gson v2.6.1
3. OKhttp3 v4.11.0
4. retrofit2 v2.6.1
5. security-crypto v1.1.0-alpha06:用于构建EncryptedSharedPreferences的androidx官方依赖库
6. room v2.8.4数据库以及相关依赖
7. ksp插件
- 架构：
1. MVVM架构(注：由于时间紧迫，部分模块可能不遵循MVVM架构)
- 线程通信方式：
1. ViewModel
2. ViewModel里的LiveData
- 已有功能：
1. 实现登陆注册功能
2. 显示已创建任务
3. 实现对任务的增删查改
4. 实现对任务的任意拖拽
5. 右滑任务能进行删除
### 2.任务主页 ###
①任务View由material库的CardView和其他常见控件组合而来，并通过RecyclerView实现排列和组合。

任务的数据类使用了@Entity进行标记，使之可以被ROOM数据库进行增删改查。

②侧滑删除功能使用了ItemTouchHelper，作者从文档和网上一个一个抄实现了侧滑删除功能。

为了实现功能，作者定义了一个接口，其中包含onItemMove，onItemRemove，onItemComplete三大方法。

自定义的ItemTouchHelper通过实现该接口，可以实现Fragment/Activity中对应的接口方法。通过重写onSwiped方法实现侧滑监听，而item会在侧滑后会弹出一个Dialog提示你是否进行删除，若选择是，则会应用ViewModel中的removeItem方法将ROOM中对应的表删除；若选择不是，则会让适配器刷新，实现任务被拉回。

③拖拽功能使用了ItemTouchHelper。通过重写onMove方法实现移动监听，会调用接口的onItemMove方法，并且在对应的方法中使用adapter的notifyItemMoved方法改变item的位置。

当拖拽完成后，ItemTouchHelper的clearView方法会被触发，此时在其中调用onItemComplete方法，则会确认拖拽完成后的位置，并改变ROOM数据库中对应表的对应次序。

④通过自定义接口和监听方法，用户可以点击任务以打开其编辑界面，并对对应任务进行修改。

同时，通过BottomSheetDialogFragment方法，可以让用户在触发点击事件时弹出一个BottomSheet。该BottomSheet包含有DatePicker和TimePicker，双方通过一个ViewModel联系在一起，以此支持用户任意选择时间

⑤通过SwipeRefreshLayout实现刷新方法。每次触发刷新事件都会刷新ViewModel中的值，以此让RV本身刷新。

### 3.创建任务 ###
由于时间问题，“创建任务”采用的方法与“任务主页”的④点一致。只不过ViewModel中操作数据库的方法从update变为了Insert

### 4.个人页面 ###
由于没有配套后端，目前暂时没有登录和注册检测。
由于时间问题，暂不支持修改个人资料
①通过Retrofit在ViewModel中进行网络请求，并在请求成功后改变LiveData的值，以此实现改变UI的操作。
②同时，这里自建了一个Login/Register State密封类用于表述请求的状态：请求成功、请求失败、正在请求。在取得Retrofit的Callback后，程序会根据返回的情况修改LiveData中Login/Register State的值，实现响应。
## 二.功能展示 ##

1.登录注册(无配套后端，且登录检测功能暂未实装)
 *注意：如果您想加上配套的后端设施或者用POSTman/APIfox进行测试，可以在com.example.xiwangnotepad下的Config中进行修改

![[登录注册.gif|412]]

2.任务拖拽（通过拖拽改变任务顺序，且在退出重进/刷新后仍然保持该顺序）
![[任务拖拽.gif|414]]

3.删除任务（通过侧滑删除任务）
![[删除任务.gif|410]]

4.创建任务（能够通过TimePicker和DatePicker设定截止时间）
![[创建任务.gif|438]]

5.修改任务（修改时间功能与”创建任务“一致；由于时间问题，备注功能暂未实装）
![[任务修改.gif|426]]

## 三.待优化处和感想 ##

由于主播还要写后端五一考核，实在没时间了，所以UI和很多细节都没弄
1. 如果不填入时间，任务会显示默认的1970年，违和感拉满
2. 设置、登录检测、备注等功能暂未实装
3. 可以做个置顶功能
4. UI写的一坨屎
5. 可以在主页的封面处加上最近需要解决的任务，并且可以加个倒计时
6. 可以使用Service或者广播让app在任务即将到期时在通知栏提醒
7. 可以设个每分钟自动刷新功能，并且在截止时间一天之内用24小时标注截止时间

### 感想 ###
写项目太杀时间了，主播坐高铁时就在一直写代码，写到手指抽筋
但是，即便如此，在经过了数个月的学习后，我的开发效率直线上升，要知道，主播写寒假考核写了20天——而现在，这样一个看起来功能比较“完整”的app只用了主播四天。
随着学习的继续跟进，以及未来可能的使用compose技术，主播的开发效率还能更高，UI还能更加好看——那就是主播之后要去干的事情了。

