/* 
	main.java

	Author:			default
	Description:	
*/

package hunny;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class main extends Applet implements Runnable
{

	Thread timer = null;
	
	/* define an buffer for building the image of the screen, 
	    offscreen, will hold an image of the sreen in memory  */
	
	Image offscreen;
	Graphics buffer;
	
	

	boolean isStandalone = false;
	
	Image pot, background, pooh;
	
	Pot hunny[] = new Pot[2];
	
	Bees squadran1, squadran2, squadran3;

	public main() 
	{
	}

	public void init()
	{
		/* create off screen buffer  */
		
		offscreen 	= this.createImage(800,600);
		
		/* buffer is defined as the graphic object for this buffer */
		
		buffer		= offscreen.getGraphics();
	
		pot 		= getImage(getCodeBase(),"pot.gif");             
		pooh		= getImage(getCodeBase(),"Pooh4.gif");
		background 	= getImage(getCodeBase(),"trees.jpg");
		
		
		hunny[0] = new Pot(300,100,160,240,70,150,pooh);
		hunny[1] = new Pot(600,500,45,55,22,5,pot);
		
		squadran1 = new Bees(5,8,13,0,0,0,0,0.09,0.1);
		squadran2 = new Bees(10,23,20,0,0,0,0,0.1,0.2);
		squadran3 = new Bees(10,15,30,0,0,0,0,-0.08,0.3);
		
		
			
		
	}
	
	public void start()
	{
		repaint();
			/* start the Thread  */
		if(timer==null)
		{
			timer = new Thread(this);
			timer.start();
		}
		
		
	}
	
	
	public void stop()
	{
		timer = null;
	}

	
	public boolean mouseDown(Event e, int x, int y)
	{
		int i;
		for(i=0;i<2;i++)
		{
			hunny[i].SelectIcon(x,y);
		}	
		return(true);
	}
	
	
	public boolean	mouseDrag(Event e, int x, int y)
	{
		int i;
		for(i=0;i<2;i++)
		{
			if (hunny[i].IsSelected())
			{
				hunny[i].DragIcon(x,y);
			}
		}	
		return(true);
	}
	
	
	public boolean mouseUp(Event e, int x, int y)
	{
		int i;
		for(i=0;i<2;i++)
		{
			hunny[i].UnSelectIcon();
		}	
		return(true);
	}

	
	public void run()
	{
		while (timer!=null)
		{
			try
			{Thread.sleep(50);}		
				catch (InterruptedException e){}
				
			int range0,range1,i;
			
			range0 = squadran3.GetDistance(hunny[0].GetXCoord(),hunny[0].GetYCoord());	
			range1 = squadran3.GetDistance(hunny[1].GetXCoord(),hunny[1].GetYCoord());
			
			if (range0 < range1)
			{
				i=0;
			}
			else
			{
				i=1;
			}	
				
			squadran1.updateBees(hunny[i].GetXCoord(),hunny[i].GetYCoord());	
			squadran2.updateBees(hunny[i].GetXCoord(),hunny[i].GetYCoord());	
			squadran3.updateBees(hunny[i].GetXCoord(),hunny[i].GetYCoord());	
				
			repaint();
		}
	}
	
	
	public void update(Graphics g)
	{
		/* draw screen image in offscreen buffer */
	
		buffer.drawImage(background,0,0,this);
		int i;
		for(i=0;i<2;i++)
		{
			hunny[i].DrawIcon(buffer);
		}	
		squadran1.DrawBees(buffer);
		squadran2.DrawBees(buffer);
		squadran3.DrawBees(buffer);


		/* draw offscreen buffer on screen  */
				
		g.drawImage(offscreen,0,0,this);
	}		
	
}
