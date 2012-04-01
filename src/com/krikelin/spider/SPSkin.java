/*
 * Copyright (C) 2011 Alexander Forselius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.krikelin.spider;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;





public abstract class SPSkin {
	public static final int IMG_PLAYBACK_ICON = 2800;
	public static final int IMG_PLAYBACK_ICON_SELECTED = 2800+1;
	public static final int IMG_PLAYBACK_ICON_DIFFERENT_VIEW = 2800+2;
	public static final int IMG_VERTICAL_DIVIDER = 280052;
	public static final int IMG_VERTICAL_DIVIDER_FG = 280086;
	
	public abstract Color getPlayingBg();
	public abstract Color getPlayingFg();
	public static final int MODE_NORMAL = 2000; 
	public static final int MODE_PLAYING = 2001;
	public static final int MODE_SELECTED = 2002;
	public abstract Image getDashedBackground();
	public abstract Image getComponentByName(String name);
	public static String stripe(String src,int count){
		if(src.length() > count){
			return src.substring(0,count)+"...";
		}
		return src;
	}
	public static String stripe(String src,int width, Font font, Graphics g){
		
		int xwidth = 0;
		for(int i=0; i< src.length() ; i++){
			xwidth+=g.getFontMetrics(font).charWidth(src.charAt(i));
			if(xwidth > width){
				return src.substring(0, i)+"...";
			}
		}
		
		return src;

	}
	/**
	 * Get css
	 * @return
	 */
	public String getCSS(){
		return "";
	}
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
	public void drawText(String str,Color color, Graphics g,int x,int y,Color shadowColor,int shadowX,int shadowY)
	{
	
		drawText(str,color,g,x,y,Color.black);
		
	}
	public void drawText(String str,Color color, Graphics g,int x,int y,boolean shadow)
	{
	
		drawText(str,color,g,x,y,Color.black);
		
	}
	/**
	 * @from http://www.javalobby.org/java/forums/t19183.html
	 * @param color
	 * @return
	 */
	public String toHTMLColor(Color color){
		String rgb = Integer.toHexString(color.getRGB());
		rgb = rgb.substring(2, rgb.length());
		return rgb;
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
//	public abstract NinePatch getButtonImage();
	
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
//	public abstract NinePatch getButtonPressedImage();
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
