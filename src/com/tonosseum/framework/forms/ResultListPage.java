package com.tonosseum.framework.forms;
import java.io.IOException;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.DefaultListModel;
import com.tonosseum.framework.Main;
import com.tonosseum.framework.list.ListItem;
import com.tonosseum.framework.list.ListItemRenderer;
import com.tonosseum.framework.utils.HTTPRequest;


public class ResultListPage extends Form implements ActionListener{
	
	protected Main parent;
	protected List listPanel;
	protected Command prevCommand;
	protected Command nextCommand;
	public boolean isBack;
	public Label waitingLabel;
	public Thread waitDataThread;
	public Thread retrieveDataThread;
	
	class WaitData implements Runnable{
		public void run() {
			// TODO Auto-generated method stub
			waitingLabel.setVisible(true);
			int c = 1;
			try {
				while(true){
					Thread.sleep(600);
					if (c == 4)
					{
						waitingLabel.setText("Loading");
						c = 1;
					}
					else
					{
						String text = "Loading";
						for (int i = 0; i < c; i++)
						{
							text += " .";
						}
						waitingLabel.setText(text);
						ResultListPage.this.repaint();
						c++;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				waitingLabel.setVisible(false);
				System.err.println("Interrupted Exception , message : " + e.getMessage());
			}
			ResultListPage.this.repaint();
		}
	}
	
	class RetrieveData implements Runnable{		
		public void run() {
			// TODO Auto-generated method stub
			initList(isBack);
			waitDataThread.interrupt();
		}
	}
	
	public ResultListPage(String title, Main parent) {
		super(title);
		this.isBack = false;
		this.parent = parent;	
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		// list panel
		listPanel = new List();		
		listPanel.addActionListener(this);							
		listPanel.setListCellRenderer(new ListItemRenderer());
		addComponent(listPanel);
				
		// exit command
		Command exitCommand = new Command("Exit") {
            public void actionPerformed(ActionEvent e) {
            	backToSearchPage();
            }		
        };
        
        this.addCommand(exitCommand);
        this.setBackCommand(exitCommand);
        
        // prev command
        prevCommand = new Command("Previous",parent.prevLImage){
        	public void actionPerformed(ActionEvent evt) {
        		goToPrevPage();
        	}
        };
        
        // next command
        nextCommand = new Command("Next", parent.nextLImage){
        	public void actionPerformed(ActionEvent evt) {
        		goToNextPage();
        	}
        };
        
        Command refreshCommand = new Command("Refresh", parent.refreshLImage){
        	public void actionPerformed(ActionEvent evt) {
        		refreshPage();
        	}
        };
                
        this.addCommand(nextCommand);
        this.addCommand(prevCommand);
        this.addCommand(refreshCommand);
        
        waitingLabel = new Label("Loading");
        waitingLabel.setAlignment(Component.CENTER);
        waitingLabel.getStyle().setBgColor(0xFFFFFF);
        waitingLabel.getStyle().setFgColor(0x000000);
        waitingLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        
        this.addComponent(waitingLabel);
        
        // Show Listener
        this.addShowListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.gc();
				if (!ResultListPage.this.isBack)
				{
					waitDataThread = new Thread(new WaitData());
					retrieveDataThread = new Thread(new RetrieveData());
					retrieveDataThread.start();
					waitDataThread.start();
				}
			}
		});
        
