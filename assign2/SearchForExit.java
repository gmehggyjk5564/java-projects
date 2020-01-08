import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * SearchForExit searches the labyrinth for exit, and reports if you found it at
 * the end of the program.
 * 
 * SearchForExit implements the Labyrinth solving algorithm. It searches for an
 * exit in a Labyrinth made up of hexagonal tiles, using a stack to keep track
 * of tiles yet to be checked. It starts at the beginning and searches through
 * all open spaces one at a time until the exit or end is found.
 * 
 * @author Sofya Pryadko
 */

public class SearchForExit {

	public static void main(String[] args) {

		/* Attribute declarations */
		String labyrinthFile = null; // name of labyrinth file
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
			ArrayStack<Hexagon> hexagonArrayStack = new ArrayStack<Hexagon>();
			hexagonArrayStack.push(hexagon);
			boolean endFound = false; // to keep track of whether we have found the end
			Hexagon currentHexagon = null;
			int stepCount = 0; // The number of steps that it takes to finish a labyrinth
			while (hexagonArrayStack.isEmpty() == false && endFound == false) {
				currentHexagon = hexagonArrayStack.pop(); // pop the top of the stack to get the current hexagon tile
				stepCount++;
				if (currentHexagon.isEnd()) {
					endFound = true;
				} else {
					for (int i = 0; i < 6; i++) { // push neighbours of the current hexagon on the stack
						if (currentHexagon.getNeighbour(i) != null && currentHexagon.getNeighbour(i).isUnvisited()) {
							hexagonArrayStack.push(currentHexagon.getNeighbour(i));
							currentHexagon.getNeighbour(i).setPushed();
						}
					}
				}
				currentHexagon.setProcessed();
				labyrinth.repaint();
			}
			if (endFound == true) {
				System.out.println("The end was found.");
			}

			if (endFound == false) {
				System.out.println("The end was not found.");
			}
			System.out.println("It took " + stepCount + " steps to finish.");
			System.out.println("There still is(are) " + hexagonArrayStack.size() + " tile(s) on the stack.");
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
