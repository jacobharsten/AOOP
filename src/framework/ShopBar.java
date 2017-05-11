package framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

public class ShopBar extends JPanel {

	JTextArea list;
	private JLabel header;
	private JButton save;
	private HashMap<String, String> shopList;	
	private boolean visible;
	/**
	 * Construct a empty shopping bar.
	 */
	public ShopBar(){
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);

		shopList = new HashMap<String, String>();

		list = new JTextArea(10, 2);
		list.setEditable(false);
		list.setBackground(Color.BLACK);
		list.setForeground(Color.WHITE);



		ImageIcon kundvagn = new ImageIcon("src/images/shoppingCart.png");

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
		add(list, BorderLayout.CENTER);
		add(save, BorderLayout.SOUTH);
		setVisible(false);
	}

	/**
	 * Save the shopList to a text file.
	 */
	private void saveToFile(){
		JFileChooser c = new JFileChooser();
		if (c.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			try{
				File file = c.getSelectedFile();
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

	/**
	 * Set if the shopBar should be visible or not.
	 */
	public void setVisible(boolean temp){
		header.setVisible(temp);
		list.setVisible(temp);
		save.setVisible(temp);
		visible = temp;
	}
	/**
	 * Get if the ShopBar is visible.
	 * @return true if the shopBar is visible
	 */
	public boolean getVisible(){
		return visible;
	}
	/**
	 * Add a item to the shopping list
	 * @param item added item
	 */
	public void add(Object item){
		shopList.put(item.toString(), calculate(item));
		setVisible(true);
		updateCart();
	}
	/**
	 * Removes a selected item from the shopList. If the count is 0 it will remove the item. 
	 */
	public void remove(Object item){
		if(shopList.containsKey(item.toString())){
			int count = Integer.parseInt(shopList.get(item.toString()));
			count--;
			shopList.put(item.toString(), Integer.toString(count));
			if(count == 0){
				shopList.remove(item.toString());
			}
		}
		updateCart();
	}
	/**
	 * Empty the shopList and updates the cart. 
	 */
	public void emptyAll(){
		shopList.clear();
		updateCart();

	}
	/**
	 *  Adds all of the components in a List to the shopList
	 */
	public void addAll(List <Node> temp){
		for(int i=0; i<temp.size(); i++){
			add(temp.get(i));
		}
	}
	/**
	 * Calculates the how many of the item already exists and return the new number when adding. 
	 * @param item the item to add
	 * @return count of already existing item
	 */
	private String calculate(Object item){
		if(shopList.containsKey(item.toString())){
			Integer count = Integer.parseInt(shopList.get(item.toString()));
			count++;
			return Integer.toString(count);
		}
		return "1";
	}

	/**
	 * Updates the JTextArea to show the context of the shopList. 
	 */
	public void updateCart(){
		if(shopList.isEmpty()){
			setVisible(false);
		}
		list.setText("");
		for(Entry<String, String> entry: shopList.entrySet()){
			list.append(entry.getKey() + " | " + entry.getValue() + "\n");
		}
		list.getPreferredSize();
	}	
}
