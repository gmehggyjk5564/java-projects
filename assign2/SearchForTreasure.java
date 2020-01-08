import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * SearchForTreasure searches the maze for treasure, and reports how much you
 * found at the end of the program.
 * 
 * SearchForTreasure implements the Labyrinth solving algorithm. It searches for
 * an treasure in a Labyrinth made up of hexagonal tiles, using a stack to keep
 * track of tiles yet to be checked. It starts at the beginning and searches
 * through all open spaces one at a time until all the treasure is found.
 * 
 * @author Sofya Pryadko
 */

public class SearchForTreasure {

	public static void main(String[] args) {

		/* Attribute declarations */
		String labyrinthFile = null; // name of labyrinth file
		int numTiles = 0; // number of tiles is the number of open tiles (non walls) in the labyrinth
		int numTreasure = 0; // number of treasure found
		Labyrinth labyrinth = null; // a hexagon-tile based Labyrinth

		try {
			labyrinthFile = args[0]; // To read a single argument
		} catch (Exception e) {
			System.out.println("This file is not working. Please provide a labyrinth file again.");
			System.exit(1);
		}

		// Try to open a new Labyrinth from one of the Labyrinth files provided
		try {
			labyrinth = new Labyrinth(labyrinthFile);
		} catch (FileNotFoundException e) {
			System.out.println("The file " + labyrinthFile + " does not exist.");
		} catch (IOException e) {
			System.out.println("While reading the file " + labyrinthFile + " an error occured.");
		} catch (UnknownLabyrinthCharacterException e) {
			System.out.println("The file " + labyrinthFile + " contains an unknown character.");
		} // handles the exceptions and informs the user through a console print statement
			// what specifically has happened

		if (labyrinth != null) {
			Hexagon hexagon = labyrinth.getStart();
			LinkedStack<Hexagon> hexagonLinkedStack = new LinkedStack<Hexagon>();
			hexagonLinkedStack.push(hexagon);
			Hexagon currentHexagon = null;
			while (hexagonLinkedStack.isEmpty() == false) {
				currentHexagon = hexagonLinkedStack.pop(); // pop the top of the stack to get the current hexagon tile
				numTiles++;
				if (currentHexagon.hasTreasure()) {
					currentHexagon.getTreasure();
					numTreasure++;
				}
				for (int i = 0; i < 6; i++) { // push neighbours of the current hexagon on the stack
					if (currentHexagon.getNeighbour(i) != null && currentHexagon.getNeighbour(i).isUnvisited()) {
						hexagonLinkedStack.push(currentHexagon.getNeighbour(i));
						currentHexagon.getNeighbour(i).setPushed();
					}
				}

				currentHexagon.setProcessed();
				labyrinth.repaint();
			}

			System.out.println("Number of tiles in labyrinth: " + numTiles);
			System.out.println("Amount of treasure found: " + numTreasure);
			// Saves the labyrinth’s finished state to a file called “processed_” + input
			// file name.
			String savedLabyrinthFile = "processed_" + labyrinthFile;
			try {
				labyrinth.saveLabyrith(savedLabyrinthFile);
			} catch (FileNotFoundException e) {
				System.out.println("The file " + savedLabyrinthFile + " does not exist.");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Cannot write into The file: " + savedLabyrinthFile);
				System.exit(0); // handles the exceptions and informs the user through a console print statement
				// what specifically has happened
			}

		}

	}

}
