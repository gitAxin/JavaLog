package cn.giteasy.log4j2;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * 入门案例
 * @author axin
 * @date 2021/12/26
 */
public class Log4j2Test01 {


    /**
     * 仅仅使用Log4j2的门面+实现
     *  Log4j2和Log4j提供了相同的日志级别输出
     *  默认的日志级别是 error
     */
    @Test
    public void test01(){


        Logger logger = LogManager.getLogger(this.getClass());
        logger.fatal("fatal>>>>>>>>>>>>>>>>>");
        logger.error("error>>>>>>>>>>>>>>>>>");
        logger.warn("warn>>>>>>>>>>>>>>>>>");
        logger.info("info>>>>>>>>>>>>>>>>>");
        logger.debug("debug>>>>>>>>>>>>>>>>>");
        logger.trace("trace>>>>>>>>>>>>>>>>>");

        /**
         * 在没有进行任何配置时，使用的是默认配置文件， 默认级别是error
         * 12:29:52.003 [main] FATAL cn.giteasy.log4j2.Log4j2Test01 - fatal>>>>>>>>>>>>>>>>>
         * 12:29:52.019 [main] ERROR cn.giteasy.log4j2.Log4j2Test01 - error>>>>>>>>>>>>>>>>>
         */
    }


    /**
     * 使用配置文件进行配置
     * log4j2是参考logback创作出来的，所以配置文件也是xml
     * log4j2同样是默认加载类路径（resources）下的文件
     * 文件名一般配置为log4j2.xml
     */
    @Test
    public void test02(){

    }
}
