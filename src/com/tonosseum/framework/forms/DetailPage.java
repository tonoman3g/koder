package com.tonosseum.framework.forms;



import java.io.InputStream;

import javax.microedition.io.ConnectionNotFoundException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.html.DocumentInfo;
import com.sun.lwuit.html.DocumentRequestHandler;
import com.sun.lwuit.html.HTMLComponent;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.FlowLayout;
import com.tonosseum.framework.Main;
import com.tonosseum.framework.list.ListItem;

public class DetailPage extends Form {
	/** Attribute **/
	// Midlet
	private Main parent;
	// previous page
	private final ResultListPage previousPage;
	
	// Detail item
	private final ListItem item;
	
	// Containers
	private Container cInfo;	
	private Container cFacility;
	private Container cNearFacility;
	private Container cOwner;
	
	private TextArea name;
	private Label annuallyRate;
	private Label monthlyRate;
	
	private Label mayorOccupant;
	private Label city;
	
	private Label facilityLabel;
	private Label cityLabel;
	private Label occupantLabel;
	
	private Label nearFacilityLabel;
	private Label ownerLabel;
	private TextArea ownerName;
	private TextArea ownerHandphone;
	private TextArea ownerTelephone;
	private TextArea ownerEmail;
				
	
	/** Method **/
	// Constructor
	public DetailPage(final String formName, Main mainMidlet, ResultListPage previousPage, final ListItem item){
		// Init Variable
		super(formName);
		this.parent = mainMidlet;
		this.previousPage = previousPage;
		this.item = item;

		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		getStyle().setBgColor(0xFFFFFF);
		getStyle().setBgTransparency(255);
		
		// Add Exit Command
		Command exitCommand = new Command("Exit") {
            public void actionPerformed(ActionEvent e) {
            	DetailPage.this.previousPage.isBack = true;
            	DetailPage.this.previousPage.showBack();
                                               
            }
        };
               
        
        this.addCommand(exitCommand);
        this.setBackCommand(exitCommand);
        
        // Show Listener
        this.addShowListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.gc();
			}
		});
        
        // Init all ui components
        cInfo = new Container(new BoxLayout(BoxLayout.Y_AXIS));                            
        cOwner = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        
        // add name
        name = new TextArea(item.kostName);
        name.setEditable(false);	
        name.setRows(5);
        name.setColumns(100);        
       
        // add the containers
        addComponent(name);
        Label infoLabel = new Label("Info");
        infoLabel .getStyle().setBgColor(0x003DF5);        
        infoLabel .getStyle().setFgColor(0xFFFFFF);
        addComponent(infoLabel);
        addComponent(cInfo);
        
        if (item.annualRate.length()!=0){
        	if(!item.annualRate.equals("0")){
	        	annuallyRate = new Label("Rp "+item.annualRate +" / tahun");
	        	annuallyRate.getStyle().setBgTransparency(0);
	        	annuallyRate.getStyle().setFgColor(0x000000);
	        	cInfo.addComponent(annuallyRate);
        	}
        }
        
        if (item.monthlyRate.length()!=0){
        	if(!item.monthlyRate.equals("0")){
	        	monthlyRate = new Label("Rp "+item.monthlyRate+" / bulan");
	        	monthlyRate.getStyle().setBgTransparency(0);
	        	monthlyRate.getStyle().setFgColor(0x000000);
	        	cInfo.addComponent(monthlyRate);
        	}
        }
        
        if (item.kostCity.length()!=0){
        	city = new Label("Kota : "+item.kostCity);
        	city.getStyle().setBgTransparency(0);
        	city.getStyle().setFgColor(0x000000);
        	cInfo.addComponent(city);
        }
        
        if (item.mayorOccupants!=null){
        	if (item.mayorOccupants.length>0){
        		StringBuffer sb = new StringBuffer();
    			for (int i=0; i< item.mayorOccupants.length;i++){
    				sb.append(item.mayorOccupants[i]);
    				sb.append('\n');
        		}        		
        		mayorOccupant = new Label("Mayor Occupants : "+sb.toString());
        		mayorOccupant.getStyle().setBgTransparency(0);
        		mayorOccupant.getStyle().setFgColor(0x000000);
        		cInfo.addComponent(mayorOccupant);
        	}        	
        }
        
        if (item.facility!=null){
        	if (item.facility.length>0){
	        	facilityLabel = new Label("Fasilitas");
	        	facilityLabel .getStyle().setBgColor(0x003DF5);        
	        	facilityLabel .getStyle().setFgColor(0xFFFFFF);
	        	cFacility = new Container(new FlowLayout());
	        	addComponent(facilityLabel);
	            addComponent(cFacility);
	            	            
	            boolean internet = false, tv =false, bath=false, park=false, lemari=false, furnished=false;
	            boolean ac = false, bed=false, ref=false;
	            for (int i=0; i< item.facility.length;i++){
	            	String s = item.facility[i].toLowerCase();
//	            	System.out.println(s);
	            	if (s.indexOf("internet")!=-1){	     
	            		if (!internet){
	            			Label l =new Label(parent.internetLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			internet=true;
	            		}
	            	}
	            	if (s.indexOf("tv")!=-1){
	            		if (!tv){
	            			Label l =new Label(parent.tvLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			tv = true;
	            		}
	            	}
	            	if (s.indexOf("mandi")!=-1){
	            		if (!bath){
	            			Label l =new Label(parent.bathLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			bath=true;
	            		}
	            	}
	            	if (s.indexOf("parkir")!=-1){	  
	            		if (!park){
	            			Label l =new Label(parent.parkLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			park=true;
	            		}
	            	}
	            	if (s.indexOf("lemari")!=-1){
	            		if (!lemari){
	            			Label l =new Label(parent.lemariLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			lemari= true;
	            		}
	            	}
	            	if (s.indexOf("furnished")!=-1){	            		
	            		if(!furnished){
	            			Label l =new Label(parent.furnishedLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			furnished=true;
	            		}
	            	}
	            	if (s.indexOf("a\\c")!=-1){
	            		if (!ac){
	            			Label l =new Label(parent.acLImage);
	            			l.getStyle().setBgTransparency(0);
		            		cFacility.addComponent(l);
		            		ac=true;
	            		}
	            	}
	            	if (s.indexOf("kasur")!=-1){
	            		if (!bed){
	            			Label l =new Label(parent.bedLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			bed=true;
	            		}
	            	}
	            	if (s.indexOf("kulkas")!=-1){
	            		if(!ref){
	            			Label l =new Label(parent.refrigratorLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cFacility.addComponent(l);
	            			ref = true;
	            		}
	            	}                        
	            	
	            	
	            }
        	}
        }
        
        if (item.nearFacility!=null){
        	if (item.nearFacility.length>0){
	        	nearFacilityLabel = new Label("Fasilitas Terdekat");
	        	nearFacilityLabel .getStyle().setBgColor(0x003DF5);        
	        	nearFacilityLabel .getStyle().setFgColor(0xFFFFFF);
	        	cNearFacility = new Container( new FlowLayout());
	        	addComponent(nearFacilityLabel);
	            addComponent(cNearFacility);	       
	            boolean hospital = false, school=false, mall=false;
	            for (int i=0; i< item.nearFacility.length;i++){
	            	String s = item.nearFacility[i].toLowerCase();
	            	if (s.indexOf("mall")!=-1){
	            		if(!mall){
	            			Label l =new Label(parent.mallLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cNearFacility.addComponent(l);
	            			mall=true;
	            		}
	            	}
	            	else if (s.indexOf("school")!=-1||s.indexOf("kampus")!=-1||s.indexOf("sekolah")!=-1){
	            		if (!school){
	            			Label l =new Label(parent.schoolLImage);
	            			l.getStyle().setBgTransparency(0);
	            			cNearFacility.addComponent(l);
	            			school=true;
	            		}
	            	}
	            	else if (s.indexOf("apotik")!=-1||s.indexOf("hospital")!=-1){
	            		if (!hospital){	            			
	            			Label label = new Label(parent.hospitalLImage);
	            			label.getStyle().setBgTransparency(0);
	            			cNearFacility.addComponent(label);
	            			hospital=true;
	            		}
	            	}else{
	            		Label l  =new Label(item.nearFacility[i]);
	            		l.getStyle().setBgTransparency(0);
	            		l.getStyle().setFgColor(0x000000);	            		
	            		l.getStyle().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_SMALL));
	            		addComponent(l);
	            	}
	            }
        	}
        }
                              
        // add owner data
        addComponent(cOwner);                       
        ownerLabel= new Label("Pemilik");
        ownerLabel.getStyle().setBgColor(0x003DF5);        
        ownerLabel.getStyle().setFgColor(0xFFFFFF);        
        cOwner.addComponent(ownerLabel);
        if (!item.ownerName.equals("")){
        	ownerName = new TextArea(item.ownerName, 3, 100);
        	ownerName.setEditable(false);
            cOwner.addComponent(ownerName);
        }else{
        	cOwner.addComponent(new Label("Anonimus"));
        }
        
        
        if (item.ownerHandphone.length()>3){
        	ownerHandphone = new TextArea(item.ownerHandphone, 2,100);
        	ownerHandphone.setEditable(false);        	
        	Container container = new Container(new BoxLayout(BoxLayout.X_AXIS));
        	Button callHandphone = new Button(parent.callLImage);
        	callHandphone.setPreferredW(44);
        	callHandphone.setPreferredH(44);
        	callHandphone.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					try {
						parent.platformRequest("tel:" + item.ownerHandphone);
					} catch (ConnectionNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
        	Button smsHandphone = new Button(parent.messageLImage);
        	smsHandphone.setPreferredW(44);
        	smsHandphone.setPreferredH(44);
        	smsHandphone.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					try {
						parent.platformRequest("sms:" + item.ownerHandphone);
					} catch (ConnectionNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
        	
        	container.addComponent(callHandphone);
        	container.addComponent(smsHandphone);
        	container.addComponent(ownerHandphone);
        	cOwner.addComponent(container);        	
        }else{
        	
        }
        if (item.ownerTelephone.length()>3){
        	
        	if (!item.ownerTelephone.equals(item.ownerHandphone)){
	        	ownerTelephone= new TextArea(item.ownerTelephone,2,100);
	        	ownerTelephone.setEditable(false);	        	
	        	Container container = new Container(new BoxLayout(BoxLayout.X_AXIS));
	        	Button callTelephone = new Button(parent.callLImage);
	        	callTelephone.setPreferredW(44);
	        	callTelephone.setPreferredH(44);
	        	callTelephone.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {						
						try {
							parent.platformRequest("tel:" + item.ownerTelephone);
						} catch (ConnectionNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	        	container.addComponent(callTelephone);
	        	container.addComponent(ownerTelephone);
	        	cOwner.addComponent(container);
        	}
        }
        if (item.ownerTelephone.length()<3 && item.ownerHandphone.length()<3){
        	Label label = new Label("Tidak memiliki nomor kontak");
        	label.getStyle().setBgTransparency(0);
        	label.getStyle().setFgColor(0x000000);
    		cOwner.addComponent(label);
    		
    		
    	}
        
        if (!item.ownerEmail.equals("")){
        	ownerEmail= new TextArea("Email : "+item.ownerEmail, 2,100);
        	ownerEmail.setEditable(false);
        	cOwner.addComponent(ownerEmail);        	
        }else{
        	Label label =new Label("Tidak memiliki email");
        	label.getStyle().setFgColor(0x000000);
        	label.getStyle().setBgTransparency(0);
        	cOwner.addComponent(label);
        	
        }
        
        
	}
}

