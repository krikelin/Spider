package com.krikelin.spider;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class text extends Element {
	private SPWebView mHost;
	public text(SPWebView host) {
		super(host);
		this.mHost=host;
		// TODO Auto-generated constructor stub
	}



	@Override
	public void assignChildren() {
		// TODO Auto-generated method stub
		
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
		int col = 0 ; // column
		int row = 0; // row
		int x = 0,y = 0;
		String lineBuffer ="";
		String buffer = mText;
		g.translate(bounds.x, bounds.y);
		for(int i=0; i < buffer.length(); i++)
		{
			int wid = getBounds().width;
			// bounds for the current character
			Rectangle2D strBounds = g.getFontMetrics().getStringBounds(new char[]{buffer.charAt(i)},0,1, g);
			if(i < buffer.length()-1)
			{
				
				if(buffer.charAt(i+1 )== '\n' || x >=  wid - getPadding()*2)
				{
					col = 0;
					row += 1;
					y += strBounds.getHeight();
					x = 0;
				}
			}
			char character = buffer.charAt(i);
			//g.drawChars(new char[]{character},0+getPadding(),1+getPadding(), x, y);
			// increase the pointer's location
			getHost().getSkin().drawText(String.valueOf(character), getHost().getSkin().getForeColor(), g, x, y+20, true);
			col += 1;
			x += strBounds.getWidth();
		}
		g.translate(-bounds.x, -bounds.y);
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
	public void mouseOver(Point relativePoint, Point absolutePoints) {
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
