# Conda工具使用

**Anaconda:**[http://continuum.io/downloads](http://continuum.io/downloads)

**Miniconda:**[https://conda.io/miniconda.html](https://conda.io/miniconda.html)

```bash
   conda update --help
   conda --version
   conda update conda
```

## 环境管理

```bash
#  conda create --name 环境名 默认安装包
conda create --name snowflakes biopython
conda create -n snowflakes biopython

# 激活环境
source activate snowflakes

#这将创建第二个新的环境，名为 /envs/bunnies，且包含Python3和Astroid、Babel. 
conda create --name bunnies python=3 astroid babel

#列出所有环境
conda info --envs

#验证当前环境
conda info --envs

#将当前环境的路径更改回root: 
source deactivate

# 制作环境的完整副本
conda create --name flowers --clone snowflakes

#删除环境
#如果你真的不想要一个名为flowers的环境，只需删除它如下: 
conda remove --name flowers --all

```

## python 管理

### 检查Python版本

首先让我们检查一下可以安装哪些版本的Python:

```sh
    conda search --full-name python
```

你可以使用`conda search python`来显示名字中包含的所有包  
文本`python`或添加`--full-name`选项来指定.

### **安装不同版本的Python**

假如你需要Python3来学习编程，但你不想通过更新来覆盖你的Python2.7环境. 你可以创建并激活名为snakes的新环境，然后安装最新版本的Python3，命令如下:

```sh
    conda create --name snakes python=3
```

* Linux，OS X: `source activate snakes`
* Windows: `activate snakes`

**提示**: 明智的做法是将这个环境命名为`python`这样的描述性名称.

### **验证添加的环境**

要验证是否已添加了snakes环境，请键入以下命令:

```sh
    conda info --envs
```

Conda显示所有环境的列表，当前环境显示在前面的提示中的(括号)或\[括号\]里:

```sh
    (snakes)
```

### **在新环境中验证Python版本**

验证snakes环境使用Python3版本:

```sh
    python --version
```

### **使用不同版本的Python**

要切换到新环境使用不同版本的Python，只需要激活它. 让我们切换回默认值，2.7:

* Linux，OS X: `source activate snowflakes`
* Windows: `activate snowflakes`

### **在环境中验证Python版本**

验证snowflakes环境是否使用安装conda时使用的相同Python版本:

```sh
    python --version
```

### **停用此环境**

在雪花环境中完成工作后，停用此环境  
将您的PATH恢复到之前的状态:

* Linux，OS X: `source deactivate`
* Windows: `deactivate`

> 4.4 管理包
> -------

我们来认识包. 当我们创建一个新的环境时(Astroid，Babel和一个具体的版本的Python)，我们已经安装了几个包. 我们将检查我们有什么包，检查什么是可用的，查找特定的包并安装它. 然后我们会查找并安装存在于Anaconda.org存储库中的包，安装更使用pip install的包，以及安装一个商业包.

### **查看在环境中安装的软件包和版本的列表**

使用此选项可查看环境中安装的是哪个版本的Python或其他程序，或者确认已添加或删除了包. 在您的终端窗口中，只需键入:  ```conda list```

**用conda install命令查看使可用的软件包列表**

可用于conda安装的软件包列表(按Python版本排序)可从[http://docs.continuum.io/anaconda/pkg-docs.html](http://docs.continuum.io/anaconda/pkg-docs.html)得到.

**搜索包**

首先让我们检查一下我们想要的软件包是否可供conda安装:

    conda search beautifulsoup4

这将显示包，因此我们知道它是可用的.

**安装新软件包**

我们将在当前环境中安装Beautiful Soup，使用conda安装如下:

    conda install --name bunnies beautifulsoup4

**注意**: 你必须告诉conda环境的名称(`--name bunnies`)，否则它将安装在当前环境.

现在激活bunnies环境，并做一个conda列表看到安装的新程序:

* Linux，OS X: `source activate bunnies`
* Windows: `activate bunnies`

所有平台:

    conda list

### **从Anaconda.org安装软件包**

对于使用conda install不可用的软件包，我们接下来看看Anaconda.org. Anaconda.org是一个用于公共和私人包存储库的包管理服务. Anaconda.org是Continuum Analytics产品，就像Anaconda和Miniconda.

**提示**: 您不需要注册到Anaconda.org下载文件.

要从Anaconda.org下载到当前环境，我们将通过键入我们想要的包的完整的URL来指定Anaconda.org作为“通道”.

在浏览器中，转到[http://anaconda.org](http://anaconda.org/). 我们正在寻找一个名为“bottleneck”的包，在左上角名为“Search Anaconda Cloud”的框中，输入“bottleneck”，然后单击“Search”按钮.

在Anaconda.org上有十多个bottleneck可用，但我们想要的最多下载量的副本. 因此，你可以通过点击“下载”标题按下载数量进行排序.

通过单击软件包名称选择下载量最多的版本. 这将带您到Anaconda.org详细信息页面，其中显示用于下载的确切命令:

    conda install --channel https://conda.anaconda.org/pandas bottleneck

**检查包下载是否正确**

    conda list

### **使用pip安装软件包**

对于conda或Anaconda.org不提供的软件包，我们经常可以使用pip(“pip installs packages”的缩写)来安装软件包.

**提示**: Pip只是一个包管理器，所以它不能为您管理环境.  Pip甚至不能更新Python，因为不像conda，它不把Python当做一个包. 但它确实安装了一些conda没有的东西.  pip和conda都包括在Anaconda和Miniconda.

我们激活想放置程序的环境，然后用pip安装一个名为“See”的程序:

* Linux，OS X: `source activate bunnies`
* Windows: `activate bunnies`

所有平台:

    pip install see

**验证pip安装**

检查看是否已安装:

    conda list

### **安装商业包**

安装商业包与用conda安装任何其他包相同. 因此，作为示例，让我们安装，然后删除Continuum的商业包IOPro的试用版，这可以加速你的Python处理:

    conda install iopro

提示: 除了学术用途，此免费试用期在30天后过期.

现在，您可以使用conda命令，从Anaconda.org下载或使用pip install安装和验证任何您想使用conda的软件包，无论是开源还是商业.

> 4.5 删除软件包，环境或conda
> ------------------

让我们结束这个测试，通过删除一个或多个测试包，环境或conda.

### **删除包**

如果你决定不继续使用商业包IOPro，您可以从bunnies环境中删除它:

    conda remove --name bunnies iopro

**确认程序已删除**

使用conda列表确认IOPro已被删除:

    conda list

### **删除环境**

我们不再需要snakes环境，因此输入命令:

    conda remove --name snakes --all

**验证环境已删除**

要验证蛇的环境现在已被删除，请键入命令:

    conda info --envs

Snakes不再显示在环境列表中，因此我们知道它已被删除.

### **删除conda**

* Linux，OS X:

删除Anaconda或Miniconda安装目录:

    rm -rf ~/miniconda OR  rm -rf ~/anaconda
