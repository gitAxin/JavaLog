package cn.giteasy.logback.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logback 日志测试
 *  结合slf4j日志门面框架
 * @author axin
 * @date 2022/1/23
 */
public class LogbackTest {

    /**
     * 入门案例
     *
     * logback有5种级别的日志输出，分别是
     * trace < debug < info < warn < error
     *
     *  默认级别：debug
     */
    @Test
    public void testLogback(){

        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.error("========= ERROR信息 test Logback =========");
        logger.warn("========= WARN信息 test Logback =========");
        logger.info("========= INFO信息 test Logback =========");
        logger.debug("========= DEBUG信息 test Logback =========");
        logger.trace("========= TRACE信息 test Logback =========");

        /**
         *通过控制台信息，可以看到默认级别是debug。 trace级别的信息没有输出
         * 17:18:06.755 [main] ERROR cn.giteasy.logback.test.LogbackTest - ========= ERROR信息 test Logback =========
         * 17:18:06.757 [main] WARN cn.giteasy.logback.test.LogbackTest - ========= WARN信息 test Logback =========
         * 17:18:06.757 [main] INFO cn.giteasy.logback.test.LogbackTest - ========= INFO信息 test Logback =========
         * 17:18:06.757 [main] DEBUG cn.giteasy.logback.test.LogbackTest - ========= DEBUG信息 test Logback =========
         */

    }

    /**
     * 使用logback配置文件
     */
    @Test
    public void testLogbackConfigFile(){

        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.error("========= ERROR信息 test Logback =========");
        logger.warn("========= WARN信息 test Logback =========");
        logger.info("========= INFO信息 test Logback =========");
        logger.debug("========= DEBUG信息 test Logback =========");
        logger.trace("========= TRACE信息 test Logback =========");
    }


    /**
     * 将日志输出到文件中
     *
     * 在实际生产环境中，我们更希望将日志信息保存到文件中
     */
    @Test
    public void testFileAppender(){

        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.error("========= ERROR信息 test Logback =========");
        logger.warn("========= WARN信息 test Logback =========");
        logger.info("========= INFO信息 test Logback =========");
        logger.debug("========= DEBUG信息 test Logback =========");
        logger.trace("========= TRACE信息 test Logback =========");
    }


    /**
     * 将日志输出为html文件格式，样式由logback控制，
     * 内容和格式由我们自定义
     */
    @Test
    public void testHTMLFileAppender(){

        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.error("========= ERROR信息 test Logback =========");
        logger.warn("========= WARN信息 test Logback =========");
        logger.info("========= INFO信息 test Logback =========");
        logger.debug("========= DEBUG信息 test Logback =========");
        logger.trace("========= TRACE信息 test Logback =========");
    }


    /**
     *
     * 日志文件拆分和归档压缩
     *
     * 重要标签来源：
     * 查看源码
     *      RollingFileAppender类中找到rollingPolicy属性
     *      SizeAndTimeBasedRollingPolicy类中找到maxFilesize属性
     * 这些属性在类中都是以set方法的形式进行的赋值
     * 我们在配置文件中配置的信息，其实找到的都是这些属性的set方法
     * 在TimeBasedRollingPolicy找到
     *       static final string FNP_NOT_SET =
     *          "The FileNamePattern option must be set before using TimeBasedRollingPolicy."
     *
     * 只要我们要使用到日志的拆分
     * FileNamePattern属性是必须要使用到了
     */
    @Test
    public void testFileSplit(){
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        for (int i = 0; i < 100; i++) {

            logger.error("========= ERROR信息 test Logback =========");
            logger.warn("========= WARN信息 test Logback =========");
            logger.info("========= INFO信息 test Logback =========");
            logger.debug("========= DEBUG信息 test Logback =========");
            logger.trace("========= TRACE信息 test Logback =========");
        }

    }

