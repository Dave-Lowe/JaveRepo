/* 
	main.java

	Title:			lander
	Author:			default
	Description:	
*/

package lander;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class main extends Applet implements Runnable
{

//BEGIN GENERATED CODE
	// member declarations
// END GENERATED CODE

	/*  x & y are the coordinates of the rocket at any time */

	
	double initx, inity;
	
	/* Angle is the rocket's angle, and deltaand is it's angular velocity  */
	
	double initAngle;
	
	/* initdelatax and initdeltay determine the landers initial velocity */
	
	double initdeltax, initdeltay;
	
	
	/* thrust and anglethrust are constants which determine how powerful the engines are  */
	
	double initthrust, initanglethrust;
	

	/* Mass of Rockaet */
	
	double RMass;
	
		
	/* flags to show which events have taken place in the last 20 ms  */	
		
	boolean left, right, throttle;
	
	
	/* flag to show if the Rocket has crashed  */
	
	boolean crashed;
	
	
	
	Thread timer = null;
	
	
	/* Character and string to hold keypresses, and a Text field to capture them in */
	char letter[] = new char[10];
  	String c = new String();
	
	TextField input;
	
	
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
	Color Grey	 = new Color(128,128,128);
	
	
	/* Counter to determine when to restart the Applet after a crash  */
	
	
	int Counter;
	
	
	/* define objects 
	
		r is the pointer for the Rocket
		d[] is the pointers for the Rocket debris
		e[]	is the pointer for the Planet Debris
	*/
	
	
	Rocket r;
	
	Debris d[] = new Debris[60];
		

	boolean isStandalone = false;

	public void init() 
	{
		/* Create offscreen area and make buffer its graphics method  */
		
		offscreen 	= this.createImage(800,600);
					
		buffer		= offscreen.getGraphics();
	
		
		/*Declare Rocket Mass */
		
		RMass		= 100;
			
	
		/* Set up constants  */
		
		initthrust = 5;
		
		initanglethrust = 0.1;

		/* declare and draw text field we are going to use to read the keyboard */

		input = new TextField(1);
		
		add(input);
		
		input.setVisible(true);
		
		
		if (timer == null)
		{
			timer = new Thread(this);
			timer.start();
		}
	}
	
	
	public void start()
	{
	/* Set up initial variable values */
		
		
		initx = 0;
		inity = 30;
		
		initdeltax = 3;
		initdeltay = 0;
		
		// Set initial angle of pi/2 radians (90 deg. from the vertical)
		
		initAngle = 1.57079632;
		
				
		left     = false;
		right    = false;
		throttle = false;

		crashed  = false;		
		
		Counter = 0;
		
		r = new Rocket(initx,inity,initdeltax,initdeltay,initAngle,RMass,initthrust,initanglethrust,Grey);
		
		
	
	}
	
	public void stop()
	{
		timer = null;
	}
	
	
	
	
	public void run()
	{
	
		while (timer != null)
		{
			
			/* Sleep for 1/50th Second */
			
			try
			{
				Thread.sleep(20);
			}
			catch(InterruptedException e)
			{
			}
				

			
			/* Bounce rocket if required */
			
			
			
			if (r.GetYCoord() > 562)
			{
			  	if (r.GetSpeed() < 1.8)
				{
					r.SetYCoord(562);
					r.Bounce_y();
					r.Spin_x();
					r.Friction_x();
				}
				else
				{
					if (crashed == false)
					{
						crashed = true;
						InitialiseDebris();
					}
				}
									
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
			
			
			
			/*  Apply effect of Planet's gravity on Rocket */
			r.ApplyGravity();
		
				
			/* read the contents of our text field */
					
			c = input.getText();
			
			/* only check the value of the first character of the text field if the length of c is non-zero
				(i.e., if there's at least one character in it).  
				If you try to read the first character when there isn't one, you get a substring out
				of range error.  */		
			
			if (c.length() > 0)
			{
			
				// convert the string c into a character array called 'letter'
				
				letter = c.toCharArray();
			

				if (letter[0] == 'z')
				{
					left = true;
				}
			
			
			
				if (letter[0] == 'x')
				{
					right = true;
				}
			
					
				if (letter[0] == ' ')
				{
					throttle = true;
				}
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
			
			/*  empty the text field */
			
			input.setText("");
			
			/* reset out booleans ready for the next iteration */
			
			right = false;
			left  = false;
			throttle = false;				
			
				
		}	
	}
	
	
			
	
	public void update(Graphics g)
	{
		/* create the screen in the off screen buffer */
		
		/* Draw the black background  */
		buffer.setColor(black);
		buffer.fillRect(0,0,800,600);
		
		
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



				

	public void InitialiseDebris()
	{
		int n;
		
		for	(n=0;n<60;n++)
		{
			d[n] = new Debris(r.GetXCoord(),r.GetYCoord(),r.GetColor());
		}
			
			
	}


	public void UpdateDebris()
	{
		int n;
		
		for	(n=0;n<60;n++)
		{
			d[n].UpdatePosition();
		}
			
	}


	public void DrawWreckage(Graphics g)
	{
		int n;
		
		for	(n=0;n<60;n++)
		{
			d[n].DrawDebris(g);
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
