/* 
	main.java

	Author:			default
	Description:	
*/

package hunny;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class hunny extends Frame implements Runnable, WindowListener , MouseMotionListener, MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int NumIcons = 4;
	
	Thread timer = null;
	Thread loader;
	
	MediaTracker track;
	

	
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

	public static void main(String[] args) 
	{
		hunny f = new hunny();
		f.setSize(800, 600);
		f.setVisible(true);
		f.setLayout(new FlowLayout());
	}

	public hunny()
	{
		/* Create Window Listener */
		this.addWindowListener(this);
		
		/* Create Mouse Listeners */
		addMouseListener(this);
		addMouseMotionListener(this);
		
		/* define Media Track to ensure that images are loaded completely before starting */
		
		track = new MediaTracker(this);
		
		/* import images $*/

		
		try {
			pot			= ImageIO.read(new URL(ClassLoader.getSystemResource("pot.gif"), "pot.gif"));
		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}
		
		try {
			pooh			= ImageIO.read(new URL(ClassLoader.getSystemResource("Pooh4.gif"), "Pooh4.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			background		= ImageIO.read(new URL(ClassLoader.getSystemResource("beetree.jpg"), "beetree.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			eeyore			= ImageIO.read(new URL(ClassLoader.getSystemResource("eeyore.gif"), "eeyore.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			pot2			= ImageIO.read(new URL(ClassLoader.getSystemResource("pot2.gif"), "pot2.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* add images to list of items to be tracked */
		
		track.addImage(pot,0);
		track.addImage(pooh,1);
		track.addImage(background,2);
		track.addImage(eeyore,3);
		track.addImage(pot2,4);
		
		start();
		
	}
	
	public void start()
	{
		/*repaint();*/
			/* start the Media Tracker Thread  */
			
		loader = new Thread(this);
		loader.start();
			
		
	}
	
	
	@SuppressWarnings("deprecation")
	public void stop()
	{
		loader.stop();
		loader = null;
		timer = null;
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

		/* define an buffer for building the image of the screen, 
	    offscreen, will hold an image of the screen in memory  */
		
		/* create off screen buffer  */
		
		offscreen 	= this.createImage(800,600);
		
		/* buffer is defined as the graphic object for this buffer */
		
		buffer		= offscreen.getGraphics();
		
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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		dispose();
		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {

		int i;
		for(i=0;i<NumIcons;i++)
		{
			if (hunny[i].IsSelected())
			{
				hunny[i].DragIcon((int) e.getPoint().getX(),(int) e.getPoint().getY());
			}
		}	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int i;
		for(i=0;i<NumIcons;i++)
		{
			hunny[i].SelectIcon((int) e.getPoint().getX(),(int) e.getPoint().getY());
		}	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int i;
		for(i=0;i<NumIcons;i++)
		{
			hunny[i].UnSelectIcon();
		}	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}		
	
}
