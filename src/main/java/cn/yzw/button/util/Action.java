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
     * red状态描述
     */
    Desc getRed();

    /**
     * blue状态描述
     */
    Desc getBlue();

    /**
     * red按钮
     */
    Desc[] getRedBtn();

    /**
     * blue按钮
     */
    Desc[] getBlueBtn();

    /**
     * red权限
     */
    default String getRedPermission() {
        return processPermission(getRedBtn());
    }

    /**
     * blue权限
     */
    default String getBluePermission() {
        return processPermission(getBlueBtn());
    }

    default String processPermission(Desc[] btn) {
        int permis = Arrays.stream(ofNullable(btn).orElseGet(() -> new Desc[0]))
                .map(e -> 0B1 << ((Enum<?>) e).ordinal())
                .reduce(0B0, (r, c) -> r | c);
        return new StringBuilder(Integer.toBinaryString(permis)).reverse().toString();
    }

}