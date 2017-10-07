/* 
	Applet1.java

	Author:			default
	Description:	
*/

package test;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Applet1 extends Applet implements Runnable
{

	Thread timer = null;
	
	
	/* NumberOfBees  is the number of Bees!  */
	
	int NumberOfBees;
	
	
	/* Height and width are fairly obvious, Dist Mouse indicates the offset for the clock */
	
	int Height,Width,DistMouseX,DistMouseY;  
	
	/* Mousex and mousy hold the position of the cursor */
	
	int mousex,mousey;
	
	
	
	/* Speed is the Speed at which the message 'catches up with the mouse
	
		Step is the speed of rotation of the message
		
		currstep holds the Value of Step toggled by -1 by each update??? 
		
		Dsplit holds the angle between each letter (360/ number of letters)*/
		
	    	
	
	double Speed,step,currstep, Dsplit;
	
	
	
	double Dx[];  /*  Dx, Dy are the delay for each letter  */
	double Dy[];
	double DX[]; /* DX and DY are the previous delay for each letter (I think!)  */
	double DY[];
	int 	x[]; 	 /* x and y are the coordinates of eaxh position  */
	int 	y[]; 
	
	
	
	
	boolean isStandalone = false;
	
	public void init()
	{
		int i;
		
		/*  Set up initial values  */
		
		NumberOfBees = 50;
		
		Height = 40;
		Width = 40;
		DistMouseX = 0;
		DistMouseY = 0;
		
		mousex = 0;
		mousey = 0;
		
		Speed = 0.6;
		
		step = 0.06;
		
		currstep = 0;
		
		Dsplit = 360/NumberOfBees;
		
		
		/* Define the sizes of the Arrays  */
		
		
		Dx = new double[NumberOfBees];  /*  Dx, Dy are the delay for each letter  */
	    Dy = new double[NumberOfBees];
		DX = new double[NumberOfBees];	  /* DX and DY are the previous delay for each letter (I think!)  */
		DY = new double[NumberOfBees];
		x  = new int[NumberOfBees];	 /* x and y are the coordinates of eaxh position  */
		y  = new int[NumberOfBees];
		
				
		/*Initialise the Arrays */	
		
		
		for (i=0;i<NumberOfBees;i++)
		{
			Dx[i]=0;
			Dy[i]=0;
			DX[i]=0;
			DY[i]=0;
			x[i]=0;
			y[i]=0;
		}	
		
		/* start the Thread  */
		
		if(timer==null)
			{
				timer = new Thread(this);
				timer.start();
			}
		
	}	
	
	
	public void start()
	{
		
	}
	
	
	public void stop()
	{
		timer = null;
	}



	public boolean mouseMove(Event e, int x, int y)
	{
	
		/* Capture the Mouses coordinates and apply the specified Offset */
		mousex = x + DistMouseX;
		mousey = y + DistMouseY;
		
		return(true);
	}	
			


	public void run()
	{
		while (timer!=null)
		{
			try
			{Thread.sleep(50);}		
				catch (InterruptedException e){}
				
			updatePosition();
			repaint();
		}
	}
	
			
	public void updatePosition()
	{
		int i;
		
		/* calculate the delay between each letter reletive to the previous letter.  The delay for the first 
			letter is calculated reletive the mouse  */
		
		Dy[0] =  java.lang.Math.round(DY[0]+=((mousey)-DY[0])*Speed);
		Dx[0] =  java.lang.Math.round(DX[0]+=((mousex)-DX[0])*Speed);
		
		for (i=1;i<NumberOfBees;i++)
		{
			Dy[i] =  java.lang.Math.round(DY[i]+=(Dy[i-1]-DY[i])*Speed);
			Dx[i] =  java.lang.Math.round(DX[i]+=(Dx[i-1]-DX[i])*Speed);
		}
		
		/* Calculate the new coordinates for each letter  */
		
		for (i=0;i<NumberOfBees;i++)
		{
			y[i] = (int) (Dy[i] + Height * java.lang.Math.sin(currstep+i*Dsplit*java.lang.Math.PI/180));
			x[i] =  (int) (Dx[i] + Width *  java.lang.Math.cos(currstep+i*Dsplit*java.lang.Math.PI/180));
		}
		
		currstep-=step;	
			
	}
	
	
	public void paint(Graphics g)	
	{
		int i;
		
		/*Draw out each letter in turn*/
		
		for (i=0;i<NumberOfBees;i++)
		{
			g.drawOval(x[i],y[i],4,4);
		}
	}		
		
			
	
}
