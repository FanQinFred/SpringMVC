package cqu.ioc.BeanUtil;


import cqu.ioc.Register;

import java.util.List;

/**
 * 创建bean实例
 */
public class Creator {

    private Register register;
    public Creator(Register register){
        this.register = register;
    }

    public void create(List<Bean> bds){
        for (Bean bd:bds){
            doCreate(bd);
        }
    }

    private void doCreate(Bean bd) {
        Object instance = bd.getInstance();
        this.register.registInstanceMapping(bd.getId(),instance);
    }
}