    /**
     * 使用过滤器，对日志进行更细粒度的控制
     *
     *
     *      <filter class="ch.qos.logback.classic.filter.LevelFilter">
     *             <level>ERROR</level>
     *             <!--高于level中设置的级别，则打印日志-->
     *             <onMatch>ACCEPT</onMatch>
     *             <!--低于level中设置的级别，则屏蔽-->
     *             <onMismatch>DENY</onMismatch>
     *         </filter>
     */
    @Test
    public void test06(){
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.error("========= ERROR信息 test Logback =========");
        logger.warn("========= WARN信息 test Logback =========");
        logger.info("========= INFO信息 test Logback =========");
        logger.debug("========= DEBUG信息 test Logback =========");
        logger.trace("========= TRACE信息 test Logback =========");


        /**
         * 2022-01-28 21:27:30.784 [main] ERROR cn.giteasy.logback.test.LogbackTest test06 128 ========= ERROR信息 test Logback =========
         */

    }

    /**
     * 异步日志
     *
     * 按照我们当前的代码执行顺序，
     * 代码肯定是按照从上向下的顺序执行，上面的代码完全执行完毕后，才会执行下面的业务逻辑
     *
     * 由此得出会出现的问题：
     *  只要是在记录日志，那么系统本身的功能就处于一种停滞的状态，当日志记录完毕后，才会执行其他代码
     *   如果日志记录量非常庞大的话，那么系统本身业务代码的执行效率会非常低
     *  所以logback为我们提供了异步日志的功能
     *
     *
     *  配置方式：
     * 1.配置异步日志
     *      在异步日志中引入我们真正需要输出的appender
     *          <appender name="asyncAppender" class="ch.qos.logback.classic.AsyncAppender">
     *              <appender-ref ref="consoleAppender"/>
     *          </appender>
     * 2.在rootLogger中引入导步日志
     *        <root level="ALL">
     *               <appender-ref ref="asyncAppender"/>
     *         </root>
     *
     * 所谓异步日志的原理是：
     * 系统会为日志操作单独的分配出来一个线程，主线程会继续向下执行
     *  线程1：系统业务代码执行
     *  线程2：打印日志
     *  两个线程争夺CPU的使用权
     *
     * 在实际项目开发中，越大的项目对于日志的记录就越庞大，为了保证系统的执行效率，异步日志是不错的选择
     *
     *
     */
    @Test
    public void test07(){
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);

        //日志输出
        for (int i = 0; i < 100; i++) {

            logger.error("========= ERROR信息 test Logback =========");
            logger.warn("========= WARN信息 test Logback =========");
            logger.info("========= INFO信息 test Logback =========");
            logger.debug("========= DEBUG信息 test Logback =========");
            logger.trace("========= TRACE信息 test Logback =========");
        }

        //业务逻辑操作
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }

    }

    /**
     * 自定义Logger
     */
    @Test
    public void testCustomLogger(){
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.error("========= ERROR信息 test Logback =========");
        logger.warn("========= WARN信息 test Logback =========");
        logger.info("========= INFO信息 test Logback =========");
        logger.debug("========= DEBUG信息 test Logback =========");
        logger.trace("========= TRACE信息 test Logback =========");

    }

    /**
     * 关于logback的补充
     *
     * 1. 异步日志
     *    可配置属性:
     *      当队列的剩余容量小于这个阈值的时候，当前日志的级别 trace、debug、info这3个级别的日志将被丢弃
     *      设置为0，说明永远都不会丢弃trace、debug、info这3个级
     *              <discardingThreshold>0</discardingThreshold>
     *       配置队列的深度，这个值会影响记录日志的性能，默认值256
     *              <queueSize>256</queueSize>
     *      关于这两个属性，一般情况下，我们使用默认值即可，不要乱配置，会影响系统性能，了解其功能即可
     *  2. 配置文件转换
     *      关于不同的日志实现，配置文件也是不同的：
     *          log4j一般使用的是properties属性文件
     *          logback使用的是xml配置文件
     *      如果我们遇到了一种需求，需要将正在使用的log4j，替换为logback，应该如何处理？
     *
     *      使用转换工具：
     *          访问logback官网
     *              找到log4j.properties转换器  (https://logback.qos.ch/translator/)
     *          只有是二者兼容的配置，才会被翻译
     *          如果是log4j独立的技术，logback没有，或者是有这个技术但是并不兼容转义
     *          那么这个工具则不会为我们进行转换，如果是遇到简单的配置，我们可以使用工具。如果是配置比较繁多，复杂，建议手动进行配置。
     */
    @Test
    public void test(){

    }



}
