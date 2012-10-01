package com.tonosseum.framework;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Image;
import javax.microedition.location.AddressInfo;
import javax.microedition.midlet.MIDlet;

import com.nokia.mid.ui.CategoryBar;
import com.nokia.mid.ui.ElementListener;
import com.nokia.mid.ui.IconCommand;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.tonosseum.framework.forms.ResultListPage;
import com.tonosseum.framework.forms.SearchPage;
import com.tonosseum.framework.forms.SocialPage;
import com.tonosseum.framework.forms.SplashPage;
import com.tonosseum.framework.list.ListItem;
import com.tonosseum.framework.utils.HTTPRequest;

public class Main extends MIDlet implements ElementListener{
	/** Constant **/
	public static String APP_NAME = "Koder";
	
	/** Attribute **/
	// Mind Talk
	public int postOffset = 0;
	
	// Search Criteria
	public short jenisKelamin = 0;
	public short pekerjaan = 0;
	public short kisaranHarga = 0;
	public short kota = 0;

	// Search Offset
	public int lowerOffset = 0;
	public int upperOffset = 0;
	public int total = 0;

	// Page Detail
	private boolean midletPaused = false;
	
	public Image searchImage;	
	public Image socialImage;
	
	// icons
	public com.sun.lwuit.Image prevLImage;
	public com.sun.lwuit.Image nextLImage;
	public com.sun.lwuit.Image refreshLImage;
	
	// action icons
	
	public com.sun.lwuit.Image callLImage;
	public com.sun.lwuit.Image messageLImage;
	
	// info icons
	public com.sun.lwuit.Image cityLImage;
	
	// facilites icons
	public com.sun.lwuit.Image tvLImage;
	public com.sun.lwuit.Image internetLImage;
	public com.sun.lwuit.Image bathLImage;
	public com.sun.lwuit.Image parkLImage;
	public com.sun.lwuit.Image bedLImage;
	public com.sun.lwuit.Image refrigratorLImage;
	public com.sun.lwuit.Image acLImage;
	public com.sun.lwuit.Image furnishedLImage;
	public com.sun.lwuit.Image lemariLImage;
	
	public com.sun.lwuit.Image appIcon;
	public com.sun.lwuit.Image mindIcon;
	public com.sun.lwuit.Image infoIcon;

	public CategoryBar  categoryBar;
	
	
	// near facilites icons
	public com.sun.lwuit.Image hospitalLImage;
	public com.sun.lwuit.Image schoolLImage;
	public com.sun.lwuit.Image mallLImage;
	
	
	
	/** Method **/
	public void startApp() {
		
		if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;         
	 
	}
	
	private void resumeMIDlet() {
		(new SearchPage(APP_NAME, this)).show();
	}

	private void initialize() {
		Display.init(this);	
		// init category bar
		try {			
			searchImage = Image.createImage("/search.png");
			socialImage = Image.createImage("/social.png");
			
			prevLImage = com.sun.lwuit.Image.createImage("/prev.png");
			nextLImage = com.sun.lwuit.Image.createImage("/next.png");
			refreshLImage = com.sun.lwuit.Image.createImage("/refresh.png");

			// action
			callLImage = com.sun.lwuit.Image.createImage("/call.png");
			messageLImage = com.sun.lwuit.Image.createImage("/message.png");
			
			// facilities
			internetLImage = com.sun.lwuit.Image.createImage("/wifi.png");			
			tvLImage = com.sun.lwuit.Image.createImage("/tv.png");
			lemariLImage = com.sun.lwuit.Image.createImage("/cabinet.png");
			bedLImage = com.sun.lwuit.Image.createImage("/bed.png");
			bathLImage = com.sun.lwuit.Image.createImage("/bath.png");
			parkLImage = com.sun.lwuit.Image.createImage("/park.png");
			refrigratorLImage= com.sun.lwuit.Image.createImage("/refrigerator.png"); 
			acLImage = com.sun.lwuit.Image.createImage("/ac.png");
			furnishedLImage = com.sun.lwuit.Image.createImage("/furnished.png");
			
			// near facility
			mallLImage = com.sun.lwuit.Image.createImage("/mall.png");
			schoolLImage = com.sun.lwuit.Image.createImage("/school.png");
			hospitalLImage = com.sun.lwuit.Image.createImage("/hospital.png");
			
			appIcon = com.sun.lwuit.Image.createImage("/icon.png");
			mindIcon = com.sun.lwuit.Image.createImage("/mindtalk.png");
			infoIcon = com.sun.lwuit.Image.createImage("/infokost.png");
			
			IconCommand searchIconCommand = new IconCommand("Search", searchImage, null, 
					javax.microedition.lcdui.Command.SCREEN, 1);
			IconCommand socialIconCommand = new IconCommand("Social", socialImage, null, 
					javax.microedition.lcdui.Command.SCREEN, 1);	
			
			IconCommand  categories [] = new IconCommand[2];
			categories[0] = searchIconCommand;
			categories[1] = socialIconCommand;
			
			categoryBar = new CategoryBar(categories, true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
		
		
			
		Command exitCommand = new Command("Exit"){
			public void actionPerformed(ActionEvent evt) {
				notifyDestroyed();
			}
		};		
	}

	private void startMIDlet() {
		
		(new SplashPage(this)).show();
	 	Timer timer =new Timer();
	 	timer.schedule(new TimerTask() {
			
			public void run() {
				 (new SearchPage(APP_NAME, Main.this)).show();
				 categoryBar.setVisibility(true);
				categoryBar.setElementListener(Main.this);
				categoryBar.setSelectedIndex(0);
			}
		}, 4000);
	}
	
	public void notifyElementSelected(CategoryBar bar, int selectedIndex) {
		System.out.println(selectedIndex+ " "+bar.getSelectedIndex());
		if (selectedIndex==0){
			// go to search page
			 goToSearchPage();
		}else if (selectedIndex==1){
			// go to social page 
			goToSocialPage();
		}

	}
	
	public void goToSearchPage(){		
		(new SearchPage(APP_NAME, this)).show();
	}
	
	public void goToSocialPage(){
		(new SocialPage(APP_NAME, this)).show();
	}
	
	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
	}
}


