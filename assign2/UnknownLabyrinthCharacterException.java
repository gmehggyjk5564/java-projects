/**
 * @author Sofya Pryadko
 * Represents the situation in which an unknown character is not recognized
 */

public class UnknownLabyrinthCharacterException extends RuntimeException {
	/**
	 * Sets up this exception with an appropriate message.
	 * @param char c that was used to build a labyrinth
	 */
	public UnknownLabyrinthCharacterException(char c) {
		super("The unknown character" + c + "is not recognized.");
	}
}
