package cn.giteasy.log4j.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.junit.Test;

/**
 * Demo class
 *
 * @author axin
 * @date 2022/1/16
 */
public class TestLog4jv1 {


    /***
     * LOG4j 入门案例
     */
    @Test
    public void testLog4j(){

        /**
         *
         * 配置初始化信息：
         *  BasicConfigurator.configure();
         *  如果不进行此项配置，会报如下警告
         *
         * log4j:WARN No appenders could be found for logger (cn.giteasy.log4j.test.TestLog4jv1).
         * log4j:WARN Please initialize the log4j system properly.
         * log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
         *
         *
         * 查看源码可知， 默认情况下：
         * BasicConfigurator.configure()
         * 1. 创建了根节点的对象，Logger root = Logger.getRootLogger();
         * 2. 根节点添加了ConsoleAppender对象，（表示默认打印到控制台，自定义的格式化输出）
         *
         *
         *
         * 源码：
         *org.apache.log4j.BasicConfigurator#configure()
         *     public static void configure() {
         *         Logger root = Logger.getRootLogger();
         *         root.addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %p %c %x - %m%n")));
         *     }
         */
        BasicConfigurator.configure();

        Logger logger = Logger.getLogger(this.getClass());

        /**
         * 日志级别说明：
         * Log4j提供了8个日志输出级别：
         * *********************************************************
         * ALL      最低级别，用于打开所有级别的日志记录
         * TRACE    程序推进下的追踪信息，这个追踪信息的日志级别非常低，一般情况下是不会使用的
         * DEBUG    消息的细粒度信息输出，对调试应用程序是非常有帮助的，主要是配合开发，在开发过程中打印一些重要的运行信息
         * INFO     消息的粗粒度级别运行信息
         * WARN     表示警告，程序在运行过程中会出现的有可能会发生的隐形错误
         *           注意：有些信息不是错误，但是这个级别的输出目的是为了给程序员以提示
         * ERROR    系统的错误信息，发生的错误不影响系统的运行，
         *           一般情况下，如果不想输出太多的日志，则使用该级别即可。
         * FATAL    表示严重错误，一旦发生系统就不可能继续运行的严重错误，
         *          如果这种级别的错误出现了，表示程序可以停止运行了
         * OFF      最高等级的级别，用户关闭所有的日志记录
         *
         *其中DEBUG是在我们没有进行设置的情况下，为Log4j默认的日志级别
         */



        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= "); //默认级别为debug, 所以trace打印未输出



    }


    /**
     * Log4j的正确打开方式，配置文件的使用
     *
     *不使用 BasicConfigurator.configure();
     * 而是使用配置文件来实现配置，通过对BasicConfigurator.configure();源码的分析，
     * 我们的配置文件需要提供Logger、Appender、Layout这3个组件信息
     *
     *
     * 分析Logger.getLogger(this.getClass());
     * 查看源码：
     *  public static Logger getLogger(Class clazz) {
     *         return LogManager.getLogger(clazz.getName());
     *     }
     *
     *  查看LogManager类，可以看到有多个常量信息，配置文件的格式
     *  log4j.properties
     *  log4j.xml
     *  log4j.configuration
     *  ...
     *  ...
     *
     * 继续查看LogManager类，可以看到有个static代码块，其中
     *
     *          url = Loader.getResource("log4j.properties");
     *  表示，Log4j会在类路径下查找log4j.properties, 对于当前演示项目，会在resources目录下
     *
     *          OptionConverter.selectAndConfigure(url, configuratorClassName, getLoggerRepository());
     *  进入selectAndConfigure方法：
     *
     *   configurator = new PropertyConfigurator();
     *
     *
     * 进入PropertyConfigurator类，有很多常量信息
     * 其中
     *     static final String ROOT_LOGGER_PREFIX = "log4j.rootLogger";
     *     static final String APPENDER_PREFIX = "log4j.appender.";
     *  是必须配置的
     *
     *  通过源码：
     *          String prefix = "log4j.appender." + appenderName;
     *  可知我们需要配置一个appenderName（起名要见名知意，我们起名为console,在控制台输出打印日志）
     *  log4j.appender.console
     *  它的取值为log4j为我们提供的appender类
     *      例如:
     *      log4j.appender.console = org.apache.log4j.ConsoleAppender
     *   我们同时指定输出的格式：
     *   通过源码：
     *           String layoutPrefix = prefix + ".layout";
     *   配置：
     *    log4j.appender.console.layout=org.apache.log4j.SimpleLayout
     *
     *  通过log4j.rootLogger继续在类中搜索
     *  找到void configureRootCategory方法
     *  在这个方法中执行了this.parsecategory方法
     * 观察该方法以下代码：
     *          stringTokenizer st = new StringTokenizer(value,",");
     * 表示要以逗号的方式来切割字符串，证明了log4j.rootLogger的取值，其中可以有多个值，使用逗号进行分隔
     *  通过代码：
     *          String levelstr = st.nextToken();
     *  表示切割后的第一个值是日志的级别
     *  通过代码：
     *          while(st.hasMoreTokens())
     *  表示接下来的值，是可以通过while循环遍历得到的
     *  第2~第n个值，就是我们配置的其他的信息，这个信息就是appenderName
     *   证明了我们配置的方式
     *
     *  log4j.rootLogger=日志级别,appenderName1,appenderName2,appenderName3,...
     *
     *  表示可以同时在根节点上配置多个日志输出的途径
     *
     * 祥情，查看log4j.properties配置文件
     */
    @Test
    public void testConfigFile(){
        Logger logger = Logger.getLogger(this.getClass());
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");
    }


