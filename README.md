# android-wordpress-rest-api
Integrate your wordpress.com website into Android app

## การใช้งานเบื้้องต้น

โค้ดชุดนี้จะผูกข้อมูลอยู่กับเว็บ eieiblog.wordpress.com ที่เป็นเว็บทดสอบ ถ้าจะนำไปใช้ก็ให้เปลี่ยนเป็นของตัวเอง สามารถไปเปลี่ยนได้ที่คลาส App ตรงสตริง "../100381386 ให้นำชื่อ หรือ id ของเว็บตนเองมาใส่แทนตัวเลข

ในกรณีที่ต้องการใช้เว็บไซต์ที่ใช้ wordpress.org ให้ทำการตัดตั้งปลั๊กอิน Jetpack ของ Automattic และลิงก์ Plugin ให้เรียบร้อยหลังจากนั้นเปิดใช้งาน JSON API ใน Jetpack Setting ก็สามารถใช้งานร่วมกับแอปนี้ได้เช่นกัน

อ่าน doc เพิ่มเติมที่ https://developer.wordpress.com/docs/api/1.1/get/sites/%24site/posts/

## คำอธิบายเพิ่มเติม

โค้ดโปรเจกต์ตอนนี้สามารถดึงข้อมูลรายการโพสทั้งหมดมาแสดงได้ และกดที่รายการเพื่อแสดงโพสแบบละเอียดได้ (ยังไม่ได้ทำ Comment) ส่วนเสริมอื่นๆ ก็จะมี การ Swipe Down to Refresh ที่สามารถโหลดโพสเพิ่มเติมได้ (วิธีใช้ก็แค่เปิดแอปแล้วเอานิ้วจิ้ม + ดึงให้มีลูกศรหมุนออกมา เหมือนเวลารีเฟรชในแอปเฟซบุ๊ค) แต่โค้ดมันอาจจะซับซ้อนไปสักหน่อย แถมผูกกับหลายๆ ไลบรารี่ เอาไปดูก็น่าจะงงกันเลย (เดี๋ยวจะพยายามพิมพ์คำอธิบายโค้ดแล้วอัพเดทเพิ่มเติม)

ดูเพิ่มเติมได้ที่

https://www.bignerdranch.com/blog/implementing-swipe-to-refresh/

http://www.androidhive.info/2015/05/android-swipe-down-to-refresh-listview-tutorial/

--

การแสดงผลตอนนี้รองรับเพียงแนวตั้งเท่านั้น ถ้าต้องการให้รองรับแนวนอนด้วยก็แค่ไปเซ็ต orientation ใน Manifest แต่สิ่งที่จะตามมาคือ เวลาหมุนจอ Activity จะถูกทำลายทิ้งและสร้างใหม่ ทำให้จำเป็นต้อง Call HTTP GET หลายครั้ง (เปลืองเน็ต) แต่ก็มีตัวช่วยเพียงแค่ใช้ savedInstanceState ให้เป็น (เดี๋ยวจะทำให้ภายหลังครับ) ซึ่งเจ้า savedInstanceState เราสามารถบันทึกข้อมูลที่ต้องการไว้ได้แม้ Activity จะถูกทำลาย การบันทึกข้อมูลนั้นจะอยู่ในรูปแบบจำกัดอย่าง String, int, float, boolean, byte ง่ายๆ คือใช้ได้กับตัวแปรพื้นฐาน กับอ็อบเจกต์อีก 1 ประเภทคือ Parcelable ซึ่งการใช้ Parcelable ก็ไม่ยากเสิร์ชหาในเน็ตแปปเดียวก็เจอ จะหาไลบรารี่มาช่วยก็มีเยอะแยะเลยจ้า

ดูเพิ่มเติมได้ที่

http://developer.android.com/training/basics/activity-lifecycle/recreating.html

http://developer.android.com/reference/android/os/Parcelable.html

http://www.artit-k.com/dev-parceler-library-for-android/

##เมื่อพูดถึงไลบรารี่ที่ใช้ โปรเจกต์นี้ใช้อะไรบ้าง ?

### 1) com.android.support:appcompat-v7

เมื่อสร้างโปรเจกต์รองรับเวอร์ชันเก่าๆ Android Studio จะใส่ไลบรารี่นี้มาให้อัตโนมัติ เพื่อให้แอปของเรารองรับเวอร์ชันเก่า ๆ  ซึ่งในที่นี้เลือกขั้นต่ำสุดคือประมาณเวอร์ชัน 4 แล้วเราใช้ตรงไหน ? เราจะใช้ที่ Activity ครับ เวลาสร้าง Activity จะสืบทอดคลาสจาก AppCompatActivity แทน Activity ปกติ ถ้าเราต้องการใช้งาน ActionBar จำเป็นต้องเรียก getSupportActionBar(); แทน getActionBar();
ในโปรเจกต์นี้จะมี 2 คลาสที่ใช้คือ 
- FeedActivity : เป็น Home Activity ที่จะดึงโพสมาจาก Wordpress
- PostActivity : เป็น Activity สำหรับแสดงข้อมูลของโพสทั้งหมด รวมถึง Comment ด้วย (แต่ตอนนี้ยังไม่เสร็จ)

### 2) com.android.support:recyclerview-v7

