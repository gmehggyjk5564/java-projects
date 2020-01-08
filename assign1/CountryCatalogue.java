import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Class that allows a program to store, search, remove and filter country data
 * Class that represents a country catalogue as a list of countries
 * 
 * @author Sofya Pryadko
 *
 */

public class CountryCatalogue {

	private final int DEFAULT_MAX_COUNTRIES = 10000000; // default size of array

	/* Attribute declarations */
	private Country[] catalogue; // array of countries
	private HashMap<String, String> continentMap = new HashMap<String, String>(); // dictionary that assigns countries
																					// to their continents
	private int numCountries;

	/**
	 * Constructor initializes the country's name and continent in a catalogue by
	 * reading two files
	 */
	public CountryCatalogue(String continentFileName, String countryFileName) {
		catalogue = new Country[DEFAULT_MAX_COUNTRIES];
		numCountries = 0;
		ThingToReadFile continentFileWhichIsRead = new ThingToReadFile(continentFileName);
		String aLineWithComma = continentFileWhichIsRead.readLine();
		while (continentFileWhichIsRead.endOfFile() == false) {
			aLineWithComma = continentFileWhichIsRead.readLine();
			String[] oneCountryOneContinent = aLineWithComma.split(",");
			continentMap.put(oneCountryOneContinent[0], oneCountryOneContinent[1]);
		}
		continentFileWhichIsRead.close();
		ThingToReadFile countryFileWhichIsRead = new ThingToReadFile(countryFileName);
		String aLineWithCommaCountryFile = countryFileWhichIsRead.readLine();
		while (countryFileWhichIsRead.endOfFile() == false) {
			aLineWithCommaCountryFile = countryFileWhichIsRead.readLine();
			String[] splitLine = aLineWithCommaCountryFile.split(",");
			Country aCountry = new Country(splitLine[0], Integer.parseInt(splitLine[1]),
					Double.parseDouble(splitLine[2]), continentMap.get(splitLine[0]));
			catalogue[numCountries] = aCountry;
			numCountries++;
		}
		countryFileWhichIsRead.close();
	}

	public boolean addCountry(String countryName, int countryPopulation, double countryArea, String countryContinent) {
		// create a new Country object
		Country aNewCountry = new Country(countryName, countryPopulation, countryArea, countryContinent);
		final int NOT_FOUND = -1;
		int search = NOT_FOUND;

		// search the list for the specified country
		for (int i = 0; i < numCountries && search == NOT_FOUND; i++) {
			if (catalogue[i].equals(aNewCountry) == false) {
				search = i; // if not found, should add new country to catalogue and add continent to
							// continentMap
				continentMap.put(countryName, countryContinent);
				catalogue[numCountries] = aNewCountry;
				numCountries++;
				return true;
			} // if country with the same name found, return false
		}
		return false;
	}

	public void deleteCountry(String countryName) {
		final int NOT_FOUND = -1;
		int search = NOT_FOUND;

		// search the list for the specified country
		for (int i = 0; i < numCountries && search == NOT_FOUND; i++) {
			if (catalogue[i].getName().equals(countryName)) {
				search = i;
				// target country found, remove by replacing with last one in list in both
				// continentMap and catalogue
				continentMap.remove(countryName);
				catalogue[search] = catalogue[numCountries - 1];
				catalogue[numCountries - 1] = null;
				numCountries--;
				System.out.println(countryName + " has been removed from the country catalogue");
			}
		}

		// if not found, can't remove
		if (search == NOT_FOUND)
			System.out.println(countryName + "has never existed in the country catalogue");
		// if list is empty, can't remove
		if (numCountries == 0)
			System.out.println("nothing can be removed,country catalogue is empty");
	}

	public Country findCountry(String countryName) {
		final int NOT_FOUND = -1;
		int search = NOT_FOUND;

		// search the list for the specified country
		for (int i = 0; i < numCountries && search == NOT_FOUND; i++) {
			if (catalogue[i].getName().equals(countryName)) {
				search = i;
				// target country found, return it from catalogue
				return catalogue[i];
			}
		}
		return null;
	}

	/**
	 * prints countries that are in the given continent
	 * 
	 */
	public void filterCountriesByContinent(String continentName) {
		List<String> listOfCountries = new ArrayList<>();
		for (Entry<String, String> entry : continentMap.entrySet()) {
			if (entry.getValue().equals(continentName)) {
				listOfCountries.add(entry.getKey());
				System.out.println(entry.getKey());
			}
		}
	}

