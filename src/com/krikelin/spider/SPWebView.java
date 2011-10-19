package com.krikelin.spider;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.script.ScriptException;
import javax.swing.JComponent;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
/***
 * webview for SPView
 * @author Alex
 *
 */
public class SPWebView extends JComponent {
	private SPSkin mSkin= new DefaultSkin();
	public SPWebView()
	{
		mSkin= new DefaultSkin();
		setBackground(mSkin.getBackgroundColor());
	}
	public SPSkin getSkin()
	{
		return mSkin;
	}
	@Override
	public void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paint(arg0);
		if(getElements().size()> 0)
		{
			Element c = getElements().get(0);
		
			
		
			
			c.setBounds(new Rectangle(0,0,getWidth(),getHeight()));
			c.paint(arg0, new Rectangle(0,0,getWidth(),getHeight()));
			
		}
		
	}
	public void setSkin(SPSkin mSkin)
	{
		this.mSkin=mSkin;
	}
	private ArrayList<Element> mElements = new ArrayList<Element>();
	public ArrayList<Element> getElements()
	{
		return mElements;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1796099027823630657L;
	private JComponent mContext;
	public JComponent getContext()
	{
		return mContext;
	}
	/***
	 * @from http://forums.macrumors.com/showthread.php?t=368861
	 * @param source
	 */
	public String capitalize(String source)
	{
		String bob = "";
		for (String string : source.split(" ")) {
			bob+=(string.substring(0, 1).toUpperCase());
			bob+=(string.substring(1).toLowerCase());
			bob+=(" ");
		}
		return bob;
	}
	String cf = "";
	
	public Element deserialize(Node c) throws NoSuchMethodException
	{
		
		
		if (c instanceof org.w3c.dom.Element)
		{
			org.w3c.dom.Element elm = (org.w3c.dom.Element)c;
		
			try {
				Element _elm = (Element)Class.forName(elm.getTagName().toLowerCase()).getConstructors()[0].newInstance(getContext(),this);
				
				for(int i=0; i <elm.getAttributes().getLength(); i++)
				{
					Node attrib = elm.getAttributes().item(i);
					String val = attrib.getNodeValue();
					String attribute = attrib.getNodeName();
					try
					{
						int valN = Integer.valueOf(val);
						elm.getClass().getMethod("set"+capitalize(attribute),Integer.class).invoke(elm,valN);
					}
					catch(Exception e)
					{
						_elm.getClass().getMethod("set"+capitalize(attribute), String.class).invoke(elm, val);
						
					}
					for(int j=0; j <elm.getChildNodes().getLength(); j++)
					{
						
						Element f = deserialize(elm.getChildNodes().item(i));
						_elm.getChildren().add(f);
					}
					
				}
				return _elm;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	
		return null;
	}
	public void loadMarkup(String markup,Properties props)
	{
		cf = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		{

			@Override
			public synchronized void write(byte[] arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				super.write(arg0, arg1, arg2);
				cf+=arg0;
			}
		};
					
		/*try {
			
			
			Casper.eval(new ByteArrayInputStream(markup.getBytes()), baos, props);
		
		} catch (CasperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Render content
		catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 
		try {
			Document c = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(cf);
			Element elm = deserialize(c);
			getElements().add(elm);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	Timer mRefresher;
	public SPWebView(JComponent mContext) {

		mRefresher = new Timer();
		mRefresher.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				repaint();
			}
			
		}, 0,10);
		this.mContext=mContext;
		mSkin = new DefaultSkin();
		setBackground(mSkin.getBackgroundColor());
		//setBackground(mContext.getSkin().getBackgroundColor());
		
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see com.krikelin.spotifysource.BufferedContainer#draw(java.awt.Graphics)
	 */
	
	
}
