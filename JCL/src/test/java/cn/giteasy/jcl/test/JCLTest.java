package cn.giteasy.jcl.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * Demo class
 *
 * @author axin
 * @date 2022/1/22
 */
public class JCLTest {


    /**
     *在没有导入第三方的日志框架情况下，例如log4j，会使用JUL日志框架做日志的记录操作
     *
     * JCL使用的原则：
     * 如果有log4j，优先使用log4j
     * 如果项目中没有任何第三方日志框架，使用的就是JUL
     */
    @Test
    public void testJCL(){

        Log log = LogFactory.getLog(JCLTest.class);
        log.fatal("========= FATAL信息 test jcl =========");
        log.error("========= ERROR信息 test jcl =========");
        log.warn("========= WARN信息 test jcl =========");
        log.info("========= INFO信息 test jcl =========");
        log.debug("========= DEBUG信息 test jcl =========");
        log.trace("========= TRACE信息 test jcl =========");


        /**
         * 输出结果：（JUL日志格式默认风格）
         * 一月 22, 2022 3:35:26 下午 cn.giteasy.jcl.test.JCLTest testJCL
         * 严重: ========= FATAL信息 test jcl =========
         * 一月 22, 2022 3:35:26 下午 cn.giteasy.jcl.test.JCLTest testJCL
         * 严重: ========= ERROR信息 test jcl =========
         * 一月 22, 2022 3:35:26 下午 cn.giteasy.jcl.test.JCLTest testJCL
         * 警告: ========= WARN信息 test jcl =========
         * 一月 22, 2022 3:35:26 下午 cn.giteasy.jcl.test.JCLTest testJCL
         * 信息: ========= INFO信息 test jcl =========
         */
    }

    /**
     *引入log4j信赖后
     */
    @Test
    public void testUCL_Log4j(){
        Log log = LogFactory.getLog(JCLTest.class);
        log.fatal("========= FATAL信息 test jcl =========");
        log.error("========= ERROR信息 test jcl =========");
        log.warn("========= WARN信息 test jcl =========");
        log.info("========= INFO信息 test jcl =========");
        log.debug("========= DEBUG信息 test jcl =========");
        log.trace("========= TRACE信息 test jcl =========");

        /**
         * 输出信息：
         *
         * log4j:WARN No appenders could be found for logger (cn.giteasy.jcl.test.JCLTest).
         * log4j:WARN Please initialize the log4j system properly.
         * log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
         *
         *
         * 我们观察控制台打印日志，可以发现已经使用了log4j作为日志输出，
         * 警告信息为没有发现Appender,是因为没有进行log4j的相关配置，需要log4j.properties配置文件进行配置，
         *
         * 进行配置后输出信息：
         * 2022-01-22 15:42:16.053 [main] FATAL cn.giteasy.jcl.test.JCLTest.testUCL_Log4j(JCLTest.java:54) ========= FATAL信息 test jcl =========
         * 2022-01-22 15:42:16.056 [main] ERROR cn.giteasy.jcl.test.JCLTest.testUCL_Log4j(JCLTest.java:55) ========= ERROR信息 test jcl =========
         * 2022-01-22 15:42:16.056 [main] WARN  cn.giteasy.jcl.test.JCLTest.testUCL_Log4j(JCLTest.java:56) ========= WARN信息 test jcl =========
         * 2022-01-22 15:42:16.057 [main] INFO  cn.giteasy.jcl.test.JCLTest.testUCL_Log4j(JCLTest.java:57) ========= INFO信息 test jcl =========
         * 2022-01-22 15:42:16.057 [main] DEBUG cn.giteasy.jcl.test.JCLTest.testUCL_Log4j(JCLTest.java:58) ========= DEBUG信息 test jcl =========
         * 2022-01-22 15:42:16.057 [main] TRACE cn.giteasy.jcl.test.JCLTest.testUCL_Log4j(JCLTest.java:59) ========= TRACE信息 test jcl =========
         *
         *
         *总结：
         *  虽然日志框架进行了改变，但是代码完全没有改变。
         *
         *日志门面的好处：
         *  门面技术是面向接口的开发，不再依赖具体的实现类，减少代码的耦合性。
         *  可以根据实际需求，灵活的切换日志框架。
         *  统一的API，方便开发者学习和使用，统一的配置管理便于项目日志的维护
         *
         *
         *
         */
    }


    /**
     * 源码分析：
     *  Log接口的4个实现类
     *      JDk13
     *      JDK14 正常java.util.logging
     *      Log4j 我们集成的log4j
     *      Simple JCL自带实现类
     *
     *
     *  （1）查看Jdk14Logger证明里面使用的是JUL日志框架
     *  （2）查看Log4JLogger证明里面使用的是Log4j日志框架
     *  （3）观察LogFactory，查看如何加载的Logger对象
     *       这是一个抽象类，无法实例化，需要观察其实现类LogFactoryImpl
     *  （4）观察LogFactoryImpl，真正加载日志实现使用的就是这个实现类LogFactoryImpl
     *  （5）进入getLog方法
     *      进入getInstance方法
     *          找到instance = this.newInstance(name);//继续进入
     *          找到instance = this.discovertogImplementation(name)；//    表示发现一个日志的实现
     *
     *              for(int i = 0; i < classesToDiscover.length && result == null; ++i) {
     *                  result = this.createLogFromclass(classesToDiscover[i], logCategory, true)
     *              }
     *      遍历我们拥有的日志实现框架,遍历的是一个数组，这个数组是按照 log4j、 jdk14、jdk13、SimpleLogger的顺序依次遍历
     *      表示的是，第一个要遍历的就是log4j，如果有log4j则执行该日志框架,如果没有，则遍历出来第二个，使用jdk14的JUL日志框架,以此类推.
     *
     *              result = this createLogFromclass(classesToDiscover[i], logCategory, true);
     *      表示帮我们创建Logger对象在这个方法中，我们看到了
     *              c = class.forName(logAdapterclassName, true, currentCL);
     *      是取得该类型的反射类型对象,使用反射的形式帮我们创建logger对象
     *              constructor = c.getconstructor(this.logConstructorsignature);
     *
     *
     */

}
