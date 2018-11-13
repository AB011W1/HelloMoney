package com.barclays.ussd.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.barclays.ussd.bean.CurrentRunningTransaction;
import com.barclays.ussd.bean.LanguageDTO;
import com.barclays.ussd.bean.LocaleDTO;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.ServiceMappingDTO;
import com.barclays.ussd.bean.SkipNodeDTO;
import com.barclays.ussd.bean.TwoFactorQuestion;
import com.barclays.ussd.common.configuration.ConfigurationManager;

public class UssdMenuBuilder {
    private static final String OPTION_ATTRIBUTE = "option";
    private static final String QUESTION_ID = "quesId";
    private static final String XML_PATH = "xmlPath";
    private static final String DISPLAY_LABEL_ID = "displayLabelId";
    private static final String LOCALE = "locale";
    private static final String NA = "NA";
    private static final String TYPE = "type";
    private static final String TRAN_DATA_OP_CODE = "tranDataOpCode";
    private static final String TRUE = "true";
    private static final String SKIP_NODE = "skipNode";
    private static final String PARENT_SCREEN_ID = "parentScreenId";
    private static final String DEFAULT_VALUE = "defaultValue";
    private Map<String, Document> domTreeMap = new HashMap<String, Document>();
    private Map<String, Document> serviceTreeDocMap = new HashMap<String, Document>();

    private Map<String, ServiceMappingDTO> serviceTreeMap = new HashMap<String, ServiceMappingDTO>();
    private Document txDocument;
    private String menuXmlFileNamePrefix = "MenuSkeleton_";
    private String txXmlFileNamePrefix = "Transaction";
    private String srvMapXmlFileNamePrefix = "ServiceMapping_";
    private String xmlFileNameSuffix = ".xml";
    private Map<String, NavigationOptionsDTO> navigationMap = new HashMap<String, NavigationOptionsDTO>();

    private final Map<String, CurrentRunningTransaction> transactionMap = new HashMap<String, CurrentRunningTransaction>();

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(UssdMenuBuilder.class);

    public void extractMenus() {
	LOGGER.debug("ExtractMenus Begins");

	File menuDir = null;
	try {
	    menuDir = getDirectory();
	    parseServiceMappingsXml(menuDir);
	    parseMenus(menuDir);
	    parseTransactions(menuDir);
	    parseSrvMapAndSkipNodes();
	    populateMenuTree(this.domTreeMap);

	} catch (URISyntaxException e) {
	    LOGGER.fatal("URISyntaxException occured: resources could not be read..", e);
	} catch (Exception e) {
	    LOGGER.fatal("Exception occured during initialization of the DOM trees..", e);
	}

    }

    public void parseSrvMapAndSkipNodes() {
	for (String key : serviceTreeDocMap.keySet()) {
	    populateServiceMappingMap(serviceTreeDocMap.get(key));
	}

    }

