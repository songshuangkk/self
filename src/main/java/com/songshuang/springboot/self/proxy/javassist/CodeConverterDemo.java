package com.songshuang.springboot.self.proxy.javassist;

import javassist.*;
import org.springframework.context.annotation.Bean;

/**
 * 使用javassist.CodeConverter进行字节码增强.
 */
public class CodeConverterDemo {

  public static void main(String[] args) {
    if (args.length >= 3) {
      try {
        // Set up class loader with translator
        ConverterTranslator converterTranslator = new ConverterTranslator();
        ClassPool pool = ClassPool.getDefault();
        CodeConverter converter = new CodeConverter();
        CtMethod smeth = pool.get(args[0]).getDeclaredMethod(args[1]);
        CtMethod pmeth = pool.get("TranslateConvert").getDeclaredMethod("reportSet");
        converter.insertBeforeMethod(smeth, pmeth);
        converterTranslator.setConverter(converter);
        Loader loader = new Loader(pool);

        // invoke "main" method of application class
        String[] pargs = new String[args.length - 3];
        System.arraycopy(args, 3, pargs, 0, pargs.length);
        loader.run(args[2], pargs);
      } catch (Exception e) {
        e.printStackTrace();
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    }
  }

  public static void reportSet(CodeConverterModel target, String value) {
    System.out.printf("Call to set value %s\n", value);
  }

  public static class ConverterTranslator implements Translator {

    private CodeConverter converter;

    private void setConverter(CodeConverter converter) {
      this.converter = converter;
    }

    @Override
    public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {

    }

    @Override
    public void onLoad(ClassPool classPool, String s) throws NotFoundException, CannotCompileException {

    }

    public void onWrite(ClassPool pool, String name) throws CannotCompileException, NotFoundException {
      CtClass ctClass = pool.get(name);
      ctClass.instrument(converter);
    }
  }
}
