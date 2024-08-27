#

## CallBack

定义一个回调方法

```dart
typedef CallBack = void Function(bool check);
```

## LoadingDailog

```dart
// LoadingDialog.dart
import 'package:flutter/material.dart';

void showCustomDialog(BuildContext context, String text) {
  showDialog(
      context: context,
      builder: (BuildContext context) {
        return LoadingDialog(text);
      });
}

class LoadingDialog extends Dialog {
  String text;

  LoadingDialog(
    this.text, {
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return new Material(
      //创建透明层
      type: MaterialType.transparency, //透明类型
      child: new Center(
        //保证控件居中效果
        child: new SizedBox(
          width: 100.0,
          height: 100.0,
          child: new Container(
            decoration: ShapeDecoration(
              color: Color(0xffffffff),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.all(
                  Radius.circular(8.0),
                ),
              ),
            ),
            child: new Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new CircularProgressIndicator(),
                new Padding(
                  padding: const EdgeInsets.only(
                    top: 20.0,
                  ),
                  child: new Text(
                    text,
                    style: new TextStyle(fontSize: 12.0),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
```

## NetWork

一个小的网络库

```dart
// HttpUtils.dart
import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:widgets/LoadingDialog.dart';

final String urlDebug = "http://baidu.com";
final String urlRelease = "http://google.com";

getApiUrl() {
  if (kReleaseMode) {
    return urlRelease;
  } else {
    return urlDebug;
  }
}

loginUrl() {
  return getApiUrl() + "/login";
}


Future<dynamic> post<T>(
  String path, {
  data,
  Map<String, dynamic>? queryParameters,
  Map<String, String>? headers,
  BuildContext? context,
  String message = "",
  reposeAll = false,
}) async {
  if (context != null) {
    showCustomDialog(context, message);
  }
  var client = http.Client();
  try {
    Uri url = Uri.parse(path);
    print(url);
    if (headers == null) {
      headers = {HttpHeaders.contentTypeHeader: "application/json"};
    }
    var response = await client.post(url, headers: headers, body: data);
    if (response.statusCode != 200) {
      throw FormatException("网络错误", response.statusCode);
    }
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

    if (int.parse(decodedResponse['code']) != 200) {
      throw FormatException(
        decodedResponse['message'],
        decodedResponse['code'],
      );
    }
    if (context != null) {
      Navigator.pop(context);
    }
    return Future<dynamic>(
        () => reposeAll ? response : decodedResponse['data']);
  } catch (e) {
    if (context != null) {
      Navigator.pop(context);
    }
    return new Future.error(e);
  } finally {
    client.close();
  }
}
```
