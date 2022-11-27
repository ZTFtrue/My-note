#

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
            console.log(search) ;
        }
    }, []);
    return(<div></div>);
}
```

## Detial 参数

```tsx
function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Navigate replace to="/home" />}></Route>
        <Route path="home" element={<Home />}></Route>
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
    return (<div></div>)
}  
```
