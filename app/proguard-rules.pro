# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# keep our own classes
-keep class com.dominikgold.calorietracker.**
# keep all db models from being renamed
-keepnames class com.dominikgold.calorietracker.datasources.db.model.** { *; }
# keep all Proto store models from being renamed
-keepnames class com.dominikgold.calorietracker.datasources.models.** { *; }