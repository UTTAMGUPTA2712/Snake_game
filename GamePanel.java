package SNAKE;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener{
	static final int width = 1000;
	static final int height = 1000;
	static final int size = 25;
	static final int units = (width*height)/(size*size);
	static final int speed = 75;
	final int x[] = new int[units];
	final int y[] = new int[units];
	int bodyParts = 6;
	int pointsEaten;
	int pointX;
	int pointY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newpoint();
		running = true;
		timer = new Timer(speed,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
			g.setColor(Color.black);
			g.fillOval(pointX, pointY, size, size);
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.red);
					g.fillRect(x[i], y[i], size, size);
				}
				else {
					g.setColor(new Color(255,99,71));
					g.fillRect(x[i], y[i], size, size);
				}			
			}
		}
		else {
			gameOver(g);
		}
	}
	public void newpoint(){
		pointX = random.nextInt((int)(width/size))*size;
		pointY = random.nextInt((int)(height/size))*size;
	}
	public void move(){
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - size;
			break;
		case 'D':
			y[0] = y[0] + size;
			break;
		case 'L':
			x[0] = x[0] - size;
			break;
		case 'R':
			x[0] = x[0] + size;
			break;
		}
	}
	public void checkpoint() {
		if((x[0] == pointX) && (y[0] == pointY)) {
			bodyParts++;
			pointsEaten++;
			newpoint();
		}
	}
	public void checkCollisions() {
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		if(x[0] < 0) {
			running = false;
		}
		if(x[0] > width) {
			running = false;
		}
		if(y[0] < 0) {
			running = false;
		}
		if(y[0] > height) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont( new Font("Ariel",Font.BOLD, 80));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: "+pointsEaten, (width - metrics.stringWidth("Score: "+pointsEaten))/2,  height/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkpoint();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:case KeyEvent.VK_A:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:case KeyEvent.VK_D:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:case KeyEvent.VK_W:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:case KeyEvent.VK_S:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}