package cn.edu.cqu.ioc;


import cn.edu.cqu.ioc.support.BeanDefinition;

import java.util.List;

//Definition和Bean实例的注册方法
public interface BeanRegister {
    /**
     * 向工厂内注册BeanDefinition
     * @param bds
     */
    void registBeanDefinition(List<BeanDefinition> bds);

    /**
     * 向工厂内注册bean实例对象
     * @param id
     * @param instance
     */
    void registInstanceMapping(String id, Object instance);
}
