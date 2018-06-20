package OpenSourceTeam;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class BrickTest extends JPanel implements KeyListener, ActionListener{

	private Rectangle[] brick = new Rectangle[10];
	private JScrollPane p;
	private static final int BOX_WIDTH = 800; // ��ü ��
	private static final int BOX_HEIGHT = 900; // ��ü ����
	private float ballRadius = 13; // ���� ������
	private float ballX = 130; // ���� �ʱ� X��ġ
	private float ballY = BOX_HEIGHT - ballRadius; // ���� �ʱ� Y��ġ
	private float ballBoxWidth = 200;
	private float ballBoxHeight = 150;
	private float ballBoxX = 20;
	private float ballBoxY = BOX_HEIGHT - ballBoxHeight;
	private float ballSpeedX = 10;
	private float ballSpeedY = 5; // ���� Y�ӵ�
	private JButton play; // ���۹�ư
	private JButton stop; // ������ư
	private boolean started = false;
	private boolean isPlay = true; // �÷����Ұ�����
	
	public BrickTest() {
		super();
		setSize(800, 900);
		setBackground(new Color(232, 180, 192));
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		/*
		p = new JScrollPane(colorPnl);
		p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		p.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JViewport my = p.getViewport();
		Point a = my.getViewPosition();
		a.x = 150;
		a.y = 200;
		my.setViewPosition(a);
		p.setViewport(my);
		add(p, BorderLayout.CENTER);
		
		JButton resetBtn = new JButton("RESET");
		resetBtn.addActionListener(this);
		add(resetBtn, BorderLayout.SOUTH);
		*/ // former Button and JScrollPane;
		allocateBricks();
		Thread t = new MyThread(); // ������ ��ü ����
		t.start(); // ������ ����
	}
	
	public void allocateBricks() {
		for(int i = 0; i < 10; i++) {
			if(i % 2 != 0) {
				brick[i] = new Rectangle(70, (365 + 50*i), 140, 20);
			}
			else {
				brick[i] = new Rectangle(300, (365 + 50*i), 140, 20);
			}
		}	
	}
	public void paintComponent(Graphics g) {
		g.setColor(new Color(232, 180, 192));
		g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);
		g.setColor(Color.GREEN);
			
		for(int i = 0; i < 10; i++) {
			if(i % 2 == 0) {
			//	brick[i] = new Rectangle(70, (330 + 60*i), 140, 20);
				g.fillRect((int)brick[i].getX(), (int)brick[i].getY(), (int)brick[i].getWidth(), (int)brick[i].getHeight());
			}
			else {
			//	brick[i] = new Rectangle(300, (330 + 60*i), 140, 20);
				g.fillRect((int)brick[i].getX(), (int)brick[i].getY(), (int)brick[i].getWidth(), (int)brick[i].getHeight());
			}
		}		
		g.setColor(Color.RED); // ���������� ä��
		g.fillOval((int) (ballX - ballRadius), (int) (ballY - ballRadius),
				(int) (2 * ballRadius), (int) (2 * ballRadius)); // ��
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String btn = e.getActionCommand();
		
		if(btn.equals("RESET")) {
			JViewport my = p.getViewport();
			Point a = my.getViewPosition();
			a.x = 150;
			a.y = 200;
			my.setViewPosition(a);
			p.setViewport(my);
			add(p);
		}
	}
	class MyThread extends Thread {
		public void run() { // �����Ͽ��� �ϴ� �۾��� ������
			while (true) {
				if(isPlay) {
					ballY += ballSpeedY;
					if(ballSpeedY < 0) {
						if(ballSpeedY < -5) 
							ballSpeedY = -5;
						else {
							ballSpeedY = (float)(ballSpeedY + 0.153);
						}
					}
					if(ballSpeedY > 0) {                             //���ӵ� ȿ�� 5�� 0.153�� ����
						if(ballSpeedY > 5)                            
							ballSpeedY = 5;
						else
							ballSpeedY = (float)(ballSpeedY + 0.153);
					}
					calBallBox();
					if(ballY - ballRadius < 0) {  // ���� ball box�� õ�忡 ������
						ballSpeedY = -ballSpeedY;
						ballY = ballBoxY + ballRadius;
					} else if (ballY + ballRadius > ballBoxY + ballBoxHeight) { // ���� ball box�� �ٴڿ� ������
						ballSpeedY = -ballSpeedY;
						ballY = ballBoxY + ballBoxHeight - ballRadius;
					}
				}
				if (isPlay) // isPlay ������ true�̸�
					repaint(); // �׸���.
				try {
					Thread.sleep(10); // ���� �ӵ� ����
				} catch (InterruptedException ex) {
				}
			}
		}
	}
	
	protected void calBallBox() {// ���� �ٿ� ��Ҵ��� Ȯ��
		Rectangle tempBall = new Rectangle((int)(ballX - ballRadius), (int)(ballY - ballRadius), (int)(ballRadius*2), (int)(ballRadius*2));
		int brickNum = -1;
		//float tempBallX, tempBallY;
		for(int i = 0; i < 10; i++) {
			if(brick[i].intersects(tempBall)) {
				brickNum = i;
				break;
			}
		}
		
		if(ballSpeedY > 0) {
			if(brickNum < 0 && ((ballY + ballRadius) < BOX_HEIGHT)) {
				if(ballBoxY < (BOX_HEIGHT - ballBoxHeight))
					ballBoxY += ballSpeedY;
				else
					ballBoxY = BOX_HEIGHT - ballBoxHeight;
			}
			
			if(brickNum != -1) {
				if((tempBall.getY()+ballRadius*2) < brick[brickNum].getY() + 19) {			
					ballBoxY = (float) (brick[brickNum].getY() - ballBoxHeight);
					ballSpeedY = 5;
				}
			}
		}
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				ballX -= 4;
				this.repaint();
				break;
			case KeyEvent.VK_RIGHT:
				ballX += 4;
				this.repaint();
				break;
			case KeyEvent.VK_UP:
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
