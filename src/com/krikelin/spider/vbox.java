package com.krikelin.spider;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class vbox extends Element {

	public vbox(SPWebView host) {
		super(host);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public void assignChildren() {
		// TODO Auto-generated method stub
		super.assignChildren();
		int count = this.getChildren().size();
		/***
		 * 1.
		 * This block of code will decide
		 * which elements who are belonging to the
		 * the fixed size left and right side,
		 * and the dynamic midle positiioned elements
		 */
		ArrayList<Element> staticFirstElements = new ArrayList<Element>();
		ArrayList<Element> dynamicMiddle = new ArrayList<Element>();
		ArrayList<Element> staticLastElements = new ArrayList<Element>();
		int sts = 0;
		int preFlex = 0;
		for(int i=0; i < count ;i++)
		{
			Element elm = getChildren().get(i);
			if(elm.getFlex() != preFlex)
			{
				sts++;
			}
			preFlex = elm.getFlex();
			// Decide what collection of element it should be assigned to
			switch(sts)
			{
			case 0:
				staticFirstElements.add(elm);
				break;
			case 1:
				dynamicMiddle.add(elm);
				break;
			case 2:
				staticLastElements.add(elm);
			}
		}
		/***
		 * 2.
		 * Next step is to assign bounds for the static blocks
		 */
		int leftHeight = 0;
		for(Element e: staticFirstElements)
		{
		
			leftHeight+=e.getBounds().height-getPadding()*2;
		}
		int rightHeight = 0;
		for(Element e: staticLastElements)
		{
			rightHeight+=e.getBounds().height-getPadding()*2;
		}
		/**
		 * Upon this, calculate the dynamic block sizes
		 */
		int space = getBounds().height - (leftHeight+rightHeight);
		// Then calculate individual sizes
		int elmHeight = 0;
		try
		{
			elmHeight = space /dynamicMiddle.size();
		}
		catch(Exception e)
		{
			
		}
		
		int top=0;
		// Then assign the proportions of the left page
		for(Element elm : staticFirstElements)
		{
			elm.setBounds(new Rectangle(getPadding(),top+getPadding(),getBounds().width-getPadding()*2,elm.getBounds().height-getPadding()*2));
			top += elm.getBounds().height + getPadding();
		}
		// Then assign the proportions of the right page
		for(int x= 0; x < staticLastElements.size(); x++)
		{
			
			Element elm = staticLastElements.get(staticLastElements.size()-1-x);
			top -= elm.getBounds().height - getPadding()*2;
			elm.setBounds(new Rectangle(0,getBounds().height-top,getBounds().width-getPadding()*2,elm.getBounds().height-getPadding()*2));
		}
		// Then assign the width to the children
		int i=0;
		for(Element elm : dynamicMiddle)
		{
			elm.setBounds(new Rectangle(getPadding(),leftHeight+elmHeight*i+getPadding(),getBounds().width-getPadding()*2,elmHeight-getPadding()*2));
			
			i++;
		}
	}

	@Override
	public Object getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g, Rectangle bounds) {
		// TODO Auto-generated method stub
		super.paint(g, bounds);
		drawChildren(g, bounds);
	}

	@Override
	public void mouseOver(Point relativePoint, Point absolutePoints) {
		// TODO Auto-generated method stub
		super.mouseOver(relativePoint, absolutePoints);
	}

	@Override
	public void mouseDown(Point relativePoint,Point absolutePoint) {
		// TODO Auto-generated method stub
		super.mouseDown(relativePoint,absolutePoint);
		for(Element e : getChildren())
		{
			if(this.childAt(relativePoint) == e)
			{
				e.mouseDown(new Point(absolutePoint.x - relativePoint.x,absolutePoint.y - relativePoint.y),relativePoint);
				
			}
		}
	}

	@Override
	public void backendRender(Object... args) {
		// TODO Auto-generated method stub
		for(Element child : getChildren())
		{
			child.backendRender(args);
		}
	}

	@Override
	public void onLoad(Object... args) {
		// TODO Auto-generated method stub
		
	}

	
}
