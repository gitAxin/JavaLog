package cn.giteasy.slf4j.jul.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J集成JDK14
 *
 * @author axin
 * @date 2022/1/23
 */
public class SLF4J_JUL_TEST {


    /**
     * SLF4J 使用JUL作为日志实现
     *
     *  与log4j一样，JUL也是在slf4j之前出品的日志框架实现,所以并没有遵循slf4j的API规范
     *  如果想要使用JUL作为slf4j的实现，需要导入slf4j-jdk14适配器依赖
     */
    @Test
    public void test01(){
        Logger logger = LoggerFactory.getLogger(SLF4J_JUL_TEST.class);
        logger.error("========= ERROR信息 test slf4j-JUL =========");
        logger.warn("========= WARN信息 test slf4j-JUL =========");
        logger.info("========= INFO信息 test slf4j-JUL =========");
        logger.debug("========= DEBUG信息 test slf4j-JUL =========");
        logger.trace("========= TRACE信息 test slf4j-JUL =========");

        /**
         * 打印结果：
         *
         *
         * 一月 23, 2022 2:58:36 下午 cn.giteasy.slf4j.jul.test.SLF4J_JUL_TEST test01
         * 严重: ========= ERROR信息 test slf4j-JUL =========
         * 一月 23, 2022 2:58:36 下午 cn.giteasy.slf4j.jul.test.SLF4J_JUL_TEST test01
         * 警告: ========= WARN信息 test slf4j-JUL =========
         * 一月 23, 2022 2:58:36 下午 cn.giteasy.slf4j.jul.test.SLF4J_JUL_TEST test01
         * 信息: ========= INFO信息 test slf4j-JUL =========
         *
         *
         * 从控制台打印的结果，可以看出这就是JUL的日志默认打印风格
         */

    }

}
