# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\yt_works\program_files\adt-bundle-windows-x86_64-20140321\adt-bundle-windows-x86_64-20140321\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript inter
# class:
#-keepclassmembers class fqcn.of.javascript.inter.for.webview {
#   public *;
#}
#指定代码的压缩级别
-optimizationpasses 5
#是否使用大小写混合
-dontusemixedcaseclassnames
 #不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#不优化输入的类文件
-dontoptimize
#混淆时是否做预校验
-dontpreverify
#混淆时是否记录日志
-verbose
#保护注解
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-dontwarn android.support.**
-dontwarn android.support.v4.**
-keep class android.support.**{*;}
#-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.app.**
-keepattributes InnerClasses,Signature

#记录生成的日志数据,gradle build时在本项目根目录输出start
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从apk中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#记录生成的日志数据,gradle build时在本项目根目录输出ebd


#混淆保护自己项目的部分代码以及引用的第三方jar包start
#afinal start
-dontwarn net.tsz.afinal.**
-keep class net.tsz.afinal.** { *; }
-keep public class * extends net.tsz.afinal.**
-keep public interface net.tsz.afinal.** {*;}
#afinal end
#支付宝混淆start
-dontwarn com.alipay.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
#支付宝混淆end
#高德地图sdk混淆start
-dontnote com.loc.**
#3D 地图
-dontwarn com.amap.**
-dontwarn com.autonavi.**
-keep class com.amap.api.**{*;}
-keep class com.autonavi.**{*;}
#-keep class com.a.a.**{*;}
#Location
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
#Service
-keep class com.amap.api.services.**{*;}
#高德地图sdk混淆end

#百度地图混淆start
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
#百度地图混淆end

#fastjson混淆start
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keep class com.google.gson** { *; }
-keepclassmembers class com.google.gson** {
   *;
}
#fastjson混淆end
#httpclicent混淆start
#-dontwarn org.apache.http.**
#-keep class org.apache.http.** { *; }
#httpclicent混淆end
#JPush混淆start
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}
#JPush混淆end
#微信混淆start
-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.** { *; }
#微信混淆end
#阿里云sdk混淆start
-keep class com.alibaba.sdk.android.** { *; }
#阿里云sdk混淆end
#友盟混淆start
#-dontwarn com.umeng.**
#-keep class com.umeng.** { *; }
#-keep class com.umeng.onlineconfig.** { *; }
#-keep public class * extends com.umeng.**
-keep class com.umeng.commonsdk.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers class * {
    public <methods>;
}
-keep public class com.jlhm.personal.R$*{
   public static final int *;
}
-keep class com.umeng.onlineconfig.OnlineConfigAgent {
   public <fields>;
   public <methods>;
}
-keep class com.umeng.onlineconfig.OnlineConfigLog {
   public <fields>;
   public <methods>;
}
-keep interface com.umeng.onlineconfig.UmengOnlineConfigureListener {
   public <methods>;
}
#友盟混淆end
#融云start
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepattributes Exceptions,InnerClasses
-keep class io.rong.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent{*;}
-dontnote **Unsafe
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
 public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.eclipse.jdt.annotation.**
-keep class com.ultrapower.** {*;}
-dontwarn uk.co.**
#融云end
#EventBus混淆start
-keepclassmembers class ** {
    public void onEvent*(**);
}
#EventBus混淆end
#sharesdk混淆start
-dontnote com.mob.**
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn **.R$*
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-keep class com.bytedance.**{*;}
#sharesdk混淆end
#V7包混淆start
-dontwarn android.support.v7.**
-keep class android.support.v7.**{*;}
-keep class !android.support.v7.view.menu.*MenuBuilder*, android.support.v7.** { *; }
-keep interface android.support.v7.* { *; }
#V7包混淆end
#butterknife混淆start
-dontwarn butterknife.internal.**
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#butterknife混淆end
#扫描二维码混淆start
-dontwarn com.google.zxing.**
-keep class com.google.zxing.**{*;}
#扫描二维码混淆end
#bugly混淆start
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#bugly混淆end
#design混淆start
-dontwarn android.support.design.**
-keep class android.support.design.**{*;}
#design混淆end
#leakcanary-analyzer混淆start
-dontwarn android.app.Notification
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.leakcanary.**{*;}
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }
#leakcanary-analyzer混淆end
#volley混淆start
-dontwarn com.android.volley.**
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }
#volley混淆end
#glide混淆start
#-dontwarn com.github.bumptech.glide.**
#-keep class com.github.bumptech.glide.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#glide混淆end
#banner混淆start
-keep class com.youth.banner.** {
    *;
 }
