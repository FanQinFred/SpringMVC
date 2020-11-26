package cqu.mvc.servlet;

import cqu.ioc.annotation.Controller;
import cqu.ioc.annotation.RequestMapping;
import cqu.ioc.annotation.RequestParam;
import cqu.ioc.support.AnnotationApplicationContext;
import cqu.mvc.Handler;
import cqu.mvc.HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


@MultipartConfig
public class DispatcherServlet extends HttpServlet {

    public static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private List<Handler> handlerMapping = new ArrayList<Handler>();

    private Map<Handler, HandlerAdapter> adapterMapping = new ConcurrentHashMap<Handler, HandlerAdapter>();

    @Override
    public void init() throws ServletException {
        //取出web.xml中的配置
        String location = getInitParameter(CONTEXT_CONFIG_LOCATION);
        //创建ApplicationContext上下文,启动bean的解析、创建、注入等过程，完成Spring的依赖注入
        AnnotationApplicationContext context = new AnnotationApplicationContext(location);
        //解析url和Method的关联关系
        initHandlerMappings(context);
        //适配器（匹配的过程）
        initHandlerAdapters(context);

    }


    private void initHandlerAdapters(AnnotationApplicationContext context) {
        if (handlerMapping.isEmpty()) return;

        for (Handler handler : handlerMapping) {
            Method method = handler.getMethod();
            Map<String, Integer> paramType = new HashMap<String, Integer>();
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramType.put(type.getName(),i);
                }
            }

            Annotation[][] pas = method.getParameterAnnotations();
            for (int i = 0; i < pas.length; i++) {
                Annotation[] pa = pas[i];
                for (Annotation a:pa){
                    if(a instanceof RequestParam){
                        String paramName = ((RequestParam) a).value();
                        if(!"".equals(paramName)){
                            paramType.put(paramName,i);
                        }
                    }
                }
            }
            adapterMapping.put(handler,new HandlerAdapter(paramType));
        }
    }

    /**
     * 初始化handlerMappings
     * @param context
     */
    private void initHandlerMappings(AnnotationApplicationContext context) {
        Map<String, Object> beans = context.getBeans();
        if (beans.isEmpty()) return;
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            if (!entry.getValue().getClass().isAnnotationPresent(Controller.class)) continue;

            String url = "";
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                url = clazz.getAnnotation(RequestMapping.class).value();
            }

            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                if (!m.isAnnotationPresent(RequestMapping.class)) continue;
                String subUrl = m.getAnnotation(RequestMapping.class).value();
                String regex = (url + subUrl).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                String requestType = "";

                requestType = m.getAnnotation(RequestMapping.class).method();

                handlerMapping.add(new Handler(entry.getValue(), m, pattern,requestType));
            }
        }
    }

    //servlet调用
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //doPost
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Handler handler = getHandler(req);
        HandlerAdapter ha;
        if(handler!=null){
            ha = getHandlerAdapter(handler);
            ha.handle(req,resp,handler);
        }

    }

    private HandlerAdapter getHandlerAdapter(Handler handler) {
        if(adapterMapping.isEmpty())return null;
        return adapterMapping.get(handler);
    }

    private Handler getHandler(HttpServletRequest req) {
        if(handlerMapping.isEmpty())return null;
        String contextPath = req.getContextPath();
        String url = req.getRequestURI();
        String requestType = req.getMethod();
        url = url.replace(contextPath,"").replaceAll("/+","/");
        for (Handler handler:handlerMapping){
            if(handler.getPattern().matcher(url).matches() && handler.getRequstType().equals(requestType) ){
                return handler;
            }
        }
        return null;
    }
}