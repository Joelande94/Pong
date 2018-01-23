package game;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	private int xCoor, yCoor, xVel, yVel;
	private int width = 20, height = 20;
	
	Ball(int xCoor, int yCoor, int xVel, int yVel){
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xCoor, yCoor, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(xCoor, yCoor, width, height);
	}
	
	int getxCoor(){
		return xCoor;
	}
	int getyCoor(){
		return yCoor;
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
