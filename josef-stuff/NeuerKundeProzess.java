import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;
import java.util.concurrent.TimeUnit;

public class NeuerKundeProzess extends SimProcess {
  public FriseurModell model;

  public NeuerKundeProzess(Model owner, String name, boolean showInTrace) {
    super(owner, name, showInTrace);
    model = (FriseurModell) owner;
  }

  public void lifeCycle() throws SuspendExecution {
    while (true) {
      double z = model.zwischenAnkunftsZeit.sample();
      hold(new TimeSpan(z, TimeUnit.SECONDS));
      KundeProzess kp = new KundeProzess(model, "Kunde", true);
      kp.activate();
    }
  }
}
