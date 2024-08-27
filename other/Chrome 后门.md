#

## 只对 Google site 起作用，获取电脑硬件信息

<https://x.com/simonw/status/1810731216796324080>

To test this out for yourself, open a tab on a **Google site** and paste this into the Chrome DevTools console:

```js
chrome.runtime.sendMessage('nkeimhogjdpnpccoofpliimaahmaaome', {method: 'cpu.getInfo'}, response => {console.log(JSON.stringify(response, null, 2));});
```

So, Google Chrome gives all ***.google.com** sites full access to system / tab CPU usage, GPU usage, and memory usage. It also gives access to detailed processor information, and provides a logging backchannel.

This API is not exposed to other sites - only to ***.google.com**.
