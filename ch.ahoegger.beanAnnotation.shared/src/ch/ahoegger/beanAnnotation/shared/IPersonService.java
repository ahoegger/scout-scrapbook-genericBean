package ch.ahoegger.beanAnnotation.shared;

import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import ch.ahoegger.beanAnnotation.shared.beans.Person;
import ch.ahoegger.beanAnnotation.shared.beans.Persons;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IPersonService extends IService2 {

  Person loadPerson(Long id);

  IProcessingStatus storePerson(Person p) throws ProcessingException;

  /**
   * 
   */
  Persons getAllPersons();
}
