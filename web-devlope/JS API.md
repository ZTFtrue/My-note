# API

|Name||
|---|---|
|Object.assign()| 合并两个变成一个新的|

Object.entries() eturns an array of a given object's own enumerable string-keyed property [key, value] pairs, in the same order as that provided by a for...in loop.

Object.fromEntries() method transforms a list of key-value pairs into an object.

entries()和fromEntries() 可以把json `keyA:{key:value}` 互转`[key:keyA,key:value]`

```js
let data=[
   {
    "id": "哈哈哈2",
    "value": "哈哈哈2",
   },
   {
    "id": "哈哈哈1",
    "value": "哈哈哈1",
   },
 ]
 this.aaaa = Object.fromEntries(
          data.map(({id, ...d}) => {
           return [id, d];
          })
        );
/**
// 先转成
[
  ['id', ...d]
]
// ----
**/

 this.aaaa = Object.entries(
          data.map(([id,d]) => {
           return {
           id,
           ...d
           };
          })
        );


arrary.map()
```

----------------

class extends'

extends:

继承的.prototype必须是一个Object 或者 null.
可以像扩展普通类一样扩展null，但是新对象的原型将不会继承 Object.prototype.

```js
class myDate extends Date {
  constructor() {
   super();
  }

  getFormattedDate() {
   var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
   return this.getDate() + "-" + months[this.getMonth()] + "-" + this.getFullYear();
  }
}
```

服务器在设置Cookie时可以使用httpOnly，设定了httpOnly的Cookie将不能被JavaScript读取.
这个行为由浏览器实现，主流浏览器均支持httpOnly选项，IE从IE6 SP1开始支持.
为了确保安全，服务器端在设置Cookie时，应该始终坚持使用httpOnly.

```js
window.innerHeight;// 网页宽高
window.innerWidth;
// navigator
// navigator对象表示浏览器的信息，最常用的属性包括: 
// navigator.appName: 浏览器名称；
// navigator.appVersion: 浏览器版本；
// navigator.language: 浏览器设置的语言；
// navigator.platform: 操作系统类型；
// navigator.userAgent: 浏览器设定的User-Agent字符串. 
navigator.appName;


// 正确的方法是充分利用JavaScript对不存在属性返回undefined的特性，直接用短路运算符||计算: 
let width = window.innerWidth || document.body.clientWidth;//undefined不能够转换为true
// js 短路运算判断能够转换为true和false

// screen
// screen对象表示屏幕的信息，常用的属性有: 

screen.width;//屏幕宽度，以像素为单位；
screen.height;//屏幕高度，以像素为单位；
screen.colorDepth;//返回颜色位数，如8、16、24. 

// location对象表示当前页面的URL信息. 例如，一个完整的URL: 

//http://www.example.com:8080/path/index.html?a=1&b=2#TOP
//   可以用location.href获取. 要获得URL各个部分的值，可以这么写: 

location.protocol; // 'http'
location.host; // 'www.example.com'
location.port; // '8080'
location.pathname; // '/path/index.html'
location.search; // '?a=1&b=2'
location.hash; // 'TOP'
// 要加载一个新页面，可以调用location.assign(). 如果要重新加载当前页面，调用location.reload()方法非常方便. 
if (confirm('重新加载当前页' + location.href + '?')) {
   location.reload();
} else {
   location.assign('/'); // 设置一个新的URL地址
}


document.title = '努力学习JavaScript!';//  修改网页title
document.cookie; // 'v=123; remember=true; prefer=zh'

// 服务器在设置Cookie时可以使用httpOnly，设定了httpOnly的Cookie将不能被JavaScript读取. 
// 这个行为由浏览器实现，主流浏览器均支持httpOnly选项，IE从IE6 SP1开始支持. 
// 为了确保安全，服务器端在设置Cookie时，应该始终坚持使用httpOnly. 

设置预置参数:  return new Student(props || {})
```

The Object.create() method creates a new object, using an existing object as the prototype of the newly created object.(深拷贝)

