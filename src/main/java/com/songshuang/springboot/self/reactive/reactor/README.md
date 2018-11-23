# Reactor

Reactor 则是完全基于反应式流规范设计和实现的库，没有 RxJava 那样的历史包袱，在使用上更加的直观易懂。Reactor 也是 Spring 5 中反应式编程的基础。学习和掌握 Reactor 可以更好地理解 Spring 5 中的相关概念。
在 Java 程序中使用 Reactor 库非常的简单，只需要通过 Maven 或 Gradle 来添加对 io.projectreactor:reactor-core 的依赖即可，目前的版本是 3.0.5.RELEASE。

## Flux 和 Mono
Flux 和 Mono 是 Reactor 中的两个基本概念。Flux 表示的是包含 0 到 N 个元素的异步序列。在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用。Mono 表示的是包含 0 或者 1 个元素的异步序列。该序列中同样可以包含与 Flux 相同的三种类型的消息通知。Flux 和 Mono 之间可以进行转换。对一个 Flux 序列进行计数操作，得到的结果是一个 Mono<Long>对象。把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。

### 创建Flux
有多种不同的方式可以创建 Flux 序列。

#### Flux 类的静态方法
静态方法:
- just(): 可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
- romArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
- empty()：创建一个不包含任何元素，只发布结束消息的序列。
- error(Throwable error)：创建一个只包含错误消息的序列。
- never()：创建一个不包含任何消息通知的序列。
- range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
- interval(Duration period)和 interval(Duration delay, Duration period)：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
- intervalMillis(long period)和 intervalMillis(long delay, long period)：与 interval()方法的作用相同，只不过该方法通过毫秒数来指定时间间隔和延迟时间。

上面的这些静态方法适合于简单的序列生成，当序列的生成需要复杂的逻辑时，则应该使用 generate() 或 create() 方法。

generate()方法

generate()方法通过同步和逐一的方式来产生 Flux 序列。序列的产生是通过调用所提供的 SynchronousSink 对象的 next()，complete()和 error(Throwable)方法来完成的。逐一生成的含义是在具体的生成逻辑中，next()方法只能最多被调用一次。在有些情况下，序列的生成可能是有状态的，需要用到某些状态对象。此时可以使用 generate()方法的另外一种形式 generate(Callable<S> stateSupplier, BiFunction<S,SynchronousSink<T>,S> generator)，其中 stateSupplier 用来提供初始的状态对象。在进行序列生成时，状态对象会作为 generator 使用的第一个参数传入，可以在对应的逻辑中对该状态对象进行修改以供下一次生成时使用。

在代码清单 2中，第一个序列的生成逻辑中通过 next()方法产生一个简单的值，然后通过 complete()方法来结束该序列。如果不调用 complete()方法，所产生的是一个无限序列。第二个序列的生成逻辑中的状态对象是一个 ArrayList 对象。实际产生的值是一个随机数。产生的随机数被添加到 ArrayList 中。当产生了 10 个数时，通过 complete()方法来结束序列。

```java
Flux.generate(sink -> {
    sink.next("Hello");
    sink.complete();
}).subscribe(System.out::println);
 
 
final Random random = new Random();
Flux.generate(ArrayList::new, (list, sink) -> {
    int value = random.nextInt(100);
    list.add(value);
    sink.next(value);
    if (list.size() == 10) {
        sink.complete();
    }
    return list;
}).subscribe(System.out::println);
```

create()方法
create()方法与 generate()方法的不同之处在于所使用的是 FluxSink 对象。FluxSink 支持同步和异步的消息产生，并且可以在一次调用中产生多个元素。在代码清单 3 中，在一次调用中就产生了全部的 10 个元素。

```java
Flux.create(sink -> {
    for (int i = 0; i < 10; i++) {
        sink.next(i);
    }
    sink.complete();
}).subscribe(System.out::println);
```

### 创建Mono
Mono 的创建方式与之前介绍的 Flux 比较相似。Mono 类中也包含了一些与 Flux 类中相同的静态方法。这些方法包括 just()，empty()，error()和 never()等。除了这些方法之外，Mono 还有一些独有的静态方法。
- fromCallable()、fromCompletionStage()、fromFuture()、fromRunnable()和 fromSupplier()：分别从 Callable、CompletionStage、CompletableFuture、Runnable 和 Supplier 中创建 Mono。
- delay(Duration duration)和 delayMillis(long duration)：创建一个 Mono 序列，在指定的延迟时间之后，产生数字 0 作为唯一值。
- ignoreElements(Publisher<T> source)：创建一个 Mono 序列，忽略作为源的 Publisher 中的所有元素，只产生结束消息。
- justOrEmpty(Optional<? extends T> data)和 justOrEmpty(T data)：从一个 Optional 对象或可能为 null 的对象中创建 Mono。只有 Optional 对象中包含值或对象不为 null 时，Mono 序列才产生对应的元素。

## 操作符
### buffer 和 bufferTimeout
这两个操作符的作用是把当前流中的元素收集到集合中，并把集合对象作为流中的新元素。在进行收集时可以指定不同的条件：所包含的元素的最大数量或收集的时间间隔。方法 buffer()仅使用一个条件，而 bufferTimeout()可以同时指定两个条件。指定时间间隔时可以使用 Duration 对象或毫秒数，即使用 bufferMillis()或 bufferTimeoutMillis()两个方法。

除了元素数量和时间间隔之外，还可以通过 bufferUntil 和 bufferWhile 操作符来进行收集。这两个操作符的参数是表示每个集合中的元素所要满足的条件的 Predicate 对象。bufferUntil 会一直收集直到 Predicate 返回为 true。使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中；bufferWhile 则只有当 Predicate 返回 true 时才会收集。一旦值为 false，会立即开始下一次收集。

代码清单 5 给出了 buffer 相关操作符的使用示例。第一行语句输出的是 5 个包含 20 个元素的数组；第二行语句输出的是 2 个包含了 10 个元素的数组；第三行语句输出的是 5 个包含 2 个元素的数组。每当遇到一个偶数就会结束当前的收集；第四行语句输出的是 5 个包含 1 个元素的数组，数组里面包含的只有偶数。

需要注意的是，在代码清单 5 中，首先通过 toStream()方法把 Flux 序列转换成 Java 8 中的 Stream 对象，再通过 forEach()方法来进行输出。这是因为序列的生成是异步的，而转换成 Stream 对象可以保证主线程在序列生成完成之前不会退出，从而可以正确地输出序列中的所有元素。

