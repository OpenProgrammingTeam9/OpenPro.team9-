package OpenSourceTeam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BrickTest brick = new BrickTest();
		JFrame game = new JFrame();
		JPanel below = new JPanel();
		//JScrollPane temp = new JScrollPane();
		//temp.add(brick);
		game.setSize(520, 1000);
		game.setLayout(new BorderLayout());
		game.setTitle("BreakMap");	
		game.add(below, BorderLayout.SOUTH);
		game.add(brick, BorderLayout.CENTER);
		game.setResizable(true);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
