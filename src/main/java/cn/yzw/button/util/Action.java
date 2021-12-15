package cn.yzw.button.util;

import cn.hutool.core.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * 描述：双方的描述和按钮
 *
 * @author w.dehi.2021-11-14
 */
public interface Action {

    Object EMPTY = new Object() {
        @Override
        public String toString() {
            return "EMPTY";
        }
    };

    /**
     * producer状态描述
     */
    Desc getProducer();

    /**
     * consumer状态描述
     */
    Desc getConsumer();

    /**
     * producer按钮
     */
    Desc[] getProducerBtn();

    /**
     * consumer按钮
     */
    Desc[] getConsumerBtn();

    /**
     * producer权限
     */
    default String getProducerPermission() {
        return processPermission(getProducerBtn());
    }

    /**
     * consumer权限
     */
    default String getConsumerPermission() {
        return processPermission(getConsumerBtn());
    }

    default String processPermission(Desc[] btn) {
        int lenght = 0;
        if (btn != null && btn.length > 0) {
            //noinspection unchecked
            lenght = EnumUtil.getNames((Class<? extends Enum<?>>) btn[0].getClass()).size();
        }
        int permis = Arrays.stream(ofNullable(btn).orElseGet(() -> new Desc[0]))
                .map(e -> 0B1 << ((Enum<?>) e).ordinal())
                .reduce(0B0, (r, c) -> r | c);
        String btnList = new StringBuilder(Integer.toBinaryString(permis)).reverse().toString();
        // 给右侧0补齐
        return StringUtils.rightPad(btnList, lenght, '0');
    }

}
