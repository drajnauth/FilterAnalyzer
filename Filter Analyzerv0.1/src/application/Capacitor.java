package application;

public class Capacitor {
	private double c;
	Complex z;
	
	public Capacitor () {
		z = new Complex();
	}
	
	public Capacitor (double c) {
		z = new Complex();
		this.c = c;
	}
	
	public double getC () {
		return this.c;
	}
	
	public void setC (double c) {
		this.c = c;
	}
	
	public Complex getImpedance (double f) {
		double omega = 2.0 * Math.PI * f; 
		Complex ret = new Complex (0.0, (-1.0 / (omega * this.c)) );
		return ret;
	}
	
	public void setImpedance (Complex z) {
		this.z = z.get();
	}
	
}
