#Guava 使用

## Base 使用

---

### NULL

* Optional
Optional 在创建的时候，就会进行判断是否为NULL，在第一时间进行判断.
```java


Optional<Integer> possible = Optional.of(t);

possible.isPresend();
possible.get();
```

Optional.fromNullable(T);表示能够返回可能为null的数据。

* Preconditions

Preconditions用来进行一个参数的判断。
```java

import com.google.common.base.Preconditions;

Preconditions.checkNotNull(data, errorMessage);
Preconditions.checkArgument();

```
