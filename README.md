This example intends to demo handling of webview client with SW.

* 1 - Normal case & also registers SW. Upon loading again, it will act as passthrough mode. i.e. https://cactus-fixed-value.glitch.me

```
self.addEventListener("fetch", e => {
  e.respondWith(
    fetch(e.request)
  );
});
```

* 2 - SW interecepts ther request, makes a request to origin for dynamic response. Sends some local response to renderer meanwhile. Once origin response comes, it is then passed to the renderer. i.e. https://cactus-fixed-value.glitch.me/index-via-sw-200.html

* 3 - SW interecepts ther request, makes a request to origin for dynamic response. Sends some local response to renderer meanwhile. But origin request fails, so passes generic error message, in the form of HTML, to the renderer. i.e. https://cactus-fixed-value.glitch.me/index-via-sw-500.html

* 4 - Pass through mode where server responds with 500 error code & generic html. Connected WebViewClient registers it as sucessful page load. i.e. https://cactus-fixed-value.glitch.me/500

* 5 - Same as 4 but SW doesn't handle the request. It simply returns which then causes browser to perform the navigation. Here Connected WebViewClient registers as failed page load and triggers native error view. i.e. https://cactus-fixed-value.glitch.me/500?nosw=1