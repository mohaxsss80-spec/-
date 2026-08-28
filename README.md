# نتائجنا | Nataejna

تطبيق أندرويد فخم لمنصة نتائج الطلاب في سوريا  
الموقع: https://mhazim929282.pythonanywhere.com/

**المطور: AWAD HAZIM**

---

## المميزات

- شاشة بداية أنيقة مع شعار متحرك
- اسم **AWAD HAZIM** يتحرك بلطف في الخلفية (علامة مائية فخمة)
- شريط تحميل بلون أخضر الموقع
- سحب للتحديث (Pull to Refresh)
- صفحة خطأ جميلة عند انقطاع الإنترنت
- أيقونة تطبيق مخصصة
- تصميم داكن يطابق الموقع
- دعم GitHub Actions لبناء الـ APK تلقائياً

---

## البناء

### Android Studio / AndroidIDE
1. فك الضغط
2. افتح المجلد كمشروع Gradle
3. انتظر Sync
4. Build → Build APK(s)

### GitHub Actions
1. ارفع المشروع على GitHub
2. Actions → Build APK → Run workflow
3. نزّل الـ Artifact `nataejna-apk`

---

لا تضع أي بيانات حساسة داخل المستودع.
