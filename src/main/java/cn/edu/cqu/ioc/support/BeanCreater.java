package cn.edu.cqu.ioc.support;


import cn.edu.cqu.ioc.BeanRegister;

import java.util.List;

public class BeanCreater {

    private BeanRegister register;
    public BeanCreater(BeanRegister register){
        this.register = register;
    }

    public void create(List<BeanDefinition> bds){
        for (BeanDefinition bd:bds){
            doCreate(bd);
        }
    }

    private void doCreate(BeanDefinition bd) {
        Object instance = bd.getInstance();
        this.register.registInstanceMapping(bd.getId(),instance);
    }
}