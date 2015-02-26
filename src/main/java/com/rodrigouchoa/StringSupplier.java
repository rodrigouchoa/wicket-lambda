package com.rodrigouchoa;

import java.io.Serializable;

/*OBS: I did not use Java's own java.util.function.Supplier because it's
 * not serializable. */

/**
 * Simply returns a String.
 * 
 * @author Rodrigo Uchoa (rodrigo.uchoa@gmail.com)
 *
 */
@FunctionalInterface
public interface StringSupplier extends Serializable {
	
	public String get();

}
