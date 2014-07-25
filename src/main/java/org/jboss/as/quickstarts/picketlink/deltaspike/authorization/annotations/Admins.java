package org.jboss.as.quickstarts.picketlink.deltaspike.authorization.annotations;

import org.apache.deltaspike.security.api.authorization.SecurityBindingType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 19.07.14
 * Time: 23:05
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@SecurityBindingType
public @interface Admins {
}
