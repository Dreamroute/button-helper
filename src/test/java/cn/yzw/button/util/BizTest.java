package cn.yzw.button.util;

import cn.yzw.button.util.demo.Button;
import cn.yzw.button.util.demo.DemoAction;
import cn.yzw.button.util.demo.OrderAction;
import org.junit.jupiter.api.Test;

import static cn.yzw.button.util.ButtonHelper.ERROR_DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 描述：模拟业务场景测试
 * <p>
 * // TODO 为这个工具搞一个技术分享，分享给其他人，让别人少重复造轮子
 *
 * @author w.dehi.2021-11-17
 */
class BizTest {

    ButtonHelper<Status, AuditFlag, OrderAction> helper = new ButtonHelper<>(OrderAction.values());

    /**
     * 常规测试
     */
    @Test
    void orderTest() {
        // 获取producer状态
        String producerDesc = helper.getProducerDesc(Status.INIT, AuditFlag.PENDDING);
        assertEquals("待对方确认", producerDesc);

        // 获取producer按钮
        String producerPermission = helper.getProducerPermission(Status.INIT, AuditFlag.PENDDING);
        assertEquals("0", producerPermission);

        // 获取consumer状态
        String consumerDesc = helper.getConsumerDesc(Status.INIT, AuditFlag.PENDDING);
        assertEquals("待确认", consumerDesc);

        // 获取consumer按钮
        String consumerPermission = helper.getConsumerPermission(Status.INIT, AuditFlag.PENDDING);
        assertEquals("1101", consumerPermission);
    }

    /**
     * 获取按钮，带有自定义逻辑
     */
    @Test
    void getPermissionTest() {
        String producerPermission = helper.getProducerPermission(Status.INIT, AuditFlag.PENDDING, permission -> {
            System.err.println(permission);
            return permission;
        });
        String consumerPermission = helper.getConsumerPermission(Status.INIT, AuditFlag.PENDDING, permission -> {
            System.err.println(permission);
            return permission;
        });
        assertEquals("0", producerPermission);
        assertEquals("1101", consumerPermission);
    }

    /**
     * 测试0开头的按钮列表，比如00101这种
     */
    @Test
    void startZeroTest() {
        String producerPermission = helper.getProducerPermission(Status.FINISH, AuditFlag.INIT1);
        System.err.println(producerPermission);
    }

    /**
     * 自定义返回值（如果获取不到对应的状态）
     */
    @Test
    void customErrorMsgTest() {
        String wrongDesc = helper.getConsumerDesc(Status.FINISH, AuditFlag.PENDDING, desc -> {
            if (desc.equals(ButtonHelper.ERROR_DESC))
                desc = "Wrong";
            return desc;
        });
        assertEquals("Wrong", wrongDesc);
    }

    /**
     * 如果获取不到响应的状态，可以跑错
     */
    @Test
    void throwExTest() {
        assertThrows(IllegalArgumentException.class, () ->
                helper.getConsumerDesc(Status.FINISH, AuditFlag.PENDDING, desc -> {
                    if (desc.equals(ERROR_DESC)) {
                        throw new IllegalArgumentException("找不到对应状态");
                    }
                    return desc;
                })
        );
    }

    /**
     * 重复key异常测试
     */
    @Test
    void duplicateKeyTest() {
        assertThrows(IllegalArgumentException.class, () -> new ButtonHelper<>(DemoAction.values()));
    }

}
