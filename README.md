
<div dir="rtl">
<b>هیلت چی هست؟</b>

هیلت به کتابخونه تزریق وابستگیه که گوگل جدیدا اراِئه کرده اول اینکه یه روشه استاندارد و آسون برای تامین وابستگی ها و تزریق اونها توی پروژه اندروید هست دوم اینکه برای هر کامپوننت اندرویدی یه کانتینر در نظر میگیره و چرخه عمر اون رو خودش خودکار مدیریت میکنه.

<b>چه مزایایی داره؟</b>

طبق گفته ی خود سایت dagger.dev با استفاده از هیلت boilertplate کاهش پیدا میکنه منظورش اینه که بدون اینکه نگرانی در مورد راه اندازی دگر رو داشته باشید به جاش راحتتر روی تعاریف و کاربردها متمرکز بشید، قابلیت استفاده مجدد از کد ، استفاده و پیاده سازی آسون ، از بین بردن پیچیدگی های تست با دگر ، با استانداردهای تعریف شده دیگه درگیر پیچیدگی های تعریف و ایجاد و استفاده از کامپوننت و ماژول مخصوصا توی کلاسهای پیچیده نمیشید .

<b>وابستگی های پروژه :</b>

برای شروع دیپندنسی های زیر رو به build.gradle سطح پروژه به پروژه اضافه میکنیم:

</div>
<pre>
  <code>
dependencies {
def lifecycle_version = "2.3.0-alpha05"
def material_version = "1.1.0"
def room_version = "2.2.5"
def hilt_jetpack = "1.0.0-alpha01"

// Room
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"
// Activity Ktx for by viewModels()
implementation "androidx.activity:activity-ktx:1.1.0"
//Dagger - Hilt
implementation "com.google.dagger:hilt-android:$hilt_version"
kapt "com.google.dagger:hilt-android-compiler:$hilt_version"
implementation "androidx.hilt:hilt-lifecycle-viewmodel:$hilt_jetpack"
kapt "androidx.hilt:hilt-compiler:$hilt_jetpack"
// coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6"
// ViewModel
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0-alpha05'
// LiveData
implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
}
  </code>
</pre>
<div dir="rtl">
خب این دو تا پلاگین رو هم توی گریدل سطح app اضافه کنیم:
</div>
<pre>
  <code>
apply plugin: 'kotlin-kapt'
apply plugin: 'dagger.hilt.android.plugin'  </code>
</pre>
<div dir="rtl">و این دیپندنسی هم به گریدل سطح ماژول باید اضافه کنیم و بعد گریدل رو سینک کنیم:
</div>
<pre><code>classpath "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"</code></pre>
<div dir="rtl">
میخوایم تو این پروژه فرضی با استفاده از room یه سری دیتا ذخیره کنیم و لیست کنیم که وظیفه ی تامین و تزریق وابستگی ها با hilt باشه.

<b>قدم اول:</b>

یک کلاس انتیتی بسازیم که اسمش user باشه و id , name , familiy داخلش داشته باشه:

