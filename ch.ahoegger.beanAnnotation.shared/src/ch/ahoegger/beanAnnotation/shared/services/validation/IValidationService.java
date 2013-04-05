/**
 * 
 */
package ch.ahoegger.beanAnnotation.shared.services.validation;

import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean;

/**
 * @author aho
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IValidationService extends IService2 {
  IProcessingStatus validate(IPropertyBean propertyBean);
}
