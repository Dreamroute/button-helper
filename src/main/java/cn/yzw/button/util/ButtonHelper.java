package cn.yzw.button.util;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

/**
 * 描述：按钮工具类
 *
 * @author w.dehi.2021-11-17
 */
public class ButtonHelper<R, C, V extends Action> {

    private final String errorMsg = "错误状态";
    private final String emptyBtn = "0";

    private final Table<R, C, V> table = HashBasedTable.create();

    @SuppressWarnings("unchecked")
    public ButtonHelper(Action[] actions) {
        for (Action action : actions) {
            R r = (R) ReflectUtil.getFieldValue(action, "r");
            C c = (C) ReflectUtil.getFieldValue(action, "c");
            V existValue = table.get(r, c);
            if (existValue == null)
                throw new IllegalArgumentException("你的[r = " + r + ", c = " + c + "]存在重复！");
            V v = (V) action;
            table.put(r, c, v);
        }

    }

    public String getProducerDesc(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getProducer)
                .map(Desc::getDesc)
                .orElse(errorMsg);
    }

    public String getConsumerDesc(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getConsumer)
                .map(Desc::getDesc)
                .orElse(errorMsg);
    }

    public <Q extends String> String getProducerDesc(R r, C c, Function<Desc, Q> ops) {
        V v = getValue(r, c);
        Desc producer = v.getProducer();
        return ops.apply(producer);
    }

    public <Q extends String> String getConsumerDesc(R r, C c, Function<Desc, Q> ops) {
        V v = getValue(r, c);
        Desc producer = v.getConsumer();
        return ops.apply(producer);
    }

    private V getValue(R r, C c) {
        V v = table.get(r, c);
        if (v == null)
            throw new IllegalArgumentException("传入的参数无对应的值, [r = " + r + ", c = " + c + "]");
        return v;
    }

    public String getProducerPermission(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getProducerPermission)
                .orElse(emptyBtn);
    }

    public String getConsumerPermission(R r, C c) {
        return ofNullable(table.get(r, c))
                .map(Action::getConsumerPermission)
                .orElse(emptyBtn);
    }

    public <Q extends String> String getProducerPermission(R r, C c, Function<String, Q> ops) {
        V v = getValue(r, c);
        String producerPermission = v.getProducerPermission();
        return ops.apply(producerPermission);
    }

}