    /**
     * 通过Logger中的开关,打印log4j本身的详细信息
     *
     * 查看LogManager类中的方法
     *  getLoggerRepository()
     * 找到代码LogLog.debug(msg, ex);
     * LogLog会使用debug级别的输出为我们展现日志输出详细信息
     * LogLog是什么？
     *  Logger是记录系统的日志，那么LogLog是用来记录Logger的日志信息的
     *
     * 进入到LogLog.debug(msg，ex);方法中
     * 通过代码：if（debugEnabled && !quietMode）{
     * 观察到if判断中的这两个开关都必须开启才行
     *  lquietMode是已经启动的状态，不需要我们去管
     *  debugEnabled默认是关闭的
     * 所以我们只需要设置debugEnabled为true就可以了
     *
     *      LogLog.setInternalDebugging(true);
     */
    @Test
    public void test03(){

        //打开Logger日志的开关
        LogLog.setInternalDebugging(true);

        Logger logger = Logger.getLogger(this.getClass());
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");


        /**
         * 可以看到控制台，输出我们输出的日志信息外，多出以下信息：
         * 以下信息记录了Log4j从初始化到配置完成的日志
         *
         * log4j: Trying to find [log4j.xml] using context classloader sun.misc.Launcher$AppClassLoader@18b4aac2.
         * log4j: Trying to find [log4j.xml] using sun.misc.Launcher$AppClassLoader@18b4aac2 class loader.
         * log4j: Trying to find [log4j.xml] using ClassLoader.getSystemResource().
         * log4j: Trying to find [log4j.properties] using context classloader sun.misc.Launcher$AppClassLoader@18b4aac2.
         * log4j: Using URL [file:/E:/Workspace_IDEA/JavaLog/LOG4J/target/classes/log4j.properties] for automatic log4j configuration.
         * log4j: Reading configuration from URL file:/E:/Workspace_IDEA/JavaLog/LOG4J/target/classes/log4j.properties
         * log4j: Parsing for [root] with value=[trace,console].
         * log4j: Level token is [trace].
         * log4j: Category root set to TRACE
         * log4j: Parsing appender named "console".
         * log4j: Parsing layout options for "console".
         * log4j: End of parsing for "console".
         * log4j: Parsed "console" options.
         * log4j: Finished configuring.
         */
    }


    /**
     * log4j.properties配置祥解
     *
     * HTMLLayout
     * SimpleLayout
     * PatternLayout
     *
     *  其中PatternLayout是我们最常使用的格式
     *
     *  查看源码 PatternLayout 类
     *
     *  setConversionPattern这个方法就是该PatternLayout的核心方法
     *  根据方法名可知，我们需要在配置文件中将我们的layout配置一个conversionPattern属性
     *
     *    log4j.appender.console.layout.conversionPattern = %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5p %l %m%n
     * 祥情，查看log4j.properties配置文件
     */
    @Test
    public void test04(){

        Logger logger = Logger.getLogger(this.getClass());
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");


        /**
         * 2022-01-16 18:08:03.480 [main] FATAL cn.giteasy.log4j.test.TestLog4jv1.test04(TestLog4jv1.java:248)  FATAL信息 =========testLog4j=========
         * 2022-01-16 18:08:03.482 [main] ERROR cn.giteasy.log4j.test.TestLog4jv1.test04(TestLog4jv1.java:249)  ERROR信息 =========testLog4j=========
         * 2022-01-16 18:08:03.482 [main] WARN  cn.giteasy.log4j.test.TestLog4jv1.test04(TestLog4jv1.java:250)  WARN信息 =========testLog4j=========
         * 2022-01-16 18:08:03.482 [main] INFO  cn.giteasy.log4j.test.TestLog4jv1.test04(TestLog4jv1.java:251)  INFO信息 =========testLog4j=========
         * 2022-01-16 18:08:03.482 [main] DEBUG cn.giteasy.log4j.test.TestLog4jv1.test04(TestLog4jv1.java:252)  DEBUG信息 =========testLog4j=========
         * 2022-01-16 18:08:03.482 [main] TRACE cn.giteasy.log4j.test.TestLog4jv1.test04(TestLog4jv1.java:253)  TRACE信息 =========testLog4j=========
         */
    }


