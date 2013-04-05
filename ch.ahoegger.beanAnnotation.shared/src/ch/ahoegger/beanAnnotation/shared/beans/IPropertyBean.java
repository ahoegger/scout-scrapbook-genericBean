/**
 * 
 */
package ch.ahoegger.beanAnnotation.shared.beans;

import java.io.Serializable;

/**
 * @author aho
 */
public interface IPropertyBean extends Serializable {

  /**
   * @param key
   * @param value
   */
  void setProperty(String key, Object value);

  /**
   * @param key
   * @return
   */
  Object getProperty(String key);

}