![1](https://user-images.githubusercontent.com/9361634/86300947-bc71cb80-bc19-11ea-97cc-e87794c7bed9.png)


</div>
<div dir="rtl">
<b>قدم دوم:</b>

اینترفیس DAO مربوط به کلاس بالا رو میسازیم که شامل دو تا متد insert و getAllUsers هست:

![2](https://user-images.githubusercontent.com/9361634/86300993-ecb96a00-bc19-11ea-8495-04071bd52f2c.png)
</div>
<div dir="rtl">

<b>قدم سوم:</b>

کلاس ابسترکتی بسازیم که از RoomDatabase ارث بری میکنه و یه متد بدون بدنه داره که خروجیش از نوع UserDao هست :

![3](https://user-images.githubusercontent.com/9361634/86301192-6d786600-bc1a-11ea-9c2a-c1f15116af19.png)

</div>
<div dir="rtl">تا الان بخشهای مربوط به room رو انجام دادیم حالا بریم سر پیاده سازی Hilt :

<b>قدم چهارم:</b>

ما نیاز داریم یه کلاسی داشته باشیم که از Application ارث بری میکنه و انوتیشن HiltAndroidApp بالای سرش هست :

![4](https://user-images.githubusercontent.com/9361634/86301211-7e28dc00-bc1a-11ea-814c-87f819bb35ae.png)

</div>
<div dir="rtl">
<b>قدم پنجم:</b>

این کلاس Application رو توی مانیفست هم معرفی میکنیم</div>
<pre><code>android:name=".HiltApplication"</code></pre>

<div dir="rtl">
<b>قدم ششم:</b>

ما باید یه آبجکت به نام DbModule بسازیم برای Hilt بسیازیم تا به hilt بگیم که چطور یه اینستنس از کلاس AppDb بسازه تا مجبور نشیم وقتی از AppDb میخوایم استفاده کنیم اون رو new کنیم و وابستگی هاشو تامین کنیم خود Hilt برای ما زحمتشو بکشه برای اینکار باید این کلاس رو با انوتیت Module تزیین کنیم یعنی به Hilt بفهمونیم این کلاس یه ماژول هست دومین کاری که باید بکنیم اینه که انوتیشن installIn رو بالاسر این کلاس قراار بدیم تا به هیلت بفهمونیم که این ماژول رو روی کدوم کامپوننت سوار کنه ورودی هم بهش ApplicationContext رو میدیم حالا توی این کلاس قراره چخبر باشه؟ قراره یه متد ابسترکت بسازیم که خروجیش AppDb هست و داخل این متد با استفاده از بیلدر Room کلاس AppDb رو تولید کنیم و برگردونیم این متد هم قراره بالاسرش انوتیشن provides داشته باشه که به هیلت بفهمونه هرجا نیاز به کلاس AppDb داشتی چطور باید تولیدش کنی نهایتا این کلاسه هم این شکلی میشه:

![6](https://user-images.githubusercontent.com/9361634/86301229-94369c80-bc1a-11ea-898c-e95884d203bf.png)

</div>

<div dir="rtl">
<b>قدم هفتم:</b>

حالا باید یه کلاس ریپازیتوری بسازیم که توش یه متد SaveUser داشته باشه که پارامتر ورودی اون از نوع User باشه و داخل ان متد با استفاده از کلاس UserDao متد insert اون کلاس رو صدا بزنیم و اطلاعات user رو که پاس دادیم ذخیره کنیم ،برای اینکه به userDao دسترسی داشته باشیم توی متد سازنده همین کلاس appDb رو میگیریم تا با استفاده از متد userDao بهش دسترسی پیدا کنیم. قبل از کانستراکتور کلاس ریپازیتوریمون یه انوتیشن inject قرار میدیم تا به هیلت بفهمونیم ما باید از اینستنس این کلاس استفاده کنیم.

![7](https://user-images.githubusercontent.com/9361634/86301248-9e589b00-bc1a-11ea-91b8-18f76b357c95.png)

</div>
<div dir="rtl">
<b>قدم هشتم:</b>

یه کلاس ویومدل بسازیم و کلاس UserRepository که بالا ساختیم رو اینجکت کنیم داخلش این کلاس باید قبل از متدسازنده ش یه انوتیشن ViewModelInject بذاریم و ورودی کانستراکتورش باید UserRepository باشه این انوتیشن به هیلت میفهمون که UserRepository رو اینجکت کن داخل این ویومدل داخل ویدومدل هم یه متد save میذاریم که با استفاده از coroutines و MutableLiveData یک user رو به متد save ریپازیتوریمون ارسال میکنیم و استتوس true برمیگردونیم به اکتیویتی ای که قراره متصل بشه به این ویومدل ما.

![8](https://user-images.githubusercontent.com/9361634/86301265-a9133000-bc1a-11ea-907d-cb3229647fa1.png)

</div>
<div dir="rtl">
<b>قدم نهم:</b>

یه اکتیویتی میسازیم که بالاسرش انوتیشن AndroidEntryPoint رو قرار دادیم این انوتیشن وظیفه ش اینه که به هیلت بفهمونه که وابستگی های مربوط به این کامپوننتی که بالاسرش از این انوتیشن استفاده شده رو فراهم کن .این اکتیویتی متصل شده به ویومدلی که بالا ساختیم و اطلاعات ورودی user رو با استفاده از دیتابایندینگ از xml دریافت میکنه و وقتی دکمه save زده شده به متد save ویومدل ما ارسال میشه.

![9](https://user-images.githubusercontent.com/9361634/86301275-b0d2d480-bc1a-11ea-9d9e-4c41c5d4364a.png)

</div>
<div dir="rtl">
کارمون تموم شد :)

</div>
<a href="https://virgool.io/@ftml/%D9%BE%DB%8C%D8%A7%D8%AF%D9%87-%D8%B3%D8%A7%D8%B2%DB%8C-hilt-%D8%AF%D8%B1-%DB%8C%DA%A9-%D9%BE%D8%B1%D9%88%DA%98%D9%87-%D8%A7%D9%86%D8%AF%D8%B1%D9%88%DB%8C%D8%AF%DB%8C-xzyrq3jyd8ef" title="virgool">virgool</a>
