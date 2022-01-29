package cn.giteasy.jul.test;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.logging.*;

/**
 * Demo class
 *
 * @author axin
 * @date 2021/12/27
 */
public class JULTest {


    /**
     * 案例
     */
    @Test
    public void testJUL(){
        //日志入口：java.util.logging.Logger
        //Logger对象的创建方式，不能直接new对象
        //取得对象的方法参数，需要引入当前类的全路径字符串（Logger是有父子关系的，根据包的结构（后面有介绍））
        Logger logger = Logger.getLogger("cn.giteasy.jul.test.JULTest");

        /*
         *对于日志的输出，有两种方式
         *
         * 方式一：
         * 直调用日志级别相关的方法，方法中传递日志输出的信息
         */
         logger.info("INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("=====================================================");
         /*
            方式二：
            调用log方法，然后通过传入Level类型的的级别来定义日志的级别，并传入日志输出的信息参数
          */
        //JUL默认级别是INFO
        logger.log(Level.OFF,"OFF>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("=====================================================");

        /*
            输出日志时，传入系统运行中的数据
         */
        String name = "giteasy";
        String like = "Java";
        logger.log(Level.INFO,"My name is {0}, my like {1}",new Object[]{name,like});

        /**
         * 输出结果：
         *
         * 十二月 27, 2021 11:10:22 下午 cn.giteasy.jul.test.JULTest testJUL
         * 信息: My name is giteasy, my like Java
         */

    }


    /**
     * Jul日志级别演示
     */
    @Test
    public void testJulLevel(){
        /*
        日志的级别（可通过源码查看）java.util.logging.Level
        SEVERE  :   错误              最高级的日志级别
        WARNING :   警告
        INFO    :   （默认级别）消息
        CONFIG  :   配置
        FINE    :   详细信息（少）
        FINER   :    详细信息（中）
        FINEST  :   详细信息（多）       最低级的日志级别

        两个特殊的级别（我们重点关注的是new对象的时候的第二个参数是一个数值）
            OFF 可用来关闭日志记录        Integer.MAX_VALUE   整型最大值
            ALL 启用所有消息的日志记录     Integer.MIN_VALUE   整型最小值



        OFF     Integer.MAX_VALUE   整型最大值
        SEVERE  1000
        WARNING 900
        INFO    800
        CONFIG  700
        FINE    500
        FINER   400
        FINEST  300
        ALL Integer.MIN_VALUE   整型最小值

        假如我们设定的日志级别是INFO（800），那么在打印的时候，我们在应用中打印的日志的时候以下的日志都会输出
            SEVERE  1000
            WARNING 900
            INFO    800
          这是因为他们的值，比我们设定的值INFO（800）大或相等。
          例如：logger.info("我是 SEVERE 信息");
               logger.info("我是 WARNING 信息");
               logger.info("我是 INFO 信息");
               logger.info("我是 CONFIG 信息"); //不打印，因为比我们设定的INFO级别小。

         */
        Logger logger = Logger.getLogger("cn.giteasy.jul.test.JULTest");

        /*
            仅仅只是通过setLevel（）设置日志级别，是不起作用的，需要搭配处理器handler共同设置才可以
         */
        //logger.setLevel(Level.FINE);

        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");

        /**
         * 通过控制台输出，可以看到仅仅输出了info级别以及比info级别高的日志信息
         * 由此可知 jul日志框架的默认日志级别是 info
         *
         *
         *
         * 一月 15, 2022 4:03:19 下午 cn.giteasy.jul.test.JULTest testJulLevel
         * 严重: SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>
         * 一月 15, 2022 4:03:19 下午 cn.giteasy.jul.test.JULTest testJulLevel
         * 警告: WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>
         * 一月 15, 2022 4:03:19 下午 cn.giteasy.jul.test.JULTest testJulLevel
         * 信息: INFO>>>>>>>>>>>>>>>>>>>>>>>>>>
         */



    }


    /**
     * 自定义日志级别演示
     */
    @Test
    public void testSetDefaultLevel(){
        //日志记录器
        Logger logger = Logger.getLogger("cn.giteasy.jul.test.JULTest");

        //将默认的日志打印方式关掉
        //参数设置为false ，打印日志时就不会按照默认的方式去打印了
        logger.setUseParentHandlers(false);

        //日志处理器：日志处理器有控制台处理器、文件日志处理器等等，这里演示控制台日志处理器
        ConsoleHandler consoleHandler = new ConsoleHandler();
        //设置输出格式
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        consoleHandler.setFormatter(simpleFormatter);

        //将处理器添加到日志记录器
        logger.addHandler(consoleHandler);

        //设置日志的打印级别
        //日志记录器和处理器的级别均需要进行统一的设置，才可以达到日志级别自定义设置的需求
        logger.setLevel(Level.FINE);
        consoleHandler.setLevel(Level.FINE);

        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");


        /**
         * 控制台打印结果
         *
         * 一月 15, 2022 4:17:38 下午 cn.giteasy.jul.test.JULTest testSetDefaultLevel
         * 严重: SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>
         * 一月 15, 2022 4:17:38 下午 cn.giteasy.jul.test.JULTest testSetDefaultLevel
         * 警告: WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>
         * 一月 15, 2022 4:17:38 下午 cn.giteasy.jul.test.JULTest testSetDefaultLevel
         * 信息: INFO>>>>>>>>>>>>>>>>>>>>>>>>>>
         * 一月 15, 2022 4:17:38 下午 cn.giteasy.jul.test.JULTest testSetDefaultLevel
         * 配置: CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>
         * 一月 15, 2022 4:17:38 下午 cn.giteasy.jul.test.JULTest testSetDefaultLevel
         * 详细: FINE>>>>>>>>>>>>>>>>>>>>>>>>>>
         */


    }

    /**
     * 文件日志打印
     */
    @Test
    public void testFileLog() throws IOException {
        //日志记录器
        Logger logger = Logger.getLogger("cn.giteasy.jul.test.JULTest");
        logger.setUseParentHandlers(false);

        //文件日志处理器
        FileHandler fileHandler = new FileHandler("d:\\jul_test.log");
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        //将处理器添加到日志记录器
        logger.addHandler(fileHandler);

        logger.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);

        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }



    /**
     *文件日志与控制台日志同时打印
     */
    @Test
    public void testConsoleAndFileLog() throws IOException {
        //日志记录器
        Logger logger = Logger.getLogger("cn.giteasy.jul.test.JULTest");
        logger.setUseParentHandlers(false);

        //文件日志处理器
        FileHandler fileHandler = new FileHandler("d:\\jul_test.log");

        //控制台日志处理器
        ConsoleHandler consoleHandler = new ConsoleHandler();

        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        consoleHandler.setFormatter(simpleFormatter);

        //将处理器添加到日志记录器
        logger.addHandler(fileHandler);
        logger.addHandler(consoleHandler);

        logger.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);//文件打印日志级别

        consoleHandler.setLevel(Level.CONFIG);//控制台日志打印级别

        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");


        /**
         * 总结：
         *
         * 用户使用Logger来进行日志的记录，Logger可以持有多个处理器Handler
         * （日志的记录使用的是Logger，日志的输出使用的是Handler）
         * 添加了哪些handler对象，就相当于需要根据所添加的handler将日志输出到指定的位置上，例如控制台、文件．
         *
         */
    }


