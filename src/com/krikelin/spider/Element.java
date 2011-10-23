package com.krikelin.spider;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;



/***
 * An drawable element in Spider markup language
 * @author Alex
 *
 */
public class Element {
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
	public void setWidth(Integer width)
	{
		mBounds = new Rectangle(0,0,width,getHeight());
	}
	public void setHeight(Integer height)
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
	
	protected int mFlex;
	public int getFlex()
	{
		return mFlex;
	}
	public void setFlex(Integer i)
	{
		mFlex=i;
	}
	private ArrayList<Element> mChildren = new ArrayList<Element>();
	protected Rectangle mBounds = new Rectangle();
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
			
			if(c.getBounds() == null)
			{
				c.setBounds(new Rectangle(0,0,0,0));
			}
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
		
		
		for(Element child : mChildren)
		{
			child.paint(g, new Rectangle(child.getBounds().x, child.getBounds().y,child.getBounds().width,child.getBounds().height));
		}
	
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
	private MouseListener mMouseListener;
	public MouseListener getMouseListener() {
		return mMouseListener;
	}
	public void setMouseListener(MouseListener mMouseListener) {
		this.mMouseListener = mMouseListener;
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
		public abstract void mouseOver(Point absolutePoints);
		public abstract void mouseUp(Point absolutePoints);
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
		public void mouseClicked(Point relativePoint) {
			// TODO Auto-generated method stub
			
		}
	}
	/***
	 * Gets the tag for the object
	 * @return 
	 */
	public  Object getTag() {
		return null;
	}
	/***
	 * Creates the elemenet
	 */
	public  void create() { 
		
	}
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
	public boolean inBounds(Point c)
	{
		assignChildren();
		if(c.x >= getBounds().x &&
			c.x <= getBounds().x + getWidth() &&
		c.y >= getBounds().y &&
		c.y <= getBounds().y + getHeight()){
			return true;
		}
		return false;
					
	}
	/**
	 * Occurs when mouse is over the element
	 * @param relativePoint
	 * @param absolutePoints
	 */
	public void mouseOver(Point absolutePoints){
		assignChildren();
		for(Element e: getChildren())
		{
			if(e.inBounds(absolutePoints))
				e.mouseOver(absolutePoints);
			else
				e.MouseLeave(absolutePoints);
				
		}
		if(getMouseListener()!= null)
		{
			getMouseListener().mouseOver(absolutePoints);
		}
	}
	protected void MouseLeave(Point absolutePoints) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Occurs when mouse is over the element
	 * @param relativePoint
	 * @param absolutePoints
	 */
	public void mouseUp(Point absolutePoints){
		assignChildren();
		for(Element e: getChildren())
		{
			if(e.inBounds(absolutePoints))
				e.mouseOver(absolutePoints);
				
		}
		if(getMouseListener()!= null)
		{
			getMouseListener().mouseOver(absolutePoints);
		}
	}
	/***
	 * Occurs on mouse down
	 * @param relativePoint
	 */
	public void mouseDown(Point absolutePoints){
		for(Element e: getChildren())
		{
			if(e.inBounds(new Point(absolutePoints)))
				e.mouseDown( absolutePoints);
				
		}
		if(getMouseListener()!= null)
		{
			getMouseListener().mouseDown(absolutePoints);
		}
	}
	/***
	 * Occurs on rendering at backend
	 * @param args
	 */
	public void backendRender(Object... args){ 
		
	}
	/**
	 * Occurs on load
	 * @param args
	 */
	public void onLoad(Object... args){
		
	}
	public void mouseClicked(Point absolutePoints) {
		// TODO Auto-generated method stub
		for(Element e: getChildren())
		{
			if(e.inBounds(absolutePoints))
				e.mouseClicked(absolutePoints);
				
		}
		if(getMouseListener()!= null)
		{
			getMouseListener().mouseClicked(absolutePoints);
		}
	}
	
}
