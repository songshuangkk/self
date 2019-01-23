package com.songshuang.springboot.self.el;

import lombok.Data;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ElDemo {

  public static void main(String[] args) {

    ElModel elModel = new ElModel();
    elModel.setName("HaHaHaHa");
    elModel.setAge(11111);

    ExpressionParser spelParser = new SpelExpressionParser();

    // 设置要进行计算操作的对象进入上下文
    StandardEvaluationContext modelContext =new StandardEvaluationContext();
    modelContext.setRootObject(elModel);

    // 设置sPel的表达式的开始和结束的标志
    ParserContext context = new ParserContext() {
      @Override
      public boolean isTemplate() {
        return true;
      }

      @Override
      public String getExpressionPrefix() {
        return "${";
      }

      @Override
      public String getExpressionSuffix() {
        return "}";
      }
    };

    // 使用模板进行计算
    // 这里可以直接使用计算，也可以使用三目运算符
    Expression expression = spelParser.parseExpression("${(age  + 100) > 0 ? 'HaHaHa': 'WoWoWo'}", context);

    System.out.printf("value = %s\n", expression.getValue(modelContext, elModel, String.class));
  }

  @Data
  static class ElModel {

    private String name;

    private Integer age;
  }
}
