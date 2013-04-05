/**
 * 
 */
package ch.ahoegger.beanAnnotation.shared.beans;

import org.eclipse.scout.commons.CompareUtility;

/**
 * @author aho
 */
public class Person extends AbstractPropertyBean implements Comparable<Person> {
  private static final long serialVersionUID = 1L;

  public static final String ID = "ID";
  public static final String NAME = "NAME";
  public static final String PRENAME = "PRENAME";

  public Person() {

  }

  public Person(Long id, String name, String prename) {
    setId(id);
    setName(name);
    setPrename(prename);

  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    setProperty(ID, id);
  }

  /**
   * @return the id
   */
  public Long getId() {
    return (Long) getProperty(ID);
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    setProperty(NAME, name);
  }

  /**
   * @return the name
   */
  public String getName() {
    return (String) getProperty(NAME);
  }

  /**
   * @param prename
   *          the prename to set
   */
  public void setPrename(String prename) {
    setProperty(PRENAME, prename);
  }

  /**
   * @return the prename
   */
  public String getPrename() {
    return (String) getProperty(PRENAME);
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Person arg0) {
    if (arg0 == null) {
      return -1;
    }
    return getId().intValue() - arg0.getId().intValue();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Person)) {
      return false;
    }
    return CompareUtility.equals(getId(), ((Person) obj).getId());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return getId().intValue();
  }

}
