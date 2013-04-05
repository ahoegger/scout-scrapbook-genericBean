/**
 * 
 */
package ch.ahoegger.beanAnnotation.server.data;

import ch.ahoegger.beanAnnotation.shared.beans.Person;
import ch.ahoegger.beanAnnotation.shared.beans.Persons;

/**
 * @author aho
 */
public final class PersonDataStore {

  private static PersonDataStore instance;
  private Persons m_allPersons;

  private PersonDataStore() {
    // setup
    m_allPersons = new Persons();
    m_allPersons.addPerson(new Person(Long.valueOf(1), "Milinkovich", "Mike"));
    m_allPersons.addPerson(new Person(Long.valueOf(2), "Mueller", "Ralph"));
    m_allPersons.addPerson(new Person(Long.valueOf(3), "Beaton", "Wayne"));
  }

  public static PersonDataStore getInstance() {
    if (instance == null) {
      instance = new PersonDataStore();
    }
    return instance;
  }

  public static Persons getPersons() {
    return getInstance().m_allPersons;
  }

  public static void updatePerson(Person p) {
    getInstance().m_allPersons.addPerson(p);
  }

  /**
   * @param id
   * @return
   */
  public static Person getPerson(Long id) {
    return getInstance().m_allPersons.getPerson(id);
  }

}
