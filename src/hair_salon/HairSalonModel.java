package src.hair_salon;

import desmoj.core.simulator.*;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import desmoj.core.dist.*;
import desmoj.core.report.Reporter;

/**
 * DESMO-J Hair Salon Model.
 * 
 * @author Michael Schreiber, Markus Innerlohinger
 *
 */
public class HairSalonModel extends Model {
	/**
	 * Random generator for customer arrival.
	 */
	protected ContDistExponential customerArrivalTime;

	/**
	 * Random generator for service time (when serviced by a hair stylist).
	 */
	protected ContDistUniform serviceTime;

	/**
	 * Customers in hair salon queue.
	 */
	protected ProcessQueue<CustomerProcess> customersInHairSalonQueue;
	
	/**
	 * Customers waiting for service queue.
	 */
	protected ProcessQueue<CustomerProcess> customersWaitingForServiceQueue;
	
	/**
	 * Customers being serviced queue.
	 */
	protected ProcessQueue<CustomerProcess> customersBeingServicedQueue;

	/**
	 * Hair stylists queue.
	 */
	protected ProcessQueue<HairStylistProcess> hairStylistsQueue;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 * @param name
	 * @param showInReport
	 * @param showIntrace
	 */
	public HairSalonModel(Model owner, String name, boolean showInReport, boolean showIntrace) {
		super(owner, name, showInReport, showIntrace);
	}

	/**
	 * Model description.
	 */
	public String description() {
		return "Hair Salon Model (process oriented)";
	}

	/**
	 * Simulation initial stuff.
	 */
	public void doInitialSchedules() {
		// setup new customer process
		NewCustomerProcess newCustomer = new NewCustomerProcess(this, "New Customer", true);
		newCustomer.activate(new TimeSpan(0.0));
	}

	/**
	 * DESMO-J init.
	 */
	public void init() {
		// customer arrival time
		this.customerArrivalTime = new ContDistExponential(this, "Customer Arrival Time", Config.CUSTOMER_ARRIVAL_MEAN, true, true);
		// exponential distribution may give negative values
		this.customerArrivalTime.setNonNegative(true);

		// service time
		this.serviceTime = new ContDistUniform(this, "Service Time", Config.SERVICE_TIME_LOWER, Config.SERVICE_TIME_UPPER, true, true);

		// customer queues
		this.customersInHairSalonQueue = new ProcessQueue<CustomerProcess>(this, "Customers In Hair Salon Queue", 0, 10 + Config.MAX_HAIR_STYLISTS, true, true);
		this.customersWaitingForServiceQueue = new ProcessQueue<CustomerProcess>(this, "Customers Waiting For Service Queue",0 , 10, true, true);
		this.customersBeingServicedQueue = new ProcessQueue<CustomerProcess>(this, "Customers Being Serviced Queue",0,Config.MAX_HAIR_STYLISTS, true, true);

		// hair stylists queue
		this.hairStylistsQueue = new ProcessQueue<HairStylistProcess>(this, "Hair Stylists Queue", true, true);
		
		// A, B, C,...
		for (int i = 0; i < Config.MAX_HAIR_STYLISTS; ++i) {
			this.hairStylistsQueue.insert(new HairStylistProcess(this, "Hair Stylist " + (char) ('A' + i), true));
		}
	}
	
	/**
	 * Experiment from external source.
	 * 
	 * @param seed
	 * @param evaluateCosts
	 * @return
	 */
	public Reporter[] run() {
		Experiment experiment = new Experiment("Hair-Salon-Process", "reports");
		SimClock time = new SimClock("time");

		HairSalonModel hairSalonModel = new HairSalonModel(null, "Hair Salon Model", true, true);
		hairSalonModel.connectToExperiment(experiment);

		experiment.setSeedGenerator(Config.SEED);
		experiment.setShowProgressBar(false);
		experiment.debugOn(new TimeInstant(0));
		experiment.traceOn(new TimeInstant(0));
		experiment.stop(new TimeInstant(Config.RUNTIME_MINUTES, TimeUnit.MINUTES));
		experiment.setSilent(true);
		experiment.start();
		
		// ...simulation running now - on complete:
		
		//experiment.report();
		experiment.finish();
		
		Reporter[] reports = new Reporter[3 + Config.MAX_HAIR_STYLISTS];
		
	    reports[0] = hairSalonModel.customersInHairSalonQueue.getReporter();
	    reports[1] = hairSalonModel.customersWaitingForServiceQueue.getReporter();
	    reports[2] = hairSalonModel.customersBeingServicedQueue.getReporter();
	    
	    Iterator<HairStylistProcess> iter = hairSalonModel.hairStylistsQueue.iterator();
	    for (int i = 0; i < Config.MAX_HAIR_STYLISTS; ++i) {
	    	HairStylistProcess hairStylistProcess = (HairStylistProcess) iter.next();
	    	reports[3 + i] = hairStylistProcess.customers.getReporter();
	    }
	    
	    return reports;
	}
	
	/**
	 * Internal experiment.
	 * 
	 * @param args
	 */
	public static void main(java.lang.String[] args) {
		Experiment experiment = new Experiment("Hair-Salon-Process", "reports");
		
		HairSalonModel hairSalonModel = new HairSalonModel(null, "Hair Salon Model", true, true);
		hairSalonModel.connectToExperiment(experiment);
		
		experiment.setSeedGenerator(Config.SEED);
		experiment.setShowProgressBar(true);
		experiment.debugOn(new TimeInstant(0));
	    experiment.traceOn(new TimeInstant(0));
		experiment.stop(new TimeInstant(Config.RUNTIME_MINUTES, TimeUnit.MINUTES));
		experiment.start();
		
		// ...simulation running now - on complete:
		
		experiment.report();
		experiment.finish();
	}
}