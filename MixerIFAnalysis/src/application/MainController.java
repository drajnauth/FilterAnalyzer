package application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {
	
	@FXML private TextArea myMessage;
	@FXML private TextField txtRF;
	@FXML private TextField txtRFBW;
	@FXML private TextField txtIFBW;
	@FXML private TextField txtIF;
	@FXML private Label txtLO;
	@FXML private TextField myText;
	@FXML private RadioButton addvaluesBTN;
	@FXML private RadioButton subvaluesBTN;
	@FXML private RadioButton verboseBTN;
	@FXML private RadioButton harmonicBTN;
	@FXML private RadioButton byproductBTN;
	@FXML private CheckBox sqrwaveCheckBox;
	
	private long LocalOsc = 0;
	private long RadioFreq = 0;
	private long currentrf = 0;
	private long IntFreq = 0;
	private long RFBandWid = 0;
	private long IFBandWid = 0;
	private int i = 0;
	private int n = 0;
	private int m = 0;
	private int o = 0;
	private int probcnt = 0;
	
	private long tlo, trf;
	
	private long[] tifplus = new long[100000];
	private long[] tifminus = new long[100000];
	private long[] ifharms = new long[100];
	
	private boolean  okflag = false;
	private boolean  verboseflag = false;
	private boolean  byprodanalysis = false;
	
	private String probno;
	
	private static final int MAX_SQUARE_HARMONICS = 5;
	private static final int MAX_SINE_HARMONICS = 3;
	private int maxharmonics;


	@FXML
	public void initialize() {
		myMessage.clear();
		
		txtRF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (!txtRF.getText().isEmpty() && txtRF.getText().length() > 0) {
						RadioFreq = Long.valueOf(txtRF.getText());
						if (RadioFreq <= 0) {
							myMessage.appendText("Invalid RF Frequency\n");
							txtRF.clear();
						}
						if (RadioFreq > 0 && IntFreq > 0) {
							if (addvaluesBTN.isSelected()) {
								LocalOsc = RadioFreq + IntFreq;
							} else {
								LocalOsc = Math.abs (RadioFreq - IntFreq);
							}				
							txtLO.setText(Long.toString(LocalOsc));
						}
					} 				
				} catch (Exception e) {
					myMessage.appendText("Improper value entered for RF: " + e.getMessage() + "\n");
					txtRF.clear();
				}
			
			}
    	});

		txtIF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (!txtIF.getText().isEmpty() && txtIF.getText().length() > 0) {
						IntFreq = Long.valueOf(txtIF.getText());
						if (IntFreq <= 0) {
							myMessage.appendText("Invalid IF Frequency\n");
							txtIF.clear();
						}
						if (RadioFreq > 0 && IntFreq > 0) {
							if (addvaluesBTN.isSelected()) {
								LocalOsc = RadioFreq + IntFreq;
							} else {
								LocalOsc = Math.abs (RadioFreq - IntFreq);
							}				
							txtLO.setText(Long.toString(LocalOsc));
						}
					} 				
				} catch (Exception e) {
					myMessage.appendText("Improper value entered for IF: " + e.getMessage() + "\n");
					txtIF.clear();
				}
			
			}
    	});
		
		
	}
	

	
	@FXML 
	public void ExecuteAnalysis (ActionEvent event) {
		myMessage.clear();
		
		if (txtRF.getText().isEmpty()) {
			myMessage.appendText("No RF Frequency Defined\n");
			return;
		} else {
			RadioFreq = Long.valueOf(txtRF.getText());
			if (RadioFreq <= 0) {
				myMessage.appendText("Invalid RF Frequency\n");
				return;
			}
		}
		
		if (txtIF.getText().isEmpty()) {
			myMessage.appendText("No Intermediate Frequency Defined\n");
			return;
		} else {
			IntFreq = Long.valueOf(txtIF.getText());
			if (IntFreq <= 0) {
				myMessage.appendText("Invalid Intermediate Frequency\n");
				return;
			}
		}
		
		if (txtIFBW.getText().isEmpty()) {
			myMessage.appendText("No IF Bandwidth Defined\n");
			return;
		} else {
			IFBandWid = Long.valueOf(txtIFBW.getText());
			if (IFBandWid <= 0 || IFBandWid > 10000) {
				myMessage.appendText("Invalid Bandwidth Value\n");
				return;
			}
		}
		
		if (txtRFBW.getText().isEmpty()) {
			myMessage.appendText("No RF Bandwidth Defined. Assume 0 Hz\n");
			RFBandWid = 0;
		} else {
			RFBandWid = Long.valueOf(txtRFBW.getText());
			if ( RFBandWid < IFBandWid) {
				myMessage.appendText("Bandwidth too small.  Must be larger than IF Bandwidth\n");
				return;
			}
			
			currentrf = (long) (maxharmonics * maxharmonics * Math.ceil((double)RFBandWid/(double)IFBandWid));
			if (RFBandWid <= 0 || RFBandWid > 2000000 || currentrf > 100000) {
				myMessage.appendText("Invalid Bandwidth Value\n");
				return;
			}
		}
		
		if (Math.abs (RadioFreq - IntFreq) < 1000) {
			myMessage.appendText("Invalid RF or IF\n");
			return;
		}
		
		if (addvaluesBTN.isSelected()) {
			LocalOsc = RadioFreq + IntFreq;
		} else {
			LocalOsc = Math.abs (RadioFreq - IntFreq);
		}	

		if (verboseBTN.isSelected()) {
			verboseflag = true;
		} else {
			verboseflag = false;
		}	
		
		if (byproductBTN.isSelected()) {
			byprodanalysis = true;
		} else {
			byprodanalysis = false;
		}	
		
		txtLO.setText(Long.toString(LocalOsc));

		myMessage.appendText("\n\nChecking Mixing Products for RF: " + String.format ("%.6f", ((double)RadioFreq/1000000.0)) + " LO: "+ String.format ("%.6f", ((double)LocalOsc/1000000.0)) +"\n");

		okflag = false;
		probno = "";
		probcnt = 1;
		
		int numharms = 0;
		i = 0;
		int steps = 0;
		
		if (sqrwaveCheckBox.isSelected()) {
			maxharmonics = MAX_SQUARE_HARMONICS;
			steps = 2;
		} else {
			maxharmonics = MAX_SINE_HARMONICS;
			steps = 1;
		}
		
		myMessage.appendText ("\nStarting Analysis of LO, RF and IF Harmonics\n\n");
		myMessage.appendText ("Analysis limited to " + maxharmonics + " harmonics\n");
		if (sqrwaveCheckBox.isSelected()) {
			myMessage.appendText ("Harmonics beyond this level contribute less than 0.5% of total power\n");
			myMessage.appendText ("With converstion loss about 7dbm, harmonic #" + maxharmonics + " will be down -14 dB\n\n");
		}
		
		for (currentrf=RadioFreq; currentrf<=(RadioFreq+RFBandWid); currentrf+=(IFBandWid)) {
			trf = currentrf;
			for (n=1; n<(maxharmonics+1); n+=steps) {
				tlo = (long)n*LocalOsc;
			
				probno = Integer.toString(n) + ": ";
			
				tifplus[i] = trf + tlo;
				tifminus[i] = Math.abs (trf - tlo);
				i++;
				numharms++;
				ifharms[0] = ifharms[1] = 0;
			
				for (o=2; o<(maxharmonics+1); o++) {
					ifharms[o] = IntFreq*o;
					
					//LO is close to IF
					if (Math.abs (tlo - ifharms[o]) <= IFBandWid) {
						if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + 
								"IF Harmonic: " + Integer.toString(o) + " (" + Long.toString(ifharms[o]) + 
								") close to LO Harmonic: " + Integer.toString(n) + " (" + Long.toString(tlo) + 
								")"+ "\n");
						okflag = true;
						probcnt++;
					}
					// RF is close to IF
					if (Math.abs (trf - ifharms[o]) <= IFBandWid) {
						if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + ""
								+ "IF Harmonic: " + Integer.toString(o) + " (" + Long.toString(ifharms[o]) + 
								") close to RF (" + Long.toString(currentrf) + "\n");
						okflag = true;
						probcnt++;
					}
				
					// - Mix of RF and LO close to IF
					if (Math.abs (trf - tlo - ifharms[o]) <= IFBandWid) {
						if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + 
								"IF Harmonic: " + Integer.toString(o) + " (" + Long.toString(ifharms[o]) + 
								") and RF ("+ Long.toString(currentrf) + ") and LO Harmonic: " + 
								Integer.toString(n) + " (" + Long.toString(tlo) + ") mix close to IF"+ "\n");
						okflag = true;
						probcnt++;
					}
				
					// + Mix of RF and LO close to IF within IF bandwidth
					if (Math.abs (trf + tlo - ifharms[o]) <= IFBandWid) {
						if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + 
								"IF Harmonic: " + Integer.toString(o) + " (" + Long.toString(ifharms[o]) + 
								") and RF ("+ Long.toString(currentrf) + " and LO Harmonic: " + Integer.toString(n) + " (" + Long.toString(tlo) + 
								") mix close to IF"+ "\n");
						okflag = true;
						probcnt++;
					}
				
				}
			
				if (m < 2 && Math.abs (currentrf - tlo) <= IFBandWid) {
					if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + "LO Harmonic " + 
							Integer.toString(n) + " (" + Long.toString(tlo) + ") close to RF " + 
							Long.toString(currentrf) + "\n");
					okflag = true;
					probcnt++;
				}
			
				if (Math.abs (trf - tlo) <= IFBandWid) {
					if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + "LO Harmonic: " + 
							Integer.toString(n) + " (" + Long.toString(tlo) + ") and RF ("+ 
							Long.toString(currentrf) + ") are too close\n");
					okflag = true;
					probcnt++;
				}
			
				if (Math.abs (trf - tlo - IntFreq) <= IFBandWid) {
					if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + "LO Harmonic: " + 
							Integer.toString(n) + " (" + Long.toString(tlo) + ") and RF (" + 
							Long.toString(currentrf) + " ) mix close to IF (" + IntFreq + ")\n");
					okflag = true;
					probcnt++;
				}
			
				if (Math.abs (trf + tlo - IntFreq) <= IFBandWid) {
					if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + "LO Harmonic: " + 
							Integer.toString(n) + " (" + Long.toString(tlo) + ") and RF (" + Long.toString(currentrf) +
							" ) mix close to IF (" + IntFreq + ")\n");
					okflag = true;
					probcnt++;
				}
			
			}			
		}
		if (okflag) myMessage.appendText (Integer.toString(--probcnt) + " Mixing problems found\n");
		else myMessage.appendText ("Done Analyzing LO and RF Harmonics!\n");
		
		
		if (!byprodanalysis) {
			return;
		}
		
		okflag = false;
		probno = "";
		probcnt = 1;
		myMessage.appendText ("\nStarting Analysis of Mixing of Byproducts...\n");
		
		for (m=0; m<numharms; m++) {			
			for (n=0; n<numharms; n++) {		
				probno = Integer.toString(m) + "x" + Integer.toString(n) + ": ";			
				// Check if close to LO
				if (Math.abs (tifminus[m] - tifplus[n]) <= IFBandWid || Math.abs (tifminus[m] + tifplus[n]) <= IFBandWid) {
					if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + "Byproduct " + Integer.toString(n) + " (" + Long.toString(tifplus[n]) + ") Close to Byproduct " + Integer.toString(m) + " (" + Long.toString(tifminus[m]) +") " + "\n");
					okflag = true;
					probcnt++;
				}
								
				// check if close to IF
				if (Math.abs (tifminus[m] - tifplus[n] - IntFreq) <= IFBandWid || Math.abs (tifminus[m] + tifplus[n] - IntFreq) <= IFBandWid) {
					if (verboseflag) myMessage.appendText(Integer.toString(probcnt) + ":" + probno + "Byproduct " + Integer.toString(n) + " (" + Long.toString(tifplus[n]) + ") and Byproduct " + Integer.toString(m) + " (" + Long.toString(tifminus[m]) +") Close to IF" + "\n");
					okflag = true;
					probcnt++;
				}
				
			}
		}
		if (okflag) myMessage.appendText (Integer.toString(--probcnt) + " Mixing problems found\n");
		else myMessage.appendText ("Done Analyzing Byroducts!\n");
		
		myMessage.appendText ("Array elements consumed: " + Integer.toString(i));
		
	}
	
	@FXML 
	public void ExitProgram (ActionEvent event) {
		System.out.println("Exiting");
		System.exit(0);;
	}
	
	@FXML 
	public void ResetProgram (ActionEvent event) {
		System.out.println("Reseting");
		myMessage.clear();
		txtRF.clear();
		txtIFBW.clear();
		txtRFBW.clear();
		txtIF.clear();
		txtLO.setText("");
		addvaluesBTN.selectedProperty().setValue(true);
		subvaluesBTN.selectedProperty().setValue(false);		
	}
		
}
