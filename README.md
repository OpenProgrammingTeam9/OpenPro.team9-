# OpenPro.team9-
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BouncingBall extends JPanel{
	
	private static final int BOX_WIDTH = 400; // 전체 폭
	private static final int BOX_HEIGHT = 300; // 전체 높이
	private float ballRadius = 20; // 공의 반지름
	private float ballX = ballRadius + 120; // 공의 초기 X위치
	private float ballY = ballRadius + 80; // 공의 초기 Y위치
	private float ballSpeedX = 10; // 공의 X속도
	private float ballSpeedY = 10; // 공의 Y속도
	private JButton play; // 시작버튼
	private JButton stop; // 중지버튼
	private boolean isPlay = false; // 플레이할것인지
 
	public BouncingBall() {
		
		this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		JButton play = new JButton("PLAY"); // 시작버튼 생성
		JButton stop = new JButton("STOP"); // 중지버튼 생성
		add(play);
		add(stop);
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("PLAY")) { // 만약 버튼의 문자열이 PLAY라면
					isPlay = true;
				}
			}
		});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("STOP")) {// 만약 버튼의 문자열이 STOP이라면
					isPlay = false;
				}
			}
		});
  
	class MyThread extends Thread {
		public void run() { // 수행하여야 하는 작업을 적어줌
			while (true) {
				ballY += ballSpeedY;
				if (ballY - ballRadius < 0) {
					ballSpeedY = -ballSpeedY;
					ballY = ballRadius;
				} else if (ballY + ballRadius > BOX_HEIGHT) {
					ballSpeedY = -ballSpeedY;
					ballY = BOX_HEIGHT - ballRadius;
				}
				if (isPlay) // isPlay 변수가 true이면
					repaint(); // 그린다.
				try {
					Thread.sleep(50); // 공의 속도 조절
				} catch (InterruptedException ex) {
				}
			}
		}
	}
	Thread t = new MyThread(); // 스레드 객체 생성
	t.start(); // 스레드 시작
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT); // 직사각형
		g.setColor(Color.RED); // 빨간색으로 채움
		g.fillOval((int) (ballX - ballRadius), (int) (ballY - ballRadius),
				(int) (2 * ballRadius), (int) (2 * ballRadius)); // 원
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Bouncing Ball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(600,800);
		frame.setContentPane(new BouncingBall());
		frame.pack();
		frame.setVisible(true);
	}
}