## Generators(生成器), yield

```js
//创建实例
httpRequest = new XMLHttpRequest();
request.onreadystatechange = function () { // 状态发生变化时，函数被回调
   if (request.readyState === 4) { // 成功完成
      // 判断响应结果:
      if (request.status === 200) {
        // 成功，通过responseText拿到响应的文本:
        return success(request.responseText);
      } else {
        // 失败，根据响应码判断失败原因:
        return fail(request.status);
      }
   } else {
      // HTTP请求还在继续...
   }
};
httpRequest.open('GET', 'http://www.example.org/some.file', true);
httpRequest.send();


try {
   r1 = yield ajax('http://url-1', data1);
   r2 = yield ajax('http://url-2', data2);
   r3 = yield ajax('http://url-3', data3);
   success(r3);
}
catch (err) {
   handle(err);
}

function* infiniteNumbers() {
   let n = 1;
   while (true) {
      yield n++;//冻结，下次调用在执行
   }
}

const numbers = infiniteNumbers(); // returns an iterable object

console.log(numbers.next().done); // { value: 1, done: false }
console.log(numbers.next()); // { value: 2, done: false }
console.log(numbers.next()); // { value: 3, done: false }



function count() {
   let arr = [];
   for (let i=1; i<=3; i++) {
      arr.push(function () {
        return i * i;
      });
   }
   return arr;
}

// const results = count();
// console.log(results[0]());
// console.log(results[1]());
// console.log(results[2]());


let results = count();
let f1 = results[0];
let f2 = results[1];
let f3 = results[2];

console.log(f1()); // 16
console.log(f2()); // 16
console.log(f3()); // 16
```

```js
let myHeading = document.querySelector('h1'); //获取h1
myHeading.innerHTML = 'Hello world!';

function multiply(num1, num2) {

   return num1 * num2;
}

function getCar(make, model, value) {
   return {
      // 简写变量
      make,  // 等同于 make: make
      model, // 等同于 model: model
      value, // 等同于 value: value

      // 属性可以使用表达式计算值
      ['make' + make]: true,

      // 忽略 `function` 关键词简写对象函数
      depreciate1() {
        return this.value -= 2500;
      },

      depreciate: function () {
        return this.value -= 2500;
      }

   };
}

let a = getCar('Barret', 'Lee', 40000);
console.log(a);
console.log(a.depreciate());
//  myImage.setAttribute ('src','images/firefox2.png');  设置属性

```

## Promise

对象用于表示一个异步操作的最终完成 (或失败)及其结果值

一个 Promise 必然处于以下几种状态之一:

待定(pending): 初始状态，既没有被兑现，也没有被拒绝.
已兑现(fulfilled): 意味着操作成功完成.
已拒绝(rejected): 意味着操作失败.

Promise.all 有一个返回错误,直接全挂, 想知道那个返回错误, 可以返回是添加变量或者自定义PormiseAll

Promise 状态: Pending,Fulfilled,Rejected.

Promise().finally()

```js
var p = new Promise(function (resolve, reject) {
    console.log("start");
    resolve("ok");
});

p.then((msg) => console.log(msg));
console.log("end");

// start
// end
// ok
```

### Promise的链式调用

```js
const myPromise =
  (new Promise(myExecutorFunc))
  .then(handleFulfilledA,handleRejectedA)
  .then(handleFulfilledB,handleRejectedB)
  .then(handleFulfilledC,handleRejectedC);

// 或者，这样可能会更好...

const myPromise =
  (new Promise(myExecutorFunc))
  .then(handleFulfilledA) //   { /*...*/; return nextValue;  }
  .then(handleFulfilledB)
  .then(handleFulfilledC)
  .catch(handleRejectedAny);
```

