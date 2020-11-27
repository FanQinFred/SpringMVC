package cqu.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class HandlerAdapter {
    //保存对应的RequestParam  value==>参数的位置
    //或者HttpRequestServlet.getName()===>index
    private Map<String, Integer> paramType;
    final static String REDIRECT = "redirect:";
    public HandlerAdapter(Map<String, Integer> paramType) {
        this.paramType = paramType;
    }


    public void handle(HttpServletRequest req, HttpServletResponse resp, Handler handler) throws Exception {

        //获取要调用方法的全部参数类型
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();

        //创建一个反射调用需要的参数值得数组,数组长度和参数长度一样
        Object[] paramValues = new Object[parameterTypes.length];


        if (paramType.containsKey(HttpServletRequest.class.getName())) {

            paramValues[paramType.get(HttpServletRequest.class.getName())] = req;

        }
        if (paramType.containsKey(HttpServletResponse.class.getName())) {

            paramValues[paramType.get(HttpServletResponse.class.getName())] = resp;

        }

        for (Map.Entry<String, Integer> entry : paramType.entrySet()) {
            String paramName = entry.getKey();
            Integer index = entry.getValue();
            //拿到请求name对应的value
            String[] values = req.getParameterValues(paramName);
            //非空
            if (values != null && values.length != 0) {
                //处理参数值
                String value = Arrays.toString(values).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                //赋值给参数数组,并且把取出来的string类型转成我们参数的类型
                paramValues[index] = castValueType(value, parameterTypes[index]);
            }
        }
        //最后反射调用Controller的method方法
        if (handler.getMethod().getReturnType() == String.class) {
            String viewName = (String) handler.getMethod().invoke(handler.getController(), paramValues);
            if (viewName.startsWith(REDIRECT)) {
                resp.sendRedirect(viewName.substring(REDIRECT.length()));
                System.out.println(viewName.substring(REDIRECT.length()));
            }
            else
                render(req, resp, viewName);
        }
        else {
            handler.getMethod().invoke(handler.getController(), paramValues);
        }
    }

    private void render(HttpServletRequest request, HttpServletResponse res, String viewName) throws Exception {
        String viewPath = "/WEB-INF/view/"+ viewName + ".jsp";
        System.out.println(viewPath);

        request.getRequestDispatcher(viewPath).forward(request, res);
//        res.sendRedirect("https://baidu.com");
    }


    //类型转换
    private Object castValueType(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == int.class) {
            return Integer.valueOf(value).intValue();
        } else {
            return null;
        }
    }

    public Map<String, Integer> getParamType() {
        return paramType;
    }

    public void setParamType(Map<String, Integer> paramType) {
        this.paramType = paramType;
    }
}