package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener {

	/*
	 * Attributes
	 */

	private int numRows;
	private int numCols = -1;
	private int numRooms = 0;

	private static Board INSTANCE = new Board();
	private String layout;
	private String setup;
	private List<List<BoardCell>> board;
	private Map<Character, Room> roomMap;

	private Set<BoardCell> targets;
	private ArrayList<BoardCell> doorways;

	private Set<Card> deck;
	private Set<Card> playerCards;
	private Set<Card> weaponCards;
	private Set<Card> roomCards;
	private Solution trueSolution;

	private ArrayList<Player> players;
	private HumanPlayer human;

	private int currentPlayerTurn = 0;

	private int cellWidth;
	private int cellHeight;

	private int currRoll;

	/*
	 * Constructor
	 */

	private Board() {
		super();
		this.board = new ArrayList<List<BoardCell>>();

		this.players = new ArrayList<Player>();

		// Card sets
		this.deck = new HashSet<Card>();
		this.playerCards = new HashSet<Card>();
		this.weaponCards = new HashSet<Card>();
		this.roomCards = new HashSet<Card>();
	}

	public static Board getInstance() {
		return INSTANCE;
	}

	/*
	 * Public Methods
	 */

	public void initialize() {
		addMouseListener(this);

		INSTANCE = new Board();
		this.board = new ArrayList<List<BoardCell>>();
		this.roomMap = new HashMap<Character, Room>();

		numCols = -1;
		numRooms = 0;

		try {
			this.loadSetupConfig();
		} catch (FileNotFoundException error) {
			System.out.println("File Not Found");

		} catch (BadConfigFormatException error) {
			System.out.println("Bad Config: Invalid Character in Setup");
			error.printStackTrace();
		}

		try {
			this.loadLayoutConfig();
		} catch (FileNotFoundException error) {
			System.out.println("File Not Found");

		} catch (BadConfigFormatException error) {
			System.out.println("Bad Config: Invalid Character in Layout");
			error.printStackTrace();
		}
		// initialize the adjacency list
		this.initializeAdjacencies();

		// Assign initial unique positions to each player
		this.assignPlayerPositions();

		// Assign initial unique colors to each player. Later put colors in setup
		// config.
		// this.assignPlayerColors();

		// NOW do the first roll for the player and calculate the first set of targets

		currRoll = this.getRoll();
		calcTargets(human.getLocation(), currRoll);

	}

	public void setConfigFiles(String layout, String setup) {
		this.layout = layout;
		this.setup = setup;
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		this.roomMap = new HashMap();
		File set = new File("data/" + this.setup);
		Scanner reader = new Scanner(set);
		while (reader.hasNextLine()) {
			String data = reader.nextLine();
			if (!data.substring(0, 2).equals("//") && !data.equals("\n")) {

				String[] splitData = data.split(", ");
				Room r;
				Card card;

				// if it's a room
				if (splitData[0].equals("Room")) {
					this.numRooms++;
					r = new Room(splitData[1]);
					r.setIsRoom(true);
					this.roomMap.put(splitData[2].charAt(0), r);

					// create and add the room card
					card = new Card(splitData[1], CardType.ROOM);
					this.roomCards.add(card);

					// Add the card that represents the room
					r.setRoomCard(card);

					// if it's a space
				} else if (splitData[0].equals("Space")) {
					r = new Room(splitData[1]);
					this.roomMap.put(splitData[2].charAt(0), r);

					// if it's a weapon
				} else if (splitData[0].equals("Weapon")) {
					// create and add the weapon card
					card = new Card(splitData[1], CardType.WEAPON);
					this.weaponCards.add(card);

					// if it's a Person
				} else if (splitData[0].equals("Person")) {
					Player player;
					// if it is the first player, it is a human
					// TODO: Assign unique colors and starting locations

					int x = Integer.valueOf(splitData[3]);
					int y = Integer.valueOf(splitData[4]);

					String[] rgb = splitData[5].strip().split(" ");

					Color c = new Color(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2]), 255);

					if (this.players.size() == 0) {

						human = new HumanPlayer(splitData[1], Board.INSTANCE, new BoardCell(x, y), c);
						player = human;

						this.currentPlayerTurn = 0;

						// otherwise its a computer
					} else {
						player = new ComputerPlayer(splitData[1], Board.INSTANCE, new BoardCell(x, y), c);
					}
					// set the player number
					player.setPlayerNum(this.players.size());
					// add the player

					players.add(player);

					card = new Card(splitData[1], CardType.PERSON);
					this.playerCards.add(card);

					// Else there's something wrong with the setup file
				} else {
					throw new BadConfigFormatException();
				}
			}
		}
		reader.close();
	}

	/*
	 * Load the playing board.
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

		File lay = new File("data/" + this.layout);
		Scanner reader = new Scanner(lay);

		this.doorways = new ArrayList<BoardCell>();

		int rowIndex = 0;
		int cols = 0;

		// Iterate through each row of the csv and thus each row of the board
		while (reader.hasNextLine()) {

			// Seperate out the columns delimeted by a csv
			String data = reader.nextLine();
			String[] splitData = data.split(",");

			cols = splitData.length;

			// If the number of Columns hasn't been set
			if (this.numCols == -1) {
				this.numCols = cols;
			} else if (cols != this.numCols) {
				// if the number of columns varies, then we have a bad configuration
				throw new BadConfigFormatException();
			}

			// create the array for the row
			ArrayList<BoardCell> row = new ArrayList<BoardCell>(cols);

			// iterate through the split data
			int colIndex = 0;
			for (String cell : splitData) {
				BoardCell currentCell = new BoardCell(rowIndex, colIndex);
				Room room;
				colIndex++;

				// if the room label is not valid throw an Exception
				if (!roomMap.containsKey(cell.charAt(0))) {
					throw new BadConfigFormatException();
				} else {
					// Get the room
					room = this.roomMap.get(cell.charAt(0));

				}

				// If the cell is a normal cell, just a label
				if (cell.length() == 1) {
					currentCell.setCellLabel(cell.charAt(0));

					// if the cell has some special operation
				} else if (cell.length() == 2) {
					currentCell.setCellLabel(cell.charAt(0));
					char specialOperation = cell.charAt(1);

					DoorDirection direction;

					// set the door direction
					if (specialOperation == '^') {
						direction = DoorDirection.UP;
						currentCell.setDoorDirection(direction);

					} else if (specialOperation == '>') {
						direction = DoorDirection.RIGHT;
						currentCell.setDoorDirection(direction);

					} else if (specialOperation == '<') {
						direction = DoorDirection.LEFT;
						currentCell.setDoorDirection(direction);

					} else if (specialOperation == 'v') {
						direction = DoorDirection.DOWN;
						currentCell.setDoorDirection(direction);

						// set the room label
					} else if (specialOperation == '#') {
						currentCell.setRoomLabel(true);
						// set the cell that cooresponds to the label location
						room.setLabelCell(currentCell);

						// set the room center
					} else if (specialOperation == '*') {
						currentCell.setRoomCenter(true);
						// Set the cell that corresponds to the roomcenter
						room.setCenter(currentCell);

					} else if (roomMap.containsKey(specialOperation)) {
						// else if the value is another room, then it's a secret passage
						currentCell.setSecretPassage(specialOperation);
					} else {
						// Else there is an error and it's a bad config
						throw new BadConfigFormatException();
					}
				}
				row.add(currentCell);

				if (currentCell.isDoorway()) {
					this.doorways.add(currentCell);
				}

			}

			// add the row
			this.getBoard().add(row);

			// increment the row
			rowIndex++;

		}
		// Close the file
		reader.close();
		// Set the number of rows
		this.numRows = rowIndex;
	}

	// Assign initial unique positions to all players
	private void assignPlayerPositions() {
		BoardCell swap;

		for (Player player : this.players) {
			swap = player.getLocation();
			player.setLocation(board.get(swap.getRow()).get(swap.getCol()));
			player.getLocation().setOccupied(true);
		}
	}

	public boolean checkAccusation(Solution accusation) {
		return accusation.equals(this.trueSolution);

	}

	public Card handleSuggestion(Solution suggestion, Player suggestor) {
		Player player;
		int i = this.players.indexOf(suggestor);
		Card result = null;

		do {
			i++;

			if (i >= this.players.size()) {
				i = 0;
			}

			player = this.players.get(i);

			result = player.disproveSuggestion(suggestion, suggestor);
			if (result != null) {
				break;
			}

		} while (player != suggestor);

		return result;

	}

	public void calcTargets(BoardCell startCell, int pathlength) {
		targets = new HashSet<BoardCell>();
		this.calcTargets(startCell, pathlength, pathlength, new HashSet<BoardCell>());
	}

	public void deal() {
		// Create a deck
		this.deck.addAll(this.playerCards);
		this.deck.addAll(this.weaponCards);
		this.deck.addAll(this.roomCards);

		// Select the cards for the solution
		Card perpetrator = getRandomCard(this.playerCards);
		Card weapon = getRandomCard(this.weaponCards);
		Card place = getRandomCard(this.roomCards);

		// Create the solution
		this.trueSolution = new Solution(perpetrator, weapon, place);

		// Create the deck to deal from
		Set<Card> dealDeck = new HashSet<Card>();
		// Add all the values from deck to it
		dealDeck.addAll(deck);

		// remove the solution from the deck to be dealt
		dealDeck.remove(perpetrator);
		dealDeck.remove(weapon);
		dealDeck.remove(place);

		Card choiceCard;

		// Deal the deck
		while (!dealDeck.isEmpty()) {
			// iterate through the payers
			for (Player player : this.players) {
				// select a random card
				choiceCard = getRandomCard(dealDeck);
				// give the card to the player
				player.updateHand(choiceCard);
				// remove the card from the deck
				dealDeck.remove(choiceCard);
				// if the deck is empty, break
				if (dealDeck.size() == 0) {
					break;
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Get the size of the Board Panel
		Dimension boardSize = this.getSize();

		// Set the cell dimensions based off of this, the division will naturally round
		// down
		cellHeight = (int) boardSize.getHeight() / this.getNumRows();
		cellWidth = (int) boardSize.getWidth() / this.getNumColumns();
		// Set the cell dimension

		ArrayList<BoardCell> doorways = new ArrayList<BoardCell>();

		// Draw the board cells
		for (List<BoardCell> row : this.board) {
			for (BoardCell cell : row) {

				cell.draw(g, cellWidth, cellHeight);
				// if the cell is a door, save it for later
				if (cell.isDoorway()) {
					doorways.add(cell);
				}

			}

		}
		// if it is the human player
		if (players.get(currentPlayerTurn).equals(human)) {

			// if he human has not moved
			if (!human.getHasMoved()) {
				for (BoardCell cell : targets) {
					cell.drawTarget(g, cellWidth, cellHeight);
				}
			}

		}

		// Draw the Room Labels
		for (Map.Entry<Character, Room> entry : this.roomMap.entrySet()) {
			Room room = entry.getValue();
			room.draw(g, cellWidth, cellHeight);
		}
		// Draw the doors
		for (BoardCell door : doorways) {
			door.drawDoorway(g, cellWidth, cellHeight);

		}

		// Paint the players
		for (Player player : players) {
			player.drawPlayer(g, cellWidth, cellHeight);
		}

	}

	/*
	 * Private Methods
	 */
	private void initializeAdjacencies() {

		ArrayList<BoardCell> secretPaths = new ArrayList<BoardCell>();

		// iterate through the gameboard
		for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
			for (int colIndex = 0; colIndex < this.numCols; colIndex++) {

				// get the boardcell
				BoardCell currentCell = this.getBoard().get(rowIndex).get(colIndex);

				Room room = this.roomMap.get(currentCell.getCellLabel());

				// If the cell is not 'Unused' then it needs adjacencies.
				if (currentCell.getCellLabel() != 'X') {

					// if the room is the center, set it
					if (currentCell.getRoomCenter()) {
						room.setCenter(currentCell);
					} else if (currentCell.getRoomLabel()) { // if the room is the label, set it, room labels have no
																// adjacencies
						room.setLabelCell(currentCell);
					} else if (currentCell.getSecretPassage() != '\0') {
						secretPaths.add(currentCell);
					} else {

						// Check bounds for "normal cells"
						if (rowIndex - 1 >= 0) {
							BoardCell adj = this.getBoard().get(rowIndex - 1).get(colIndex);
							this.processCell(currentCell, adj);
						}

						if (rowIndex + 1 < this.numRows) {
							BoardCell adj = this.getBoard().get(rowIndex + 1).get(colIndex);
							this.processCell(currentCell, adj);
						}

						if (colIndex - 1 >= 0) {
							BoardCell adj = this.getBoard().get(rowIndex).get(colIndex - 1);
							this.processCell(currentCell, adj);
						}

						if (colIndex + 1 < this.numCols) {
							BoardCell adj = this.getBoard().get(rowIndex).get(colIndex + 1);
							this.processCell(currentCell, adj);
						}
					}
				}
			}
		}

		addSecretPassageAdjacencies(secretPaths);
	}

	private void addSecretPassageAdjacencies(ArrayList<BoardCell> secretPaths) {
		BoardCell centerCell;
		BoardCell secretCell;

		// Add secret cell adjacencies from secretPaths list
		for (BoardCell sp : secretPaths) {
			centerCell = roomMap.get(sp.getCellLabel()).getCenterCell();
			secretCell = roomMap.get(sp.getSecretPassage()).getCenterCell();
			centerCell.addAdjacency(secretCell);
			secretCell.addAdjacency(centerCell);
		}

		// Code for adding adjacencies from room center, secret path done at end.
		for (BoardCell doorway : this.doorways) {

			BoardCell checkedCell = new BoardCell(-1, -1);

			if (doorway.getDoorDirection() == DoorDirection.UP) {
				checkedCell = this.getBoard().get(doorway.getRow() - 1).get(doorway.getCol());
			}

			else if (doorway.getDoorDirection() == DoorDirection.DOWN) {
				checkedCell = this.getBoard().get(doorway.getRow() + 1).get(doorway.getCol());
			}

			else if (doorway.getDoorDirection() == DoorDirection.LEFT) {
				checkedCell = this.getBoard().get(doorway.getRow()).get(doorway.getCol() - 1);
			}

			else if (doorway.getDoorDirection() == DoorDirection.RIGHT) {
				checkedCell = this.getBoard().get(doorway.getRow()).get(doorway.getCol() + 1);
			}

			BoardCell roomCell = roomMap.get(checkedCell.getCellLabel()).getCenterCell();

			if (checkedCell.getCellLabel() == roomCell.getCellLabel()) {
				roomCell.addAdjacency(doorway);
				doorway.addAdjacency(roomCell);
			}
		}
	}

	/*
	 * Helper function for adding cell adjancies.
	 */
	private void processCell(BoardCell t, BoardCell adj) {
		if (adj.getCellLabel() != 'X' && !roomMap.get(adj.getCellLabel()).getIsRoom()) {
			t.addAdjacency(adj);
		}
	}

	/*
	 * Caller function for calcTargets(BoardCell startCell, int pathlength, int
	 * maxpath, Set<BoardCell> visited), but this one is shorter and more human
	 * readable.
	 */

	/*
	 * Calculate all the targets and add them to the adjacency list, this method is
	 * private because it is called by the public one above which is used as a
	 * "setup" function.
	 */
	private void calcTargets(BoardCell startCell, int pathlength, int maxpath, Set<BoardCell> visited) {
		if (visited.isEmpty()) {
			visited.add(startCell);
		}

		if (pathlength == 0 || (startCell.isRoomCenter() && maxpath != pathlength)) {
			targets.add(startCell);

		} else {
			for (BoardCell cell : startCell.getAdjList()) {
				if ((cell.isRoomCenter() || !cell.getOccupied()) && !visited.contains(cell)) {
					visited.add(cell);
					calcTargets(cell, pathlength - 1, maxpath, visited);
					visited.remove(cell);
				}
			}
		}
	}

	// Choses a random card from the deck
	private Card getRandomCard(Set<Card> cards) {
		Random rand = new Random();
		// set the initial to zero
		int i = 0;
		// Choose a random index in the size of the deck
		int randIdx = rand.nextInt(cards.size());
		Card randCard = null;
		// Iterate through the deck set
		for (Card card : cards) {
			// if the index is the random index, remove the card
			if (i == randIdx) {
				randCard = card;
				break;
			}
			// increment the index
			++i;
		}
		return randCard;
	}

	// TODO: refactor these
	public int getRoll() {
		Random rand = new Random(System.currentTimeMillis());
		return rand.nextInt(6) + 1;
	}

	// I used this to set the initial roll in the ControlPanel
	public int getCurrRoll() {
		return currRoll;

	}
	/*
	 *
	 * Getters and Setters
	 * 
	 */

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Set<Card> getDeck() {
		return deck;
	}

	public Solution getSolution() {
		return trueSolution;
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	/*
	 * Get the number of rows in the file
	 */
	public int getNumRows() {
		return this.numRows;
	}

	/*
	 * Get the number of columns in the board
	 */
	public int getNumColumns() {
		return this.numCols;
	}

	/*
	 * get the number of rooms in the layout
	 */
	public int getNumRooms() {
		return this.numRooms;
	}

	/*
	 * get a specific cell specified by (row, column)
	 */
	public BoardCell getCell(int row, int col) {
		return getBoard().get(row).get(col);
	}

	/*
	 * Get the room label of a cell
	 */
	public Room getRoom(BoardCell cell) {
		char label = cell.getCellLabel();
		return this.roomMap.get(label);
	}

	/*
	 * Return the adjacency list of the boardCell at (i, j)
	 */
	public Set<BoardCell> getAdjList(int i, int j) {
		return getBoard().get(i).get(j).getAdjList();
	}

	/*
	 * get the targets stored in the Set of targets
	 */
	public Set<BoardCell> getTargets() {
		return this.targets;
	}

	public Set<Card> getPlayerCards() {
		return this.playerCards;
	}

	public Set<Card> getRoomCards() {
		return this.roomCards;
	}

	public Set<Card> getWeaponCards() {
		return this.weaponCards;
	}

	public List<List<BoardCell>> getBoard() {
		return board;
	}

	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	public HumanPlayer getHumanPlayer() {
		return this.human;
	}

	public int getCurrentPlayerTurn() {
		return this.currentPlayerTurn;
	}
	// Nextturn, updates the gamestate, given a roll

	public void nextTurn(int roll) {

		currRoll = roll;

		// Since the current player's turn is over make it so they can move again
		players.get(currentPlayerTurn).setHasMoved(false);

		// update the currentPlayer
		currentPlayerTurn++;
		// Modulo by the size to create the looping back to begenning of list

		currentPlayerTurn = currentPlayerTurn % players.size();

		// If it is the human player's turn
		if (players.get(currentPlayerTurn) == this.human) {
			// Calculate the targets, they will be drawn in paintComponent()
			calcTargets(this.human.getLocation(), roll);

		} else {
			// Otherwise calculate the computer's move and move them
			ComputerPlayer cumpeepee = (ComputerPlayer) players.get(currentPlayerTurn);
			calcTargets(cumpeepee.getLocation(), roll);
			cumpeepee.move(cumpeepee.selectTarget(targets));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// If it is the human Player's turn

		if (players.get(currentPlayerTurn).equals(human)) {
			boolean found = false;

			// if the human has already moved
			if (!human.getHasMoved()) {

				// iterate through the targets
				for (BoardCell target : targets) {
					// if the target has the click
					if (target.containsClick(e.getX(), e.getY(), cellWidth, cellHeight)) {
						// move the player
						found = true;
						human.move(target);
						break;

					}

				}
				// if there is a move, revalidate and repaint
				if (found) {
					this.revalidate();
					this.repaint();
				} else {
					JOptionPane wait = new JOptionPane();

					wait.showMessageDialog(new JFrame(), "Invalid Choice: Please select a highlighted tile",
							"Invalid Choice", JOptionPane.WARNING_MESSAGE);
				}

			}

		} else {
			JOptionPane wait = new JOptionPane();

			wait.showMessageDialog(new JFrame(), "It's not your turn, be patient!", "Patience is a Virtue",
					JOptionPane.WARNING_MESSAGE);

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
