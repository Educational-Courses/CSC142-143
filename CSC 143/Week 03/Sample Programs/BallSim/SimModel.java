import java.util.*;

/**
 * A SimModel is a basic simulation framework for simulations of
 * collections of objects.  SimThings can be added to the model,
 * and SimViewers can ask to be notified regularly so they can
 * display the world.<p>
 *
 * The simulation repeats the following cycle as many times as requested:<br>
 *
 * Birth - anything added to the simulation on the previous cycle
 * is added to the world.<br>
 *
 * Action - all SimThings are asked to perform their action() method
 * in some unpredictable order.<br>
 *
 * View - all SimViewers are notified<p>
 * 
 * The birth and action operations are separated so new things added to
 * the simulation in the middle of one cycle don't affect other
 * actions on the same cycle (i.e., all actions on one cycle should
 * be carried out with the existing population of things, without newly
 * created things interacting with old ones in unexpected ways).<p>
 * 
 * This particular simulation is unrealistic in that the object coordinates
 * are the same as the display graphics coordinates.  In a more realistic
 * system, the object coordinates would be appropriate for the model and would
 * be scaled by the viewer to fit the view.<p>
 *
 * Originally based on simulation framework developed by Ben Dugan for CSE142/3.
 *
 * CSE143 demo Au01, Sp03, Sp04
 * 
 * @author Hal Perkins; updated by CSC 143 - VO
 * @version 10/18/01, 4/16/03, 4/17/04, 1/24/07
 */
public class SimModel {
  
  // Implementation note: this simulation implements a hand-crafted
  // observer pattern by directly keeping lists of things and viewers.
  // The standard library observer/observable types could be used instead
  // (and probably should be in production code), but this may be more
  // transparent and easier to follow.
  
  // instance variables
  private ArrayList<SimThing> things;    // List of all SimThings in this model
                                         // at the beginning of the current cycle.
  private ArrayList<SimThing> newThings; // SimThings added to this model during
                                         // the current cycle.
  private ArrayList<SimView> viewers;    // SimViews registered to view this
                                         // SimModel.
  private int cyclesToGo;         // Number of cycles remaining in the 
                                  //   current simulation
  private int width, height;      // Width and height of the simulated world
  
  private final int sleepTime = 50;   // # msec. to sleep between cycles
  
  /**
   * Constructor for objects of class SimModel
   * @param width Width of the simulated world
   * @param height Height of the simulated world
   */
  public SimModel(int width, int height) {
    // capture model size
    this.width = width;
    this.height = height;

    // Start with no SimThings and no new ones to add
    things     = new ArrayList<SimThing>();
    newThings  = new ArrayList<SimThing>();
    viewers    = new ArrayList<SimView>();
    cyclesToGo = 0;
  }
  
  /**
   * Add the given SimThing to the world after this cycle is complete.
   * @param t the SimThing to be added
   */
  public void add(SimThing t) {
    newThings.add(t);
  }
  
  /**
   * Add the given SimView to the list of viewers to be
   * notified each cycle.
   * @param v the new viewer
   */
  public void addView(SimView v) {
    viewers.add(v);
  }
  
  /**
   * Return a copy of the list of Things in the simulation
   * at the beginning of the current cycle.
   * @return a new list with a copy of all things currently in the simulation
   */
  public List getThings() {
    return new ArrayList<SimThing>(things);
  }
  
  /**
   * Return the horizontal size of the simulated world
   * @return width of the simulation
   */
  public int getWidth() {
    return width;
  }
  
  /**
   * Return the vertical size of the simulated world
   * @return height of the simulation
   */
  public int getHeight() {
    return height;
  }
  
  /**
   * Run the simulation for the requested number of cycles
   * @param nCycles number of cycles to run
   */
  public void go(int nCycles) {
    cyclesToGo = nCycles;
    runSim();
  }
  
  /**
   * Run the simulation indefinitely
   */
  public void go( ) {
    go(Integer.MAX_VALUE);
  }
  
  /**
   * Stop the simulation
   */
  public void stop() {
    cyclesToGo = 0;
  }
  
  /**
   * Run the simulation until number of requested cycles
   * performed, or until stopped.
   */
  private void runSim() {
    while (cyclesToGo > 0) {
      cycle();
      cyclesToGo--;
      try { Thread.sleep(sleepTime); }
      catch (Exception e) { /* ignore */ }
    }
  }
  
  /**
   * Run one simulation cycle
   */
  private void cycle() {
    // Add newborn things to the world, and set
    // list of newborns to empty to start this cycle
    things.addAll(newThings);
    newThings.clear();
    
    // Perform actions for all things
    Iterator<SimThing> it = things.iterator();
    while (it.hasNext()) {
      SimThing t = it.next();
      t.action();
    }
    
    // Notify all viewers
    Iterator<SimView> vit = viewers.iterator();
    while (vit.hasNext()) {
      SimView v = vit.next();
      v.notifyViewer();
    }
  }
  
}
