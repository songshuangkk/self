#JNI

使用JNI调用C代码

Java代码如下:
```java
public class JNITest {
    public native void sayHello();

    static {
        // 设置java.library.path的路径，使之能够调用到动态库
        System.setProperty("java.library.path", ".");
        // 调用动态库
        System.loadLibrary("test");
    }

    public static void main(String[] args) {
        JNITest jniTest = new JNITest();
        jniTest.sayHello();
    }
}
```

然后将该java文件进行编译
```jshelllanguage
javac JNITest.java
```

再将生成的class文件进行生成对应的jni头文件.
```jshelllanguage
javah -jni JNITest
```

这样就会生成一个JNITest.h的头文件.

然后创建一个C++的文件(比如取名为JNITest.cpp或者其他名字)，对这个JNITest.h头文件中的定义方法进行实现:
```
#include <iostream>
#include "JNITest.h"

using namespace std;

JNIEXPORT void JNICALL Java_JNITest_sayHello
  (JNIEnv *, jobject) {
    cout << "Jni Out Put\n";
  }
```

下面是比较关键的一步，要生成动态库文件，这样java程序需要调用这个动态库的代码才能调用到具体的native的实现。
```jshelllanguage
g++ -shared -o libtest.jnilib -I /Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/include/ -I /Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/include/darwin/
```

这里需要指出的一点，在我们使用javah生成的jni头文件中，它include了jni.h这个头文件，由于这个头文件不是在C的library路径下，这个
时候，需要我们进行一个指定的路径进行引入。但是在jni.h的过程，还有可能会依赖ni_md.h头文件，所以我们也要使用同样的方式将他们引入.

生成了动态库libtest.jnilib文件之后，这样我们就能正确调用了:
```jshelllanguage
java JNITest

Jni Out Put
```

这里需要指出的是为什么我们要生成的动态库文件是libtest.jnilib文件而不是libtest.so文件。这个是由于System.loadLibrary()的时候，内部进行
了一个平台的区分: 

```java
if (OSType.isMac()) {
  var2 = "lib" + var1 + ".jnilib";
  var3 = this.findLibrary0(var2);
  if (var3 != null) {
    return var3;
  }
}
```

内部的实现如上，所以我们会有这样的一个命名。

以上就是我们对JNI的一个简单调用的过程使用.