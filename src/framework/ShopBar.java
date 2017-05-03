package framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ShopBar extends JPanel {
	
	JTextArea list;
	private JLabel header;
	private JButton save;
	private Color bg_color;
	private HashMap<String, Integer> shopList;
	
	private boolean visible;
	
	private JLabel textGroup;
	
	// Lägg till en -> .txt funktion. - KLART
	// Lägg till så man kan modda antalet komponenter 
	// Lägg till button för att spara til text-fil - KLART 
	
	public ShopBar(){
		setLayout(new BorderLayout());
		bg_color = new Color(30,30,30);
		setBackground(Color.BLACK);
		//setPreferredSize(new Dimension(180,200));
		setupFields();
		
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
		save.setOpaque(true);
		save.setBackground(Color.LIGHT_GRAY);
		save.setForeground(Color.WHITE);
		save.setBorderPainted(false);
		save.setVisible(false);
		save.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				saveToFile();
			}
		});
		
		
		add(header, BorderLayout.NORTH);
		//add(list, BorderLayout.CENTER);
		add(save, BorderLayout.SOUTH);
		add(panel, BorderLayout.CENTER);
	}
	private void editList(){
		System.out.println("Noob");
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
		panel.setVisible(temp);
		
		visible = temp;
	}
	public boolean getVisible(){
		return visible;
	}
	public void add(Object item){
		shopList.put(item.toString(), calculate(item));
		setVisible(true);
		updateCart();
		uppdateCart();
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
		uppdateCart();
	}
	public void emptyAll(){
		shopList.clear();
		updateCart();
		uppdateCart();
	}
	public void addAll(List <Node> temp){
		for(int i=0; i<temp.size(); i++){
			add(temp.get(i));
		}
	}
	private int calculate(Object item){
		Integer count = shopList.get(item.toString());
		if(count == null) return 1;
		
		if(shopList.containsKey(item.toString()))count++;
		return count;
	}
	
	public void uppdateCart(){
		int count = 0;
		if(shopList.isEmpty()){
			setVisible(false);
		}
		clearLabels();
		for(Map.Entry<String, Integer> entry: shopList.entrySet()){
			getLabel(count).setText(entry.getKey());
			getTextField(count).setText(Integer.toString(entry.getValue()));
			count++;
		}
		panelVisible();
	}
	private void clearLabels(){
		label_1.setText("");
		label_2.setText("");
		label_3.setText("");
		label_4.setText("");
		label_5.setText("");
		label_6.setText("");
	}
	private JTextField getTextField(int index){
		if(index == 0)return field_1;
		if(index == 1)return field_2;
		if(index == 2)return field_3;
		if(index == 3)return field_4;
		if(index == 4)return field_5;
		else return field_6;
	}
	private JLabel getLabel(int index){
		if(index == 0)return label_1;
		if(index == 1)return label_2;
		if(index == 2)return label_3;
		if(index == 3)return label_4;
		if(index == 4)return label_5;
		else return label_6;
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
	private void panelVisible(){
		if (label_1.getText() == "") {field_1.setVisible(false);}
		else{field_1.setVisible(true);}
		
		if (label_2.getText() == "") field_2.setVisible(false);
		else{field_2.setVisible(true);}
		
		if (label_3.getText() == "") field_3.setVisible(false);
		else{field_3.setVisible(true);}
		
		if (label_4.getText() == "") field_4.setVisible(false);
		else{field_4.setVisible(true);}
		
		if (label_5.getText() == "") field_5.setVisible(false);
		else{field_5.setVisible(true);}
		
		if (label_6.getText() == "") field_6.setVisible(false);
		else{field_6.setVisible(true);}
	}
	
	private void setupFields2(){
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(180,200));
		panel.setVisible(false);
	}
	private void updateFromInput(JTextField field){
		
		for(Map.Entry<String, Integer> entry: shopList.entrySet()){
			System.out.println(entry.getValue());
			System.out.println(field.getText());
			if(!field.getText().isEmpty()){
				Integer temp = new Integer(field.getText());
				shopList.put(entry.getKey(), temp);
			}
			//list.append(entry.getKey() + " | " + entry.getValue() + "\n");
		}
		uppdateCart();
		
	}
	
	private void setupFields(){
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(180,200));
		
		
		label_1 = new JLabel();
		field_1 = new JTextField("",2);
		field_1.addKeyListener(new KeyAdapter()
	    {
	        public void keyPressed(KeyEvent ke)
	        {
	            if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
	            {
	                updateFromInput(field_1);
	            }
	        }
	    });
		
		label_2 = new JLabel();
		field_2 = new JTextField("",2);

		label_3 = new JLabel();
		field_3 = new JTextField("",2);
		
		label_4 = new JLabel();
		field_4 = new JTextField("",2);
		
		label_5 = new JLabel();
		field_5 = new JTextField("",2);
		
		label_6 = new JLabel();
		field_6 = new JTextField("",2);
		
		panel.add(label_1);
		panel.add(field_1);
		panel.add(label_2);
		panel.add(field_2);
		panel.add(label_3);
		panel.add(field_3);
		panel.add(label_4);
		panel.add(field_4);
		panel.add(label_5);
		panel.add(field_5);
		panel.add(label_6);
		panel.add(field_6);
		
		panel.setVisible(false);
		
		
		
	}
	private JPanel panel;
	
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	
	private JTextField field_1;
	private JTextField field_2;
	private JTextField field_3;
	private JTextField field_4;
	private JTextField field_5;
	private JTextField field_6;
	
	
}
