# React

## 路由参数

### 路径 query 参数

```tsx
const Home = () => {
  const useCustomSearchParams = () => {
    const [search, setSearch] = useSearchParams();
    const searchAsObject = Object.fromEntries(new URLSearchParams(search));
    return [searchAsObject, setSearch];
  };
  const [search, setSearch] = useCustomSearchParams();
  useEffect(() => {
    // @ts-ignore
    if (search && search.token) {
      // @ts-ignore
      console.log(search);
    }
  }, []);
  return <div></div>;
};
```

### Detial 参数

```tsx
function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Navigate replace to="/home" />}></Route>
        <Route path="home/:token" element={<Home />}></Route>
        <Route path="detail">
          <Route path=":detailId" element={<Detail />}></Route>
        </Route>
      </Routes>
    </div>
  );
}

navigate(`/detail/${detailId}`, { replace: false });

const Detail = () => {
  let { detailId } = useParams();
  return <div></div>;
};
```

## 性能优化 只渲染可视区域

<https://github.com/bvaughn/react-virtualized>

## useEffect useLayoutEffect

useLayoutEffect（不常用）

useEffect  在全部渲染完毕后才会执行；
useLayoutEffect  会在 浏览器 layout  之后， painting  之前执行；
使用方式同 useEffect  相同，但它会在所有的 DOM  变更之后同步调用 effect ；
可以使用它来读取 DOM  布局并同步触发重渲染；
在浏览器执行绘制之前 useLayoutEffect  内部的更新计划将被同步刷新；
尽可能使用标准的 useEffect  以避免阻塞视图更新。

## React.forwardRef

子组件 暴露 一个组件 给父元素（父组件获取到子组件的一个组件）

forwardRef lets your component expose a DOM node to parent component with a ref.

<https://react.dev/reference/react/forwardRef>

## 环境配置

Install

```sh
 npm install env-cmd
```

分别在项目根目录创建文件`.env.development`,`.env.production` 表示`development` 和 `production` 环境.
内容示例

```env
REACT_APP_API_URL='https://gogo.destory.com'
```

配置`package.json`

```json
  "start": "env-cmd -f .env.development  react-scripts start",
  "build-dev": "env-cmd -f .env.development react-scripts build",
  "build-prod": "env-cmd -f .env.production react-scripts build",
```

```sh
npm run build-dev # 对应命令
```

使用时导入:

```js
process.env.REACT_APP_VIDEO_URL
```

## 阻止生成SourceMap

配合上节"环境配置"在相应的配置文件, 如: `.env.production` 添加 `GENERATE_SOURCEMAP=false`

<https://stackoverflow.com/a/60565725>

## Store

Recoil

Redux too difficult
