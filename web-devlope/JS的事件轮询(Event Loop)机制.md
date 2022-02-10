# JS的事件轮询(Event Loop)机制

JS 运行方法:

将要执行A函数,将A函数放入_消息队列_，等待主线程读取A函数->读取A函数,放入执行栈->执行A函数,执行完成出栈(FIFO). 主线程不停的读就是事件轮询,循环一次为一个tick(嘀嗒).

A 函数来源: 回调函数, 例如点鼠标，网络请求完成.

## 任务和微任务

[在 JavaScript 中通过 queueMicrotask() 使用微任务 - Web API 接口参考 | MDN https://developer.mozilla.org/en-US/docs/Web/API/HTML_DOM_API/Microtask_guide](https://developer.mozilla.org/en-US/docs/Web/API/HTML_DOM_API/Microtask_guide)

宏任务:参与了事件循环的任务,JavaScript 引擎不直接执行的任务,参与了事件循环的异步任务(被放入消息队列的).

- Chromium 自定义消息
- Socket 或者文件等 IO 消息
- UI 相关的消息;blink 渲染引擎过来的消息，例如各种 DOM 的事件
- 与平台无关的消息，例如 setTimeout

微任务: 直接在 Javascript 引擎中的执行的，没有参与事件循环的任务.

- 是个内存回收的清理任务
- 普通的回调，MutationObserver 也是这一类
- Callable
- 包括 Fullfiled 和 Rejected 也就是 Promise 的完成和失败
- Thenable 对象的处理任务

## 完全执行顺序

<!-- 1. 先执行主线程
2. 遇到宏任务(macrotask)放到宏队列
3. 遇到微任列(microtask)放到微队列
4. 主线程执行完毕
5. 执行微队列(microtask)，直到执行完毕
6. 执行一次宏队列(macrotask)中的一个任务，直到执行完毕
7. 执行微队列(microtask)，执行完毕
8. 回到6. -->

```js
// 一外企面试题
function promiseLog(){
    return  new Promise((resolve, reject)=>{
console.log(1);
resolve(2);
console.log(3)
    })
}
promiseLog().then(console.log)
console.log(4)
/*----------------*/
输出 1->3->4->2
```

## Example

```js
let p1 = Promise.reject(1),
  p2 = Promise.resolve(2),
  p3 = Promise.resolve(3)

myPromiseRace([p1, p2, p3]).then(res => {
  console.log(res)
}).catch(err => {
  console.log(err);
})
```

### one

```js
function myPromiseRace(arr) {
  return new Promise((resolve, reject) => {
    for (let item of arr) {
      item.then(
          res => { resolve(res) },
          err => { reject(err) }
        )
    }
  })
}
```

promise 本体在 current task 中执行，  
then 里面的回调被添加到 micro task 队列中  
三个 promise 依次被添加 then 里面定义的 micro task，得到队列 p1-then 、p2-then 、p3-then，即同时将 resolve 和 reject 添加到了 微任务 队列 .  
p1-then 的 reject 回调会赢得 race ；

----

### two

```js
function myPromiseRace(arr) {
  return new Promise((resolve, reject) => {
    for (let item of arr) {
      item
        .then(
          res => { resolve(res) }
        )
        .catch(err => {
          reject(err)
        })
    }
  })
}
```

catch 是在 then 所属的 micro task 被执行时才被添加到 micro task 队列的  
三个 promise __依次__ 被添加 then 里面定义的 micro task，micro task 的执行结果会得到新的 promise，继而 __依次__ 添加 catch 里面的 micro task，完全执行下去会有 6 个 micro task，依次是 __p1-then 、p2-then 、p3-then 、p1-catch 、p2-catch 、p3-catch__，p1 的 then 虽然排在第 1 位，但只是返回了一个新的 promise 并将 p1-catch 添加到 micro task 队列，并没有回调 then 的第一个参数，所以 p2-then 赢得 race .
