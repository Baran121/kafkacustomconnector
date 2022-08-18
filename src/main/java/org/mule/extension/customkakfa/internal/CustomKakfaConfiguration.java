package org.mule.extension.customkakfa.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import java.util.*;
/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(CustomKakfaOperations.class)
@ConnectionProviders(CustomKakfaConnectionProvider.class)
public class CustomKakfaConfiguration {
/**
	 * A parameter that is always required to be configured.
	 */
	@Parameter
	private String username;

	public String getUserName() {
		return username;
	}

	/**
	 * A parameter that is not required to be configured by the user.
	 */
	@Parameter
	private String password;

	public String getPassword() {
		return password;
	}

	@Parameter
	private HashMap<String, String> assignments;

	public HashMap<String, String> getAssignments() {
		return assignments;
	}

	
	@Parameter
	private String groupId;

	public String getGroupId() {
		return groupId;
	}


	@Parameter
	private String bootStrapServerURL;

	public String getBootStrapServerURL() {
		return bootStrapServerURL;
	}
}
