# ساخت APK با GitHub

1. این پروژه را در یک repository جدید GitHub آپلود کن.
2. مطمئن شو فایل `gradlew` در ریشه repository باشد.
3. برو به `Actions`.
4. workflow با نام `Build Top Topy Board APK` را باز کن.
5. روی `Run workflow` بزن.
6. بعد از اتمام Build، در بخش `Artifacts` فایل `Top-Topy-Board-APK` را دانلود کن.

همچنین با هر push به شاخه `main` یا `master`، Build به صورت خودکار اجرا می‌شود.

این پروژه یک Android IME است و طبق معماری استاندارد Android از `InputMethodService` استفاده می‌کند.
