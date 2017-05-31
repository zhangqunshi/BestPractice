javascript获取窗口大小
============

JavaScript获取各种窗口大小的方法很多，但是需要在其中找到你自己需要的：

- document.body.clientWidth: 网页body宽度（注意：可能会超出屏幕分辨率，因为网页通过滚动条显示更多内容）
- document.body.clientHeight: 网页body高度（注意：可能会超出屏幕分辨率，因为网页通过滚动条显示更多内容）
- window.screen.width:  整个屏幕宽度 （不变，除非改屏幕分辨率）
- window.screen.height: 整个屏幕高度 （不变，除非改屏幕分辨率）
- window.screen.availWidth: window屏幕宽度，用户的可见部分
- window.screen.availHeight: window屏幕高度，用户的可见部分

对于地图页面，一般不使用可见区域，而是网页大小，所以使用的是window.innerWidth。

@完


