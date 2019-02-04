package application;



import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainScreenController {
	
	private static final int FREQSTART = 4912200;			
	private static final int FREQEND = 4918100;			
	private static final int FREQINC = 10;			
	
	@FXML Button singleShotButton;
	@FXML Button exitButton;
	@FXML Button sweepButton;
	@FXML Button iterateButton;
	
	@FXML RadioButton plotZButton;
	@FXML RadioButton plotDBButton;
	@FXML RadioButton singleValueButton;
	
	@FXML MenuItem newMenuOption;
	@FXML MenuItem openMenuOption;
	@FXML MenuItem saveMenuOption;
	@FXML MenuItem exitMenuOption;
	@FXML MenuItem aboutMenuOption;
	
	@FXML TextField c1Text;
	@FXML TextField c3Text;
	@FXML TextField c4Text;
	@FXML TextField c5Text;
	@FXML TextField c2Text;
	
	@FXML TextField y1CText;
	@FXML TextField y2CText;
	@FXML TextField y3CText;
	@FXML TextField y4CText;
	
	@FXML TextField y1LText;
	@FXML TextField y2LText;
	@FXML TextField y3LText;
	@FXML TextField y4LText;
	
	@FXML TextField y1RText;
	@FXML TextField y2RText;
	@FXML TextField y3RText;
	@FXML TextField y4RText;
	
	@FXML TextField y1CSText;
	@FXML TextField y2CSText;
	@FXML TextField y3CSText;
	@FXML TextField y4CSText;
	
	@FXML TextField startFreqText;
	@FXML TextField endFreqText;
	@FXML TextField incFreqText;

	@FXML TextField zinRealText;
	@FXML TextField zinImagText;
	@FXML TextField zoutRealText;
	@FXML TextField zoutImagText;
	@FXML CheckBox terminatedCheckBox;
	@FXML CheckBox printDataCheckBox;
	
	@FXML ImageView schematicImage;
	
	
	@FXML TextArea textArea;
	
	private Complex C1, C2, C3, C4, C5;
	private Complex Y1, Y2, Y3, Y4;
	private Complex ZIN, ZOUT;
	
	private Capacitor c1, c2, c3, c4, c5;
	private Crystal y1, y2, y3, y4;
	
	private Integer fStart, fEnd, fInc, fCentre;
	private double zinCentre, zoutCentre;
	private double dbCentre;
	
	private List<Double> dbList;
	private List<Complex> zinList;
	private List<Complex> zoutList;
	private List<Integer> freqList;
	
	private boolean proceed = false;
	private boolean terminated = false;
	
	private static double femto = 1e-15;
	private static double pico = 1e-12;
	private static double milla = 1e-3;
	
	SceneController sceneController = new SceneController();
	Notifications note = new Notifications();

    @FXML
    public void initialize() {
    	ResetParameters(null);

/*   	
		// Test data set
    	c1Text.setText(Double.toString(39));
    	c3Text.setText(Double.toString(68));
    	c4Text.setText(Double.toString(82));
    	c5Text.setText(Double.toString(68));
    	c2Text.setText(Double.toString(39));

    	y1CText.setText(Double.toString(18.77));
    	y2CText.setText(Double.toString(18.77));
    	y3CText.setText(Double.toString(18.77));
    	y4CText.setText(Double.toString(18.77));
    	
    	y1LText.setText(Double.toString(55.89));
    	y2LText.setText(Double.toString(55.89));
    	y3LText.setText(Double.toString(55.89));
    	y4LText.setText(Double.toString(55.89));
    	
    	y1RText.setText(Double.toString(11.87));
    	y2RText.setText(Double.toString(11.87));
    	y3RText.setText(Double.toString(11.87));
    	y4RText.setText(Double.toString(11.87));
    	
    	y1CSText.setText(Double.toString(0));
    	y2CSText.setText(Double.toString(0));
    	y3CSText.setText(Double.toString(0));
    	y4CSText.setText(Double.toString(0));    	
*/    	

/*

		// Actual Data Set
    	startFreqText.setText(Integer.toString(FREQSTART));
    	endFreqText.setText(Integer.toString(FREQEND));
    	incFreqText.setText(Integer.toString(FREQINC));

    	c1Text.setText(Double.toString(39));
    	c3Text.setText(Double.toString(68));
    	c4Text.setText(Double.toString(82));
    	c5Text.setText(Double.toString(68));
    	c2Text.setText(Double.toString(39));

    	y1CText.setText(Double.toString(18.77));
    	y2CText.setText(Double.toString(18.77));
    	y3CText.setText(Double.toString(18.77));
    	y4CText.setText(Double.toString(18.77));
    	
    	y1LText.setText(Double.toString(55.91));
    	y2LText.setText(Double.toString(55.89));
    	y3LText.setText(Double.toString(55.89));
    	y4LText.setText(Double.toString(55.89));
    	
    	y1RText.setText(Double.toString(16.1));
    	y2RText.setText(Double.toString(11.87));
    	y3RText.setText(Double.toString(11.87));
    	y4RText.setText(Double.toString(11.87));
    	
    	y1CSText.setText(Double.toString(5));
    	y2CSText.setText(Double.toString(5));
    	y3CSText.setText(Double.toString(5));
    	y4CSText.setText(Double.toString(5));  	

*/   	

    	
    	Image img = new Image(getClass().getResourceAsStream("CrystalFilterSchematic.jpg")); 
    	schematicImage.setImage(img);
    	schematicImage.toFront();
    	schematicImage.setVisible(true);
    	
    	setListeners();
    	
     	
    }
    
    @FXML
    public void ResetParameters (ActionEvent event) {

    	singleShotButton.setDisable(true);
    	
		terminatedCheckBox.setSelected(false);
		printDataCheckBox.setSelected(false);
    	
    	
    	// Actual Data Set
    	startFreqText.clear();
    	endFreqText.clear();
    	incFreqText.clear();

    	c1Text.clear();
    	c3Text.clear();
    	c4Text.clear();
    	c5Text.clear();
    	c2Text.clear();

    	y1CText.clear();
    	y2CText.clear();
    	y3CText.clear();
    	y4CText.clear();
    	
    	y1LText.clear();
    	y2LText.clear();
    	y3LText.clear();
    	y4LText.clear();
    	
    	y1RText.clear();
    	y2RText.clear();
    	y3RText.clear();
    	y4RText.clear();
    	
    	y1CSText.clear();
    	y2CSText.clear();
    	y3CSText.clear();
    	y4CSText.clear();   	

    	zinRealText.clear(); 
    	zinImagText.clear(); 
    	zoutRealText.clear(); 
    	zoutImagText.clear(); 
    
    }

  
	@FXML 
	public void RadioButton (ActionEvent event) {
		if (singleValueButton.isSelected()) {
			sweepButton.setDisable(true);
			endFreqText.setDisable(true);
			incFreqText.setDisable(true);
			singleShotButton.setDisable(false);
			startFreqText.setDisable(false);
		} else {
			sweepButton.setDisable(false);
			endFreqText.setDisable(false);
			incFreqText.setDisable(false);
			startFreqText.setDisable(false);
			singleShotButton.setDisable(true);
		}
	}

	
	@FXML 
	public void getTerminateImpedance (ActionEvent event) {
		terminated = false;
		
		if (terminatedCheckBox.isSelected()) {
			if (ZIN.abs() == 0.0) {
				textArea.appendText("Value for Zin cannot be zero\n");
				terminatedCheckBox.setSelected(false);
				return;
			} else if (ZOUT.abs() == 0.0) {
				textArea.appendText("Value for Zout cannot be zero\n");
				terminatedCheckBox.setSelected(false);
				return;
			} else {
				terminated = true;
			}
		} else {
			terminated = false;
		}
		
	}

	private void setListeners () {
		ZOUT = new Complex(0, 0);
		ZIN = new Complex(0, 0);
		
	   		zinRealText.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					try {
//						ZIN.setReal(0);
						if (!zinRealText.getText().isEmpty() && zinRealText.getText().length() > 0) {
							ZIN.setReal(Double.parseDouble(zinRealText.getText().trim()));
							if (ZOUT.abs() == 0.0) {
								textArea.appendText("Value for Zin cannot be zero\n");
								zoutRealText.clear();
								terminatedCheckBox.setSelected(false);
							} 
						} 				
					} catch (Exception e) {
						textArea.appendText("Improper value entered for Zin: " + e.getMessage() + "\n");
						zinRealText.clear();
						terminatedCheckBox.setSelected(false);
					}
				
				}
	    	});
	   		// 
			zoutRealText.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					try {
//						ZOUT.setReal(0);
						if (!zoutRealText.getText().isEmpty() && zoutRealText.getText().length() > 0) {
							ZOUT.setReal(Double.parseDouble(zoutRealText.getText().trim()));
							if (ZOUT.abs() == 0.0) {
								textArea.appendText("Value for Zout cannot be zero\n");
								zoutRealText.clear();
								terminatedCheckBox.setSelected(false);
							} 
						} 				
					} catch (Exception e) {
						textArea.appendText("Improper value entered Zout: " + e.getMessage() +"\n");
						zoutRealText.clear();
						terminatedCheckBox.setSelected(false);
					}
				
				}
	    	});
			// 
			zinImagText.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					try {
//						ZIN.setImaginary(0);
						if (!zinImagText.getText().isEmpty() && zinImagText.getText().length() > 0) {
							ZIN.setImaginary(Double.parseDouble(zinImagText.getText().trim()));
							if (ZIN.abs() == 0.0) {
								textArea.appendText("Value for Zin cannot be zero\n");
								zinImagText.clear();
								terminatedCheckBox.setSelected(false);
							} 
						} 				
					} catch (Exception e) {
						textArea.appendText("Improper value entered for Zin: " + e.getMessage() + "\n");
						zinImagText.clear();
						terminatedCheckBox.setSelected(false);
					}
				
				}
	    	});


	   		// 	   	
			zoutImagText.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					try {
//						ZOUT.setImaginary(0);
						if (!zoutImagText.getText().isEmpty() && zoutImagText.getText().length() > 0) {
							ZOUT.setImaginary(Double.parseDouble(zoutImagText.getText().trim()));
						} 				
						if (ZOUT.abs() == 0.0) {
							textArea.appendText("Value for Zout cannot be zero\n");
							zoutImagText.clear();
							terminatedCheckBox.setSelected(false);
						} 
					} catch (Exception e) {
						textArea.appendText("Improper value entered for Zout: " + e.getMessage() + "\n");
						zoutImagText.clear();
						terminatedCheckBox.setSelected(false);
					}
				
				}
	    	});
			
	   	
	}
	
	@FXML 
	public void GenerateSweep (ActionEvent event) {

		Stage stage;
		Stage parentStage;

		
		proceed = true;
		textArea.clear();
		
	    //populating the series with data. This also checks the data
		if (!loadCrystals()) {
			note.alert ("Inproper Values", "One or more of the crystal data values are incorrect");
			return;
		}
		if (!loadCapacitors()) {
			note.alert ("Inproper Data Values", "One or more of the capacitor data values are incorrect");
			return;
		}
		if (!loadFrequencies()) {
			note.alert ("Inproper Data Values", "One or more of the frequency data values are incorrect");
			return;
		}
		
		
		
		stage = new Stage();
		parentStage = sceneController.getRootStage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(parentStage);			// Default JavaFx stage is the parent
		
        stage.setTitle("Filter Performance Plot");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Frequency (Hz)");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(Integer.parseInt(startFreqText.getText().trim()));
        xAxis.setUpperBound(Integer.parseInt(endFreqText.getText().trim()));
        xAxis.setTickUnit( (Integer.parseInt(endFreqText.getText().trim().trim()) - Integer.parseInt(startFreqText.getText().trim().trim())) 
        						/ Integer.parseInt(incFreqText.getText().trim()));
        
        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        //defining a series
        XYChart.Series series = new XYChart.Series();

        if (plotZButton.isSelected()) {
            yAxis.setLabel("Impedance (Ohms)");
            lineChart.setTitle("Filter Impedance");
            series.setName("Impedance Plot");       	
        } else {
            yAxis.setLabel("Gain (dB)");
            lineChart.setTitle("Filter Gain");
            series.setName("Gain Plot");       	        	
        }
                 
 		
		Integer freq = 0;
        double dbTotal = 0.0;
        double dbAverage = 0.0;
        
    	dbList = new ArrayList<Double>();
    	zinList = new ArrayList<Complex>();
    	zoutList = new ArrayList<Complex>();
    	freqList = new ArrayList<Integer>();
		
		for (freq=fStart; freq<fEnd; freq+=fInc) {
			
/*
//=========================================
			C1 = new Complex (0,39);
			C2 = new Complex (0,39);
			C3 = new Complex (0,68);
			C4 = new Complex (0,82);
			C5 = new Complex (0,68);
			
			Y1 = new Complex (0,56);
			Y2 = new Complex (0,56);
			Y3 = new Complex (0,56);
			Y4 = new Complex (0,56);

//======================================			
			C1 = c1.getImpedance(freq);
			C2 = c2.getImpedance(freq);
			C3 = c3.getImpedance(freq);
			C4 = c4.getImpedance(freq);
			C5 = c5.getImpedance(freq);
			
			Y1 = y1.getImpedance(freq);
			Y2 = y2.getImpedance(freq);
			Y3 = y3.getImpedance(freq);
			Y4 = y4.getImpedance(freq);
			
//======================================			
			c1.setC(39*pico); 
			c2.setC(39*pico); 
			c3.setC(39*pico); 
			c4.setC(39*pico); 
			c5.setC(39*pico); 
			c5.setC(39*pico); 
			
			C1 = c1.getImpedance(freq);
			C2 = c2.getImpedance(freq);
			C3 = c3.getImpedance(freq);
			C4 = c4.getImpedance(freq);
			C5 = c5.getImpedance(freq);
			
			Y1 = C1.get();
			Y2 = C2.get();
			Y3 = C3.get();
			Y4 = C4.get();
			
*/			

			calculateReactance (freq);
			
			Complex Zout = new Complex(0, 0);
			Complex Zin = new Complex(0, 0);
			Zin = calculateZin();
			Zout = calculateZout();

			
			CircuitParameters cp = new CircuitParameters();			
			cp = calculateCircuitParameters ();
			
			double db = 20.0*Math.log10(cp.getGain().abs());
			String dbs = String.format("%.3f", db );
			String zins = String.format("%.3f", Zin.abs() );
			String zouts = String.format("%.3f", Zout.abs() );
			if (printDataCheckBox.isSelected()) {
				textArea.appendText("Freq: " + freq + "\n");
				textArea.appendText("\tZin: " + zins + " Zout: " + zouts + "\n");
				textArea.appendText("\tdB: "  + cp.getGain().showValue() + " mag: " + dbs + "\n");
				textArea.appendText("\tVout: "  + cp.getVoltage("Vout").showValue() + " (" + cp.getVoltage("Vout").abs() + ")\n");
				textArea.appendText("\tComplex Zin: " +  Zin.showValue() + " Zout: " +  Zin.showValue() + "\n");
				
			}
	        if (plotZButton.isSelected()) {
	        	series.getData().add(new XYChart.Data(freq, Zout.abs()));
	        } else {
	        	series.getData().add(new XYChart.Data(freq, db));	        	
	        }	
	        
			dbList.add(db);
			zinList.add(Zin);
			zoutList.add(Zout);
			freqList.add(freq);
			dbTotal += db;
		
		}
		
		dbAverage = dbTotal / ((fEnd-fStart)/fInc);
		
		// Get centre frequency
		Integer lowFreq = 0;
		Integer highFreq = 0;
		boolean rising = true;
		boolean done = false;
		double minDB = dbList.get( (int) Math.round( ((fEnd-fStart)/fInc/2) ) );
		double minZ = zoutList.get( (int) Math.round( ((fEnd-fStart)/fInc/2) ) ).abs();
		for (Integer i=0; i<dbList.size()-2; i++) {
			
			if (!done && rising && dbList.get(i) <= dbAverage && dbList.get(i+1) > dbAverage && dbList.get(i+2) > dbAverage) {
				rising = false;
				lowFreq = freqList.get(i);
			}
			
			if (!done && !rising && dbList.get(i) >= dbAverage && dbList.get(i+2) < dbAverage && dbList.get(i+2) < dbAverage) {
				highFreq = freqList.get(i);
				done = true;
			}
			
			if (dbList.get(i) < minDB) minDB = dbList.get(i);
			if (zoutList.get(i).abs() < minZ) minZ = zoutList.get(i).abs();
		}
		fCentre = lowFreq + (highFreq-lowFreq)/2;

		done = false;
		Integer el = 0;
		// Get Zin/Zout and db at centre frequency
		for (Integer i=0; i<freqList.size()-1; i++) {
			if (!done && fCentre >= freqList.get(i) && fCentre < freqList.get(i+1)) {
				done = true;
				zinCentre = zinList.get(i).abs();
				zoutCentre = zoutList.get(i).abs();
				dbCentre = dbList.get(i);
				el = i;
			}
		}
		String freqs = String.format("%d", fCentre );;
		String dbs = String.format("%.1f", dbCentre );
		String zins = String.format("%.1f", zinCentre );
		String zouts = String.format("%.1f", zoutCentre );
		textArea.appendText("\n\nCentre Freq: "  + freqs + " dB: " + dbs + " Zin: " + zins + " Zout: " + zouts + "\n");
		textArea.appendText("Complex Zin: " + zinList.get(el).showValue() + " Zout: " + zoutList.get(el).showValue() + "\n");
		
		lineChart.setCreateSymbols(false);
        Scene plotScene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series); 
        
        stage.setScene(plotScene);
        stage.show();		
        
	}

	
	
	@FXML 
	public void Iterate (ActionEvent event) {

		textArea.clear();
		
	    //populating the series with data. This also checks the data
		if (!loadCapacitors()) {
			note.alert ("Inproper Data Values", "One or more of the capacitor data values are incorrect");
			return;
		}
		if (!loadCrystals()) {
			note.alert ("Inproper Values", "One or more of the crystal data values are incorrect");
			return;
		}
		if (!loadFrequencies()) {
			note.alert ("Inproper Data Values", "One or more of the frequency data values are incorrect");
			return;
		}
		
		String freqs = "";
		String dbs = "";
		String zins = "";
		String zouts = "";
		
		double oldZinCentre = 0;
		double oldZoutCentre = 0;
		
    	terminated = false;
    	boolean again = true;
    	int match = 0;
    	int endless = 0;
    	while (again) {
    		oldZinCentre = zinCentre;
    		oldZoutCentre = zoutCentre;

        	dbList = new ArrayList<Double>();
        	zinList = new ArrayList<Complex>();
        	zoutList = new ArrayList<Complex>();
        	freqList = new ArrayList<Integer>();
    		
    		Integer freq = 0;
            double dbTotal = 0.0;
            double dbAverage = 0.0;
               	
    		zinCentre = 0;
    		zoutCentre = 0;
    		dbCentre = 0;
    		fCentre = 0;
     		
    		for (freq=fStart; freq<fEnd; freq+=fInc) {
    			calculateReactance (freq);
    			
    			Complex Zout = new Complex(0, 0);
    			Complex Zin = new Complex(0, 0);
    			Zin = calculateZin();
    			Zout = calculateZout();

    			CircuitParameters cp = new CircuitParameters();			
    			cp = calculateCircuitParameters ();
    			
    			double db = 20.0*Math.log10(cp.getGain().abs());
    	        
    			dbList.add(db);
    			zinList.add(Zin);
    			zoutList.add(Zout);
    			freqList.add(freq);
    			dbTotal += db;
    		
    		}
    		
    		dbAverage = dbTotal / ((fEnd-fStart)/fInc);
    		
    		// Get centre frequency
    		Integer lowFreq = 0;
    		Integer highFreq = 0;
    		boolean rising = true;
    		boolean done = false;
    		double minDB = dbList.get( (int) Math.round( ((fEnd-fStart)/fInc/2) ) );
    		double minZ = zoutList.get( (int) Math.round( ((fEnd-fStart)/fInc/2) ) ).abs();
    		for (Integer i=0; i<dbList.size()-2; i++) {
    			
    			if (!done && rising && dbList.get(i) <= dbAverage && dbList.get(i+1) > dbAverage && dbList.get(i+2) > dbAverage) {
    				rising = false;
    				lowFreq = freqList.get(i);
    			}
    			
    			if (!done && !rising && dbList.get(i) >= dbAverage && dbList.get(i+2) < dbAverage && dbList.get(i+2) < dbAverage) {
    				highFreq = freqList.get(i);
    				done = true;
    			}
    			
    			if (dbList.get(i) < minDB) minDB = dbList.get(i);
    			if (zoutList.get(i).abs() < minZ) minZ = zoutList.get(i).abs();
    		}
    		fCentre = lowFreq + (highFreq-lowFreq)/2;

    		done = false;
    		Integer el = 0;
    		// Get Zin/Zout and db at centre frequency
    		for (Integer i=0; i<freqList.size()-1; i++) {
    			if (!done && fCentre >= freqList.get(i) && fCentre < freqList.get(i+1)) {
    				done = true;
    				zinCentre = zinList.get(i).abs();
    				zoutCentre = zoutList.get(i).abs();
    				dbCentre = dbList.get(i);
    				el = i;
    			}
    		}
    		freqs = String.format("%d", fCentre );;
    		dbs = String.format("%.1f", dbCentre );
    		zins = String.format("%.1f", zinList.get(el).abs() );
    		zouts = String.format("%.1f", zoutList.get(el).abs() );
    		textArea.appendText("\nCentre Freq: "  + freqs + " dB: " + dbs + " zin: " + zins + " zout: " + zouts + "\n");
    		textArea.appendText("Complex zin: " + zinList.get(el).showValue() + " zout: " + zoutList.get(el).showValue() + "\n");
    		
       		ZOUT.setImaginary(zoutList.get(el).getImaginary());
       		ZOUT.setReal(zoutList.get(el).getReal());
       		ZIN.setImaginary(zinList.get(el).getImaginary());
       		ZIN.setReal(zinList.get(el).getReal());

       		zinRealText.setText(Double.toString(ZIN.getReal()));
       		zinImagText.setText(Double.toString(ZIN.getImaginary()));
       		zoutRealText.setText(Double.toString(ZOUT.getReal()));
       		zoutImagText.setText(Double.toString(ZOUT.getImaginary()));
    		terminated = true;
    		terminatedCheckBox.setSelected(true);
    		
    		zins = String.format("%.1f", ZIN.abs() );
    		zouts = String.format("%.1f", ZOUT.abs() );
    		textArea.appendText("\tRe-Iterate with ZIN: " + ZIN.showValue() + " ZOUT: " + ZOUT.showValue() + "\n");
    		textArea.appendText("\tZIN: " + zins + " ZOUT: " + zouts + "\n");

    	
    		if (Math.abs(oldZinCentre-zinCentre) < 50 && Math.abs(oldZoutCentre-zoutCentre) < 50) {
        		if (match++ > 2) {
        			again = false;
            		zins = String.format("%.1f", ZIN.abs() );
            		zouts = String.format("%.1f", ZOUT.abs() );
        	    	textArea.appendText("\nDone!!\nCentre Freq: "  + freqs + " dB: " + dbs + " Zin: " + zins + " ZOUT: " + zouts + "\n");
        	    	textArea.appendText("Complex Zin: " + ZIN.showValue() + " Zout: " + ZOUT.showValue() + "\n");
        		}
    		}
    		if (endless++ > 500) {
    			again = false;
    	    	textArea.appendText("\n\nDoes not converge" + "\n");
    		}
    	
    	} 	
       
	}
	
	
	
	
	
	
	private CircuitParameters calculateCircuitParameters () {
		CircuitParameters cp = new CircuitParameters();

		Complex z1 = new Complex(0, 0);
		Complex z2 = new Complex(0, 0);
		Complex z3 = new Complex(0, 0);
		Complex z4 = new Complex(0, 0);
		Complex z5 = new Complex(0, 0);
		Complex total = new Complex(0, 0);
		
		Complex Vj1 = new Complex(0, 0);
		Complex Vj2 = new Complex(0, 0);
		Complex Vj3 = new Complex(0, 0);
		
		Complex Vin = new Complex(1, 0);
		Complex Vout = new Complex(0, 0);	
		Complex gain = new Complex(0, 0);
		Complex i1 = new Complex(0, 0);
		Complex i2 = new Complex(0, 0);
		Complex i3 = new Complex(0, 0);
		Complex i4 = new Complex(0, 0);
		Complex i5 = new Complex(0, 0);
		Complex i6 = new Complex(0, 0);
		Complex i7 = new Complex(0, 0);
		
		if (!terminated) {
			z1 = Complex.addComplex(C1, Y1);  
			z2 = Complex.addComplex(Y3, C5); 	// Y3, C5
			z3 = Complex.parallel(C4, z2);   	// C4, Y3, C5
			z3.add(Y2);		
			
			z4 = Complex.parallel(C3, z3);
			total = Complex.addComplex(z1, z4);
			i1 = Complex.divComplex( Vin, total);
			Vj1 = Complex.subComplex( Vin, Complex.multComplex(i1, z1) );
			i2 = Complex.divComplex(Vj1, C3);
			i3 = Complex.subComplex(i1, i2);	
			
			Vj2 = Complex.subComplex( Vj1, Complex.multComplex(i3, Y2) );
			i4 = Complex.divComplex(Vj2, C4);
			i5 = Complex.subComplex(i3, i4);
			Vj3 = Complex.multComplex(i5, C5);
			Vout = Vj3.get();
			
		} else {
			z1 = Complex.addComplex(C2, Y4);  
			z1.add(ZOUT);  
			
			z2 = Complex.parallel(z1, C5); 		// ZOUT, C2, Y4, C5
			z2.add(Y3);
			
			z3 = Complex.parallel(C4, z2);   	// C4, Y3 
			z3.add(Y2);		
			
			z4 = Complex.parallel(C3, z3);
			z4.add(Y1);
			z4.add(C1);
			z4.add(ZIN);
			
			z5 = Complex.addComplex(ZIN, C1);
			z5.add(Y1);
			
			i1 = Complex.divComplex( Vin, z4);
			Vj1 = Complex.subComplex( Vin, Complex.multComplex(i1, z5) );
			
			i2 = Complex.divComplex(Vj1, C3);		// Current C3
			i3 = Complex.subComplex(i1, i2);		// Current Y2
			
			Vj2 = Complex.subComplex( Vj1, Complex.multComplex(i3, Y2) );
			
			i4 = Complex.divComplex(Vj2, C4);  		// Current C4
			i5 = Complex.subComplex(i3, i4);		// Current Y3
			
			Vj3 = Complex.subComplex( Vj2, Complex.multComplex(i5, Y3) );
			
			i6 = Complex.divComplex(Vj3, C5);
			i7 = Complex.subComplex(i5, i6);
		
			z1 = Complex.addComplex(C2, Y4);  
			Vout = Complex.subComplex( Vj3, Complex.multComplex(i7, z1) );
			
		}

		gain = Complex.divComplex(Vout, Vin);

/*
textArea.appendText("\tz1: " +  z1.showValueLong() + "\n");
textArea.appendText("\tz2: " +  z2.showValueLong() + "\n");
textArea.appendText("\tz3: " +  z3.showValueLong() + "\n");
textArea.appendText("\tz4: " +  z4.showValueLong() + "\n");
textArea.appendText("\ttotal: " +  total.showValueLong() + "\n");
textArea.appendText("\ti1: " +  i1.showValueLong() + "\n");
textArea.appendText("\ti2: " +  i2.showValueLong() + "\n");
textArea.appendText("\ti3: " +  i3.showValueLong() + "\n");
textArea.appendText("\ti4: " +  i4.showValueLong() + "\n");
textArea.appendText("\ti5: " +  i5.showValueLong() + "\n");
textArea.appendText("\ti6: " +  i5.showValueLong() + "\n");
textArea.appendText("\ti7: " +  i5.showValueLong() + "\n");
textArea.appendText("\tVj1: " +  Vj1.showValueLong() + "\n");
textArea.appendText("\tVj2: " +  Vj2.showValueLong() + "\n");
textArea.appendText("\tVj3: " +  Vj3.showValueLong() + "\n");
textArea.appendText("\tVout: " +  Vout.showValueLong() + "\n");
*/			

		cp.setGain(gain);
		cp.setCurrent("i1", i1);
		cp.setCurrent("i2", i2);
		cp.setCurrent("i3", i3);
		cp.setCurrent("i4", i4);
		cp.setCurrent("i5", i5);
		
		cp.setJunctionVoltages("Vj1", Vj1);
		cp.setJunctionVoltages("Vj2", Vj2);
		cp.setJunctionVoltages("Vj3", Vj3);
		
		cp.setVoltage("Vin", Vin);
		cp.setVoltage("Vout", Vout);	
				
		return cp;
	}
	
	
	
	
	private void calculateReactance (double freq) {
		
		C1 = new Complex(0, 0);
		C2 = new Complex(0, 0);
		C3 = new Complex(0, 0);
		C4 = new Complex(0, 0);
		C5 = new Complex(0, 0);
		Y1 = new Complex(0, 0);
		Y2 = new Complex(0, 0);
		Y3 = new Complex(0, 0);
		Y4 = new Complex(0, 0);
		
		C1 = c1.getImpedance(freq);
		C2 = c2.getImpedance(freq);
		C3 = c3.getImpedance(freq);
		C4 = c4.getImpedance(freq);
		C5 = c5.getImpedance(freq);
		
		Y1 = y1.getImpedance(freq);
		Y2 = y2.getImpedance(freq);
		Y3 = y3.getImpedance(freq);
		Y4 = y4.getImpedance(freq);	

/*
textArea.appendText("Freq: " + freq + " C1z: " + C1.showValue() + "\n");
textArea.appendText("Freq: " + freq + " C2z: " + C2.showValue() + "\n");
textArea.appendText("Freq: " + freq + " C3z: " + C3.showValue() + "\n");
textArea.appendText("Freq: " + freq + " C4z: " + C4.showValue() + "\n");
textArea.appendText("Freq: " + freq + " C5z: " + C5.showValue() + "\n");

textArea.appendText("Freq: " + freq + " Y1z: " + Y1.showValue() + "\n");
textArea.appendText("Freq: " + freq + " Y2z: " + Y2.showValue() + "\n");
textArea.appendText("Freq: " + freq + " Y3z: " + Y3.showValue() + "\n");
textArea.appendText("Freq: " + freq + " Y4z: " + Y4.showValue() + "\n");
*/			

	}
	
	private Complex calculateZin () {
		
		Complex Z1 = new Complex(0, 0);
		Complex Z2 = new Complex(0, 0);
		Complex Z3 = new Complex(0, 0);
		Complex Z4 = new Complex(0, 0);
		Complex Zin = new Complex(0, 0);

//Zin
		Z1 = C2.get();
		Z1.add(Y4);
		if (terminated) {
			Z1.add(ZOUT);
		}
		
		Z2 = Complex.parallel(Z1, C5);
		Z2.add(Y3);
		
		Z3 = Complex.parallel(Z2, C4);
		Z3.add(Y2);
		
		Z4 = Complex.parallel(Z3, C3);
		Z4.add(Y1);
		Z4.add(C1);
		
		Zin = Z4.get();
/*
textArea.appendText("Zin:\n");
textArea.appendText("\tZ1 (C2+Y4): " +  Z1.showValueLong() + "\n");
textArea.appendText("\tZ1 (Z1//C5): " +  Z2.showValueLong() + "\n");
textArea.appendText("\tZ2 (Z1//C5+Y3): " +  Z2.showValueLong() + "\n");
textArea.appendText("\tZ3 (Z2//C4): " +  Z3.showValueLong() + "\n");
textArea.appendText("\tZ3 (Z2//C4+Y2): " +  Z3.showValueLong() + "\n");
textArea.appendText("\tZ4 (Z3//C3): " +  Z4.showValueLong() + "\n");
textArea.appendText("\tZ4 (Z3//C3+Y1): " +  Z4.showValueLong() + "\n");
textArea.appendText("\tZ4 (Z3//C3+Y1+C1): " +  Z4.showValueLong() + "\n");
textArea.appendText("\tZin: " +  Zin.showValueLong() + "\n");
*/

		return Zin;
		
	}

	
	private Complex calculateZout () {
		
		Complex Z1 = new Complex(0, 0);
		Complex Z2 = new Complex(0, 0);
		Complex Z3 = new Complex(0, 0);
		Complex Z4 = new Complex(0, 0);
		Complex Zout = new Complex(0, 0);
//Zout			
		Z1 = C1.get();
		Z1.add(Y1);
		if (terminated) {
			Z1.add(ZIN);
		}
		
		Z2 = Complex.parallel(Z1, C3);
		Z2.add(Y2);
		
		Z3 = Complex.parallel(Z2, C4);
		Z3.add(Y3);
		
		Z4 = Complex.parallel(Z3, C5);
		Z4.add(Y4);
		Z4.add(C2);
		
		Zout = Z4.get();

/*
textArea.appendText("Freq: " + freq + "\n");
textArea.appendText("\tZ1 (C1+Y1): " +  Z1.showValueLong() + "\n");
textArea.appendText("\tZ2 (Z1//C3+Y2): " +  Z2.showValueLong() + "\n");
textArea.appendText("\tZ3 (Z2//C4+Y3): " +  Z3.showValueLong() + "\n");
textArea.appendText("\tZ4 (Z3//C5+Y4+C2): " +  Z4.showValueLong() + "\n");
textArea.appendText("\tZout: " +  Zout.showValueLong() + "\n");
*/

		return Zout;
	}
	
	@FXML 
	public void SingleShot (ActionEvent event) {
		proceed = true;
		
		textArea.clear();
		
		if (!loadCrystals()) {
			note.alert ("Inproper Values", "One or more of the crystal data values are incorrect");
			return;
		}
		if (!loadCapacitors()) {
			note.alert ("Inproper Data Values", "One or more of the capacitor data values are incorrect");
			return;
		}
		try {
			if (!startFreqText.getText().isEmpty() && startFreqText.getText().length() > 0) {
				fStart = Integer.parseInt(startFreqText.getText().trim());
			} else {
				textArea.appendText("Value of start frequency is incorrect. Value must be in less than end frequency and in Hz\n");
				note.alert ("Inproper Data Values", "One or more of the frequency data values are incorrect");
				return;
			} 
		} catch (Exception e) {
			textArea.appendText("Improper frequency data entered: " + e.getMessage() + "\n");
			return;			
		}
		
		double freq = fStart;

		textArea.appendText("Freq: " + freq + "\n");	

		calculateReactance (freq);
		
		Complex Zout = new Complex(0, 0);
		Complex Zin = new Complex(0, 0);
		
		Zin = calculateZin();
		Zout = calculateZout();
		
		CircuitParameters cp = new CircuitParameters();			
		cp = calculateCircuitParameters ();
		
		double db = 20.0*Math.log10(cp.getGain().abs());
		String dbs = String.format("%.3f", db );
		String zins = String.format("%.3f", Zin.abs() );
		String zouts = String.format("%.3f", Zout.abs() );

		textArea.appendText("\tVj1: " + cp.getJunctionVoltages("Vj1").showValue() + "  Vj2: " + cp.getJunctionVoltages("Vj2").showValue() + "  Vj3: " + 
								cp.getJunctionVoltages("Vj3").showValue()+ "\n");
		textArea.appendText("\tVout: "  + cp.getVoltage("Vout").showValue() + "\n");
		textArea.appendText("\tZin: " +  Zin.showValue() + "  (" + zins + ")\n");
		textArea.appendText("\tZout: " +  Zout.showValue() + "  (" + zouts + ")\n");
		textArea.appendText("\tdB: "  + cp.getGain().showValue() + "  (" + dbs + ")\n");

		if (printDataCheckBox.isSelected()) {
			textArea.appendText("\n\nImpedances:\n");
			textArea.appendText("\tC1z: " + C1.showValueLong() + "\n");
			textArea.appendText("\tC2z: " + C2.showValueLong() + "\n");
			textArea.appendText("\tC3z: " + C3.showValueLong() + "\n");
			textArea.appendText("\tC4z: " + C4.showValueLong() + "\n");
			textArea.appendText("\tC5z: " + C5.showValueLong() + "\n");
			textArea.appendText("\tY1z: " + Y1.showValueLong() + "\n");
			textArea.appendText("\tY2z: " + Y2.showValueLong() + "\n");
			textArea.appendText("\tY3z: " + Y3.showValueLong() + "\n");
			textArea.appendText("\tY4z: " + Y4.showValueLong() + "\n");
		}
		
	}	

	
	private boolean loadFrequencies () {
		boolean status = true;
		
		fStart = 0;
		fEnd = 0;
		fInc = 0;

		try {
			if (!startFreqText.getText().isEmpty() && startFreqText.getText().length() > 0) {
				fStart = Integer.parseInt(startFreqText.getText().trim());
			} else {
				status = false;
				textArea.appendText("Value of start frequency is incorrect. Value must be in less than end frequency and in Hz\n");
			} 
			
			if (!endFreqText.getText().isEmpty() && endFreqText.getText().length() > 0) {
				fEnd = Integer.parseInt(endFreqText.getText().trim());
			} else {
				status = false;
				textArea.appendText("Value of end frequency is incorrect. Value must be in greater than start frequency and in Hz\n");
			} 
			
			if (!incFreqText.getText().isEmpty() && incFreqText.getText().length() > 0) {
				fInc = Integer.parseInt(incFreqText.getText().trim());
			} else {
				status = false;
				textArea.appendText("Value of frequency increment is incorrect. Value must be positive and less than start-end frequency and in Hz\n");
			} 
			
		} catch (Exception e) {
			textArea.appendText("Improper value entered for frequecy data: " + e.getMessage() + "\n");
			return false;
		}
		
		if (!status) return status;
		
		if (fInc <= 0) {
			status = false;
			textArea.appendText("Value of frequency increment is incorrect. Value must be positive and less than start-end frequency and in Hz\n");			
		}
		
		if (fEnd <= 0 || fEnd <= fStart) {
			status = false;
			textArea.appendText("Value of end frequency is incorrect. Value must be in greater than start frequency and in Hz\n");
		}
		
		if (fStart <= 0 || fStart >= fEnd) {
			status = false;
			textArea.appendText("Value of start frequency is incorrect. Value must be in less than end frequency and in Hz\n");
		}
				
		return status;
	}
	
	
	
	private boolean loadCrystals () {
		boolean status = true;

		y1 = new Crystal();
		y2 = new Crystal();
		y3 = new Crystal();
		y4 = new Crystal();
		
		try {
			if (!y1CText.getText().isEmpty() && y1CText.getText().length() > 0) {
				y1.setCm(Double.parseDouble(y1CText.getText().trim())*femto);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y1 motional capacitor is incorrect. Value must be in femto Farads\n");
			} 
			
			if (!y2CText.getText().isEmpty() && y2CText.getText().length() > 0) {
				y2.setCm(Double.parseDouble(y2CText.getText().trim())*femto);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y2 motional capacitor is incorrect. Value must be in femto Farads\n");
			} 

			if (!y3CText.getText().isEmpty() && y3CText.getText().length() > 0) {
				y3.setCm(Double.parseDouble(y3CText.getText().trim())*femto);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y3 motional capacitor is incorrect. Value must be in femto Farads\n");
			} 

			if (!y4CText.getText().isEmpty() && y4CText.getText().length() > 0) {
				y4.setCm(Double.parseDouble(y4CText.getText().trim())*femto);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y4 motional capacitor is incorrect. Value must be in femto Farads\n");
			} 

			if (!y1LText.getText().isEmpty() && y1LText.getText().length() > 0) {
				y1.setLm(Double.parseDouble(y1LText.getText().trim())*milla);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y1 motional inductor is incorrect. Value must be in milla Henries\n");
			} 
			
			if (!y2LText.getText().isEmpty() && y2LText.getText().length() > 0) {
				y2.setLm(Double.parseDouble(y2LText.getText().trim())*milla);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y2 motional inductor is incorrect. Value must be in milla Henries\n");
			} 

			if (!y3LText.getText().isEmpty() && y3LText.getText().length() > 0) {
				y3.setLm(Double.parseDouble(y3LText.getText().trim())*milla);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y3 motional inductor is incorrect. Value must be in milla Henries\n");
			} 

			if (!y4LText.getText().isEmpty() && y4LText.getText().length() > 0) {
				y4.setLm(Double.parseDouble(y4LText.getText().trim())*milla);
			} else {
				status = false;
				textArea.appendText("Value of crystal Y4 motional inductor is incorrect. Value must be in milla Henries\n");
			} 
			
			if (!y1RText.getText().isEmpty() && y1RText.getText().length() > 0) {
				y1.setRm(Double.parseDouble(y1RText.getText().trim()));
			} else {
				status = false;
				textArea.appendText("Value of crystal Y1 motional resistor is incorrect. Value must be in ohms\n");
			} 
			
			if (!y2RText.getText().isEmpty() && y2RText.getText().length() > 0) {
				y2.setRm(Double.parseDouble(y2RText.getText().trim()));
			} else {
				status = false;
				textArea.appendText("Value of crystal Y2 motional resistor is incorrect. Value must be in ohms\n");
			} 
			
			if (!y3RText.getText().isEmpty() && y3RText.getText().length() > 0) {
				y3.setRm(Double.parseDouble(y3RText.getText().trim()));
			} else {
				status = false;
				textArea.appendText("Value of crystal Y3 motional resistor is incorrect. Value must be in ohms\n");
			} 
			
			if (!y4RText.getText().isEmpty() && y4RText.getText().length() > 0) {
				y4.setRm(Double.parseDouble(y4RText.getText().trim()));
			} else {
				status = false;
				textArea.appendText("Value of crystal Y4 motional resistor is incorrect. Value must be in ohms\n");
			} 
			
			if (!y1CSText.getText().isEmpty() && y1CSText.getText().length() > 0) {
				y1.setCc(Double.parseDouble(y1CSText.getText().trim())*pico);
			} else {
				y1.setCc(0);
				textArea.appendText("Value of crystal Y1 motional case capacitance is missing or invalid. Value must be in pico Farads.  Assuming its 0\n");
			} 
			
			if (!y2CSText.getText().isEmpty() && y2CSText.getText().length() > 0) {
				y2.setCc(Double.parseDouble(y2CSText.getText().trim())*pico);
			} else {
				y2.setCc(0);
				textArea.appendText("Value of crystal Y1 motional case capacitance is missing or invalid. Value must be in pico Farads.  Assuming its 0\n");
			} 
			
			if (!y3CSText.getText().isEmpty() && y3CSText.getText().length() > 0) {
				y3.setCc(Double.parseDouble(y3CSText.getText().trim())*pico);
			} else {
				y3.setCc(0);
				textArea.appendText("Value of crystal Y1 motional case capacitance is missing or invalid. Value must be in pico Farads.  Assuming its 0\n");
			} 
			
			if (!y4CSText.getText().isEmpty() && y4CSText.getText().length() > 0) {
				y4.setCc(Double.parseDouble(y4CSText.getText().trim())*pico);
			} else {
				y4.setCc(0);
				textArea.appendText("Value of crystal Y1 motional case capacitance is missing or invalid. Value must be in pico Farads.  Assuming its 0\n");
			} 
			
		} catch (Exception e) {
			textArea.appendText("Improper crystal data entered: " + e.getMessage() + "\n");
			return false;
		}
		
		
		return status;
			
	}	
	
	private boolean loadCapacitors () {
		// Numbers
		boolean status = true;
		c1 = new Capacitor();
		c2 = new Capacitor();
		c3 = new Capacitor();
		c4 = new Capacitor();
		c5 = new Capacitor();
		
		try {
			if (!c1Text.getText().isEmpty() && c1Text.getText().length() > 0) {
				c1.setC(Double.parseDouble(c1Text.getText().trim())*pico);
			} else {
				status = false;
				textArea.appendText("Value of capacitor C1 is incorrect. Value must be in pico Farads\n");
			} 

			if (!c2Text.getText().isEmpty() && c2Text.getText().length() > 0) {
				c2.setC(Double.parseDouble(c2Text.getText().trim())*pico);
			} else {
				status = false;
				textArea.appendText("Value of capacitor C2 is incorrect. Value must be in pico Farads\n");
			} 
			
			if (!c3Text.getText().isEmpty() && c3Text.getText().length() > 0) {
				c3.setC(Double.parseDouble(c3Text.getText().trim())*pico);
			} else {
				status = false;
				textArea.appendText("Value of capacitor C3 is incorrect. Value must be in pico Farads\n");
			} 
			
			if (!c4Text.getText().isEmpty() && c4Text.getText().length() > 0) {
				c4.setC(Double.parseDouble(c4Text.getText())*pico);
			} else {
				status = false;
				textArea.appendText("Value of capacitor C4 is incorrect. Value must be in pico Farads\n");
			} 
			
			if (!c5Text.getText().isEmpty() && c5Text.getText().length() > 0) {
				c5.setC(Double.parseDouble(c5Text.getText())*pico);
			} else {
				status = false;
				textArea.appendText("Value of capacitor C5 is incorrect. Value must be in pico Farads\n");
			} 
			
		} catch (Exception e) {
			textArea.appendText("Exception: " + e.getMessage() + "\n");
			return false;
		}
		
		
		return status;
			
	}
	
	private boolean isNumeric (String str) {
		return str.matches("^[0-9]+$");
	}
	
	@FXML 
	private void ExitProgram (ActionEvent event) {
		Stage stage = sceneController.getRootStage();
		stage.close();
		System.exit(0);
	}
	
    @FXML
    public void openFile() {	
		String fileName = "";
		Stage stage = sceneController.getRootStage();
	   	FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Select file to save component values to");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("XML Files", "*.xml"));
		fileChooser.setInitialFileName("FA Parameters.xml");
		
		try {
			File f;
			f = new File(new File(".").getCanonicalPath());
			fileChooser.setInitialDirectory(f);
		} catch (IOException e) {
			textArea.appendText("Could not set filechooser default directory. Exception: " + e.getMessage() + "\n");
		}


		try {
			fileName = fileChooser.showOpenDialog(stage).getAbsolutePath();
		} catch (Exception e) {
			textArea.appendText("Could not open file to read configuration parameters from. Exception: " + e.getMessage() + "\n");
			return;
		}
    	
		ArrayList<SerializeDataSet> arraylist1;
    	try {
    		FileInputStream inputStream = new FileInputStream(fileName);
			XMLDecoder  decoder = new XMLDecoder (inputStream);
			arraylist1 = (ArrayList<SerializeDataSet>) decoder.readObject();
			decoder.close();
		} catch (Exception ex) {
			textArea.appendText("Exception: " + ex.getMessage() + "\n");
			return;
		} 
    	
    	for (SerializeDataSet sds :  arraylist1 ) {
    		switch (sds.getType()) {
			case "C1":
				c1Text.setText(sds.getValue());
				break;
			case "C2":
				c2Text.setText(sds.getValue());
				break;
			case "C3":
				c3Text.setText(sds.getValue());
				break;
			case "C4":
				c4Text.setText(sds.getValue());
				break;
			case "C5":
				c5Text.setText(sds.getValue());
				break;

			case "Y1Cm":
				y1CText.setText(sds.getValue());
				break;
			case "Y1Lm":
				y1LText.setText(sds.getValue());
				break;
			case "Y1Rm":
				y1RText.setText(sds.getValue());
				break;
			case "Y1Cc":
				y1CSText.setText(sds.getValue());
				break;
				
			case "Y2Cm":
				y2CText.setText(sds.getValue());
				break;
			case "Y2Lm":
				y2LText.setText(sds.getValue());
				break;
			case "Y2Rm":
				y2RText.setText(sds.getValue());
				break;
			case "Y2Cc":
				y2CSText.setText(sds.getValue());
				break;
		
			case "Y3Cm":
				y3CText.setText(sds.getValue());
				break;
			case "Y3Lm":
				y3LText.setText(sds.getValue());
				break;
			case "Y3Rm":
				y3RText.setText(sds.getValue());
				break;
			case "Y3Cc":
				y3CSText.setText(sds.getValue());
				break;
				
			case "Y4Cm":
				y4CText.setText(sds.getValue());
				break;
			case "Y4Lm":
				y4LText.setText(sds.getValue());
				break;
			case "Y4Rm":
				y4RText.setText(sds.getValue());
				break;
			case "Y4Cc":
				y4CSText.setText(sds.getValue());
				break;
				
			case "FStart":
				startFreqText.setText(sds.getValue());
				break;
			case "FEnd":
				endFreqText.setText(sds.getValue());
				break;
			case "Finc":
				incFreqText.setText(sds.getValue());
				break;  			
 
			case "ZINREAL":
				zinRealText.setText(sds.getValue());
				break;
			case "ZINIMAG":
				zinImagText.setText(sds.getValue());
				break;
			case "ZOUTREAL":
				zoutRealText.setText(sds.getValue());
				break;
			case "ZOUTIMAG":
				zoutImagText.setText(sds.getValue());
				break;
    		
    		
    		}
    	}

    }

	
    @FXML
    public void saveFile() {	
    	
	    //populating the series with data. This also checks the data
		if (!loadCrystals()) {
			note.alert ("Inproper Values", "One or more of the crystal data values are incorrect");
			return;
		}
		if (!loadCapacitors()) {
			note.alert ("Inproper Data Values", "One or more of the capacitor data values are incorrect");
			return;
		}
		if (!loadFrequencies()) {
			note.alert ("Inproper Data Values", "One or more of the frequency data values are incorrect");
			return;
		}
    	
    	SerializeDataSet c1ds = new SerializeDataSet ("C1", c1Text.getText());
    	SerializeDataSet c2ds = new SerializeDataSet ("C2", c2Text.getText());
    	SerializeDataSet c3ds = new SerializeDataSet ("C3", c3Text.getText());
    	SerializeDataSet c4ds = new SerializeDataSet ("C4", c4Text.getText());
    	SerializeDataSet c5ds = new SerializeDataSet ("C5", c5Text.getText());
    	
    	SerializeDataSet y1cmds = new SerializeDataSet ("Y1Cm", y1CText.getText());
    	SerializeDataSet y1lmds = new SerializeDataSet ("Y1Lm", y1LText.getText());
    	SerializeDataSet y1rmds = new SerializeDataSet ("Y1Rm", y1RText.getText());
    	SerializeDataSet y1ccds = new SerializeDataSet ("Y1Cc", y1CSText.getText());

    	SerializeDataSet y2cmds = new SerializeDataSet ("Y2Cm", y2CText.getText());
    	SerializeDataSet y2lmds = new SerializeDataSet ("Y2Lm", y2LText.getText());
    	SerializeDataSet y2rmds = new SerializeDataSet ("Y2Rm", y2RText.getText());
    	SerializeDataSet y2ccds = new SerializeDataSet ("Y2Cc", y2CSText.getText());

    	SerializeDataSet y3cmds = new SerializeDataSet ("Y3Cm", y3CText.getText());
    	SerializeDataSet y3lmds = new SerializeDataSet ("Y3Lm", y3LText.getText());
    	SerializeDataSet y3rmds = new SerializeDataSet ("Y3Rm", y3RText.getText());
    	SerializeDataSet y3ccds = new SerializeDataSet ("Y3Cc", y3CSText.getText());

    	SerializeDataSet y4cmds = new SerializeDataSet ("Y4Cm", y4CText.getText());
    	SerializeDataSet y4lmds = new SerializeDataSet ("Y4Lm", y4LText.getText());
    	SerializeDataSet y4rmds = new SerializeDataSet ("Y4Rm", y4RText.getText());
    	SerializeDataSet y4ccds = new SerializeDataSet ("Y4Cc", y4CSText.getText());
    	
    	SerializeDataSet fsds = new SerializeDataSet ("FStart", startFreqText.getText());
    	SerializeDataSet feds = new SerializeDataSet ("FEnd", endFreqText.getText());
    	SerializeDataSet fids = new SerializeDataSet ("Finc", incFreqText.getText());  	
    	
    	SerializeDataSet zinreds = new SerializeDataSet ("ZINREAL", zinRealText.getText());
    	SerializeDataSet zinimds = new SerializeDataSet ("ZINIMAG", zinImagText.getText());
    	SerializeDataSet zoutreds = new SerializeDataSet ("ZOUTREAL", zoutRealText.getText());
    	SerializeDataSet zoutimds = new SerializeDataSet ("ZOUTIMAG", zoutImagText.getText());        	
    	
	    ArrayList arrayList1 = new ArrayList();
	    arrayList1.add(c1ds);
	    arrayList1.add(c2ds);
	    arrayList1.add(c3ds);
	    arrayList1.add(c4ds);
	    arrayList1.add(c5ds);

	    arrayList1.add(y1cmds);
	    arrayList1.add(y1lmds);
	    arrayList1.add(y1rmds);
	    arrayList1.add(y1ccds);

	    arrayList1.add(y2cmds);
	    arrayList1.add(y2lmds);
	    arrayList1.add(y2rmds);
	    arrayList1.add(y2ccds);
	    
	    arrayList1.add(y3cmds);
	    arrayList1.add(y3lmds);
	    arrayList1.add(y3rmds);
	    arrayList1.add(y3ccds);
 
	    arrayList1.add(y4cmds);
	    arrayList1.add(y4lmds);
	    arrayList1.add(y4rmds);
	    arrayList1.add(y4ccds);

	    arrayList1.add(fsds);
	    arrayList1.add(feds);
	    arrayList1.add(fids);
	    
	    arrayList1.add(zinreds);
	    arrayList1.add(zinimds);
	    arrayList1.add(zoutreds);
	    arrayList1.add(zoutimds);
    	
	    
		String fileName = "";
		Stage stage = sceneController.getRootStage();
	   	FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Select file to save component values to");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("XML Files", "*.xml"));
		fileChooser.setInitialFileName("FA Parameters.xml");
		
		try {
			File f;
			f = new File(new File(".").getCanonicalPath());
			fileChooser.setInitialDirectory(f);
		} catch (IOException e) {
			textArea.appendText("Could not set filechooser default directory. Exception: " + e.getMessage() + "\n");
		}
		
		try {
			fileName = fileChooser.showSaveDialog(stage).getAbsolutePath();
		} catch (Exception e) {
			textArea.appendText("Could not open file to read configuration parameters from. Exception: " + e.getMessage() + "\n");
			return;
		}
    	
    	
    	try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			XMLEncoder encoder = new XMLEncoder(outputStream);
			encoder.writeObject(arrayList1);
			encoder.close();
			Thread.sleep(10000);
		} catch (Exception ex) {
			textArea.appendText("Exception: " + ex.getMessage() + "\n");
			return;
		} 
    }
	
    
    @FXML
    public void aboutHelp() {	
    	System.out.println("About");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Filter Analyser");
		alert.setHeaderText("Filter Analyzer v0.1");
		alert.setContentText("Content Text Here");

		TextArea textArea;
		String txt = "Filter Analyzer is a program that is used to characterize a four pole crystal ladder filter. " + 
			"It calculates the voltage gain, input impedance and output impedance as a function of frequency. " +
			"It also generates plots of voltage gain and impedance\n\n" + 
			"Written by Dave Rajnauth (VE3OOI)\n" + 
			"Licensed under Creative Commons. No commercial use permitted";
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
    	
    }
	
	
}
