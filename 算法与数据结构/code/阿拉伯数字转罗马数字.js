function convertToRoman(num) {
    let a={
        1:"I",
        5:"V",
        10:"X",
        50:"L",
        100:"C",
        500:"D",
        1000:"M",
    };
    let str="";
    let i=0;
    while (num>0) {
        let t=num%10;
        num=parseInt(num/10);
        if(t<=3){ // 重复n次
            for (let index = 0; index < t; index++) {
                str=a[Math.pow(10,i)]+str;
            }
        }else if(t<=5){// 左减
            let temp=a[Math.pow(10,i)*5];
            for (let index = t; index < 5; index++) {
                temp=a[Math.pow(10,i)]+temp;
            }
            str=temp+str;
        }else if(t<=8){// 右加
            let temp=a[Math.pow(10,i)*5];
            for (let index = 5; index <t ; index++) {
                temp=temp+a[Math.pow(10,i)];
            }
            str=temp+str;
        }else{// 左减
            let temp=a[Math.pow(10,i)*10];
            for (let index = t; index < 10; index++) {
                temp=a[Math.pow(10,i)]+temp;
            }
            str=temp+str;
        }
        i++;
    }
    return str;
}
