package com.barclays.bmg.ecrime;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Configuration Reader using Apache Combined Configuration.
 */
public class ConfigurationReader implements InitializingBean {

    private static final transient Logger log = Logger.getLogger(ConfigurationReader.class);

    private String listNode;

    private List<String> mapperFiles;

    private CombinedConfiguration configuration;

    private String fileNamePattern;

    /**
     *
     */
    public ConfigurationReader() {
	configuration = new CombinedConfiguration();
    }

    public void afterPropertiesSet() throws Exception {
	Assert.notNull(listNode, "The List Node for the Configuration is Required");
	if (this.fileNamePattern == null && CollectionUtils.isEmpty(mapperFiles)) {
	    throw new IllegalArgumentException("Either the mapper File Name Pattern or the Mapper Files for the Configuration is Required");
	}

	this.configuration.getNodeCombiner().addListNode(listNode);

	if (fileNamePattern != null) {
	    String basePath = "";
	    String realPattern = fileNamePattern;
	    int lastIndex = fileNamePattern.lastIndexOf('/');

	    if (lastIndex > 0) {
		basePath = fileNamePattern.substring(0, lastIndex);
		realPattern = fileNamePattern.substring(lastIndex + 1);
	    }

	    URL url = getClass().getClassLoader().getResource(basePath);
	    Assert.notNull(url, "Can't find the basePath:" + basePath);
	    File dir = new File(url.getPath());
	    File[] files = dir.listFiles(new FileNamePatternFilter(realPattern));
	    Assert
		    .notEmpty(files, "no matched configuation files found in the folder: " + dir.getPath() + ",fileNamePattern:"
			    + this.fileNamePattern);

	    for (int i = 0; i < files.length; i++) {
		log.debug("load mapper file : " + files[i].getPath());
		XMLConfiguration config = new XMLConfiguration(files[i].getPath());
		configuration.addConfiguration(config);
	    }

	}

	if (CollectionUtils.isNotEmpty(mapperFiles)) {
	    for (String filePath : mapperFiles) {
		log.debug("load mapper file: " + filePath);
		XMLConfiguration config = new XMLConfiguration(filePath);
		configuration.addConfiguration(config);
	    }
	}

    }

    /**
     * Set the Mapper Files which will be load together.
     * 
     * @param mapperFiles
     *            the mapperFiles to set
     */
    public void setMapperFiles(List<String> mapperFiles) {
	this.mapperFiles = mapperFiles;
    }

    /**
     * Set the name of a node to the list of known list nodes. This means that nodes with this name will never be combined.
     * 
     * @param combinedNode
     */
    public void setListNode(String listNode) {
	this.listNode = listNode;
    }

    public CombinedConfiguration getConfiguration() {
	return configuration;
    }

    /**
     * Mapper File Name Pattern
     * 
     * @param fileNamePattern
     *            the fileNamePattern to set
     */
    public void setFileNamePattern(String fileNamePattern) {
	this.fileNamePattern = fileNamePattern;
    }

    private static class FileNamePatternFilter implements java.io.FilenameFilter {
	private Pattern filterPattern;

	private FileNamePatternFilter(String pattern) {
	    filterPattern = Pattern.compile(pattern.replaceAll("\\*", ".*"));
	}

	/**
	 * @param dir
	 * @param name
	 * @return
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	public boolean accept(File dir, String name) {
	    return filterPattern.matcher(name).matches();
	}
    }

}
