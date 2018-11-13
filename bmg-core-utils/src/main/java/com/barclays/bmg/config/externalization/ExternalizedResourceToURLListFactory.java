package com.barclays.bmg.config.externalization;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.FactoryBean;

import com.barclays.bmg.config.externalization.io.ExternalizedFileSystemResource;

/**
 *
 * FactoryBean for externalized resources.
 *
 * @author Govind Singh
 */
@SuppressWarnings("unchecked")
public class ExternalizedResourceToURLListFactory implements FactoryBean {
	/**
	 * List of resources.
	 */
	private ExternalizedFileSystemResource[] resources;

	/**
	 * Resources string to be converted to resources.
	 *
	 * @param inputResources
	 *            Resources path
	 */
	public void setResources(
			final ExternalizedFileSystemResource[] inputResources) {
		this.resources = inputResources.clone();
	}

	/**
	 * Gets the list of resources.
	 *
	 * @return List of resources
	 */
	public ExternalizedFileSystemResource[] getResources() {
		return resources.clone();
	}

	/**
	 * Overridden method from Factory Bean to provide the Object.
	 *
	 * @return Object The object from the Factory
	 * @throws IOException
	 *             In case of problem retreiving the files
	 */
	public final Object getObject() throws IOException {
		if (resources.length == 0) {
			return Collections.EMPTY_LIST;
		}

		List filenameList = new ArrayList(resources.length);

		for (int i = 0; i < resources.length; i++) {
			URL url = resources[i].getURL();
			filenameList.add(url.toString());
		}

		return filenameList;
	}

	/**
	 * Returns the type of object to be created by Factory.
	 *
	 * @return Class the class of which the object is being created.
	 */
	public final Class getObjectType() {
		return List.class;
	}

	/**
	 * Checks if the object is singleton.
	 *
	 * @return boolean True if singleton, false otherwise
	 */
	public final boolean isSingleton() {
		return false;
	}

	/**
	 * Overridden toString method.
	 *
	 * @return String string interpretation of class
	 */
	@Override
	public final String toString() {
		ToStringBuilder buffer = new ToStringBuilder(this);

		buffer.appendSuper(super.toString());

		buffer.append("resources", resources);

		return buffer.toString();
	}
}
