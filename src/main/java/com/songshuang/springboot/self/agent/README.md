# 实现Java Agent
Java Agent是从JDK1.5及以后引入的，其作用相当于你的main函数之前的一个拦截器，即在执行main函数前，先执行Agent中的代码。

Agent的代码与你的main方法在同一个JVM中运行，并被同一个类加载器所加载，被同一的安全策略 和上下文所管理。

## 定义Agent
在resources文件下的META-INF下创建MANIFSET.MF文件中添加指定的Agent对象

```javascript
Manifest-Version: 1.0
Premain-Class: com.songshuang.springboot.self.agent.MyAgent
Can-Redefine-Classes: true
```

Premain-Class: 用于指定Agent的class。
Manifset-Version：版本号

然后再pom中修改
```xml
<packaging>jar</packaging>
    <build>
        <finalName>my-agent</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>2.3.2</version>
                <configuration>
                    <archive>
                        <index>true</index>
                        <manifestFile>
                            src/main/resources/META-INF/MANIFEST.MF
                        </manifestFile>
                        <manifest>
                            <addDefaultImplementationEntries/>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
    </plugins>
    </build>
```

## 使用方式
在使用的时候，对上述的包进行打包生成jar包
然后在java -javaagent:xxxx.jar     xxxxx
xxxx.jar用于指定生成的jar路径
如:

```jshelllanguage
javac test.java => test.class
java -javaagent:/demo/target/my-agent.jar   test
```

如果要传递参数:
```jshelllanguage
java -javaagent:my-agent.jar="hello world" test
```

### 使用ByteBuddy实现Java Agent.
ByteBuddy不仅仅是为了生成Java-Agent，它提供的API甚至可以改变重写一个Java类，本文我们使用其API实现和第二节一样的功能，给目标类中的函数统计其调用耗时。

引入ByteBuddy.
```xml
<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy</artifactId>
    <version>1.5.7</version>
</dependency>

<dependency>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy-agent</artifactId>
    <version>1.5.7</version>
</dependency>
```

更改Agent的pom文件:
```xml
<plugin> 
  <groupId>org.apache.maven.plugins</groupId>  
  <artifactId>maven-shade-plugin</artifactId>  
  <executions> 
    <execution> 
      <phase>package</phase>  
      <goals> 
        <goal>shade</goal> 
      </goals> 
    </execution> 
  </executions>  
  <configuration> 
    <artifactSet> 
      <includes> 
        <include>javassist:javassist:jar:</include>  
        <include>net.bytebuddy:byte-buddy:jar:</include>  
        <include>net.bytebuddy:byte-buddy-agent:jar:</include> 
      </includes> 
    </artifactSet> 
  </configuration> 
</plugin>
```
通过AgentBuilder方法，生成一个Agent。这里有两点需要特别说明：其一是在AgentBuilder.type处，这里可以指定需要拦截的类；其二是在builder.method处，这里可以指定需要拦截的方法。当然其API支持各种isStatic、isPublic等等一系列方式。


### ByteBuddy的使用

具体调用和使用方式可以参考: [ByteBuddy][https://notes.diguage.com/byte-buddy-tutorial/#calling-a-super-method]

#### 创建Java Agent
```java
class ToStringAgent {
  public static void premain(String arguments, Instrumentation instrumentation) {
    new AgentBuilder.Default()
        .type(isAnnotatedWith(ToString.class))
        .transform(new AgentBuilder.Transformer() {
      @Override
      public DynamicType.Builder transform(DynamicType.Builder builder,
                                              TypeDescription typeDescription,
                                              ClassLoader classloader) {
        return builder.method(named("toString"))
                      .intercept(FixedValue.value("transformed"));
      }
    }).installOn(instrumentation);
  }
}
```

#### 属性和方法

```java
String toString = new ByteBuddy()
  .subclass(Object.class)
  .name("example.Type")
  .make()
  .load(getClass().getClassLoader())
  .getLoaded()
  .newInstance() // Java reflection API
  .toString();
```

--- 

```java
String toString = new ByteBuddy()
  .subclass(Object.class)
  .name("example.Type")
  .method(named("toString")).intercept(FixedValue.value("Hello World!"))
  .make()
  .load(getClass().getClassLoader())
  .getLoaded()
  .newInstance()
  .toString();
```