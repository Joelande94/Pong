package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{

	private static final long serialVersionUID = 2499184065980865929L;

	public static final int WIDTH = 800, HEIGHT = 600;
	
	private int xCoor, yCoor, xVel, yVel;
	private int yCoor1, yCoor2;
	private int ticks = 0;
	
	private int score1 = 0, score2 = 0;
	
	private Thread thread;
	
	private Ball ball;
	private Paddle rightPaddle, leftPaddle;
	private SmashLoadingBar rightLoading, leftLoading;
	
	private boolean running = false;
	private boolean up1 = false, down1 = false, up2 = false, down2 = false;
	private boolean smash1 = false, smashdown1 = false;
	private boolean smash2 = false, smashdown2 = false;
	private boolean loading1 = false, loading2 = false;
	
	private double time, collisionTime, smashTime1, smashTime2;
	
	private Key key;
	
	private Random r;
	
	public Screen(){
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		key = new Key();
		addKeyListener(key);
		
		r = new Random();
		
		xVel = r.nextInt(5);
		yVel = 5-xVel;
		
		if(xVel == 0){
			yVel --;
			xVel ++;
		}
		if(yVel == 0){
			xVel --;
			yVel ++;
		}
		
		xCoor = WIDTH/2 -10;
		yCoor = HEIGHT/2 -10;
		
		ball = new Ball(xCoor, yCoor, xVel, yVel);
		rightPaddle = new Paddle(WIDTH - 40);
		leftPaddle = new Paddle(20);
		
		rightLoading = new SmashLoadingBar(5*WIDTH/8);
		leftLoading = new SmashLoadingBar(2*WIDTH/8);
		
		start();
	}
	
	public void paint(Graphics g){
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		//Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		//Dashed line
		for(int i=0; i<22; i++){
			g.setColor(Color.WHITE);
			g.fillRect((WIDTH/2) -5 , i*(HEIGHT/21), 5, WIDTH/50);
		}
		
		g.drawString("Player 1: " + score1, WIDTH/4, HEIGHT/8);
		g.drawString("Player 2: " + score2, 5*WIDTH/8, HEIGHT/8);
		
		rightPaddle.draw(g);
		leftPaddle.draw(g);
		
		ball.draw(g);
		
		rightLoading.draw(g);
		leftLoading.draw(g);
	}
	
	void tick(){
		//Ball   O
		if(xCoor < 0){
			score2++;
			xCoor = WIDTH/2;
			yCoor = HEIGHT/2;
			
			xVel = r.nextInt(5);
			yVel = 5-xVel;
			
			if(xVel == 0){
				yVel --;
				xVel ++;
			}
			if(yVel == 0){
				xVel --;
				yVel ++;
			}
		}
		if(xCoor > WIDTH - 20){
			score1++;
			xCoor = WIDTH/2;
			yCoor = HEIGHT/2;
			
			xVel = r.nextInt(5);
			yVel = 5-xVel;
			
			if(xVel == 0){
				yVel --;
				xVel ++;
			}
			if(yVel == 0){
				xVel --;
				yVel ++;
			}
			xVel = -xVel;
		}
		
		if(yCoor < 0){
			yVel = -yVel;
			yCoor = 1;
		}
		if(yCoor >= HEIGHT-20){
			yVel = -yVel;
			yCoor = HEIGHT-21;
		}
//		BouncyTime
//		if(xCoor < 0){
//			xVel = -xVel;
//			xCoor = 1;
//		}
//		if(xCoor >= WIDTH-20){
//			xVel = -xVel;
//			xCoor = WIDTH-21;
//		}
		
		//OOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		
		//Loading bars [//////  ] [/////// ]  [////////]
		if(System.nanoTime() - smashTime1 > 5000000000.0) loading1 = false;
		if(System.nanoTime() - smashTime1 > 5000000000.0) loading2 = false;
		
//		if(!loading1) {
//			rightLoading.setProgress(1);
//		}else{
			rightLoading.setProgress((System.nanoTime() - smashTime1)/5000000000.0);
//		}
//		if(!loading2) {
//			leftLoading.setProgress(1);
//		}else{
			leftLoading.setProgress((System.nanoTime() - smashTime2)/5000000000.0);
//		}
		
		
		if(System.nanoTime() - collisionTime > 200000000){
			//Hit right paddle
			if( (ball.getxCoor() +20 >= rightPaddle.getxCoor() && ball.getxCoor() +20 <= rightPaddle.getxCoor() +20) && (ball.getyCoor() +10 >= rightPaddle.getyCoor() && ball.getyCoor() + 10 <= rightPaddle.getyCoor() +100) 
					&& (ball.getxCoor() > rightPaddle.getxCoor() != true)){
				xVel = -xVel;
				yVel += rightPaddle.getyVel()/2;
				xVel += rightPaddle.getxVel();
				xCoor = rightPaddle.getxCoor() - 25;
				collisionTime = System.nanoTime();
			}
			//Hit left paddle
			if( (ball.getxCoor() <= leftPaddle.getxCoor() +20 && ball.getxCoor() >= leftPaddle.getxCoor() -20) && (ball.getyCoor() +10 >= leftPaddle.getyCoor() && ball.getyCoor() +10 <= leftPaddle.getyCoor() +100)  ){
				xVel = -xVel;
				yVel += leftPaddle.getyVel()/2;
				xVel += leftPaddle.getxVel();
				xCoor = leftPaddle.getxCoor() + 25;
				collisionTime = System.nanoTime();
			}
		}
		
		ticks++;
		
		if(System.nanoTime() - time > 10000000){
			time = System.nanoTime();
			xCoor += xVel;
			yCoor += yVel;
			
			//smash1
			if(smash1){
				rightPaddle.setxVel(rightPaddle.getxVel() - 1);
				
				if(rightPaddle.getxCoor() <= 7 * WIDTH/8){
					smash1 = false;
					smashdown1 = true;
					rightPaddle.setxVel(0);
				}
			}
			if(smashdown1){
				rightPaddle.setxVel(rightPaddle.getxVel() + 1);
				
				if(rightPaddle.getxCoor() >= WIDTH - 40){
					smashdown1 = false;
					rightPaddle.setxVel(0);
				}
			}
			//smash2
			if(smash2){
				leftPaddle.setxVel(leftPaddle.getxVel() + 1);
				
				if(leftPaddle.getxCoor() >= 1 * WIDTH/8){
					smash2 = false;
					smashdown2 = true;
					leftPaddle.setxVel(0);
				}
			}
			if(smashdown2){
				leftPaddle.setxVel(leftPaddle.getxVel() - 1);
				
				if(leftPaddle.getxCoor() <= 20){
					smashdown2 = false;
					leftPaddle.setxVel(0);
				}
			}
			
			if(up1 && yCoor1 >= 0)              yCoor1 += rightPaddle.getyVel();
			if(down1 && yCoor1 <= HEIGHT - 100) yCoor1 += rightPaddle.getyVel();
			if(up2 && yCoor2 >= 0) 				yCoor2 += leftPaddle.getyVel();
			if(down2 && yCoor2 <= HEIGHT - 100) yCoor2 += leftPaddle.getyVel();
			
			rightPaddle.setyCoor(yCoor1);
			leftPaddle.setyCoor(yCoor2);
			
			rightPaddle.setxCoor(rightPaddle.getxCoor() + rightPaddle.getxVel());
			leftPaddle.setxCoor(leftPaddle.getxCoor() + leftPaddle.getxVel());

			ball.setyCoor(yCoor);
			ball.setxCoor(xCoor);
		}
	}
	
	@Override
	public void run() {
		while(running){
			tick();
			repaint();
		}
	}
	
	void start(){
		time = System.nanoTime();
		running = true;
		thread = new Thread(this, "Game loop");
		thread.start();
	}
	void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	class Key implements KeyListener{

		@Override
		public void keyPressed(KeyEvent pressed) {
			if(pressed.getKeyCode() == KeyEvent.VK_UP){
				rightPaddle.setyVel(-3);
				up1 = true;
				down1 = false;
			}
			if(pressed.getKeyCode() == KeyEvent.VK_DOWN){
				rightPaddle.setyVel(3);
				up1 = false;
				down1 = true;
			}
			if(pressed.getKeyCode() == KeyEvent.VK_W){
				leftPaddle.setyVel(-3);
				up2 = true;
				down2 = false;
			}
			if(pressed.getKeyCode() == KeyEvent.VK_S){
				leftPaddle.setyVel(3);
				up2 = false;
				down2 = true;
			}
			if(pressed.getKeyCode() == KeyEvent.VK_LEFT){
				if(System.nanoTime() - smashTime1 > 5000000000.0){
					smash1 = true;
					loading1 = true;
					smashTime1 = System.nanoTime();
				}
			}
			if(pressed.getKeyCode() == KeyEvent.VK_D){
				if(System.nanoTime() - smashTime2 > 5000000000.0){
					smash2 = true;
					loading2 = true;
					smashTime2 = System.nanoTime();
				}
			}
			
		}
		
		@Override
		public void keyReleased(KeyEvent released) {
			if(released.getKeyCode() == KeyEvent.VK_UP || released.getKeyCode() == KeyEvent.VK_DOWN){
				rightPaddle.setyVel(0);
			}
			if(released.getKeyCode() == KeyEvent.VK_W || released.getKeyCode() == KeyEvent.VK_S){
				leftPaddle.setyVel(0);
			}
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
	

}
