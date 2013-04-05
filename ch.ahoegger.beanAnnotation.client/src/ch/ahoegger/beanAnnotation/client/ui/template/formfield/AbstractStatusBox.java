package ch.ahoegger.beanAnnotation.client.ui.template.formfield;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.ahoegger.beanAnnotation.shared.Icons;

public abstract class AbstractStatusBox extends AbstractSequenceBox {

  @Override
  protected boolean getConfiguredAutoCheckFromTo() {
    return false;
  }

  @Override
  protected int getConfiguredGridW() {
    return 2;
  }

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Status");
  }

  @Override
  protected boolean getConfiguredLabelVisible() {
    return false;
  }

  @Override
  protected boolean getConfiguredVisible() {
    return false;
  }

  public IconField getIconField() {
    return getFieldByClass(IconField.class);
  }

  public TextField getTextField() {
    return getFieldByClass(TextField.class);
  }

  public void setStatus(IStatus status) {
    if (status == null) {
      setVisible(false);
    }
    else {
      switch (status.getSeverity()) {
        case IStatus.ERROR:
          getIconField().setImageId(Icons.Error);
          break;
        case IStatus.WARNING:
          getIconField().setImageId(Icons.Warning);
          break;
        case IStatus.INFO:
          getIconField().setImageId(Icons.Info);
          break;
      }
      getTextField().setValue(status.getMessage());
      setVisible(true);
    }
  }

  @Order(10.0)
  public class IconField extends AbstractImageField {

    @Override
    protected boolean getConfiguredGridUseUiWidth() {
      return true;
    }

    @Override
    protected double getConfiguredGridWeightX() {
      return 0.0;
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }
  }

  @Order(20.0)
  public class TextField extends AbstractLabelField {

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }
  }
}
