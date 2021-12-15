package cn.yzw.button.util.demo;

import cn.yzw.button.util.Action;
import cn.yzw.button.util.AuditFlag;
import cn.yzw.button.util.Desc;
import cn.yzw.button.util.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static cn.yzw.button.util.demo.Button.CONFIRM;
import static cn.yzw.button.util.demo.Button.REFUSE;
import static cn.yzw.button.util.demo.Button.SEND;


/**
 * 描述：状态实现类
 *
 * @author w.dehi.2021-11-14
 */
@Getter
@AllArgsConstructor
public enum OrderAction implements Action {

    COMMIT(Status.INIT, AuditFlag.PENDDING, Display.WAIT_SIDE_CONFIRM, Display.WAIT_CONFIR, null, Desc.of(CONFIRM, REFUSE, SEND)),
    CONFIR(Status.INIT, AuditFlag.INIT1, Display.WAIT_SIDE_AUDIT, Display.WAIT_SIDE_AUDIT, Desc.of(CONFIRM, SEND), null),
    FINISH(Status.FINISH, AuditFlag.INIT1, Display.WAIT_SIDE_CONFIRM, Display.WAIT_AUDIT, Desc.of(Button.SEND, Button.SEND_AGAIN), null);

    private final Status r;
    private final AuditFlag c;
    private final Desc producer;
    private final Desc consumer;
    private final Desc[] producerBtn;
    private final Desc[] consumerBtn;

}