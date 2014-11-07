/* Author: Justin Cooper
 * This program draws a growing spiral. At a certain time, the spiral will grow one more edge.
 * The user can move the mousewheel up or down to increase or decrease the speed of the growth.
 * After the spiral grows to 10 rings, it resets and starts over.
 * Last Modified: 10/17/14
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class ScreenSaver extends JPanel implements ActionListener, MouseWheelListener{
	
	private int x[]=new int[60];
	private int y[]=new int[60];
	private int numOfPoints=0;
	private int radius=10;
	private int delay=1000;
	private Timer timer=null; 
	
	public ScreenSaver(){
		// Creates new timer object of int delay
		timer = new Timer(delay, this); // time interval is 1000 ms
		// starts the timer which triggers the events
		timer.start();
		//adds the mousewheellistener to the object
		addMouseWheelListener(this);
	}

	public void actionPerformed(ActionEvent e){
		// Finds the center X and Y 
		double centerX = getSize().getWidth()/2;
		double centerY = getSize().getHeight()/2;
		
		// point x and point y
		double pX = centerX + Math.cos(numOfPoints * Math.PI/3)*radius;
		double pY = centerY + Math.sin(numOfPoints * Math.PI/3)*radius;
		// arrays to store the points	
		x[numOfPoints] = (int)pX;
		y[numOfPoints] = (int)pY;
		//increments by 1
		numOfPoints++;
		radius++;
		
		// Resets the numOfPoints and radius
		if(numOfPoints == 60){
			numOfPoints = 0;
			radius = 10;
		}
		// repaints the new line created
		repaint();
	}
	
	public void paintComponent(Graphics g){
		// clears the background and draws the polyline based on X, Y, and numOfPoints
		g.clearRect(0,0,(int)getSize().getWidth(),(int)getSize().getHeight());
		g.drawPolyline(x, y, numOfPoints);
	}
	
	
	// MouseWheel Event Handler
	@Override
	public void mouseWheelMoved(MouseWheelEvent e){
		
		int r = e.getWheelRotation();
		if( r < 0){ // moving the wheel up - away from you
			// decrease delay
			timer.setDelay(delay-=70);
		}
		else{		
			// increase delay
			timer.setDelay(delay+=70);
		}
		// Updates and restarts the new timer
		timer.restart();
		
	}
}

public class GrowingSpiral extends JFrame{
	public GrowingSpiral(){
		super("Spiral");
		ScreenSaver saver=new ScreenSaver();
		setLayout(new BorderLayout());
		add(saver, BorderLayout.CENTER);
	}
	public static void main(String[] args) { 
		GrowingSpiral spiral=new GrowingSpiral();
		spiral.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		spiral.setSize( 200, 210 ); // set frame size
		spiral.setResizable(false); 
		spiral.setVisible(true); // display frame
		spiral.setLocationRelativeTo(null);	
	}
}