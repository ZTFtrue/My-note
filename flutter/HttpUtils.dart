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
