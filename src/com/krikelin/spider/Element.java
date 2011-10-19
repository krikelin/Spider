package com.krikelin.spider;

import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JComponent;



/***
 * An drawable element in Spider markup language
 * @author Alex
 *
 */
public abstract class Element {
	private int mPadding;
	public int getPadding()
	{
		return mPadding;
	}
	public void setPadding(int mPadding){
		this.mPadding=mPadding;
	}
	private int mMargin;

	public int getMargin() {
		return mMargin;
	}
	public int getWidth(){
		return mBounds.width;
	}
	public int getHeight(){
		return mBounds.height;
	}
	public void setWidth(int width)
	{
		mBounds = new Rectangle(0,0,width,getHeight());
	}
	public void setHeight(int height)
	{
		mBounds = new Rectangle(0,0,getWidth(),height);
	}
	public void setMargin(int mMargin) {
		this.mMargin = mMargin;
	}
	/**
	 * Get context
	 * @return JComponent the window belonging to the element
	 */
	public  SPWebView getContext(){
		return mContext;
	}
	public SPWebView getHost()
	{
		return mHost;
	}
	private SPWebView mContext;
	private SPWebView mHost;
	public Element(SPWebView host)
	{
		mHost=host;
	}
	
	private int mFlex;
	public int getFlex()
	{
		return mFlex;
	}
	public void setFlex(int i)
	{
		mFlex=i;
	}
	private ArrayList<Element> mChildren = new ArrayList<Element>();
	private Rectangle mBounds = new Rectangle();
	/***
	 * Gets the bounds
	 * @return
	 */
	public Rectangle getBounds()
	{
		return mBounds;
	}
	/**
	 * Sets the bounds
	 * @param rect
	 */
	public void setBounds(Rectangle rect)
	{
		mBounds = rect;
	
	}
	public ArrayList<Element> getChildren()
	{
		return mChildren;
	}
	/***
	 * Assign children's bounds
	 */
	public void assignChildren(){
		for(Element c : getChildren())
		{
			if(c.getBounds().width <= 0)
			{
				c.getBounds().width = getWidth();
			}
			if(c.getBounds().height <= 0)
			{
				c.getBounds().height = getHeight();
			}
		}
	}
	/***
	 * draws the children
	 * @param d
	 */
	public void drawChildren(Graphics g,Rectangle bounds)
	{
		assignChildren();
		int x = getBounds().x;
		int y = getBounds().y;
		g.translate(x, y);
		for(Element child : mChildren)
		{
			child.paint(g, new Rectangle( child.getBounds().x, child.getBounds().y,child.getBounds().width,child.getBounds().height));
		}
		g.translate(-x, -y);
	}

	/***
	 * gets the child at
	 * @param point
	 * @return
	 */
	public Element childAt(Point point)
	{
		assignChildren();
	
		
		for(Element child : mChildren)
		{
			if(point.x >= child.getBounds().x &&
				point.x <= child.getBounds().x +child.getBounds().width &&
				point.y >= child.getBounds().y &&
				point.y <= child.getBounds().y + child.getBounds().height
					)
			{
				Element dchild = child.childAt(new Point(point.x+child.getBounds().x,point.y+child.getBounds().y));
				if(dchild != null)
				{
					return dchild;
				}
				return child;
				
			}
		}
		return null;
		
	}
	private Hashtable<String,Object> attributes = new Hashtable<String,Object>();
	/**
	 * Sets the markup attribute of the object
	 * @param key
	 * @param val
	 */
	public void setAttribute(String key,Object val)
	{
		
		attributes.put(key, val);
	}
	/**
	 * Gets the attribute
	 * @param key
	 */
	public Object getAttribute(String key,Object defaultValue)
	{
		try{
		return attributes.get(key);
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}
	/***
	 * MouseListener adapter
	 * @author Alex
	 *
	 */
	public abstract class MouseListener{
		public abstract void onClick(Element src);
		/**
		 * Occurs when mouse is over the element
		 * @param relativePoint
		 * @param absolutePoints
		 */
		public abstract void mouseOver(Point relativePoint,Point absolutePoints);
		/***
		 * Occurs on mouse down
		 * @param relativePoint
		 */
		public abstract void mouseDown(Point relativePoint);
		/***
		 * Occurs on rendering at backend
		 * @param args
		 */
		public abstract void backendRender(Object... args);
		/**
		 * Occurs on load
		 * @param args
		 */
		public abstract void onLoad(Object... args);
	}
	/***
	 * Gets the tag for the object
	 * @return
	 */
	public abstract Object getTag();
	/***
	 * Creates the elemenet
	 */
	public abstract void create();
	/***
	 * Paints the element on the board. The x and y coordinates 
	 * is relative to the scrolling and anchors
	 * @param g
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void paint(Graphics g,Rectangle bounds)
	{
		// Draws the container's children
	}
	/**
	 * Occurs when mouse is over the element
	 * @param relativePoint
	 * @param absolutePoints
	 */
	public abstract void mouseOver(Point relativePoint,Point absolutePoints);
	
	/***
	 * Occurs on mouse down
	 * @param relativePoint
	 */
	public void mouseDown(Point relativePoint,Point absolutePoints){
		
	}
	/***
	 * Occurs on rendering at backend
	 * @param args
	 */
	public abstract void backendRender(Object... args);
	/**
	 * Occurs on load
	 * @param args
	 */
	public abstract void onLoad(Object... args);
	
}
