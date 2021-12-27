package cn.giteasy.jul.test;

import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demo class
 *
 * @author axin
 * @date 2021/12/27
 */
public class JULTest {


    /**
     * 案例
     */
    @Test
    public void testJUL(){
        //日志入口：java.util.logging.Logger
        //Logger对象的创建方式，不能直接new对象
        //取得对象的方法参数，需要引入当前类的全路径字符串（Logger是有父子关系的，根据包的结构（后面有介绍））
        Logger logger = Logger.getLogger("cn.giteasy.jul.test.JULTest");

        /*
         *对于日志的输出，有两种方式
         *
         * 方式一：
         * 直调用日志级别相关的方法，方法中传递日志输出的信息
         */
         logger.info("INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("=====================================================");
         /*
            方式二：
            调用log方法，然后通过传入Level类型的的级别来定义日志的级别，并传入日志输出的信息参数
          */
        //JUL默认级别是INFO
        logger.log(Level.OFF,"OFF>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.SEVERE,"SEVERE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.WARNING,"WARNING>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.INFO,"INFO>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.CONFIG,"CONFIG>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINE,"FINE>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINER,"FINER>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.FINEST,"FINEST>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.log(Level.ALL,"ALL>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("=====================================================");

        /*
            输出日志时，传入系统运行中的数据
         */
        String name = "giteasy";
        String like = "Java";
        logger.log(Level.INFO,"My name is {0}, my like {1}",new Object[]{name,like});

        /**
         * 输出结果：
         *
         * 十二月 27, 2021 11:10:22 下午 cn.giteasy.jul.test.JULTest testJUL
         * 信息: My name is giteasy, my like Java
         */

    }


    @Test
    public void testJulLevel(){
        /*
        日志的级别（可通过源码查看）java.util.logging.Level
        SEVERE  :   错误              最高级的日志级别
        NARNING :   警告
        INFO    :   （默认级别）消息
        CONFIG  :   配置
        FINE    :   详细信息（少）
        FINER   :    详细信息（中）
        FINEST  :   详细信息（多）       最低级的日志级别

        两个特殊的级别（我们重点关注的是new对象的时候的第二个参数是一个数值）
            OFF 可用来关闭日志记录        Integer.MAX_VALUE   整型最大值
            ALL 启用所有消息的日志记录     Integer.MIN_VALUE   整型最小值



        OFF     Integer.MAX_VALUE   整型最大值
        SEVERE  1000
        WARNING 900
        INFO    800
        CONFIG  700
        FINE    500
        FINER   400
        FINEST  300
        ALL Integer.MIN_VALUE   整型最小值

        假如我们设定的日志级别是INFO（800），那么在打印的时候，我们在应用中打印的日志的时候以下的日志都会输出
            SEVERE  1000
            WARNING 900
            INFO    800
          这是因为他们的值，比我们设定的值INFO（800）大或相等。
          例如：logger.info("我是 SEVERE 信息");
               logger.info("我是 WARNING 信息");
               logger.info("我是 INFO 信息");
               logger.info("我是 CONFIG 信息"); //不打印，因为比我们设定的INFO级别小。

         */


    }
}
