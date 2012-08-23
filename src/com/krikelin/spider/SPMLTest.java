package com.krikelin.spider;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SPMLTest {
	/**
	 * Creates an test label
	 * @param text
	 * @param flex
	 * @param webview
	 * @return 
	 */
	protected static text createText(String text,int flex,SPWebView webview,int width)
	{
		text d = new text(webview);
		d.setText(text);
		d.setFlex(flex);
		d.setHeight(20);
		d.setWidth(width);
		return d;
	}
	/**
	 * 
	 */
	protected static Element Spider(SPWebView webview,int flex,int box_type,int count,int width)
	{
		Element box = null;
		switch(box_type)
		{
		case 0:
			box = new hbox(webview);
			break;
		case 1:
			box = new vbox(webview);
			break;
		
		}
		for(int i=0; i < count; i++)
		{
			int xflex = 0;
			if(i == 0)
				xflex = 0;
			if(i > 0 && i < count -1) 
				xflex = 1;
			box.getChildren().add(createText(String.format("test %s",i),xflex,webview,120));
		}
		box.setWidth(width);
		box.setFlex(flex);
		return box;
		
	} 
	public static void main(String[] args) {
		String path = System.getProperty("java.library.path");
		System.out.println(path);
		JFrame frame = new JFrame();
		JComponent sw = null;
		SPWebView webview = new SPWebView(sw);
		InputStream stream = SPMLTest.class.getResourceAsStream("boxmodel.xml");
		String c = new Scanner(stream).useDelimiter("\\A").next();
		webview.loadMarkup(c, null);
		frame.add(webview);
 		frame.show(); 
 		Timer t = new Timer();
 		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}, 1, 1000);
	} 
}
