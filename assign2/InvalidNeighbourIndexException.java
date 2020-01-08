/**
 * @author Sofya Pryadko
 * Represents the situation in which an entity requests a neighbour that
 * is not 0-5 inclusive.
 */

public class InvalidNeighbourIndexException extends ArrayIndexOutOfBoundsException {
	/**
	 * Sets up this exception with an appropriate message.
	 * @param int i representing the index of the neighbour
	 */
	public InvalidNeighbourIndexException(int i) {
		super("The neighbour's index " + i + " is not 0-5 inclusive.");
	}
}