    public void populateServiceMappingMap(Document srvMapDocument) {
	LOGGER.debug("Inside populateServiceMappingMap");
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	try {
	    String searchExpr = "//srv";
	    XPathExpression expr = xpath.compile(searchExpr);
	    Object result = expr.evaluate(srvMapDocument, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);
		ServiceMappingDTO mappingDTO = buildServiceMappingDTO(node, srvMapDocument);
		this.serviceTreeMap.put(mappingDTO.getTranId(), mappingDTO);
	    }

	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while populating service mapping map ", e);
	}
    }

    public void populateMenuTree(Map<String, Document> domTreeMap) {
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	try {
	    String searchExpr = "//node[@tranId]";
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Importing the transactions in to the menu DOM tree");
	    }

	    for (Entry<String, Document> entrySet : domTreeMap.entrySet()) {
		Document doc = entrySet.getValue();
		XPathExpression expr = xpath.compile(searchExpr);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;

		for (int i = 0; i < nodes.getLength(); i++) {
		    Node node = nodes.item(i);
		    insertTransaction(node, doc, entrySet.getKey());
		}
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while populating menu tree", e);
	}
    }

    private CurrentRunningTransaction buildCurrentTransactionDataItem(final Node domNode) {

	NamedNodeMap attributeMap = domNode.getAttributes();
	CurrentRunningTransaction currRunningTransaction = new CurrentRunningTransaction();
	currRunningTransaction.setBmgOpCode(attributeMap.getNamedItem("bmgOpCode").getNodeValue());
	currRunningTransaction.setTranDataId(attributeMap.getNamedItem("tranDataId").getNodeValue());
	currRunningTransaction.setTransactionDataOpCode(attributeMap.getNamedItem("tranDataOpCode").getNodeValue());
	currRunningTransaction.setHeaderId(attributeMap.getNamedItem("headerId").getNodeValue());
	Node tempNode = attributeMap.getNamedItem("backOptionReq");
	currRunningTransaction.setBackOptionReq(tempNode != null ? tempNode.getNodeValue() : "false");

	tempNode = attributeMap.getNamedItem("homeOptionReq");
	currRunningTransaction.setHomeOptionReq(tempNode != null ? tempNode.getNodeValue() : "false");

	currRunningTransaction.setType(attributeMap.getNamedItem("type").getNodeValue());

	Node skipNode = attributeMap.getNamedItem("skipNode");
	if (skipNode != null) {
	    currRunningTransaction.setSkipNode(skipNode.getNodeValue());
	}
	Node defaultValueNode = attributeMap.getNamedItem("defaultValue");
	if (defaultValueNode != null) {
	    currRunningTransaction.setDefaultValue(defaultValueNode.getNodeValue());
	}

	Node parentScreenIdNode = attributeMap.getNamedItem("parentScreenId");
	if (parentScreenIdNode != null) {
	    currRunningTransaction.setParentScreenId(parentScreenIdNode.getNodeValue());
	}

	// currentRunningTransaction.setTranId(domNode.getParentNode().getAttributes().getNamedItem("tranId").getNodeValue());

	return currRunningTransaction;

    }

    public void insertTransaction(Node menuNode, Document menuDoc, String busiIdCntryCode) {
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	Element impNode = null;
	String tranid = null;
	Set<SkipNodeDTO> listSkipNodes = null;

	String tranId = menuNode.getAttributes().getNamedItem("tranId").getNodeValue();
	ServiceMappingDTO mappingDTO = this.serviceTreeMap.get(tranId);
	if (mappingDTO != null) {
	    tranid = mappingDTO.getValue();
	    listSkipNodes = mappingDTO.getSkippedNodes();
	}
	try {
	    if (StringUtils.isNotEmpty(tranid)) {
		String searchExpr = "//tx[@tranId='" + tranid + "']/transaction";
		XPathExpression expr = xpath.compile(searchExpr);
		Object result = null;
		NodeList nodes = null;

		result = expr.evaluate(this.serviceTreeDocMap.get(busiIdCntryCode), XPathConstants.NODESET);
		nodes = (NodeList) result;
		if (nodes == null || nodes.getLength() == 0) {
		    result = expr.evaluate(this.txDocument, XPathConstants.NODESET);
		    nodes = (NodeList) result;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
		    Node transactionNode = nodes.item(i);
		    CurrentRunningTransaction tranNodeDTO = buildTransactionDataItem(transactionNode);

		    Node importedTxNode = menuDoc.importNode(transactionNode, true);
		    impNode = (Element) importedTxNode;

		    Attr parentScreenIdAttribute = menuDoc.createAttribute(PARENT_SCREEN_ID);
		    String parentScreenId = menuNode.getAttributes().getNamedItem("screenId").getNodeValue();
		    parentScreenIdAttribute.setValue(parentScreenId);
		    impNode.setAttributeNode(parentScreenIdAttribute);

		    if (listSkipNodes != null) {
			for (SkipNodeDTO skipNode : listSkipNodes) {
			    if (skipNode.getTranDataId().equalsIgnoreCase(tranNodeDTO.getTranDataId())) {
				{
				    Attr skipNodeAttribute = menuDoc.createAttribute(SKIP_NODE);
				    skipNodeAttribute.setValue(TRUE);
				    impNode.setAttributeNode(skipNodeAttribute);

				    impNode.setAttribute(TRAN_DATA_OP_CODE, USSDConstants.NEGATE_UI_RENDERING);
				    impNode.setAttribute(TYPE, NA);
				}
				{
				    Attr defaultValueAttribute = menuDoc.createAttribute(DEFAULT_VALUE);
				    impNode.setAttributeNode(defaultValueAttribute);

				    impNode.setAttribute(DEFAULT_VALUE, skipNode.getDefaultValue());

				}
			    }
			}
		    }

		    // append attribute to contact element
		    menuNode.appendChild(impNode);

		    tranNodeDTO = buildCurrentTransactionDataItem(impNode);
		    tranNodeDTO.setTranId(tranId);
		    transactionMap.put(busiIdCntryCode + USSDConstants.SEPERATOR_UNDESCORE + tranId + USSDConstants.SEPERATOR_UNDESCORE
			    + tranNodeDTO.getTranDataId(), tranNodeDTO);

		}
	    }

	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while inserting the transaction into the menu tree", e);

	}
    }

    @SuppressWarnings("unchecked")
    public void parseServiceMappingsXml(File directory) {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(true);
	DocumentBuilder builder;
	try {
	    builder = factory.newDocumentBuilder();
	    RegexFileFilter regxFilter = new RegexFileFilter(getRegExpression(srvMapXmlFileNamePrefix));
	    List<File> files = (List<File>) FileUtils.listFiles(directory, regxFilter, TrueFileFilter.INSTANCE);

	    for (File srvMapXmlFile : files) {
		Document srvMapDocument = builder.parse(srvMapXmlFile);
		String fileNameWOExtn = srvMapXmlFile.getName().replaceAll(xmlFileNameSuffix, "");
		String businessId = fileNameWOExtn.split("_")[1];
		String countryCode = fileNameWOExtn.split("_")[2];
		this.serviceTreeDocMap.put((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode), srvMapDocument);
		parseNavigationOptions(businessId, countryCode);
	    }
	} catch (ParserConfigurationException e) {
	    LOGGER.fatal("Exception ocurred while parsing transaction.xml", e);
	} catch (SAXException e) {
	    LOGGER.fatal("Exception ocurred while reading transaction.xml", e);
	} catch (IOException e) {
	    LOGGER.fatal("I/O Exception ocurred while reading transaction.xml ", e);
	}

    }

    private String getRegExpression(String fileNamePrefix) {
	StringBuilder regx = new StringBuilder();
	regx.append("(^").append(fileNamePrefix).append(")(.*)(").append(xmlFileNameSuffix).append("$)");
	return regx.toString();
    }

    @SuppressWarnings("unchecked")
    public void parseTransactions(File directory) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("ParseTransaction begins...");
	}

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(true);
	DocumentBuilder builder;

	try {
	    builder = factory.newDocumentBuilder();
	    RegexFileFilter regxFilter = new RegexFileFilter(getRegExpression(txXmlFileNamePrefix));
	    List<File> files = (List<File>) FileUtils.listFiles(directory, regxFilter, TrueFileFilter.INSTANCE);

	    for (File txXmlFile : files) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Resource name read from directory:" + txXmlFile.getName());
		}
		this.txDocument = builder.parse(txXmlFile);
	    }
	} catch (ParserConfigurationException e) {
	    LOGGER.fatal("Exception ocurred while parsing transaction.xml", e);
	} catch (SAXException e) {
	    LOGGER.fatal("Exception ocurred while reading transaction.xml", e);
	} catch (IOException e) {
	    LOGGER.fatal("I/O Exception ocurred while reading transaction.xml ", e);
	}

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("ParseTransaction ends and transaction tree initialized successfully...");
	}

    }

    /**
     * @return File
     * @throws URISyntaxException
     */
    private File getDirectory() throws URISyntaxException {
	String xmlMenuPath = ConfigurationManager.getString(XML_PATH);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Reading resources from the path :" + xmlMenuPath);
	}
	// URL dirUrl = this.getClass().getClassLoader().getResource(xmlMenuPath);
	URL dirUrl = Thread.currentThread().getContextClassLoader().getResource(xmlMenuPath);
	File directory = new File(dirUrl.toURI());

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Reading resources from directory:" + directory.getName());
	}

	return directory;
    }

    @SuppressWarnings("unchecked")
    public void parseMenus(File directory) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("ParseMenus starts and menu tree initialization started...");
	}

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(true);
	DocumentBuilder builder;
	try {
	    builder = factory.newDocumentBuilder();
	    RegexFileFilter regxFilter = new RegexFileFilter(getRegExpression(menuXmlFileNamePrefix));
	    List<File> files = (List<File>) FileUtils.listFiles(directory, regxFilter, TrueFileFilter.INSTANCE);

	    for (File menuXmlFile : files) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Reading files: " + menuXmlFile.getName());
		}

		Document menuDocument = builder.parse(menuXmlFile);
		String fileNameWOExtn = menuXmlFile.getName().replaceAll(xmlFileNameSuffix, "");
		String businessId = fileNameWOExtn.split("_")[1];
		String countryCode = fileNameWOExtn.split("_")[2];
		NavigationOptionsDTO backHomeOptions = getNavigationOptions(businessId, countryCode);

		// insert back and home options on the screen
		insertBackAndHomeOnNodes(menuDocument, USSDConstants.XML_HOME_OPTION_ATTR, backHomeOptions.getHomeOption());
		insertBackAndHomeOnNodes(menuDocument, USSDConstants.XML_BACK_OPTION_ATTR, backHomeOptions.getBackOption());

		this.domTreeMap.put((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode), menuDocument);
	    }
	} catch (ParserConfigurationException e) {
	    LOGGER.fatal("Exception ocurred while parsing transaction.xml", e);
	} catch (SAXException e) {
	    LOGGER.fatal("Exception ocurred while reading transaction.xml", e);
	} catch (IOException e) {
	    LOGGER.fatal("I/O Exception ocurred while reading transaction.xml ", e);
	}

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("ParseMenus ends and menu tree initialized successfully...");

	}
    }

    public void insertBackAndHomeOnNodes(Document menuDocument, String attributName, String option) {
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	try {
	    String searchExpr = "//node[@" + attributName + "=\"true\"" + "]";
	    XPathExpression expr = xpath.compile(searchExpr);
	    Object result = expr.evaluate(menuDocument, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    for (int i = 0; i < nodes.getLength(); i++) {
		Node parentNode = nodes.item(i);

		if (parentNode.hasChildNodes()) {
		    Node firstChildNode = parentNode.getFirstChild().getNextSibling();
		    Node homeBackNode = menuDocument.createElement("node");
		    setBackAndHomeOptionAttributes(homeBackNode, attributName, parentNode.getAttributes()
			    .getNamedItem(USSDConstants.XML_NODE_ID_ATTR).getNodeValue(), firstChildNode.getAttributes().getNamedItem(
			    USSDConstants.XML_SCREEN_ID_ATTR).getNodeValue(), option);
		    parentNode.appendChild(homeBackNode);
		}
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while inserting home and back option...", e);
	}

    }

    public void setBackAndHomeOptionAttributes(Node node, String attributName, String parentNodeId, String screenId, String option) {
	if (attributName.equalsIgnoreCase(USSDConstants.XML_BACK_OPTION_ATTR)) {
	    setAttribute(node, USSDConstants.XML_NODE_ID_ATTR, parentNodeId + "back");
	    setAttribute(node, USSDConstants.XML_SCREEN_ID_ATTR, screenId);
	    setAttribute(node, USSDConstants.XML_LEVEL_ATTR, parentNodeId + "back");
	    setAttribute(node, USSDConstants.XML_OPTION_ID_ATTR, option);
	    setAttribute(node, USSDConstants.XML_LABEL_ID_ATTR, USSDConstants.BACK_LBL);
	    setAttribute(node, USSDConstants.XML_TRAN_ID_ATTR, USSDConstants.GO_BACK_TRAN_ID);
	} else {
	    setAttribute(node, USSDConstants.XML_NODE_ID_ATTR, parentNodeId + "home");
	    setAttribute(node, USSDConstants.XML_SCREEN_ID_ATTR, screenId);
	    setAttribute(node, USSDConstants.XML_LEVEL_ATTR, parentNodeId + "home");
	    setAttribute(node, USSDConstants.XML_OPTION_ID_ATTR, option);
	    setAttribute(node, USSDConstants.XML_LABEL_ID_ATTR, USSDConstants.GO_TO_HOME_PAGE_LABEL_ID);
	    setAttribute(node, USSDConstants.XML_TRAN_ID_ATTR, USSDConstants.GO_TO_HOME_PAGE_TRAN_ID);
	}
    }

    public void setAttribute(Node node, String attName, String val) {
	NamedNodeMap attributes = node.getAttributes();
	Node attNode = node.getOwnerDocument().createAttribute(attName);
	attNode.setNodeValue(val);
	attributes.setNamedItem(attNode);
    }

    public Map<String, Document> getDomTreeMap() {
	return domTreeMap;
    }

    public void setDomTreeMap(Map<String, Document> domTreeMap) {
	this.domTreeMap = domTreeMap;
    }

    public Document getTxDocument() {
	return txDocument;
    }

    public void setTxDocument(Document txDocument) {
	this.txDocument = txDocument;
    }

    private ServiceMappingDTO buildServiceMappingDTO(Node domNode, Document srvMapDocument) {
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	ServiceMappingDTO mappingDTO = new ServiceMappingDTO();
	mappingDTO.setTranId(domNode.getAttributes().getNamedItem("tranId").getNodeValue());
	mappingDTO.setValue(domNode.getAttributes().getNamedItem("importTran").getNodeValue());

	String searchExpr = "//srv[@tranId='" + mappingDTO.getTranId() + "']/skipNode";
	XPathExpression expr = null;
	try {
	    expr = xpath.compile(searchExpr);
	    Object result = null;
	    result = expr.evaluate(srvMapDocument, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    if (nodes != null) {
		Set<SkipNodeDTO> listSkipNodes = new HashSet<SkipNodeDTO>();
		for (int i = 0; i < nodes.getLength(); i++) {
		    Node skipNode = nodes.item(i);
		    SkipNodeDTO skipNodeDTO = buildSkipNodeDataItem(skipNode);
		    listSkipNodes.add(skipNodeDTO);
		}
		mappingDTO.setSkippedNodes(listSkipNodes);
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while mapping the menu options with the transactions...", e);
	}
	return mappingDTO;
    }

    public Map<String, ServiceMappingDTO> getServiceTreeMap() {
	return serviceTreeMap;
    }

    public void setServiceTreeMap(Map<String, ServiceMappingDTO> serviceTreeMap) {
	this.serviceTreeMap = serviceTreeMap;
    }

    private CurrentRunningTransaction buildTransactionDataItem(Node domNode) {
	String backOptionReq = "false";
	NamedNodeMap attributes = domNode.getAttributes();
	if (attributes.getNamedItem("backOptionReq") != null) {
	    backOptionReq = attributes.getNamedItem("backOptionReq").getNodeValue();
	}
	String homeOptionReq = "false";
	if (attributes.getNamedItem("homeOptionReq") != null) {
	    backOptionReq = attributes.getNamedItem("homeOptionReq").getNodeValue();
	}

	CurrentRunningTransaction currentRunningTransaction = new CurrentRunningTransaction();
	currentRunningTransaction.setBmgOpCode(domNode.getAttributes().getNamedItem("bmgOpCode").getNodeValue());
	currentRunningTransaction.setTranDataId(domNode.getAttributes().getNamedItem("tranDataId").getNodeValue());
	currentRunningTransaction.setTransactionDataOpCode(domNode.getAttributes().getNamedItem(TRAN_DATA_OP_CODE).getNodeValue());
	currentRunningTransaction.setHeaderId(domNode.getAttributes().getNamedItem("headerId").getNodeValue());
	currentRunningTransaction.setBackOptionReq(backOptionReq);
	currentRunningTransaction.setHomeOptionReq(homeOptionReq);
	currentRunningTransaction.setType(domNode.getAttributes().getNamedItem(TYPE).getNodeValue());
	return currentRunningTransaction;
    }

    private SkipNodeDTO buildSkipNodeDataItem(Node domNode) {
	SkipNodeDTO skipNodeDTO = new SkipNodeDTO();
	skipNodeDTO.setTranDataId(domNode.getAttributes().getNamedItem("tranDataId").getNodeValue());
	skipNodeDTO.setDefaultValue(domNode.getAttributes().getNamedItem("defaultValue").getNodeValue());
	return skipNodeDTO;
    }

    /**
     * This method returns default locale for country
     *
     * @param countryCode
     * @return String
     */
    public String getDefaultLocale(String countryCode, String businessId) {
	Document srvMapDoc = serviceTreeDocMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	String searchExpr = "//lang[@default='true']";
	XPathExpression expr = null;

	try {
	    expr = xpath.compile(searchExpr);
	    NodeList nodes = (NodeList) expr.evaluate(srvMapDoc, XPathConstants.NODESET);

	    if (nodes != null) {
		Node langNode = nodes.item(0);
		return langNode.getAttributes().getNamedItem(LOCALE).getNodeValue();
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while getting the default locale", e);
	}

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("No default localte could be found... getDefaultLocale returned null");
	}
	return null;
    }

    /**
     * This method returns default locale for country in a DTO
     *
     * @param countryCode
     * @return String
     */
    public LocaleDTO getDefaultLocaleDTO(String countryCode, String businessId) {
	LocaleDTO localeDTO = null;
	String locale = getDefaultLocale(countryCode, businessId);
	if (locale != null && StringUtils.isNotEmpty(locale)) {
	    localeDTO = new LocaleDTO();

	    localeDTO.setLanguage(locale.split(USSDConstants.UNDERSCORE_SEPERATOR)[0]);
	    localeDTO.setCountry(locale.split(USSDConstants.UNDERSCORE_SEPERATOR)[1]);
	}
	return localeDTO;
    }

    /**
     * This method returns phone number length for country
     *
     * @param countryCode
     * @return String
     */
    public MsisdnDTO getPhoneNoLength(String countryCode, String businessId) {
	MsisdnDTO msisdnDTO = new MsisdnDTO();
	Document srvMapDoc = serviceTreeDocMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	String searchExpr = "//msisdn/cclen";
	String searchExprSnLen = "//msisdn/snlen";
	String searchExprCCValue = "//msisdn/ccvalue";
	XPathExpression expr = null;

	try {
	    expr = xpath.compile(searchExpr);
	    Object result = null;
	    result = expr.evaluate(srvMapDoc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    String ccLen = StringUtils.EMPTY;

	    if (nodes != null) {
		Node msisdnCCLen = nodes.item(0);
		ccLen = msisdnCCLen.getTextContent();
	    }
	    expr = xpath.compile(searchExprSnLen);
	    result = expr.evaluate(srvMapDoc, XPathConstants.NODESET);
	    NodeList nodesSnLen = (NodeList) result;
	    String snLen = StringUtils.EMPTY;

	    if (nodesSnLen != null) {
		Node snLenNode = nodesSnLen.item(0);
		snLen = snLenNode.getTextContent();
	    }

	    expr = xpath.compile(searchExprCCValue);
	    result = expr.evaluate(srvMapDoc, XPathConstants.NODESET);
	    NodeList nodesCCValueNode = (NodeList) result;
	    String ccValue = StringUtils.EMPTY;

	    if (nodesCCValueNode != null) {
		Node ccValNode = nodesCCValueNode.item(0);
		ccValue = ccValNode.getTextContent();
	    }

	    if (NumberUtils.isNumber(ccLen)) {
		msisdnDTO.setCclen(Integer.parseInt(ccLen));
	    }
	    if (NumberUtils.isNumber(snLen)) {
		msisdnDTO.setSnlen(Integer.parseInt(snLen));
	    }
	    if (NumberUtils.isNumber(ccValue)) {
		msisdnDTO.setCcvalue(Integer.parseInt(ccValue));
	    }

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Phone details:" + msisdnDTO);
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while getting the phone length", e);
	}

	return msisdnDTO;
    }

    public LanguageDTO getLanguages(String countryCode, String businessId) {
	Document srvMapDoc = serviceTreeDocMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	LanguageDTO langDTO = new LanguageDTO();
	List<String> langSet = new ArrayList<String>();

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();

	String searchExpr = "//languages/lang";
	XPathExpression expr = null;

	try {
	    expr = xpath.compile(searchExpr);
	    NodeList nodes = (NodeList) expr.evaluate(srvMapDoc, XPathConstants.NODESET);

	    if (nodes != null) {
		for (int i = 0; i < nodes.getLength(); i++) {
		    Node langNode = nodes.item(i);
		    langSet.add(langNode.getAttributes().getNamedItem(LOCALE).getNodeValue());
		}
	    }

	    searchExpr = "//languages";
	    expr = xpath.compile(searchExpr);
	    nodes = (NodeList) expr.evaluate(srvMapDoc, XPathConstants.NODESET);

	    if (nodes != null) {
		// since there will be only one display label id
		Node langNode = nodes.item(0);
		langDTO.setDisplayLabelId(langNode.getAttributes().getNamedItem(DISPLAY_LABEL_ID).getNodeValue());

	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while extracting the languages", e);
	}

	langDTO.setLanguages(langSet);
	return langDTO;
    }

    public List<TwoFactorQuestion> getTwoFactQuesList(String countryCode, String businessId) {
	List<TwoFactorQuestion> questList = new ArrayList<TwoFactorQuestion>();
	Document srvMapDoc = serviceTreeDocMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();

	String searchExpr = "//twoFactQuesSet/question";
	XPathExpression expr = null;

	try {
	    expr = xpath.compile(searchExpr);
	    Object result = null;
	    result = expr.evaluate(srvMapDoc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    if (nodes != null) {
		TwoFactorQuestion question = null;
		for (int i = 0; i < nodes.getLength(); i++) {
		    question = new TwoFactorQuestion();
		    Node questNode = nodes.item(i);
		    question.setQuesId(questNode.getAttributes().getNamedItem(QUESTION_ID).getNodeValue());
		    questList.add(question);
		}
	    }

	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while extracting the languages", e);
	}

	return questList;
    }

    public List<TwoFactorQuestion> getSelfRegQuesList(String countryCode, String businessId) {
	List<TwoFactorQuestion> questList = new ArrayList<TwoFactorQuestion>();
	Document srvMapDoc = serviceTreeDocMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();

	String searchExpr = "//selfRegQuesSet/question";
	XPathExpression expr = null;

	try {
	    expr = xpath.compile(searchExpr);
	    Object result = null;
	    result = expr.evaluate(srvMapDoc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    if (nodes != null) {
		TwoFactorQuestion question = null;
		for (int i = 0; i < nodes.getLength(); i++) {
		    question = new TwoFactorQuestion();
		    Node questNode = nodes.item(i);
		    question.setQuesId(questNode.getAttributes().getNamedItem(QUESTION_ID).getNodeValue());
		    questList.add(question);
		}
	    }

	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while extracting the languages", e);
	}

	return questList;
    }

    public String getUserFrmMSISDN(UssdMenuBuilder ussdMenuBuilder, String countryCode, String msisdnNo, String businessId) {
	if (ussdMenuBuilder != null && StringUtils.isNotEmpty(countryCode) && StringUtils.isNotEmpty(msisdnNo)) {
	    MsisdnDTO msisdnDTO = getPhoneNoLength(countryCode, businessId);
	    return msisdnNo.substring(msisdnNo.length() - msisdnDTO.getSnlen(), msisdnNo.length());
	}
	return StringUtils.EMPTY;
    }

    public Map<String, Document> getServiceTreeDocMap() {
	return serviceTreeDocMap;
    }

    public void setServiceTreeDocMap(Map<String, Document> serviceTreeDocMap) {
	this.serviceTreeDocMap = serviceTreeDocMap;
    }

    public int getFirstScreen(String serviceName, String countryCode, String businessId) {
	int firstScreenSeqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ONE.getSequenceNo();
	Document srvMapDoc = serviceTreeDocMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Getting the first screen");
	}

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();

	String searchExpr = "//srv[@importTran='" + serviceName + "']";
	XPathExpression expr = null;

	try {
	    expr = xpath.compile(searchExpr);
	    Object result = null;
	    result = expr.evaluate(srvMapDoc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    if (nodes != null) {
		for (int i = 0; i < nodes.getLength(); i++) {
		    Node node = nodes.item(i);
		    Node namedItem = node.getAttributes().getNamedItem("firstScreenSequencerId");
		    if (namedItem != null) {
			String firstScreen = namedItem.getNodeValue();
			firstScreenSeqNo = Integer.parseInt(firstScreen);
			if (LOGGER.isDebugEnabled()) {
			    LOGGER.debug("Screen Found");
			}
			break;
		    }
		}
	    }

	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred while getting the first screen", e);
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("firstScreenSeqNo :" + firstScreenSeqNo);
	}

	return firstScreenSeqNo;
    }

    private NodeList getNavigationNodes(Document srvMapDoc, String option) throws XPathExpressionException {
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();

	XPathExpression expr = null;
	String searchExpr = "//navigationOptions/" + option;
	expr = xpath.compile(searchExpr);
	NodeList nodes = (NodeList) expr.evaluate(srvMapDoc, XPathConstants.NODESET);

	return nodes;
    }

    public NavigationOptionsDTO getNavigationOptions(String businessId, String countryCode) {
	NavigationOptionsDTO navigationOptionDTO = navigationMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	return navigationOptionDTO;
    }

    public NavigationOptionsDTO parseNavigationOptions(String businessId, String countryCode) {
	NavigationOptionsDTO navigationOptionDTO = new NavigationOptionsDTO();
	try {
	    Document srvMapDoc = serviceTreeDocMap.get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));

	    NodeList nodes = getNavigationNodes(srvMapDoc, "back");
	    if (nodes != null) {
		Node backNode = nodes.item(0);
		navigationOptionDTO.setBackOption(backNode.getAttributes().getNamedItem(OPTION_ATTRIBUTE).getNodeValue());
	    } else {
		navigationOptionDTO.setBackOption(USSDConstants.GO_BACK_OPTION);
	    }

	    nodes = getNavigationNodes(srvMapDoc, "home");
	    if (nodes != null) {
		Node homeNode = nodes.item(0);
		navigationOptionDTO.setHomeOption(homeNode.getAttributes().getNamedItem(OPTION_ATTRIBUTE).getNodeValue());
	    } else {
		navigationOptionDTO.setHomeOption(USSDConstants.GO_TO_HOME_OPTION);
	    }

	    nodes = getNavigationNodes(srvMapDoc, "scrollUp");
	    if (nodes != null) {
		Node scrollUpNode = nodes.item(0);
		navigationOptionDTO.setScrollUpOption(scrollUpNode.getAttributes().getNamedItem(OPTION_ATTRIBUTE).getNodeValue());
	    } else {
		navigationOptionDTO.setScrollUpOption(USSDConstants.SCROLL_UP_INPUT);
	    }

	    nodes = getNavigationNodes(srvMapDoc, "scrollDown");
	    if (nodes != null) {
		Node scrollDownNode = nodes.item(0);
		navigationOptionDTO.setScrollDownOption(scrollDownNode.getAttributes().getNamedItem(OPTION_ATTRIBUTE).getNodeValue());
	    } else {
		navigationOptionDTO.setScrollDownOption(USSDConstants.SCROLL_DOWN_INPUT);
	    }
	} catch (XPathExpressionException e) {
	    navigationOptionDTO.setBackOption(USSDConstants.GO_BACK_OPTION);
	    navigationOptionDTO.setHomeOption(USSDConstants.GO_TO_HOME_OPTION);
	    navigationOptionDTO.setScrollUpOption(USSDConstants.SCROLL_UP_INPUT);
	    navigationOptionDTO.setScrollDownOption(USSDConstants.SCROLL_DOWN_INPUT);
	    LOGGER.fatal("Exception ocurred while extracting the languages", e);
	}
	navigationMap.put((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode), navigationOptionDTO);
	return navigationOptionDTO;
    }

    public Map<String, NavigationOptionsDTO> getNavigationMap() {
	return navigationMap;
    }

    public Map<String, CurrentRunningTransaction> getTransactionMap() {
	return transactionMap;
    }
}
