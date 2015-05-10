/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming;

/**
 * @author Dariusz Wakuliński
 */
public class TestDto {

	private String name;

	private Long count;

	private String timestamp;

	private String state;

	private String ignoreMe;

	private String dtoOnly = "dtoOnly"; // don't touch me

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIgnoreMe() {
		return ignoreMe;
	}

	public void setIgnoreMe(String ignoreMe) {
		this.ignoreMe = ignoreMe;
	}

	public String getDtoOnly() {
		return dtoOnly;
	}

	public void setDtoOnly(String dtoOnly) {
		this.dtoOnly = dtoOnly;
	}

}
