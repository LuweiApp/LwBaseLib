# LwBaseLib
### 芦苇科技Android基础框架库

- ### Dialog

  > 待添加

- ### Popup

  > Popup弹窗是一个非常易用，并且自定义性比较高的对系统PopupWindow的封装。通过链式调用可一行代码快速实现Popup，也可以通过使用预创建好的Popup实现同类型弹窗，避免重复代码。

  1. 基本使用

     - Popup的基础文件只有 `BasePopup`、`XGravity`、`YGravity` 三个，其中后两者主要是控制弹窗展示的方向，所有Popup的实现和控制都在 `BasePopup` 中。在按照自己需求创建Popup时，需要继承 `BasePopup` ，并传入必要的 `Context` 信息。

     - 为了方便直接使用，在基础库中已经创建了一个基础的通用自定义Popup—— `CustomPopup` ，下面通过示例，看一下如何通过调用`CustomPopup` 创建一个功能完整的Popup：

       ```java
       CustomPopup.newInstance(this)//创建实例，传入Context
               .setContentView(R.layout.market_popup_invite_guild)//设置弹窗布局xml文件
               .setWidth(ScreenUtils.getScreenWidth() / 3 * 2)//设定宽度 
               .setBackgroundDimEnable(true)//设定打开弹窗时背景变暗
               .setFocusAndOutsideEnable(true)//点击Popup之外的地方可关掉Popup
               .setOnViewListener((view, popup) -> {
                   EditText etApplyText = view.findViewById(R.id.et_invite_text);
                   //... 此处省略 对View中的控件进行处理
                   popup.dismiss();//关闭弹窗
                   });
               })
       				.showAtLocation(getView(), Gravity.CENTER);//居中显示Popup
       ```

     - 在项目中，为了处理同样类型的弹窗，可以通过继承 `BasePopup` 创建特定类型的Popup。比如最常用的确认弹窗，包含确认、取消、关闭三个按钮，以及顶部标题栏，描述栏等内容。下面举例如何封装一个通用的确认弹窗Popup：

       ```java
       public class ConfirmPopup extends BasePopup<ConfirmPopup> {
           private Context mContext;
           private Callback mCallback;
           private View.OnClickListener mOnCancelListener;
           private View.OnClickListener mOnConfirmListener;
           private CharSequence mTitleText;
           private CharSequence mDescribeText;
           private CharSequence mCancelText;
           private CharSequence mConfirmText;
           private int mCancelColor;
           private int mConfirmColor;
       
           private ConfirmPopup(Context context) {
               this.mContext = context;
               setContext(mContext);
           }
       
           public static ConfirmPopup newInstance(Context context) {
               return new ConfirmPopup(context);
           }
       
           @Override
           protected void initAttributes() {
               setContentView(R.layout.base_popup_confirm);//设置布局xml文件
               setBackgroundDimEnable(true)//设定打开弹窗时背景变暗
               setFocusAndOutsideEnable(true)//点击Popup之外的地方可关掉Popup
               setWidth(ScreenUtils.getScreenWidth() / 3 * 2)//设定宽度 
           }
       
           @Override
           protected void initViews(View view, ConfirmPopup popup) {
               TextView tvTitle = view.findViewById(R.id.tv_title);
               TextView tvDescribe = view.findViewById(R.id.tv_describe);
               TextView tvConfirm = view.findViewById(R.id.tv_confirm);
               TextView tvCancel = view.findViewById(R.id.tv_cancel);
               if (mCallback != null) {
                   tvConfirm.setOnClickListener(v -> mCallback.onClick(this));
               } else if (mOnConfirmListener != null) {
                   tvConfirm.setOnClickListener(mOnConfirmListener);
               }
               if (mOnCancelListener != null) {
                   tvCancel.setOnClickListener(mOnCancelListener);
               } else {
                   tvCancel.setOnClickListener(v -> popup.dismiss());//添加默认取消按钮监听
               }
               if (mTitleText != null) {
                   tvTitle.setText(mTitleText);
               }
               if (mDescribeText != null) {
                   tvDescribe.setText(mDescribeText);
               }
               if (mConfirmText != null) {
                   tvConfirm.setText(mConfirmText);
               }
               if (mCancelText != null) {
                   tvCancel.setText(mCancelText);
               }
               if (mCancelColor != 0) {
                   tvConfirm.setTextColor(mCancelColor);
               }
               if (mConfirmColor != 0) {
                   tvConfirm.setTextColor(mConfirmColor);
               }
           }
       		//设置取消按钮监听
           public ConfirmPopup setOnCancelClickListener(View.OnClickListener onCancelListener) {
               mOnCancelListener = onCancelListener;
               return this;
           }
           //设置确认按钮监听
           public ConfirmPopup setOnConfirmClickListener(View.OnClickListener onConfirmListener) {
               mOnConfirmListener = onConfirmListener;
               return this;
           }
           //设置标题文字
           public ConfirmPopup setTitleText(CharSequence titleText) {
               mTitleText = titleText;
               return this;
           }
           //设置描述文字
           public ConfirmPopup setDescribeText(CharSequence describeText) {
               mDescribeText = describeText;
               return this;
           }
           //设置取消按钮文字
           public ConfirmPopup setCancelText(CharSequence cancelText) {
               mCancelText = cancelText;
               return this;
           }
           //设置确认按钮文字
           public ConfirmPopup setConfirmText(CharSequence confirmText) {
               mConfirmText = confirmText;
               return this;
           }
           //省去了所有文字设置以资源方式的重载方法
           //设置确认按钮文字颜色
           public ConfirmPopup setConfirmColor(@ColorRes int confirmColorRes) {
               mConfirmColor = ContextCompat.getColor(mContext, confirmColorRes);
               return this;
           }
           //设置取消按钮文字颜色
           public ConfirmPopup setCancelColor(@ColorRes int cancelColorRes) {
               mCancelColor = ContextCompat.getColor(mContext, cancelColorRes);
               return this;
           }
           //设置确认按钮监听（callback方式）
           public ConfirmPopup setOnConfirmCallback(Callback callback) {
               mCallback = callback;
               return this;
           }
       		
           public interface Callback {
               void onClick(ConfirmPopup popup);
           }
       }
       ```

       使用例子：

       ```java
       ConfirmPopup.newInstance(getContext())
               .setTitleText(R.string.user_bind_weixin)//设置标题字段
               .setDescribeText(R.string.user_bind_wexin_text)//设置描述字段
               .setConfirmText(R.string.user_bind)//设置确认按钮字段
               .setOnDismissListener(() -> {//设置Popup关闭时的监听
                   //...省略操作
               })
               .setOnConfirmCallback(popup -> {//设置确认按钮点击操作
                   //...省略操作
                   popup.dismiss();
               })
               .showAtLocation(getView(), Gravity.CENTER);//居中显示Popup
       ```

     - 以上两种Popup的使用方法中，第一种直接通过 `CustomPopup` 完全自定义创建一个Popup对象，优势是使用快捷，除了布局文件不会再创建其他内容。第二种通过调用预先创建好的Popup，优点则是具有了可复用性，且如果自定义方法足够详细，在调用时也能更加方便，减少代码量。

  2. Popup所有Api使用介绍

     | 方法名                     | 传参类型                           | 功能                           | 说明                                |
     | -------------------------- | ---------------------------------- | ------------------------------ | ----------------------------------- |
     | setContext                 | Context                            | 传入Context                    | 必要参数                            |
     | setContentView             | @LayoutRes int 、View              | 传入View                       | 必要参数                            |
     | setWidth                   | int                                | 设置宽度                       | 默认为ContentView根布局宽度         |
     | setHeight                  | int                                | 设置高度                       | 默认为ContentView根布局高度         |
     | setAnchorView              | View                               | 设置锚点View                   | 以该View作为锚点进行布局            |
     | setYGravity                | @YGravity int                      | 设置Y轴方向                    | 在Y轴上相对锚点的位置               |
     | setXGravity                | @XGravity int                      | 设置X轴方向                    | 在X轴上相对锚点的位置               |
     | setOffsetY                 | int                                | Y轴的偏移量                    | 在Y轴上相对锚点的位置偏移量         |
     | setOffsetX                 | int                                | Y轴的偏移量                    | 在X轴上相对锚点的位置偏移量         |
     | setAnimationStyle          | @StyleRes int                      | 设置动画                       | 弹窗打开和关闭的动画                |
     | setEnterTransition         | Transition                         | 进场动画                       | 弹窗打开的动画                      |
     | setExitTransition          | Transition                         | 退场动画                       | 弹窗关闭的动画                      |
     | setFocusable               | boolean                            | 是否可以获取焦点               | 默认为true                          |
     | setOutsideTouchable        | boolean                            | 是否点击Popup以外区域关闭Popup | 默认为true                          |
     | setTouchable               | boolean                            | Popup是否可触摸                | 默认为true，false情况下点击直接关闭 |
     | setFocusAndOutsideEnable   | boolean                            | 设置获取焦点+点击外部取消      | 默认为true                          |
     | setBackgroundDimEnable     | boolean                            | 弹出pop时，背景是否变暗        | 默认为true                          |
     | setDimValue                | float                              | 设置背景变暗时透明度           | 默认为0.7f                          |
     | setDimColor                | @ColorInt int                      | 设置背景变暗颜色               | 默认为黑色                          |
     | setDimView                 | ViewGroup                          | 设置背景变暗的View             |                                     |
     | setInputMethodMode         | int                                | 设置输入法的操作模式           |                                     |
     | setSoftInputMode           | int                                | 设置输入法的操作模式           |                                     |
     | setNeedReMeasureWH         | boolean                            | 是否重新测量宽高               | 默认为true                          |
     | showAtLocation             | View，int                          | 按照位置显示Popup              |                                     |
     | showAtAnchorView           | View，@YGravity int，@XGravity int | 根据锚点显示Popup              |                                     |
     | setOnDismissListener       | OnDismissListener                  | 设置Popup关闭的监听            | 监听 Popup关闭的事件                |
     | setOnRealWHAlreadyListener | OnRealWHAlreadyListener            | 设置真实宽高获取成功监听       | 用于获取准确的Popup宽高             |
     | dismiss                    |                                    | 关闭Popup                      |                                     |

     

