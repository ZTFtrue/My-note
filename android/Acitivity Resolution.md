# Activity

如果已经启动了四个Activity: A，B，C和D. 在D Activity里，我们要跳到B Activity，同时希望C finish掉，可以在startActivity(intent)里的intent里添加flags标记，如下所示:

```java
    Intent intent = new Intent(this, B.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
```

这样启动B Activity，就会把D，C都finished掉，如果你的B Activity的启动模式是默认的(multiple) ，则B Activity会finished掉，再启动一个新的Activity B.   如果不想重新再创建一个新的B Activity，则在上面的代码里再加上:

```java
    1. intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
```

这样B Activity就会再创建一个新的了，而是会重用之前的B Activity，同时调用B Activity的onNewIntent()方法.

## 问题

1. 多activity中退出整个程序，例如从A->B->C->D，这时我需要从D直接退出程序. 

解决问题:

我们知道Android的窗口类提供了历史栈，我们可以通过stack的原理来巧妙的实现，这里我们在D窗口打开A窗口时在Intent中直接加入标志Intent.FLAG_ACTIVITY_CLEAR_TOP，再次开启A时将会清除该进程空间的所有Activity. 

在D中使用下面的代码:

```java
    Intent intent = new Intent();
    intent.setClass(D.this, A.class);  
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //注意本行的FLAG设置
    startActivity(intent);  
    finish();  
```

关掉自己
在A中加入代码:

```java
    Override  
    protected void onNewIntent(Intent intent) {  
    // TODO Auto-generated method stub  
    super.onNewIntent(intent);  
    //退出  
     if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {  
     finish();  
     }  
    }

//  A的Manifest.xml配置成android:launchMode="singleTop"
```

原理总结:

一般A是程序的入口点，从D起一个A的activity，加入标识Intent.FLAG_ACTIVITY_CLEAR_TOP这个过程中会把栈中B，C，都清理掉. 因为A是android:launchMode="singleTop"不会调用oncreate(),而是响应onNewIntent()这时候判断Intent.FLAG_ACTIVITY_CLEAR_TOP，然后把A finish()掉. 栈中A,B,C,D全部被清理. 所以整个程序退出了. 

可以利用清理历史栈的方法，来巧妙关闭所有activity，首先用一个设置为不可见的activity A来启动程序，这个activity A的作用只是用来垫栈底，只有启动和退出程序才会用到这个activity，而你需要退出的时候，只需要跳转至这个activity A  ，并让A  finish自己就可以实现关闭所有的activity. 

```java
    Intent intent = new Intent();  
    intent.setClass(B.this, A.class);     //B为你按退出按钮所在的activity  
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //最关键是这句  
    startActivity(intent);  
```

Intent.FLAG_ACTIVITY_CLEAR_TOP使得处于栈底的A发挥推土机的作用，从最底层把栈里所有的activity都清理掉，再在自己的oncreate方法加一句finish结束自己，即可实现退出. 不放心的话，可以在A的ondestroy方法中加上system.exit(0) ,连跳转过程中的线程也可以终止的. 

-------------
