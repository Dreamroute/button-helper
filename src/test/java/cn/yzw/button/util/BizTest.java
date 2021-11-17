package cn.yzw.button.util;

import cn.yzw.button.util.demo.DemoAction;
import cn.yzw.button.util.demo.OrderAction;
import org.junit.jupiter.api.Test;

/**
 * 描述：模拟业务场景测试
 *
 * @author w.dehi.2021-11-17
 */
class BizTest {

    @Test
    void orderTest() {
        ButtonHelper<Status, AuditFlag, OrderAction> helper = new ButtonHelper<>(OrderAction.values());
        System.err.println(helper.getProducerDesc(Status.INIT, AuditFlag.PENDDING));
        System.err.println(helper.getConsumerDesc(Status.INIT, AuditFlag.PENDDING));
        System.err.println(helper.getProducerPermission(Status.INIT, AuditFlag.PENDDING));
        System.err.println(helper.getConsumerPermission(Status.INIT, AuditFlag.PENDDING));

        // TODO 对于异常的单元测试
        // TODO 设计单个参数
        // TODO 如果Action有重复该怎么解决
        // TODO 编写使用教程
        // TODO 参考赵春建的实现，进行方法修改
        String producerDesc = helper.getProducerDesc(Status.INIT, AuditFlag.PENDDING, (Desc desc) -> {
            System.err.println(desc);
            return "dd";
        });
    }

    @Test
    void demoTest() {
        ButtonHelper<Status, Object, DemoAction> helper = new ButtonHelper<>(DemoAction.values());
        System.err.println(helper.getConsumerDesc(Status.INIT, null));
    }

}
