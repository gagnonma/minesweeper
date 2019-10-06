package project2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Class that sets up all the components for the
 * panel for the minesweeper game.
 *
 * @author Max Gagnon and Shane Watrous
 *
 */

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton quitButton;
	private Cell iCell;
	private int boardSize = -1;
	private int mineCount = -1;
	private int wins = 0;
	private int losses = 0;
	private JLabel winsLabel, lossLabel;
	private ImageIcon flagIcon, emptyIcon, bombIcon;

	private MineSweeperGame game;  // model

	/**
	 * Constructor that asks for board size and mine count
	 * then sets up the panel
	 */
	public MineSweeperPanel() {
		//ask for board size
		String x = JOptionPane.showInputDialog (null,
				"Enter in the size of the board: ");
		try {
			boardSize = Integer.parseInt(x);
		}catch (Exception e){ }
		while(boardSize < 3 || boardSize > 30){
			x = JOptionPane.showInputDialog (null,
					"Invalid input please enter size between 3 and 30");
			try {
				boardSize = Integer.parseInt(x);
			}catch (Exception e){ }
		}

		//ask for mine count
		String y = JOptionPane.showInputDialog (null,
				"Enter the amount of mines: ");
		try {
			mineCount = Integer.parseInt(y);
		}catch (Exception e){}
		while(mineCount < 0 || mineCount > boardSize*boardSize){
			y = JOptionPane.showInputDialog (null,
					"Ope you entered an invalid number of mines try again");
			try {
				mineCount = Integer.parseInt(y);
			}catch (Exception e){}
		}


		JPanel bottom = new JPanel();
		JPanel center = new JPanel();

		// create game, listeners
		ButtonListener listener = new ButtonListener();

		//Create MouseListener
		MouseListener me = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {


				if(e.getButton() == MouseEvent.BUTTON3){


					for (int r = 0; r < boardSize; r++ ) {
						for (int c = 0; c < boardSize; c++) {
							if(e.getSource() == board[r][c]) {
								iCell = game.getCell(r,c);
							}
						}
					}
					if(iCell.isFlagged()){
						iCell.setFlagged(false);
					}else {
						iCell.setFlagged(true);
					}
					displayBoard();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		};

		game = new MineSweeperGame(boardSize,mineCount);

		//Instantiate the quit button
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		bottom.add(quitButton);

		// create the board
		center.setLayout(new GridLayout(boardSize, boardSize));
		board = new JButton[boardSize][boardSize];

		//create icons
		emptyIcon = new ImageIcon("empty.jpg");
		flagIcon =  new ImageIcon("flag.jpg");
		bombIcon = new ImageIcon("bomb.jpg");

		for (int row = 0; row < boardSize; row++)
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new JButton("",emptyIcon);
				board[row][col].addActionListener(listener);
				board[row][col].addMouseListener(me);

				center.add(board[row][col]);
			}

		displayBoard();

		bottom.setLayout(new GridLayout(boardSize, boardSize));

		// add all to contentPane
		add(new JLabel("MineSweeper"), BorderLayout.NORTH);
		winsLabel = new JLabel("Wins: " + wins);
		add(winsLabel,BorderLayout.EAST);
		lossLabel = new JLabel("Losses: " + losses);
		add(lossLabel, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}


	/**
	 * Displays the board by labeling each cell and
	 * enabling and disabling them.
	 */
	private void displayBoard() {

		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("");
				board[r][c].setIcon(emptyIcon);

				// readable, ifs are verbose
				if (iCell.isFlagged()){
					board[r][c].setIcon(flagIcon);
				}else if (iCell.isMine()) {
					board[r][c].setIcon(bombIcon);
				}

				if (iCell.isExposed()) {
					board[r][c].setEnabled(false);
					if(iCell.getMineCount() != 0) {
						board[r][c].setText("" + iCell.getMineCount());
					}else{
						board[r][c].setText("");

					}
				}else{
					board[r][c].setEnabled(true);
				}
			}
	}


	/**
	 * private Listener class that performs the appropriate
	 * action depending on the status of the game and what
	 * button is pressed.
	 */
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {


			 //Check each cell to see if it was the button pressed
			for (int r = 0; r < boardSize; r++)
				for (int c = 0; c < boardSize; c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

			displayBoard();

			//check if game has been lost
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null,
						"You Lose \n The game will reset");
				//exposeMines = false;
				losses++;
				lossLabel.setText("Losses: " + losses);
				game.reset();
				displayBoard();

			}

			//check if the game has been won
			if (game.getGameStatus() == GameStatus.Won) {
				JOptionPane.showMessageDialog(null,
						"You Win: all mines have been found!\n " +
								"The game will reset");
				wins++;
				winsLabel.setText("Wins: " + wins);
				game.reset();
				displayBoard();
			}

			//check if quit button was pressed
			if(e.getSource() == quitButton){
				System.exit(0);
			}

		}

	}
}



