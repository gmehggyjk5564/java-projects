import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;



public class SampleSolution{

	private final int DEFAULT_SIZE = 5;  
	final int NOT_FOUND = -1;

	private Country[] catalogue;
	private int numCountries;
	private Map<String, String> continentMap;

	//for convenience
	private Set<String> continents;

	public SampleSolution(String continentFileName, String countriesFileName){
		
		catalogue = new Country[DEFAULT_SIZE];
		continentMap = new HashMap<String, String>();
		
		continents = new HashSet<String>();
		
		numCountries = 0;
		
		ThingToReadFile iFile = new ThingToReadFile(continentFileName);
		iFile.readLine();	//reads header
		
		while(!iFile.endOfFile()){
			String line = iFile.readLine();
			String[] splitLine = line.split(",");
			continentMap.put(splitLine[0], splitLine[1]);
			continents.add(splitLine[1]);
		}
		iFile.close();

		iFile = new ThingToReadFile(countriesFileName);
		iFile.readLine();
		while(!iFile.endOfFile()){
			String line = iFile.readLine();
			String[] splitLine = line.split(",");
			Country cntry = new Country(splitLine[0], Integer.parseInt(splitLine[1]), 
					Double.parseDouble(splitLine[2]), continentMap.get(splitLine[0]));
			this.addCatalogue(cntry);
		}
		iFile.close();

	}

	public Country getCountry(int index){
		if(index >= 0 && index < numCountries){
			return catalogue[index];
		}
		else{
			//System.out.println("Sorry");
			return null;
		}
	}

	private void addCatalogue(Country cntry) {
		// if array is not big enough, double its capacity automatically
		if(numCountries == catalogue.length)
			expandCapacity();
		// add part at next free location in array
		catalogue[numCountries] = cntry; 
		numCountries++; 
	}
	
	public void addCountry(Country cntry){
		addCatalogue(cntry);
		continents.add(cntry.getContinent());
		continentMap.put(cntry.getName(), cntry.getContinent());
	}

	public boolean addCountry(String cntryName, int pop, double area, String continent) {
		if (this.findCountry(cntryName)==null) {
			Country newCntry = new Country(cntryName, pop, area, continent);
			this.addCountry(newCntry);
			return true;
		}
		//there is already a country with this name
		else
			return false;
	}

	private void expandCapacity() {
		Country[] largerCatalogue = new Country[catalogue.length*2];
		for(int i=0;i<catalogue.length;i++){
			largerCatalogue[i] = catalogue[i];
		}
		catalogue = largerCatalogue;
	}

	//How to iterate through dictionary
	public void filterCountriesByContinent(String continent){
		System.out.println("Countries in " + continent + ":");
		for (String key : continentMap.keySet()){
			if (continentMap.get(key).equals(continent)){
				System.out.println(key);
			}
		}
	}

	public void printCountryCatalogue(){
		System.out.println("Country Catalogue:");
		for(int i = 0 ; i < numCountries ; i++){
			System.out.println(catalogue[i]);
		}
	}

	public Country findCountry(String cName) {
		int index = searchCatalogue(cName);

		if (index == NOT_FOUND)
			return null;
		return catalogue[index];

	}

	//returns location if found
	public int searchCatalogue(String cName) {
		int search = NOT_FOUND;
		// search the list for the specified country
		for(int i=0;i<numCountries && search == NOT_FOUND;i ++)
			if(catalogue[i].getName().equals(cName)) search = i;


		if(search==NOT_FOUND) {
			System.out.println("Target country not in catalogue.\n");
			return NOT_FOUND;
		}
		// return country object index 
		return search;
	}

	public void deleteCountry(String cName){

		int index = searchCatalogue(cName);

		if(index != NOT_FOUND){
			for(int i = index; i < numCountries - 1 ; i++){
				catalogue[i] = catalogue[i+1];
			}
			numCountries--;
			System.out.println("Country \"" + cName + "\" removed sucessfully.\n");
		}
		else{
			System.out.println("Country \"" + cName + "\" could NOT be removed.\n");
		}
	}

	public boolean setPopulationOfACountry(String cName, int pop){

		int index = searchCatalogue(cName);

		if(index != NOT_FOUND){
			catalogue[index].setPopulation(pop);
			//System.out.println("Country \"" + cName + "\"\'s population changed sucessfully.");
			return true;
		}
		else{
			return false;
			//System.out.println("Country \"" + cName + "\"\'s population could not be changed.");
		}
	}
	
	private void writeToFile(Country c, ThingToWriteFile oFile){
		oFile.writeLine(c.getName() + "|" + c.getContinent() + "|" + c.getPopulation() + "|" + c.getPopDensity() + "\n");
	}

	public int saveCountryCatalogue(String fName){
		ThingToWriteFile oFile = new ThingToWriteFile(fName);
		for(int i = 0 ; i < this.numCountries ; i++){
			writeToFile(this.catalogue[i], oFile);
		}
		oFile.close();
		return this.numCountries;
	}

	public String findCountryWithLargestPop(){
		int index = -1;
		int largePop = 0;
		for(int i = 0 ; i < numCountries ; i++){
			if(catalogue[i].getPopulation() > largePop){
				largePop = catalogue[i].getPopulation();
				index = i;
			}
		}
		return catalogue[index].getName();
	}

	public String findCountryWithSmallestArea(){
		int index = -1;
		double smallArea = Double.MAX_VALUE;
		for(int i = 0 ; i < numCountries ; i++){
			if(catalogue[i].getArea() < smallArea){
				smallArea = catalogue[i].getArea();
				index = i;
			}
		}
		return catalogue[index].getName();
	}

	public void filterCountriesByPopDensity(int lowerBound, int upperBound){
		System.out.println("Countries with a population density between " + lowerBound + " and " + upperBound + ":");
		for(int i = 0 ; i < numCountries ; i++){
			if (catalogue[i].getPopDensity() >= lowerBound && catalogue[i].getPopDensity() <= upperBound){
				System.out.println("\t" + catalogue[i].toString() + "\thas a population density of " + catalogue[i].getPopDensity() + ".\n");
			}
		}
	}

	public void printMostPopulousContinent(){
		Map<String, Integer> tCont = new HashMap<String, Integer>();
		for (String s : continents){
			tCont.put(s, 0);
		}
		for (int i = 0 ; i < numCountries ; i++){
			tCont.put(catalogue[i].getContinent(), catalogue[i].getPopulation() + tCont.get(catalogue[i].getContinent()));
		}
		//for (String s : continents){
		//	System.out.println(s + " " + tCont.get(s));
		//}
		String key = "";
		int largePop = 0;
		for (String s : tCont.keySet()){
			if(tCont.get(s) > largePop){
				largePop = tCont.get(s);
				key = s;
			}
		}
		System.out.println("The most populous continent is " + key + ", at a population of " + largePop + ".");
	}
}
