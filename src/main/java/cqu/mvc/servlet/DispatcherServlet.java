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

    //pattern与对应的处理方法映射
    private List<Handler> handlerMapping = new ArrayList<Handler>();

    private Map<Handler, HandlerAdapter> adapterMapping = new ConcurrentHashMap<Handler, HandlerAdapter>();

    @Override
    public void init() throws ServletException {
        //取出来web.xml中配置的param参数
        String location = getInitParameter(CONTEXT_CONFIG_LOCATION);
        //创建ApplicationContext上下文,启动bean的解析  创建  注入等过程 完成Spring的依赖注入
        AnnotationApplicationContext context = new AnnotationApplicationContext(location);

        //todo 请求解析
        initMultipartResolver(context);
        //todo 多语言、国际化
        initLocaleResolver(context);
        //todo 主题View层的
        initThemeResolver(context);

        //解析url和Method的关联关系
        initHandlerMappings(context);
        //适配器（匹配的过程）
        initHandlerAdapters(context);

        //todo 异常解析
        initHandlerExceptionResolvers(context);
        //todo 视图转发（根据视图名字匹配到一个具体模板）
        initRequestToViewNameTranslator(context);

        //todo 解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
        initViewResolvers(context);
        //todo flash信息
        initFlashMapManager(context);
    }

    private void initFlashMapManager(AnnotationApplicationContext context) {
    }

    private void initViewResolvers(AnnotationApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(AnnotationApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(AnnotationApplicationContext context) {
    }

    private void initHandlerAdapters(AnnotationApplicationContext context) {
        if (handlerMapping.isEmpty()) return;
        //遍历所有的handlerMapping
        for (Handler handler : handlerMapping) {
            Method method = handler.getMethod();
            //创建一个保存RequestParam 注解的value(即参数名)==>index(参数位置索引)
            Map<String, Integer> paramType = new HashMap<String, Integer>();
            //获取所有的参数类型数组
            Class<?>[] parameterTypes = method.getParameterTypes();
            //处理req和res对象
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                //如果有HttpServletRequest类型就往map中保存 类型名==>index
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramType.put(type.getName(),i);
                }
            }

            //获取所有的参数注解,之所以返回二维数组,是因为每个参数可能有
            //多个注解修饰
            Annotation[][] pas = method.getParameterAnnotations();
            for (int i = 0; i < pas.length; i++) {
                //获取第i个参数的修饰注解数组
                Annotation[] pa = pas[i];
                //遍历每个参数的修饰注解
                for (Annotation a:pa){
                    //处理 requestParam注释的变量
                    if(a instanceof RequestParam){
                        String paramName = ((RequestParam) a).value();
                        if(!"".equals(paramName)){
                            //如果注解属于@RequestParam
                            //把注解参数 name==>index保存map
                            paramType.put(paramName,i);
                        }
                    }
                    //todo：注释value为空的时候没有处理
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
        //获取context中所有的bean(instancesMapping)实例对象数组
        Map<String, Object> beans = context.getBeans();
        if (beans.isEmpty()) return;
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            //只对controller修饰的类做解析 原文错误！处理controller bean，因为只有controller有mapping
            if (!entry.getValue().getClass().isAnnotationPresent(Controller.class)) continue;
            //获取类层面的url
            String url = "";
            Class<?> clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                url = clazz.getAnnotation(RequestMapping.class).value();
            }

            //再取对应方法上的url
            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                if (!m.isAnnotationPresent(RequestMapping.class)) continue;
                String subUrl = m.getAnnotation(RequestMapping.class).value();
                String regex = (url + subUrl).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                String requestType = "";

                requestType = m.getAnnotation(RequestMapping.class).method();

                //添加到handlerMapping中去
                handlerMapping.add(new Handler(entry.getValue(), m, pattern,requestType));
            }
        }
    }

    private void initThemeResolver(AnnotationApplicationContext context) {
    }

    private void initLocaleResolver(AnnotationApplicationContext context) {
    }

    private void initMultipartResolver(AnnotationApplicationContext context) {
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
        //取出匹配的handler，通过之前维护的handleMapping获取handler
        Handler handler = getHandler(req);
        //根据handler取出HandlerAdapter
        HandlerAdapter ha;
        if(handler!=null){
            ha = getHandlerAdapter(handler);
            ha.handle(req,resp,handler);
        }
        //调用handle方法处理请求,暂时未做ModalAndView处理

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
        //获取请求的url  除去contextPath剩余的
        url = url.replace(contextPath,"").replaceAll("/+","/");
        //遍历handlermapping，找到url匹配的handler
        for (Handler handler:handlerMapping){
            if(handler.getPattern().matcher(url).matches() && handler.getRequstType().equals(requestType) ){
                //匹配到就把handler返回
                return handler;
            }
        }
        return null;
    }
}