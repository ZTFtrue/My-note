
# [Javascript: Web API中的Blob, 使用URL.createObjectURL创建url对象, Blob 视频地址, 浏览器中创建和下载文件](http://justcode.ikeepstudying.com/2018/05/javascript-web-api%E4%B8%AD%E7%9A%84blob-%E6%B5%8F%E8%A7%88%E5%99%A8%E4%B8%AD%E5%88%9B%E5%BB%BA%E5%92%8C%E4%B8%8B%E8%BD%BD%E6%96%87%E4%BB%B6-%E4%BD%BF%E7%94%A8url-createobjecturl%E5%88%9B%E5%BB%BAu/)

在一般的Web开发中，很少会用到Blob，但Blob可以满足一些场景下的特殊需求. Blob，Binary Large Object的缩写，代表二进制类型的大对象. Blob的概念在一些数据库中有使用到，例如，MYSQL中的BLOB类型就表示二进制数据的容器. 在Web中，Blob类型的对象表示不可变的类似文件对象的原始数据，通俗点说，就是Blob对象是二进制数据，但它是类似文件对象的二进制数据，因此可以像操作File对象一样操作Blob对象，实际上，File继承自Blob.

## Blob使用场景

### 分片上传

 File继承自Blob，因此我们可以调用slice方法对大文件进行分片长传.

```js
function uploadFile(file) {
  var chunkSize = 1024 * 1024;   // 每片1M大小
  var totalSize = file.size;
  var chunkQuantity = Math.ceil(totalSize/chunkSize);  //分片总数
  var offset = 0;  // 偏移量
  
  var reader = new FileReader();
  reader.onload = function(e) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST","http://xxxx/upload?fileName="+file.name);
    xhr.overrideMimeType("application/octet-stream");
    xhr.onreadystatechange = function() {
      if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        ++offset;
        if(offset === chunkQuantity) {
          alert("上传完成");
        } else if(offset === chunkQuantity-1){
          blob = file.slice(offset*chunkSize, totalSize);   // 上传最后一片
          reader.readAsBinaryString(blob);
        } else {
          blob = file.slice(offset*chunkSize, (offset+1)*chunkSize);
          reader.readAsBinaryString(blob);
        }
      }else {
        alert("上传出错");
      }
    }
    if(xhr.sendAsBinary) {
      xhr.sendAsBinary(e.target.result);   // e.target.result是此次读取的分片二进制数据
    } else {
      xhr.send(e.target.result);
    }
  }
   var blob = file.slice(0, chunkSize);
   reader.readAsBinaryString(blob);
}
```

## 实例

```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <style>
        body {
            margin: 0px;
            padding: 0px;
            background-color: rgb(63, 63, 63);
            overflow: hidden;
            height: 100vh;
            width: 100vw;
            display: flex;
        }

        .code {
            overflow: auto;
            width: 50%;
            padding: 20px;
            float: left;
            height: 100%;
            overflow: scroll;
        }

        #threed {
            width: 50vw;
            overflow: hidden;
            float: right;
            background-color: aliceblue;
            height: 100%;
        }

        .editView {
            width: 100%;
            border: 1px solid #00008b;
            height: calc(100% - 100px);
            font-size: 1.3rem;
            overflow: auto;
        }
    </style>
    <script>
        //运行文本域代码
        var objectURL;
        function runEx(code) {
            if (objectURL) {
                window.URL.revokeObjectURL(objectURL);
                objectURL = null;
            }
            code = document.all(code)
            code = code.value;
            var iframe =        document.getElementById("threed");
            var doc = iframe.contentDocument;
            var blob = new Blob([code], {
                type: "text/html"
            });
            objectURL = URL.createObjectURL     (blob);
            iframe.src = objectURL;
        }
        var switchStatus = false;
    </script>
</head>
<body onload="runEx('runCode')">
    <div class="code">
        <textarea name="textarea" autofocus     class="editView" id="runCode">

        </textarea>
        <br>
        <input onclick="runEx('runCode')"  type="button" value="运行代码" style="cursor:hand">
    </div>
    <iframe id="threed" />
</body>
</html>
```
