/**
 * 
 */
package ch.ahoegger.beanAnnotation.client.ui.forms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author aho
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Binding {
  String value();
}
