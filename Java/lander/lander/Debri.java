/* 
	Newton.java

	Author:			Dave Lowe
	Description:	Debris Object. Models a simple lump of debris.  Based on Planet Method but with no Mass or radius.
					
*/

package lander;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Debris extends Applet
{

	/*  x & y are the coordinates of the Debri at any time

	deltax and deltay are the horezontal and vertical components of the Rocket's velocity */

	private double x,y;
	private double deltax,deltay;
	
	
		
	/* Colour of Debri */
		
	private Color DebrisColor = new Color(255,0,0);
	
			
	
	boolean isStandalone = false;

	public Debris(double initx, double inity, Color c) 
	{
	
	
		/*  Set up initial Values */
		
		
		x = initx;
		y = inity;
		
		
		deltax = (java.lang.Math.random()*10 - 5);
		deltay = (java.lang.Math.random()*10 - 5);
		
	
		
		DebrisColor = c;
						
	}
	
	
	
	
	public void UpdatePosition()
	{	
		
		/*Calculate new y position  */
		
		ApplyGravity();
		
		y = y + deltay;
		
		
		/* Calculate new x position  */
		
		x = x + deltax;

	}		 
	
	public void ApplyGravity()
	{
		deltay = deltay + 0.05;
	}
			
		
	public double FindRange (double x1, double y1)
	{
		double rsquared, distx, disty;
		
		distx = x - x1;
		disty = y - y1;
		
		rsquared = java.lang.Math.pow(distx,2) + java.lang.Math.pow(disty,2);
		
		return(java.lang.Math.sqrt(rsquared));
	}	


	public double GetXCoord ()
	{
		return(x);
	}
	
	
	public double GetYCoord ()
	{
		return(y);
	}
	
	
	
	public Color GetColor ()
	{
		return(DebrisColor);
	}
			
		
	public void SetXCoord(double x1)
	{
		x=x1;
	}
	
	public void SetYCoord(double y1)
	{
		y=y1;
	}
	
	
		
			
	
	public void DrawDebris(Graphics g)
	{
		
		/* Draw the Debris  */
		
		g.setColor(DebrisColor);
		g.fillOval((int)x-1,(int)y-1,2,2);
			
		
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
