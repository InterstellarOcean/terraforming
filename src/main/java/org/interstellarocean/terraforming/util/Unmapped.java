/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Explicitly weaves requirement that {@link EnumInitUtil.SafeMapFrom#from(java.util.Collection)}
 * method's argument provides a non-empty collection.
 *
 * @author Dariusz Wakuliński
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface Unmapped {

}
