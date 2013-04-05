/**
 * 
 */
package ch.ahoegger.beanAnnotation.shared.beans;

import java.util.HashMap;

/**
 * @author aho
 */
public abstract class AbstractPropertyBean implements IPropertyBean {

  private static final long serialVersionUID = 1L;

  private final HashMap<String, Object> m_propertyMap;

  public AbstractPropertyBean() {
    m_propertyMap = new HashMap<String, Object>();
  }

  @Override
  public void setProperty(String key, Object value) {
    m_propertyMap.put(key, value);
  }

  @Override
  public Object getProperty(String key) {
    return m_propertyMap.get(key);
  }
}
