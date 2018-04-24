package com.songshuang.springboot.self.apache.cli;

import org.apache.commons.cli.*;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CliDemo {

  public static void main(String[] args) throws ParseException {

    Options options = new Options();

    options.addOption("t", false, "display current time");

    // 为true的时候，表示需要额外的参数
    options.addOption("c", true, "country code");

    CommandLineParser parser = new PosixParser();

    CommandLine cmd = parser.parse(options, args);

    if (cmd.hasOption("t")) {
      System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
    } else {
      System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
    }

    // 获取参数匹配的外参数
    String value = cmd.getOptionValue("c");

    if (StringUtils.isEmpty(value)) {
      System.out.printf("the country code is empty error.");
    } else {
      System.out.printf("the country code is " + value);
    }
  }
}
