package com.tonosseum.framework.list;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.FlowLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;
import com.tonosseum.framework.Main;

public class StreamItemRenderer extends Container implements ListCellRenderer{

	protected Label name;
	protected Label mindTalk;
	protected Label infoKost;
	protected TextArea message;
	protected final Main main;
	protected Container container; 
	
	public StreamItemRenderer(Main main) {
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		this.main = main;
		name = new Label();
		mindTalk= new Label(main.mindIcon);
		infoKost= new Label(main.infoIcon);
		message = new TextArea(3,100);
		
		container = new Container(new FlowLayout());
		
		container.getStyle().setBgTransparency(255);
		container.getStyle().setBgColor(0x003DF5);
		mindTalk.getStyle().setBgTransparency(0);
		
		infoKost.getStyle().setBgTransparency(0);
		
		
		addComponent(container);
		container.addComponent(name);
		container.addComponent(mindTalk);
		
		
		addComponent(message);
		
		getStyle().setBorder(Border.createRoundBorder(10, 10, 0x00B8F5,true));
		
		name.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
		name.getStyle().setFgColor(0xCCFFFF);
		name.getStyle().setPadding(0, 0, 5, 0);
		message.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		message.getStyle().setBorder(Border.createEmpty());
		message.getStyle().setPadding(0, 0, 15, 0);
	
		name.getStyle().setBgTransparency(255);
		name.getStyle().setFgColor(0xFFFFFF);
		name.getStyle().setBgColor(0x003DF5);
		message.getStyle().setBgTransparency(0);
		message.getStyle().setFgColor(0x000000);
		getStyle().setBgTransparency(255);
	}
	
	public Component getListCellRendererComponent(List arg0, Object arg1,
			int arg2, boolean arg3) {
		
		if (container.contains(infoKost)){
			container.removeComponent(infoKost);
		}
		
		
		StreamItem item = (StreamItem) arg1;
		name.setText("@"+item.name);
		message.setText(item.message);
		
		if (item.url.length()>5){
			container.addComponent(infoKost);
		}
		
		if (arg3){
			// selected
			getStyle().setBgColor(0xEAEAEA);			
											
		}else{						
			getStyle().setBgColor(0xD4D4D4);																		
		}
		
		
		return this;		
	}

	public Component getListFocusComponent(List arg0) {
		return null;
	}

}