过早地处理被拒绝的 promise 会对之后 promise 的链式调用造成影响. 不过有时候我们因为需要马上处理一个错误也只能这样做.
被返回的 nextValue 可能是另一个promise对象，这种情况下这个promise会被动态地插入链式调用.
当 .then() 中缺少能够返回 promise 对象的函数时，链式调用就直接继续进行下一环操作. 因此，链式调用可以在最后一个 .catch() 之前把所有的 handleRejection 都省略掉. 类似地， .catch() 其实只是没有给 handleFulfilled 预留参数位置的 .then() 而已.

```js
 let foo = async () => 'hello'; foo(); 
 // The word “async” before a function means one simple thing: a function always returns a promise. Other values are wrapped in a resolved promise automatically.
 // The keyword await makes JavaScript wait until that promise settles and returns its result.
   async function f() {

    let promise = new Promise((resolve, reject) => {
      setTimeout(() => resolve("done!"), 1000)
    });

    let result = await promise; // wait until the promise resolves (*)

    alert(result); // "done!"
   }
   f();
   // https://javascript.info/async
```

`Promise.prototype.catch(onRejected)`  
添加一个拒绝(rejection) 回调到当前 promise, 返回一个新的promise. 当这个回调函数被调用，新 promise 将以它的返回值来resolve，否则如果当前promise 进入fulfilled状态，则以当前promise的完成结果作为新promise的完成结果.

`Promise.prototype.then(onFulfilled, onRejected)`  
添加解决(fulfillment)和拒绝(rejection)回调到当前 promise, 返回一个新的 promise, 将以回调的返回值来resolve.

`Promise.prototype.finally(onFinally)`  
添加一个事件处理回调于当前promise对象，并且在原promise对象解析完毕后，返回一个新的promise对象. 回调会在当前promise运行完毕后被调用，无论当前promise的状态是完成(fulfilled)还是失败(rejected)

## call apply

call并且apply是非常相似的，他们调用了指定的功能this方面，和可选的参数. call和之间的唯一区别apply是，call要求将参数逐一传递，并将apply参数作为数组.

MutationObserver接口提供了监视对DOM树所做更改的能力. 它被设计为旧的Mutation Events功能的替代品，该功能是DOM3 Events规范的一部分.

elementList = parentNode.querySelectorAll(selectors);
fetch  获取网络内容 XMLHttpRequest
CustomeElement 自定义元素

```js
class Student {
   constructor(name) {
      this.name = name;
   }
   hello() {
      alert('Hello, ' + this.name + '!');
   }
}

class PrimaryStudent extends Student {
   constructor(name, grade) {
      super(name); // 记得用super调用父类的构造方法!
      this.grade = grade;
   }

   myGrade() {
      alert('I am at grade ' + this.grade);
   }
}
```

deleted

## new

promise、generator、async/await

### 解构赋值

- 数组解构

```js
let [a, b, c] = [1, 2, 3] //a=1, b=2, c=3

let [d, [e], f] = [1, [2], 3]   //嵌套数组解构 d=1, e=2, f=3
let [g, ...h] = [1, 2, 3]  //数组拆分 g=1, h=[2, 3], 可替代slice
let [i,,j] = [1, 2, 3]  //不连续解构 i=1, j=3
let [k,l] = [1, 2, 3]  //不完全解构 k=1, l=2
```

- 复制代码对象解构

```js
let {a, b} = {a: 'aaaa', b: 'bbbb'}    //a='aaaa' b='bbbb'
let obj = {d: 'aaaa', e: {f: 'bbbb'}}
let {d, e:{f}} = obj   //嵌套解构 d='aaaa' f='bbbb'
let g;
(g = {g: 'aaaa'})  //以声明变量解构 g='aaaa'
let [h, i, j, k] = 'nice'   //字符串解构 h='n' i='i' j='c' k='e'
```

- 复制代码函数参数的定义

```js
function personInfo({name, age, address, gender}) {
  console.log(name, age, address, gender)
}
personInfo({gender: 'man', address: 'changsha', name: 'william', age: 18})
// 么写我们只知道要传声明参数就行来，不需要知道参数的顺序也没关系
```

- 交换变量的值

```js
let a=1, b=2;
[b, a] = [a, b]
console.log(a, b)
```

