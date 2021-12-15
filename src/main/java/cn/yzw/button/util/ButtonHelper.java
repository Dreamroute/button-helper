package cn.yzw.button.util;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

/**
 * 描述：按钮工具类，获取状态，按钮这一系列操作均由此工具类完成
 *
 * @author w.dehi.2021-11-17
 */
public class ButtonHelper<R extends Enum<?>, C, V extends Action> {

    public static final String ERROR_DESC = "错误状态";
    public static final String EMPTY_BUTTON = "0";

    private final Table<R, C, V> table = HashBasedTable.create();

    @SuppressWarnings("unchecked")
    public ButtonHelper(Action[] actions) {
        for (Action action : actions) {
            R r = (R) ReflectUtil.getFieldValue(action, "r");
            C c = (C) ReflectUtil.getFieldValue(action, "c");
            V existValue = table.get(r, c);
            if (existValue != null) {
                throw new IllegalArgumentException("你的[r = " + r + ", c = " + c + "]存在重复！");
            } else {
                table.put(r, c, (V) action);
            }
        }
    }

    /**
     * 获取生产者状态
     *
     * @param r 行
     * @param c 列
     * @return 返回生产者状态字符串
     */
    public String getProducerDesc(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getProducer)
                .map(Desc::getDesc)
                .orElse(ERROR_DESC);
    }

    /**
     * 获取消费者状态
     *
     * @param r 行
     * @param c 列
     * @return 返回消费者状态字符串
     */
    public String getConsumerDesc(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getConsumer)
                .map(Desc::getDesc)
                .orElse(ERROR_DESC);
    }

    /**
     * 获取生产者状态，如果默认返回值不满足，默认返回值是<code>ButtonHelper.ERROR_DESC</code>，可以自定义返回值
     *
     * @param r 行
     * @param c 列
     * @return 返回生产者状态字符串
     */
    public String getProducerDesc(R r, C c, Function<String, String> ops) {
        String desc = getProducerDesc(r, c);
        return ops.apply(desc);
    }

    /**
     * 获取消费者状态，如果默认返回值不满足，默认返回值是<code>ButtonHelper.ERROR_DESC</code>，可以自定义返回值
     *
     * @param r 行
     * @param c 列
     * @return 返回消费者状态字符串
     */
    public String getConsumerDesc(R r, C c, Function<String, String> ops) {
        String desc = getConsumerDesc(r, c);
        return ops.apply(desc);
    }

    /**
     * 获取生产者按钮
     *
     * @param r 行
     * @param c 列
     * @return 返回生产者所拥有的按钮列表
     */
    public String getProducerPermission(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getProducerPermission)
                .orElse(EMPTY_BUTTON);
    }

    /**
     * 获取消费者按钮
     *
     * @param r 行
     * @param c 列
     * @return 返回消费者所拥有的按钮列表
     */
    public String getConsumerPermission(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getConsumerPermission)
                .orElse(EMPTY_BUTTON);
    }

    /**
     * 获取生产者按钮列表，可以加入自定义逻辑
     *
     * @param r 行
     * @param c 列
     * @param ops 自定义逻辑
     * @return 返回生产者二进制按钮列表
     */
    public String getProducerPermission(R r, C c, Function<String, String> ops) {
        V v = getValue(r, c);
        String producerPermission = v.getProducerPermission();
        return ops.apply(producerPermission);
    }

    public String getConsumerPermission(R r, C c, Function<String, String> ops) {
        V v = getValue(r, c);
        String producerPermission = v.getConsumerPermission();
        return ops.apply(producerPermission);
    }

    private V getValue(R r, C c) {
        V v = table.get(r, c);
        if (v == null) {
            throw new IllegalArgumentException("传入的参数无对应的值, [r = " + r + ", c = " + c + "]");
        }
        return v;
    }
}
