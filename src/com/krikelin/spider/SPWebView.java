package com.krikelin.spider;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
		arg0.setColor(getSkin().getBackgroundColor());
		arg0.fillRect(0, 0, getWidth(), getHeight());
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
	public Element deserialize(Document d) throws NoSuchMethodException
	{
		
		for(int i=0; i < d.getDocumentElement().getChildNodes().getLength(); i++){
			
			org.w3c.dom.Node elm = d.getDocumentElement().getChildNodes().item(i);
			if(elm instanceof org.w3c.dom.Element){
				Element _elm = deserialize((org.w3c.dom.Element)elm);
				return _elm;
			}
			
		}
		return null;
	}
	public Element deserialize(Node c) throws NoSuchMethodException
	{
		
		
		if (c instanceof org.w3c.dom.Element)
		{
			org.w3c.dom.Element elm = (org.w3c.dom.Element)c;
		
			try {
				Element _elm = (Element)Class.forName("com.krikelin.spider."+elm.getTagName().toLowerCase()).getConstructors()[0].newInstance(this);
			
				for(int i=0; i <elm.getAttributes().getLength(); i++)
				{
					Node attrib = elm.getAttributes().item(i);
					String val = attrib.getNodeValue();
					String attribute = attrib.getNodeName();
					String methodName = capitalize(attribute).trim();
					if(attribute.startsWith("src"))
					{
						int ec = 3;
					}
					try
					{
						
						int valN = Integer.valueOf(val);
						Class.forName("com.krikelin.spider."+elm.getTagName().toLowerCase()).getMethod("set"+methodName,java.lang.Integer.class).invoke(_elm,valN);
					}
					catch(Exception e)
					{
						try{
							_elm.getClass().getDeclaredMethod("set"+methodName, String.class).invoke(_elm, val);
						}
						catch(Exception e2){
							
						}
					}
					// set data of method
					
					
					
				
					
				}
				try
				{
					Node x= ((org.w3c.dom.Element)elm).getFirstChild();
					if(x instanceof org.w3c.dom.Text)
						if(x.getNodeValue() != null)
						{
							_elm.getClass().getMethod("setData", java.lang.String.class).invoke(_elm,x.getNodeValue());
							
						}
				}
				catch(Exception e)
				{
					
				}
				NodeList childNodes = elm.getChildNodes();
				int length = childNodes.getLength();
				for(int j=0; j < length; j++)
				{
					
					Node welm = childNodes.item(j);
					Node parent = welm.getParentNode();
					
					if(welm instanceof org.w3c.dom.Element)
					{
						if(welm.getParentNode()!=(elm))
							continue;
						Element f = deserialize(welm);
						if(f != null)
						{
				//			f.setBounds(new Rectangle(0,0,_elm.getWidth(),_elm.getHeight()));
							_elm.getChildren().add(f);
						}
					}
					}
				_elm.onLoad((Object[])null);
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
	
		try {
			Document c = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(cf);
			Element elm = deserialize(c.getDocumentElement());
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
	public void loadMarkup(InputStream in,Properties props)
	{
	
		try {
			Document c = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			Element elm = deserialize(c);
			getElements().add(elm);
			elm.setBounds(new Rectangle(0,0,getWidth(),getHeight()));
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
	
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				for(Element elm : getElements())
				{
					elm.mouseOver( e.getPoint());
					
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				for(Element elm : getElements())
				{
					elm.mouseDown( e.getPoint());
					
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				for(Element elm : getElements())
				{
					elm.mouseClicked(e.getPoint());
				}
			}
		});
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see com.krikelin.spotifysource.BufferedContainer#draw(java.awt.Graphics)
	 */
	
	
}