- 函数默认参数值

```js
function saveInfo({name= 'william', age= 18, address= 'changsha', gender= 'man'} = {}) {
  console.log(name, age, address, gender)
}
saveInfo()
```

- forEach、for in、for of三者区别
forEach更多的用来遍历数
for in 一般常用来遍历对象或json
for of数组对象都可以遍历，遍历对象需要通过和Object.keys()
for in循环出的是key，for of循环出的是value
使用箭头函数应注意什么？
1、用了箭头函数，this就不是指向window，而是父级(指向是可变的)
2、不能够使用arguments对象
3、不能用作构造函数，这就是说不能够使用new命令，否则会抛出一个错误
4、不可以使用yield命令，因此箭头函数不能用作 Generator 函数

## 数组,Set

### 数组的方法

- concat()  连接两个或更多的数组，并返回结果.
- join()   把数组的所有元素放入一个字符串. 元素通过指定的分隔符进行分隔.
- pop()    删除并返回数组的最后一个元素
- push()   向数组的末尾添加一个或更多元素，并返回新的长度.
- reverse()  颠倒数组中元素的顺序.  
- shift()  删除并返回数组的第一个元素  v8 使用此操作时,将指针重新指向数组的地一个元素,[参考](https://stackoverflow.com/questions/27341352/why-does-a-a-nodejs-array-shift-push-loop-run-1000x-slower-above-array-length-87)
- slice()  从某个已有的数组返回选定的元素  
- sort()     对数组的元素进行排序  
- splice()  删除元素，并向数组添加新元素.  
- toSource()  返回该对象的源代码.  
- toString()  把数组转换为字符串，并返回结果.  
- toLocaleString()  把数组转换为本地数组，并返回结果.  
- unshift()  向数组的开头添加一个或更多元素，并返回新的长度.  
- valueOf()  返回数组对象的原始值  
- flat(number?) 展平数组, number 表示到第几级.
- reduce() each element of the array, resulting in single output value.
- filter
- map
- every
- some
- forEach
- reduce

#### 遍历数组

```js
let fruits=["Apple","Banana"];
fruits.forEach(function (item, index, array) {
  console.log(item, index);
});
// Apple 0
// Banana 1
```

#### 移除重复元素

ES6中新的数据类型，Set

```js
let arr2 = [1, '1', 2, 3, 2, 1, '1', {key1: 1}, {key1: 2}]
console.log([...new Set(arr2)]) // [ 1, '1', 2, 3, { key1: 1 }, { key1: 2 } ]
```

```js
let arr = [1, 2, 3, 2, 1, 5, 5, 6, 5, 7]
let unique_arr = arr.sort().filter((elem, index, self) => {
   return !index || elem != self[index - 1]
})
console.log(unique_arr) // [ 1, 2, 3, 5, 6, 7 ]
```

```js
let arr = [1, 2, 3, 2, 1, 5, 5, 6, 5, 7]
function uniqArr(arr) {
  // 这里用了一个watch变量记录便利数组过程中元素是否存在过，不存在就保留，存在就过滤掉. 
   let watch = {}
   return arr.filter((elem, index, self) => {
      return watch[elem] ? false : true;
   })
}
```

## 对象

o = o   {} 表示: 如果o为null或undefined，则将o初始化空对象(即{})，否则o不变. 目的是防止o为null或未定义的错误.

## 变量

let 只在所在的代码块内有效

var 会有变量提升现象

const声明一个只读的常量.  

`const`实际上保证的，并不是变量的值不得改动，而是变量指向的那个内存地址所保存的数据不得改动.  
对于简单类型的数据(数值、字符串、布尔值)，值就保存在变量指向的那个内存地址，因此等同于常量. __但对于复合类型的数据(主要是对象和数组)__，变量指向的内存地址，保存的只是一个指向实际数据的指针，`const`只能保证这个指针是固定的(即总是指向另一个固定的地址)，至于它指向的数据结构是不是可变的，就完全不能控制了. 因此，将一个对象声明为常量必须非常小心.
如果真的想将对象冻结，应该使用Object.freeze方法. ```const foo = Object.freeze({});```.  
对象的属性也可以冻结. 下面是一个将对象彻底冻结的函数.

  ```js
  let constantize = (obj) => {
   Object.freeze(obj);
   Object.keys(obj).forEach( (key, i) => {
    if ( typeof obj[key] === 'object' ) {
      constantize( obj[key] );
    }
   });
  };
  ```

## 变量的解构赋值

```js
let [a, b, c] = [1, 2, 3];

let [foo, [[bar], baz]] = [1, [[2], 3]];
foo // 1
bar // 2
baz // 3

let [ , , third] = ["foo", "bar", "baz"];
third // "baz"

let [head, ...tail] = [1, 2, 3, 4];
head // 1
tail // [2, 3, 4]

let [x, y, ...z] = ['a'];
x // "a"
y // undefined
z // []

let [x, y, z] = new Set(['a', 'b', 'c']);
x // "a"
```

事实上，只要某种数据结构具有 Iterator 接口，都可以采用数组形式的解构赋值.

解构赋值允许指定默认值.

```js
let [foo = true] = [];
foo // true

let [x, y = 'b'] = ['a']; // x='a', y='b'
let [x, y = 'b'] = ['a', undefined]; // x='a', y='b'

let [x = 1] = [undefined];
x // 1

let [x = 1] = [null];
x // null

let { foo, bar } = { foo: 'aaa', bar: 'bbb' };
foo // "aaa"
bar // "bbb"

// 例一
let { log, sin, cos } = Math;

// 例二
const { log } = console;
log('hello') // hello

// 正确的写法
let x;
({x} = {x: 1});

const [a, b, c, d, e] = 'hello';
a // "h"
b // "e"
c // "l"
d // "l"
e // "o"

let {length : len} = 'hello';
len // 5

let {toString: s} = 123;
s === Number.prototype.toString // true

// 解构赋值时，如果等号右边是数值和布尔值，则会先转为对象. 
let {toString: s} = true;
s === Boolean.prototype.toString // true

function move({x = 0, y = 0} = {}) {
  return [x, y];
}
```

解构也可以用于嵌套结构的对象.

解构赋值的规则是，只要等号右边的值不是对象或数组，就先将其转为对象. 由于undefined和null无法转为对象，所以对它们进行解构赋值，都会报错.

### 用途

#### 交换变量的值

```js
let x = 1;
let y = 2;

[x, y] = [y, x];
```

#### 从函数返回多个值

// 返回一个数组

function example() {
  return [1, 2, 3];
}
let [a, b, c] = example();

// 返回一个对象

function example() {
  return {
   foo: 1,
   bar: 2
  };
}
let { foo, bar } = example();

#### 函数参数的定义

```js
// 参数是一组有次序的值
function f([x, y, z]) { ... }
f([1, 2, 3]);

// 参数是一组无次序的值
function f({x, y, z}) { ... }
f({z: 3, y: 2, x: 1});
```

#### 提取 JSON 数据

```js
let jsonData = {
  id: 42,
  status: "OK",
  data: [867, 5309]
};

let { id, status, data: number } = jsonData;

console.log(id, status, number);
```

#### 遍历 Map 结构

任何部署了 Iterator 接口的对象，都可以用for...of循环遍历. Map 结构原生支持 Iterator 接口，配合变量的解构赋值，获取键名和键值就非常方便.

```js
const map = new Map();
map.set('first', 'hello');
map.set('second', 'world');

for (let [key, value] of map) {
  console.log(key + " is " + value);
}
// first is hello
// second is world
如果只想获取键名，或者只想获取键值，可以写成下面这样. 

// 获取键名
for (let [key] of map) {
  // ...
}

// 获取键值
for (let [,value] of map) {
  // ...
}
```

#### 输入模块的指定方法

加载模块时，往往需要指定输入哪些方法. 解构赋值使得输入语句非常清晰.

```js
const { SourceMapConsumer, SourceNode } = require("source-map");
```

### 字符串的遍历器接口

```js
ES6 为字符串添加了遍历器接口(详见《Iterator》一章)，使得字符串可以被for...of循环遍历. 

for (let codePoint of 'foo') {
  console.log(codePoint)
}
// "f"
// "o"
// "o"
除了遍历字符串，这个遍历器最大的优点是可以识别大于0xFFFF的码点，传统的for循环无法识别这样的码点. 

let text = String.fromCodePoint(0x20BB7);

for (let i = 0; i < text.length; i++) {
  console.log(text[i]);
}
// " "
// " "

for (let i of text) {
  console.log(i);
}
// "𠮷"
```

### 模板字符串

[模板字符串](https://es6.ruanyifeng.com/#docs/string)
上面代码中，所有模板字符串的空格和换行，都是被保留的，比如\<ul>标签前面会有一个换行. 如果你不想要这个换行，可以使用trim方法消除它.

模板字符串中嵌入变量，需要将变量名写在${}之中. 大括号内部可以放入任意的 JavaScript 表达式，可以进行运算，以及引用对象属性. 模板字符串之中还能调用函数.

```js
function fn() {
  return "Hello World";
}

`foo ${fn()} bar`
// foo Hello World bar
```

## 箭头函数和this

(1)函数体内的this对象，就是定义时所在的对象，而不是使用时所在的对象.

(2)不可以当作构造函数，也就是说，不可以使用new命令，否则会抛出一个错误.

(3)不可以使用arguments对象，该对象在函数体内不存在. 如果要用，可以用 rest 参数代替.

(4)不可以使用yield命令，因此箭头函数不能用作 Generator 函数.

上面四点中，第一点尤其值得注意. this对象的指向是可变的，但是在箭头函数中，它是固定的.

```js
function foo() {
  setTimeout(() => {
   console.log('id:', this.id);
  }, 100);
}

var id = 21;

foo.call({ id: 42 });
// id: 42
```

上面代码中，setTimeout的参数是一个箭头函数，这个箭头函数的定义生效是在foo函数生成时，而它的真正执行要等到 100 毫秒后. 如果是普通函数，执行时this应该指向全局对象window，这时应该输出21. 但是，箭头函数导致this总是指向函数定义生效时所在的对象(本例是{id: 42})，所以输出的是42.

箭头函数可以让setTimeout里面的this，绑定定义时所在的作用域，而不是指向运行时所在的作用域. 下面是另一个例子.

```js
function Timer() {
  this.s1 = 0;
  this.s2 = 0;
  // 箭头函数
  setInterval(() => this.s1++, 1000);
  // 普通函数
  setInterval(function () {
   this.s2++;
  }, 1000);
}

var timer = new Timer();

setTimeout(() => console.log('s1: ', timer.s1), 3100);
setTimeout(() => console.log('s2: ', timer.s2), 3100);
// s1: 3
// s2: 0
```

上面代码中，Timer函数内部设置了两个定时器，分别使用了箭头函数和普通函数. 前者的this绑定定义时所在的作用域(即Timer函数)，后者的this指向运行时所在的作用域(即全局对象). 所以，3100 毫秒之后，timer.s1被更新了 3 次，而timer.s2一次都没更新.

箭头函数可以让this指向固定化，这种特性很有利于封装回调函数. 下面是一个例子，DOM 事件的回调函数封装在一个对象里面.

```js
var handler = {
  id: '123456',

  init: function() {
   document.addEventListener('click',
    event => this.doSomething(event.type), false);
  },

  doSomething: function(type) {
   console.log('Handling ' + type  + ' for ' + this.id);
  }
};
```

上面代码的init方法中，使用了箭头函数，这导致这个箭头函数里面的this，总是指向handler对象. 否则，回调函数运行时，this.doSomething这一行会报错，因为此时this指向document对象.

this指向的固定化，并不是因为箭头函数内部有绑定this的机制，实际原因是箭头函数根本没有自己的this，导致内部的this就是外层代码块的this. 正是因为它没有this，所以也就不能用作构造函数.

所以，箭头函数转成 ES5 的代码如下.

```js
// ES6
function foo() {
  setTimeout(() => {
   console.log('id:', this.id);
  }, 100);
}

// ES5
function foo() {
  var _this = this;

  setTimeout(function () {
   console.log('id:', _this.id);
  }, 100);
}
```

上面代码中，转换后的 ES5 版本清楚地说明了，箭头函数里面根本没有自己的this，而是引用外层的this.

请问下面的代码之中有几个this？

```js
function foo() {
  return () => {
   return () => {
    return () => {
      console.log('id:', this.id);
    };
   };
  };
}

var f = foo.call({id: 1});

var t1 = f.call({id: 2})()(); // id: 1
var t2 = f().call({id: 3})(); // id: 1
var t3 = f()().call({id: 4}); // id: 1
```

上面代码之中，只有一个this，就是函数foo的this，所以t1、t2、t3都输出同样的结果. 因为所有的内层函数都是箭头函数，都没有自己的this，它们的this其实都是最外层foo函数的this.

除了this，以下三个变量在箭头函数之中也是不存在的，指向外层函数的对应变量: arguments、super、new.target.

```js
function foo() {
  setTimeout(() => {
   console.log('args:', arguments);
  }, 100);
}

foo(2, 4, 6, 8)
// args: [2, 4, 6, 8]
```

上面代码中，箭头函数内部的变量arguments，其实是函数foo的arguments变量.

另外，由于箭头函数没有自己的this，所以当然也就不能用call()、apply()、bind()这些方法去改变this的指向.

```js
(function() {
  return [
   (() => this.x).bind({ x: 'inner' })()
  ];
}).call({ x: 'outer' });
// ['outer']
```

上面代码中，箭头函数没有自己的this，所以bind方法无效，内部的this指向外部的this.

长期以来，JavaScript 语言的this对象一直是一个令人头痛的问题，在对象方法中使用this，必须非常小心. 箭头函数”绑定”this，很大程度上解决了这个困扰.

## 一些注入攻击示例

```js
javascript:document.body.contentEditable='true'; document.designMode='on'; void 0 //随心所欲修改web页面
javascript:document.body.setAttribute('contenteditable', true);//
-javascript:alert("密码: "+document.querySelectorAll("input[type=password]")[0].value);//显示密码
```

## js 事件委托, 点击以后根据点击事件, 判断点击元素

## 事件冒泡与捕获

父到子 叫 捕获过程(capturing phase)
子到父 叫 冒泡过程(bubling phase)

一盆水, 上边的水看做父, 下边的看作子. 上边点一下, 层层传递到下层, 被鱼捕获到; 之后鱼又吐个泡, 泡又开始往上冒.
对应代码:

```html
<div id="water1">
  <div id="water2">
   <button>Fish</button>
  </div>
</div>
```

stopPropagation 阻止事件继续传递.

Event.cancelBubble，event.defaultPrevented

## EventTarget

EventTarget.addEventListener()
EventTarget.removeEventListener()

监听函数内部的this指向触发事件的那个元素节点.

Event.cancelable属性返回一个布尔值，表示事件是否可以取消. 该属性为只读属性，一般用来了解 Event 实例的特性.

Event.defaultPrevented. 取消事件. 属性返回一个布尔值，表示该事件是否调用过. 如果事件不能取消，调用Event.preventDefault()没有用.

Event.cancelBubble=true 阻止事件冒泡.

Event.preventDefault (只读)表示事件是否取消, ture 是取消.

## Libary

<https://github.com/lodash/lodash>

<https://github.com/ramda/ramda>

## 异步迭代

```js
array.forEach(async (item)=>{await gogo(item);})
```

## 原子操作

```js
Atomics
```

## 柯里化

arity（参数个数）是函数所需的形参的数量。 函数柯里化（Currying）意思是把接受多个 arity 的函数变换成接受单一 arity 的函数, 并且返回接受余下的参数而且__返回结果__的__新函数__的技术

函数柯里化(链式调用,闭包)和局部调用.

```js
var foo = function(a) {
  return function(b) {
    return a * a + b * b;
  }
}
```

## getter setter

```js
class Book {
  constructor(author) {
    this._author = author;
  }
  // getter
  get writer() {
    return this._author;
  }
  // setter
  set writer(updatedAuthor) {
    this._author = updatedAuthor;
  }
}
const novel = new Book('anonymous');
console.log(novel.writer);
novel.writer = 'newAuthor';
console.log(novel.writer);
```

## URL.createObjectURL()  Blob  url

将 File, Blob, or MediaSource object to create an object URL

```js
let file;// 从input 获取
let url = URL.createObjectURL(file)
// <img src="url">
```

```html
src="blob:https://video_url"
```

<https://stackoverflow.com/questions/30864573/what-is-a-blob-url-and-why-it-is-used>

```js
URL.revokeObjectURL() // 释放 URL.createObjectURL() 创建的url
```

下载 Blob 文件

```js
function downloadFileByBlob(blobUrl, filename) {
  const eleLink = document.createElement('a')
  eleLink.download = filename
  eleLink.style.display = 'none'
  eleLink.href = blobUrl
  // 触发点击
  document.body.appendChild(eleLink)
  eleLink.click()
  // 然后移除
  document.body.removeChild(eleLink)
}
```

### [Bolb 播放视频 MediaSource 分段加载视频](https://developer.mozilla.org/zh-CN/docs/Web/API/MediaSource#Browser_compatibility)

视频文件需要 Fragmented MP4 ， 且服务器需要支持 byte-range 。

<https://stackoverflow.com/questions/8616855/how-to-output-fragmented-mp4-with-ffmpeg>

```bash
ffmpeg -re -i infile.ext -g 52 \
-c:a aac -b:a 64k -c:v libx264 -b:v 448k \
-f mp4 -movflags frag_keyframe+empty_moov+default_base_moof \
output.mp4
```

```js
const video = document.querySelector('video');

const assetURL = 'frag_bunny.mp4';
// Need to be specific for Blink regarding codecs
// ./mp4info frag_bunny.mp4 | grep Codec
const mimeCodec = 'video/mp4; codecs="avc1.42E01E, mp4a.40.2"';

if ('MediaSource' in window && MediaSource.isTypeSupported(mimeCodec)) {// 检查是否可用
  const mediaSource = new MediaSource();
  //console.log(mediaSource.readyState); // closed
  video.src = URL.createObjectURL(mediaSource);
  // 添加监听
  mediaSource.addEventListener('sourceopen', sourceOpen);
} else {
  console.error('Unsupported MIME type or codec: ', mimeCodec);
}

function sourceOpen (_) {
  //console.log(this.readyState); // open
  const mediaSource = this;
  /**
   * 方法会根据给定的 MIME 类型创建一个新的 SourceBuffer 对象，然后会将它追加到 MediaSource 的 SourceBuffers 列表中。*/
  const sourceBuffer = mediaSource.addSourceBuffer(mimeCodec);
  fetchAB(assetURL, function (buf) {
    sourceBuffer.addEventListener('updateend', function (_) {
      video.play();
      // 在这里进行下一个视频的加载
      // sourceBuffer.appendBuffer(buf);
      // 视频结束时调用
      mediaSource.endOfStream();
        // URL.revokeObjectURL(video.src); 
      //console.log(mediaSource.readyState); // ended
    });
    sourceBuffer.appendBuffer(buf);
  
  });
};

function fetchAB (url, cb) {
  console.log(url);
  const xhr = new XMLHttpRequest;
  xhr.open('get', url);
  xhr.responseType = 'arraybuffer';
  xhr.onload = function () {
    cb(xhr.response);
  };
  xhr.send();
};


```

## Video.js

播放视频 m3u8 视频流