    /**
     * 将日志输出到文件中，实际项目中一般都是输出到文件中
     *
     * 在此之前我们在配置文件中的配置是输出到控制台
     * 我们也可能将日志输出到文件，在输出到控制台的基础上，保留原先的配置，添加输出到文件的配置，做多方向的输出。
     *
     *
     * 日志文件要保存到磁盘的什么位置？
     * 查看FileAppender的源码
     * 看到属性信息
     * protected boolean fileAppend；表示是否追加信息，通过构造方法赋值为true
     * protected int buffersize；缓冲区的大小，通过构造方法赋值为8192
     * 继续观察，找到setFile方法，这个方法就是用来指定文件位置的方法
     * 通过ognl，可以推断setFile方法操作的属性就是file
     *  由此可知配置
     *         log4j.appender.fileAppender.file = D:\\log4j_test.log
     *
     * 如果有输出中文的需求，需要设置编码？
     * 观察FileAppender的父类
     * 找到protected String encoding;属性
     * 由此可知配置：
     *      log4j.appender.fileAppender.encoding = UTF-8
     *
     *
     * 祥情配置，查看log4j.properties配置文件
     *
     *
     *
     */
    @Test
    public void testOutputFile(){

        Logger logger = Logger.getLogger(this.getClass());
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");
    }


    /**
     *
     * 将日志输出到文件中——按照文件大小进行拆分
     * FileAppender为我们提供了好用的子类来进一步的对于文件输出进行处理
     *  RollingFileAppender
     *  DailyRollingFileAppender
     *
     *
     *  1.RollingFileAppender
     *          这个类表示使用按照文件大小进行拆分的方式进行操作
     *          配置文件进行RollingFileAppender相关配置
     *          观察RollingFileAppender的源码
     *              protected long maxFilesize = 10485760L；表示拆分文件的大小
     *              protected int maxBackupIndex = 1；表示拆分文件的数量
     *
     *      #指定日志文件大小，只要超过1MB，那么则生成另外一个文件
     *      log4j.appender.rollingFileAppender.maxFileSize = 1MB
     *      #指定文件数量，文件最多生成5个，超过5个，会覆盖时间最早的那个文件
     *      log4j.appender.rollingFileAppender.maxBackupIndex = 5
     *
     *
     */
    @Test
    public void testOutputFileSplit(){

        Logger logger = Logger.getLogger(this.getClass());
        for (int i = 0; i < 10000; i++) {

            logger.fatal("FATAL信息 =========testLog4j========= ");
            logger.error("ERROR信息 =========testLog4j========= ");
            logger.warn("WARN信息 =========testLog4j========= ");
            logger.info("INFO信息 =========testLog4j========= ");
            logger.debug("DEBUG信息 =========testLog4j========= ");
            logger.trace("TRACE信息 =========testLog4j========= ");
        }


    }

    /**
     * 将日志输出到文件中——按照时间进行拆分
     * 2. DailyRollingFileAppender
     * 按照时间来进行文件的拆分
     *  查看源码属性：
     *  private String datePattern = " "yyyy-MM-dd"；默认是按照天来进行拆分的
     *
     */
    @Test
    public void testOutputFileSplitForTime(){

        Logger logger = Logger.getLogger(this.getClass());
        for (int i = 0; i < 10000; i++) {

            logger.fatal("FATAL信息 =========testLog4j========= ");
            logger.error("ERROR信息 =========testLog4j========= ");
            logger.warn("WARN信息 =========testLog4j========= ");
            logger.info("INFO信息 =========testLog4j========= ");
            logger.debug("DEBUG信息 =========testLog4j========= ");
            logger.trace("TRACE信息 =========testLog4j========= ");
        }


    }


    /**
     * 将日志持久化到数据库
     * 1. 创建表结构：
     * （字段的制定可以根据需求进行调整）
     *
     *
     *      CREATE TABLE my_log(
     *              id int(11) NOT NULL AUTO_INCREMENT,
     *              name varchar(255) DEFAULT NULL COMMENT '项目名称',
     *              create_time varchar(255) DEFAULT NULL COMMENT '创建时间',
     *              thread varchar(255) DEFAULT NULL COMMENT '线程名称',
     *              level varchar(255) DEFAULT NULL COMMENT '日志级别',
     *              category varchar(255) DEFAULT NULL COMMENT '所在类的全路径',
     *              message varchar(255) DEFAULT NULL COMMENT '日志消息',
     *              PRIMARY KEY(id)
     *      )
     * 2. 导入数据库驱动
     *         <!-- 将日志持久化到数据库 -->
     *         <dependency>
     *             <groupId>mysql</groupId>
     *             <artifactId>mysql-connector-java</artifactId>
     *             <version>5.1.32</version>
     *         </dependency>
     *
     * 3.配置log4j.properties
     *
     *  祥情本项目资源路径下配置文件log4j.properties
     *
     */

