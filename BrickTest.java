package OpenSource;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Timer;

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
import java.util.Random;

public class BrickTest extends JPanel implements KeyListener, ActionListener{

	private Rectangle[] brick = new Rectangle[10];
	private JScrollPane p;
	private static final int BOX_WIDTH = 500; // 전체 폭
	private static final int BOX_HEIGHT = 650; // 전체 높이
	private float ballRadius = 13; // 공의 반지름
	private float ballX = 250; // 공의 초기 X위치
	private double ballY = BOX_HEIGHT - ballRadius; // 공의 초기 Y위치
	private float ballBoxWidth = 200;
	private float ballBoxHeight = 150;
	private float ballBoxX = 20;
	private float ballBoxY = BOX_HEIGHT - ballBoxHeight;
	private float ballSpeedX = 10;
	private float ballSpeedY = 5; // 공의 Y속도
	private JButton play; // 시작버튼
	private JButton stop; // 중지버튼
	private boolean left = false;
	private boolean right = false;
	private boolean space = false;
	private boolean started = false;
	private boolean isPlay = true; // 플레이할것인지
	
	public BrickTest() {
		super();
		setSize(500, 650);
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
			Thread t = new MyThread(); // 스레드 객체 생성
			t.start(); // 스레드 시작	
	}
		
	public void allocateBricks() { // 막대 바를 아래쪽에서부터 생
		for(int i = 0; i<10 ; i++) {
			Random random = new Random();
			int rNum = random.nextInt(361);
			if(i % 2 != 0) {
				brick[i] = new Rectangle(rNum, (BOX_HEIGHT - 60*i), 140, 20);
			}
			else {
				brick[i] = new Rectangle(rNum, (BOX_HEIGHT - 60*i), 140, 20);
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
		g.setColor(Color.RED); // 빨간색으로 채움
		g.fillOval((int) (ballX - ballRadius), (int) (ballY - ballRadius),
				(int) (2 * ballRadius), (int) (2 * ballRadius)); // 원
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
		public void run() { // 수행하여야 하는 작업을 적어줌
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
					if(ballSpeedY > 0) {                             //가속도 효과 5에 0.153이 적절
						if(ballSpeedY > 5)                            
							ballSpeedY = 5;
						else
							ballSpeedY = (float)(ballSpeedY + 0.153);
					}
					calBallBox();
					
					if (left == true) {
						ballX -= 2;
					    right = false;
					}
					if (right == true) {
					    ballX += 2;
					    left = false;
					}
					if(ballY - ballRadius < 0) {  // 공이 ball box의 천장에 닿으면
						ballSpeedY = -ballSpeedY;
						ballY = ballBoxY + ballRadius;
					} else if (ballY + ballRadius > ballBoxY + ballBoxHeight) { // 공이 ball box의 바닥에 닿으면
						ballSpeedY = -ballSpeedY;
						ballY = ballBoxY + ballBoxHeight - ballRadius;
					}
				}
				if (isPlay) // isPlay 변수가 true이면
					repaint(); // 그린다.
				try {
					Thread.sleep(15); // 공의 속도 조절
				} catch (InterruptedException ex) {
				}
			}
		}
	}
	
	protected void calBallBox() {// 공이 바에 닿았는지 확인
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
	  int keyCode = e.getKeyCode();
	  if (keyCode == KeyEvent.VK_LEFT) {
	   left = true;
	   // System.out.print("left");
	  }

	  if (keyCode == KeyEvent.VK_RIGHT) {
	   right = true;
	   // System.out.print("right");
	  }
	 }

	 @Override
	 public void keyReleased(KeyEvent e) {
	  int keyCode = e.getKeyCode();
	  if (keyCode == KeyEvent.VK_LEFT) {
	   left = false;
	  }

	  if (keyCode == KeyEvent.VK_RIGHT) {
	   right = false;
	  }
	  
	  if (keyCode == KeyEvent.VK_SPACE)
		  space = false;
	 }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		int KeyCode = e.getKeyCode();
		boolean space;
		if (KeyCode == KeyEvent.VK_SPACE)
			space = true;
	}
}