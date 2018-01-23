package game;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
	private int xCoor, yCoor = 250, width = 20, height = 100;
	private int xVel, yVel;
	
	public Paddle(int xCoor){
		this.xCoor = xCoor;
		System.out.println(yCoor);
	}
	
	void draw(Graphics g){
		System.out.println(yCoor);
		g.setColor(Color.WHITE);
		g.fillRect(this.xCoor, this.yCoor, width, height);
	}
	
	int getxCoor(){
		return xCoor;
	}
	int getyCoor(){
		return yCoor;
	}
	int getxVel(){
		return xVel;
	}
	int getyVel(){
		return yVel;
	}
	void setxCoor(int xCoor){
		this.xCoor = xCoor;
	}
	void setyCoor(int yCoor){
		this.yCoor = yCoor;
	}
	void setxVel(int xVel){
		this.xVel = xVel;
	}
	void setyVel(int yVel){
		this.yVel = yVel;
	}
}