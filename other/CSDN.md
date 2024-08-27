# CSDN

1. 方法1: 打开控制台 ,点击左上角箭头`Select an Element`, 选择页面上的元素, 然后控制台`Console`输入`console.log($0.innerText)`
2. 控制台`Console`输入`$=0`, 这种方法不能复制代码, 还需要添加`contenteditable="true"`
    + `document.getElementsByTagName('body')[0].contenteditable="true"`
