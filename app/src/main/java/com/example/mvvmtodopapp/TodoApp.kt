package com.example.mvvmtodopapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// IMPORTANT! specify android:name=".TodoApp" that we have an application class in manifest
@HiltAndroidApp // give access to the application and also be able to use the app context to provide some object and dependencies
class TodoApp: Application()