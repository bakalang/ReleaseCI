package com.asuscloud.doTestcase.action.base;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.asuscloud.common.TestcaseConstant;

public class ControlBase implements TestcaseConstant
{
	protected static <T extends Component> List<T> findAllChildren(JComponent component, Class<T> clazz)
	{
		List<T> lstChildren = new ArrayList<T>(5);
		for ( Component comp : component.getComponents() )
		{
			if ( clazz.isInstance(comp) )
			{
				lstChildren.add((T)comp);
			}
			else if ( comp instanceof JComponent )
			{
				lstChildren.addAll(findAllChildren((JComponent)comp, clazz));
			}
		}

		return Collections.unmodifiableList(lstChildren);
	}

	protected Map<String, String> getTextFieldsMap(List<?> tf)
	{
		Map<String, String> mp = new HashMap<String, String>();
		for ( int i = 0; i < tf.size(); i++ )
		{
			mp.put(( (JTextField)tf.get(i) ).getName(), ( (JTextField)tf.get(i) ).getText());
		}
		return mp;
	}

	protected List<String> getCheckBoxUnSelectMap(List<?> tf)
	{
		List<String> al = new ArrayList<String>();
		for ( int i = 0; i < tf.size(); i++ )
		{
			if ( !( (JCheckBox)tf.get(i) ).isSelected() )
				al.add(( (JCheckBox)tf.get(i) ).getName());
		}
		return al;
	}
}
