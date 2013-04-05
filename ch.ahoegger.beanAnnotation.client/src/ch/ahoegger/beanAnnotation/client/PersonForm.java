package ch.ahoegger.beanAnnotation.client;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import ch.ahoegger.beanAnnotation.client.PersonForm.MainBox.CancelButton;
import ch.ahoegger.beanAnnotation.client.PersonForm.MainBox.NameField;
import ch.ahoegger.beanAnnotation.client.PersonForm.MainBox.OkButton;
import ch.ahoegger.beanAnnotation.client.PersonForm.MainBox.PrenameField;
import ch.ahoegger.beanAnnotation.client.ui.forms.AbstractBeanAnnotationForm;
import ch.ahoegger.beanAnnotation.client.ui.forms.Binding;
import ch.ahoegger.beanAnnotation.shared.IPersonService;
import ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean;
import ch.ahoegger.beanAnnotation.shared.beans.Person;
import ch.ahoegger.beanAnnotation.shared.services.validation.IPersonValidationService;
import ch.ahoegger.beanAnnotation.shared.services.validation.IValidationService;

public class PersonForm extends AbstractBeanAnnotationForm {

  private Long m_personId;

  public PersonForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.client.ui.forms.AbstractBeanAnnotationForm#getConfiguredValidationService()
   */
  @Override
  protected Class<? extends IValidationService> getConfiguredValidationService() {
    return IPersonValidationService.class;
  }

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.client.ui.forms.AbstractBeanAnnotationForm#getConfiguredPropertyBeanClass()
   */
  @Override
  protected Class<? extends IPropertyBean> getConfiguredPropertyBeanClass() {
    return Person.class;
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public PrenameField getPrenameField() {
    return getFieldByClass(PrenameField.class);
  }

  @Order(10.0)
  public class MainBox extends RootBox {

    @Order(10.0)
    @Binding(Person.PRENAME)
    public class PrenameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Prename");
      }
    }

    @Order(20.0)
    @Binding(Person.NAME)
    public class NameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Name");
      }

      /* (non-Javadoc)
       * @see org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField#execValidateValue(java.lang.Object)
       */
      @Override
      protected String execValidateValue(String rawValue) throws ProcessingException {
        // TODO Auto-generated method stub
        return super.execValidateValue(rawValue);
      }
    }

    @Order(30.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    /* (non-Javadoc)
     * @see org.eclipse.scout.rt.client.ui.form.AbstractFormHandler#execLoad()
     */
    @Override
    protected void execLoad() throws ProcessingException {
      Person person = SERVICES.getService(IPersonService.class).loadPerson(Long.valueOf(1));
      importPropertyBean(person);
    }

    /* (non-Javadoc)
     * @see org.eclipse.scout.rt.client.ui.form.AbstractFormHandler#execStore()
     */
    @Override
    protected void execStore() throws ProcessingException {
      IProcessingStatus result = SERVICES.getService(IPersonService.class).storePerson((Person) exportToPropertyBean());
      if (result != null) {
        throw new VetoException(result);
      }
    }
  }

  @Binding(Person.ID)
  public Long getPersonId() {
    return m_personId;
  }

  @Binding(Person.ID)
  public void setPersonId(Long personId) {
    m_personId = personId;
  }
}
