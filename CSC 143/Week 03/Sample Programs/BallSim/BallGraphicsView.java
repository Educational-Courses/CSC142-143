import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Graphical viewer for Ball simulation.  Should be added to a top-level
 * container for viewing, and should be added as a SimView in the model
 * we wish to view
 * 
 * @author Hal Perkins 
 * @version 10/18/01, 10/13/03, 4/12/04
 */

public class BallGraphicsView extends JPanel implements SimView {
  // instance variables
  private SimModel model;  // the SimModel we are viewing
  
  /**
   * Construct a new BallGraphicsView viewer for the given model
   * @param model  The model we are watching
   */
  public BallGraphicsView(SimModel model) {
    this.model = model;
  }
  
  /**
   * React when notified of a change by a model by repainting this view
   */
  public void notifyViewer() {
    repaint();
  }
  
  /**
   * Repaint the simulation by requesting each item to repaint itself
   * @param g the graphics context where the painting should take place
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // clear frame to background color (white, in this case)
    Rectangle bounds = getBounds();
    g.clearRect(0, 0, bounds.width, bounds.height);
    
    java.util.List things = model.getThings();
    Iterator it = things.iterator();
    while (it.hasNext()) {
      SimThing thing = (SimThing)it.next();
      thing.paintComponent(g);
    }
  }
  
}
