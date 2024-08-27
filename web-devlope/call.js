function getGlobalObject(){
    return this;
}
Function.prototype.callFn = function call(thisArg, ...args){
    if(typeof this !== 'function'){ 
        // 不一定是函数调用, 比如 Object.prototype=unction.prototype.callFn ;
        throw new TypeError(this + ' is not a function'); } 
    // 非严格模式
    if(thisArg===void 0||thisArg===null){
        thisArg = getGlobalObject();
    }

    thisArg=new Object(thisArg);
    var __fn='_fn';// 问题: 如果 thisArg 原来就有_fn 会覆盖, 随机生成key, 检查(hasOwnProperty)是否有对象, 或者使用 Symbol('foo') === Symbol('foo') 
    thisArg[__fn]=this;
    console.log("this---",this);
    console.log("thisArg---",thisArg);
    var result = thisArg[__fn](...args);
    console.log("res---",result);
    delete thisArg[__fn];
    return result;
}

function Product(name, price) {
    this.name = name;
    this.price = price;
    console.log(this.category)
    return {"Product":"Product"};
}
  
function Food(name, price) {
  this.category = 'food';
  let a=Product.callFn(this, name, price);
  console.log("a",a);
}
var food=new Food('cheese', 5);
console.log(food);
console.log(food.name);
