package com.krikelin.spider;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;


public class image extends Element
{
	@Override
	public Rectangle getBounds()
	{
		return super.mBounds;
	}
	private Image mImage;
	public image(SPWebView host)
	{
		super(host);
		
	}
	@Override
	public void mouseDown(Point absolutePoint)
	{
		if(mSrcMouseDown != null)
			setSrc(mSrcMouseDown);
	}
	private String mSrcMouseDown;
	private String mSrcHover;
	private String mSrcNormal;
	public void setSrc_mousedown(String resource)
	{
		mSrcMouseDown=resource;
	}
	public void setSrc_mouseover(String resource)
	{
		mSrcHover=resource;
		
	}
	public void setSrc_normal(String resource)
	{
		mSrcNormal=resource;
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
	@Override
	public void setHeight(Integer height)
	{
		super.mBounds = new Rectangle(0,0,getWidth(),height);
	}
	public void setSrc(String src)
	{
		mSrcNormal=src;
	}
	public void setBackground(String src)
	{
		try {
			// If the address start with res, load an resource image
			if(src.startsWith("res:")){
				mImage = getHost().getSkin().getComponentByName(src.replace("res:",""));
				return;
			}
			downloadImage(new URL(src));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	protected void MouseLeave(Point absolutePoints) {
		// TODO Auto-generated method stub
		super.MouseLeave(absolutePoints);
		setBackground(mSrcNormal);
		
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
			g.drawImage(mImage, bounds.x, bounds.y,bounds.width,bounds.height,null);
	}
	@Override
	public void mouseOver( Point absolutePoints) {
		// TODO Auto-generated method stub
		if(mSrcHover != null)
			setBackground(mSrcHover);
		
	}
	@Override
	public void backendRender(Object... args) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoad(Object... args) {
		// TODO Auto-generated method stub
		setBackground(mSrcNormal);
	}
	public void setImage(Image mImage) {
		this.mImage = mImage;
	}

	public Image getImage() {
		return mImage;
	}
	
}
