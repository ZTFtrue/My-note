/**
 * 计算斐波那契数列
 * 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, …
 */
let index = 0;
let MAX_INDEX = 11;

function recurFib(n) {
    if (n < 2) {
        return n;
    } else {
        return recurFib(n - 1) + recurFib(n - 2);
    }
}
console.log(recurFib(10)); // 显示55

function dynFib(n) {
    var val = []; // 每一步的值,记录下来
    for (var i = 0; i <= n; ++i) {
        val[i] = 0;
    }
    if (n == 1 || n == 2) {
        return 1;
    } else {
        val[1] = 1;
        val[2] = 2;
        for (var i = 3; i <= n; ++i) {
            val[i] = val[i - 1] + val[i - 2];
        }
        return val[n - 1];
    }
}

function iterFib(n) {
    var last = 1; // 记录上一个值
    var nextLast = 1;
    var result = 1;
    for (var i = 2; i < n; ++i) {
        result = last + nextLast;
        nextLast = last;
        last = result;
    }
    return result;
}
