package application;

public class CircuitParameters {

	private Complex Vj1; 
	private Complex Vj2;
	private Complex Vj3;
	
	private Complex Vin;
	private Complex Vout;	
	private Complex gain;
	private Complex i1;
	private Complex i2;
	private Complex i3;
	private Complex i4;
	private Complex i5;
	
	Notifications note;
	
	public CircuitParameters() {
		note = new Notifications();
	}
	
	public void initializeValues () {
		this.Vj1 = new Complex();
		this.Vj2 = new Complex();
		this.Vj3 = new Complex();
		
		this.Vin = new Complex ();
		this.Vout = new Complex ();		
		this.gain = new Complex ();
		this.i1 = new Complex();
		this.i2 = new Complex();
		this.i3 = new Complex();
		this.i4 = new Complex();
		this.i5 = new Complex();
	}
	
	public void setJunctionVoltages (String name, Complex value) {
		if (name.contains("Vj1")) {
			this.Vj1 = value.get();
			this.Vj1.setabs();
		} else if (name.contains("Vj2")) {
			this.Vj2 = value.get();
			this.Vj2.setabs();
		} else if (name.contains("Vj3")) {
			this.Vj3 = value.get();
			this.Vj3.setabs();
		} else {
			note.alert ("Inproper Values", "Incorrect junction voltage specified for CircuitParameter");		
		}
	}

	public void setCurrent (String name, Complex value) {
		if (name.contains("i1")) {
			this.i1 = value.get();
			this.i1.setabs();
		} else if (name.contains("i2")) {
			this.i2 = value.get();
			this.i2.setabs();
		} else if (name.contains("i3")) {
			this.i3 = value.get();
			this.i3.setabs();
		} else if (name.contains("i4")) {
			this.i4 = value.get();
			this.i4.setabs();
		} else if (name.contains("i5")) {
			this.i5 = value.get();
			this.i5.setabs();
		} else {
			note.alert ("Inproper Values", "Incorrect current specified for CircuitParameter");		
		}
	}
	
	public void setVoltage (String name, Complex value) {
		if (name.contains("Vin")) {
			this.Vin = value.get();
			this.Vin.setabs();
		} else if (name.contains("Vout")) {
			this.Vout = value.get();
			this.Vout.setabs();
		} else {
			note.alert ("Inproper Values", "Incorrect voltage specified for CircuitParameter");		
		}
	}

	public Complex getJunctionVoltages (String name) {
		if (name.contains("Vj1")) return this.Vj1;
		else if (name.contains("Vj2")) return this.Vj2;
		else if (name.contains("Vj3")) return this.Vj3;
		else {
			note.alert ("Inproper Values", "Incorrect junction voltage specified for CircuitParameter");		
			return null;
		}
	}

	public Complex getCurrent (String name) {
		if (name.contains("i1")) return this.i1;
		else if (name.contains("i2")) return this.i2;
		else if (name.contains("i3")) return this.i3;
		else if (name.contains("i4")) return this.i4;
		else if (name.contains("i5")) return this.i5;
		else {
			note.alert ("Inproper Values", "Incorrect current specified for CircuitParameter");		
			return null;
		}
	}
	
	public Complex getVoltage (String name) {
		if (name.contains("Vin")) return this.Vin;
		else if (name.contains("Vout")) return this.Vout;
		else {
			note.alert ("Inproper Values", "Incorrect voltage specified for CircuitParameter");		
			return null;
		}
	}
	
	public void setGain (Complex value) {
		this.gain = value.get();
		this.gain.setabs();
	}
	
	public Complex getGain () {
		return this.gain;
	}
	
	
	
}
