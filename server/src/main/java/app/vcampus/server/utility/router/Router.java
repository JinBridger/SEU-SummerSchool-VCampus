package app.vcampus.server.utility.router;

import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.Utility;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Router {
    private Map<String, Object> controllerBeans = new HashMap<>();
    private Map<String, Action> uri2Action = new HashMap<>();
    private Map<String, String> uri2Role = new HashMap<>();

    public void addController(Class<?> cls) {
        try {
            log.info("Router: addController: cls: {}", cls.getName());

            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == RouteMapping.class) {
                        RouteMapping anno = (RouteMapping) annotation;
                        String uri = anno.uri();
                        String role = anno.role();
                        if (!controllerBeans.containsKey(cls.getName())) {
                            controllerBeans.put(cls.getName(), cls.getDeclaredConstructor().newInstance());
                        }
                        uri2Action.put(uri, new Action(controllerBeans.get(cls.getName()), method));
                        uri2Role.put(uri, role);
                        log.info("Router: addController: uri: {}, method: {}, role: {}", uri, method.getName(), role);
                    }
                }
            }

        } catch (Exception e) {
            log.error("Router: addRouter: Exception: {}", e.getMessage());
        }
    }

    public boolean hasRoute(String uri) {
        return uri2Action.containsKey(uri);
    }

    public String getRole(String uri) {
        return uri2Role.get(uri);
    }

    public Response invoke(Request request) {
        Action action = uri2Action.get(request.getUri());
        log.info("Router: invoke: action: {}", action);
        if (action != null) {
            return (Response) action.call(request);
        } else {
            return Utility.notFound();
        }
    }

    public void testRoute(String uri) {
        Action action = uri2Action.get(uri);
        log.info("Router: testRoute: action: {}", action);
//        if (action != null) {
//            action.call();
//        } else {
//            System.out.println(uri + " is not found");
//        }
    }

    private static class Action {
        private final Object object;
        private final Method method;

        public Action(Object object, Method method) {
            this.object = object;
            this.method = method;
        }

        public Object call(Request request) {
            try {
                return method.invoke(object, request);
            } catch (IllegalAccessException e) {
                log.error("Router: Action: call: IllegalAccessException: {}", e.getMessage());
            } catch (InvocationTargetException e) {
                log.error("Router: Action: call: InvocationTargetException: {}", e.getMessage());
            }

            return Utility.internalError();
        }
    }

}
