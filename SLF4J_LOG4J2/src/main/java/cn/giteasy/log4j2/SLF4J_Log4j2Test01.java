package cn.giteasy.log4j2;



import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 虽然log4j2也是日志门面，但是现在主流趋势仍然是slf4j，
 * 故我们扔需要使用slf4j作为日志门面，搭配log4j2强大的日志实现，进行日志的相关操作
 *
 * 此Demo为当今市场上最强大，最主流的日志框架搭配方式：
 * slf4j+log4j2
 *
 * @author axin
 * @date 2021/12/26
 */
public class SLF4J_Log4j2Test01 {

    /**
     * 1.导入slf4j日志门面 slf4j-api
     * 2.导入log4j2适配器 log4j-slf4j-impl
     * 3.导入log4j2日志门面 log4j-api
     * 4.导入log4j2日志实现 log4j-core
     *
     * Q：即然我们使用了slf4j的日志门面，为什么还要导入log4j2的日志门面？
     *  A：slf4j门面调用的是log4j2的门面，再由log4j2的门面调用log4j2的实现
     */
    @Test
    public void test01(){

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.error("slf4j-log4j2 error>>>>>>>>>>>>>>>>>");
        logger.warn("slf4j-log4j2 warn>>>>>>>>>>>>>>>>>");
        logger.info("slf4j-log4j2 info>>>>>>>>>>>>>>>>>");
        logger.debug("slf4j-log4j2 debug>>>>>>>>>>>>>>>>>");
        logger.trace("slf4j-log4j2 trace>>>>>>>>>>>>>>>>>");

    }

    /**
     * 测试日志文件滚动
     */
    @Test
    public void testRollingFile(){
        Logger logger = LoggerFactory.getLogger(this.getClass());

        for (int i = 0; i < 1000; i++) {
            logger.error("slf4j-log4j2 error>>>>>>>>>>>>>>>>>");
            logger.warn("slf4j-log4j2 warn>>>>>>>>>>>>>>>>>");
            logger.info("slf4j-log4j2 info>>>>>>>>>>>>>>>>>");
            logger.debug("slf4j-log4j2 debug>>>>>>>>>>>>>>>>>");
            logger.trace("slf4j-log4j2 trace>>>>>>>>>>>>>>>>>");
        }

    }


    /**
     *
     * 异步日志：单独分配线程记录日志
     *
     * 测试异步日志 方式1: AsyncAppender方式
     *
     *  在没有引入异步依赖时，会发现控制台输出信息是有顺序的：先输出1000次logger打印的日志，再输入了System.out输出的信息
     *  log4j2.xml 的Appenders节点下，添加
     *
     *          <Async name="asyncAppender">
     *             <AppenderRef ref="consoleAppender"/>
     *         </Async>
     *
     * 并在Loggers节点 使用它
     *      <Loggers>
     *         <Root level="trace">
     *             <AppenderRef ref="asyncAppender"/>
     *         </Root>
     *      </Loggers>
     *
     */
    @Test
   public void testAsyncAppender(){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        for (int i = 0; i < 500; i++) {
            logger.error("slf4j-log4j2 error>>>>>>>>>>>>>>>>>");
            logger.warn("slf4j-log4j2 warn>>>>>>>>>>>>>>>>>");
            logger.info("slf4j-log4j2 info>>>>>>>>>>>>>>>>>");
            logger.debug("slf4j-log4j2 debug>>>>>>>>>>>>>>>>>");
            logger.trace("slf4j-log4j2 trace>>>>>>>>>>>>>>>>>");
        }

        //系统业务逻辑
        for (int i = 0; i < 500; i++) {
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");

        }

    }

    /**
     *
     * 测试异步日志 方式2:AsyncLogger方式 全局异步
     * AsyncLogger方式有两种选择：全局异步 和 混合异步
     *
     *  全局异步：所有的日志都是异步的日志记录，在配置文件上不用做任何的改动
     *  只需要在类路径resources 下添加一个properties属性文件，做一步配置即可
     *  文件名要求是:log4j2.component.properties
     *          内容添加
     *          Log4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
     *
     */
    @Test
    public void testAsyncLogger(){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        for (int i = 0; i < 500; i++) {
            logger.error("slf4j-log4j2 error>>>>>>>>>>>>>>>>>");
            logger.warn("slf4j-log4j2 warn>>>>>>>>>>>>>>>>>");
            logger.info("slf4j-log4j2 info>>>>>>>>>>>>>>>>>");
            logger.debug("slf4j-log4j2 debug>>>>>>>>>>>>>>>>>");
            logger.trace("slf4j-log4j2 trace>>>>>>>>>>>>>>>>>");
        }

        //系统业务逻辑
        for (int i = 0; i < 500; i++) {
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");

        }

    }


    /**
     * 测试异步日志 方式2:AsyncLogger方式 混合异步
     * AsyncLogger方式有两种选择：全局异步 和 混合异步
     *
     *  混合异步：
     *      可以在应用中同时使用同步日志和异步日志，这将使得日志的配置及输出更加灵活。
     *
     *   需求：
     *      自定义logger cn.giteasy.async 异步执行
     *      rootLogger同步执行

         注意：
            测试前，一定要将全局的异步注释掉，否则所有的日志都是异步的

        提示：AsyncAppender、AsyncLogger不要同时出现，效果不会叠加。
            如果同时出现，那么效率会以AsyncAppender为主，效率降低

            AsyncLogger的全局异步和混合异步也不要同时出现。
     *
     */
    @Test
    public void testAsync(){

        /*
         * 指定日志名称为 cn.giteasy.async，作为自定义日志
         * log4j2.xml中指定的就是这个日志才使用异步
         *         <AsyncLogger name="cn.giteasy.async" level="trace" includeLocation="false" additivity="false">
         *             <!--将控制台输出consoleAppender设置为异步-->
         *             <AppenderRef ref="consoleAppender"/>
         *         </AsyncLogger>
         *
         * 实际生产中，不用指定，而是使用this.getClass()或者ClassName.class，使用当前类所有的包路径作为名称
         */
        //Logger logger = LoggerFactory.getLogger("cn.giteasy.async");

        //测试根日志
        Logger logger = LoggerFactory.getLogger(this.getClass());
        for (int i = 0; i < 500; i++) {
            logger.error("slf4j-log4j2 error>>>>>>>>>>>>>>>>>");
            logger.warn("slf4j-log4j2 warn>>>>>>>>>>>>>>>>>");
            logger.info("slf4j-log4j2 info>>>>>>>>>>>>>>>>>");
            logger.debug("slf4j-log4j2 debug>>>>>>>>>>>>>>>>>");
            logger.trace("slf4j-log4j2 trace>>>>>>>>>>>>>>>>>");
        }

        //系统业务逻辑
        for (int i = 0; i < 500; i++) {
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");
            System.out.println("处理业务逻辑");

        }

    }

}
