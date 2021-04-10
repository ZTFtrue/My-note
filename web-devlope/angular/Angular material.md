# 手把手教你如何用 Angualr material 自定义主题

## 做准备

1. 第一步, 在src目录下新建```custom-theme.scss```文件.
2. 第二步, 找到```angualr.json```文件(一般在项目根目录中)并打开, 在到```"styles"``` , 在其中添加 ```"src/custom-theme.scss"```, 就是刚才创造的文件.
3. 打开```custom-theme.scss``` , 添加如下代码:

```scss
@import '~@angular/material/theming';
// Plus imports for other components in your app.

// Include the common styles for Angular Material. We include this here so that you only
// have to load a single css file for Angular Material in your app.
// Be sure that you only ever include this mixin once!
// 导入核心混入器，确保只导入一次
@include mat-core();
```

如果你使用SCSS样式,其他SCSS文件不应该导入自定义主题文件. 因为这会使CSS的输出中复制样式. 如果要使用自定义主题(如```$candy-app-theme```)在其他SCSS文件，则应将主题对象的定义从拆分为独立的文件.主题文件可以与应用程序的其余CSS串联并缩小.

## 定义主题

### 做一个深色主题

```scss
// 定义主调色板
$dark-primary: mat-palette($mat-gray);
// 强调调色板
$dark-accent: mat-palette($mat-blue-gray, A700, 100, A900);
// 警告调色板
$dark-warn: mat-palette($mat-pink, A500, A100, A900);
$dark-theme: mat-dark-theme($dark-primary, $dark-accent, $dark-warn);

.bookreader-dark-theme {
  @include angular-material-theme($dark-theme);
  $primary: map-get($dark-theme, primary);
  $accent: map-get($dark-theme, accent);
  $warn: map-get($dark-theme, warn);
//   color: white;
}
```

问题:

1. 这里的调色板怎么用?
  使用Angular Material的```mat-palette```混入,```mat-palette```接受一个基本调色板, 比如```red```并返回一个新调色板，该调色板带有给定基本调色板的“浅”，  “暗”和“特定于材质”的色相值的“对比”颜色.

    ```scss
    // mat-palette 接受的参数
    @function mat-palette($base-palette, $default: 500, $lighter:   100, $darker: 700)
    ```

    ```base-palette``` 是基本调色板,[在这里,文章末尾可以找到](https://material.io/design/color/#tools-for-picking-colors), 比如   ```mat-red```,   ```mat-yellow```, ```mat-gray``` 等等.

    ```default```,```lighter```,```darker``` 可选传入, 如需传入按调色版给的值传入即可,如```100```, ```A200```.

2. 如何让主题应用

    在要应用的组件或元素上直接```calss="bookreader-dark-theme"```即可.或者

    ```ts
    // element 是你要应用的元素
    element.classList.toggle(theme);
    ```

    基于浮层的组件,如对话框,要这么使用

    ```ts
    import {OverlayContainer} from '@angular/cdk/overlay';

    @NgModule({
      // ...
    })
    export class UnicornCandyAppModule {
      constructor(overlayContainer: OverlayContainer) {
        overlayContainer.getContainerElement().classList.add  ('bookreader-dark-theme');
      }
    }
    ```

    或者直接```<body class="bookreader-dark-theme">```, 不过,这会影响到全局

3. 如何动态更换主题

    ```ts
    // element 是你要应用的元素
    element.classList.replace('bookreader-dark-theme',  'bookreader-amber-theme');
    ```

    ```ts
    constructor( @Inject(DOCUMENT) private document: Document,) {}
    this.document.body.classList.replace(this.lastTheme, theme))
    ```

4. 这些色板分别表示什么?

    - 主调色板: 那些在所有屏幕和组件中广泛使用的颜色.
    - 强调调色板: 那些用于浮动按钮和可交互元素的颜色.
    - 警告调色板: 那些用于传达出错状态的颜色.
    - 前景调色板: 那些用于问题和图标的颜色.
    - 背景色调色板: 那些用做原色背景色的颜色.
