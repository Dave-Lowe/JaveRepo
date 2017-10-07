/* 
	Bees.java

	Author:			Dave Lowe
		Description:	
*/

package hunny;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Bees extends Applet
{

	
	
	
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
	
	Color Black = new Color(0,0,0);
	
	Color Yellow = new Color(255,255,0);
	
	
	
	public Bees(int initno, int initheight, int initwidth, int initDistx, int initDisty, int initPosx, int initPosy, double initstep, double initspeed)
	{
		int i;
		
		/*  Set up initial values  */
		
		NumberOfBees = initno;
		
		Height = initheight;
		Width = initwidth;
		DistMouseX = initDistx;
		DistMouseY = initDisty;
		
		mousex = initPosx;
		mousey = initPosy;
		
		Speed = initspeed;
		
		step = initstep;
		
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
		
		
	}	
	
	
		
	public void updateBees(int xpos,int ypos)
	{
		int i;
		
		/* calculate the delay between each letter reletive to the previous letter.  The delay for the first 
			letter is calculated reletive the mouse  */
			
		mousex = xpos + DistMouseX;
		mousey = ypos + DistMouseY;	
		
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
	
	public int GetDistance(int xinput, int yinput)
	{
		int xdist,ydist, range;
		
		/* find the distance of the requested point from the lead bee */
		
		xdist = x[0] - xinput;
		ydist = y[0] - yinput;
		
		range = (int) java.lang.Math.sqrt(java.lang.Math.pow(xdist,2) + java.lang.Math.pow(ydist,2));
		
		return(range);
	}	
	
	public void DrawBees(Graphics g)	
	{
		int i;
		
		/*Draw out each letter in turn*/
		
		
		
		for (i=0;i<NumberOfBees;i++)
		{
			g.setColor(Black);
			g.fillOval(x[i],y[i],4,4);
			g.setColor(Yellow);
			g.fillRect(x[i]+1,y[i],2,4);
		}
	}		
		
			
	
}
