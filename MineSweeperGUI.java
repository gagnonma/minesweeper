package project2;


import javax.swing.*;

/**
 * Sets up the GUI for the Minesweeper game
 *
 * @author Max Gagnon and Shane Watrous
 */

public class MineSweeperGUI {
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Mine Sweeper!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);
		frame.setSize(1000, 500);
		frame.setVisible(true);
	}
}

