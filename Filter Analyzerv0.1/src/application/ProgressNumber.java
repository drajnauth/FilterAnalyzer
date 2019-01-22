package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ProgressNumber {
	private static DoubleProperty progress;
   
    public final void setProgress (double p) {
    	ProgressNumber.progressNumberProperty().set(p);
    }
    
    public final double getProgress () {
    	if (progress == null) {
    		return progress.get();
    	}
    	return 0;
    }
    
    public final static DoubleProperty progressNumberProperty() {
    	if (progress == null) {
    		progress = new SimpleDoubleProperty(0);
    	}
    	return progress;
    }
	

}
