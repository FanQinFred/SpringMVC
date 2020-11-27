package cqu.ioc.support;


import cqu.ioc.BeanRegister;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionParser {

    public static final String SCAN_PACKAGE = "scanPackage";


    private BeanRegister register;

    public BeanDefinitionParser(BeanRegister register){
        this.register = register;
    }

    public void parse(Properties properties){
        String packageName = properties.getProperty(SCAN_PACKAGE);
        doRegister(packageName);
    }


    private void doRegister(String packageName) {
        URL url = getClass().getClassLoader().getResource("./"+packageName.replaceAll("\\.","/"));
        File dir = new File(url.getFile());
        for (File file:dir.listFiles()){
            if(file.isDirectory()){
                doRegister(packageName+"."+file.getName());
            }else {
                String className = packageName+"."+file.getName().replaceAll(".class","").trim();
                List<BeanDefinition> definitions = BeanDefinitionGenerator.generate(className);
                if(definitions == null)continue;
                this.register.registBeanDefinition(definitions);
            }
        }

    }
}