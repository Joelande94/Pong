package game;

import javax.swing.JFrame;

public class Frame extends JFrame{
	
	Screen screen;
	
	public Frame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Pong");
		
		init();
	}
	
	public void init(){
		screen = new Screen();
		add(screen);
		
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args){
		new Frame();
	}
}
