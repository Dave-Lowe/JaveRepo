/* 
	Newton.java

	Author:			Dave Lowe
	Description:	Planet Object. Models a simple Planet.  Based on Rocket Method but with no thrust.
					
*/

package newton;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Planet extends Applet
{

	/*  x & y are the coordinates of the rocket at any time

	deltax and deltay are the horezontal and vertical components of the Rocket's velocity */

	private double x,y;
	private double deltax,deltay;
	
	
	/* Mass hold the mass of the Planet  */
	
	
	private double mass;
	
	
	/* Radius of planet  */
	
	private double radius;
		
		
	/* horizontal and vertical component of planets gravity */
		
	private double gravx, gravy;
		
		
	/* Colour of Planet */
		
	private Color PlanetColor = new Color(255,0,0);
	private Color BackgroundColor = new Color(0,0,0);
	
	/* Variables to determine if and where to draw an impact crater  
		impact x and impact y give the location and size, and crater tells the draw function whether to draw it
	*/
	
	double impactx, impacty, craterRadius;
	
	boolean crater;			
	
	
	
	boolean isStandalone = false;

	public Planet(double initx, double inity, double initmass, double initr, Color c, Color b) 
	{
	
	
		/* Set up constants  */
		
		
		mass = initmass;
		
		radius = initr;
		
		
				
		/*  Set up initial Values */
		
		
		x = initx;
		y = inity;
		
		
		deltax = 0;
		deltay = 0;
		
	
		gravx	 = 0;
		gravy	 = 0;
		
		PlanetColor = c;
		
		BackgroundColor = b;
		
		crater = false;
						
	}
	
	
				
	
	
	
	public void ApplyGravity(double planetx, double planety, double GMm)
	{
		double rsquared, theta, distx, disty, grav;
		
		/*  find the position of the Planet to the Object in polar coordinates */
		
		
		distx = x - planetx;
		disty = y - planety;
		
		/* actually only need r^2 for this function */
		
		rsquared = java.lang.Math.pow(distx,2) + java.lang.Math.pow(disty,2);

		
		theta = java.lang.Math.atan2(disty,distx);
		
		
		/* calculate the horizontal and vertical components of gravity  */
		
		
		
		
		grav = GMm/rsquared;
		gravx = grav * java.lang.Math.cos(theta);
		gravy = grav * java.lang.Math.sin(theta);
		
		deltax	= deltax - gravx;
		deltay	= deltay - gravy;
	}
	
	
	public void UpdatePosition()
	{	
		
		/*Calculate new y position  */
		
		y = y + deltay;
		
		
		/* Calculate new x position  */
		
		x = x + deltax;

	}		 
			
		
	public double FindRange (double x1, double y1)
	{
		double rsquared, distx, disty;
		
		distx = x - x1;
		disty = y - y1;
		
		rsquared = java.lang.Math.pow(distx,2) + java.lang.Math.pow(disty,2);
		
		return(java.lang.Math.sqrt(rsquared));
	}	


	public void SetCrash(double x1, double y1, double deltax1, double deltay1, double mass1, boolean showCrater)
	{	
		/*set flag to show impact crater or not and find relative position of crater */
		
		crater = showCrater;
		impactx = x1 - x;
		impacty = y1 - y;
		
		
		/*  Crater Radius is Proportional to the momentum of the rocket  */
		
				
		craterRadius = (java.lang.Math.sqrt(java.lang.Math.pow(deltax1,2) +
											java.lang.Math.pow(deltay1,2))* mass)/500;
		
		
		/* Apply effect of momentum imparted by impact to the planet's velocty */
		
		deltax = deltax + ((deltax1*mass1)/(mass));
		deltay = deltay + ((deltay1*mass1)/(mass));
		
		
	}
	
		
	public double GetXCoord ()
	{
		return(x);
	}
	
	
	public double GetYCoord ()
	{
		return(y);
	}
	
	
	
	public double GetMass ()
	{
		return(mass);
	}
	
	
	public double GetRadius ()
	{
		return(radius);
	}
	
	public Color GetColor ()
	{
		return(PlanetColor);
	}
			
		
	public void SetXCoord(double x1)
	{
		x=x1;
	}
	
	public void SetYCoord(double y1)
	{
		y=y1;
	}
	
	
		
			
	
	public void DrawPlanet(Graphics g)
	{
		int R,D;
		
		R = (int)radius;
		
		D = R * 2;
		
		/* Draw the Rocket  */
		
		g.setColor(PlanetColor);
		g.fillOval((int)x-R,(int)y-R,D,D);
		
		/*draw crater if required  */
		
		if (crater)
		{
			g.setColor(BackgroundColor);
			g.fillOval((int)(impactx+x)-(int)craterRadius,
						(int)(impacty+y)-(int)craterRadius,
						(int)craterRadius*2,
						(int)craterRadius*2);
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
