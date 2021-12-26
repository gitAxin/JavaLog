package cn.giteasy.springbootlog;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Level;

@SpringBootTest
class SpringBootLogApplicationTests {

    /**
     * springboot日志具体实现
     */
    @Test
    void testLevel() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error("error>>>>>>>>>>>>>>>>>>>>");
        logger.warn("warn>>>>>>>>>>>>>>>>>>>>");
        logger.info("info>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.debug("debug>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.trace("trace>>>>>>>>>>>>>>>>>>>");

        /**
         *
         * 从打印结果看，默认级别是info
         * logback的风格输出 （默认使用的是logback的日志实现）
         *
         * 2021-12-26 22:16:05.959 ERROR 3716 --- [           main] c.g.s.SpringBootLogApplicationTests      : error>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 22:16:05.959  WARN 3716 --- [           main] c.g.s.SpringBootLogApplicationTests      : warn>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 22:16:05.960  INFO 3716 --- [           main] c.g.s.SpringBootLogApplicationTests      : info>>>>>>>>>>>>>>>>>>>>>>>>>
         */



    }

    /**
     *观罕log4j-to-slf4j桥接器是否起作用
     */
    @Test
    public void testLog4jToSlf4j(){

        org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());
        logger.error("error>>>>>>>>>>>>>>>>>>>>");
        logger.warn("warn>>>>>>>>>>>>>>>>>>>>");
        logger.info("info>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.debug("debug>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.trace("trace>>>>>>>>>>>>>>>>>>>");

        //java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();
        java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(this.getClass().getName());

        //java.util.logging.Logger julLogger = logManager.("cn.giteasy");

        //JUL默认级别是INFO
        julLogger.log(Level.OFF,"OFF==================");
        julLogger.log(Level.SEVERE,"SEVERE==================");
        julLogger.log(Level.WARNING,"WARNING==================");
        julLogger.log(Level.INFO,"INFO==================");
        julLogger.log(Level.CONFIG,"CONFIG==================");
        julLogger.log(Level.FINE,"FINE==================");
        julLogger.log(Level.FINER,"FINER==================");
        julLogger.log(Level.FINEST,"FINEST==================");
        julLogger.log(Level.ALL,"ALL==================");
        julLogger.info("jul info==================");



        /**
         * 观察日志输出结果，同样还是logback的日志输出风格，所以log4j-to-slf4j  和 jul-to-slf4j 桥接器是起作用的。即使使用log4j和jul打印日志，还会桥接到logback
         *
         * 2021-12-26 22:29:09.209 ERROR 12368 --- [           main] c.g.s.SpringBootLogApplicationTests      : error>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 22:29:09.209  WARN 12368 --- [           main] c.g.s.SpringBootLogApplicationTests      : warn>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 22:29:09.209  INFO 12368 --- [           main] c.g.s.SpringBootLogApplicationTests      : info>>>>>>>>>>>>>>>>>>>>>>>>>
         */
    }

    /**
     * application.properties（yml）是springboot的核心配置文件（用来简化开发）
     * 我们可以通过该配置文件，修改日志相关的配置
     * 添加如下配置
     *              logging.level.cn.giteasy=trace
     *              logging.pattern.console=%d{yyyy-MM-dd} %t [%level] - %m%n
     *
     *
     */
    @Test
    public void testConfig(){

        /**
         * 观察结果，可以发现修改配置是可以起作用的
         */
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error("error>>>>>>>>>>>>>>>>>>>>");
        logger.warn("warn>>>>>>>>>>>>>>>>>>>>");
        logger.info("info>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.debug("debug>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.trace("trace>>>>>>>>>>>>>>>>>>>");


        /**
         * 打印结果：
         *
         * 2021-12-26 main [ERROR] - error>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 main [WARN] - warn>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 main [INFO] - info>>>>>>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 main [DEBUG] - debug>>>>>>>>>>>>>>>>>>>>>>>>>
         * 2021-12-26 main [TRACE] - trace>>>>>>>>>>>>>>>>>>>
         */


    }

    /**
     * 添加配置将日志输出到文件中
     *
     *  logging.file.path=D://springboot_log//
     */
    @Test
    public void testFileLog(){


        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error("error>>>>>>>>>>>>>>>>>>>>");
        logger.warn("warn>>>>>>>>>>>>>>>>>>>>");
        logger.info("info>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.debug("debug>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.trace("trace>>>>>>>>>>>>>>>>>>>");

    }

    /**
     *  如果是需要配置日志拆分等相对高级的功能，
     *  那么application.properties就达不到我们的需求了
     *  需要使用日志实现相应的配置文件
     *
     *  例如：我们当前日志默认实现使用的是logback
     *  那么我们就需要在类路径resources下，配置logback.xml
     */
    @Test
    public void testRollFileLog(){
        Logger logger = LoggerFactory.getLogger(this.getClass());

        for (int i = 0; i < 1000; i++) {
            logger.error("error>>>>>>>>>>>>>>>>>>>>");
            logger.warn("warn>>>>>>>>>>>>>>>>>>>>");
            logger.info("info>>>>>>>>>>>>>>>>>>>>>>>>>");
            logger.debug("debug>>>>>>>>>>>>>>>>>>>>>>>>>");
            logger.trace("trace>>>>>>>>>>>>>>>>>>>");
        }

    }

    /**
     * 由于log4j2性能的强大，以及当今市场上越来越多的项目选择使用slf4j+log4j2的组合
     *
     * springboot默认使用的是slf4j+logback的组合，
     * 但我们可以将默认的logback置换为log4j2
     *
     * 1.启动器的信赖，间接的信赖logback,所以需要将之前的环境中,logback的信赖去除
     *
     *              <exclusions>
     *                 <!--排除掉原始日志信赖，达到去除logback信赖的目的-->
     *                 <exclusion>
     *                     <groupId>org.springframework.boot</groupId>
     *                     <artifactId>spring-boot-starter-logging</artifactId>
     *                 </exclusion>
     *             </exclusions>
     *
     * 2. 添加log4j2的信赖
     *         <dependency>
     *             <groupId>org.springframework.boot</groupId>
     *             <artifactId>spring-boot-starter-log4j2</artifactId>
     *         </dependency>
     *
     * 3.添加log4j2的配置文件 log4j2.xml 到resources目录下
     */
    @Test
    public void testLog4j2(){
        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.error("error>>>>>>>>>>>>>>>>>>>>");
        logger.warn("warn>>>>>>>>>>>>>>>>>>>>");
        logger.info("info>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.debug("debug>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.trace("trace>>>>>>>>>>>>>>>>>>>");


    }



}
