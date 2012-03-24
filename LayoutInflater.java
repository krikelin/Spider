package android.view;
import java.util.HashMap;

import javax.swing.JComponent;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.krikelin.spotifysource.Context;


public class LayoutInflater{
	HashMap<Integer, JComponent> components = new HashMap<Integer, JComponent>();
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
	private JComponent rootComponent;
	public JComponent deserialize(Node c) throws NoSuchMethodException
	{
		
		
		if (c instanceof org.w3c.dom.Element)
		{
			org.w3c.dom.Element elm = (org.w3c.dom.Element)c;
		
			try {
				JComponent _elm = (JComponent)Class.forName("javax.swing."+elm.getTagName().toLowerCase()).getConstructors()[0].newInstance(this);
				
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
						Class.forName("javax.swing."+elm.getTagName().toLowerCase()).getMethod("set"+methodName,java.lang.Integer.class).invoke(_elm,valN);
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
							_elm.getClass().getMethod("setText", java.lang.String.class).invoke(_elm,x.getNodeValue());
							
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
						JComponent f = deserialize(welm);
						if(f != null)
						{
				//			f.setBounds(new Rectangle(0,0,_elm.getWidth(),_elm.getHeight()));
							_elm.add(f);
						}
					}
					}
				//_elm.getClass().((Object[])null);
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
	public JComponent getRootComponent() {
		return rootComponent;
	}
	public void setRootComponent(JComponent rootComponent) {
		this.rootComponent = rootComponent;
	}
}