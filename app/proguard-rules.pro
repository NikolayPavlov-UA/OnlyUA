-dontobfuscate
-dontoptimize
-ignorewarnings
-dontwarn
-dontnote

-dontwarn androidx.arch.**
-dontwarn androidx.lifecycle.**
-keep class androidx.arch.** { *; }
-keep class androidx.lifecycle.** { *; }

-keep class com.bumptech.** {*;}
-keepclassmembers class com.bumptech.** {*;}
