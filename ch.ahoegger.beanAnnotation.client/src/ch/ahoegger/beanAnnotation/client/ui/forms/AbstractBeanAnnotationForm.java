/**
 * 
 */
package ch.ahoegger.beanAnnotation.client.ui.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.Collection;

import org.eclipse.scout.commons.annotations.ConfigPropertyValue;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.ITableField;
import org.eclipse.scout.rt.shared.services.common.exceptionhandler.IExceptionHandlerService;
import org.eclipse.scout.service.SERVICES;

import ch.ahoegger.beanAnnotation.client.ui.forms.AbstractBeanAnnotationForm.RootBox.StatusBox;
import ch.ahoegger.beanAnnotation.client.ui.template.formfield.AbstractStatusBox;
import ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean;
import ch.ahoegger.beanAnnotation.shared.services.validation.IValidationService;

/**
 * @author aho
 */
public abstract class AbstractBeanAnnotationForm extends AbstractForm {

  private PropertyChangeListener m_fieldValidationListener;
  private Class<? extends IValidationService> m_validationServiceClass;

  /**
   * @throws ProcessingException
   */
  public AbstractBeanAnnotationForm() throws ProcessingException {
    super();
  }

  @SuppressWarnings("unchecked")
  public void importPropertyBean(IPropertyBean bean) throws ProcessingException {
    // properties
    for (Method m : this.getClass().getMethods()) {
      if (m.getName().startsWith("set")) {
        Binding bindingAnnotation = m.getAnnotation(Binding.class);
        if (bindingAnnotation != null) {
          try {
            m.invoke(this, bean.getProperty(bindingAnnotation.value()));
          }
          catch (Exception e) {
            throw new ProcessingException("could not import property ''!", e);
          }
        }
      }
    }
    for (IFormField field : getAllFields()) {
      Binding bindingAnnotation = field.getClass().getAnnotation(Binding.class);
      if (bindingAnnotation != null) {
        String key = bindingAnnotation.value();
        Object property = bean.getProperty(key);
        if (ITableField.class.isAssignableFrom(field.getClass())) {
          importTableFieldData((ITableField) field, property);
        }
        else {
          ((IValueField) field).setValue(property);
        }
      }
    }
  }

  /**
   * @param tableField
   * @param property
   * @throws ProcessingException
   */
  private void importTableFieldData(ITableField tableField, Object property) throws ProcessingException {
    ITable table = tableField.getTable();
    table.deleteAllRows();
    if (property instanceof Collection) {
      Object[][] matrix = new Object[((Collection) property).size()][table.getColumnCount()];
      int row = 0;
      for (Object rowProperty : ((Collection) property)) {
        if (rowProperty instanceof IPropertyBean) {
          int column = 0;
          for (IColumn col : table.getColumns()) {
            Binding bindingAnnotation = col.getClass().getAnnotation(Binding.class);
            if (bindingAnnotation != null) {
              matrix[row][column] = ((IPropertyBean) rowProperty).getProperty(bindingAnnotation.value());
            }
            column++;
          }
        }
        row++;
      }
      table.addRowsByMatrix(matrix);
    }
    // TODO Auto-generated method stub

  }

  public IPropertyBean exportToPropertyBean() {
    Class<? extends IPropertyBean> configuredPropertyBeanClass = getConfiguredPropertyBeanClass();
    if (configuredPropertyBeanClass != null) {
      try {
        IPropertyBean propertyBean = configuredPropertyBeanClass.newInstance();
        // properties
        for (Method m : this.getClass().getMethods()) {
          if (m.getName().startsWith("get")) {
            Binding bindingAnnotation = m.getAnnotation(Binding.class);
            if (bindingAnnotation != null) {
              try {
                Object result = m.invoke(this);
                propertyBean.setProperty(bindingAnnotation.value(), result);
              }
              catch (Exception e) {
                throw new ProcessingException("could not import property ''!", e);
              }
            }
          }
        }
        // fields 
        for (IFormField field : getAllFields()) {
          Binding bindingAnnotation = field.getClass().getAnnotation(Binding.class);
          if (bindingAnnotation != null) {
            String key = bindingAnnotation.value();
            propertyBean.setProperty(key, ((IValueField) field).getValue());
          }
        }
        return propertyBean;
      }
      catch (Exception e) {
        SERVICES.getService(IExceptionHandlerService.class).handleException(new ProcessingException(this.getClass().getSimpleName(), e));
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.scout.rt.client.ui.form.AbstractForm#initFormInternal()
   */
  @Override
  protected void initFormInternal() throws ProcessingException {
    super.initFormInternal();
    Class<? extends IValidationService> validationServiceClass = getConfiguredValidationService();
    setValidationServiceClass(validationServiceClass);
  }

  /**
   * @param validationServiceClass
   */
  public void setValidationServiceClass(Class<? extends IValidationService> validationServiceClass) {
    m_validationServiceClass = validationServiceClass;
    if (m_validationServiceClass != null) {
      if (m_fieldValidationListener == null) {
        m_fieldValidationListener = new P_FieldChangeListener();
        getRootGroupBox().addSubtreePropertyChangeListener(IValueField.PROP_VALUE, m_fieldValidationListener);
      }
    }
    else {
      if (m_fieldValidationListener != null) {
        getRootGroupBox().removeSubtreePropertyChangeListener(m_fieldValidationListener);
        m_fieldValidationListener = null;
      }
    }
  }

  /**
   * @return the validationService
   */
  public Class<? extends IValidationService> getValidationServiceClass() {
    return m_validationServiceClass;
  }

  @Order(1100)
  @ConfigPropertyValue("null")
  protected Class<? extends IValidationService> getConfiguredValidationService() {
    return null;
  }

  public StatusBox getStatusBox() {
    return getFieldByClass(StatusBox.class);
  }

  @Order(1200)
  @ConfigPropertyValue("null")
  protected Class<? extends IPropertyBean> getConfiguredPropertyBeanClass() {
    return null;
  }

  @Order(10)
  public class RootBox extends AbstractGroupBox {
    @Order(1.0)
    public class StatusBox extends AbstractStatusBox {

    }
  }

  private class P_FieldChangeListener implements PropertyChangeListener {
    /* (non-Javadoc)
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      if (getValidationServiceClass() != null) {
        IProcessingStatus result = SERVICES.getService(getValidationServiceClass()).validate(exportToPropertyBean());
        getStatusBox().setStatus(result);

      }

    }
  }
}
