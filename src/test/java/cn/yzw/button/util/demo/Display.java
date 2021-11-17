package cn.yzw.button.util.demo;

import cn.yzw.button.util.Desc;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：状态枚举，将所有需要展示的状态罗列在此，顺序就是枚举的位置顺序
 *
 * @author w.dehi.2021-11-14
 */
@Getter
@AllArgsConstructor
public enum Display implements Desc {
    INIT("初始化"),
    WAIT_AUDIT("待审批"),
    WAIT_SIDE_AUDIT("待对方审批"),
    WAIT_CONFIR("待确认"),
    WAIT_SIDE_CONFIRM("待对方确认");

    private final String desc;
}
