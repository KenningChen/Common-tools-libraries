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
		api 'com.github.KenningChen:WltUtil:util-Beta-0.2.5'//最新版本号
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

日期:

常规的日期处理,详见`com.kenning.kcutil.utils.date`文件夹下的文件

其他内容自行查看
