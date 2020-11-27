package cqu.ioc;


import cqu.ioc.support.BeanDefinition;

import java.util.List;

public interface BeanRegister {
    void registBeanDefinition(List<BeanDefinition> bds);
    void registInstanceMapping(String id, Object instance);
}
