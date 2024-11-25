package com.ismultiwindow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule

class IsMultiWindowModule(reactContext: ReactApplicationContext) :
        ReactContextBaseJavaModule(reactContext) {

  private var listenerCount = 0
  private var receiver: BroadcastReceiver? = null

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun addListener(_eventName: String) {
    listenerCount += 1
    if (listenerCount == 1 && receiver == null) {
      receiver =
              object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                  onMultiWindowModeChanged(intent.getBooleanExtra("isInMultiWindowMode", false))
                }
              }

      compatRegisterReceiver(
              reactApplicationContext,
              receiver!!,
              IntentFilter("onMultiWindowModeChanged"),
              false
      )
    }
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    if (listenerCount == 0) {
      println("Listener is not exist")
      return
    }
    listenerCount -= count
    if (listenerCount == 0 && receiver != null) {
      reactApplicationContext.unregisterReceiver(receiver)
      receiver = null
    }
  }

  fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
    val eventName = "onMultiWindowModeChanged"

    getReactApplicationContext()
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, isInMultiWindowMode)
  }

  @ReactMethod
  fun isMultiWindowMode(promise: Promise) {
    try {
      val activity = currentActivity
      if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val isInMultiWindow = activity.isInMultiWindowMode
        promise.resolve(isInMultiWindow)
      } else {
        promise.resolve(false)
      }
    } catch (e: Exception) {
      promise.reject("fail", e)
    }
  }

  fun compatRegisterReceiver(
          context: Context,
          receiver: BroadcastReceiver,
          filter: IntentFilter,
          exported: Boolean
  ) {
    if (Build.VERSION.SDK_INT >= 34 && context.applicationInfo.targetSdkVersion >= 34) {
      context.registerReceiver(
              receiver,
              filter,
              if (exported) Context.RECEIVER_EXPORTED else Context.RECEIVER_NOT_EXPORTED
      )
    } else {
      context.registerReceiver(receiver, filter)
    }
  }

  companion object {
    const val NAME = "IsMultiWindow"
  }
}
