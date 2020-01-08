/**
 * Class that represents a country with attributes name, population, area,
 * continent
 * 
 * @author Sofya Pryadko
 *
 */

public class Country {

	/* Attribute declarations */
	private String name; // a country's name
	private int population; // number of people living in a country
	private double area; // a country's area
	private String continent; // the continent on which a country is located

	/**
	 * Constructor initializes the country's name, population, area and continent
	 */
	public Country(String name, int population, double area, String continent) {
		this.name = name;
		this.population = population;
		this.area = area;
		this.continent = continent;
	}

	/**
	 * getName method returns the country's name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getPopulation method returns the country's population
	 * 
	 * @return population
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * getArea method returns the country's area
	 * 
	 * @return area
	 */
	public double getArea() {
		return area;
	}

	/**
	 * getContinent method returns the country's continent
	 * 
	 * @return continent
	 */
	public String getContinent() {
		return continent;
	}

	/**
	 * getPopDensity method returns the country's population density
	 * 
	 * @return population density
	 */
	public double getPopDensity() {
		return ((double) population) / area;
	}

	/**
	 * setPopulation method sets the country's population number
	 * 
	 * @param population
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/**
	 * toString method returns a string representation of the "Name in Continent"
	 * 
	 * @return string with the county's name "in" the continent name
	 */
	public String toString() {
		String s = name + " in " + continent;
		return s;
	}

}