	/**
	 * printCountryCatalogue prints strings that describe countries in the catalogue
	 * 
	 */
	public void printCountryCatalogue() {
		for (int i = 0; i < numCountries; i++) {
			System.out.println(catalogue[i].toString());
		}

	}

	/**
	 * setPopulationOfACountry method sets country's population to new population if
	 * country exists in catalogue
	 * 
	 * @return true or false
	 */
	public boolean setPopulationOfACountry(String countryName, int countryPopulation) {
		final int NOT_FOUND = -1;
		int search = NOT_FOUND;

		// search the list for the specified country name
		for (int i = 0; i < numCountries && search == NOT_FOUND; i++) {
			if ((catalogue[i].getName()).equals(countryName)) {
				search = i;
				catalogue[search].setPopulation(countryPopulation);
				return true;
			}

		}
		return false;
	}

	/**
	 * findCountryWithLargestPop method returns name of country with largest
	 * population
	 * 
	 * @return name of country with largest population
	 */
	public String findCountryWithLargestPop() {
		double maxValue = catalogue[0].getArea();
		int indexOfMaxValue = -1;
		for (int i = 0; i < numCountries; i++) {
			if (catalogue[i].getPopulation() > maxValue) {
				maxValue = catalogue[i].getPopulation();
				indexOfMaxValue = i;
			}
		}
		return catalogue[indexOfMaxValue].getName();
	}

	/**
	 * findCountryWithSmallestArea method returns the county's with smallest area
	 * 
	 * @return the county's with smallest area
	 */
	public String findCountryWithSmallestArea() {
		double minValue = catalogue[0].getArea();
		int indexOfMinValue = -1;
		for (int i = 0; i < numCountries; i++) {
			if (catalogue[i].getArea() < minValue) {
				minValue = catalogue[i].getArea();
				indexOfMinValue = i;
			}
		}
		return catalogue[indexOfMinValue].getName();
	}

	/**
	 * filterCountriesByPopDensity method prints countries which are in range
	 * between lower and upper range by population Density
	 * 
	 */
	public void filterCountriesByPopDensity(int lowerBound, int upperBound) {
		ArrayList<Country> listLowUp = new ArrayList<>();
		for (int i = 0; i < numCountries; i++) {
			if (catalogue[i].getPopDensity() <= upperBound && catalogue[i].getPopDensity() >= lowerBound) {
				listLowUp.add(catalogue[i]);
				System.out.println(catalogue[i].getName());
			}
		}
	}

	/**
	 * printMostPopulousContinent method prints most populous continent and
	 * population number it has
	 * 
	 */
	public void printMostPopulousContinent() { // here we compare populations of each continent
		String mostPopuCont = "";
		int maxPopOfACont = 0; // first we find population of each continent woth the helper method and then we
								// compare each to another to find maximum of those
		for (int i = 0; i < numCountries; i++) {
			if (mostPopOFAContinent(catalogue[i].getContinent()) > maxPopOfACont) {
				maxPopOfACont = mostPopOFAContinent(catalogue[i].getContinent());
				mostPopuCont = catalogue[i].getName();
			}
		}
		System.out.println("Most populuous continent is " + mostPopuCont + " with population of " + maxPopOfACont);
	}

	/**
	 * saveCountryCatalogue method returns number of lines in a file we write in
	 * 
	 * @return numOfLines
	 */
	public int saveCountryCatalogue(String filename) {
		ThingToWriteFile fileWhichIsWritten = new ThingToWriteFile(filename);
		int numOfLines = 0;
		for (int i = 0; i < numCountries; i++) {
			fileWhichIsWritten.writeLine(catalogue[i].getName() + "|" + catalogue[i].getContinent() + "|"
					+ catalogue[i].getPopulation() + "|" + catalogue[i].getPopDensity());
			numOfLines++;
		}
		fileWhichIsWritten.close();
		return numOfLines;
	}

	// helper method, this method can find the population of a continent
	public int mostPopOFAContinent(String continentName) {
		int popOfACont = 0;
		for (int i = 0; i < numCountries; i++) {
			if (continentName.equals(catalogue[i].getContinent())) {
				popOfACont += catalogue[i].getPopulation();
			}
		}
		return popOfACont;
	}

}
