/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming;

import java.lang.Thread.State;
import java.time.Instant;

/**
 * @author Dariusz Wakuliński
 */
public class TestDomain {

	private String name;

	private Long count;

	private Instant timestamp;

	private State state;

	private String ignoreMe;

	private String domainOnly = "domainOnly"; // don't touch me

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

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getIgnoreMe() {
		return ignoreMe;
	}

	public void setIgnoreMe(String ignoreMe) {
		this.ignoreMe = ignoreMe;
	}

	public String getDomainOnly() {
		return domainOnly;
	}

	public void setDomainOnly(String domainOnly) {
		this.domainOnly = domainOnly;
	}

}
