package com.tonosseum.framework.list;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.SelectionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;


public class ListItemRenderer extends Container implements ListCellRenderer{

	private Label name ;
	private Label rate;
	private Label city;	
	
//	private Label focus;
	
	public ListItemRenderer() {
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));				
		
				
		name = new Label();
		rate = new Label();
		city = new Label();
		
		addComponent(name);
		addComponent( city);
		addComponent( rate);
		
//		getStyle().setBorder(Border.createRoundBorder(10, 10, 0x00B8F5, true));
		
		name.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
		rate.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		city.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_SMALL));
							
		getStyle().setBgTransparency(255);		
		name.getStyle().setBgTransparency(255);
		name.getStyle().setPadding(0, 0, 5, 0);
		rate.getStyle().setBgTransparency(0);
		rate.getStyle().setPadding(0, 0, 8, 0);
		city.getStyle().setBgTransparency(0);
		city.getStyle().setPadding(0, 0, 5, 0);
		rate.setAlignment(Component.RIGHT);
		name.getStyle().setBgColor(0x003DF5); // namenya biru tua
		name.getStyle().setFgColor(0xFFFFFF);
		rate.getStyle().setFgColor(0x000000);
		city.getStyle().setFgColor(0x000000);

	}
	
	public Component getListCellRendererComponent(List arg0, Object arg1,
			int pos, boolean isSelected) {
		ListItem item = (ListItem) arg1;
		
		name.setText(item.kostName);
		
		if (!item.monthlyRate.equals("0")){
			StringBuffer sb = new StringBuffer();
			sb.append('R');
			sb.append('p');
			sb.append(item.monthlyRate);
			sb.append(" / bulan");
			rate.setText(sb.toString());			
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append('R');
			sb.append('p');
			sb.append(item.annualRate);
			sb.append(" / tahun");
			
			rate.setText(sb.toString());
		}

		
		city.setText(item.kostCity);
		
		if (isSelected){
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
