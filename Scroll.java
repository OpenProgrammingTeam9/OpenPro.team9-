package Demo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Scroll extends JFrame implements KeyListener, ActionListener{
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 1000;
	
	private float ballRadius = 20;
	private float ballX = ballRadius + 120;
	private float ballY = ballRadius + 80;
	private float ballSpeedy = 10;
	
	JScrollPane scrollPan;
	
	public Scroll() {
		super("BALL BOUNCE GAME");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		JPanel background = new JPanel();
		background.setPreferredSize(new Dimension(400, 2000));
		background.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.YELLOW);
		background.add(topPanel, BorderLayout.NORTH);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);
		background.add(bottomPanel, BorderLayout.SOUTH);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setPreferredSize(new Dimension(20, 1000));
		scrollBar.setValue(100);
		//add(scrollBar, BorderLayout.EAST);
		
		scrollPan = new JScrollPane();
		scrollPan.setViewportView(background);
		//scrollPan.add(scrollBar);
		/*
		JScrollBar scrollBar = scrollPan.getVerticalScrollBar();
		int value = scrollBar.getMaximum();
		*/
		scrollPan.getVerticalScrollBar().setLocation(new Point(0, 0));
		
		
		add(scrollPan, BorderLayout.CENTER);
		
		scrollPan.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			public void adjustmentValueChanged(AdjustmentEvent e) {
				System.out.println("Value of vertical scroll bar: " + e.getValue());
			}
		});
		
	}
	/*
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		//g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT); // 직사각형
		g.setColor(Color.RED); // 빨간색으로 채움
		g.fillOval((int) (ballX - ballRadius), (int) (ballY - ballRadius),
				(int) (2 * ballRadius), (int) (2 * ballRadius));
	}
	*/
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			System.out.println("ok");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCmd = e.getActionCommand();
		/*
		if(e.equals("ok")) {
			scrollBar.setAlignmentX(1000);
			scrollBar.setAlignmentY(3000);
			scrollBar.set
		}
		*/
			
	}
}
