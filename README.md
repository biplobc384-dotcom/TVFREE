# 📺 TV FREE - লাইভ টিভি অ্যাপ

একটি শক্তিশালী এবং ব্যবহারকারী-বান্ধব অ্যান্ড্রয়েড অ্যাপ্লিকেশন যেখানে আপনি বিনামূল্যে লাইভ টিভি চ্যানেল দেখতে পারবেন।

---

## 📥 অ্যাপ ডাউনলোড করুন

| প্ল্যাটফর্ম | ডাউনলোড |
|:---:|:---:|
| **GitHub Releases** | [সর্বশেষ সংস্করণ ডাউনলোড করুন](https://github.com/biplobc384-dotcom/TVFREE/releases) |
| **DIRECKT LINK** | [সর্বশেষ সংস্করণ ডাউনলোড করুন](https://release-assets.githubusercontent.com/github-production-release-asset/1289009685/35a05b15-2c75-4d64-83f0-4022e09cc0f6?sp=r&sv=2018-11-09&sr=b&spr=https&se=2026-07-10T14%3A57%3A10Z&rscd=attachment%3B+filename%3DTV.FREE.apk&rsct=application%2Fvnd.android.package-archive&skoid=96c2d410-5711-43a1-aedd-ab1947aa7ab0&sktid=398a6654-997b-47e9-b12b-9515b896b4de&skt=2026-07-10T13%3A56%3A12Z&ske=2026-07-10T14%3A57%3A10Z&sks=b&skv=2018-11-09&sig=%2FXpC7R%2BxENmVc%2FlHPjR6K%2F96ByBIMmmyPzETrBVYzw0%3D&jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmVsZWFzZS1hc3NldHMuZ2l0aHVidXNlcmNvbnRlbnQuY29tIiwia2V5Ijoia2V5MSIsImV4cCI6MTc4MzY5NDkyNywibmJmIjoxNzgzNjkzMTI3LCJwYXRoIjoicmVsZWFzZWFzc2V0cHJvZHVjdGlvbi5ibG9iLmNvcmUud2luZG93cy5uZXQifQ.-tWY0EIzaSOrhGjgAuQ1Rmqdyk4EUbZCfNjY2ieJbP4&response-content-disposition=attachment%3B%20filename%3DTV.FREE.apk&response-content-type=application%2Fvnd.android.package-archive) |
> **প্রয়োজনীয়তা:** Android 5.0 (API 21) বা তার উপরে

---

## ✨ প্রধান ফিচারসমূহ

- 📡 **লাইভ টিভি স্ট্রিমিং** - ExoPlayer ব্যবহার করে উচ্চ মানের m3u8 স্ট্রিম প্লেব্যাক
- 🔄 **রিয়েল-টাইম আপডেট** - Firebase Firestore থেকে চ্যানেলের তালিকা স্বয়ংক্রিয়ভাবে সিঙ্ক করা হয়
- 🎛️ **সহজ নেভিগেশন** - RecyclerView এবং GridLayoutManager দিয়ে সুন্দর চ্যানেল লেআউট
- 🔍 **শক্তিশালী সার্চ** - দ্রুত এবং সহজে আপনার পছন্দের চ্যানেল খুঁজে পান
- 🖥️ **ফুলস্ক্রিন মোড** - সর্বোত্তম ভিউয়িং অভিজ্ঞতার জন্য ফুলস্ক্রিন সাপোর্ট
- 💬 **সাপোর্ট সিস্টেম** - সমস্যার জন্য সরাসরি ডেভেলপারের সাথে যোগাযোগ করুন
- ⚡ **দ্রুত লোডিং** - Coil লাইব্রেরি দিয়ে অপটিমাইজড ইমেজ লোডিং

---

## 🛠️ ব্যবহৃত প্রযুক্তি

| বিভাগ | প্রযুক্তি |
|:---:|---|
| **ভাষা** | Kotlin |
| **UI Framework** | Android XML Layouts, ConstraintLayout |
| **মিডিয়া প্লেয়ার** | Media3 ExoPlayer (HLS সাপোর্ট) |
| **ব্যাকএন্ড** | Firebase Firestore, Firebase Realtime Database |
| **ইমেজ লোডিং** | Coil |
| **লিস্ট ম্যানেজমেন্ট** | RecyclerView, GridLayoutManager |
| **Minimum SDK** | Android 5.0 (API 21) |

---

## 📦 প্রজেক্ট স্ট্রাকচার

```
TVFREE/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/tvfree/
│   │   │   │       ├── MainActivity.kt
│   │   │   │       ├── PlayerActivity.kt
│   │   │   │       └── ...
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       ├── drawable/
│   ��   │       └── values/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json
├── README.md
└── ...
```

---

## ⚙️ ইনস্টলেশন এবং সেটআপ গাইড

### প্রয়োজনীয় জিনিস:
- Android Studio (সর্বশেষ সংস্করণ)
- JDK 11 বা তার উপরে
- একটি Firebase অ্যাকাউন্ট
- Google Play Services

### স্টেপ ১: রিপোজিটরি ক্লোন করুন

```bash
git clone https://github.com/biplobc384-dotcom/TVFREE.git
cd TVFREE
```

### স্টেপ ২: Android Studio তে খুলুন

1. Android Studio খুলুন
2. `File` → `Open` → ডাউনলোড করা ফোল্ডার সিলেক্ট করুন
3. প্রজেক্ট সিঙ্ক হওয়ার অপেক্ষা করুন

### স্টেপ ৩: Firebase সেটআপ করুন

1. [Firebase Console](https://console.firebase.google.com/) এ যান
2. নতুন প্রজেক্ট তৈরি করুন অথবা বিদ্যমান প্রজেক্ট ব্যবহার করুন
3. **Android অ্যাপ যোগ করুন:**
   - প্যাকেজ নাম: `com.example.tvfree` (আপনার বিল্ড config অনুযায়ী)
   - SHA-1 সার্টিফিকেট ফিঙ্গারপ্রিন্ট যোগ করুন
4. `google-services.json` ডাউনলোড করুন এবং `app/` ফোল্ডারে রাখুন

### স্টেপ ৪: Firestore Database সেটআপ করুন

1. Firebase Console এ **Firestore Database** ক্রিয়েট করুন
2. নতুন কালেকশন তৈরি করুন নাম: **`channels`**
3. প্রতিটি চ্যানেলের জন্য ডকুমেন্ট যোগ করুন নিম্নলিখিত ফিল্ড সহ:

```json
{
  "name": "চ্যানেলের নাম",
  "image": "লোগো বা থাম্বনেইলের URL",
  "url": "m3u8 স্ট্রিম লিংক"
}
```

**উদাহরণ:**
```json
{
  "name": "BTV",
  "image": "https://example.com/btv-logo.png",
  "url": "https://example.com/btv.m3u8"
}
```

### স্টেপ ৫: অ্যাপ বিল্ড এবং রান করুন

```bash
# Gradle Sync করুন
./gradlew build

# এমুলেটর বা ডিভাইসে চালান
./gradlew installDebug
```

অথবা Android Studio এ সরাসরি **Run** বাটন ক্লিক করুন (Shift + F10)

---

## 🎯 ব্যবহার করার উপায়

1. **অ্যাপ খুলুন** - সব উপলব্ধ চ্যানেল প্রদর্শিত হবে
2. **চ্যানেল নির্বাচন করুন** - যেকোনো চ্যানেল ট্যাপ করে দেখুন
3. **প্লেয়ার নিয়ন্ত্রণ করুন** - প্লে, পজ, ভলিউম এবং সাবটাইটেল সামঞ্জস্য করুন
4. **ফুলস্ক্রিন মোড** - স্ক্রিন রোটেট করলে স্বয়ংক্রিয়ভাবে ফুলস্ক্রিন হবে
5. **সার্চ ফিচার** - সার্চ বারে চ্যানেলের নাম টাইপ করে খুঁজুন
6. **সাপোর্ট** - কোনো সমস্যা হলে সাপোর্ট অপশন থেকে যোগাযোগ করুন

---

## 🐛 সমস্যা সমাধান

### সমস্যা: অ্যাপ ক্র্যাশ হচ্ছে

**সমাধান:**
- `google-services.json` ফাইল সঠিক জায়গায় আছে কিনা চেক করুন
- Firebase সংযোগ সঠিক কিনা নিশ্চিত করুন
- Android Studio এ `Build` → `Clean Project` করুন

### সমস্যা: চ্যানেল লোড হচ্ছে না

**সমাধান:**
- ইন্টারনেট সংযোগ চেক করুন
- Firebase Firestore ডেটা সঠিকভাবে যোগ করা হয়েছে কিনা দেখুন
- Firestore নিরাপত্তা নিয়ম (Security Rules) সঠিক কিনা পরীক্ষা করুন

### সমস্যা: ভিডিও প্লে হচ্ছে না

**সমাধান:**
- m3u8 স্ট্রিম লিংক কাজ করছে কিনা পরীক্ষা করুন
- ExoPlayer লগ দেখুন: `logcat | grep ExoPlayer`
- ডিভাইসের ভিডিও কোডেক সাপোর্ট চেক করুন

---

## 📝 লাইসেন্স

এই প্রজেক্টটি **MIT License** এর অধীনে লাইসেন্সপ্রাপ্ত।

ব্যবহৃত ওপেন সোর্স লাইব্রেরিগুলি তাদের নিজস্ব লাইসেন্স সহ অন্তর্ভুক্ত:
- **ExoPlayer** - Apache 2.0
- **Coil** - Apache 2.0
- **Firebase** - Google Mobile Services License
- **Kotlin** - Apache 2.0

বিস্তারিত জানতে [LICENSE](./LICENSE) ফাইল দেখুন।

---

## 👤 ডেভেলপার

**ARIFUR JAMAN**
- GitHub: [@biplobc384-dotcom](https://github.com/biplobc384-dotcom)
- Email: arifurjjaman511@gmail.com

---

## 📞 যোগাযোগ এবং সাপোর্ট

যেকোনো সমস্যা বা পরামর্শের জন্য:

- **GitHub Issues:** [Issue তৈরি করুন](https://github.com/biplobc384-dotcom/TVFREE/issues)
- **Email:** arifurjjaman511@gmail.com
- **Phone:** 01799517156
- **ইন-অ্যাপ সাপোর্ট:** অ্যাপের সেটিংস মেনু থেকে সাপোর্ট অপশন ব্যবহার করুন

---

## ⭐ যদি আপনার পছন্দ হয়েছে

এই প্রজেক্টটিকে একটি ⭐ স্টার দিন এবং বন্ধুদের সাথে শেয়ার করুন!

---

## 🔄 আপডেট লগ

### v1.0.0 (সর্বশেষ)
- প্রথম রিলিজ
- লাইভ টিভি স্ট্রিমিং ফিচার
- Firebase ইন্টিগ্রেশন
- সার্চ এবং ফিল্টার সুবিধা

---

**আপনার মূল্যবান মতামতের জন্য ধন্যবাদ! 🙏**
