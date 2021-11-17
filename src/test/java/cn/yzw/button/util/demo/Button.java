package cn.yzw.button.util.demo;

import cn.yzw.button.util.Desc;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：展示的操作按钮，所有需要展示给用户的操作按钮均罗列在此，desc属性可以作为按钮上的文字描述信息
 *
 * @author w.dehi.2021-11-14
 */
@Getter
@AllArgsConstructor
public enum Button implements Desc {
    CONFIRM             ("确认"),
    REFUSE                ("拒绝"),
    CANCEL                ("取消订单"),
    SEND                    ("发货"),
    SEND_AGAIN       ("再次发货"),
    FINISH                 ("结束订单");

    private final String desc;
}
