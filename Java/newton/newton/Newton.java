/* 
	Newton.java

	Author:			Dave Lowe
	Description:	Demonstrator of Newton's laws
*/

package newton;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Newton extends Applet implements Runnable
{

//BEGIN GENERATED CODE
	// member declarations
// END GENERATED CODE

	/*  x & y are the coordinates of the rocket at any time */

	
	double initx, inity;
	
	/* Angle is the rocket's angle, and deltaand is it's angular velocity  */
	
	double initAngle;
	
	
	/* thrust and anglethrust are constants which determine how powerful the engines are  */
	
	double initthrust, initanglethrust;
	
	
	/* coordinates of planet  */
	
	int planetx, planety;
	
	
	/* Mass of planet  */
	
	double PMass;
	
	/* Radius of planet */
	
	double radius;
	
	
	/* Mass of Rockaet */
	
	double RMass;
	
		
	/* flags to show which events have taken place in the last 20 ms  */	
		
	boolean gravity, left, right, throttle;
	
	
	/* flag to show if the Rocket has crashed and one to determine is we show the impact crater */
	
	boolean crashed, showCrater;
	
	
	
	Thread timer = null;
	
	
	/* Set up offscreen image area and a buffer of type graphics  */
	
	Image offscreen;
	
	Graphics buffer;
	
	
	/* Colours.. */
	
	Color red    = new Color(255,0,0);
	Color black  = new Color(0,0,0);
	Color yellow = new Color(255,255,0);
	Color cyan	 = new Color(0,255,255);
	Color blue   = new Color(0,0,255);
	Color green  = new Color(0,255,0);
	Color Gratis = new Color(0,50,0);
	
	/* buttons...  */
	
	Button butGrav, butLeft, butRight, butReset;
	
	
	/* Counter to determine when to restart the Applet after a crash  */
	
	
	int Counter;
	
	
	/* define objects 
	
		r is the pointer for the Rocket
		P is the pointer for the Planet
		d[] is the pointers for the Rocket debris
		e[]	is the pointer for the Planet Debris
	*/
	
	
	Rocket r;
	
	Planet p;
		
	Debris d[] = new Debris[30];
	Debris e[] = new Debris[30];
		

	boolean isStandalone = false;

	public void init() 
	{
		/* Create offscreen area and make buffer its graphics method  */
		
		offscreen 	= this.createImage(800,600);
					
		buffer		= offscreen.getGraphics();
	
		
		/* Declare Plametary Mass */
		
		PMass		= 1000;
		
		
		/* Declare planets radius  */
		
		
		radius = 20;
		
		
		/*Declare Rocket Mass */
		
		RMass		= 100;
			
	
		/* Set up constants  */
		
		initthrust = 10;
		
		initanglethrust = 3;
		
		planetx = 400;
		planety = 300;
		
				
		/*  Declare and draw buttons    */
		
		butGrav	 = new Button ("Asteriod");
		butLeft  = new Button("Left");
		butRight = new Button("Right");
		butReset = new Button("Reset");
		
		add(butGrav);
		add(butLeft);
		add(butRight);
		add(butReset);

		
		if (timer == null)
		{
			timer = new Thread(this);
			timer.start();
		}
	}
	
	
	public void start()
	{
	/* Set up initial variable values */
		
		
		initx = 600;
		inity = 300;
		
		initAngle = 0;
		
				
		gravity  = false;
		left     = false;
		right    = false;
		throttle = false;

		crashed  = false;		
		showCrater = true;
		
		Counter = 0;
		
		r = new Rocket(initx,inity,initAngle,RMass,initthrust,initanglethrust,red);
		
		p = new Planet(planetx, planety,PMass,radius,cyan,black);
		
	
	}
	
	public void stop()
	{
		timer = null;
	}
	
	public boolean action(Event evt, Object arg)
	{
		if (evt.target == butGrav)
		{
			if (gravity == false)
			{
				gravity = true;
			}
			else
			{	
				gravity = false;
			}
		}			
		
		if (evt.target == butLeft)
		{
			left = true;
		}	
		
		if (evt.target == butRight)
		{
			right = true;
		}	
		
		if (evt.target == butReset)
		{
			start();
		}	
		
		return(true);
	}
	
	
	/* The following two methods are a test to prove the feasibility of having a throttle that stays 'on' as long
		as the mouse button is held down.  	*/	
	
	public boolean mouseDown(Event e, int x, int y)
	{
		throttle = true;
		return(true);
	}	
	
	public boolean mouseUp(Event e, int x, int y)
	{
		throttle = false;
		return(true);
	}	
				
	
	public void run()
	{	
		while (timer != null)
		{
			
			/* Sleep for 1/50th Second */
			
			try
			{Thread.sleep(20);}
				catch(InterruptedException e){}
				
			
			
			/* wrap Rocket around vertically if required */
			
			
			
			if (r.GetYCoord() > 580)
			{
				r.SetYCoord(20);
			}
			
			if (r.GetYCoord() < 20)
			{
				r.SetYCoord(580);
			}	
			
			
			/* wrap Rocket around horizontally if required */
			
			if (r.GetXCoord() > 780)
			{
				r.SetXCoord(20);
			}	
			
			if (r.GetXCoord() < 20)
			{
				r.SetXCoord(780);
			}
			
			
			/* wrap Planet around vertically if required */
			
			
			if (p.GetYCoord() > 580)
			{
				p.SetYCoord(20);
			}
			
			if (p.GetYCoord() < 20)
			{
				p.SetYCoord(580);
			}	
			
			
			/* wrap Planet around vertically if required */
			
			if (p.GetXCoord() > 780)
			{
				p.SetXCoord(20);
			}	
			
			if (p.GetXCoord() < 20)
			{
				p.SetXCoord(780);
			}
			
			
			
			/* Check for a collision.  If there isn't one update for the effect of gravity on both planet and rocket,
				 and then update the position of the planet.   */
			
			if (gravity)
			{
				if (crashed == false)
				{
					/* Check for Collision and if true set variable crashed = true.  if there isn't one
						calculate the effects of gravity */
					if (r.FindRange(p.GetXCoord(),p.GetYCoord()) < p.GetRadius())
					{
						crashed = true;
						p.SetCrash(r.GetXCoord(),r.GetYCoord(),r.GetDeltaX(),r.GetDeltaY(),r.GetMass(),showCrater);
						InitialiseDebris();
					}
					else
					{
						/*  Apply effect of Planet's gravity on Rocket */
						r.ApplyGravity(p.GetXCoord(),p.GetYCoord(),p.GetMass());
		
						/* 	Apply effect of Rocket's gravity on Planet */
						p.ApplyGravity(r.GetXCoord(),r.GetYCoord(),r.GetMass());
					}			
				}	
				
			
				/*Update position of Planet  */
				p.UpdatePosition();
			}		
			
			/* 	calculate new coordintates for the Rocket if not crashed 
				If it has crashed, increment the counter to see if 5 seconds hase passed.
				If it has, restart the Applet
				If not, Update the posisition of all the debris */
			
			
			if (crashed == false)
			{		
				r.UpdatePosition(throttle,left,right);
			}
			else
			{
				Counter ++;
				if (Counter < 250)
				{
					UpdateDebris();
				}
				else
				{
					start();
				}		
			}	
			
			
			
			
			/* redraw the screen */
						
			repaint();
			
			
			right = false;
			left  = false;
					
		}	
	}
	
			
	
	public void update(Graphics g)
	{
		/* create the screen in the off screen buffer */
		
		/* Draw the black background  */
		buffer.setColor(black);
		buffer.fillRect(0,0,800,600);
		
		/* draw gratiscule  */
		
		/* DrawGratiscule(buffer);      /* Not sure I like this so commented out for now!	*/
		
		/* draw Planet if Gravity selected  */
		
		if(gravity)
		{
			p.DrawPlanet(buffer);
			
		}
		
		
		/* Draw the Rocket if not crashed - otherwise draw debris*/
		
		if (crashed == false)
		{
			r.DrawRocket(buffer,throttle,left,right);
		}
		else
		{
			DrawWreckage(buffer);
		}
		
		
		
		
		/* draw offscreen buffer on screen  */
				
		g.drawImage(offscreen,0,0,this);
			
		
		
		
		
	}



	public void DrawGratiscule(Graphics g)
	{
		int n;
		
		g.setColor(Gratis);
		
		for(n=0;n<800;n=n+40)
		{
			g.drawLine(n,0,n,600);
		}
		
		for(n=0;n<600;n=n+40)
		{
			g.drawLine(0,n,800,n);
		}
	}
				

	public void InitialiseDebris()
	{
		int n;
		
		for	(n=0;n<30;n++)
		{
			d[n] = new Debris(r.GetXCoord(),r.GetYCoord(),r.GetColor());
		}
			
		for	(n=0;n<30;n++)
		{
			e[n] = new Debris(r.GetXCoord(),r.GetYCoord(),p.GetColor());
		}	
	}


	public void UpdateDebris()
	{
		int n;
		
		for	(n=0;n<30;n++)
		{
			d[n].UpdatePosition();
		}
			
		for	(n=0;n<30;n++)
		{
			e[n].UpdatePosition();
		}	
	}


	public void DrawWreckage(Graphics g)
	{
		int n;
		
		for	(n=0;n<30;n++)
		{
			d[n].DrawDebris(g);
		}	
		for	(n=0;n<30;n++)
		{
			e[n].DrawDebris(g);
		}	
	}


	// Retrieve the value of an applet parameter
	public String getParameter(String key, String def) 
	{
		return isStandalone ? System.getProperty(key, def) :
			(getParameter(key) != null ? getParameter(key) : def);
	}

	// Get info on the applet parameters
	public String[][] getParameterInfo() 
	{
		return null;
	}

	// Get applet information
	public String getAppletInfo() 
	{
		return "Applet Information";
	}

	// Initialize the applet
	
	public void initComponents() throws Exception
	{
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		setLocation(new java.awt.Point(0, 0));
		setLayout(null);
		setSize(new java.awt.Dimension(800, 600));


// END GENERATED CODE
	}
	


}
