package com.lhscanextensions


import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import java.io.IOException

import com.lhscanextensions.utils.HomePage
import com.lhscanextensions.utils.List
import com.lhscanextensions.utils.ListPage
import com.lhscanextensions.utils.PostsPage

class LhscanExtensionsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
        return "LhscanExtensions"
  }

  @ReactMethod
  fun getHome(promise: Promise) {
    try { promise.resolve(HomePage().get()) } catch (e: IOException) { promise.reject(e) }
  }

  @ReactMethod
  fun getList(url:String,promise: Promise) {
    try { promise.resolve(ListPage().get(url)) } catch (e: IOException) { promise.reject(e) }
  }

  @ReactMethod
  fun getByList(promise: Promise) {
    try { promise.resolve(ListPage().getByList()) } catch (e: IOException) { promise.reject(e) }
  }

  @ReactMethod
  fun getByGenre(promise: Promise) {
    try { promise.resolve(ListPage().getByGenre()) } catch (e: IOException) {promise.reject(e)}
  }

  @ReactMethod
  fun getPosts(url:String,promise: Promise) {
    try { promise.resolve(PostsPage().getPosts(url)) } catch (e: IOException) { promise.reject(e) }
  }

  @ReactMethod
  fun getPostView(url: String,promise: Promise) {
    try { promise.resolve(PostsPage().getView(url)) } catch (e: IOException) { promise.reject(e) }
  }

  @ReactMethod
  fun postList(url: String,promise: Promise) {
      try { promise.resolve(List().getData(url)) } catch (e: IOException) { promise.reject(e) }
  }

}
