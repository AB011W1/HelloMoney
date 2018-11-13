package com.barclays.bmg.config.externalization.io.support;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.CollectionFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.barclays.bmg.config.externalization.UnsupportedExternalizdedResourcePathException;
import com.barclays.bmg.config.externalization.io.ExternalizedFileSystemResource;

/**
 * A {@link ResourcePatternResolver} implementation that is able to resolve a
 * specified <code>externalized</code> resource location path into one or more
 * matching Resources. The source path may be a simple path which has a
 * one-to-one mapping to a target
 * {@link com.barclays.bmg.config.externalization.io.ExternalizedFileSystemResource}
 * and/or internal Ant-style regular expressions (matched using Spring's
 * {@link org.springframework.util.AntPathMatcher} utility). Both of the latter
 * are effectively wildcards.
 *
 * <p>
 * <b>No Wildcards:</b>
 *
 * <p>
 * In the simple case, if the specified location path does not start with the
 * <code>"classpath*:</code>" prefix, and does not contain a PathMatcher
 * pattern, this resolver will simply return a single externalized resource via
 * a <code>getResource()</code> . Examples are pseudo-URLs such as "
 * <code>classpath:/context.xml</code>", and simple unprefixed paths such as "
 * <code>/WEB-INF/context.xml</code>".
 *
 * <p>
 * <b>Ant-style Patterns:</b>
 *
 * <p>
 * When the path location contains an Ant-style pattern, e.g.:
 *
 * <pre>
 * /WEB-INF/*-context.xml
 * com/mycompany/** /applicationContext.xml
 * file:C:/some/path/*-context.xml
 * classpath:com/mycompany/** /applicationContext.xml
 * </pre>
 *
 * the resolver follows a more complex but defined procedure to try to resolve
 * the wildcard. It produces a <code>Resource</code> for the path up to the last
 * non-wildcard segment and obtains a <code>URL</code> from it. A
 * <code>java.io.File</code> is obtained from it using the externalized folder
 * location as root, and used to resolve the wildcard by walking the filesystem.
 *
 * <p>
 * <b><code>classpath*:</code> Prefix:</b>
 *
 * <p>
 * <b>WARNING:</b> There is special support for retrieving multiple class path
 * resources with the same name, via the "<code>classpath*:</code>" prefix is
 * <strong>NOT</strong> supported with this implementation.
 *
 * <p>
 * <b>WARNING:</b> Real URLs such as "<code>file:C:/context.xml</code>" are
 * <strong>NOT</strong> supported with this implementation.
 *
 *
 * @see #CLASSPATH_ALL_URL_PREFIX
 * @see org.springframework.util.AntPathMatcher
 *
 *
 *@author Govind Singh
 *
 */
