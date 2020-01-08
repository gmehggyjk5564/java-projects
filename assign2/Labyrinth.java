import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The Labyrinth class creates a window that shows a hexagon-tile based Labyrinth.<p>
 * 
 * The Labyrinth is built from a file with the following specifications:<p>
 * <ul>
 * <li>The first line has the number of rows and cols</li>
 * 
 * <li>Each subsequent line (there will be the same number of lines as rows)</li>
 * 
 * <i>(Note: because this Labyrinth is based on hexagons, each alternating row is 
 *        offset from the left side by half a hexagon, indicated by a space in the input file)</i>
 *        </ul>
 * 
 * @author CS1027
 *
 */
public class Labyrinth { 
	private static final long serialVersionUID = 1L;


	//Characters used by the input and output files
	private static final char UNVISITED_CHAR = 'U';
	private static final char TREASURE_CHAR = 'T';
	private static final char END_CHAR = 'E';
	private static final char START_CHAR = 'S';
	private static final char WALL_CHAR = 'W';
	private static final char END_PROCESSED_CHAR = 'G';
	private static final char PROCESSED_CHAR = 'P';
	private static final char PUSHED_CHAR = 'H';
	private static final char BLANK_CHAR = 'B';
	

	private static final String MY_TITLE = "Labyrinth";

	//Default time delay when repainting the Labyrinth to reflect hexagon changes
	public static final int DEFAULT_TIME_DELAY = 50;
	private int timeDelay = DEFAULT_TIME_DELAY;
	protected Hexagon start;
	private Hexagon[][] hexLabyrinth;

	/**
	 * Constructor to build a Graphical Labyrinth with hexagonal tiles
	 * from a file containing a Labyrinth specification
	 * @param inFile
	 * @throws UnknownLabyrinthCharacterException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Labyrinth(String inFile) throws UnknownLabyrinthCharacterException, FileNotFoundException, IOException{

		// set up GUI aspects of the Labyrinth component
		/**super("Labyrinth");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel();
*/
		// set up the file reader and read the first line
		BufferedReader in;
		String line="";
		in = new BufferedReader(new FileReader(inFile));
		line = in.readLine();

		// Tokenize the first line to get the row and column
		StringTokenizer lineTokens = new StringTokenizer(line);
		// First line is the number of rows then the number of columns
		int row = Integer.parseInt(lineTokens.nextToken());
		int col = Integer.parseInt(lineTokens.nextToken());


		// The hexagons form a linked structure, and we don't 
		// use the 2D array for anything after building the labyrinth
		// .....except to output the result to a file

		hexLabyrinth = new Hexagon[row+2][col+2];	

		// HexLayout will arrange the Hexagons in the window
//		p.setLayout(new HexLayout(row, col, 4));

		// for each row
		for (int r = 1; r<row+1; r++){
			line = in.readLine();
			lineTokens = new StringTokenizer(line);
			// for each token on the line (col in the Labyrinth)
			for(int c = 1; c< col+1; c++){

				// read the token and generate the hexagon type
				char token = lineTokens.nextToken().charAt(0);
				switch(token){
				case WALL_CHAR:
					hexLabyrinth[r][c] = new Hexagon(Hexagon.HexType.WALL);
					break;
				case START_CHAR:
					hexLabyrinth[r][c] = new Hexagon(Hexagon.HexType.START);
					this.start = hexLabyrinth[r][c];
					break;
				case END_CHAR:
					hexLabyrinth[r][c] = new Hexagon(Hexagon.HexType.END);
					break;
				case TREASURE_CHAR:
					hexLabyrinth[r][c] = new Hexagon(Hexagon.HexType.TREASURE);
					break;
				case UNVISITED_CHAR:
					hexLabyrinth[r][c] = new Hexagon(Hexagon.HexType.UNVISITED);
					break;
				case BLANK_CHAR:
					hexLabyrinth[r][c] = new Hexagon(Hexagon.HexType.BLANK);
					break;
				default:
					// cannot build correct Labyrinth
					throw new UnknownLabyrinthCharacterException(token);
				}

				// add to the GUI layout
//				p.add(hexLabyrinth[r][c]);
			}// end for cols
		}// end for rows

