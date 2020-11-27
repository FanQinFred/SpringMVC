package cqu.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class HandlerAdapter {
    private Map<String, Integer> paramType;
    final static String REDIRECT = "redirect:";
    public HandlerAdapter(Map<String, Integer> paramType) {
        this.paramType = paramType;
    }


    public void handle(HttpServletRequest req, HttpServletResponse resp, Handler handler) throws Exception {

        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();

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
            String[] values = req.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                String value = Arrays.toString(values).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                paramValues[index] = castValueType(value, parameterTypes[index]);
            }
        }
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
    }


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