        getStyle().setBgColor(0xFFFFFF);
		getStyle().setBgTransparency(255);
	}
	
	protected void refreshPage() {
		// TODO Auto-generated method stub
		
	}

	public void addListItem(ListItem item){
		listPanel.addItem(item);
	}
	
	public void clearList(){
		listPanel.setModel(new DefaultListModel());
		System.gc();
	}

	public void actionPerformed(ActionEvent arg0) {
		goToDetailPage();
	}
	
	private void backToSearchPage() {
		System.gc();
		clearList();
        (new SearchPage(parent.APP_NAME, ResultListPage.this.parent)).showBack();
	}
	
	private void goToDetailPage(){
		System.gc();
		int selection = listPanel.getSelectedIndex();
		ListItem item = (ListItem) listPanel.getSelectedItem();
		DetailPage detailPage= new DetailPage(parent.APP_NAME, parent,this,item);
		detailPage.show();
	}
	
	private void goToNextPage(){
		waitDataThread = new Thread(new WaitData());
		retrieveDataThread = new Thread(new RetrieveData());
		waitDataThread.start();
		retrieveDataThread.start();
	}
	
	private void goToPrevPage(){
		waitDataThread = new Thread(new WaitData());
		retrieveDataThread = new Thread(new RetrieveData());
		waitDataThread.start();
		retrieveDataThread.start();
	}
	
	private void initList(boolean isPrev)
	{
	    try {
        	clearList();
        	
        	// Move Offset
        	if (!isPrev)
        	{
        		parent.lowerOffset = parent.upperOffset;
        	}
        	else
        	{
        		parent.upperOffset = parent.lowerOffset - 10;
        	}
        	
        	String request = "http://v2.api.infokost.net/property/search?limit=10";
        	boolean isParam = false;
        	if (parent.kota != 0)
        	{
        		isParam = true;
        		switch (parent.kota)
        		{
        			case 1 : request += "&kota=bekasi"; break;
        			case 2 : request += "&kota=bogor"; break;
        			case 3 : request += "&kota=depok"; break;
        			case 4 : request += "&kota=jakarta%20barat"; break;
        			case 5 : request += "&kota=jakarta%20pusat"; break;
        			case 6 : request += "&kota=jakarta%20selatan"; break;
        			case 7 : request += "&kota=jakarta%20timur"; break;
        			case 8 : request += "&kota=jakarta%20utara"; break;
        			case 9 : request += "&kota=tangerang"; break;
        		}
        	}
        	
        	if (parent.kisaranHarga != 0)
        	{    			
    			isParam = true;	        			
        		switch (parent.kisaranHarga)
        		{		        			
        			case 1  : request += "&min_price=0&max_price=999999"; break;
        			case 2  : request += "&min_price=1000000&max_price=2999999"; break;
        			case 3  : request += "&min_price=3000000&max_price=4999999"; break;
        			case 4  : request += "&min_price=5000000&max_price=6999999"; break;
        			case 5  : request += "&min_price=7000000"; break;
        		}
        	}
        	
        	int count = 0;
        	int count_result = 10;
        	int coba = 0;

        	while (count < 7 && count_result == 10 && ((isPrev && parent.upperOffset != 0) || !isPrev))
        	{
        		String response = "";
        		if (isParam)
        		{
        			response = HTTPRequest.requestUrl(request + "&offset=" + parent.upperOffset);
        		}
        		else
        		{
        			response = HTTPRequest.requestUrl("http://v2.api.infokost.net/property/lists?limit=20&offset=" + parent.upperOffset);
        		}
            	
            	JSONObject jObj = new JSONObject(response);

	        	parent.total = jObj.getInt("total");

	        	if (isParam)
	        	{
	        		count_result = jObj.getInt("count_result");
	        	}
	        	else
	        	{
	        		count_result = jObj.getInt("count");
	        	}
	        	
	        	JSONArray result = jObj.getJSONArray("result");
	        	System.gc();
	        	for (int i = 0; i < result.length() && count < 10; i++)
	        	{
	        		boolean isValid = true;	
	        		
	        		JSONObject data = result.getJSONObject(i);
	        		JSONObject memberData = data.getJSONObject("member");
        			JSONArray kelamin = data.getJSONArray("occupants");
		        	JSONArray facility = data.getJSONArray("facility");
		        	JSONArray publicFacility = data.getJSONArray("public_facility");
		        	JSONArray nearFacility = data.getJSONArray("near_facility");
        			JSONArray job = data.getJSONArray("mayor_occupants");
		        	
	        		if (parent.jenisKelamin != 0)
		        	{
	        			if (kelamin.length() == 0)
	        			{
	        				isValid = false;
	        			}
	        			else
	        			{
    						isValid = false;
		        			if (parent.jenisKelamin == 1)
		        			{
		        				for (int c = 0; c < kelamin.length(); c++)
		        				{
		        					if (!kelamin.getString(c).equals("pria"))
		        					{
		        						isValid = true;
		        					}
		        				}
		        			} 
		        			else if (parent.jenisKelamin == 2)
		        			{	
		        				for (int c = 0; c < kelamin.length(); c++)
		        				{
		        					if (!kelamin.getString(c).equals("wanita"))
		        					{
		        						isValid = true;
		        					}
		        				}
		        			}
		        			else
		        			{	
		        				int status = 0;
		        				for (int c = 0; c < kelamin.length() && status < 2; c++)
		        				{
		        					if (kelamin.getString(c).equals("pria"))
		        					{
		        						status++;
		        					}
		        					else if (kelamin.getString(c).equals("wanita"))
		        					{
		        						status++;
		        					}
		        					else if (kelamin.getString(c).equals("keluarga") || kelamin.getString(c).equals("suami istri"))
		        					{
		        						status = 2;
		        					}
		        				}
		        				
		        				if (status >= 2)
		        				{
		        					isValid = true;
		        				}
		        			}
	        			}
		        	}
		        	
	        		if (parent.pekerjaan != 0 && isValid)
		        	{
	        			if (job.length() == 0)
	        			{
	        				isValid = false;
	        			}
	        			else
	        			{
	        				String pattern = "";
		        			if (parent.pekerjaan == 1)
		        			{
		        				pattern = "maha";
		        			}
		        			else if (parent.pekerjaan == 2)
		        			{	
		        				pattern = "karya";
		        			}
		        			else
		        			{	
		        				pattern = "exec";
		        			}

    						isValid = false;
	        				for (int c = 0; c < job.length() && !isValid; c++)
	        				{
	        					if (!job.getString(c).startsWith(pattern))
	        					{
	        						isValid = true;
	        					}
	        				}
	        			}
		        	}
	        		
		        	if (isValid)
		        	{
		        		// list item
		        		ListItem item = new ListItem();
		        		
		        		// property ID
			        	item.propertyId = data.getString("property_id");
		        		count++;
		        		
		        		// monthly rate
		        		item.monthlyRate = data.getString("monthly_rates");
		        		
		        		// annual rate
		        		item.annualRate = data.getString("annually_rates");				        		
		        		
		        		// city
		        		item.kostCity = data.getString("kota");		        		
		        		if (item.kostCity.length() ==0){
		        			item.kostCity = memberData.getString("city");
		        		}
		        		
		        		// name
		        		item.kostName = data.getString("name");
		        						        		
		        		// Facility
		        		item.facility = new String[facility.length()];
			        	for (int j = 0; j < facility.length(); j++)
			        	{
			        		item.facility[j] = facility.getString(j);
			        	}
		        		
						// Public Facility
						item.publicFacility = new String[publicFacility.length()];
						for (int j = 0; j < publicFacility.length(); j++)
						{
							item.publicFacility[j] = publicFacility.getString(j);
						}
		        		
						// Near Facility
						item.nearFacility = new String[nearFacility.length()];
						for (int j = 0; j < nearFacility.length(); j++)
						{
							item.nearFacility[j] = nearFacility.getString(j);
						}
						
						// Occupants
						item.occupants = new String[kelamin.length()];
						for (int j = 0; j < kelamin.length(); j++)
						{
							item.occupants[j] = kelamin.getString(j);
						}
						
						// Mayor Occupants
						item.mayorOccupants = new String[job.length()];
						for (int j = 0; j < job.length(); j++)
						{
							item.mayorOccupants[j] = job.getString(j);
						}
						
						// Owner Email
						try {
							item.ownerEmail = memberData.getString("email");
						}catch (JSONException e){
							item.ownerEmail = "-";
						}

						// Owner Name
						try{
							item.ownerName = memberData.getString("name");
						}catch (JSONException e){
							item.ownerName 
							= "-";
						}
						
						// Owner Telephone
						try{
							item.ownerTelephone = memberData.getString("telephone");
						}catch (JSONException e){
							item.ownerTelephone = "-";
						}

						// Owner Handphone
						try{
							item.ownerHandphone = memberData.getString("handphone");
						}catch (JSONException e){
							item.ownerHandphone = "-";
						}
											
						listPanel.addItem(item);
		        	}
		        	
		        	if (isPrev)
		        	{
		        		parent.upperOffset--;
		        	}
		        	else
		        	{
		        		parent.upperOffset++;
		        	}
		        }
	        }
        	
        	if (parent.upperOffset < 0)
        	{
        		parent.upperOffset = 0;
        	}
        	
        	if (isPrev)
        	{
        		int temp = parent.lowerOffset;
        		parent.lowerOffset = parent.upperOffset;
        		parent.upperOffset = temp;
        	}
        	
        	if (parent.lowerOffset == 0)
        	{
        		prevCommand.setEnabled(false);
        	}

        	if (parent.upperOffset == parent.total)
        	{
        		nextCommand.setEnabled(false);
        	}	        	
        	System.gc();
        } catch (IOException e){
        	System.err.println("ResultListPage - ctor : IOException, message : " + e.getMessage());
        } catch (JSONException e) {
        	System.err.println("ResultListPage - ctor : JSONException, message : " + e.getMessage());
		}				
	}
}