		//go through the 2D matrix again and build the neighbors
		int offset = 0;
		for(int r=1;r<row+1;r++){
			for(int c=1;c<col+1;c++){
				// on even rows(inset from left side) need to add one to the upper and lower neighbors
				// on odd, do not add anything (offset should be 0)
				offset = 1 - r%2;

				// set the neighbors for this hexagon in the builder
				hexLabyrinth[r][c].setNeighbour(hexLabyrinth[r-1][c+offset], 0);
				hexLabyrinth[r][c].setNeighbour(hexLabyrinth[r][c+1], 1);
				hexLabyrinth[r][c].setNeighbour(hexLabyrinth[r+1][c+offset], 2);
				hexLabyrinth[r][c].setNeighbour(hexLabyrinth[r+1][c-1+offset], 3);
				hexLabyrinth[r][c].setNeighbour(hexLabyrinth[r][c-1], 4);
				hexLabyrinth[r][c].setNeighbour(hexLabyrinth[r-1][c-1+offset], 5);
			} // end for cols
		} // end for rows

		//close the file
		in.close();

		//set up the GUI window
//		this.add(p);/
//		this.pack();
//		this.setSize(40*row,40*col );
//		this.setVisible(true);
	}

	/**
	 * Method will return a reference to the hexagon that is the
	 * start of the Labyrinth.
	 * @return A reference to the hexagon that is the start of the Labyrinth
	 */
	public Hexagon getStart() {
		return this.start;
	}

	/**
	 * Get the current time delayed used when repainting the Labyrinth to reflect changes
	 * made to the hexagon tiles
	 * @return the timeDelay
	 */
	public int getTimeDelay() {
		return timeDelay;
	}

	/**
	 * Set the amount of time to wait when repainting the Labyrinth to reflect changes 
	 * made to the hexagon tiles
	 * @param timeDelay the timeDelay to set
	 */
	public void setTimeDelay(int timeDelay) {
		this.timeDelay = timeDelay;
	}

	public void repaint() {
		/*try {
			Thread.sleep(this.timeDelay);
		}catch(Exception e){ 
			System.err.println("Error while issuing time delay\n" + e.getMessage());
		}
		super.repaint();
		*/
	}

	/**
	 * Goes through the current state of the labyrinth, and prints the results to 
	 * a file specified in the parameter
	 * 
	 * @param outFile The name of the file to save the labyrinth to
	 * @throws IOException 
	 */
	public void saveLabyrith(String outFile) throws FileNotFoundException, IOException {
		// set up the file writer and read the first line
		BufferedWriter out;
		String line="";
		out = new BufferedWriter(new FileWriter(outFile));

		// for each row
		for (int r = 1; r<this.hexLabyrinth.length; r++){
			line = "";
			//on even row we inset by a space
			if(r%2 == 0)
				line +=" ";
			// for each col
			for(int c = 1; c< this.hexLabyrinth[r].length; c++){
				if (this.hexLabyrinth[r][c] !=null) {
					switch(this.hexLabyrinth[r][c].getHexagonType()) {

					case UNVISITED:
						line += UNVISITED_CHAR;
						break;
					case TREASURE:
						line += TREASURE_CHAR;
						break;
					case END:
						line += END_CHAR;
						break;
					case START:
						line += START_CHAR;
						break;
					case WALL:
						line += WALL_CHAR;
						break;
					case PUSHED:
						line += PUSHED_CHAR;
						break;
					case PROCESSED:
						line += PROCESSED_CHAR;
						break;
					case END_PROCESSED:
						line += END_PROCESSED_CHAR;
						break;

					default:
						line += '?';
					}
					line += ' ';
				}
				
			}
			out.write(line + "\n");
		}
		out.close();

	}
}