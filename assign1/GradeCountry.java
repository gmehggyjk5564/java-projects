/**
 * This class tests the Country class defined in SU18 CS1027 Asn1
 * It runs through each of the methods specified in turn based
 * on sample data taken from data.txt.
 * 
 * @author beth
 *
 */
public class GradeCountry {
 
	public static void main(String[] args) {

		final int NUM_TEST_COUNTRIES = 2;
		final double EPSILON = 1E-6;
		String[] testData_Countries = { "Canada", "Indonesia" };
		int[] testData_Populations = { 3427000, 260581100 };
		double[] testData_Areas = { 99140.0, 180590.97};
		String[] testData_Continents = { "North America", "Asia" };
		// double[] solution_PopDensity = { }
		Country basicTest;

		ThingToWriteFile out = new ThingToWriteFile("AutogradingCountry.txt");
		
		out.writeLine("Grade Report");
		out.writeLine("=====================================");
		out.writeLine("   Testing Country Class");
		out.writeLine("=====================================");
		out.writeLine("The = indicate correct behaviour was observed");
		out.writeLine("The X indicates incorrect behaviour");
		
		// We will learn more about "exceptions" later.  Basically java has a way to
		// catch when there are serious/program-ending type errors happening, so you have a
		// chance to recover without the program just crashing.  
		// That's what the try/catch here is about
		
		// Test whether a Country can be created at all
		try {
			basicTest = new Country("Canada", 34207000, 9976140.0, "North America");
		} catch (Exception e) {
			out.writeLine("Could not create country. Can't test anything...exiting");
			out.writeLine("0 / 20 - Base grade for Country.java");
			System.exit(1);
		}

		double getterScore = 4;
		int popScore = 4;
		int setterScore = 2;
		int toStringScore = 10;
		
		for (int i = 0; i < NUM_TEST_COUNTRIES; i++) {
			out.writeLine ("Testing: " + testData_Countries[i]);
			Country test = new Country(testData_Countries[i], testData_Populations[i], testData_Areas[i],
					testData_Continents[i]);

			// ===== Test the basic getter method ========================== //
			String outLine = "\tGetters: ";
			if (test.getName().equals(testData_Countries[i]))
				outLine += "=";
			else {
				getterScore -= 0.5;
				outLine +="X";
			}
			if (test.getPopulation() == testData_Populations[i])
				outLine += "=";
			else {
				getterScore -= 0.5;
				outLine +="X";
			}
			
			if (test.getArea() == testData_Areas[i])
				outLine += "=";
			else {
				getterScore -= 0.5;
				outLine +="X";
			}
			
			if (test.getContinent().equals(testData_Continents[i]))
				outLine += "=";
			else {
				getterScore -= 0.5;
				outLine +="X";
			}
			
			out.writeLine(outLine); // == End of test getter methods ========== //

			// ===== Test special getter ==================================== //
			outLine = "\tPopulation density: ";
			// What we expect
			double popDens = ((double) testData_Populations[i]) / testData_Areas[i];
			// Test with fp precision considerations
			if (Math.abs(test.getPopDensity() - popDens) < EPSILON)
				outLine += "=";
			else {
				if (test.getPopDensity()!=0)
					popScore --;
				popScore -=2; 
				outLine +="X";
			}
			out.writeLine(outLine); // === End of test special getter methods = //

			// ===== Test setter ============================================ //
			outLine = "\tSet population to 0: ";
			test.setPopulation(0);
			if (test.getPopulation() == 0)
				outLine += "=";
			else {
				setterScore --;
				outLine +="X";
			}
			out.writeLine(outLine); // === End test setter ===================== //

			// ===== Test toString =========================================== //
			outLine = "\ttoString: ";
			if (test.toString().equals(testData_Countries[i] + " in " + testData_Continents[i]))
				outLine += "=";
			else {
				outLine +="X - Incorrect formatting\n";
				toStringScore -=2;
				if( ! test.toString().contains(testData_Countries[i])) {
					toStringScore -=2;
					outLine += "\t\tDoes not contain Country name \n";
				}
				if (! test.toString().contains(testData_Continents[i])) {
					toStringScore -=1;
					outLine += "\t\tDoes not contain Continent name ";
				}
			}
			out.writeLine(outLine); // == End of test toString ================ //
		}
		
		out.writeLine("=====================================");
		out.writeLine("     Summary");
		out.writeLine("=====================================");
		out.writeLine("  " + getterScore +" /4 Getter Methods");
		out.writeLine("  " + popScore +" /4 Population Density");
		out.writeLine("  " + setterScore+" /2 Setter Method");
		out.writeLine("  " + toStringScore + " /10 toString Method");
		double total = getterScore + popScore + setterScore + toStringScore;
		out.writeLine(total + " / 20 Base Grade For Country.java");
		out.writeLine("=====================================");
		out.close();
//		out.writeLine("");
	}
}
