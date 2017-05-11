package framework;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


/**
   A component filled with editors for all editable properties 
   of an object.
 */
public class PropertySheet extends JPanel
{
	public PropertySheet(Object bean){
		BeanInfo info;
		setSize(300, 300);
		PropertyEditor ed = null;
		try {
			info = Introspector.getBeanInfo(bean.getClass());

			PropertyDescriptor[] desc 
			= (PropertyDescriptor[])info.getPropertyDescriptors().clone();      
			for (int i = 0; i < desc.length; i++)
			{
				setLayout(new GridLayout(desc.length+1,2));
				Class type = desc[i].getPropertyType();
				Class editorClass = desc[i].getPropertyEditorClass();
				if (editorClass != null)            
					ed = (PropertyEditor) editorClass.newInstance();
				else
					ed = PropertyEditorManager.findEditor(type);
				if(ed != null){
					Method getter = desc[i].getReadMethod();
					Method setter = desc[i].getWriteMethod();

					final PropertyEditor editor = ed;

					editor.setValue(getter.invoke(bean, new Object[] {}));
					editor.addPropertyChangeListener(new
							PropertyChangeListener()
					{
						public void propertyChange(PropertyChangeEvent event)
						{
							try
							{
								setter.invoke(bean, 
										new Object[] { editor.getValue() });
								StateChanged(null);
							}
							catch (IllegalAccessException exception)
							{
								exception.printStackTrace();
							}
							catch (InvocationTargetException exception)
							{
								exception.printStackTrace();
							}
						}
					});

					if(editor != null){
						String text = editor.getAsText();
						JLabel itemName = new JLabel(desc[i].getDisplayName());


						final JTextField textField = new JTextField(text, 10);
						textField.getDocument().addDocumentListener(new
								DocumentListener()
						{
							public void insertUpdate(DocumentEvent e) 
							{
								try
								{
									editor.setAsText(textField.getText());
								}
								catch (IllegalArgumentException exception)
								{
								}
							}
							public void removeUpdate(DocumentEvent e) 
							{
								try
								{
									editor.setAsText(textField.getText());
								}
								catch (IllegalArgumentException exception)
								{
								}
							}
							public void changedUpdate(DocumentEvent e) 
							{
							}
						});
						add(itemName);
						add(textField);
					}
				}
			}
		}
		catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void addChangeListener(ChangeListener listener)
	{
		changeListeners.add(listener);
	}


	private void StateChanged(ChangeEvent event)
	{
		for (ChangeListener listener : changeListeners)
			listener.stateChanged(event);
	}

	private ArrayList<ChangeListener> changeListeners 
	= new ArrayList<ChangeListener>();

}


