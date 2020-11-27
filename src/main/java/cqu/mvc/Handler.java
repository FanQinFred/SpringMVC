package cqu.mvc;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class Handler {

    private Object controller;
    private Method method;
    private Pattern pattern;

    private String requstType;

    public String getRequstType() {
        return requstType;
    }



    public Handler(Object controller, Method method, Pattern pattern,String requstType) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        this.requstType=requstType;

    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}