    @Test
    public void testOutputDatabase(){

        Logger logger = Logger.getLogger(this.getClass());
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");


    }

    /**
     *  Log4j的自定义logger
     *
     * 目前为止我们所创建的Logger对象，默认都是继承rootLogger，配置文件中是这样配置的：
     *      log4j.rootLogger = trace,consoleAppender,dbAppender
     *
     * 我们也可以自定义logger，让其他logger来继承这个logger，这种继承关系就是按照包结构的关系来进行指定的
     *
     * 例如我们一直使用的
     *      Logger logger = Logger.getLogger(this.getClass());
     * 路径就是：
     *      cn.giteasy.log4j.test.TestLog4jv1
     * 它的父logger就是上层的路径或者是更上层的路径,例如：
     *      cn.giteasy.log4j.test
     *      cn.giteasy.log4j
     *      cn.giteasy
     *      cn
     *
     * 参照Log4j源码是如何加载配置文件的？
     * 在源码Propertyconfigurator类中，我们看到常量：log4j.logger.
     * 这个属性值log4j.logger., 就是我们在配置文件中自定义logger时使用的前缀
     * 例如我们自定义的Logger为：
     *   log4j.logger.cn.giteasy.log4j.test = error,fileAppender
     *
     *
     *如果自定义Logger与RootLogger都进行了配置，会使用哪个？
     *
     * 1. 如果自定义Logger与RootLogger配置的输出位置是不同的 则取二者的并集，配置的位置都会进行输出操作
     * 2. 如果自定义Logger与RootLogger配置的日志级别不同，以自定的父logger的级别进行输出
     */
    @Test
    public void testCustomLog(){

        Logger logger = Logger.getLogger(this.getClass());
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");
    }

    /**
     *
     *  自定义logger的应用场景
     *  我们之所以要自定义logger，就是为了针对不同系统、不同包路径下的输出信息做更加灵活的输出操作
     *  例如：
     *      我们，需要对org.apache包下的日志单独进行配置
     *
     *          log4j.logger.org.apache=error,consoleAppender
     *
     */

    @Test
    public void testCustomApply(){
        //模拟当前是在org.apache包下进行输出
        //Logger类本身就是org.apache.log4j包下的类

        //Logger logger = Logger.getLogger("org.apache");
        Logger logger = Logger.getLogger(Logger.class);
        logger.fatal("apache FATAL信息 =========apache testLog4j========= ");
        logger.error("apache ERROR信息 =========apache testLog4j========= ");
        logger.warn("apache WARN信息 =========apache testLog4j========= ");
        logger.info("apache INFO信息 =========apache testLog4j========= ");
        logger.debug("apache DEBUG信息 =========apache testLog4j========= ");
        logger.trace("apache TRACE信息 =========apache testLog4j========= ");


        /**
         * 输出结果：
         * 2022-01-22 15:05:51.027 [main] FATAL cn.giteasy.log4j.test.TestLog4jv1.testCustomApply(TestLog4jv1.java:475) apache FATAL信息 =========apache testLog4j=========
         * 2022-01-22 15:05:51.027 [main] FATAL cn.giteasy.log4j.test.TestLog4jv1.testCustomApply(TestLog4jv1.java:475) apache FATAL信息 =========apache testLog4j=========
         * 2022-01-22 15:05:51.393 [main] ERROR cn.giteasy.log4j.test.TestLog4jv1.testCustomApply(TestLog4jv1.java:476) apache ERROR信息 =========apache testLog4j=========
         * 2022-01-22 15:05:51.393 [main] ERROR cn.giteasy.log4j.test.TestLog4jv1.testCustomApply(TestLog4jv1.java:476) apache ERROR信息 =========apache testLog4j=========
         *
         *
         * 观查输出结果，发现每条日志各输出了2次，因为我们在配置文件中，consoleAppender配置了2次，由于输出的位置appender取的是并集，所以输出2次
         *
         *      log4j.rootLogger = trace,consoleAppender,dbAppender
         *
         *      #配置apache的Logger
         *      log4j.logger.org.apache = error,consoleAppender
         *
         * 为了防止输出2次，我们可以将自定义log4j.logger.org.apache日志的consoleAppender日志去掉
         *
         */

    }


}
