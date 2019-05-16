import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class KundeProzess extends SimProcess {
  public FriseurModell model;
  public List<FriseurProzess> friseure;

  public KundeProzess(Model owner, String name, boolean showInTrace) {
    super(owner, name, showInTrace);
    model = (FriseurModell) owner;
    friseure = new ArrayList<>();
    for (FriseurProzess f : model.friseure)
      friseure.add(f);
    if (model.shufflePreferences)
      Collections.shuffle(friseure);
  }

  public void lifeCycle() throws SuspendExecution {
    model.kunden.insert(this);
    model.kundenPhase1.insert(this);
    
    for (FriseurProzess f : friseure) {
      if (f.kunden.isEmpty()) {
        f.activateAfter(this);
        break;
      }
    }
    
    passivate();
  }
  
  // Bedienung des Kunden beginnt
  public void phase2() {
    model.kundenPhase1.remove(this);
    model.kundenPhase2.insert(this);
  }
  
  // Bedienung vorbei, Salon wird verlassen
  public void phase3() {
    model.kundenPhase2.remove(this);
    model.kunden.remove(this);
  }
}