- ### RecyclerView相关

  > 实际是 [鸡汤程序员gminibird](https://github.com/gminibird)根据[MultiType](https://github.com/drakeet/MultiType)封装而来，核心思想是使用Binder替代Adapter来处理RecyclerView复杂布局的编写。在熟练后，使用非常方便且快速。
  >

  1. 基础使用

     **Step 1**. 创建一个 `class`，它将是你的数据类型或 Java bean / model. 对这个类的内容没有任何限制。示例如下：

     ```java
     public class Category {
     
         @NonNull public final String text;
     
         public Category(@NonNull String text) {
             this.text = text;
         }
     }
     ```

     **Step 2**. 创建一个 `class` 继承 `ItemViewBinder`.

     `ItemViewBinder` 是个抽象类，其中 `onCreateViewHolder` 方法用于生产你的 Item View Holder, `onBindViewHolder` 用于绑定数据到 `View`s. 一般一个 `ItemViewBinder` 类在内存中只会有一个实例对象，MultiType 内部将复用这个 binder 对象来生产所有相关的 item views 和绑定数据。示例：

     ```java
     public class CategoryViewBinder extends LwItemBinder<Category> {
     
         @Override
         protected View getView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
             return inflater.inflate(R.layout.item_category, parent, false);
         }
     
         @Override
         protected void onBind(@NonNull LwViewHolder holder, @NonNull Category category) {
             holder.category.setText(category.text);
         }
     }
     ```

     **Step 3**. 在 `Activity` 或 `Fragment` 中加入 `RecyclerView` 和 `List` 并注册你的类型，示例：

     ```java
     public class MainActivity extends AppCompatActivity {
     
         private LwAdapter adapter;
         /* Items 等同于 ArrayList<Object> */
         private Items items;
     
         @Override 
         protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_main);
             RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
     				recyclerView.setLayoutManager(new LinearLayoutManager(this));
           
             adapter = new LwAdapter(items);
             /* 注册类型和 View 的对应关系 */
             adapter.register(Category.class, new CategoryViewBinder());
             adapter.register(Song.class, new SongViewBinder());
             recyclerView.setAdapter(adapter);
     
             /* 模拟加载数据，也可以稍后再加载，然后使用
              * adapter.notifyDataSetChanged() 刷新列表 */
             items = new Items();
             for (int i = 0; i < 20; i++) {
                 items.add(new Category("Songs"));
                 items.add(new Song("小艾大人", R.drawable.avatar_dakeet));
                 items.add(new Song("许岑", R.drawable.avatar_cen));
             }
             adapter.addAll(items);
             adapter.notifyDataSetChanged();
         }
     }
     ```

     在Binder的创建中， `getView` 和 `onBind` 分别对应 `RecyclerView` 中的 `onCreateViewHolder` 和 `onBindViewHolder` 方法。

  2. 复杂布局的Item实现

     直接参考作者的文章：

     [ItemDecoration深入解析与实战（一）——源码分析](https://juejin.im/post/5c0bd6395188250e84659966)

     [ItemDecoration深入解析与实战（二）—— 实际运用](https://juejin.im/post/5c066ca4e51d451db1421ba0)

  

- ### 权限获取Permission

  > RxPermissions 是基于 RxJava 开发的用于帮助 在Android 6.0 中处理运行时权限检测的框架。在 Android 6.0 中增加了对危险权限的动态申请，而不是像 Android 6.0 之前的默认全部获取的方式。
  >
  > 详细介绍——[RxPermissions 源码解析之举一反三](https://juejin.im/post/5c18c0546fb9a049ad76fbde)

  **使用方式：**

  RxPermissions 的使用方式有两种

  方式 1：

  ```
  RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
          rxPermissions
                  .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                  .subscribe(new Consumer<Boolean>() {
                      @Override
                      public void accept(Boolean aBoolean) throws Exception {
                          if (aBoolean) {
                              // 通过
                          }else{
                              // 拒绝
                          }
                      }
                  });
  ```

  方式 2：结合 RxBinding 来使用

  ```java
  RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
  // Must be done during an initialization phase like onCreate
  RxView.clicks(findViewById(R.id.enableCamera))
      .compose(rxPermissions.ensure(Manifest.permission.CAMERA))
      .subscribe(granted -> {
          // R.id.enableCamera has been clicked
      });
  ```

  

- ### 事件订阅及接收Rxbus

  > 目前在Base库中，已经较为完善的封装了一套Rxbus框架，主要通过观察者模式处理应用程序间各个组件的通信，或者组件与组建之间的数据传递。

  **基础使用：**

  举例页面A中做了一个操作，需要更改页面B中的一个UI，贴出代码展示如何通过Rxbus实现：

  页面A：

  ```java
  RxBus.getInstance().post(new SimpleEvent(SimpleEvent.FLAG_CHANGE_UI));
  ```

  在页面B中接受事件：

  ```java
  RxBus.getInstance().register(this)
          .ofType(SimpleEvent.class)
          .subscribe(simpleEvent -> {
              if (simpleEvent.getFlag() == SimpleEvent.FLAG_CHANGE_UI) {
                  //..省略修改UI操作
              }
          }, throwable -> ToastUtils.showShort(throwable.getMessage()));
  ```

  其中用到的示例Event：

  ```
  public class SimpleEvent extends BaseEvent {
  
      /**
       * 修改UI
       */
      public static final int FLAG_CHANGE_UI = 0;
  
      public FindEvent(int flag) {
          super(flag, null);
      }
  
      public FindEvent(int flag, Object content) {
          super(flag, content);
      }
  }
  ```

- ### 简单返回事件传递SimpleForResult

  > 是对Activity中startActivityForResult功能进行的封装，通过观察者模式可以更方便地实现其功能，避免调用 startActivity 时，需要 onActivityResult 处理的类。

  **简单示例：**

  在A页面中打开页面B，并传入Extra数据。

  ```java
  /**
   * 微信号未绑定手机号情况下跳转手机绑定
   * 绑定完后授权登录微信
   */
  public void goToBindPhone(String jsonKey) {
      Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
      intent.putExtra("jsonKey", jsonKey);
      new SimpleForResult(this).startForResult(intent,
              (requestCode, resultCode, data) -> {
                  if (resultCode == RESULT_OK) {
                      onAuthorizeSuccess();//授权登录成功
                  }
              });
  }
  ```

  在B页面中，执行完成操作后，设置 `setResult` 为 `RESULT_OK` ，关闭B页面Activity返回A页面，A页面判断 `resultCode` ，code相同则执行操作。

  ```java
  /**
   * 绑定成功回调
   */
  public void onBindPhoneSuccess() {
      setResult(RESULT_OK);
      finish();
  }
  ```

- ### 通用标题栏TitleBar

  > TitleBar是一个可自定义性强，使用简单的通用标题栏框架，在xml和java代码中都能控制，可在application中全局设置文字属性等内容。

  1. 常用方式

     xml中直接使用：

     ```xml
     <com.luwei.ui.view.TitleBar
         android:id="@+id/titleBar"
         android:layout_width="match_parent"
         android:layout_height="wrap_content" />
     ```

     需要注意的是，每个标题栏都需要设置标题，可以在`manifest`中设置`android:label`属性：

     ```xml
     <activity android:name=".activity.TitleBarAcitivity"
         android:label="通用标题栏"/>
     ```

     或者通过`app:titleText`属性设置标题，如果不需要显示则直接设为`app:titleText=""`

  2. 全局设置

     在app中可以进行全局属性设置，示例如下：

     ```java
     public class MyApplication extends Application {
         @Override
         public void onCreate() {
             super.onCreate();
     
             TitleBar.getConfig()
                     .setTitleTextColor(Color.RED)
                     .setTitleTextSize(20)
                     .setBackGroundColor(Color.GRAY);
         }
     }
     ```

  3. 所有API方法

     | 方法名                  | 说明                                                      |
     | ----------------------- | --------------------------------------------------------- |
     | setTitleText            | 设置标题文字，也可以在manifest中设置label属性             |
     | setTitleTextColor       | 设置标题文字颜色                                          |
     | setTitleTextSize        | 设置标题文字大小                                          |
     | setLeftImage            | 单独设置标题栏左部图片                                    |
     | setLeftText             | 设置标题栏左部文字（如果设置了LeftImage，则只显示Image）  |
     | setLeftTextColor        | 设置标题栏左部文字颜色                                    |
     | setLeftTextSize         | 设置标题栏左部文字大小                                    |
     | setLeftDrawableLeft     | 配合左部文字使用，将图片放置到文字的左侧                  |
     | setLeftDrawableRight    | 配合左部文字使用，将图片放置到文字的右侧                  |
     | setLeftDrawablePadding  | 设置图片相距文字的间隔                                    |
     | setRightImage           | 设置标题栏右部图片                                        |
     | setRightText            | 设置标题栏右部文字（如果设置了RightImage，则只显示Image） |
     | setRightTextColor       | 设置标题栏右部文字颜色                                    |
     | setRightTextSize        | 设置标题栏右部文字大小                                    |
     | setRightDrawableLeft    | 配合右部文字使用，将图片放置到文字的左侧                  |
     | setRightDrawableRight   | 配合右部文字使用，将图片放置到文字的右侧                  |
     | setRightDrawablePadding | 设置图片相距文字的间隔                                    |
     | setBackGroundColor      | 设置标题蓝背景颜色                                        |
     | setLeftClickListener    | 设置左部区域点击事件回调                                  |
     | setRightClickListener   | 设置右部区域点击事件回调                                  |
     | setTitleClickListener   | 设置中间标题区域点击事件回调                              |

     

- ### 倒计时按钮控件TimerButton

  > 是一个专门处理倒计时的小UI控件，可以设置倒计时的时间，倒计时结束的回调等。

  **使用方式：**

  ```xml
  <com.luwei.base.widget.TimerButton
      android:id="@+id/timerbutton"
      android:layout_width="100dp"
      android:layout_height="27dp"
      android:background="@drawable/user_selector_enabled_f92b2b_else_cccccc_13"
      android:gravity="center"
      android:text="@string/user_sent_code"
      android:textColor="@drawable/user_selector_enabled_f92b2b_else_ffffff"
      android:textSize="12sp"          
      app:finishedText="@string/user_resent_code"//倒计时结束时的文段
      app:formatText="%ds"//倒计时秒数的显示格式，比如是xxs还是xx秒，x分x秒等
      app:time="60" //倒计时的秒数/>
  ```

  ```java
  mTimerbutton.setCallback(new TimerButton.Callback() {
      @Override
      public void onFinish(TimerButton button) {
          button.setEnabled(true);
      }
  
      @Override
      public void onTick(TimerButton button, long millisUntilFinished) {
  
      }
  });
  ```

  API

  | 方法名               | 功能                                             |
  | -------------------- | ------------------------------------------------ |
  | isStated             | 返回是否开启倒计时的布尔值                       |
  | resetStatus          | 恢复倒计时为最初状态                             |
  | start                | 开启倒计时                                       |
  | stop                 | 停止倒计时                                       |
  | setCallback          | 设置倒计时监听，包含结束时监听和在倒计时中的监听 |
  | setFormatText        | 设置倒计时读秒文字格式                           |
  | setFinishedText      | 设置倒计时结束文字                               |
  | setStartedColor      | 设置倒计时启动后的文字颜色                       |
  | setStartedBackground | 设置倒计时启动后的背景颜色                       |
  | setTime              | 设置倒计时秒数                                   |

  

- ### 图片预览ImagePreview

  > 待补充

- ### 图片选择ImageSelector

  > 一个简单的图片选择控件，可以单选、多选、以及打开摄像头拍摄新照片。

  打开图片需要获取手机存储权限，可以使用了base库内的RxPermission来获取权限，打开图片选择Activity中需要用到startForResult，这里通过前面封装的SimpleForResult来实现。

  打开图库有几种方式：

  1. 在`onActivityResult`中处理图片选择结果

     ```java
     public void openImageSelect() {
             // todo：打开摄像头、读写权限
             // todo:在 AndroidManifest.xml 中添加  <activity android:name="me.nereo.image_selector.MultiImageSelectorActivity"/>
             MultiImageSelector.create(this)
                     .single()
                     .showCamera(true)
                     .start(this, REQUEST_SELECT_CODE);
         }
     ```

  2. 选择单个图片、无需在`onActivityResult`中处理图片选择结果

     ```java
     public void openImageSelect2() {
         // todo：打开摄像头、读写权限
         // todo:在 AndroidManifest.xml 中添加  <activity android:name="me.nereo.image_selector.MultiImageSelectorActivity"/>
       simpleForResult.startForResult(PictureIntentHelper.getSelectSingleImageIntent(this, false))
                 .subscribe(resultInfo -> {
                     if (resultInfo.getResultCode() == RESULT_OK) {
                         List<String> urls = resultInfo.getData().getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                         if (urls != null && urls.size() > 0) {
                             mHeadUrl = urls.get(0);
                             ImageLoaderUtils.getInstance().loadCircleImage(this, ivHeader, mHeadUrl);
                         }
                     }
                 });
     }
     ```

  3. 选择多个图片

     ```java
     public void openImageSelect3() {
         // todo：打开摄像头、读写权限
         // todo:在 AndroidManifest.xml 中添加  <activity android:name="me.nereo.image_selector.MultiImageSelectorActivity"/>
         simpleForResult.startForResult(PictureIntentHelper.getSelectMultiImageIntent(this, false,3))
                 .subscribe(resultInfo -> {
                     if (resultInfo.getResultCode() == RESULT_OK) {
                         List<String> urls = resultInfo.getData().getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                         if (urls != null && urls.size() > 0) {
                             mHeadUrl = urls.get(0);
                             ImageLoaderUtils.getInstance().loadCircleImage(this, ivHeader, mHeadUrl);
                         }
                     }
                 });
     }
     ```

  4. 打开摄像头拍摄照片并裁减

  ```java
  private void openCamera() {
      try {
          mTmpFile = FileUtils.createTmpFile(this);
      } catch (IOException e) {
          e.printStackTrace();
      }
      simpleForResult.startForResult(PictureIntentHelper.getOpenCameraIntent(this, mTmpFile))
              .subscribe((resultInfo -> {
                  if (resultInfo.getResultCode() == RESULT_OK) {
                      mHeadUrl = mTmpFile.getAbsolutePath();
                      ImageLoaderUtils.getInstance().loadCircleImage(this, ivHeader, mHeadUrl);
                      // 裁剪(如果没有要求可裁剪，也可以不要）
                      startPictureZoom(mTmpFile);
                  }
              }));
  }
  
  public void startPictureZoom(File srcFile) {
      try {
          mZoomOutFile = FileUtils.createTmpFile(this);
      } catch (IOException e) {
          e.printStackTrace();
      }
      simpleForResult.startForResult(PictureIntentHelper.getPhotoZoomIntent(this, srcFile, mZoomOutFile))
              .subscribe(resultInfo -> {
                  if (mZoomOutFile != null && mZoomOutFile.exists()) {
                      mHeadUrl = mZoomOutFile.getAbsolutePath();
                      ImageLoaderUtils.getInstance().loadCircleImage(this, ivHeader, mHeadUrl);
                      Log.i(TAG, "startPictureZoom: ");
                  }
              });
  }
  ```

  

- ### 横幅广告Banner

  > 待添加

- ### 友盟分享集成

  > 待补充完善，可以先查看[Android 友盟社会化分享的集成与封装](https://juejin.im/post/5c09e118f265da61483b6939)

