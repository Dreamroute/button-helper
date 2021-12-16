package cn.yzw.button.util.misc;

import cn.yzw.button.util.demo.OrderAction;
import org.apache.commons.lang3.EnumUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 描述：// TODO
 *
 * @author w.dehi.2021-12-16
 */
class MiscTest {

    @Test
    void clsTest() {
        new Cls(OrderAction.class);

    }

    static class Cls {

        public Cls(Class c) {
            List enumList = EnumUtils.getEnumList(c);
            System.err.println(enumList);
        }
    }

}
