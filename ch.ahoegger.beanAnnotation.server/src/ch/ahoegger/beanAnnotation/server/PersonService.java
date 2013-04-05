package ch.ahoegger.beanAnnotation.server;

import org.eclipse.scout.commons.exception.IProcessingStatus;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import ch.ahoegger.beanAnnotation.server.data.PersonDataStore;
import ch.ahoegger.beanAnnotation.shared.IPersonService;
import ch.ahoegger.beanAnnotation.shared.beans.Person;
import ch.ahoegger.beanAnnotation.shared.beans.Persons;
import ch.ahoegger.beanAnnotation.shared.services.validation.IPersonValidationService;

public class PersonService extends AbstractService implements IPersonService {

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.shared.IPersonService#loadPerson(java.lang.Long)
   */
  @Override
  public Person loadPerson(Long id) {
    return PersonDataStore.getPerson(id);
  }

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.shared.IPersonService#storePerson(ch.ahoegger.beanAnnotation.shared.beans.Person)
   */
  @Override
  public IProcessingStatus storePerson(Person p) throws ProcessingException {
    // server side validation
    IProcessingStatus result = SERVICES.getService(IPersonValidationService.class).validate(p);
    if (result == null) {
      PersonDataStore.updatePerson(p);
    }
    return result;

  }

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.shared.IPersonService#getAllPersons()
   */
  @Override
  public Persons getAllPersons() {
    return PersonDataStore.getPersons();
  }
}
