package com.tonosseum.framework.forms;

import java.io.IOException;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.DefaultListModel;
import com.tonosseum.framework.Main;
import com.tonosseum.framework.list.StreamItem;
import com.tonosseum.framework.list.StreamItemRenderer;
import com.tonosseum.framework.utils.HTTPRequest;

public class SocialPage extends Form implements ActionListener {

	protected final Main parent;
	protected List listPanel;
	
	protected Command prevCommand;
	protected Command nextCommand;

	public SocialPage(String name, Main parent) {
		super(name);
		this.parent = parent;
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		Label subtitle= new Label("Channel #infokost");
		subtitle.getStyle().setBgColor(0xFFFFFF);
		subtitle.getStyle().setFgColor(0x000000);
		getStyle().setBgColor(0xFFFFFF);
		getStyle().setBgTransparency(255);
		addComponent(subtitle);
		
		listPanel = new List();
		listPanel.addActionListener(this);
		listPanel.setListCellRenderer(new StreamItemRenderer(parent));
		
		this.addShowListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.gc();
				StreamItem item = new StreamItem();
				item.name="DeveloperTeam";
				item.message="Pilih refresh untuk menampilkan post dari channel info kost";
				listPanel.addItem(item);
			}
		});

		// exit command
		Command exitCommand = new Command("Exit") {
			public void actionPerformed(ActionEvent e) {
				SocialPage.this.parent.notifyDestroyed();
			}
		};

		this.addCommand(exitCommand);
		this.setBackCommand(exitCommand);

		// prev command
		prevCommand = new Command("Previous", parent.prevLImage) {
			public void actionPerformed(ActionEvent evt) {
				prevSocialPage();
			}
		};

		// next command
		nextCommand = new Command("Next", parent.nextLImage) {
			public void actionPerformed(ActionEvent evt) {
				nextSocialPage();
			}
		};

		Command refreshCommand = new Command("Refresh", parent.refreshLImage) {
			public void actionPerformed(ActionEvent evt) {
				initSocial();
			}
		};

		this.addCommand(nextCommand);
		this.addCommand(prevCommand);
		this.addCommand(refreshCommand);
		this.addComponent(listPanel);
	}



	public void actionPerformed(ActionEvent arg0) {

	}
	
	
	public void prevSocialPage()
	{
		if (parent.postOffset > 0)
		{
			parent.postOffset -= 10;
			initSocial();
		}
		else
		{
			parent.postOffset = 90;
			initSocial();
		}
	}
	
	public void nextSocialPage()
	{
		if (parent.postOffset < 90 )
		{
			parent.postOffset += 10;
			initSocial();
		}
		else
		{
			parent.postOffset = 0;
			initSocial();
		}
	}
	
	public void initSocial()
	{
		listPanel.setModel(new DefaultListModel());
		this.repaint();
		try {
			String response = HTTPRequest.requestUrl("http://api.mindtalk.com/v1/post/search?api_key=5afa7f85a64a2228e9238840b0d2221361311ef0&keyword=infokost&offset=" + parent.postOffset);
			JSONObject jObj = new JSONObject(response);
			jObj = jObj.getJSONObject("result");
			JSONArray resultArray = jObj.getJSONArray("result");
			for (int i = 0; i < resultArray.length(); i++)
			{
				jObj = resultArray.getJSONObject(i);
				
				StreamItem item = new StreamItem();
				item.name = jObj.getJSONObject("creator").getString("name");
				item.message = jObj.getString("message");
				
				int idx = item.message.indexOf("www.infokost.net");
				if (idx != -1)
				{
					item.url = item.message.substring(idx);
				}
				
				listPanel.addItem(item);				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
