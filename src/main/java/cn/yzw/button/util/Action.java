package cn.yzw.button.util;

import java.util.Arrays;

import static java.util.Optional.ofNullable;

/**
 * 描述：双方的描述和按钮
 *
 * @author w.dehi.2021-11-14
 */
public interface Action {

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
        int permis = Arrays.stream(ofNullable(btn).orElseGet(() -> new Desc[0]))
                .map(e -> 0B1 << ((Enum<?>) e).ordinal())
                .reduce(0B0, (r, c) -> r | c);
        return new StringBuilder(Integer.toBinaryString(permis)).reverse().toString();
    }

}
