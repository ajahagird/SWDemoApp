This example intends to demo handling of webview client with SW. Each tab represents one use case. You can find the server side code at https://glitch.com/edit/#!/massive-delirious-wish. App has couple of components.
1. Main screen with Webview that loads an URL
2. WebviewClient that listens to page load events
3. Native Error screen that listens to page load events and shows when page load failure is detected by WebViewClient.

## Lifecycle
### Tab 1 - Normal Case
* Registers Service Worker (SW) when it is loaded first time. Note that it will reload the page once SW is installed.
* Subsequent load will act as passthrough mode via SW.
* ie. https://massive-delirious-wish.glitch.me
* Sample code to demonstrate what do we mean by pass through.

```
self.addEventListener("fetch", e => {
  e.respondWith(
    fetch(e.request)
  );
});
```

### Tab 2 & 3 - Hybrid page
* Demonstrates what would happen when SW responds with local content & then makes a origin response to fetch the rest. Origin response when available is then piped to the renderer.
* Tab 2 demonstrates successful case where origin responds with valid content. ie.https://massive-delirious-wish.glitch.me/index-via-sw-200.html
* Tab 3 demonstrates unsuccessful case where origin responds with 5xx error. In this we detect the failure in SW layer and sends generic error occurred HTML snippet to renderer. In this case, WebViewClient reports `onPageFinished` instead of `onReceivedHttpError`. We have a native module that listens to `onReceivedHttpError` and shows custom native screen. However in this case, because `onReceivedHttpError` is never triggered, we don't show this screen. ie.https://massive-delirious-wish.glitch.me/index-via-sw-500.html

### Tab 4 & 5 - Non Hybrid Page/Simple Passthrough
* Demonstrates that using simple passthrough doesn't propagate page load failures to WebViewClient.
* Tab 4 loads the page that will send 500 HTTP status code. However, WebViewClient registers it as successful page load and doesn't show our custom native error screen. i.e. https://massive-delirious-wish.glitch.me/500
* Tab 5 loads the same page as tab 4 but it passes an query parameter to tell the SW to skip processing this URL. So instead of calling fetch, it simply returns from fetch handler. In this case, WebViewClient registers as failed page load and triggers native error view. i.e. https://massive-delirious-wish.glitch.me/500?nosw=

```
self.addEventListener("fetch", e => {
  if(e.request.url.indexOf('nosw=1') < 0) return;

  e.respondWith(
    fetch(e.request)
  );
});
```

## Clear Cache
Demonstrates that known cache/storage clearing APIs do not clear 'cache' maintained by [CacheStorage](https://developer.mozilla.org/en-US/docs/Web/API/CacheStorage) apis. Ideally, either [WebStorage.html#deleteAllData](https://developer.android.com/reference/android/webkit/WebStorage.html#deleteAllData\(\)) or [WebView#clearCache](https://developer.android.com/reference/android/webkit/WebView#clearCache(boolean)) should work, but it seems both of them fail to delete CacheStorage api cache.

* Use Tab1 for demonstration to see impact of these delete APIs on CacheStorage and WebCache.
* CacheStorage is populated using [CacheStorage](https://developer.mozilla.org/en-US/docs/Web/API/CacheStorage) apis by service worker during installation & is available to pages using windows.caches object. WebCache is populated by browser when it downloads any cacheable HTTP resource. In our case, it is cacheable [sample.css](https://massive-delirious-wish.glitch.me/sample.css) referenced by the browser. There is no way to access WebCache directly, so we rely on performance timing api's [transferSize](https://developer.mozilla.org/en-US/docs/Web/API/PerformanceResourceTiming/transferSize) as an heuristic to detect that it loaded from the cache.
* Observe that Debug Content information confirms that there is data in CacheStorage as well as WebCache. 
* Click on Clear WebView. It will reload the page. It will execute both WebStorage.html#deleteAllData](https://developer.android.com/reference/android/webkit/WebStorage.html#deleteAllData\(\)) and [WebView#clearCache](https://developer.android.com/reference/android/webkit/WebView#clearCache(boolean)), along with bunch of known clearing methods that you can find in [MainActivity](app/src/main/java/com/example/swdemoapp/MainActivity.java).
* Observe that, after reload, CacheStorage continues to pass data presence check where as WebCache doesn't.