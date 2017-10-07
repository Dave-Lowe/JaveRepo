package hunny;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Pot extends Applet 
{


	private boolean selected;
	private int x,y,height,width, CentreX, CentreY;
	
	private int xOffset, yOffset;
	
	private Color IconColor = new Color(255,0,0);
	
	private Image Icon;
	
	
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
// END GENERATED CODE

	public Pot(int initx, int inity, int initWidth, int initHeight, int initCentreX, int initCentreY, Image Pic)
	{
		x 			= 	initx;
		y 			= 	inity;
		height		=	initHeight;
		width		=	initWidth;
		CentreX		=	initCentreX;
		CentreY		= 	initCentreY;
		
		Icon 		= 	Pic;
		
		selected	=	false;
		
		xOffset		=	0;
		yOffset		=	0;
	}


	public void SelectIcon(int mousex, int mousey)
	{
		xOffset = mousex - x;
		yOffset = mousey - y;
		
		
		if (xOffset >= 0 && xOffset <= width)
		{
			if (yOffset >= 0 && yOffset <= height)
			{
				selected = true;
			}
		}
	}
	
	public boolean IsSelected()
	{
		return(selected);
	}
	
	
	public void DragIcon(int mousex, int mousey)
	{
		x = mousex - xOffset;
		y = mousey - yOffset;
	}
	
	
	public void UnSelectIcon()
	{
		selected = false;
	}
	
	public int GetXCoord()
	{
		return (x + CentreX);
	}
	
	public int GetYCoord()
	{
		return(y + CentreY);
	}		
	
	
	public void DrawIcon(Graphics g)
	{
		g.setColor(IconColor);
		g.drawImage(Icon,x,y,width,height,this);
	}	
			





}
