package com.example

import android.content.Intent
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

class MainActivity : ReactActivity() {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "example"

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
          DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)

  override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
    super.onMultiWindowModeChanged(isInMultiWindowMode)
    // val reactContext = getContext()
    // // 네이티브 모듈로 이벤트 전달
    // val multiWindowModule = IsMultiWindowModule(reactContext)
    // multiWindowModule.onMultiWindowModeChanged(isInMultiWindowMode)
    println("example, 다중 창 모드 변경됨 : " + isInMultiWindowMode)
    val intent =
            Intent("onMultiWindowModeChanged").apply {
              putExtra("isInMultiWindowMode", isInMultiWindowMode)
            }
    sendBroadcast(intent)
  }
}
