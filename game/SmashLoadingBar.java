package game;

import java.awt.Color;
import java.awt.Graphics;

public class SmashLoadingBar {
	private int xCoor, yCoor = 40, width = 100, height = 20;
	private double progress;
	
	public SmashLoadingBar(int xCoor){
		this.xCoor = xCoor;
	}
	
	void draw(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xCoor, yCoor, (int) (progress*width), height);
		g.setColor(Color.WHITE);
		g.drawRect(xCoor, yCoor, width, height);
	}
	void setProgress(double progress){
		this.progress = progress;
		if(progress > 1) this.progress = 1;
	}
}
