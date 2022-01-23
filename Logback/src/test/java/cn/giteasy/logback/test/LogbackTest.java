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
     * todo
     * P87
     */
    @Test
    public void yourName(){

        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        logger.error("========= ERROR信息 test Logback =========");
        logger.warn("========= WARN信息 test Logback =========");
        logger.info("========= INFO信息 test Logback =========");
        logger.debug("========= DEBUG信息 test Logback =========");
        logger.trace("========= TRACE信息 test Logback =========");
    }



}
