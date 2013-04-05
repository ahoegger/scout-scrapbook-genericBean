/**
 * 
 */
package ch.ahoegger.beanAnnotation.shared.beans;

import java.util.Collection;
import java.util.TreeSet;

import org.eclipse.scout.commons.CompareUtility;

/**
 * @author aho
 */
public class Persons extends AbstractPropertyBean {

  private static final long serialVersionUID = 1L;

  public static final String ALL_PERSONS = "allPersons";

  /**
   * 
   */
  public Persons() {
    setAllPersons(new TreeSet<Person>());
  }

  @SuppressWarnings("unchecked")
  public Collection<Person> getAllPersons() {
    return (Collection<Person>) getProperty(ALL_PERSONS);
  }

  public void setAllPersons(Collection<Person> allPersons) {
    setProperty(ALL_PERSONS, allPersons);
  }

  public void addPerson(Person p) {
    getAllPersons().remove(p);
    getAllPersons().add(p);
  }

  public Person getPerson(Long id) {
    for (Person p : getAllPersons()) {
      if (CompareUtility.equals(p.getId(), id)) {
        return p;
      }
    }
    return null;
  }
}
