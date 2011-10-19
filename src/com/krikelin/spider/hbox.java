package com.krikelin.spider;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

import org.omg.CORBA.Bounds;

public class hbox extends Element {
	
	@Override
	public void paint(Graphics g, Rectangle bounds) { 
		// TODO Auto-generated method stub
		super.paint(g, bounds);
		drawChildren(g, bounds);
		
	}

	public hbox( SPWebView host) {
		super(host);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Assigns the children after the weight
	 */
	@Override
	public void assignChildren() {
		super.assignChildren();
		// TODO Auto-generated method stub
		int count = this.getChildren().size();
		int cellWidth = getBounds().width/count;
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
		int leftWidth = 0;
		for(Element e: staticFirstElements)
		{
			leftWidth+=e.getBounds().width;
		}
		int rightWidth = 0;
		for(Element e: staticLastElements)
		{
			rightWidth+=e.getBounds().width;
		}
		/**
		 * Upon this, calculate the dynamic block sizes
		 */
		int space = getBounds().width - (leftWidth+rightWidth);
		// Then calculate individual sizes
		
		// Then assign the width to the children
		int i=0;
		
		int left=0;
		// Then assign the proportions of the left page
		for(Element elm : staticFirstElements)
		{
			elm.setBounds(new Rectangle(left+getPadding(),getPadding(),elm.getBounds().width-getPadding()*2,getBounds().height-getPadding()*2));
			left += elm.getBounds().width;
		}
		int right = getBounds().width;
		// Then assign the proportions of the right page
		for(int x= 0; x < staticLastElements.size(); x++)
		{
			
			Element elm = staticLastElements.get(staticLastElements.size()-1-i);
			right -= elm.getBounds().width -getPadding()*2;
			elm.setBounds(new Rectangle(right+getPadding(),getPadding(),elm.getBounds().width-getPadding()*2,getBounds().height-getPadding()*2));
		}
		
		int elmWidth = 0;
		if(dynamicMiddle.size() > 0)
			elmWidth = space /dynamicMiddle.size();
		space = right - space;
		for(Element elm : dynamicMiddle)
		{
			elm.setBounds(new Rectangle(left+elmWidth*i+getPadding(),getPadding(),elmWidth-getPadding()*2,getBounds().height-getPadding()*2));
			
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
	public void mouseOver(Point relativePoint, Point absolutePoints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDown(Point relativePoint,Point absolutePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backendRender(Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Object... args) {
		// TODO Auto-generated method stub
		
	}

	
}
