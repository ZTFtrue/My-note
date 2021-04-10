
# contenteditable 属性

```html
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <title>
    Editable Div
  </title>
  <meta charset="UTF-8">
  </meta>
  <style >
    .edit-div {
      height: 300px;
      width: 100vw;
    }
  </style>
</head>

<body>
  <div id="edit-div" contenteditable="true">
    1. 譬如厨子做菜，有人品评他坏，他固不应该将厨刀铁斧交给批评者，说道你来做一碗好的看；
    2. 从来如此便对吗？
    3. 损着别人的牙眼，却反对报复，主张宽容的人，万勿和他接近
    4. 他们都叫你学好，好自个使坏. 
  </div>
</body>

</html>
```

## 编辑style

```html
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <title>
    Editable Div
  </title>
  <meta charset="UTF-8">
  </meta>

</head>

<body>
  <!-- 可以编辑 background-color , 相应edit-div 会有变化 -->
  <style contenteditable style="display:block">
    .edit-div {
      height: 300px;
      width: 100vw;
      background-color: aliceblue;
    }
  </style>
    <div class="edit-div" contenteditable="true">
      1. 譬如厨子做菜，有人品评他坏，他固不应该将厨刀铁斧交给批评者，说道你来做一碗好的看；
      2. 从来如此便对吗？
      3. 损着别人的牙眼，却反对报复，主张宽容的人，万勿和他接近
      4. 他们都叫你学好，好自个使坏. 
    </div>
</body>

</html>

```
