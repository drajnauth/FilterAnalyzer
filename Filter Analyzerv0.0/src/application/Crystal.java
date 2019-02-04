package application;

public class Crystal {
	private double Cm;
	private double Lm;
	private double Rm;
	private double Cc;
	private Complex z;
	
	public Crystal () {
		this.z = new Complex();
	}
	
	public Crystal (double Cm, 	double Lm, double Rm, double Cc) {
		this.z = new Complex();
		this.Cm = Cm;
		this.Lm = Lm;
		this.Rm = Rm;
		this.Cc = Cc;
	}

	public void setCm (double Cm) {
		this.Cm = Cm;
	}
	
	public void setLm (double Lm) {
		this.Lm = Lm;
	}
	
	public void setRm (double Rm) {
		this.Rm = Rm;
	}
	
	public void setCc (double Cc) {
		this.Cc = Cc;
	}

	public double getCm () {
		return this.Cm;
	}
	
	public double getLm () {
		return this.Lm;
	}
	
	public double getRm () {
		return this.Rm;
	}
	
	public double getCc () {
		return this.Cc;
	}
	
	public Complex getImpedance (double f) {
		double omega = 2.0 * Math.PI * f; 
		double xl = omega * this.Lm;
		double xcm = -1.0 / (omega * this.Cm);

		Complex one = new Complex (Rm, (xl+xcm));
		Complex ret = new Complex();
		ret = one.get();
		
		if (this.Cc > 0) {
			double xcc = -1.0 / (omega * this.Cc);
			Complex two = new Complex (0.0, xcc);
			ret = new Complex();
			ret = Complex.parallel(one, two);
		} 
		
		return ret;
	}
	
	public void setImpedance (Complex z) {
		this.z = z.get();
	}
	
	
}
