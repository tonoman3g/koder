package com.tonosseum.framework.forms;

import java.io.IOException;

import com.sun.lwuit.Button;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.DefaultLookAndFeel;
import com.sun.lwuit.plaf.LookAndFeel;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.plaf.UIManager;
import com.tonosseum.framework.Main;

public class SearchPage extends Form {
	/** Attribute **/
	private Main parent;

	/** Method **/
	// Constructor
	public SearchPage(String formName, Main mainMidlet){
		// Init Variable
		super(formName);
		this.parent = mainMidlet;
		this.parent.kota = 0;
		this.parent.kisaranHarga = 0;
		this.parent.jenisKelamin = 0;
		this.parent.pekerjaan = 0;

		Image searchImage;
		try {
			searchImage = Image.createImage("/search.png");
			Command searchCommand = new Command("Search", searchImage){
				public void actionPerformed(ActionEvent evt) {	
					parent.lowerOffset = 0;
					parent.upperOffset = 0;
					search();
				}
			};			
			this.addCommand(searchCommand);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Init Command
		Command exitCommand = new Command("Exit") {
            public void actionPerformed(ActionEvent e) {
                parent.notifyDestroyed();
            }
        };
        
        this.addCommand(exitCommand);
        this.setBackCommand(exitCommand);
        
        // Init UI
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.getStyle().setBgColor(0xFFFFFF);

        Image logo;
        Label textLabel;
        ComboBox comboBox;
        Button button;
        Style headerStyle;
        Font headerFont;
        Style comboStyle;
        Style comboPressedStyle;
        
        headerFont = Font.createSystemFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        headerStyle = new Style(0x000000, 0xFFFFFF, headerFont, (byte)0xFF);
        comboStyle = new Style(0x0,0x003DF5, this.getStyle().getFont(), (byte)0xFF);
        comboPressedStyle = new Style(0x0,0x000DB5, this.getStyle().getFont(), (byte)0xFF);

        // Group 1
        textLabel = new Label("Kota");
        textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);
        
		comboBox = new ComboBox();
        comboBox.addItem("--");
		comboBox.addItem("Bekasi");
		comboBox.addItem("Bogor");
		comboBox.addItem("Depok");
		comboBox.addItem("Jakarta Barat");
		comboBox.addItem("Jakarta Pusat");
		comboBox.addItem("Jakarta Selatan");
		comboBox.addItem("Jakarta Timur");
		comboBox.addItem("Jakarta Utara");
		comboBox.addItem("Tangerang");
		comboBox.setUnselectedStyle(comboStyle);
		comboBox.setSelectedStyle(comboStyle);
		comboBox.setPressedStyle(comboPressedStyle);
		comboBox.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				parent.kota = (short)((ComboBox) arg0.getSource()).getSelectedIndex();
			}
		});
		this.addComponent(comboBox);
		
		textLabel = new Label(" ");
		textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);

		// Group 2
        textLabel = new Label("Harga/Bulan");
        textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);

		comboBox = new ComboBox();
        comboBox.addItem("--");
		comboBox.addItem("< 1.000.000");
		comboBox.addItem("1.000.000 - 3.000.000");
		comboBox.addItem("3.000.000 - 5.000.000");
		comboBox.addItem("5.000.000 - 7.000.000");
		comboBox.addItem("> 7.000.000");
		comboBox.setUnselectedStyle(comboStyle);
		comboBox.setSelectedStyle(comboStyle);
		comboBox.setPressedStyle(comboPressedStyle);
		comboBox.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				parent.kisaranHarga = (short)((ComboBox) arg0.getSource()).getSelectedIndex();
			}
		});
		this.addComponent(comboBox);
        
		textLabel = new Label(" ");
		textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);
		
        // Group 3
        textLabel = new Label("Jenis Kelamin");
        textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);

        comboBox = new ComboBox();
        comboBox.addItem("--");
        comboBox.addItem("Pria");
        comboBox.addItem("Wanita");
        comboBox.addItem("Pria & Wanita");
		comboBox.setUnselectedStyle(comboStyle);
		comboBox.setSelectedStyle(comboStyle);
		comboBox.setPressedStyle(comboPressedStyle);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				parent.jenisKelamin = (short)((ComboBox) arg0.getSource()).getSelectedIndex();
			}
		});
        this.addComponent(comboBox);
        
		textLabel = new Label(" ");
		textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);
		
        // Group 4
        textLabel = new Label("Pekerjaan");
        textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);

		comboBox = new ComboBox();
        comboBox.addItem("--");
		comboBox.addItem("Mahasiswa");
		comboBox.addItem("Karyawan");
		comboBox.addItem("Executive Expatriat");
		comboBox.setUnselectedStyle(comboStyle);
		comboBox.setSelectedStyle(comboStyle);
		comboBox.setPressedStyle(comboPressedStyle);
		comboBox.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				parent.pekerjaan = (short)((ComboBox) arg0.getSource()).getSelectedIndex();
			}
		});
		this.addComponent(comboBox);
		
		textLabel = new Label(" ");
		textLabel.setUnselectedStyle(headerStyle);
        this.addComponent(textLabel);

        // Show Listener
        this.addShowListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.gc();
			}
		});
	}
	
	public void search()
	{
		System.gc();
		(new ResultListPage(Main.APP_NAME, parent)).show();
	}
}
