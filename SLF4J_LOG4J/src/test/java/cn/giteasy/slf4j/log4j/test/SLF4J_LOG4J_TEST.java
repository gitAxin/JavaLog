package cn.giteasy.slf4j.log4j.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo class
 *
 * @author axin
 * @date 2022/1/22
 */
public class SLF4J_LOG4J_TEST {


    /**
     * slf4j绑定log4j
     *
     * 由于log4j是在slf4j之前出品的日志框架实现,所以并没有遵循slf4j的API规范
     * （之前集成的logback，是slf4j之后出品的日志框架实现, logback就是按照slf4j的标准制定的API， 所以我们导入依赖就能用）
     * 如果想要使用log4j，需要绑定一个适配器，叫做slf4j-log4j12
     *
     *
     */
    @Test
    public void test01(){
        Logger logger = LoggerFactory.getLogger(SLF4J_LOG4J_TEST.class);
        logger.error("========= ERROR信息 test slf4j =========");
        logger.warn("========= WARN信息 test slf4j =========");
        logger.info("========= INFO信息 test slf4j =========");
        logger.debug("========= DEBUG信息 test slf4j =========");
        logger.trace("========= TRACE信息 test slf4j =========");


        /**
         * 输出结果：
         * log4j:WARN No appenders could be found for logger (cn.giteasy.slf4j.log4j.test.SLF4J_LOG4J_TEST).
         * log4j:WARN Please initialize the log4j system properly.
         * log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
         *
         * 很熟悉的打印结果，这是已经绑定了log4j日志实现，之所以打印这些信息，是因为没有配置Appender
         * 配置了Appender或添加了配置文件后，就可以使用了
         */


    }

    /**
     * 添加配置文件log4j.properties后测试
     */
    @Test
    public void test02(){
        Logger logger = LoggerFactory.getLogger(SLF4J_LOG4J_TEST.class);
        logger.error("========= ERROR信息 test slf4j =========");
        logger.warn("========= WARN信息 test slf4j =========");
        logger.info("========= INFO信息 test slf4j =========");
        logger.debug("========= DEBUG信息 test slf4j =========");
        logger.trace("========= TRACE信息 test slf4j =========");

        /**
         * 输出结果：
         * 2022-01-22 21:55:27.908 [main] ERROR cn.giteasy.slf4j.log4j.test.SLF4J_LOG4J_TEST.test02(SLF4J_LOG4J_TEST.java:54) ========= ERROR信息 test slf4j =========
         * 2022-01-22 21:55:27.910 [main] WARN  cn.giteasy.slf4j.log4j.test.SLF4J_LOG4J_TEST.test02(SLF4J_LOG4J_TEST.java:55) ========= WARN信息 test slf4j =========
         * 2022-01-22 21:55:27.910 [main] INFO  cn.giteasy.slf4j.log4j.test.SLF4J_LOG4J_TEST.test02(SLF4J_LOG4J_TEST.java:56) ========= INFO信息 test slf4j =========
         * 2022-01-22 21:55:27.910 [main] DEBUG cn.giteasy.slf4j.log4j.test.SLF4J_LOG4J_TEST.test02(SLF4J_LOG4J_TEST.java:57) ========= DEBUG信息 test slf4j =========
         * 2022-01-22 21:55:27.910 [main] TRACE cn.giteasy.slf4j.log4j.test.SLF4J_LOG4J_TEST.test02(SLF4J_LOG4J_TEST.java:58) ========= TRACE信息 test slf4j =========
         *
         * 看到以上日志输出格式，完全是我们在配置文件中定义的格式，说明配置成功。
         *
         *
         *
         * 总结：
         * 通过这个集成测试，我们会发现虽然底层的日志实现变了，但是源代码没有改变.
         * 这就是日志门面给我们带来最大的好处, 在底层真实记录日志的时候，不需要应用去做任何的了解
         * 应用只需要去记slf4j的API就可以了
         *
         * 我们虽然底层使用的是log4j做的打印，但是从当前代码使用来看,我们其实使用的仍然是slf4j日志门面，（导包时只导入了slf4j的API接口）
         * 至于日志是log4j打印的（或者是logback打印的）都是由slf4j进行操作的，我们无需关心
         */

    }







}
