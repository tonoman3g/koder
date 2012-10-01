package com.tonosseum.framework.forms;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.tonosseum.framework.Main;

public class SplashPage extends Form{

	private Main main;
	
	public SplashPage(Main main) {
		super(Main.APP_NAME);
		
		setLayout(new BorderLayout());					
		
		getStyle().setBgColor(0xffffff);
		getStyle().setBgTransparency(255);
		Container panel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		panel.getStyle().setBgTransparency(0);
		panel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/2));
		Label label = new Label(main.appIcon);
		label.getStyle().setBgTransparency(0);
		label.setAlignment(Component.CENTER);
		
		addComponent(BorderLayout.CENTER,label);
	}
}
