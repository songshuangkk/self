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
 static int[] pogeon_sort(int[] unsorted, int maxNumber = 10) {
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

## 计数排序
当输入的元素是 {\displaystyle n} n个 {\displaystyle 0} {\displaystyle  0 }到 {\displaystyle k}  k 之间的整数时，它的运行时间是 {\displaystyle \Theta (n+k)} {\displaystyle \Theta (n+k)}。计数排序不是比较排序，排序的速度快于任何比较排序算法。

由于用来计数的数组 {\displaystyle C}  C 的长度取决于待排序数组中数据的范围（等于待排序数组的最大值与最小值的差加上1），这使得计数排序对于数据范围很大的数组，需要大量时间和内存。例如：计数排序是用来排序0到100之间的数字的最好的算法，但是它不适合按字母顺序排序人名。但是，计数排序可以用在基数排序算法中，能够更有效的排序数据范围很大的数组。

通俗地理解，例如有10个年龄不同的人，统计出有8个人的年龄比A小，那A的年龄就排在第9位，用这个方法可以得到其他每个人的位置，也就排好了序。当然，年龄有重复时需要特殊处理（保证稳定性），这就是为什么最后要反向填充目标数组，以及将每个数字的统计减去1。算法的步骤如下：

找出待排序的数组中最大和最小的元素
统计数组中每个值为 {\displaystyle i} i的元素出现的次数，存入数组 {\displaystyle C}  C 的第 {\displaystyle i} i项
对所有的计数累加（从 {\displaystyle C}  C 中的第一个元素开始，每一项和前一项相加）
反向填充目标数组：将每个元素 {\displaystyle i} i放在新数组的第 {\displaystyle C[i]} {\displaystyle C[i]}项，每放一个元素就将 {\displaystyle C[i]} {\displaystyle C[i]}减去1

```java
public class CountingSort {
    public static void main(String[] argv) {
        int[] A = CountingSort.countingSort(new int[]{16, 4, 10, 14, 7, 9, 3, 2, 8, 1});
        Utils.print(A);
    }

    public static int[] countingSort(int[] A) {
        int[] B = new int[A.length];
        // 假设A中的数据a'有，0<=a' && a' < k并且k=100
        int k = 100;
        countingSort(A, B, k);
        return B;
    }

    private static void countingSort(int[] A, int[] B, int k) {
        int[] C = new int[k];
        // 计数
        for (int j = 0; j < A.length; j++) {
            int a = A[j];
            C[a] += 1;
        }
        Utils.print(C);
        // 求计数和
        for (int i = 1; i < k; i++) {
            C[i] = C[i] + C[i - 1];
        }
        Utils.print(C);
        // 整理
        for (int j = A.length - 1; j >= 0; j--) {
            int a = A[j];
            B[C[a] - 1] = a;
            C[a] -= 1;
        }
    }
}
```