package com.songshuang.springboot.self.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("com.songshuang.springboot.self.annotation.CheckGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CheckGetterProcessor extends AbstractProcessor {

  /**
   * Process方法接收两个参数，分别代表注解处理器所能处理的注解类型，
   * 以及囊括当前轮生成的抽象语法树的RoundEnvironment.
   *
   * @param annotations
   * @param roundEnv
   * @return
   */
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    roundEnv.getElementsAnnotatedWith(CheckGetter.class);   // 获取所有被@CheckGetter注解的类.
    return false;
  }
}
