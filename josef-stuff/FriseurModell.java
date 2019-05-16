import java.util.Iterator;
import desmoj.core.dist.*;
import desmoj.core.report.Reporter;
import desmoj.core.simulator.*;

public class FriseurModell extends Model {
  public ProcessQueue<KundeProzess> kunden;
  public ProcessQueue<KundeProzess> kundenPhase1;
  public ProcessQueue<KundeProzess> kundenPhase2;
  public ProcessQueue<FriseurProzess> friseure;
  public ContDistExponential zwischenAnkunftsZeit;
  public ContDistUniform bedienZeit;
  
  public int seed, friseurCount, ankunftFrequenz, bedienMinimum, bedienMaximum, zeitraum;
  public boolean shufflePreferences;
  
  public FriseurModell(Model owner, String modelName, boolean showInReport, boolean showInTrace) {
    super(owner, modelName, showInReport, showInTrace);
  }
  
  public void init() {
    kunden = new ProcessQueue<>(this, "Kunden im Salon", true, true);
    kundenPhase1 = new ProcessQueue<>(this, "Kunden wartend", true, true);
    kundenPhase2 = new ProcessQueue<>(this, "Kunden in Bedienung", true, true);
    friseure = new ProcessQueue<>(this, "Friseure", true, true);
    for (int i = 0; i < friseurCount; i++) {
      String friseurName = "Friseur " + (char) ('A' + i);
      FriseurProzess friseur = new FriseurProzess(this, friseurName, true);
      friseure.insert(friseur);
    }
    
    zwischenAnkunftsZeit = new ContDistExponential(this, "Zwischenankunftszeit", ankunftFrequenz, true, true);
    zwischenAnkunftsZeit.setNonNegative(true);
    bedienZeit = new ContDistUniform(this, "Bedienzeit", bedienMinimum, bedienMaximum, true, true);
  }
  
  public void doInitialSchedules() {
    NeuerKundeProzess nkp = new NeuerKundeProzess(this, "NeuerKundeProzess", true);
    nkp.activate();
  }
  
  public String description() {
    return "FriseurModell";
  }
  
  public Reporter[] runExperiment(int seed, int friseurCount, int ankunftFrequenz, int bedienMinimum, int bedienMaximum, boolean shufflePreferences, int zeitraum) {
  	
    this.seed = seed;
    this.friseurCount = friseurCount;
    this.ankunftFrequenz = ankunftFrequenz; // Sekunden!
    this.bedienMinimum = bedienMinimum; // Sekunden!
    this.bedienMaximum = bedienMaximum; // Sekunden!
    this.shufflePreferences = shufflePreferences;
    this.zeitraum = zeitraum; // Sekunden!
    
     
    Experiment experiment = new Experiment("experiment");
    this.connectToExperiment(experiment);
    experiment.setShowProgressBar(false);
    experiment.setSeedGenerator(seed);
    experiment.getDistributionManager().newSeedAll();
    experiment.debugOn(new TimeInstant(0));
    experiment.traceOn(new TimeInstant(0));
    experiment.stop(new TimeInstant(zeitraum));
    experiment.start();
    experiment.report();
    experiment.finish();
    
    Reporter[] reports = new Reporter[3+friseurCount];		
    reports[0] = kunden.getReporter();
    reports[1] = kundenPhase1.getReporter();
    reports[2] = kundenPhase2.getReporter();
    
    Iterator<FriseurProzess> iter = friseure.iterator();
    
    
    for (int i = 0; i < friseurCount; i++) {
    	FriseurProzess fp = (FriseurProzess)iter.next();
    	reports[i+3] = fp.kunden.getReporter(); ;
    }
    
   
    return reports;
    
    
    
  }
}
