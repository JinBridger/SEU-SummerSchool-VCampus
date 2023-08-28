package app.vcampus.server.utility.router;

import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Router {
    private final Map<String, Object> controllerBeans = new HashMap<>();
    private final Map<String, Action> uri2Action = new HashMap<>();
    private final Map<String, String> uri2Role = new HashMap<>();

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

    public Response invoke(Request request, Session database) {
        Action action = uri2Action.get(request.getUri());
        log.info("Router: invoke: action: {}", action);
        if (action != null) {
            return (Response) action.call(request, database);
        } else {
            return Response.Common.notFound();
        }
    }

    private record Action(Object object, Method method) {

        public Object call(Request request, Session database) {
            try {
                return method.invoke(object, request, database);
            } catch (IllegalAccessException e) {
                log.error("Router: Action: call: IllegalAccessException: {}", e.getMessage());
            } catch (InvocationTargetException e) {
                log.error("Router: Action: call: InvocationTargetException: {}", e.getMessage());
            }

            return Response.Common.internalError();
        }
    }

}
