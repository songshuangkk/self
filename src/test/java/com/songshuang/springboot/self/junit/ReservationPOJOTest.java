package com.songshuang.springboot.self.junit;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationPOJOTest {

  @Test
  public void myFirstTest() {
    assertEquals(2, 1 + 1);
  }

  @DisplayName("ParameterizedTest Test Case")
  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
  public void palindromes(String candidate) {
    assertTrue(candidate.length() > 0);
  }

  /**
   * 重复测试次数.
   */
  @RepeatedTest(10)
  public void repeatedTest() {
    System.out.printf("111");
  }
}
