package cn.giteasy.slf4j.action.test;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *  需求：
 *      假设我们项目一直以来使用的是log4j日志框架,但是随着技术和需求的更新换代.
 *      log4j已然不能够满足我们系统的需求. 我们现在就需要将系统中的日志实现重构为 slf4j+logback的组合
 *      在不触碰java源代码的情况下，如何斛决这个问题？
 *
 *
 * @author axin
 * @date 2022/1/23
 */
public class SLF4J_ACTION {


    /**
     * 模拟当前我们只是使用Log4j打印日志
     */
    @Test
    public void test01(){


        Logger logger = Logger.getLogger(SLF4J_ACTION.class);
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");

        /***
         * 输出结果
         *
         * 2022-01-23 16:02:52.881 [main] FATAL cn.giteasy.slf4j.action.test.SLF4J_ACTION.test01(SLF4J_ACTION.java:26) FATAL信息 =========testLog4j=========
         * 2022-01-23 16:02:52.883 [main] ERROR cn.giteasy.slf4j.action.test.SLF4J_ACTION.test01(SLF4J_ACTION.java:27) ERROR信息 =========testLog4j=========
         * 2022-01-23 16:02:52.883 [main] WARN  cn.giteasy.slf4j.action.test.SLF4J_ACTION.test01(SLF4J_ACTION.java:28) WARN信息 =========testLog4j=========
         * 2022-01-23 16:02:52.883 [main] INFO  cn.giteasy.slf4j.action.test.SLF4J_ACTION.test01(SLF4J_ACTION.java:29) INFO信息 =========testLog4j=========
         * 2022-01-23 16:02:52.883 [main] DEBUG cn.giteasy.slf4j.action.test.SLF4J_ACTION.test01(SLF4J_ACTION.java:30) DEBUG信息 =========testLog4j=========
         * 2022-01-23 16:02:52.883 [main] TRACE cn.giteasy.slf4j.action.test.SLF4J_ACTION.test01(SLF4J_ACTION.java:31) TRACE信息 =========testLog4j=========
         */
    }


    /**
     *
     * SLF4J桥接器
     *
     * 新的需求：
     *  在不更任何改源代码的情况下，我们需要将项目中使用的日志日志替换为slf4j+logback的方式.
     * 我们既然不用log4j了，就将log4j去除, 将slf4j日志门面和logback的日志实现依赖加入进来,这样做是不是就可以解决这个问题呢？
     * 这样做，项目中没有了log4j环境的支持，import导入的包都不会存在，编译则报错。这个时候就需要使用桥接器来做这个需求了。
     *
     * 桥接器解决的是项目中日志的重构问题，当前系统中存在之前的日志API，可以通过桥接转换到slf4j的实现。
     *
     *
     * 实现步骤：
     * 桥接器的使用步骤：
     * 1.去除之前旧的日志框架依赖
     *      <dependency>
     *          <groupId>log4j</groupId>
     *          <artifactId>log4j</artifactId>
     *          <version>1.2.17</version>
     *      </dependency>
     *
     *     此时项目编译会报错，不用担心，下一步引入桥接器就可以了。
     *
     * 2.添加slf4j核心依赖和SLF4J提供的桥接组件
     *      根据官网手册提供的介绍和示图，我们引入LOG4J相关的桥接器
     *        <dependency>
     *             <groupId>org.slf4j</groupId>
     *             <artifactId>slf4j-api</artifactId>
     *             <version>1.7.32</version>
     *         </dependency>
     *
     *         <dependency>
     *             <groupId>org.slf4j</groupId>
     *             <artifactId>log4j-over-slf4j</artifactId>
     *             <version>1.7.32</version>
     *         </dependency>
     *        此时，我们发现项目编译的报错，已恢复正常，此时我们并没有改变一行源代码
     *
     * 3.添加我们需求中要使用的日志实现依赖logback
     *         <dependency>
     *             <groupId>ch.qos.logback</groupId>
     *             <artifactId>logback-classic</artifactId>
     *             <version>1.2.10</version>
     *         </dependency>
     *  测试打印输出
     *  在重构之后，就会为我们造成这样一种假象，使用的是log4j包下的日志组件，但是真正日志的实现，却是slf4j门面+logback实现，这就是桥接器给我们带来的效果。
     *
     *
     *
     * 注意：
     * 在桥接器加入之后，log4j适配器就没有必要加入了,
     * 桥接器和适配器不能同时导入依赖,假如导入了，会存在两种情况：
     *  1. 桥接器如果配置在适配器的上方，则运行时会报错（栈溢出异常），不同同时出现。（slf4j->适配器slf4j-log4j12->桥接器log4j-over-slf4j->适配器slf4j-log4j12->...... 出现死循环）
     *  2. 桥接器如果配置在适配器的下方，则不会执行桥接器，对于我们来说就没有任何意义。（我们的需求是要将log4j的打印，桥接到slf4j+logback实现上）
     *
     *
     */
    @Test
    public void test02(){


        Logger logger = Logger.getLogger(SLF4J_ACTION.class);
        logger.fatal("FATAL信息 =========testLog4j========= ");
        logger.error("ERROR信息 =========testLog4j========= ");
        logger.warn("WARN信息 =========testLog4j========= ");
        logger.info("INFO信息 =========testLog4j========= ");
        logger.debug("DEBUG信息 =========testLog4j========= ");
        logger.trace("TRACE信息 =========testLog4j========= ");

        /**
         * 引入slf4j、SLF4J桥接器，以及logback后，打印结果，已经是logback的默认风格了
         *
         * 16:49:35.462 [main] ERROR cn.giteasy.slf4j.action.test.SLF4J_ACTION - FATAL信息 =========testLog4j=========
         * 16:49:35.464 [main] ERROR cn.giteasy.slf4j.action.test.SLF4J_ACTION - ERROR信息 =========testLog4j=========
         * 16:49:35.464 [main] WARN cn.giteasy.slf4j.action.test.SLF4J_ACTION - WARN信息 =========testLog4j=========
         * 16:49:35.464 [main] INFO cn.giteasy.slf4j.action.test.SLF4J_ACTION - INFO信息 =========testLog4j=========
         * 16:49:35.464 [main] DEBUG cn.giteasy.slf4j.action.test.SLF4J_ACTION - DEBUG信息 =========testLog4j=========
         */

    }

    /**
     *  在配置了桥接器之后，底层就是使用slf4j实现的日志
     * 源码分析：
     *
     * 通过getLogger，进入Log4jLoggerFactory
     *      Logger newInstance = new Logger(name)；//新建logger对象
     *
     * 进入构造方法
     *      protected Logger(string name) {
     *          super(name);
     *      }
     *
     * 点击进入父类的构造方法
     *      Category(String name){
     *          this name = name;
     *          this.slf4jLogger = LoggerFactory getLogger(name);
     *          if (this.slf4jLogger instanceof LocationAwareLogger) {
     *              this.locationAwareLogger = (LocationAwareLogger)this.slf4jLogger;
     *          }
     *      }
     *
     * 在这个Category构造方法中:
     *      this.slf4jLogger = LoggerFactory.getLogger(name);
     *
     *  LoggerFactory来自于org.slf4j,这就证明了getLogger是来自slf4j的。
     */
    @Test
    public void test03(){

        Logger logger = Logger.getLogger(SLF4J_ACTION.class);
        logger.info("INFO信息 =========testLog4j========= ");
    }

}
