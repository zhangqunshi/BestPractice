AngularJS国际化配置
=====

## 下载angular-translate

下载zip包：https://github.com/angular-translate/bower-angular-translate/releases

## 引入到js文件
```
<script src="/bower_components/angular-translate-2.15.2/angular-translate.min.js"></script>
<script src="/bower_components/angular-translate-loader-static-files-2.15.2/angular-translate-loader-static-files.min.js"></script>
```

## 加载tranlate组件

```
var authModule = angular.module("AuthModule", ['pascalprecht.translate']);

var transProvider = function($translateProvider) {
    var lang = window.localStorage.lang || 'cn';
    $translateProvider.preferredLanguage(lang);
    $translateProvider.useStaticFilesLoader({
        prefix: '/i18n/',
        suffix: '.json'
    });
};


authModule.config(['$translateProvider', transProvider]);
```

## 建立多语言文件
在项目中建立i18n目录。并且在目录下建立对应语言的文件，例如：
- en.json
- cn.json

在消息文件内容写入：
```
{
  "name": "名字",
}
```

## 在html中使用
```
<div>{{each.data.name | translate }}</div>
```


## 在js代码中进行翻译
```
$scope.name = $translate.instant("name");
```


@完