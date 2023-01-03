# FilterAnalyzer
Four Pole Crystal Ladder Filter Analyzer

Filter Analyzer is a program that is used to characterize a four pole crystal ladder filter using Thevenin equilavent circuit and Kirchhoff's current law. It calculates the voltage gain, input impedance and output impedance as a function of frequency. It also generates plots of voltage gain (bode plot) and impedance.

The program is written in Java and developed using Eclipse. The program requires Java VM to be installed.  You need to understand how to run a Java program using the Java VM.  Currently only Java 1.8.0_811 is supported and can be downloaded from https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html
Download "Java SE Runtime Environment 8u311".  To run the program there is a FilterAnalyzer.jar file or a filteranalyzer.bat. Run the bat file if you don't understand how to run a java program.

Usage:

java -jar FilterAnalyzer.jar


The program allows you:
1. Define various components values for the crystal filter (capacitors, crystal motional parameters, loads, etc)
2. Define start, stop and increment for frequency sweeps
3. Perform sweeps with the filter open (unterminated) or with the filter terminated. Impedance specified as a complex number
4. Perform an output impedance sweep and plot the resulting data 
5. Create a output bode plot (magnitude only without phase)
6. Perform a detailed calculation showing complex values for a single freqency
7. Perform an interative calculation where the calculated input and output impedance values are fed back into the calculation to determine if the solution converges to single input and output impedance value.
8. Save the defined components values to an xml file
9. Open a previously saved xml file with component values


Written by Dave Rajnauth (VE3OOI) and licensed under Creative Commons. No commercial use permitted.
