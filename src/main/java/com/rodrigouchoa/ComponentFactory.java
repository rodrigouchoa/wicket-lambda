package com.rodrigouchoa;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

/**
 * Factory class for creating our components.
 * 
 * @author Rodrigo Uchoa (rodrigo.uchoa@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class ComponentFactory {

	/**
	 * Creates an ajax link.
	 * 
	 * @param wicketId the wicket id
	 * @param action lambda to handle the event
	 * 
	 * @return
	 */
	public static AjaxFallbackLink<Void> newAjaxLink(String wicketId, AjaxAction action) {
		AjaxFallbackLink<Void> link = new AjaxFallbackLink<Void>(wicketId) {
			@Override
			public void onClick(AjaxRequestTarget target) {
				action.onClick(target);
			}
		}; 
		return link;
    }

	/**
	 * Creates a Label.
	 * 
	 * @param wicketId the wicket id
	 * @param supplier the lambda expression that defines what label to show.
	 * 
	 * @return
	 */
	public static Label newLabel(String wicketId, StringSupplier supplier) {
		Label label = new Label(wicketId, new Model<String>() {
			@Override
			public String getObject() {
				return supplier.get();
			}
		});
		label.setOutputMarkupId(true);
		return label;
	}
	
	/**
	 * Creates a TextField.
	 * 
	 * @param wicketId the wicket id
	 * @param label the label shown in case of error message.
	 * @param required if the input is required or not.
	 * @param behaviors optional validations, etc.
	 * 
	 * @return
	 */
	public static <T> TextField<T> newTextField(String wicketId, String label, boolean required, Behavior... behaviors) {
    	TextField<T> tf = new TextField<T>(wicketId);
    	if (StringUtils.isNotBlank(label)) {
			tf.setLabel(new Model<String>(label));
		}
		tf.setRequired(required);
		if (behaviors != null && behaviors.length > 0) {
			for (Behavior b : behaviors) {
				tf.add(b);
			}
		}
    	return tf;
    }
	
	/**
	 * Creates a regular (non-ajax) button.
	 * 
	 * @param wicketId the wicket id
	 * @param labelSupplier a lambda that provides the label to show on the button
	 * @param action the lambda expression to handle the event
	 * 
	 * @return
	 */
	public static Button newButton(String wicketId, StringSupplier labelSupplier, FormAction action) {
		Button button = new Button(wicketId) {
			@Override
    		public void onSubmit() {
    			action.onSubmit();
    		}
		};
		
		AttributeModifier attrModifier = new AttributeModifier("value", new Model<String>() {
			@Override
			public String getObject() {				
				return labelSupplier.get();
			}
		});
		button.add(attrModifier);
		
		return button;  	
	}

}
