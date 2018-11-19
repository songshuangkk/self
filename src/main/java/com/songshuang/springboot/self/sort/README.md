# 排序

### 桶排序

- 桶排序是稳定的
  
- 桶排序是常见排序里最快的一种,比快排还要快…大多数情况下
  
- 桶排序非常快,但是同时也非常耗空间,基本上是最耗空间的一种排序算法

无序数组有个要求,就是成员隶属于固定(有限的)的区间,如范围为[0-9](考试分数为1-100等)

例如待排数字[6 2 4 1 5 9]

准备10个空桶,最大数个空桶

[6 2 4 1 5 9]           待排数组

[0 0 0 0 0 0 0 0 0 0]   空桶

[0 1 2 3 4 5 6 7 8 9]   桶编号(实际不存在)

 

1,顺序从待排数组中取出数字,首先6被取出,然后把6入6号桶,这个过程类似这样:空桶[ 待排数组[ 0 ] ] = 待排数组[ 0 ]

[6 2 4 1 5 9]           待排数组

[0 0 0 0 0 0 6 0 0 0]   空桶

[0 1 2 3 4 5 6 7 8 9]   桶编号(实际不存在)

 

2,顺序从待排数组中取出下一个数字,此时2被取出,将其放入2号桶,是几就放几号桶

[6 2 4 1 5 9]           待排数组

[0 0 2 0 0 0 6 0 0 0]   空桶

[0 1 2 3 4 5 6 7 8 9]   桶编号(实际不存在)

 

3,4,5,6省略,过程一样,全部入桶后变成下边这样

[6 2 4 1 5 9]           待排数组

[0 1 2 0 4 5 6 0 0 9]   空桶

[0 1 2 3 4 5 6 7 8 9]   桶编号(实际不存在)

 

0表示空桶,跳过,顺序取出即可:1 2 4 5 6 9

![](http://images.cnblogs.com/cnblogs_com/kkun/201111/201111251834157521.png)

## 鸽巢排序Pigeonhole sort


原理类似桶排序,同样需要一个很大的鸽巢[桶排序里管这个叫桶,名字无所谓了]

鸽巢其实就是数组啦,数组的索引位置就表示值,该索引位置的值表示出现次数,如果全部为1次或0次那就是桶排序

例如

var pigeonHole = new int[100];

pigeonHole[0]的值表示0的出现次数...

pigeonHole[1]的值表示1的出现次数...

pigeonHole[2]的值表示2的出现次数...
```java
// 鸽巢排序
        /// </summary>
        /// <param name="unsorted">待排数组</param>
        /// <param name="maxNumber">待排数组中的最大数,如果可以指定的话</param>
        /// <returns></returns>
        static int[] pogeon_sort(int[] unsorted, int maxNumber = 10)
        {
            int[] pogeonHole = new int[maxNumber + 1];
            foreach (var item in unsorted)
            {
                pogeonHole[item]++;
            }
            return pogeonHole;
            /*
             * pogeonHole[10] = 4; 的含意是
             * 在待排数组中有4个10出现,同理其它
             */
        }

        static void Main(string[] args)
        {
            int[] x = { 99, 65, 24, 47, 47, 50, 99, 88, 66, 33, 66, 67, 31, 18, 24 };
            var sorted = pogeon_sort(x, 99);
            for (int i = 0; i < sorted.Length; i++)
            {
                for (int j = 0; j < sorted[i]; j++)
                {
                    Console.WriteLine(i);
                }
            }
            Console.ReadLine();
        }
```