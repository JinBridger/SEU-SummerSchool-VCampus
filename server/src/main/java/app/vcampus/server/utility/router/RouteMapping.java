package app.vcampus.server.utility.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RouteMapping annotation. Used to mark the uri and role as a route.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteMapping {
    String uri();

    String role() default "anonymous";
}