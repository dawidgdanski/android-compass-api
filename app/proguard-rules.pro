#-printusage proguard/unused.txt
#-dump proguard/class_files.txt
#-printseeds proguard/seeds.txt
#-printmapping proguard/mapping.txt

# Removing logs
-assumenosideeffects class android.util.Log { *; }

#Guava
-keep,allowoptimization class com.google.inject.** { *; }
-keep,allowoptimization class javax.inject.** { *; }
-keep,allowoptimization class javax.annotation.** { *; }
-keep,allowoptimization class com.google.inject.Binder

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

-keepclassmembers,allowoptimization class com.google.common.* {
    void finalizeReferent();
    void startFinalizer(java.lang.Class,java.lang.Object);
}

-keep class com.google.common.io.Resources {
    public static <methods>;
}
-keep class com.google.common.collect.Lists {
    public static ** reverse(**);
}
-keep class com.google.common.base.Charsets {
    public static <fields>;
}

-keep class com.google.common.base.Joiner {
    public static Joiner on(String);
    public ** join(...);
}

-keep class com.google.common.collect.MapMakerInternalMap$ReferenceEntry
-keep class com.google.common.cache.LocalCache$ReferenceEntry

-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-dontwarn javax.annotation.**

# ButterKnife 7
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Play Services Location API
-keep public class com.google.android.gms.**

-dontwarn com.google.android.gms.**