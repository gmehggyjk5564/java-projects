import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GradeAssignment1 {

	public static void main(String[] args) {

		ThingToWriteFile out = new ThingToWriteFile("AutogradingOutputCatalog.txt");


		ByteArrayOutputStream buf = new ByteArrayOutputStream();

		PrintStream oldStream = System.out;
		System.setOut(new PrintStream(buf, true));
		// Stop capturing
		//System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));


		out.writeLine("Grade Report");
		out.writeLine("=====================================");
		out.writeLine("   Testing Country Catalog Class");
		out.writeLine("=====================================");
		out.writeLine("The = indicate correct behaviour was observed");
		out.writeLine("The X indicates incorrect behaviour");
		out.writeLine("There should be a Summary at the bottom with your base grade");
		out.writeLine("=====================================");

		CountryCatalogue cc=null;
		SampleSolution scc = null;
		Country cntry;
		try {

			cc = new CountryCatalogue("continent.txt", "data.txt");
			scc = new SampleSolution("continent.txt", "data.txt");
		}
		catch(Exception e) {
			
			out.writeLine("X - Could not create catalog - cannot grade\n"
					+ e.getMessage()+"\n"
					+ "0 / 80 Base Grade For CountryCatalog.java ");
			out.close();
			System.exit(1);  //Hard quit
		}

		String result1 = null;
		String result2 = null;

		//test printCountrycatalogue
		int printCCScore = 10;
		try {
			out.writeLine("=====================================");
			out.writeLine("Testing printCountryCatalog");
			/*buf.reset();
			scc.printCountryCatalogue();
			result1 = buf.toString();
			 */
			buf.reset();
			cc.printCountryCatalogue();
			result2 = buf.toString();
			out.writeLine("\n" + result2);
			if(result2.contains("China in Asia") 
					&& result2.contains("Mexico in North America")
					&& result2.contains("Egypt in Africa")
					//&& result2.contains("Ghana in Africa")
					&& result2.contains("France in Europe")
					&& result2.contains("United States of America in North America")
					&& result2.contains("Brazil in South America")
					&& result2.contains("Japan in Asia")
					&& result2.contains("Canada in North America")
					&& result2.contains("Indonesia in Asia")
					&& result2.contains("Nigeria in Africa")
					//&& result2.contains("Zambia in Africa")
					&& result2.contains("Italy in Europe")
					&& result2.contains("South Africa in Africa")
					&& result2.contains("South Korea in Asia")
					&& result2.contains("Colombia in South America")
					&& result2.contains("Mostus Populus in Testanica")
					&& result2.contains("Small Areaty in Testanica")
					)
				out.writeLine("= Found all expected countries in output");
			else {
				if (result2.contains("China")
						&& result2.contains("Canada")) {
					out.writeLine("X Found some country names, but incorrect format or number of countries");
					printCCScore -=4;
				}
				else {
					out.writeLine("X Did not find all expected countries");
					printCCScore -=6;	
				}
			}

		} catch (Exception e) {
			out.writeLine("X Exception when Testing print country catalog");
			printCCScore = 2;
			out.writeLine("\t" + e.getMessage());
		}


		//Test add, delete find
		int addDelFindScore = 10;
		try { 
			out.writeLine("=====================================");
			out.writeLine("Testing add, delete and find");
			cntry = cc.findCountry("Canada");
			if(cntry != null){
				if(cntry.getName().equals("Canada"))
					out.writeLine("= Found Canada");
				else {
					out.writeLine("X Couldn't find Canada");
					addDelFindScore -=2;
				}
			}
			cntry = cc.findCountry("England");
			if(cntry != null){
				out.writeLine("X Found England Before it was added");
				addDelFindScore -=4;
			}
			scc.addCountry("England", 54316600, 130279, "Europe");
			if(cc.addCountry("England", 54316600, 130279, "Europe")) {
				out.writeLine("= Added England (according to return value)");
				cntry = cc.findCountry("England");
				if(cntry != null){
					out.writeLine("= Found England");
				}
				else {
					out.writeLine("X but couldn't find England");
					addDelFindScore -=2;
				}
			}
			else {
				out.writeLine("X Could not add England");
				addDelFindScore -=2;
			}
			scc.deleteCountry("Brazil");

			//method to test
			out.writeLine("Attempting to delete Brazil");
			buf.reset();
			cc.deleteCountry("Brazil");
			result1 = buf.toString();
			buf.reset();

			cc.deleteCountry("Havartia");
			result2 = buf.toString();

			if(result1.equals(result2)) {
				out.writeLine("X When deleting - success and fail have the same message");
				addDelFindScore -=2;
			}
			cntry = cc.findCountry("Brazil");
			if(cntry != null){
				out.writeLine("X found Brazil");
				addDelFindScore -=2;
			}
			else {
				out.writeLine("= and couldn't find Brazil");
			}

		}
		catch (Exception e){
			out.writeLine("X Exception when Testing add, delete, find");
			addDelFindScore = 2;
			out.writeLine("\t" + e.getMessage());
		}
		if (addDelFindScore<0) {
			addDelFindScore = 0;
		}

		int filtByContScore = 10;
		out.writeLine("=====================================");
		out.writeLine("  Testing filter by continent");
		
		try {
			buf.reset();
			cc.filterCountriesByContinent("Asia");
			result2 = buf.toString();
			out.writeLine("\n" + result2);
			if(result2.contains("China") 
					&& result2.contains("South Korea")
					&& result2.contains("Japan")
					&& result2.contains("Indonesia")
					)
				out.writeLine("= Found all expected countries in output");
			else {
				out.writeLine("X Did not print all expected countries");
				filtByContScore -=4;
			}
			if(result2.contains("\n"))
				out.writeLine("= Whitespace detected");
			else {
				out.writeLine("X No newlines detected in output");
				filtByContScore -=2;
			}
			if(result2.contains("Mexico")  
					|| result2.contains("Egypt")
					|| result2.contains("France")
					|| result2.contains("Mostus Populus")
					) {
				out.writeLine("X Unexpected Country(ies) found in output");
				filtByContScore -=2;
			}
			else
				out.writeLine("= No unexpected countries found in output");
		}
		catch (Exception e){
			out.writeLine("X Exception when testing filter by continent");
			filtByContScore = 2;
			out.writeLine("\t" + e.getMessage());
		}
		if(filtByContScore<0)
			filtByContScore = 0;


		int setPopScore = 10;
		out.writeLine("=====================================");
		out.writeLine("  Testing set population");

		try {
			cc.setPopulationOfACountry("South Korea", 50504500); 
			
				out.writeLine("= Method Reports Success");
				Country found = cc.findCountry("South Korea");
				if(found!=null && found.getPopulation() == 50504500) {
					out.writeLine("= Found South Korea with population change");
				}
				else {if (found != null && found.getPopulation() != 50504500) {
					out.writeLine("X Found South Korea but population unchanged");
					setPopScore-=4;
				}
				else {
					out.writeLine("X Could not find South Korea to test pop change");
					setPopScore-=8;
				}}

		}
		catch (Exception e){
			out.writeLine("X Exception when testing");
			setPopScore = 1;
			out.writeLine("\t" + e.getMessage());
		}

		int findLarPopScore = 5;
		out.writeLine("=====================================");
		out.writeLine("  Testing find largest population");

		try {
			result2 = cc.findCountryWithLargestPop();

			if(result2!=null) {
				out.writeLine("= A country was found");
				if(result2.equals("Mostus Populus")) {
					out.writeLine("= Found Mostus Populus");
				}
				else {
					out.writeLine("X Did not find Mostus Populus as largest pop");
					findLarPopScore -=3;
				}
			}
			else {
				out.writeLine("X Did not return a valid String");
				findLarPopScore -=4;
			}
		}
		catch (Exception e){
			out.writeLine("X Exception when testing largest population");
			findLarPopScore = 1;
			out.writeLine("\t" + e.getMessage());
		}

		int findSmAreaScore = 5;
		out.writeLine("=====================================");
		out.writeLine("  Testing find smallest area");

		try {
			result2 = cc.findCountryWithSmallestArea();

			if(result2!=null) {
				out.writeLine("= A country was found");
				if(result2.equals("Small Areaty")) {
					out.writeLine("= Found Small Areaty");
				}
				else {
					out.writeLine("X Did not find Small Areaty as largest pop");
					findSmAreaScore -=3;
				}
			}
			else {
				out.writeLine("X Did not return a valid String");
				findSmAreaScore -=4;
			}
		}
		catch (Exception e){
			out.writeLine("X Exception when testing smallest area");
			findSmAreaScore = 1;
			out.writeLine("\t" + e.getMessage());
		}


		int filtPopDensScore = 10;
		out.writeLine("=====================================");
		out.writeLine("  Testing filter by pop density");

		try {
			out.writeLine("Testing with range 0, 20 - Expect NO results");
			buf.reset();
			cc.filterCountriesByPopDensity(0, 20);
			result2 = buf.toString();
			out.writeLine(result2);
			if(result2!=null) {
				if(result2.contains("China") 
						|| result2.contains("Mexico")
						|| result2.contains("Egypt")
						//|| result2.contains("Ghana in Africa")
						|| result2.contains("France")
						|| result2.contains("United States of America")
						|| result2.contains("Brazil")
						|| result2.contains("Japan")
						|| result2.contains("Canada")
						|| result2.contains("Indonesia")
						|| result2.contains("Nigeria")
						//|| result2.contains("Zambia in Africa")
						|| result2.contains("Italy")
						|| result2.contains("South Africa")
						|| result2.contains("South Korea")
						|| result2.contains("Colombia")
						|| result2.contains("Mostus Populus")
						|| result2.contains("Small Areaty")
						) {
					out.writeLine("X Result contains a country name");
					filtPopDensScore -= 4;
				}
			}	
			out.writeLine("Testing with range 40, 100 - Expect Mexico, Egypt, South Africa and Columbia");
			buf.reset();
			cc.filterCountriesByPopDensity(40, 100);
			result2 = buf.toString();
			out.writeLine(result2);
			if(result2!=null) {
				if(result2.contains("China") 
						|| result2.contains("France")
						|| result2.contains("United States of America")
						|| result2.contains("Brazil")
						|| result2.contains("Japan")
						|| result2.contains("Canada")
						|| result2.contains("Indonesia")
						|| result2.contains("Nigeria")
						|| result2.contains("Italy")
						|| result2.contains("South Korea")
						|| result2.contains("Mostus Populus")
						|| result2.contains("Small Areaty")
						) {
					out.writeLine("X Result contains an unexpected country name");
					filtPopDensScore -= 4;

				}
				else {
					out.writeLine("= No unexpected countries found");
				}
				if (result2.contains("Mexico")
						&& result2.contains("Egypt")
						&& result2.contains("South Africa")
						&& result2.contains("Colombia")
						)
					out.writeLine("= Correct countries found");
				else {
					out.writeLine("X Not all correct countries found");
					filtPopDensScore-=4;
				}
			}

		}
		catch (Exception e){
			out.writeLine("X Exception when testing filter by pop dens");
			filtPopDensScore = 2;
			out.writeLine("\t" + e.getMessage());
		}

		int printMostPopContScore = 10;
		out.writeLine("=====================================");
		out.writeLine("  Testing print most populous continent");

		try {
			buf.reset();
			cc.printMostPopulousContinent();
			result2 = buf.toString();
			out.writeLine(result2);

			buf.reset();
			scc.printMostPopulousContinent();
			result1=buf.toString();
			out.writeLine(result1);

			if(result1.equals(result2)) {
				out.writeLine("= Output matches sample solution perfectly");
			}
			else if( result2.contains("Testanica") && result2.contains("1778655600") ){
				out.writeLine("X Output contains correct information but incorrect format");
				printMostPopContScore -=2;
			}
			else if( result2.contains("Testanica")) {
				out.writeLine("X Output contains only correct continent name");
				printMostPopContScore -=4;
			}
			else {
				out.writeLine("X Output does not contain correct infomation");
				printMostPopContScore -=8;
			}

		}
		catch (Exception e){
			out.writeLine("X Exception when testing print most populus continent");
			printMostPopContScore = 1;
			out.writeLine("\t" + e.getMessage());
		}

		int saveCCScore = 10;
		out.writeLine("=====================================");
		out.writeLine("  Testing save country catalogue");

		try {
			cc.saveCountryCatalogue("outDetails.txt");
			scc.saveCountryCatalogue("outDetailsSample.txt");
			
			buf.reset();
			ThingToReadFile ccFile = new ThingToReadFile("outDetails.txt");
			result1 = buf.toString();
			if (result1.contains("not found") || result1.contains("cannot be read")) {
				out.writeLine("X Exception when loading saved catalogue");
				saveCCScore = 4;
				out.writeLine("\t" + result1);
			}
			else {
				ThingToReadFile sccFile = new ThingToReadFile("outDetailsSample.txt");
				result2="";
				while(!ccFile.endOfFile()) {
					result2 += ccFile.readLine() + "\n";
				}
				out.writeLine(result2);
				int linesWrong = 0;
				while(!sccFile.endOfFile()) {
					String line = sccFile.readLine();
					if (line.length()>20 && !line.startsWith("England")) { //skip empty lines
						//out.writeLine((String)line.subSequence(0, 20));
						if(!result2.contains( line.subSequence(0, 20) )) {
							out.writeLine("X Outfile does not contain the expected line beginning: " + line.subSequence(0, 20));
							linesWrong++;
						}
					}
				}
				if (linesWrong == 0) {
					out.writeLine("= No major errors in outfile");
				}
				else if (linesWrong <3) {
					out.writeLine("X Some errors in outfile");
					saveCCScore -=2;
				}
				else {
					if(result2!=null) {
						if(result2.contains("China") 
								&& result2.contains("Mexico")
								&& result2.contains("Egypt")
								//&& result2.contains("Ghana in Africa")
								&& result2.contains("France")
								&& result2.contains("United States of America")
								&& result2.contains("Brazil")
								&& result2.contains("Japan")
								&& result2.contains("Canada")
								&& result2.contains("Indonesia")
								&& result2.contains("Nigeria")
								//&& result2.contains("Zambia in Africa")
								&& result2.contains("Italy")
								&& result2.contains("South Africa")
								&& result2.contains("South Korea")
								&& result2.contains("Colombia")
								&& result2.contains("Mostus Populus")
								&& result2.contains("Small Areaty")
								) {
							out.writeLine("X outfile contains all Countries - likley formatting error");
							saveCCScore -= 2;
						}

						else if (result2.contains("China") 
								|| result2.contains("Mexico")
								|| result2.contains("Egypt")
								//|| result2.contains("Ghana in Africa")
								|| result2.contains("France")
								|| result2.contains("United States of America")
								|| result2.contains("Brazil")
								|| result2.contains("Japan")
								|| result2.contains("Canada")
								|| result2.contains("Indonesia")
								|| result2.contains("Nigeria")
								//|| result2.contains("Zambia in Africa")
								|| result2.contains("Italy")
								|| result2.contains("South Africa")
								|| result2.contains("South Korea")
								|| result2.contains("Colombia")
								|| result2.contains("Mostus Populus")
								|| result2.contains("Small Areaty")
								) {
							out.writeLine("X Result contains at least one country name, but more than a few lines incorrect");
							saveCCScore -= 4;
						}
						else{
							out.writeLine("X outfile does not contain any countries");
							saveCCScore -=8;
						}
					}
				}

			}
		}
		catch (Exception e){
			out.writeLine("X Exception when testing save country catalogue");
			saveCCScore = 2;
			out.writeLine("\t" + e.getMessage());
		}


		out.writeLine("=====================================");
		out.writeLine("     Summary");
		out.writeLine("=====================================");
		out.writeLine(printCCScore +" /10 Print Country Catalogue (and constructor)");
		out.writeLine(addDelFindScore +" /10 Add, Delete and Find");
		out.writeLine(filtByContScore+ " /10 Filter Countries by Continent");
		out.writeLine(setPopScore + " /10 Set Population");
		out.writeLine(findLarPopScore +" /5 Find Largest Population");
		out.writeLine(findSmAreaScore +" /5 Find Smallest Area");
		out.writeLine(filtPopDensScore+ " /10 Filter by Population Density");
		out.writeLine(printMostPopContScore+" /10 Print Most Populous Continent");
		out.writeLine(saveCCScore + " /10 Save Country Catalogue");
		double total = printCCScore+addDelFindScore+filtByContScore+setPopScore+findLarPopScore+findSmAreaScore+filtPopDensScore+printMostPopContScore+saveCCScore;
		out.writeLine(total + " / 80 Base Grade For CountryCatalogue.java");
		out.writeLine("=====================================");
		out.close();
	}

}