#banner混淆end
#nineoldandroids混淆start
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.**{*;}
#nineoldandroids混淆end
#permissiondispatcher混淆start
-dontwarn permissions.dispatcher.**
-keep class permissions.dispatcher.**{*;}
#permissiondispatcher混淆end
#pickerview混淆start
-dontwarn com.bigkoo.pickerview.**
-keep class com.bigkoo.pickerview.**{*;}
#pickerview混淆end
#rebound混淆start
-dontwarn com.facebook.rebound.**
-keep class com.facebook.rebound.**{*;}
#rebound混淆end
#recyclerview-animators混淆start
-dontwarn jp.wasabeef.recyclerview.**
-keep class jp.wasabeef.recyclerview.**{*;}
#recyclerview-animators混淆end
#RinnedRecyclerView混淆start
-dontwarn com.kiguruming.recyclerview.itemdecoration.**
-keep class com.kiguruming.recyclerview.itemdecoration.**{*;}
#RinnedRecyclerView混淆end
#support-annotation混淆start
-dontwarn android.support.annotation.**
-keep class android.support.annotation.**{*;}
#support-annotation混淆end
#ultra-ptr混淆start
#-dontwarn class in.srain.cube.image.ImageProvider
#-dontwarn class android.graphics.Bitmap
#-ignorewarnings class in.srain.cube.image.ImageProvider
#-ignorewarnings class android.graphics.Bitmap
-keep class in.srain.**{*;}
#ultra-ptr混淆start
#universal-image-loader混淆start
-dontwarn com.nostra13.**
-keep class com.nostra13.**{*;}
#universal-image-loader混淆end
#DbUtils混淆start
-keep class qubuyer.bean.**{*;}
-keep class qubuyer.provider.**{*;}
-keep class com.qubuyer.okhttputil.helper.**{*;}
-keep class com.qubuyer.business.order.view.OrderCommentRatingView{*;}
-keep class com.qubuyer.business.good.view.GoodDetailCommentRatingView{*;}
#DbUtils混淆end
#科大讯飞语音混淆start
-dontwarn com.iflytek.**
-dontwarn com.iflytek.speech.**
-keep class com.iflytek.**{*;}
#科大讯飞语音混淆end
#Logger日志混淆start
-dontwarn com.orhanobut.logger.**
-keep class com.orhanobut.logger.**{*;}
#Logger日志混淆end
#混淆保护自己项目的部分代码以及引用的第三方jar包end
#银联支付混淆start
-dontwarn com.unionpay.**
-keep class com.unionpay.**{*;}
-dontwarn cn.gov.pbc.tsm.client.mobile.android.bank.service.**
-keep class cn.gov.pbc.tsm.client.mobile.android.bank.service.**{*;}
-dontwarn com.UCMobile.PayPlugin.**
-keep class com.UCMobile.PayPlugin.**{*;}
#银联支付混淆end

# 连连Demo混淆keep规则，如果使用了Demo中工具包com.yintong.pay.utils下面的类，请对应添加keep规则，否则混下会包签名错误
-keep public class com.yintong.pay.utils.** {
    <fields>;
    <methods>;
}
# 连连混淆keep规则，请添加
-keep class com.yintong.secure.activityproxy.PayIntro$LLJavascriptInterface{*;}

# 连连混淆keep规则
-keep public class com.yintong.** {
    <fields>;
    <methods>;
}
#Glide start
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#Glide end

# utilcode start
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**
# utilcode end


#保持native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    public void get*(...);
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#保持枚举enum类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保持 Parcelable 不被混淆
-keepclassmembers class * implements android.os.Parcelable {
    public static android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

-keep public class * implements java.io.Serializable {
    public *;
}

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
    private <fields>;
}
-keepattributes SourceFile,LineNumberTable
-ignorewarnings

# baiduwallet  start
-keep interface com.baidu.wallet.core.NoProguard
-keep public class * implements com.baidu.wallet.core.NoProguard {
  	public protected *;
}
-keep class com.baidu.android.lbspay.** { *; }
-keep class com.baidu.android.pay.** { *; }
-keep class com.baidu.balance.** { *; }
-keep class com.baidu.fastpay.** { *; }
#如果接入nfc需要增加nfc的混淆配置 nfc  start
-keep class com.baidu.nfc.** { *; }
#nfc  end
-keep class com.baidu.traffic.** { *; }
-keep class com.baidu.scancode.** { *; }
-keep class com.baidu.apollon.**{*;}
-keep class com.baidu.wallet.remotepay.**{*;}
-keep class com.baidu.home.** { *; }
-keep class com.baidu.paysdk.** { *; }
-keep class com.baidu.personal.** { *; }
-keep class com.baidu.seclab.sps.** { *; }
-keep class com.baidu.transfer.** { *; }
-keep class com.baidu.wallet.** { *; }
-keep class com.baidu.BankCardProcessing
-keep class com.baidu.BCResult
-dontwarn com.baidu.searchbox.plugin.api.**
# passsdk start
-keep class com.baidu.sapi2.** {*;}
-keepattributes JavascriptInterface
-keepattributes *Annotation*
#passsdk end

#bankcard start
-keep class com.baidu.bankdetection.** {*;} 
#bankcard end

#fingerprint start
-keep class com.lenovo.appsdk.** { *;}
-keep class com.lenovo.fido.** { *;}
-keep class com.samsung.android.sdk.** { *;}
#fingerprint end
# baiduwallet end

#takephoto start
-keep class com.jph.takephoto.** { *; }
-dontwarn com.jph.takephoto.**

-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**

-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**1
#takepohot end
