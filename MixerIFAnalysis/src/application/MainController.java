package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class MainController {
	
	@FXML private TextArea myMessage;
	@FXML private TextField txtRF;
	@FXML private TextField txtRFBW;
	@FXML private TextField txtIFBW;
	@FXML private TextField txtIF;
	@FXML private TextField txtFiltBW;
	@FXML private Label txtLO;
	@FXML private TextField myText;
	@FXML private RadioButton addvaluesBTN;
	@FXML private RadioButton subvaluesBTN;
	@FXML private RadioButton verboseBTN;
	@FXML private RadioButton harmonicBTN;
	@FXML private RadioButton byproductBTN;
	@FXML private RadioButton bpfBTN;
	

	@FXML private CheckBox sqrwaveCheckBox;
	
	@FXML private ToggleGroup  radioButtonGroupIF;
	@FXML private ToggleGroup  typeButtonGroup;
	
	private long LocalOsc = 0;
	private long RadioFreq = 0;
	private long currentrf = 0;
	private long IntFreq = 0;
	private long RFBandWid = 0;
	private long IFBandWid = 0;
	private long FiltBandWid = 0;
	private int i = 0;
	private int n = 0;
	private int m = 0;
	private int probcnt = 0;
	
	private long[] tifplus = new long[1000];
	private long[] tifminus = new long[1000];
	private long[] bfoHarms = new long[1000];
	private long[] loHarms = new long[1000];
	
	private boolean  okflag = false;
	private boolean  verboseflag = false;
	private boolean  byprodanalysis = false;

	private static final int MAX_SQUARE_HARMONICS = 4;
	private static final int MAX_SINE_HARMONICS = 3;
	private int maxharmonics;


	@FXML
	public void initialize() {
		myMessage.clear();
		
		txtFiltBW.setDisable(true);
		bpfBTN.setSelected(false);
		
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

		
		txtFiltBW.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (!bpfBTN.isSelected() ) {
						myMessage.appendText("BPF or LPF Button not selected\n");
						txtFiltBW.clear();
						return;
					}
					if (!txtFiltBW.getText().isEmpty() && txtFiltBW.getText().length() > 0) {
						FiltBandWid = Long.valueOf(txtFiltBW.getText());
						if (FiltBandWid <= 0) {
							myMessage.appendText("Invalid Filter Bandwidth\n");
							txtFiltBW.clear();
						} else if (FiltBandWid > 500000) {
							myMessage.appendText("WARNING: Large Filter Bandwidth Entered\n");							
						}
					} 				
				} catch (Exception e) {
					myMessage.appendText("Improper value entered for Filter Bandwith: " + e.getMessage() + "\n");
					txtFiltBW.clear();
				}
			
			}
    	});
		
		
	}
	

	@FXML
	public void bpfBTNPressed() {
		if (bpfBTN.isSelected() ) {
			txtFiltBW.setDisable(false);
		} else {
			txtFiltBW.setDisable(true);
		}			
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

		if ((txtFiltBW.getText().isEmpty() || FiltBandWid <= 0) && bpfBTN.isSelected()) {
			myMessage.appendText("No Filter Bandwidth Defined\n");
			return;
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
		probcnt = 1;

		if (sqrwaveCheckBox.isSelected()) {
			maxharmonics = MAX_SQUARE_HARMONICS*2;
			i=0;
			for (n=1; n<(maxharmonics+1); n+=2) {
				bfoHarms[i] = IntFreq * (long)n;
				loHarms[i++] = LocalOsc * (long)n;
			}
			maxharmonics = MAX_SQUARE_HARMONICS;
		} else {
			maxharmonics = MAX_SINE_HARMONICS;
			for (n=0; n<maxharmonics; n++) {
				bfoHarms[n] = IntFreq * (long)(n+1);
				loHarms[n] = LocalOsc * (long)(n+1);
			}
		}

		myMessage.appendText ("\nStarting Analysis of LO, RF Harmonics mixing to interfere with IF\n\n");
		myMessage.appendText ("Analysis limited to " + maxharmonics + " harmonics\n");
		
		// Check for harmonics folding back into RF or IF
		long highIF = IntFreq + IFBandWid;
		long lowIF = Math.abs(IntFreq - IFBandWid);
		long highRF = RadioFreq + RFBandWid;
		long lowRF = Math.abs(RadioFreq + RFBandWid);
		if (bpfBTN.isSelected()) {
			highRF = RadioFreq + RFBandWid + FiltBandWid;
			lowRF = Math.abs(RadioFreq - RFBandWid - FiltBandWid);
		}
		
		probcnt = 1;
		
		if (  (IntFreq < RadioFreq && highIF >= lowRF) || (IntFreq > RadioFreq && highRF >= lowIF)) {
			if (verboseflag) 
				myMessage.appendText(Integer.toString(probcnt) + ": (TX) " + 
					"IF and RF may overlap. IF Range: " + Long.toString(lowIF) + " to " + Long.toString(highIF) +
					", RF Range: " + Long.toString(lowRF) + " to " + Long.toString(highRF) + "\n");
			okflag = true;
			probcnt++;
		}
		
		// Check for harmonics folding back into RF or IF
		for (n=1; n<maxharmonics; n++) {
			// BFO folds back into IF
			if (bfoHarms[n] >= lowIF && bfoHarms[n] <= highIF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (TX) " + 
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(bfoHarms[n]) + 
						") within IF Bandwidth (" + Long.toString(lowIF) + " to " + Long.toString(highIF) + ")\n");
				okflag = true;
				probcnt++;
			}
			// BFO folds back into RF
			if (bfoHarms[n] >= lowRF && bfoHarms[n] <= highRF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (TX) " + 
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(bfoHarms[n]) + 
						") within RF Bandwidth (" + Long.toString(lowRF) + " to " + Long.toString(highRF) + ")\n");
				okflag = true;
				probcnt++;
			}
			// LO folds back into IF
			if (loHarms[n] >= lowIF && loHarms[n] <= highIF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (TX) " + 
						"LO Harmonic: " + Integer.toString(n) + " (" + Long.toString(loHarms[n]) + 
						") within IF Bandwidth (" + Long.toString(lowIF) + " to " + Long.toString(highIF) + ")\n");
				okflag = true;
				probcnt++;
			}
			// LO folds back into RF
			if (loHarms[n] >= lowRF && loHarms[n] <= highRF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (TX) " + 
						"LO Harmonic: " + Integer.toString(n) + " (" + Long.toString(loHarms[n]) + 
						") within RF Bandwidth (" + Long.toString(lowRF) + " to " + Long.toString(highRF) + ")\n");
				okflag = true;
				probcnt++;
			}
		}
		
		i = 0;
		long plus;
		long minus;
		for (n=1; n<maxharmonics; n++) {
			// Save the mixing product for additional analysis
			
			// nLO +/- IF
			plus = loHarms[n] + IntFreq;
			minus = Math.abs (loHarms[n] - IntFreq);
			tifplus[i] = plus;
			tifminus[i++] = minus;
			
			// nLO+IF folds back into RF
			if (plus >= lowRF && plus <= highRF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
						"LO Harmonic: " + Integer.toString(n) + " (" + Long.toString(loHarms[n]) + 
						") Mixing with IF is within RF Bandwidth (" + Long.toString(lowRF) + " to " + Long.toString(highRF) + ")\n");
				okflag = true;
				probcnt++;
			}
			// nLO-IF folds back into RF
			if (minus >= lowRF && minus <= highRF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
						"LO Harmonic: " + Integer.toString(n) + " (" + Long.toString(loHarms[n]) + 
						") Mixing with IF is within RF Bandwidth (" + Long.toString(lowRF) + " to " + Long.toString(highRF) + ")\n");
				okflag = true;
				probcnt++;
			}
			
			// nBFO +/- IF
			plus = bfoHarms[n] + IntFreq;
			minus = Math.abs (bfoHarms[n] - IntFreq);
			tifplus[i] = plus;
			tifminus[i++] = minus;
			// nBFO+IF folds back into RF
			if (plus >= lowRF && plus <= highRF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(bfoHarms[n]) + 
						") Mixing with IF is within RF Bandwidth (" + Long.toString(lowRF) + " to " + Long.toString(highRF) + ")\n");
				okflag = true;
				probcnt++;
			}
			// nBFO-IF folds back into RF
			if (minus >= lowRF && minus <= highRF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(bfoHarms[n]) + 
						") Mixing with IF is within RF Bandwidth (" + Long.toString(lowRF) + " to " + Long.toString(highRF) + ")\n");
				okflag = true;
				probcnt++;
			}
			
			// nBFO+IF folds back into audio
			if (plus <= IFBandWid) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(bfoHarms[n]) + 
						") Mixing with IF is within Audio (IF) Bandwidth\n");
				okflag = true;
				probcnt++;
			}
			// nBFO-IF folds back into audio
			if (minus <= IFBandWid) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(bfoHarms[n]) + 
						") Mixing with IF is within Audio (IF) Bandwidth\n");
				okflag = true;
				probcnt++;
			}

			// mLO +/- RF
			plus = loHarms[n] + RadioFreq;
			minus = Math.abs (loHarms[n] - RadioFreq);
			tifplus[i] = plus;
			tifminus[i++] = minus;
			// nLO+RF folds back into IF
			if (plus >= lowIF && plus <= highIF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(loHarms[n]) + 
						") Mixing with RF is within IF Bandwidth (" + Long.toString(lowIF) + " to " + Long.toString(highIF) + ")\n");
				okflag = true;
				probcnt++;
			}
			// nLO-RF folds back into IF
			if (minus >= lowIF && minus <= highIF) {
				if (verboseflag) 
					myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
						"BFO Harmonic: " + Integer.toString(n) + " (" + Long.toString(loHarms[n]) + 
						") Mixing with RF is within IF Bandwidth (" + Long.toString(lowIF) + " to " + Long.toString(highIF) + ")\n");
				okflag = true;
				probcnt++;
			}

			for (m=n; m<maxharmonics; m++) {
				// mLO +/- mLO
				plus = loHarms[n] + loHarms[m];
				minus = Math.abs (loHarms[n] - loHarms[m]);
				tifplus[i] = plus;
				tifminus[i++] = minus;
				// nLO+mLO folds back into IF
				if (plus >= lowIF && plus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of LOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(loHarms[m]) + 
							"x" + Long.toString(loHarms[n]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// nLO-mLO folds back into IF
				if (minus >= lowIF && minus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of LOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(loHarms[m]) + 
							"x" + Long.toString(loHarms[n]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// nLO+mLO folds back into RF
				if (plus >= lowRF && plus <= highRF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of LOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(loHarms[m]) + 
							"x" + Long.toString(loHarms[n]) + ") is within RF Bandwidth (" + Long.toString(lowRF) + 
							" to " + Long.toString(highRF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// nLO-mLO folds back into IF
				if (minus >= lowRF && minus <= highRF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of LOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(loHarms[m]) + 
							"x" + Long.toString(loHarms[n]) + ") is within RF Bandwidth (" + Long.toString(lowRF) + 
							" to " + Long.toString(highRF) + ")\n");
					okflag = true;
					probcnt++;
				}

				// mLO +/- mLO
				plus = bfoHarms[n] + bfoHarms[m];
				minus = Math.abs (bfoHarms[n] - bfoHarms[m]);
				tifplus[i] = plus;
				tifminus[i++] = minus;
				// nLO+mLO folds back into IF
				if (plus >= lowIF && plus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of BFOxBFO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
							"x" + Long.toString(bfoHarms[n]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// nLO-mLO folds back into IF
				if (minus >= lowIF && minus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of BFOxBFO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
							"x" + Long.toString(bfoHarms[n]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// nLO+mLO folds back into RF
				if (plus >= lowRF && plus <= highRF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of BFOxBFO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
							"x" + Long.toString(bfoHarms[n]) + ") is within RF Bandwidth (" + Long.toString(lowRF) + 
							" to " + Long.toString(highRF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// nLO-mLO folds back into IF
				if (minus >= lowRF && minus <= highRF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of BFOxBFO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
							"x" + Long.toString(bfoHarms[n]) + ") is within RF Bandwidth (" + Long.toString(lowRF) + 
							" to " + Long.toString(highRF) + ")\n");
					okflag = true;
					probcnt++;
				}
				
			}
			
			
			
			
			for (m=1; m<maxharmonics; m++) {
				// mLO +/- mBFO
				plus = loHarms[n] + bfoHarms[m];
				minus = Math.abs (loHarms[n] - bfoHarms[m]);
				tifplus[i] = plus;
				tifminus[i++] = minus;
				// mLO+mBFO folds back into IF
				if (plus >= lowIF && plus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of BFOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
							"x" + Long.toString(loHarms[n]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// mLO-mBFO folds back into IF
				if (minus >= lowIF && minus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of BFOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
							"x" + Long.toString(loHarms[n]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// mLO+mBFO folds back into RF
				if (plus >= lowRF && plus <= highRF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
							"Mixing of BFOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
							"x" + Long.toString(loHarms[n]) + ") is within RF Bandwidth (" + Long.toString(lowRF) + 
							" to " + Long.toString(highRF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// mLO-mBFO folds back into IF
				if (minus >= lowRF && minus <= highRF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (TX) " +  
								"Mixing of BFOxLO Harmonics: " + Integer.toString(m) + "x" + Integer.toString(n) + " (" + Long.toString(bfoHarms[m]) + 
								"x" + Long.toString(loHarms[n]) + ") is within RF Bandwidth (" + Long.toString(lowRF) + 
								" to " + Long.toString(highRF) + ")\n");
					okflag = true;
					probcnt++;
				}
				
			}
			
		}
		
		int artifacts = i;
		myMessage.appendText (" Artifacts generated and analyzed: " + artifacts + "\n");
		
		if (okflag) myMessage.appendText (Integer.toString(--probcnt) + " Mixing problems found\n");
		else myMessage.appendText ("Done Analyzing LO and RF Harmonics!\n");
		
		
		if (!byprodanalysis) {
			return;
		}
		
		okflag = false;
		probcnt = 1;
		myMessage.appendText ("\nStarting Analysis of Mixing of Byproducts...\n");
		
		for (n=0; n<artifacts; n++) {
			for (m=0; m<artifacts; m++) {
				// nBFO +/- IF
				if (tifplus[n] == tifminus[m]) continue;
				
				plus = tifplus[n] + tifminus[m];
				minus = Math.abs(tifplus[n] - tifminus[m]);
				// Mixing artifacts folds back into IF
				if (plus >= lowIF && plus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
							"Mixing of byproducts: " + Integer.toString(n) + "x" + Integer.toString(m) + " (" + Long.toString(tifplus[n]) + 
							"x" + Long.toString(tifminus[m]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				// Mixing artifacts folds back into IF
				if (minus >= lowIF && minus <= highIF) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
							"Mixing of byproducts: " + Integer.toString(n) + "x" +  Integer.toString(m) + " (" + Long.toString(tifplus[n]) + 
							"x" + Long.toString(tifminus[m]) + ") is within IF Bandwidth (" + Long.toString(lowIF) + 
							" to " + Long.toString(highIF) + ")\n");
					okflag = true;
					probcnt++;
				}
				
				// Mixing artifacts folds back into audio
				if (plus <= IFBandWid) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
							"Mixing of byproducts: " + Integer.toString(n) + "x" +  Integer.toString(m) + " (" + Long.toString(tifplus[n]) + 
							"x" + Long.toString(tifminus[m]) + ") is within Audio (IF) Bandwidth\n");
					okflag = true;
					probcnt++;
				}
				// nBFO-IF folds back into audio
				if (minus <= IFBandWid) {
					if (verboseflag) 
						myMessage.appendText(Integer.toString(probcnt) + ": (RX) " +  
								"Mixing of byproducts: " + Integer.toString(n) + "x" +  Integer.toString(m) + " (" + Long.toString(tifplus[n]) + 
								"x" + Long.toString(tifminus[m]) + ") is within Audio (IF) Bandwidth\n");
					okflag = true;
					probcnt++;
				}
				
			}
		}
		

		if (okflag) myMessage.appendText (Integer.toString(--probcnt) + " Mixing problems found\n");
		else myMessage.appendText ("Done Analyzing Byroducts!\n");		
		
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
		txtFiltBW.clear();
		txtFiltBW.setDisable(true);
		bpfBTN.setSelected(false);
		txtIF.clear();
		txtLO.setText("");
		addvaluesBTN.selectedProperty().setValue(true);
		subvaluesBTN.selectedProperty().setValue(false);		
	}
	
	@FXML
	public void helpBTNPressed() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help Information");
		alert.setHeaderText("Usage Information");
		alert.setContentText("This is the main view for the VE3OOI Mixer Analysis Program");

		TextArea textArea;
		String txt = 
				"This program analyzes inputs and outputs from a mixer to identify frequencies that could fold back into the passband. " +
				"The program assumes that an RF frequency is fed into a mixer along with an LO frequency which generates an IF output frequency " +
				"The IF frequency may either be the sum RF+LO or the difference of RF-LO. " +
				"\r\n" + "\r\n" +
				
				"First, enter the RF frequency along with the bandwidth expected for the frequency.  It is best to enter the middle frequency " +
				" of the BAND  (e.g. 7150000). For bandwidth, it is best to enter the entire bandwidth of the BAND (e.g. 250000) " +
				"\n\n" +

				"Next, enter the IF frequency that is expected to be generated along with the bandwidth of the IF signal. If the IF is feeding " +
				"a crystal filter, then enter the filter centre frequency and the bandwidth of the crystal filter (e.g. 3000) " +
				"\n\n" +

				"You now select how the IF is generated. Select the sum (RF+LO) or difference (RF-LO) button.  The program will automatically " +
				"calculate the LO frequency. " +
				"\n\n" +

				"You can specific if the LO frequency will be a square wave.  If a square wave is used, then the first 4 harmonics of the square wave " + 
				"will be used in the analysis. If unchecked, a Sine wave is assumed to be dirty and first 3 harmonics will be used for the analysis. " +
				"\n\n" +

				"If the radio will have a BPF as part of the Transmit (TX) chain, select the BPF analysis button and then enter the appoximate " + 
				"bandwidth (e.g. 500000, 1500000, etc).  This is used to determine if any fequencies will fold back into the passband of the BPF which is around " + 
				"the specified RF frequency. " +
				"\n\n" +

				"Finally, select the type of analysis needed.  Harmonic Analysis only means that harmonics of the LO and BFO will be mixed " + 
				"together to see if any frequencies fold back to interfere with the RF frequency or the IF frequency during Receive (Rx) or " +
				"Transmit (Tx). " +
				"\n\n" +

				"The Harmonic & Byproduct analysis will also identify if any of the LO/RF/IF mixed artifacts will further mix with each other and interfere " +
				"with the RF or IF.  It is important to carefully observe the frequencies that mix together. In majority of cases there frequencies " +
				"are so high that their power level will be very small and may be noise. That is it may not be strong enought to cause interference. " +
				"each round of mixing in a traditional DBM will experience around 7dB of loss so two or three rounds of mixing will greatly attenuate the artifact " +
				"\n\n" +
				"Software (c) VE3OOI. All rights reserved and provided 'as is', without warranty of any kind";
		
		textArea = new TextArea(txt);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 0);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setContent(expContent);
		alert.showAndWait();
		// enable button after processing done	}
	}

		
}
