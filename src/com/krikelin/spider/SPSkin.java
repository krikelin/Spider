package com.krikelin.spider;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Hashtable;




public abstract class SPSkin {
	public static final int IMG_PLAYBACK_ICON = 2800;
	public static final int IMG_PLAYBACK_ICON_SELECTED = 2800+1;
	public static final int IMG_PLAYBACK_ICON_DIFFERENT_VIEW = 2800+2;
	public static final int IMG_VERTICAL_DIVIDER = 280052;
	public static final int IMG_VERTICAL_DIVIDER_FG = 280086;
	
	/**
	 * Gets component by name
	 * @param name
	 * @return
	 */
	public abstract Image getComponentByName(String name);
	public abstract Color getPlayingBg();
	public abstract Color getPlayingFg();
	public static final int MODE_NORMAL = 2000; 
	public static final int MODE_PLAYING = 2001;
	public static final int MODE_SELECTED = 2002;
	/**
	 * Draws popuarity
	 * @param popularity
	 * @param countTaks
	 * @param g
	 * @param x
	 * @param y
	 */
	
	public abstract void drawPopularity(float popularity, int countTaks,Graphics g,int x,int y,int height,int mode);
	public void drawText(String str,Color color, Graphics g,int x,int y,Color shadowColor )
	{
	
		g.setColor(color);
		
		g.drawString(str,x,y+1);
		
	}
	public void drawText(String str,Color color, Graphics g,int x,int y,boolean shadow)
	{
	
		drawText(str,color,g,x,y,Color.black);
		
	}
	/**
	 * Get color from resource id
	 * @param id
	 * @return
	 */
	public abstract Color getColorById(int id); 
	/***
	 * Gets an image from the specified id
	 * @param id
	 * @return
	 */
	public abstract Image getImageById(int id); // ID for custom images
	/**
	 * Returns button image
	 * @return
	 */
	public abstract Image getButtonImage();
	
	/**
	 * Returns image for the header
	 * @return
	 */
	public abstract Image getHeaderImage();
	
	/**
	 *
	 * @return Image returns shadow image
	 */
	public abstract Image getShadowImage();
	
	/**
	 * Image for the bottom
	 * @return
	 */
	public abstract Image getBottomImage();
	public abstract Image getButtonPressedImage();
	public abstract Color getBackgroundColor();
	public abstract Color getForeColor();
	public abstract Color getLinkColor();
	public abstract Color getAlternateForeColor();
	public abstract Color getAlternateBgColor();
	public abstract Image getSectionBackground();
	public abstract Image getSectionForeground();
	public abstract Image getTabBarBackground();
	public abstract Image getActiveTabBackground();
	public abstract Image getTabBackground();
	public abstract Color getTabForeColor();
	public abstract Color getActiveTabForeColor();
	public abstract Color getSelectionBg();
	public abstract Image getSelectionBackground();
	public abstract Color getSelectionFg();
	public abstract Image getReleaseImage();
	
	public abstract Font getFont();
	
}
