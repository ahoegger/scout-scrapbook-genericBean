package ch.ahoegger.beanAnnotation.ui.swt.views;

import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipse.scout.rt.ui.swt.window.desktop.view.AbstractScoutView;

import ch.ahoegger.beanAnnotation.ui.swt.Activator;

public class NorthView extends AbstractScoutView {

  public NorthView() {
  }

  @Override
  protected ISwtEnvironment getSwtEnvironment() {
    return Activator.getDefault().getEnvironment();
  }
}
