package application;

public class Complex {
	
	private double re;
	private double im;
	private double len;
	private double ang;

	public Complex () {
	}
	
	public Complex (double re, double im) {
		this.re = re;
		this.im = im;
		this.len = re*re + im*im;
	}

	public void polar () {
		if (re != 0.0) {
			this.ang = Math.atan(this.im/this.re);
		} else {
			this.ang = 0;
			if (this.im > 0) this.ang = Math.atan(Math.PI/2);
			else if (this.im < 0) this.ang = Math.atan(Math.PI*1.5);
		}
	}
	
	
	public String showValue () {
		String output = "";
		String res = String.format("%.2E", this.re);
		String ims = String.format("%.2E", this.im);
		
		if (this.im < 0) output = res + " " + ims + "i";
		else output = res + " +" + ims + "i";
		return output;
	}

	public String showValueLong () {
		String output = "";
		
		if (this.im < 0) output = this.re + " " + this.im + "i";
		else output = this.re + " +" + this.im + "i";
		
		return output;
	}
	
	public Complex get () {
		Complex ret = new Complex();
		ret.im = this.im;
		ret.re = this.re;
		return  ret;
	}
	
	public double abs () {
		setabs();
		return this.len;
	}
	
	public void setabs () {
		this.len = Math.sqrt(this.re*this.re + this.im*this.im);
	}
	
	public static double abs (Complex cpx) {
		return (Math.sqrt(cpx.re*cpx.re + cpx.im*cpx.im));
	}
	
	public void add ( Complex cpx) {
		Complex ret = new Complex();
		ret.im = this.im + cpx.im;
		ret.re = this.re + cpx.re;
		this.re = ret.re;
		this.im = ret.im;
	}
	
	public static Complex addComplex ( Complex one, Complex two) {
		Complex ret = new Complex();
		ret.im = one.im + two.im;
		ret.re = one.re + two.re;
		return ret;
	}
	public void sub ( Complex cpx) {
		Complex ret = new Complex();
		ret.im = this.im - cpx.im;
		ret.re = this.re - cpx.re;
		this.re = ret.re;
		this.im = ret.im;
	}
	
	public static Complex subComplex ( Complex one, Complex two) {
		Complex ret = new Complex();
		ret.im = one.im - two.im;
		ret.re = one.re - two.re;
		return ret;
	}

//	this.re, this.im
//	cpx.re, cpx.im
	
	public void mult ( Complex cpx) {
		Complex ret = new Complex();
		ret.im = this.im * cpx.re + this.re * cpx.im;
		ret.re = this.re * cpx.re - this.im * cpx.im;
		this.re = ret.re;
		this.im = ret.im;
	}
	
	public static Complex multComplex ( Complex one, Complex two) {
		Complex ret = new Complex();
		ret.im = one.im * two.re + one.re * two.im;
		ret.re = one.re * two.re - one.im * two.im;
		return ret;
	}

//	this.re, this.im
//	cpx.re, cpx.im
	
	public void div ( Complex cpx) {
		Complex ret = new Complex(0,0);
		if (abs(cpx) == 0.0 || (cpx.im == 0.0 && cpx.re==0.0)) {
			System.out.println("Invalid Divisor");
			return;
		}
		ret.im = (this.im * cpx.re - this.re * cpx.im)  / (cpx.im*cpx.im + cpx.re*cpx.re);
		ret.re = (this.re * cpx.re + this.im * cpx.im) / (cpx.im*cpx.im + cpx.re*cpx.re);
		this.re = ret.re;
		this.im = ret.im;
	}
	
	public static Complex divComplex ( Complex one, Complex two) {
		Complex ret = new Complex(0,0);
		if (abs(two) == 0.0 || (two.im == 0.0 && two.re==0.0)) {
			System.out.println("Invalid Divisor");
			return ret;
		}
		ret.im = (one.im * two.re - one.re * two.im)  / (two.im*two.im + two.re*two.re);
		ret.re = (one.re * two.re + one.im * two.im) / (two.im*two.im + two.re*two.re);
		return ret;
	}
	
	public static Complex parallel ( Complex one, Complex two) {
		Complex cp1 = Complex.multComplex(one, two);
		Complex cp2 = Complex.addComplex(one, two);
		Complex ret = Complex.divComplex(cp1, cp2);
		return ret;
	}
	
	
	public void setReal (double re) {
		this.re = re;
	}
	
	public void setImaginary (double im) {
		this.im = im;
	}

	public double getReal () {
		return this.re;
	}
	
	public double getImaginary () {
		return this.im;
	}

}
	
