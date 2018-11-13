package com.barclays.bmg.service.sessionactivity.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;
import com.barclays.bmg.service.sessionactivity.dto.SessionSummaryDTO;

public class SessionActivityUtility {

	/**
	 * Convert transaction details from List to XML.
	 *
	 * @param details
	 * @return
	 */
	public static String detail2Xml(List<KeyValuePairBean> details) {
		StringWriter sw = new StringWriter();
		Element docElement = new Element("details");
		if (details != null) {
			for (KeyValuePairBean kp : details) {
				Element element = new Element("detail");
				element.setAttribute("type", kp.getKey());
				if(kp.getValue()!=null){
					element.addContent(kp.getValue().toString());
				}
				docElement.addContent(element);
			}
		}
		Document doc = new Document(docElement);
		XMLOutputter outputter = new XMLOutputter();
		try {
			outputter.output(doc, sw);
		} catch (IOException ioe) {
			throw new RuntimeException("CONVERT_XML_ERROR", ioe);
		}
		return sw.toString();
	}

	/**
	 * Convert transaction details from XML string.
	 *
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<KeyValuePairBean> xml2Detail(String xml) {
		List<KeyValuePairBean> res = new ArrayList<KeyValuePairBean>(10);
		if (xml == null) {
			return res;
		}
		// StringReader sr = new StringReader(xml);
		DOMBuilder builder = new DOMBuilder();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = builder.build(db.parse(new ByteArrayInputStream(xml
					.getBytes())));
			Element root = doc.getRootElement();
			List<Element> details = root.getChildren("detail");
			if (details != null) {
				for (Element detail : details) {
					String key = detail.getAttributeValue("type", "");
					String value = detail.getText();
					KeyValuePairBean kp = new KeyValuePairBean(key, value);
					res.add(kp);
				}
			}
		} catch (ParserConfigurationException e) {
			throw new BMBDataAccessException("XML007", e.getMessage());
		} catch (IOException e) {
			throw new BMBDataAccessException("XML007", e.getMessage());
		} catch (SAXException e) {
			throw new BMBDataAccessException("XML007", e.getMessage());
		}
		return res;
	}

	/**
	 * Recreates the transaction list from the xml string
	 * @param sessionSummaryDTO
	 */
	public static void formatSessionSummary(SessionSummaryDTO sessionSummaryDTO) {
		List<SessionActivityDTO> sessionActivityList = sessionSummaryDTO
				.getSessionActivityList();
		for (SessionActivityDTO sessionActivity : sessionActivityList) {
			String details = sessionActivity.getDetails();
			if (details == null) {
				continue;
			}

			List<KeyValuePairBean> keyVList = xml2Detail(details);
			sessionActivity.setTxnDetails(keyVList);
		}
	}

}
