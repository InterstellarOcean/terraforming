package org.interstellarocean.terraforming;

public class VarargUtils {

	// static utility pattern - instantiation and extension is forbidden
	private VarargUtils() {
	}

	public static Object[] $(Object... arguments) {
		return arguments;
	}

	public static Object[][] $$(Object[]... arguments) {
		return arguments;
	}

}
