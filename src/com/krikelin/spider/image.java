package com.krikelin.spider;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;


public class image extends Element
{
	private JComponent mContext;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2226107120577730295L;
	private Image mImage;
	public image(SPWebView host,Image mImage)
	{
		super(host);
		this.mImage = mImage;
		
	}
	private URL mUri;
	public static Hashtable<String,Image> bitmaps = new Hashtable<String,Image>();
	private void download(URL source){
		
		InputStream IS;
		if(bitmaps.containsKey(source.toString()) )
		{
			mUri = source;
			mImage = bitmaps.get(source);
			
			return;
		}
		try {
			mUri = source;
			IS = source.openStream();
			
		//	bitmaps.put(source.toString(),mContext.getSkin().getReleaseImage());
			Image bitmap =	ImageIO.read(IS);
			
			mImage= bitmap;
			bitmaps.put(source.toString(),bitmap);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	public void downloadImage(final URL source)
	{
		try {
			Runnable c = new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					download(source);
					
				}
					
			};
			
			if(!SwingUtilities.isEventDispatchThread())
			{
				
				SwingUtilities.invokeAndWait(c);
			
			}
			else
			{
				SwingUtilities.invokeLater(c);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void assignChildren() {
		// TODO Auto-generated method stub
		
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
		if(mUri != null)
		{
			mImage = bitmaps.get(mUri.toString());
		}
		if(mImage != null)
			g.drawImage(mImage, 0, 0,getWidth(),getHeight(),null);
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
	public void setImage(Image mImage) {
		this.mImage = mImage;
	}

	public Image getImage() {
		return mImage;
	}
	
}
