package com.rodrigouchoa;

import java.io.Serializable;


/**
 * Stands for a simple "form submission".
 * 
 * <p>
 * It might be used in any HTML control that submits an
 * form: Button.
 * 
 * @author Rodrigo Uchoa (rodrigo.uchoa@gmail.com)
 *
 */
@FunctionalInterface
public interface FormAction extends Serializable { //It MUST extends Serializable
	
	public abstract void onSubmit();

}
