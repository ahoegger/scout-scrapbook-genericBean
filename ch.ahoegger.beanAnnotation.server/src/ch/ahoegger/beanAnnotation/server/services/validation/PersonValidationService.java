package ch.ahoegger.beanAnnotation.server.services.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.commons.exception.ProcessingStatus;

import ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean;
import ch.ahoegger.beanAnnotation.shared.beans.Person;
import ch.ahoegger.beanAnnotation.shared.services.validation.LocalPersonValidationService;

public class PersonValidationService extends LocalPersonValidationService {

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.shared.services.validation.IValidationService#validate(ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean)
   */
  @Override
  public IProcessingStatus validate(IPropertyBean propertyBean) {
    // call local validation first
    IProcessingStatus result = super.validate(propertyBean);
    if (result != null) {
      return result;
    }
    Person person = (Person) propertyBean;
    if (!"hugo".equalsIgnoreCase(person.getPrename())) {
      return new ProcessingStatus("Prename must be 'Hugo'", IStatus.ERROR);
    }
    return null;
  }
}
