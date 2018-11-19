# ClassLoader

## 三种缺省类加载器
当一个JVM启动的时候，Java默认有三种类加载器
- 启动(Bootstrap)类加载器：Bootstrap类加载器是由C和C++实现的类加载器，它负责将 <Java_Runtime_Home>/lib 或者由 -Xbootclasspath 指定位置下的类库加载到内存中。由于是native的，所以我们无法直接接触它，也无法直接引用它。在JDK的ClassLoader类里可以看到关于它的方法调用都是private native的
- 扩展(Exttension)类加载器：ExtClassLoader是由Sun公司提供的实现，它负责将 < Java_Runtime_Home >/lib/ext 或者由系统变量 java.ext.dir 指定位置中的类库加载到内存中，在 sun.misc.Launcher$ExtClassLoader 可看到源码，由于它的访问权限是default，所以只能是包内可见，我们外部无法直接引用它
- 应用(Application)类加载器(也称系统类加载器SystemClassLoader)：AppClassLoader也是由Sun公司提供的实现，它负责将系统类路径（CLASSPATH）中指定的类库加载到内存中，在 sun.misc.Launcher$AppClassLoader 可看到源码，它的访问权限同样是default，所以只能是包内可见，我们外部无法直接引用它

## 双亲委派机制

俗的讲，就是某个特定的类加载器在接到加载类的请求时，它首先会查看自己已加载的类中是否有这个类，如果有就返回，如果没有就将加载任务委托给父类(parent)加载器加载，依次递归，如果父类加载器可以完成类加载任务，就成功返回，只有当父类加载器无法完成此加载任务时，才自己去加载

双亲委派机制下三种类加载器的关系(图片来自网络)：

![](https://static.oschina.net/uploads/img/201711/24204324_RUZ0.png)

ClassLoaderA和ClassLoaderB是我们自己实现的类加载器，这里都指定了其父加载器为APPClassLoader，当然你也可以指定ClassLoaderA的父加载器为ClassLoaderB，但由于往上最多只能拿到SystemClassLoader的引用，所以父加载器最多只能指定到SystemClassLoader
通过 java.lang.ClassLoader.getSystemClassLoader 我们可以获取到JVM启动时加载ClassPath里jar包的应用类加载器SystemClassLoader，由此我们可以从代码上看到上面的关系图：
![](https://static.oschina.net/uploads/img/201711/24204352_XOUK.png)

这里可以看到扩展类加载器的parent为null，并不是Bootstrap类加载器，那双亲委派到这一级是如何实现的呢？ 其次应用类加载器的父加载器为什么是扩展类加载器呢？

```java
public Launcher() {
  Launcher.ExtClassLoader var1;
  try {
    // 先创建扩展类加载器，加载<Java_Runtime_Home>/lib/ext下的jar包，扩展类加载器的构造方法默认指定的父类加载器就是null，因为我们引用不到BootstrapClassLoader
    var1 = Launcher.ExtClassLoader.getExtClassLoader();
  } catch (IOException var10) {
    throw new InternalError("Could not create extension class loader");
  }

  try {
    // 接着创建应用类加载器，并指定其父加载器为上面创建的ExtClassLoader，由此其父加载器为ExtClassLoader的关系成立
    this.loader = Launcher.AppClassLoader.getAppClassLoader(var1);
  } catch (IOException var9) {
    throw new InternalError("Could not create application class loader");
  }
  // 并把当前现场的类加载器设置为应用类加载器
  Thread.currentThread().setContextClassLoader(this.loader);
  
  // ... other code 
}
```

## 双亲委派的机制

除启动类加载器是native实现的外，其他所有类加载器都是继承自 java.lang.ClassLoader 抽象类，该类有一个protected的loadClass方法，双亲委派机制就在该方法中
```java
protected Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException
{
    synchronized (getClassLoadingLock(name)) {
        // First, check if the class has already been loaded
        Class c = findLoadedClass(name);
        if (c == null) {
            long t0 = System.nanoTime();
            try {
                // 检查父加载器
                if (parent != null) {
                    c = parent.loadClass(name, false);
                } else {
                    // 使用Bootstrap类加载器加载，所以当ExtClassLoader的parent为null时，它会请求Bootstrap类加载器加载，这样双亲委派机制就是成立的
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }

            if (c == null) {
                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();
                // 自己加载
                c = findClass(name);

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
        }
        if (resolve) {
            resolveClass(c);
        }
        return c;
    }
}
```

其中 findBootstrapClassOrNull 方法调用了 native 的 findBootstrapClass 方法，所以是映射到了Bootstrap类加载器的

再来看看扩展类加载器和应用类加载器的类图

![](https://static.oschina.net/uploads/img/201711/24205118_5gIj.png)