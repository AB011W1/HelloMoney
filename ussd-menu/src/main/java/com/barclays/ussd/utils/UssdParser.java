package com.barclays.ussd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.CurrentRunningTransaction;
import com.barclays.ussd.bean.MenuItem;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.bean.Transaction;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.parser.UssdDecisionParserParamDTO;

public class UssdParser {
    private static final String SCREEN_ID = "screenId";
    private static final String HEADER_ID = "headerId";
    private static final String NODE_ID = "id";
    private static final String TRANSACTION_ID = "tranId";
    private static final String LABEL_ID = "labelId";
//    private Document documentObject;

    private static final String NODE_SCREENID_SEARCH_EXPR = "//node[@screenId='";
    private static final String NODE_ID_SEARCH_EXPR = "//node[@id='";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(UssdParser.class);

    UssdMenuBuilder ussdMenuBuilder;

    public MenuItemDTO getMenuList(UssdDecisionParserParamDTO paramDTO, List<String> errorCodes) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving the required menu screen");
	}
	MenuItemDTO menuItemDTO = null;
	try {
	    final Map<String, Document> domTreeMap = ussdMenuBuilder.getDomTreeMap();
	    final Document document = domTreeMap.get((paramDTO.getBusinessId() + USSDConstants.SEPERATOR_UNDESCORE + paramDTO.getCountryCode()));
//	    this.documentObject = document;
	    menuItemDTO = searchNodeChildrenForUserSelection(paramDTO, document, errorCodes);
	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred while navigating the menus", e);
	}
	return menuItemDTO;
    }

    /*
     * Retrieve the sub menu from the DOM tree on the basis of selected Option Id
     */
    private MenuItemDTO searchNodeChildrenForUserSelection(UssdDecisionParserParamDTO paramDTO, Document document, List<String> errorCodes) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	List<MenuItem> menuItemList = new ArrayList<MenuItem>();
	MenuItem menuItem = null;
	Node headerNode = null;

	// FreeDialUSSD -->
	/*paramDTO.setUserInput("1");*/

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	try {
	    StringBuilder searchExpr = new StringBuilder(NODE_SCREENID_SEARCH_EXPR);
	    searchExpr.append(paramDTO.getCurrentScreenId()).append("' and @optionId='").append(paramDTO.getUserInput()).append("']/node");

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Searching the sub menu for the current screen based on the selected option...");
	    }
	    XPathExpression expr = xpath.compile(searchExpr.toString());
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Search the menu tree using the xPath ");
	    }
	    Object result = expr.evaluate(document, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    StringBuilder searchHeaderExpr = new StringBuilder(NODE_SCREENID_SEARCH_EXPR);
	    searchHeaderExpr.append(paramDTO.getCurrentScreenId()).append("' and @optionId='").append(paramDTO.getUserInput()).append("']");
	    XPathExpression exprHeader = xpath.compile(searchHeaderExpr.toString());
	    Object headerResult = exprHeader.evaluate(document, XPathConstants.NODESET);
	    NodeList headerNodes = (NodeList) headerResult;
	    if (headerNodes != null && headerNodes.getLength() > 0) {
		headerNode = headerNodes.item(0);
	    }

	    // If the current screen is the leaf node
	    if (nodes != null && nodes.getLength() == 0) {
		if (headerNode != null) {
		    // This block gets executed when there are no more sub menus
		    // to be rendered from the xml
		    menuItemDTO.setAccessType(headerNode.getAttributes().getNamedItem("accessType") != null ? headerNode.getAttributes()
			    .getNamedItem("accessType").getNodeValue() : "full");
		    menuItemDTO.setTranNodeId(headerNode.getAttributes().getNamedItem(NODE_ID).getNodeValue());
		    menuItemDTO.setTranId(headerNode.getAttributes().getNamedItem(TRANSACTION_ID).getNodeValue());
		    menuItemDTO.setLabelId(headerNode.getAttributes().getNamedItem(LABEL_ID).getNodeValue());
		} else {
		    if (LOGGER.isInfoEnabled()) {
			LOGGER.info("User has entered an invalid input");
			LOGGER.info("Populating the error message and refreshing the same screen");
		    }
		    /* If its an invalid input */
		    menuItemDTO = getMenuListWithNodeId(paramDTO.getCurrentScreenNodeId(), xpath, document);
		    errorCodes.add(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode());
		    // menuItemDTO.setPageHeader(USSDConstants.INVALID_INPUT_LABEL);
		}
	    } else {
		if (headerNode != null) {
		    menuItemDTO.setNextScreenNodeId(headerNode.getAttributes().getNamedItem(NODE_ID).getNodeValue());
		    menuItemDTO.setPageHeader(headerNode.getAttributes().getNamedItem(HEADER_ID).getNodeValue());
		}

		if (nodes != null) {
		    for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			menuItem = buildMenuItem(node);
			menuItemList.add(menuItem);
			menuItemDTO.setNextScreenId(node.getAttributes().getNamedItem(SCREEN_ID).getNodeValue());
		    }
		}
		menuItemDTO.setMenuItemList(menuItemList);
	    }

	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while searching the next menu for the user request", e);
	}
	return menuItemDTO;
    }

    private MenuItem buildMenuItem(Node domNode) {
	MenuItem menuItem = new MenuItem();
	menuItem.setLabelId(domNode.getAttributes().getNamedItem("labelId").getNodeValue());
	menuItem.setLevel(domNode.getAttributes().getNamedItem("level").getNodeValue());
	menuItem.setNodeId(domNode.getAttributes().getNamedItem("id").getNodeValue());
	menuItem.setOptionId(domNode.getAttributes().getNamedItem("optionId").getNodeValue());
	menuItem.setScreenId(domNode.getAttributes().getNamedItem("screenId").getNodeValue());
	return menuItem;
    }

    public MenuItemDTO getMenuListWithNodeId(String currentScreenNodeId, XPath xpath, Document document) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	List<MenuItem> menuItemList = new ArrayList<MenuItem>();
	MenuItem menuItem = null;
	Node headerNode = null;

	try {
	    // String searchExpr = "//node[@id='" + currentScreenNodeId +
	    // "']/node";
	    StringBuilder searchExpr = new StringBuilder(NODE_ID_SEARCH_EXPR).append(currentScreenNodeId).append("']/node");
	    XPathExpression expr = xpath.compile(searchExpr.toString());
	    Object result = expr.evaluate(document, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    // String searchHeaderExpr = "//node[@id='" + currentScreenNodeId +
	    // "']";
	    StringBuilder searchHeaderExpr = new StringBuilder(NODE_ID_SEARCH_EXPR).append(currentScreenNodeId).append("']");
	    XPathExpression exprHeader = xpath.compile(searchHeaderExpr.toString());
	    Object headerResult = exprHeader.evaluate(document, XPathConstants.NODESET);
	    NodeList headerNodes = (NodeList) headerResult;
	    if (headerNodes != null && headerNodes.getLength() > 0) {
		headerNode = headerNodes.item(0);
	    }

	    int length = 0;
	    if (nodes != null) {
		length = nodes.getLength();
	    }

	    if (nodes != null && length == 0) {
		if (headerNode != null) {
		    // This block gets executed when there are no more sub menus
		    // to be rendered from the xml
		    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The user has reached the leaf node for the menu; now the transactions shall began");
		    }
		    menuItemDTO.setTranNodeId(headerNode.getAttributes().getNamedItem(NODE_ID).getNodeValue());
		    menuItemDTO.setTranId(headerNode.getAttributes().getNamedItem(TRANSACTION_ID).getNodeValue());
		}
	    } else {
		if (headerNode != null) {
		    menuItemDTO.setNextScreenNodeId(headerNode.getAttributes().getNamedItem(NODE_ID).getNodeValue());
		    menuItemDTO.setPageHeader(headerNode.getAttributes().getNamedItem(HEADER_ID).getNodeValue());
		}

		if (nodes != null) {
		    for (int i = 0; i < length; i++) {
			Node node = nodes.item(i);
			menuItem = buildMenuItem(node);
			menuItemList.add(menuItem);
			menuItemDTO.setNextScreenId(node.getAttributes().getNamedItem(SCREEN_ID).getNodeValue());
		    }
		}
		menuItemDTO.setMenuItemList(menuItemList);
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while searching a menu node with Id", e);
	}

	return menuItemDTO;
    }

    public CurrentRunningTransaction getNextTransaction(Transaction userTransactionDetails, String serviceName, USSDSessionManagement ussdSessionMgmt) {
	CurrentRunningTransaction currentRunningTransaction = null;
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();

    final String businessCountryKey = ussdSessionMgmt.getBusinessId() + USSDConstants.SEPERATOR_UNDESCORE + ussdSessionMgmt.getCountryCode();
    final Map<String, CurrentRunningTransaction> transactionMap = ussdMenuBuilder.getTransactionMap();

    if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving the subsequent transaction screen");
	}

	try {
	    CurrentRunningTransaction currentRunningTransaction2 = userTransactionDetails.getCurrentRunningTransaction();
	    String tranDataId = currentRunningTransaction2.getTranDataId();
	    if (StringUtils.isEmpty(tranDataId)) {
			currentRunningTransaction2.setServiceName(serviceName);
			// get the first child
			// searchExpr = "//node[@id='" +
			// currentRunningTransaction2.getTranNodeId() +
			// "']/transaction[1]";
			int firstScreenSequenceNumber = ussdMenuBuilder.getFirstScreen(serviceName, ussdSessionMgmt.getUserProfile().getCountryCode(),
				ussdSessionMgmt.getUserProfile().getBusinessId());

			if (firstScreenSequenceNumber <= 0) {
			    firstScreenSequenceNumber = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ONE.getSequenceNo();
			}
			tranDataId = USSDUtils.getNextTransactionStep(serviceName, firstScreenSequenceNumber);
			currentRunningTransaction2.setNextScreenSequenceNumber(firstScreenSequenceNumber);


	    } else {
		int nextScreenSequenceNumber = currentRunningTransaction2.getNextScreenSequenceNumber();
		tranDataId = USSDUtils.getNextTransactionStep(currentRunningTransaction2.getServiceName(), nextScreenSequenceNumber);

		currentRunningTransaction2.setNextScreenSequenceNumber(nextScreenSequenceNumber);

	    }


	    String searchKey = businessCountryKey + USSDConstants.SEPERATOR_UNDESCORE + currentRunningTransaction2.getTranId()
		    + USSDConstants.SEPERATOR_UNDESCORE + tranDataId;

	    currentRunningTransaction = transactionMap.get(searchKey);

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Search the next transaction using the transactionMap searchKey:[" + searchKey + "]");
		LOGGER.debug("**********currentRunningTransaction2**********" + currentRunningTransaction2);
		if (currentRunningTransaction2 != null) {
		    currentRunningTransaction2.toString();
		}

		LOGGER.debug("**********currentRunningTransaction**********" + currentRunningTransaction);
		if (currentRunningTransaction != null) {
		    currentRunningTransaction.toString();
		}
	    }

		currentRunningTransaction.setTranId(currentRunningTransaction2.getTranId());
		currentRunningTransaction.setTranNodeId(currentRunningTransaction2.getTranNodeId());
		currentRunningTransaction.setServiceName(currentRunningTransaction2.getServiceName());
		currentRunningTransaction.setNextScreenSequenceNumber(currentRunningTransaction2.getNextScreenSequenceNumber());
		userTransactionDetails.setCurrentRunningTransaction(currentRunningTransaction);
	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred while searching the next transaction", e);
	    LOGGER.error("Exception ocurred while searching the next transaction", e);
	}
	if (currentRunningTransaction == null) {
	    if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("currentRunningTransaction is null");
		}
		LOGGER.info("currentRunningTransaction is null");
	    userTransactionDetails.setCurrentRunningTransaction(null);
	}
    LOGGER.info("Exit getNextTransaction");
	return currentRunningTransaction;
    }

    //Moved into UssdMenuBuilder class
    private CurrentRunningTransaction buildTransactionDataItem(Node domNode) {
	CurrentRunningTransaction currentRunningTransaction = new CurrentRunningTransaction();
	currentRunningTransaction.setBmgOpCode(domNode.getAttributes().getNamedItem("bmgOpCode").getNodeValue());
	currentRunningTransaction.setTranDataId(domNode.getAttributes().getNamedItem("tranDataId").getNodeValue());
	currentRunningTransaction.setTransactionDataOpCode(domNode.getAttributes().getNamedItem("tranDataOpCode").getNodeValue());
	currentRunningTransaction.setHeaderId(domNode.getAttributes().getNamedItem("headerId").getNodeValue());
	currentRunningTransaction.setBackOptionReq(domNode.getAttributes().getNamedItem("backOptionReq") != null ? domNode.getAttributes()
		.getNamedItem("backOptionReq").getNodeValue() : "false");
	currentRunningTransaction.setHomeOptionReq(domNode.getAttributes().getNamedItem("homeOptionReq") != null ? domNode.getAttributes()
		.getNamedItem("homeOptionReq").getNodeValue() : "false");
	currentRunningTransaction.setType(domNode.getAttributes().getNamedItem("type").getNodeValue());

	Node skipNode = domNode.getAttributes().getNamedItem("skipNode");
	if (skipNode != null) {
	    currentRunningTransaction.setSkipNode(skipNode.getNodeValue());
	}
	Node defaultValueNode = domNode.getAttributes().getNamedItem("defaultValue");
	if (defaultValueNode != null) {
	    currentRunningTransaction.setDefaultValue(defaultValueNode.getNodeValue());
	}

	Node parentScreenIdNode = domNode.getAttributes().getNamedItem("parentScreenId");
	if (parentScreenIdNode != null) {
	    currentRunningTransaction.setParentScreenId(parentScreenIdNode.getNodeValue());
	}

	currentRunningTransaction.setTranId(domNode.getParentNode().getAttributes().getNamedItem("tranId").getNodeValue());
	return currentRunningTransaction;
    }

    public UssdDecisionParserParamDTO performBackOperation(UssdDecisionParserParamDTO paramDTO) {
	UssdDecisionParserParamDTO newParamDTO = new UssdDecisionParserParamDTO();

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving the previous screen as per the users Back action");
	}
	try {

	    Map<String, Document> domTreeMap = ussdMenuBuilder.getDomTreeMap();
	    Document document = domTreeMap.get((paramDTO.getBusinessId() + USSDConstants.SEPERATOR_UNDESCORE + paramDTO.getCountryCode()));
	    StringBuilder searchExpr = new StringBuilder(NODE_ID_SEARCH_EXPR).append(paramDTO.getCurrentScreenNodeId()).append("']");
	    XPathExpression expr = xpath.compile(searchExpr.toString());
	    Object result = expr.evaluate(document, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;

	    if (nodes != null && nodes.getLength() > 0) {
		goToGrandParent(paramDTO, newParamDTO, nodes);
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while performing back action for the menu", e);
	}

	return newParamDTO;
    }

    /**
     * @param paramDTO
     * @param newParamDTO
     * @param nodes
     */
    private void goToGrandParent(UssdDecisionParserParamDTO paramDTO, UssdDecisionParserParamDTO newParamDTO, NodeList nodes) {
	Node currentNode = nodes.item(0);
	Node parentNode = currentNode.getParentNode();

	Node grandParentNode = null;
	String nodeValue = parentNode.getAttributes().getNamedItem("id").getNodeValue();
	if (StringUtils.equalsIgnoreCase(USSDConstants.WELCOME_PAGE_NODE_ID, nodeValue)
		|| StringUtils.equalsIgnoreCase(USSDConstants.UNREG_USER_WELCOME_PAGE_NODE_ID, nodeValue)) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("User is on the first menu screen");
	    }
	    grandParentNode = parentNode;
	} else {
	    grandParentNode = parentNode.getParentNode();
	}

	newParamDTO.setBusinessId(paramDTO.getBusinessId());
	newParamDTO.setCurrentScreenNodeId(grandParentNode.getAttributes().getNamedItem("id").getNodeValue());
	newParamDTO.setUserInput(parentNode.getAttributes().getNamedItem("optionId").getNodeValue());
	newParamDTO.setCurrentScreenId(parentNode.getAttributes().getNamedItem("screenId").getNodeValue());
	newParamDTO.setCountryCode(paramDTO.getCountryCode());
    }

    public UssdDecisionParserParamDTO performBackOperationForTx(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt)
	    throws USSDBlockingException {
	UssdDecisionParserParamDTO newParamDTO = new UssdDecisionParserParamDTO();
	String serviceName = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName();
	paramDTO.setServiceName(serviceName);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving previous screen within a transaction as per the user's Back action");
	}
	Stack<Integer> screenSequenceStack = ussdSessionMgmt.getUserTransactionDetails().getScreenSequenceStack();
	Integer sequenceNo = null;
	Integer previousSeqNo = null;

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	Map<String, Document> domTreeMap = ussdMenuBuilder.getDomTreeMap();
	Document document = domTreeMap.get((paramDTO.getBusinessId() + USSDConstants.SEPERATOR_UNDESCORE + paramDTO.getCountryCode()));
	XPathExpression expr = null;
	Object result = null;
	NodeList nodes = null;
	try {
	    do {
		if (screenSequenceStack == null || screenSequenceStack.empty()) {
		    goToParentOnBack(paramDTO, ussdSessionMgmt, newParamDTO, xpath, document);
		    ussdSessionMgmt.clean();
		    return newParamDTO;
		} else {
		    sequenceNo = screenSequenceStack.pop();
		    if (screenSequenceStack.empty()) {
			goToParentOnBack(paramDTO, ussdSessionMgmt, newParamDTO, xpath, document);
			ussdSessionMgmt.clean();
			return newParamDTO;
		    } else {
			if (nodes != null
				&& USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode")
					.getNodeValue())) {
			    previousSeqNo = sequenceNo;
			} else {
			    previousSeqNo = screenSequenceStack.pop();
			}
			nodes = getTreeNodeForSequence(paramDTO, ussdSessionMgmt, xpath, document, previousSeqNo);
		    }
		}
	    } while (USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode").getNodeValue()));

	    if (screenSequenceStack.empty()) {
		CurrentRunningTransaction currentRunningTransaction = new CurrentRunningTransaction();
		currentRunningTransaction.setTranId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId());
		currentRunningTransaction.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());

		Transaction userTransactionDetails = new Transaction();
		userTransactionDetails.setCurrentRunningTransaction(currentRunningTransaction);

		ussdSessionMgmt.setTransactionFlag(true);
		ussdSessionMgmt.setUserTransactionDetails(userTransactionDetails);

		newParamDTO.setServiceName(serviceName);
		newParamDTO.setUserInput(null);
		newParamDTO.setUserTransactionDetails(userTransactionDetails);
		newParamDTO.setCountryCode(ussdSessionMgmt.getCountryCode());
		newParamDTO.setBusinessId(ussdSessionMgmt.getBusinessId());

		return newParamDTO;
	    } else {
		Integer p2prevSeq = screenSequenceStack.peek();
		nodes = getTreeNodeForSequence(paramDTO, ussdSessionMgmt, xpath, document, p2prevSeq);
	    }

	    if (nodes.item(0) != null) {
		CurrentRunningTransaction crTx = buildTransactionDataItem(nodes.item(0));
		crTx.setNextScreenSequenceNumber(previousSeqNo);
		crTx.setServiceName(serviceName);
		crTx.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());
		String tranParamName = USSDInputParamsEnum.getUssdParamForTran(crTx.getTranDataId());
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		String userInput = userInputMap.get(tranParamName);
		newParamDTO.setUserInput(userInput);
		newParamDTO.setCountryCode(ussdSessionMgmt.getCountryCode());
		newParamDTO.setBusinessId(ussdSessionMgmt.getBusinessId());
		ussdSessionMgmt.getUserTransactionDetails().setCurrentRunningTransaction(crTx);
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while performing back action for transaction", e);
	    throw new USSDBlockingException(USSDBlockingExceptions.XPATH_ERROR.getErrorCode(), e.getMessage());
	}
	return newParamDTO;
    }

    private void goToParentOnBack(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt, UssdDecisionParserParamDTO newParamDTO,
	    XPath xpath, Document document) throws XPathExpressionException {
	StringBuffer searchExpr = new StringBuffer("//node");
	// String searchExpr;
	XPathExpression expr;
	Object result;
	NodeList nodes;

	CurrentRunningTransaction currentRunningTransaction = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction();
	searchExpr.append("[@tranId='").append(currentRunningTransaction.getTranId()).append("'");
	if (currentRunningTransaction.getParentScreenId() != null) {
	    searchExpr.append(" and @screenId='").append(currentRunningTransaction.getParentScreenId()).append("'");
	}
	searchExpr.append("]/transaction[@tranDataId='").append(currentRunningTransaction.getTranDataId()).append("'");
	searchExpr.append("]/../../*");

	// searchExpr = "//node[@tranId='" + currentRunningTransaction.getTranId() + "']/transaction[@tranDataId='"
	// + currentRunningTransaction.getTranDataId() + "']/../../*";
	expr = xpath.compile(searchExpr.toString());
	result = expr.evaluate(document, XPathConstants.NODESET);
	nodes = (NodeList) result;
	goToGrandParent(paramDTO, newParamDTO, nodes);
	ussdSessionMgmt.clean();
    }

    private Node getPreviousSibling(Node node) {
	Node prevSibl = node.getPreviousSibling();
	if (prevSibl == null) {
	    return null;
	} else {
	    while (StringUtils.equalsIgnoreCase(USSDConstants.NEGATE_UI_RENDERING, prevSibl.getAttributes().getNamedItem("tranDataOpCode")
		    .getNodeValue())) {
		return getPreviousSibling(prevSibl);
	    }
	}
	return prevSibl;
    }

    public UssdMenuBuilder getUssdMenuBuilder() {
	return ussdMenuBuilder;
    }

    public void setUssdMenuBuilder(UssdMenuBuilder ussdMenuBuilder) {
	this.ussdMenuBuilder = ussdMenuBuilder;
    }

    public CurrentRunningTransaction getNextTransactionForByePass(USSDSessionManagement ussdSessionMgmt, String serviceName) {
	Transaction userTransactionDetails = ussdSessionMgmt.getUserTransactionDetails();
	String businessId = ussdSessionMgmt.getBusinessId();
	String countryCode = ussdSessionMgmt.getCountryCode();
	CurrentRunningTransaction currentRunningTransaction = null;
	StringBuilder searchExpr = null;
	XPathFactory factory = XPathFactory.newInstance();
	String tranDataId = null;
	XPath xpath = factory.newXPath();
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug(" Entering into  method getNextTransactionForByePass");
	}

	try {
	    CurrentRunningTransaction currentRunningTransaction2 = userTransactionDetails.getCurrentRunningTransaction();
	    if (currentRunningTransaction2.getTranDataId() == null) {
		currentRunningTransaction2.setServiceName(serviceName);
		// get the first child
		// searchExpr = "//node[@id='" +
		// currentRunningTransaction2.getTranNodeId() +
		// "']/transaction[1]";
		int firstScreenSequenceNumber = ussdMenuBuilder.getFirstScreen(serviceName, ussdSessionMgmt.getUserProfile().getCountryCode(),
			ussdSessionMgmt.getUserProfile().getBusinessId());

		if (firstScreenSequenceNumber <= 0) {
		    firstScreenSequenceNumber = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ONE.getSequenceNo();
		}
		tranDataId = USSDUtils.getNextTransactionStep(serviceName, firstScreenSequenceNumber);
		currentRunningTransaction2.setNextScreenSequenceNumber(firstScreenSequenceNumber);
	    } else {
		int nextScreenSequenceNumber = currentRunningTransaction2.getNextScreenSequenceNumber();
		tranDataId = USSDUtils.getNextTransactionStep(currentRunningTransaction2.getServiceName(), nextScreenSequenceNumber);
		currentRunningTransaction2.setNextScreenSequenceNumber(nextScreenSequenceNumber);
	    }
	    searchExpr = new StringBuilder("//tx[@tranId='").append(serviceName);
	    searchExpr.append("']/transaction[@tranDataId=\"" + tranDataId + "\"]");

	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Search : " + searchExpr);
	    }
	    // searchExpr = new StringBuilder("//tx[@tranId='" + serviceName + "']").append("']/transaction[@tranDataId='" + tranDataId + "']");
	    XPathExpression expr = xpath.compile(searchExpr.toString());



	    boolean customizedFlag = isCustomized(ussdSessionMgmt, serviceName);

	    Document document = null;
	    if (customizedFlag) {
		document = ussdMenuBuilder.getServiceTreeDocMap().get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	    } else {
		document = ussdMenuBuilder.getTxDocument();
	    }
	    Object result = expr.evaluate(document, XPathConstants.NODESET);

	    NodeList nodes = (NodeList) result;

	    // nodes.getLength() will always be one since only one transaction
	    // data node will be returned
	    for (int i = 0; i < nodes.getLength(); i++) {
		Node node = nodes.item(i);
		currentRunningTransaction = buildTransactionDataItem(node);
		currentRunningTransaction.setTranId(userTransactionDetails.getCurrentRunningTransaction().getTranId());
		currentRunningTransaction.setTranNodeId(userTransactionDetails.getCurrentRunningTransaction().getTranNodeId());
		currentRunningTransaction.setServiceName(userTransactionDetails.getCurrentRunningTransaction().getServiceName());
		currentRunningTransaction.setNextScreenSequenceNumber(currentRunningTransaction2.getNextScreenSequenceNumber());
		userTransactionDetails.setCurrentRunningTransaction(currentRunningTransaction);
	    }
	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred while searching the next transaction", e);
	    LOGGER.error("Exception ocurred while searching the next transaction", e);
	}
	if (currentRunningTransaction == null) {

	    userTransactionDetails.setCurrentRunningTransaction(null);
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug(" End of method getNextTransactionForByePass");
	}
	return currentRunningTransaction;
    }

    private boolean isCustomized(USSDSessionManagement ussdSessionMgmt, String serviceName) {
	boolean retVal = false;
	String businessId = ussdSessionMgmt.getBusinessId();
	String countryCode = ussdSessionMgmt.getCountryCode();
	StringBuilder searchExpr = null;
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Checking if the service is customized...");
	}
	try {
	    searchExpr = new StringBuilder("//customizedServices/tx[@tranId='").append(serviceName).append("']");
	    XPathExpression expr = xpath.compile(searchExpr.toString());

	    final Document svcDocument = ussdMenuBuilder.getServiceTreeDocMap().get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));

	    Object result = expr.evaluate(svcDocument, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    if (nodes != null && nodes.getLength() > 0) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Its a customized service...");
		}
		retVal = true;
	    }
	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred while searching the service in the servicemapping tree", e);
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Its not a customized service...");
	}
	return retVal;
    }

    public UssdDecisionParserParamDTO performBackOperationForByePassTx(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt)
	    throws USSDBlockingException {
	UssdDecisionParserParamDTO newParamDTO = new UssdDecisionParserParamDTO();
	String serviceName = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName();
	paramDTO.setServiceName(serviceName);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving previous screen within a transaction as per the user's Back action");
	}
	Stack<Integer> screenSequenceStack = ussdSessionMgmt.getUserTransactionDetails().getScreenSequenceStack();
	Integer sequenceNo = null;
	Integer previousSeqNo = null;
	String businessId = ussdSessionMgmt.getBusinessId();
	String countryCode = ussdSessionMgmt.getCountryCode();

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	Document document = null;
	boolean customizedFlag = isCustomized(ussdSessionMgmt, paramDTO.getServiceName());
	if (customizedFlag) {
	    document = ussdMenuBuilder.getServiceTreeDocMap().get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	} else {
	    document = ussdMenuBuilder.getTxDocument();
	}
	XPathExpression expr = null;
	Object result = null;
	NodeList nodes = null;
	try {
	    do {
		if (screenSequenceStack == null || screenSequenceStack.empty()) {
		    break;
		} else {
		    sequenceNo = screenSequenceStack.pop();
		    if (screenSequenceStack.empty()) {
			break;
		    } else {
			if (nodes != null
				&& USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode")
					.getNodeValue())) {
			    previousSeqNo = sequenceNo;
			} else {
			    previousSeqNo = screenSequenceStack.pop();
			}
			String tranDataId = USSDUtils.getNextTransactionStep(paramDTO.getServiceName(), previousSeqNo);
			String searchExpr = "//tx[@tranId='" + paramDTO.getServiceName() + "']/transaction[@tranDataId='" + tranDataId + "']";
			expr = xpath.compile(searchExpr);
			result = expr.evaluate(document, XPathConstants.NODESET);
			nodes = (NodeList) result;
		    }
		}
	    } while (USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode").getNodeValue()));

	    if (screenSequenceStack == null || screenSequenceStack.empty()) {
		CurrentRunningTransaction currentRunningTransaction = new CurrentRunningTransaction();
		// currentRunningTransaction.setTranId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId());
		// currentRunningTransaction.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());

		Transaction userTransactionDetails = new Transaction();
		userTransactionDetails.setCurrentRunningTransaction(currentRunningTransaction);

		ussdSessionMgmt.setTransactionFlag(true);
		ussdSessionMgmt.setUserTransactionDetails(userTransactionDetails);

		newParamDTO.setServiceName(serviceName);
		newParamDTO.setByPassRequest(true);
		newParamDTO.setUserInput(null);
		newParamDTO.setUserTransactionDetails(userTransactionDetails);

		// Added to resolve the issue: If wrong pin entered, wrong message of Authentication Failed coming.
		newParamDTO.setBusinessId(businessId);
		newParamDTO.setCountryCode(countryCode);

		return newParamDTO;
	    } else {
		Integer p2prevSeq = screenSequenceStack.peek();
		String tranDataId = USSDUtils.getNextTransactionStep(paramDTO.getServiceName(), p2prevSeq);
		String searchExpr = "//tx[@tranId='" + paramDTO.getServiceName() + "']/transaction[@tranDataId='" + tranDataId + "']";
		expr = xpath.compile(searchExpr);
		result = expr.evaluate(document, XPathConstants.NODESET);
		nodes = (NodeList) result;
	    }

	    if (nodes.item(0) != null) {
		CurrentRunningTransaction crTx = buildTransactionDataItem(nodes.item(0));
		crTx.setNextScreenSequenceNumber(previousSeqNo);
		crTx.setServiceName(serviceName);
		// crTx.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());
		String tranParamName = USSDInputParamsEnum.getUssdParamForTran(crTx.getTranDataId());
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		String userInput = userInputMap.get(tranParamName);
		newParamDTO.setUserInput(userInput);
		newParamDTO.setByPassRequest(true);
		newParamDTO.setServiceName(serviceName);
		newParamDTO.setCountryCode(ussdSessionMgmt.getCountryCode());
		newParamDTO.setBusinessId(ussdSessionMgmt.getBusinessId());
		ussdSessionMgmt.getUserTransactionDetails().setCurrentRunningTransaction(crTx);
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while performing back action for transaction", e);
	    throw new USSDBlockingException(USSDBlockingExceptions.XPATH_ERROR.getErrorCode(), e.getMessage());
	}
	return newParamDTO;
    }

    public MenuItem getMainMenuNode(USSDSessionManagement ussdSessionMgmt) {
	MenuItem menuItem = null;
	String businessId = ussdSessionMgmt.getBusinessId();
	String countryCode = ussdSessionMgmt.getCountryCode();
	StringBuilder searchExpr = null;
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Checking if the service is customized...");
	}
	try {
	    searchExpr = new StringBuilder("//node[@MainMenu=\"true\"]");
	    XPathExpression expr = xpath.compile(searchExpr.toString());

	    final Document menuDocument = ussdMenuBuilder.getDomTreeMap().get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));

	    Object result = expr.evaluate(menuDocument, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    if (nodes != null && nodes.getLength() > 0) {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Its a customized service...");
		}
		Node node = nodes.item(0);
		menuItem = buildMenuItem(node);
	    }
	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred while searching the service in the servicemapping tree", e);
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Its not a customized service...");
	}
	return menuItem;
    }

    public String getServiceName(UssdDecisionParserParamDTO paramDTO, String tranId) {
	String businessId = paramDTO.getBusinessId();
	String countryCode = paramDTO.getCountryCode();
	String serviceName = null;
	StringBuilder searchExpr = null;
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Look for the service name from the transaction.xml");
	}
	try {
	    searchExpr = new StringBuilder("//srv[@tranId=\"" + tranId + "\"]");
	    XPathExpression expr = xpath.compile(searchExpr.toString());

	    final Document srvDocument = ussdMenuBuilder.getServiceTreeDocMap().get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));

	    Object result = expr.evaluate(srvDocument, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    if (nodes != null && nodes.getLength() > 0) {
		Node node = nodes.item(0);
		serviceName = node.getAttributes().getNamedItem("importTran").getNodeValue();
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Service name found is : " + serviceName);
		}
	    }
	} catch (Exception e) {
	    LOGGER.fatal("Exception ocurred while searching the service", e);
	}

	return serviceName;
    }

    public UssdDecisionParserParamDTO performBackOperationForErrorScreen(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt)
	    throws USSDBlockingException {
	UssdDecisionParserParamDTO newParamDTO = new UssdDecisionParserParamDTO();
	String serviceName = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName();
	paramDTO.setServiceName(serviceName);
	Integer sequenceNo = null;
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving previous screen within a transaction as per the user's Back action");
	}
	Stack<Integer> screenSequenceStack = ussdSessionMgmt.getUserTransactionDetails().getScreenSequenceStack();

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	Map<String, Document> domTreeMap = ussdMenuBuilder.getDomTreeMap();
	Document document = domTreeMap.get((paramDTO.getBusinessId() + USSDConstants.SEPERATOR_UNDESCORE + paramDTO.getCountryCode()));
	XPathExpression expr = null;
	Object result = null;
	NodeList nodes = null;
	try {
	    if (screenSequenceStack == null || screenSequenceStack.empty()) {
		goToParentOnBack(paramDTO, ussdSessionMgmt, newParamDTO, xpath, document);
		ussdSessionMgmt.clean();
		return newParamDTO;
	    } else {
		Integer sequenceStackTop = screenSequenceStack.peek();
		nodes = getTreeNodeForSequence(paramDTO, ussdSessionMgmt, xpath, document, sequenceStackTop);
		while (nodes != null
			&& USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode")
				.getNodeValue())) {
		    sequenceStackTop = screenSequenceStack.pop();
		    if (screenSequenceStack == null || screenSequenceStack.empty()) {
			goToParentOnBack(paramDTO, ussdSessionMgmt, newParamDTO, xpath, document);
			ussdSessionMgmt.clean();
			return newParamDTO;
		    }
		    nodes = getTreeNodeForSequence(paramDTO, ussdSessionMgmt, xpath, document, sequenceStackTop);
		}
	    }

	    do {
		if (screenSequenceStack == null || screenSequenceStack.empty()) {
		    goToParentOnBack(paramDTO, ussdSessionMgmt, newParamDTO, xpath, document);
		    ussdSessionMgmt.clean();
		    return newParamDTO;
		} else {
		    sequenceNo = screenSequenceStack.pop();
		    if (screenSequenceStack.empty()) {
			break;
		    } else {
			Integer prevSeqNo = screenSequenceStack.peek();
			nodes = getTreeNodeForSequence(paramDTO, ussdSessionMgmt, xpath, document, prevSeqNo);
		    }
		}
	    } while (USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode").getNodeValue()));
	    if (screenSequenceStack.empty()) {
		CurrentRunningTransaction currentRunningTransaction = new CurrentRunningTransaction();
		currentRunningTransaction.setTranId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId());
		currentRunningTransaction.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());
		Transaction userTransactionDetails = new Transaction();
		userTransactionDetails.setCurrentRunningTransaction(currentRunningTransaction);
		ussdSessionMgmt.setTransactionFlag(true);
		ussdSessionMgmt.setUserTransactionDetails(userTransactionDetails);
		newParamDTO.setServiceName(serviceName);
		newParamDTO.setUserInput(null);
		newParamDTO.setCountryCode(ussdSessionMgmt.getCountryCode());
		newParamDTO.setBusinessId(ussdSessionMgmt.getBusinessId());
		newParamDTO.setUserTransactionDetails(userTransactionDetails);
		return newParamDTO;
	    } else {
		if (nodes.item(0) != null) {
		    CurrentRunningTransaction crTx = buildTransactionDataItem(nodes.item(0));
		    crTx.setNextScreenSequenceNumber(sequenceNo);
		    crTx.setServiceName(serviceName);
		    crTx.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());
		    String tranParamName = USSDInputParamsEnum.getUssdParamForTran(crTx.getTranDataId());
		    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		    String userInput = userInputMap.get(tranParamName);
		    newParamDTO.setUserInput(userInput);
		    newParamDTO.setCountryCode(ussdSessionMgmt.getCountryCode());
		    newParamDTO.setBusinessId(ussdSessionMgmt.getBusinessId());
		    ussdSessionMgmt.getUserTransactionDetails().setCurrentRunningTransaction(crTx);
		}
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while performing back action for transaction", e);
	    throw new USSDBlockingException(USSDBlockingExceptions.XPATH_ERROR.getErrorCode(), e.getMessage());
	}
	return newParamDTO;
    }

    private NodeList getTreeNodeForSequence(UssdDecisionParserParamDTO paramDTO, USSDSessionManagement ussdSessionMgmt, XPath xpath,
	    Document document, Integer sequenceStackTop) throws XPathExpressionException {
	XPathExpression expr;
	Object result;
	NodeList nodes;
	String tranDataId = USSDUtils.getNextTransactionStep(paramDTO.getServiceName(), sequenceStackTop);
	String searchExpr = "//node[@tranId='" + ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId()
		+ "']/transaction[@tranDataId='" + tranDataId + "']";
	expr = xpath.compile(searchExpr);
	result = expr.evaluate(document, XPathConstants.NODESET);
	nodes = (NodeList) result;
	return nodes;
    }

    public UssdDecisionParserParamDTO performBackOperationForByePassErrorScreen(UssdDecisionParserParamDTO paramDTO,
	    USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	UssdDecisionParserParamDTO newParamDTO = new UssdDecisionParserParamDTO();
	String serviceName = ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getServiceName();
	paramDTO.setServiceName(serviceName);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Retrieving previous screen within a transaction as per the user's Back action");
	}
	Stack<Integer> screenSequenceStack = ussdSessionMgmt.getUserTransactionDetails().getScreenSequenceStack();
	Integer sequenceNo = null;
	Integer previousSeqNo = null;
	String businessId = ussdSessionMgmt.getBusinessId();
	String countryCode = ussdSessionMgmt.getCountryCode();

	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	Document document = null;
	boolean customizedFlag = isCustomized(ussdSessionMgmt, paramDTO.getServiceName());
	if (customizedFlag) {
	    document = ussdMenuBuilder.getServiceTreeDocMap().get((businessId + USSDConstants.SEPERATOR_UNDESCORE + countryCode));
	} else {
	    document = ussdMenuBuilder.getTxDocument();
	}
	XPathExpression expr = null;
	Object result = null;
	NodeList nodes = null;
	try {
	    do {
		if (screenSequenceStack == null || screenSequenceStack.empty()) {
		    break;
		} else {
		    if (!(nodes != null && USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem(
			    "tranDataOpCode").getNodeValue()))) {
			sequenceNo = screenSequenceStack.pop();
		    }
		    if (screenSequenceStack.empty()) {
			break;
		    } else {
			// if (nodes != null
			// && USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode")
			// .getNodeValue())) {
			previousSeqNo = sequenceNo;
			// } else {
			// previousSeqNo = screenSequenceStack.pop();
			// }
			String tranDataId = USSDUtils.getNextTransactionStep(paramDTO.getServiceName(), previousSeqNo);
			String searchExpr = "//tx[@tranId='" + paramDTO.getServiceName() + "']/transaction[@tranDataId='" + tranDataId + "']";
			expr = xpath.compile(searchExpr);
			result = expr.evaluate(document, XPathConstants.NODESET);
			nodes = (NodeList) result;
		    }
		}
	    } while (USSDConstants.NEGATE_UI_RENDERING.equalsIgnoreCase(nodes.item(0).getAttributes().getNamedItem("tranDataOpCode").getNodeValue()));

	    if (screenSequenceStack == null || screenSequenceStack.empty()) {
		CurrentRunningTransaction currentRunningTransaction = new CurrentRunningTransaction();
		// currentRunningTransaction.setTranId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId());
		// currentRunningTransaction.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());

		Transaction userTransactionDetails = new Transaction();
		userTransactionDetails.setCurrentRunningTransaction(currentRunningTransaction);

		ussdSessionMgmt.setTransactionFlag(true);
		ussdSessionMgmt.setUserTransactionDetails(userTransactionDetails);

		newParamDTO.setServiceName(serviceName);
		newParamDTO.setByPassRequest(true);
		newParamDTO.setUserInput(null);
		newParamDTO.setUserTransactionDetails(userTransactionDetails);

		// Added to resolve the issue of back operation
		newParamDTO.setBusinessId(businessId);
		newParamDTO.setCountryCode(countryCode);

		return newParamDTO;
	    } else {
		Integer p2prevSeq = screenSequenceStack.peek();
		String tranDataId = USSDUtils.getNextTransactionStep(paramDTO.getServiceName(), p2prevSeq);
		String searchExpr = "//tx[@tranId='" + paramDTO.getServiceName() + "']/transaction[@tranDataId='" + tranDataId + "']";
		expr = xpath.compile(searchExpr);
		result = expr.evaluate(document, XPathConstants.NODESET);
		nodes = (NodeList) result;
	    }

	    if (nodes.item(0) != null) {
		CurrentRunningTransaction crTx = buildTransactionDataItem(nodes.item(0));
		crTx.setNextScreenSequenceNumber(previousSeqNo);
		crTx.setServiceName(serviceName);
		// crTx.setTranNodeId(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());
		String tranParamName = USSDInputParamsEnum.getUssdParamForTran(crTx.getTranDataId());
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		String userInput = userInputMap.get(tranParamName);
		newParamDTO.setUserInput(userInput);
		newParamDTO.setByPassRequest(true);
		newParamDTO.setServiceName(serviceName);
		newParamDTO.setCountryCode(ussdSessionMgmt.getCountryCode());
		newParamDTO.setBusinessId(ussdSessionMgmt.getBusinessId());
		ussdSessionMgmt.getUserTransactionDetails().setCurrentRunningTransaction(crTx);
	    }
	} catch (XPathExpressionException e) {
	    LOGGER.fatal("Exception ocurred while performing back action for transaction", e);
	    throw new USSDBlockingException(USSDBlockingExceptions.XPATH_ERROR.getErrorCode(), e.getMessage());
	}
	return newParamDTO;
    }

    public NavigationOptionsDTO getNavigationOptions(String businessId, String countryCode) {
	return ussdMenuBuilder.getNavigationOptions(businessId, countryCode);
    }
}