@SuppressWarnings("unchecked")
public class ExternalizedPathMatchingResourcePatternResolver implements
		ResourcePatternResolver {

	protected final Log logger = LogFactory.getLog(getClass());

	private ClassLoader classLoader;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private String externalizedPropRootProperty;

	/**
	 * Create a new ExternalizedPathMatchingResourcePatternResolver with a
	 * property name for locating the external folder
	 *
	 */
	public ExternalizedPathMatchingResourcePatternResolver(
			String externalizedPropRootProperty) {
		this.externalizedPropRootProperty = externalizedPropRootProperty;
		this.classLoader = ClassUtils.getDefaultClassLoader();
	}

	/**
	 * Return the ClassLoader that this pattern resolver works with (never
	 * <code>null</code>).
	 */
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}

	/**
	 * Set the PathMatcher implementation to use for this resource pattern
	 * resolver. Default is AntPathMatcher.
	 *
	 * @see org.springframework.util.AntPathMatcher
	 */
	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}

	/**
	 * Return the PathMatcher that this resource pattern resolver uses.
	 */
	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}

	public Resource getResource(String location) {
		Assert.notNull(location, "Location must not be null");
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			return new FileSystemResource(new File(getExternalRootFolder(),
					location.substring(CLASSPATH_URL_PREFIX.length())));
		} else if (location.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
			return new FileSystemResource(new File(getExternalRootFolder(),
					location.substring(ResourceUtils.FILE_URL_PREFIX.length())));
		}

		return new FileSystemResource(new File(getExternalRootFolder(),
				location));
	}

	/**
	 * Returns the externalized root folder by retrieving the specified system
	 * property
	 *
	 * @return
	 */
	private String getExternalRootFolder() {
		String extPropRootFolderPath = System
				.getProperty(externalizedPropRootProperty);

		// TODO validate this StringUtils.hasText(extPropRootFolderPath);
		return extPropRootFolderPath;
	}

	public Resource[] getResources(String locationPattern) throws IOException {
		if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
			// throw exception we do not support this variant
			throw new UnsupportedExternalizdedResourcePathException(
					"Resource path  \"classpath*:\" is not supported for externalized resources");
		} else {
			// Only look for a pattern after a prefix here
			// (to not get fooled by a pattern symbol in a strange prefix).
			int prefixEnd = locationPattern.indexOf(":") + 1;
			if (getPathMatcher()
					.isPattern(locationPattern.substring(prefixEnd))) {
				// a file pattern
				return findPathMatchingResources(locationPattern);
			} else {
				// a single resource with the given name
				return new Resource[] { getResource(locationPattern) };
			}
		}
	}

	/**
	 * Find all resources that match the given location pattern via the
	 * Ant-style PathMatcher. Supports resources only in the file system.
	 *
	 * @param locationPattern
	 *            the location pattern to match
	 * @return the result as Resource array
	 * @throws IOException
	 *             in case of I/O errors
	 * @see #doFindPathMatchingJarResources
	 * @see #doFindPathMatchingFileResources
	 * @see org.springframework.util.PathMatcher
	 */
	@SuppressWarnings( { "deprecation" })
	protected Resource[] findPathMatchingResources(String locationPattern)
			throws IOException {
		String rootDirPath = determineRootDir(locationPattern);
		String subPattern = locationPattern.substring(rootDirPath.length());
		Resource[] rootDirResources = getResources(rootDirPath);
		Set result = CollectionFactory.createLinkedSetIfPossible(16);
		for (int i = 0; i < rootDirResources.length; i++) {
			Resource rootDirResource = rootDirResources[i];

			result.addAll(doFindPathMatchingFileResources(rootDirResource,
					subPattern));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Resolved location pattern [" + locationPattern
					+ "] to resources " + result);
		}
		return (Resource[]) result.toArray(new Resource[result.size()]);
	}

	/**
	 * Determine the root directory for the given location.
	 * <p>
	 * Used for determining the starting point for file matching, resolving the
	 * root directory location to a <code>java.io.File</code> and passing it
	 * into <code>retrieveMatchingFiles</code>, with the remainder of the
	 * location as pattern.
	 * <p>
	 * Will return "/WEB-INF" for the pattern "/WEB-INF/*.xml", for example.
	 *
	 * @param location
	 *            the location to check
	 * @return the part of the location that denotes the root directory
	 * @see #retrieveMatchingFiles
	 */
	protected String determineRootDir(String location) {
		int prefixEnd = location.indexOf(":") + 1;
		int rootDirEnd = location.length();
		while (rootDirEnd > prefixEnd
				&& getPathMatcher().isPattern(
						location.substring(prefixEnd, rootDirEnd))) {
			rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
		}
		if (rootDirEnd == 0) {
			rootDirEnd = prefixEnd;
		}

		String pathRoot = location.substring(0, rootDirEnd);
		return pathRoot;
	}

	/**
	 * Find all resources in the file system that match the given location
	 * pattern via the Ant-style PathMatcher.
	 *
	 * @param rootDirResource
	 *            the root directory as Resource
	 * @param subPattern
	 *            the sub pattern to match (below the root directory)
	 * @return the Set of matching Resource instances
	 * @throws IOException
	 *             in case of I/O errors
	 * @see #retrieveMatchingFiles
	 * @see org.springframework.util.PathMatcher
	 */

	protected Set doFindPathMatchingFileResources(Resource rootDirResource,
			String subPattern) throws IOException {
		File rootDir = null;
		try {
			rootDir = rootDirResource.getFile().getAbsoluteFile();
		} catch (IOException ex) {
			if (logger.isDebugEnabled()) {
				logger
						.debug(
								"Cannot search for matching files underneath "
										+ rootDirResource
										+ " because it does not correspond to a directory in the file system",
								ex);
			}
			return Collections.EMPTY_SET;
		}
		return doFindMatchingFileSystemResources(rootDir, subPattern);
	}

	/**
	 * Retrieve files that match the given path pattern, checking the given
	 * directory and its subdirectories.
	 *
	 * @param rootDir
	 *            the directory to start from
	 * @param pattern
	 *            the pattern to match against, relative to the root directory
	 * @return the Set of matching File instances
	 * @throws IOException
	 *             if directory contents could not be retrieved
	 */
	@SuppressWarnings("deprecation")
	protected Set retrieveMatchingFiles(File rootDir, String pattern)
			throws IOException {
		if (!rootDir.isDirectory()) {
			throw new IllegalArgumentException("Resource path [" + rootDir
					+ "] does not denote a directory");
		}
		String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(),
				File.separator, "/");
		if (!pattern.startsWith("/")) {
			fullPattern += "/";
		}
		fullPattern = fullPattern
				+ StringUtils.replace(pattern, File.separator, "/");
		Set result = CollectionFactory.createLinkedSetIfPossible(8);
		doRetrieveMatchingFiles(fullPattern, rootDir, result);
		return result;
	}

	/**
	 * Recursively retrieve files that match the given pattern, adding them to
	 * the given result list.
	 *
	 * @param fullPattern
	 *            the pattern to match against, with preprended root directory
	 *            path
	 * @param dir
	 *            the current directory
	 * @param result
	 *            the Set of matching File instances to add to
	 * @throws IOException
	 *             if directory contents could not be retrieved
	 */
	protected void doRetrieveMatchingFiles(String fullPattern, File dir,
			Set result) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Searching directory [" + dir.getAbsolutePath()
					+ "] for files matching pattern [" + fullPattern + "]");
		}
		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			throw new IOException("Could not retrieve contents of directory ["
					+ dir.getAbsolutePath() + "]");
		}
		boolean dirDepthNotFixed = (fullPattern.indexOf("**") != -1);
		for (int i = 0; i < dirContents.length; i++) {
			String currPath = StringUtils.replace(dirContents[i]
					.getAbsolutePath(), File.separator, "/");
			if (dirContents[i].isDirectory()
					&& (dirDepthNotFixed || StringUtils.countOccurrencesOf(
							currPath, "/") < StringUtils.countOccurrencesOf(
							fullPattern, "/"))) {
				doRetrieveMatchingFiles(fullPattern, dirContents[i], result);
			}
			if (getPathMatcher().match(fullPattern, currPath)) {
				result.add(dirContents[i]);
			}
		}
	}

	/**
	 * Find all resources in the file system that match the given location
	 * pattern via the Ant-style PathMatcher.
	 *
	 * @param rootDir
	 *            the root directory in the file system
	 * @param subPattern
	 *            the sub pattern to match (below the root directory)
	 * @return the Set of matching Resource instances
	 * @throws IOException
	 *             in case of I/O errors
	 * @see #retrieveMatchingFiles
	 * @see org.springframework.util.PathMatcher
	 */
	@SuppressWarnings("deprecation")
	protected Set doFindMatchingFileSystemResources(File rootDir,
			String subPattern) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for matching resources in directory tree ["
					+ rootDir.getPath() + "]");
		}
		Set matchingFiles = retrieveMatchingFiles(rootDir, subPattern);
		Set result = CollectionFactory.createLinkedSetIfPossible(matchingFiles
				.size());
		for (Iterator it = matchingFiles.iterator(); it.hasNext();) {
			File file = (File) it.next();
			result.add(new ExternalizedFileSystemResource(file));
		}
		return result;
	}

}
