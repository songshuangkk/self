#include <iostream>
#include "JNITest.h"

using namespace std;

JNIEXPORT void JNICALL Java_JNITest_sayHello
  (JNIEnv *, jobject) {
    cout << "Jni Out Put\n";
  }