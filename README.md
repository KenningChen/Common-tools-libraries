# KCUtil工具库说明

> 公司项目中使用比较频繁的一些小操作,个人整理了一下,进行了简单的封装,以方便后续的使用
后面会根据使用情况进行调整,对话框写的会比较乱点,后期会整理
> 

相关内容:

- String的值是数 或 其他数值类型(如:Int,Double等) 的 加减乘除算法 以及字符串的一些处理
- 对话框
- 日期选择
- 日期(时间)的格式以及日期的常用的使用方式,比如日期时间比较等
- 其他的一些小工具及自定义控件

使用:

```groovy
dependencies {
		api 'com.github.KenningChen:Common-tools-libraries:kc_util-1.0.5'//最新版本号
}
```

需要在`Application`中添加`KCUtil.initKCUtil(this)`

加减乘除:

```kotlin
"5.123".CHENG(3).KeepPoint(2)//5.123 * 3 结果保留2位小数位数
"5.123".CHENG(3,point=2)//5.123 * 3 结果保留2位小数位数
```

日历选择:

```kotlin
  
DatePickerBuilder(this)
	.setBeginDate(DateExtendUtil.getCurrentDate())
  .setEndDate(DateExtendUtil.getCurrentDate())//setSingle(false)时,该方法生效
  .setSingle(true)//是否显示两个日期选择
  .setNeedChangeState(true)// 日历选择 按日 按月切换按钮
  .setRequestCode(111)//返回code(需要在activity/fragment 实现 IPickerListener接口)
  .setLoaction(PickerControl.ShowLocation.BOTTOM)//日历显示的位置
  .start(R.id.fcvMain)//location为PickerControl.ShowLocation.BOTTOM时,只需要start(),显示在页面底部
//PickerControl.ShowLocation.TOP时,start需要传入控件id,显示在该控件的下方
```

对话框:

```kotlin
EasyDialog(context).setContentMsg("xxxxx").build()//简单提示
//其他方法
setButtonMode(vararg modes: ButtonMode)//设置底部按钮
setBottomOption(@IntRange(from=0,to = 1) option:Int)//设置按钮排列方式 0横向 1纵向
withPrompt(index: Int = 0,promptMsg:String, extendKey: String = "")//设置不再提示的文字及控件
//index 为不再提示后下次需要执行的按钮(下标)的时间
needNoNButtons(non: Boolean)//是否需要底部按钮
cancelAble(cancel: Boolean)//是否允许点击外部区域取消dialog
keyCancelAble(cancel: Boolean)//是否允许点击设备返回(手势)取消dialog
showPicture(pic:Int)//在title上方显示图片信息
setAdapter(adapter: RecyclerView.Adapter<*>)//设置内容适配器
setArray(array: Array<String?>, itemClick: (Int) -> Unit)//设置内容列表及item点击事件
//其他查看EasyDialog类中方法
```

`NumBox`类: 
用来对数值形式的数据封装并进行比较大小, 如

```kotlin
NumBox("10") > NumBox(5) 
```

自定义控件,扫描控件:`ScanViewGroup`

设置内置扫描跳转类需要在`application`中设置, 如下:

```Kotlin
KCUtil.setScanClassPath(ScanTextActivity::class.java)// 目标类需实现接口 ImplScanResult

示例:
class ScanTextActivity:BaseActivity(), ImplScanResult {
	//省略其他代码
    ....
    //

    // 此处模式识别成功
    override fun onBackPressedSupport() {
        setScanResult("dslkajflkadsjf\n")// 识别成功后, 调用该方法
        finish()
    }

    // 返回一个CameraScanBean示例
    override fun setScanResult(result: String):CameraScanBean {
        return CameraScanBean(result)
    }
}
```

| 属性 | 描述                                                         | 方法/参数                                                    |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
|      | backgroundColor                                              | 背景色                                                       |
|      | borderColor                                                  | 外边框颜色                                                   |
|      | borderWidth                                                  | 外边框宽度(线宽)                                             |
|      | cornerRadius                                                 | 外边框圆角大小                                               |
|      | textSize                                                     | 文本大小                                                     |
|      | hint                                                         | EditText提示文本                                             |
|      | titleContent                                                 | EditText的title描述文本                                      |
|      | autoClear                                                    | 自动清空EditText文本内容                                     |
|      | onlyEditText                                                 | 该组合控件仅包含一个EditText                                 |
|      | asTextview                                                   | EditText作为TextView, 仅做显示                               |
|      | showDeleteImg                                                | 是否显示删除图标                                             |
|      | showRightImg                                                 | 是否显示最右侧的图标                                         |
|      | showScanImg                                                  | 是否显示扫描图标                                             |
|      | showSearchImg                                                | 是否显示搜索标志的图标                                       |
|      | showShape                                                    | 是否显示外边框                                               |
|      | lightContent                                                 | 是否将文本和图标都设置成白色显示                             |
|      | rightImage                                                   | 设置最右侧的图标                                             |
|      | groupTheme                                                   | 快速设置该组合控件的背景<br />light: 背景色为白色, 显示外边框<br />dark: 背景为浅灰色, 不显示外边框 |
|      | viewType                                                     | 组合控件的样式<br />scan:  扫描模式, 默认含有扫描图标<br />selector: 选择模式, 默认含有最右边的默认箭头图标<br />all: 组合模式, 默认含有扫描和最右的图标 |
| 方法 |                                                              |                                                              |
|      | setOnCameraListener(listener: () -> Unit)                    | 扫描图标的点击事件(自定义监听)                               |
|      | setOnScanCallback(callback: (String) -> Unit)                | 设置扫描成功后获取处理后的内容后的回调事件                   |
|      | setOnDeleteListener(listener: () -> Unit)                    | 清除事件                                                     |
|      | requestEditFocus()                                           | EditText获取焦点                                             |
|      | setOnTouchEvent(touchEvent: (MotionEvent) -> Unit)           | 设置EditText的Touch事件                                      |
|      | setOnClickListener(sameRightView: Boolean = true, listener: (View) -> Unit) | EditText作为TextView使用时的点击事件<br />sameRightView: 是否该事件同时对右更多（自定义）图标生效 默认生效 |
|      | clear(callback: (() -> Unit)? = null)                        | 清空操作                                                     |
|      | setRightImageClickEvent(clickEvent: () -> Unit)              | 最右边图片的点击事件                                         |
|      | setTitleContent(content: String)                             | 设置Title提示                                                |
|      | setHint(hint: String)                                        | 编辑框的hint内容                                             |
|      | getText()                                                    | 获取编辑框文本内容                                           |



日期:

常规的日期处理,详见`com.kenning.kcutil.utils.date`文件夹下的文件

其他内容自行查看
