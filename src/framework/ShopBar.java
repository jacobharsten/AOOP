package framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ShopBar extends JPanel {
	
	JTextArea list;
	private JLabel header;
	private JButton save;
	private Color bg_color;
	private HashMap<String, Integer> shopList;
	
	private boolean visible;
	
	
	// Lägg till en -> .txt funktion. 
	// Lägg till så man kan modda antalet komponenter 
	// Lägg till button för att spara til text-fil
	
	public ShopBar(){
		setLayout(new BorderLayout());
		bg_color = new Color(30,30,30);
		setBackground(Color.BLACK);
		setSize(200,200);
		
		shopList = new HashMap<String, Integer>();
		
		list = new JTextArea(10, 1);
		list.setEditable(false);
		list.setBackground(Color.BLACK);
		list.setForeground(Color.WHITE);
		
		ImageIcon kundvagn = new ImageIcon("src/images/shoppingCart.png");
		//ImageIcon kundvagn =new ImageIcon(new ImageIcon("src/images/shoppingCart.png").getImage().getScaledInstance(20,20 , Image.SCALE_DEFAULT));
		header = new JLabel();
		header.setIcon(kundvagn);
		header.setForeground(Color.WHITE);
		header.setVisible(false);
		
		save = new JButton("Save");
		save.setForeground(Color.BLACK);
		save.setVisible(false);
		save.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				saveToFile();
			}
		});
		
		add(save, BorderLayout.SOUTH);
		add(header, BorderLayout.NORTH);
		add(list, BorderLayout.CENTER);
	}
	
	private void saveToFile(){
		JFileChooser c = new JFileChooser();
		if (c.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			try{
				File file = c.getSelectedFile();
				//ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
				PrintWriter out = new PrintWriter(file + ".txt");
				out.println("ShoppingList");
				out.println("----------------------");
				
				out.println(list.getText());
				out.close();
			}
			catch (IOException exception)
			{
				JOptionPane.showMessageDialog(null,
						exception);
			}
		}
	}
	
	public void setVisible(boolean temp){
		header.setVisible(temp);
		list.setVisible(temp);
		save.setVisible(temp);
		visible = temp;
	}
	public boolean getVisible(){
		return visible;
	}
	public void add(Object item){
		shopList.put(item.toString(), calculate(item));
		setVisible(true);
		updateCart();
	}
	public void remove(Object item){
		
		if(shopList.containsKey(item.toString())){
			Integer count = shopList.get(item.toString());
			count--;
			shopList.put(item.toString(), count);
			if(count == 0){
				shopList.remove(item.toString());
			}
		}
		updateCart();
	}
	public void emptyAll(){
		shopList.clear();
		updateCart();
	}
	public void addAll(List <Node> temp){
		for(int i=0; i<temp.size(); i++){
			add(temp.get(i));
		}
		updateCart();
	}
	private int calculate(Object item){
		Integer count = shopList.get(item.toString());
		if(count == null) return 1;
		
		if(shopList.containsKey(item.toString()))count++;
		return count;
	}
	public void updateCart(){
		if(shopList.isEmpty()){
			setVisible(false);
		}
		list.setText("");
		for(Map.Entry<String, Integer> entry: shopList.entrySet()){
			list.append(entry.getKey() + " | " + entry.getValue() + "\n");
		}
		list.getPreferredSize();
		
		//System.out.println(shopList);
		//list.setText(shopList.toString());
	}
}
