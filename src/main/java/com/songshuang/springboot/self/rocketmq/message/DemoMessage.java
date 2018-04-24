package com.songshuang.springboot.self.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created By songshuang on 2018/3/26
 * <p>
 * Talk is cheap. Show me the code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoMessage {

  private String message;

  private String name;
}