ปกติแล้วเราจะใช้ ListView หรือ GridView แต่ในเวอร์ชันใหม่ๆ ได้มีการเพิ่ม RecyclerView มาแทน ListView และ GridView เพื่อบังคับให้นักพัฒนาต้องเขียน Adapter ตามคอนเซ็ปต์ Object Pooling และใช้ ViewHolder ซึ่งวิธีใช้จะซับซ้อนขึ้นเล็กน้อย แต่การใช้งานค่อนข้าง Flexible เลยทีเดียว
ในโปรเจกต์นี้จะมี 1 คลาสที่ใช้คือ FeedActivity + activity_feed.xml

ดูเพิ่มเติมได้ที่

http://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465

http://nuuneoi.com/blog/blog.php?read_id=758

### 3) com.github.ksoichiro:andorid-observablescrollview

ในการแสดงข้อมูลของโพสต้องการให้ซ่อน ActionBar เวลา Scroll เพื่ออ่านเนื้อหา โดยการรับอีเว้นท์ Scroll นั้นจำเป็นจะต้องนำไปฝังการตรวจสอบไว้กับพวก ScrollView หรือ ListView ในที่นี้ฝังไว้กับ RecyclerView ซึ่งเวลาจะฝังลงใน RecyclerView จำเป็นต้องเปลี่ยนไปใช้ ObservableRecyclerView แทน การเขียนโค้ดภายใน Adapter ของ ObservableRecyclerView จะเหมือนกับ RecyclerView ทุกอย่าง
ในโปรเจกต์นี้จะมี 1 คลาสที่ใช้คือ PostActivity + activity_post.xml

ดูเพิ่มเติมได้ที่ https://github.com/ksoichiro/Android-ObservableScrollView

### 4) com.android.support:cardview-v7

CardView มีคุณสมบัติคล้าย FrameLayout ที่จะซ้อน View เป็น Layer แต่จะเพิ่มความสามารถเข้ามา เช่น การใส่เงา การทำขอบมน เมื่อติดตั้งแล้วสามารถใช้ใน xml ได้เลย
ในโปรเจกต์นี้จะมี 1 ไฟล์ที่ใช้คือ layout_feed_item.xml

ดูเพิ่มได้ที่ 

https://developer.android.com/training/material/lists-cards.html

https://components.xamarin.com/gettingstarted/xamandroidsupportv7cardview

### 5) com.mcxiaoke.volley:library

เป็นไลบรารี่สำหรับงาน HTTP Request พวก GET, POST ในที่นี้นำมาใช้แทน OkHttp เนื่องจากอยู่ดีๆ OkHttp ติดต่อกับ Wordpress REST API ไม่ได้ซะงั้น จึงจำใจต้องย้ายค่าย จริงๆ มีอีกค่ายคือ loopj แต่ยังไม่ได้ลอง ในการใช้งานจะแบ่งออกเป็น 2 ส่วนคือ Request คืองานที่ต้องการทำ กับ RequestQueue คือตัวจะการ Request
ในโปรเจกต์นี้มีการใช้งาน Request 2 คลาสคือ FeedService และ PostService เพื่อสร้าง HTTP GET Request โพสจาก Wordpress REST API และ ใช้งาน RequestQueue 1 คลาสคือ App ที่นำ RequestQueue ไว้ที่ App ก็เพื่อให้ Service ทั้งหมดเรียกตัวจัดการ Queue แค่ที่เดียว ไม่ต้องไปประกาศของตัวเอง เนื่องจากคลาส App ทำตัวเองเป็น Static Singleton Instance สามารถเรียกจากคลาสใดก็ได้

ดูเพิ่มเติมได้ที่ http://www.daydev.com/2015/android-volley-json-image-cache.html

### 6) com.github.bumptech.glide:glide

สั้นๆ ง่ายๆ คือไว้ใช้โหลดรูปจาก URL มีใช้ใน Adapter ต่างๆ และ ImageHeaderView, ImageContentView

ดูเพิ่มเติมได้ที่

https://github.com/bumptech/glide

http://nuuneoi.com/blog/blog.php?read_id=808

## โปรเจกต์นี้จะแบ่งโครงสร้างของคลาสในแต่ละหน้าดังนี้

-	Activity : คลาสหลักสำหรับเชื่อมต่อและควบคุม View (XML), Adapter, Service, Model และรับ Event ต่างๆ 
-	Adapter : คลาสสำหรับควบคุมการแสดงผลของ ListView, RecyclerView และอ่านข้อมูลจาก Model
-	Service : คลาสสำหรับการเชื่อมต่อ Wordpress REST API และนำข้อมูลที่ได้ไปอัพเดทที่ Model
-	Model : คลาสสำหรับเก็บข้อมูล เมื่อได้รับข้อมูลใหม่จาก Service จะ Notify ไปยัง Subscriber (ในที่นี้จะประกาศ Subscriber ไว้ที่ Activity) โดย Model จะสืบทอดจาก Observable อีกที

--
## Roadmap

- รองรับ tag : ol, ul, li
- รองรับ Syntax Hilighter (พวก tag pre, code)
- แสดง Comment และตอบ Comment
- Bookmark โพสที่ชอบ
- Share โพส
- Login
