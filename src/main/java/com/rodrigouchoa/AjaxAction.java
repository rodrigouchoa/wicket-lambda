package com.rodrigouchoa;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Stands for ajax request.
 * 
 * <p>
 * This interface would be used where a HTML form is not necessarily needed, 
 * for instance in a link: AjaxLink, AjaxFallbackLink.
 * 
 * Or any ajax-aware component: RadioChoice.
 * 
 * @author Rodrigo Uchoa (rodrigo.uchoa@gmail.com)
 *
 */
@FunctionalInterface
public interface AjaxAction extends Serializable { // It MUST extends Serializable
	
	public abstract void onClick(AjaxRequestTarget target);

}
