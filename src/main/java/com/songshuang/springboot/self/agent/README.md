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

