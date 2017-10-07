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

	int NumIcons = 4;
	
	Thread timer = null;
	Thread loader;
	
	MediaTracker track;
	
	/* define an buffer for building the image of the screen, 
	    offscreen, will hold an image of the sreen in memory  */
	
	Image offscreen;
	Graphics buffer;
	
	/* define fonts */
		
	Font Normal = new Font("ComicSans",2,24);
	
	/* define booleans */

	boolean isStandalone = false;
	boolean isLoaded = false;
	boolean ObjectsInstantiated = false;
	
	Image pot, pot2, background, pooh, eeyore;
	
	Pot hunny[] = new Pot[NumIcons];
	
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
		
		/* define Media Track to ensure that images are loaded completely before starting */
		
		track = new MediaTracker(this);
		
		/* import images $*/
	
		pot 		= getImage(getCodeBase(),"pot.gif");             
		pooh		= getImage(getCodeBase(),"Pooh4.gif");
		background 	= getImage(getCodeBase(),"beetree.jpg");
		eeyore      = getImage(getCodeBase(),"eeyore.gif");
		pot2        = getImage(getCodeBase(),"pot2.gif");
		
		/* add images to list of items to be tracked */
		
		track.addImage(pot,0);
		track.addImage(pooh,1);
		track.addImage(background,2);
		track.addImage(eeyore,3);
		track.addImage(pot2,4);
		
	}
	
	public void start()
	{
		repaint();
			/* start the Media Tracker Thread  */
			
		loader = new Thread(this);
		loader.start();
			
		
	}
	
	
	public void stop()
	{
		loader.stop();
		loader = null;
		timer = null;
	}

	
	public boolean mouseDown(Event e, int x, int y)
	{
		int i;
		for(i=0;i<NumIcons;i++)
		{
			hunny[i].SelectIcon(x,y);
		}	
		return(true);
	}
	
	
	public boolean	mouseDrag(Event e, int x, int y)
	{
		int i;
		for(i=0;i<NumIcons;i++)
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
		for(i=0;i<NumIcons;i++)
		{
			hunny[i].UnSelectIcon();
		}	
		return(true);
	}

	
	public void run()
	{
		
		/* Check that the Loader thread is still running */
		
		while (loader != null)
		{
		
		/*  Wait for all images to be loaded */
		
			try
			{
				track.waitForAll();
			}
			catch (InterruptedException e){}
			
			/* set loaded = true so that repaint knows what to draw */
			
			isLoaded = true;
			
			/* Stop the loader thread */
			
			loader = null;
			
			/* id our hunny pot and bee objects haven't been instantiated then do so now */
			
			if (ObjectsInstantiated == false)
			{
				hunny[0] = new Pot(300,100,160,240,70,150,pooh);
				hunny[1] = new Pot(200,500,100,65,5,28,eeyore);
				hunny[2] = new Pot(600,500,45,55,22,5,pot);
				hunny[3] = new Pot(400,500,45,55,22,5,pot2);
				
		
				squadran1 = new Bees(5,8,13,0,0,0,0,0.09,0.1);
				squadran2 = new Bees(10,23,20,0,0,0,0,0.1,0.2);
				squadran3 = new Bees(10,15,30,0,0,0,0,-0.08,0.3);
				
				ObjectsInstantiated = true;
			}	
			
			/* start our timer thread */
			
			if(timer==null)
			{
				timer = new Thread(this);
				timer.start();
			}
		
		
		}
		
		/* is the timer thread is running repeat the following pausing for 50 milliseconds on each loop */
		
		while (timer!=null)
		{
			try
			{Thread.sleep(60);}		
				catch (InterruptedException e){}
				
			/* find the icon which is nearest to Squadren3's head bee
			   (Squadren 3 is always in front because they are set up to be the fastest squadren - so
			   where they go, the other squadrens need to be made to follow)  */
			
			int Range,Nearest,MinRange,i;
			
			MinRange = 999;
			Nearest = 0;
			
			for (i=0;i<NumIcons;i++)
			{
				Range = squadran3.GetDistance(hunny[i].GetXCoord(),hunny[i].GetYCoord());	
				if (Range < MinRange)
				{
					MinRange = Range;
					Nearest  = i;
				}
			}
			
			/* Now we've found the closest icon, update all the squadrens to home in on it */
				
			squadran1.updateBees(hunny[Nearest].GetXCoord(),hunny[Nearest].GetYCoord());	
			squadran2.updateBees(hunny[Nearest].GetXCoord(),hunny[Nearest].GetYCoord());	
			squadran3.updateBees(hunny[Nearest].GetXCoord(),hunny[Nearest].GetYCoord());	
				
			repaint();
		}
	}
	
	
	public void update(Graphics g)
	{
	
		/* if images are still loading then output a nice message, otherwise repaint the screen */
	
		if (isLoaded == false)
		{
			g.setFont(Normal);
			g.drawString("Loading...",350,100);
		}
		else
		{
			/* draw screen image in offscreen buffer */
	
			buffer.drawImage(background,0,0,this);
			int i;
			for(i=0;i<NumIcons;i++)
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
	
}
