package ch.ahoegger.beanAnnotation.client.ui.forms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import ch.ahoegger.beanAnnotation.client.PersonForm;
import ch.ahoegger.beanAnnotation.client.ui.forms.DesktopForm.MainBox.PersonsTableField;
import ch.ahoegger.beanAnnotation.shared.IPersonService;
import ch.ahoegger.beanAnnotation.shared.Icons;
import ch.ahoegger.beanAnnotation.shared.beans.IPropertyBean;
import ch.ahoegger.beanAnnotation.shared.beans.Person;
import ch.ahoegger.beanAnnotation.shared.beans.Persons;

public class DesktopForm extends AbstractBeanAnnotationForm {

  public DesktopForm() throws ProcessingException {
    super();
  }

  /* (non-Javadoc)
   * @see ch.ahoegger.beanAnnotation.client.ui.forms.AbstractBeanAnnotationForm#getConfiguredPropertyBeanClass()
   */
  @Override
  protected Class<? extends IPropertyBean> getConfiguredPropertyBeanClass() {
    return Persons.class;
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_CENTER;
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.EclipseScout;
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public PersonsTableField getPersonsTableField() {
    return getFieldByClass(PersonsTableField.class);
  }

  /**
   * @throws ProcessingException
   */
  private void reloadTable() throws ProcessingException {
    IPersonService service = SERVICES.getService(IPersonService.class);
    importPropertyBean(service.getAllPersons());

  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    @Binding(Persons.ALL_PERSONS)
    public class PersonsTableField extends AbstractTableField<PersonsTableField.Table> {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(10.0)
      public class Table extends AbstractExtensibleTable {

        public NameColumn getNameColumn() {
          return getColumnSet().getColumnByClass(NameColumn.class);
        }

        public PrenameColumn getPrenameColumn() {
          return getColumnSet().getColumnByClass(PrenameColumn.class);
        }

        @Override
        protected String getConfiguredDefaultIconId() {
          return Icons.Eye;
        }

        public IdColumn getIdColumn() {
          return getColumnSet().getColumnByClass(IdColumn.class);
        }

        @Order(10.0)
        @Binding(Person.ID)
        public class IdColumn extends AbstractLongColumn {
        }

        @Order(20.0)
        @Binding(Person.NAME)
        public class NameColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Name");
          }

          @Override
          protected int getConfiguredWidth() {
            return 200;
          }
        }

        @Order(30.0)
        @Binding(Person.PRENAME)
        public class PrenameColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Prename");
          }

          @Override
          protected int getConfiguredWidth() {
            return 200;
          }
        }

        @Order(10.0)
        public class EditPersonMenu extends AbstractExtensibleMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("EditPerson");
          }

          @Override
          protected void execAction() throws ProcessingException {
            PersonForm form = new PersonForm();
            form.setPersonId(getIdColumn().getSelectedValue());
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
              reloadTable();
            }
          }

        }
      }
    }
  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      reloadTable();

    }
  }

  public void startView() throws ProcessingException {
    startInternal(new ViewHandler());
  }
}
