## 按钮权限组件

### 背景
在互助宝项目中，几乎每个模块的列表页和详情页都存在这样一种现象：系统的使用是买方和卖方，对于同一条记录，展示给买卖双方的状态和操作按钮不相同。
比如：
* A（买方）创建订单，A的列表页和详情页展示`待对方确认`，无操作按钮
* B（卖方）列表和详情页展示`待确认`，操作按钮为：` 【待确认】` `【拒绝】`

### 现状
对于互助宝而言，比如订单：存在两个字段来决定这种同记录不同状态的现象，分别是订单状态`status`和审核状态`auditFlag`，对于互助宝的结算
模块，由于没有审核，就只有`status`来决定。

### 目标
由于这个问题在互助宝是普遍存在，因此有必要抽取成公共组件来进行统一维护，可以避免重复造轮子，也可以避免不同开发者对于此问题开发的代码不一致

### 抽取原理
1. 定义业务模块的所有状态
2. 定义业务模块的所有按钮
3. 定义业务模块的所有动作
4. 使用帮助方法获取状态和按钮

### 使用
#### 引入依赖
```xml
<dependency>
    <groupId>cn.yzw</groupId>
    <artifactId>button-util</artifactId>
    <version>最新版本</version>
</dependency>

```

#### 定义业务模块全部状态
> 需要使用这种标准的模板，你需要定义自己的枚举值
```java
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
```

#### 定义业务模块全部按钮
> 需要使用这种标准的模板，你需要定义自己的枚举值
```java
@Getter
@AllArgsConstructor
public enum Button implements Desc {
    CONFIRM             ("确认"),
    REFUSE                ("拒绝"),
    CANCEL                ("取消订单"),
    SEND                    ("发货"),
    SEND_AGAIN        ("再次发货"),
    FINISH                 ("结束订单");

    private final String desc;
}

```

#### 定义业务模块全部操作
> 需要使用这种标准的模板，你需要定义自己的枚举值<br>
```java
@Getter
@AllArgsConstructor
public enum OrderAction implements Action {

    COMMIT(Status.INIT, AuditFlag.PENDDING, Display.WAIT_SIDE_CONFIRM, Display.WAIT_CONFIR, null, Desc.of(CONFIRM, REFUSE, SEND)),
    CONFIR(Status.INIT, AuditFlag.INIT1, Display.WAIT_SIDE_AUDIT, Display.WAIT_SIDE_AUDIT, Desc.of(CONFIRM, SEND), null);

    private final Status r;
    private final AuditFlag c;
    private final Desc producer;
    private final Desc consumer;
    private final Desc[] producerBtn;
    private final Desc[] consumerBtn;

}
```

#### 获取状态&按钮
> 说明：根据当前的状态获取买卖双方的显示状态以及按钮，按钮是以一个二进制序列表示<br>
> 比如：如果返回值是110100，那么就表示【确认】【拒绝】【发货】
```java
class BizTest {

    @Test
    void orderTest() {
        ButtonHelper<Status, AuditFlag, OrderAction> helper = new ButtonHelper<>(OrderAction.values());
        String producerDesc = helper.getProducerDesc(Status.INIT, AuditFlag.PENDDING);
        String producerPermission = helper.getProducerPermission(Status.INIT, AuditFlag.PENDDING);
        assertEquals("待对方确认", producerDesc);
        assertEquals("0", producerPermission);
    }

}
```

### 单个状态的处理方法
上述是2个字段决定状态，如果只有1个字段来决定，那么只需要将`OrderAction`的AuditFlag定义成`Object`类型，然后枚举列表中传入
`Action.EMPTY`这个固定值即可
