import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;
import java.util.concurrent.TimeUnit;

public class FriseurProzess extends SimProcess {
  public FriseurModell model;
  public ProcessQueue<KundeProzess> kunden;

  public FriseurProzess(Model owner, String name, boolean showInTrace) {
  	
  	
    super(owner, name, showInTrace);
    model = (FriseurModell) owner;
    String queueName = "Kunden " + name;
    kunden = new ProcessQueue<>(model, queueName, true, true);
  }

  public void lifeCycle() throws SuspendExecution {
  while (true) {
      if (model.kundenPhase1.isEmpty())
        passivate();
      KundeProzess k = model.kundenPhase1.get(0);
      kunden.insert(k);
      k.phase2(); // Kunde Phase 2
      double z = model.bedienZeit.sample();
      hold(new TimeSpan(z, TimeUnit.SECONDS));
      kunden.removeFirst();
      k.phase3(); // Kunde Phase 3
      k.activate(); // release
    }
  }
}
