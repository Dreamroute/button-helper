package cn.yzw.button.util;

/**
 * 描述：通用接口，为枚举添加一个描述信息
 *
 * @author w.dehi.2021-11-14
 */
public interface Desc {

    /**
     * 枚举的描述信息
     */
    String getDesc();

    static Desc[] of(Desc... btn) {
        return btn;
    }

}
