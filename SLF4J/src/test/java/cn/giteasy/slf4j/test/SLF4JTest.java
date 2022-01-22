package cn.giteasy.slf4j.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo class
 *
 * @author axin
 * @date 2022/1/22
 */
public class SLF4JTest {


    /**
     * 入门案例
     * SLF4J日志级别：
     * error：日志错误信息
     * warn：日志警告信息
     * info：日志关键信息（默认级别）
     * debug：日志祥细信息
     * trace：日志跟踪信息
     *
     * 在没有任何其他日志实现框架集成的基础之上， slf4j使用的就是自带的框架slf4j-simple
     * slf4j-simple也必须以单独依赖的形式导入进来
     */
    @Test
    public void test(){

        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);

        logger.error("========= ERROR信息 test slf4j =========");
        logger.warn("========= WARN信息 test slf4j =========");
        logger.info("========= INFO信息 test slf4j =========");
        logger.debug("========= DEBUG信息 test slf4j =========");
        logger.trace("========= TRACE信息 test slf4j =========");

        /**
         * 使用SLF4J自带的日志实现
         * [main] ERROR cn.giteasy.slf4j.test.SLF4JTest - ========= ERROR信息 test slf4j =========
         * [main] WARN cn.giteasy.slf4j.test.SLF4JTest - ========= WARN信息 test slf4j =========
         * [main] INFO cn.giteasy.slf4j.test.SLF4JTest - ========= INFO信息 test slf4j =========
         */

    }

    /**
     * 打印动态日志信息
     *
     * 如果是通过拼接字符串的形式，不仅麻烦，而且更重要的是可读性差，我们的日志打印是支持以替代符的形式做日志信息拼接的。
     * 一般情况下，几乎所有的日志实现产品，都会提供这种基础功能。
     */
    @Test
    public void test02(){

        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        String name = "giteasy";
        int age = 18;
        logger.info("my name is {}, I am {} years old.",new Object[]{name,age});
        logger.info("my name is {}, I am {} years old.",name,age);//常用


    }

    /**
     * 打印异常信息
     */
    @Test
    public void test03(){

        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        try {
            Class.forName("abc");
        } catch (ClassNotFoundException e) {
            //打印堆栈跟踪信息
            //e.printStackTrace();
            logger.info("错误信息：",e);
        }
    }


    /**
     * 观察SLF4J官网提供的图
     *
     * SLF43日志门面，共有3种情况对日志实现进行绑定
     *
     * 1.在没有绑定任何日志实现的基础之上，日志是不能够绑定实现任何功能的;
     * 注意：slf4j-simple是虽然是slf4j官方提供的，使用的时候，也是需要导入依赖，自动绑定到slf4j门面上，如果不导入，slf4j核心依赖是不提供任何实现的。
     *
     * 2.logback和simple（包括nop）(图中蓝色部分)
     *  都是slf4j门面时间线后面出现的日志实现框架，所以API完全遵循slf4j进行的设计，那么我们只需要导入想要使用的日志实现依赖，即可与slf4j无缝衔接
     *  注意：nop虽然也划分到了实现中，但是他是指不实现日志记录，不记录日志
     * 3.log4j和JUL
     *  都是slf4j门面时间线前面出现的日志实现框架，所以API没有遵循slf4j进行设计，通过适配桥接的技术，完成与日志门面的衔接
     *
     *
     *
     * 添加logback信赖，集成logback日志框架
     * 在集成了slf4j-simple依赖基础上，添加logback依赖
     *
     * 控制台打印：
     * SLF4J: Class path contains multiple SLF4J bindings.//说明当前项目存在多个日志实现
     *  ...
     *  ...
     * SLF4J: Actual binding is of type [org.slf4j.impl.SimpleLoggerFactory]//当出现多个日志实现时，实际绑定的依赖
     *
     * 说明在当前项目中有多个实现信赖，但实际使用的还是slf4j-simple
     *
     * 原因：因为是先导入的slf4j-simple依赖，所以默认还是使用slf4j-simple,
     *  如果希望使用指定的日志实现，需要在pom文件中修改引入的顺序，将指定的依赖放到前面。
     * 在实际应用的情况下，一般只集成一种日志实现。
     *
     */
    @Test
    public void test04(){

        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        logger.error("========= ERROR信息 test slf4j =========");
        logger.warn("========= WARN信息 test slf4j =========");
        logger.info("========= INFO信息 test slf4j =========");
        logger.debug("========= DEBUG信息 test slf4j =========");
        logger.trace("========= TRACE信息 test slf4j =========");
    }


    /**
     * 引入多个日志实现后，使用指定日志实现
     * 在pom文件中将logback日志实现导入顺序放到slf4j-simple日志实现导入前面
     *
     * 在实际应用的情况下，一般只集成一种日志实现。
     */
    @Test
    public void test05(){

        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        logger.error("========= ERROR信息 test slf4j =========");
        logger.warn("========= WARN信息 test slf4j =========");
        logger.info("========= INFO信息 test slf4j =========");
        logger.debug("========= DEBUG信息 test slf4j =========");
        logger.trace("========= TRACE信息 test slf4j =========");
    }


    /**
     * pom文件中注释slf4j-simple日志实现，只依赖logback日志实现
     *
     * 在实际应用的情况下，一般只集成一种日志实现。
     */
    @Test
    public void test06(){

        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        logger.error("========= ERROR信息 test slf4j =========");
        logger.warn("========= WARN信息 test slf4j =========");
        logger.info("========= INFO信息 test slf4j =========");
        logger.debug("========= DEBUG信息 test slf4j =========");
        logger.trace("========= TRACE信息 test slf4j =========");
    }


    /**
     *使用slf4j-nop 禁止打印日志
     *
     * slf4j-nop 是slf4j实现的，可以起到不打印日志的作用
     * 虽然是slf4j实现的，如果要使用它，还是需要引入依赖的。
     *
     * 在项目中如果存在多个日志实现的话，slf4j-simple,logback,jul日志实现，slf4j-nop与前面这几个日志实现是同一类（图中蓝色）
     * 如果让slf4j-nop起作用，根据slf4j是通过引入顺序进行绑定的，我们要将slf4j-nop依赖放到其他日志实现的前面
     */
    @Test
    public void testSlf4j_nop(){

        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        logger.error("========= ERROR信息 test slf4j =========");
        logger.warn("========= WARN信息 test slf4j =========");
        logger.info("========= INFO信息 test slf4j =========");
        logger.debug("========= DEBUG信息 test slf4j =========");
        logger.trace("========= TRACE信息 test slf4j =========");
    }
}