    /**
     *
     * Logger之间的父子关系
     * JUL中Logger之间是存在"父子"关系的
     *
     * 注意：这种父子关系不是我们普遍认为的类之间的继承关系
     * 关系是通过树状结构存储的（目录、包结构）
     *
     * JUL在初始化时会创建一个顶层RootLogger作为所有的Logger的父Logger
     * 源码：
     *
     *  java.util.logging.LogManager#ensureLogManagerInitialized()；
     *
     *       // Create and retain Logger for the root of the namespace.
     *       owner.rootLogger = owner.new RootLogger();
     *       RootLogger是LogManager的内部类
     *       owner：就是RootLogger的实例
     *      java.util.logging.LogManager$RootLogger //默认的名称为 空串
     *
     *      以上的RootLogger对象作为树状结构的根节点存在的
     *      将来自定义的父子关系通过路径来进行关联父子关系，同时也是节点之间的挂载关系
     */
    @Test
    public void testJulRelative(){


        /**
         * 根据包的结构
         * logger是logger2 的父logger
         * logger2是logger3是父logger
         */
        Logger logger1 = Logger.getLogger("cn.giteasy");
        Logger logger2 = Logger.getLogger("cn.giteasy.jul");
        Logger logger3 = Logger.getLogger("cn.giteasy.jul.test");

        //获取logger2的父logger
        Logger parentLogger = logger2.getParent();
        System.out.println(parentLogger == logger1); //true



        System.out.println("logger1的父Logger引用为：" + logger1.getParent()); //父Logger为 RootLogger
        System.out.println("logger1名称为：" + logger1.getName());
        System.out.println("logger1的父Logger名称为：" + logger1.getParent().getName()); //父logger为RootLogger,父logger为RootLogger的名字这是空字符串

        System.out.println("==================================================");

        System.out.println("logger2的父Logger引用为：" + logger2.getParent());
        System.out.println("logger2名称为：" + logger2.getName());
        System.out.println("logger2的父Logger名称为：" + logger2.getParent().getName());


        /**
         * logger1的父Logger引用为：java.util.logging.LogManager$RootLogger@61e717c2
         * logger1名称为：cn.giteasy
         * logger1的父Logger名称为：
         * ==================================================
         * logger2的父Logger引用为：java.util.logging.Logger@66cd51c3
         * logger2名称为：cn.giteasy.jul
         * logger2的父Logger名称为：cn.giteasy
         */



        /*
            父Logger所做的设置，也同样作用于子Logger

         */

        // 对logger1做打印设置
        logger1.setUseParentHandlers(false);
        //控制台日志处理器
        ConsoleHandler consoleHandler = new ConsoleHandler();
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        consoleHandler.setFormatter(simpleFormatter);
        logger1.addHandler(consoleHandler);

        consoleHandler.setLevel(Level.ALL);
        logger1.setLevel(Level.ALL);

        //使用logger2打印
        logger2.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger2.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger2.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger2.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger2.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger2.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger2.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger2.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");

        //查看控制台打印结果，可知对父logger设置的打印参数，也会作用于子Logger

    }

