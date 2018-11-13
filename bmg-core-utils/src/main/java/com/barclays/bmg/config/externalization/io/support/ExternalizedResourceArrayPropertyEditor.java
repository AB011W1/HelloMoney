package com.barclays.bmg.config.externalization.io.support;

import org.springframework.core.io.support.ResourceArrayPropertyEditor;


/**
 * Editor for
 * {@link com.barclays.bmg.config.externalization.io.ExternalizedFileSystemResource}
 * arrays, to automatically convert <code>String</code> location patterns
 * (e.g. <code>"file:C:/my*.txt"</code> or <code>"classpath:myfile.txt"</code>)
 * to <code>ExternalizedFileSystemResource</code> array properties loaded from
 * an external folder. The external folder is located through a system property.
 *
 * @see org.springframework.core.io.support.ResourceArrayPropertyEditor
 * @see com.barclays.bmg.config.externalization.io.support.ExternalizedPathMatchingResourcePatternResolver
 * @author Govind Singh
 */
public class ExternalizedResourceArrayPropertyEditor extends
		ResourceArrayPropertyEditor {

	/**
	 * Create a new ResourceArrayPropertyEditor with a property name for
	 * locating the external folder
	 *
	 * @param externalizedPropRootProperty
	 */
	public ExternalizedResourceArrayPropertyEditor(
			String externalizedPropRootProperty) {
		super(new ExternalizedPathMatchingResourcePatternResolver(
				externalizedPropRootProperty));
	}

}
