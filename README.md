This example intends to demo handling of webview client with SW. Each tab represents one use case. You can find the server side code at https://glitch.com/edit/#!/cactus-fixed-value. App has couple of components.
1. Main screen with Webview that loads an URL
2. WebviewClient that listens to page load events
3. Native Error screen that listens to page load events and shows when page load failure is detected by WebViewClient.


### Tab 1 - Normal Case
* Registers Service Worker (SW) when it is loaded first time.
* Subsequent load will act as passthrough mode via SW.
* ie. https://cactus-fixed-value.glitch.me
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
* Tab 2 demonstrates successful case where origin responds with valid content. ie.https://cactus-fixed-value.glitch.me/index-via-sw-200.html
* Tab 3 demonstrates unsuccessful case where origin responds with 5xx error. In this we detect the failure in SW layer and sends generic error occured HTML snippet to renderer. In this case, WebViewClient reports `onPageFinished` instead of `onReceivedHttpError`. We have a native module that listens to `onReceivedHttpError` and shows custom native screen. However in this case, because `onReceivedHttpError` is never triggered, we don't show this screen. ie.https://cactus-fixed-value.glitch.me/index-via-sw-500.html

### Tab 4 & 5 - Non Hybrid Page/Simple Passthrough
* Demonstrates that using simple passthrough doesn't propagate page load failures to WebViewClient.
* Tab 4 loads the page that will send 500 HTTP status code. However, WebViewClient registers it as sucessful page load and doesn't show our custom native error screen. i.e. https://cactus-fixed-value.glitch.me/500
* Tab 5 loads the same page as tab 4 but it passes an query parameter to tell the SW to skip processing this URL. So instead of calling fetch, it simply returns from fetch handler. In this case, WebViewClient registers as failed page load and triggers native error view. i.e. https://cactus-fixed-value.glitch.me/500?nosw=

```
self.addEventListener("fetch", e => {
  if(e.request.url.indexOf('nosw=1') < 0) return;

  e.respondWith(
    fetch(e.request)
  );
});
```