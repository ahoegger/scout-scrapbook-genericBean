/**
 * 
 */
package ch.ahoegger.beanAnnotation.shared.services.validation;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.commons.exception.ProcessingStatus;
import org.eclipse.scout.service.AbstractService;

import ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean;
import ch.ahoegger.beanAnnotation.shared.beans.Person;

/**
 * @author aho
 */
public class LocalPersonValidationService extends AbstractService implements IPersonValidationService {

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.shared.services.validation.IValidationService#validate(ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean)
   */
  @Override
  public IProcessingStatus validate(IPropertyBean propertyBean) {
    Person person = (Person) propertyBean;
    if (StringUtility.isNullOrEmpty(person.getName())) {
      return new ProcessingStatus("[Client validation] Name can not be null or empty!", IProcessingStatus.ERROR);
    }
    if (StringUtility.isNullOrEmpty(person.getPrename())) {
      return new ProcessingStatus("[Client validation] Prename should not be null or empty!", IProcessingStatus.WARNING);
    }
    return null;
  }

}