    /**
     * JUL 配置文件
     * 前面所有配置相关的操作，都是以java硬编码的形式进行的
     *
     * 更加专业的一种做法，就是使用配置文件，如果我们没有自己添加配置文件，则会使用系统默认的配置文件
     * 查看源码可知，配置文件的位置
     *
     *      owner.readPrimordialConfiguration();
     *      readConfiguration();
     *      java.home --> 找到jre文件夹 --> lib --> logging.properties
     *
     * $JAVA_HOME\jre\lib\logging.properties
     *
     */
    @Test
    public void testLogConfigurationFile() throws IOException {

        String path = JULTest.class.getClass().getResource("/logging.properties").getFile().toString();
        InputStream is = new FileInputStream(path);
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(is);

        Logger logger = Logger.getLogger("cn.giteasy.jul.test");


        //使用logger2打印
        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }


    /**
     * 在配置文件中配置自定义Logger
     * 输出位置
     *
     * %h:用户目录
     * %u:序号，从0开始
     * java.util.logging.FileHandler.pattern = %h/java%u.log
     */
    @Test
    public void testMyLoggerForConfigFile() throws IOException {

        String path = JULTest.class.getClass().getResource("/mylogger.properties").getFile().toString();
        InputStream is = new FileInputStream(path);
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(is);

        Logger logger = Logger.getLogger("cn.giteasy.jul.test");


        //使用logger2打印
        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }




}
