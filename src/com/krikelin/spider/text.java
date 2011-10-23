package com.krikelin.spider;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class text extends Element {
	public text(SPWebView host) {
		super(host);
	}


	public void setData(String data)
	{
		mText=data;
	}
	@Override
	public void assignChildren() {
		// TODO Auto-generated method stub
		
	} 
	@Override
	public void setHeight(Integer height)
	{
		mBounds = new Rectangle(0,0,getWidth(),height);
	}
	@Override
	public void setFlex(Integer flex)
	{ 
		mFlex=flex;
	}
	private String mText;
	private Object mTag;
	public String getText()
	{
		return mText;
	}
	public void setText(String mText)
	{
		this.mText = mText;
	}
	@Override
	public void paint(Graphics g, Rectangle bounds) {
		// TODO Auto-generated method stub
		super.paint(g, bounds);
		if(mText == null)
			return;
		int x = getBounds().x,y = getBounds().y + 15;
		String buffer = mText;
		for(int i=0; i < buffer.length(); i++)
		{
			int wid = bounds.width;
			// bounds for the current character
			Rectangle2D strBounds = g.getFontMetrics().getStringBounds(new char[]{buffer.charAt(i)},0,1, g);
			if(i < buffer.length()-1)
			{
				
				if(buffer.charAt(i+1 )== '\n' || x >=  wid - getPadding()*2)
				{
					y += strBounds.getHeight();
					x = getBounds().x;
				}
			}
			char character = buffer.charAt(i);
			//g.drawChars(new char[]{character},0+getPadding(),1+getPadding(), x, y);
			// increase the pointer's location
			getHost().getSkin().drawText(String.valueOf(character), getHost().getSkin().getForeColor(), g, x, y, true);
			x += strBounds.getWidth();
		}
	}

	@Override
	public Object getTag() {
		// TODO Auto-generated method stub
		return mTag;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseOver(Point absolutePoints) {
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
