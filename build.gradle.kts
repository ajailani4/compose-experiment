// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val koinVersion by extra { "3.4.0" }
    val ktorVersion by extra { "2.2.3" }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}