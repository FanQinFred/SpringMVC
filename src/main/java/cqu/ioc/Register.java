package cqu.ioc;


import cqu.ioc.BeanUtil.Bean;

import java.util.List;

/**
 * 注册器接口
 */
public interface Register {
    void registBeanDefinition(List<Bean> bds);
    void registInstanceMapping(String id, Object instance);
}
