package com.barclays.bmg.config.externalization.io;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * {@link Resource} implementation for <code>java.io.File</code> handles. Just
 * created to have support of custom editor for customizing resource loading
 *
 * @see org.springframework.core.io.FileSystemResource
 * @see com.barclays.bmg.config.externalization.io.support.ExternalizedResourceArrayPropertyEditor
 *  @author Govind Singh
 */
public class ExternalizedFileSystemResource extends FileSystemResource {

	public ExternalizedFileSystemResource(File file) {
		super(file);
	}

	/**
	 * Create a new FileSystemResource.
	 *
	 * @param path
	 *            a file path
	 */
	public ExternalizedFileSystemResource(String path) {
		super(path);
	}

}
