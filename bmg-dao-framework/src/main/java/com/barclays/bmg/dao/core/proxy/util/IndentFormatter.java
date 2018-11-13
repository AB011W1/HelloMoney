package com.barclays.bmg.dao.core.proxy.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndentFormatter {
	 private static final Pattern ELEMENT = Pattern.compile("<[^<^>]+>|[^<^>]+");

	    private static final Pattern ELEMENT_START = Pattern.compile("<[^/][^<^>]+[^/]>");

	    private static final Pattern ELEMENT_END = Pattern.compile("</[^<^>]+>");

	    private static final Pattern ELEMENT_EMPTY = Pattern.compile("<[^<^>]+/>");

	    private static final Pattern ELEMENT_TEXT = Pattern.compile("[^<^>]+");

	    private static Map<Integer, String> indentPool = new HashMap<Integer, String>();

	    private static final String INDENT = "  ";

	    private static MaskingRule maskingRule = MaskingRuleImpl.getInstance();

	    public static String format(String xml) {
	        StringBuffer buffer = new StringBuffer();
	        try {

	            Matcher matcher = ELEMENT.matcher(xml);

	            int level = 0;
	            String prev = null;

	            boolean needMask = false;
	            MaskingMode maskingMode = MaskingMode.NONE;
	            while (matcher.find()) {
	                String g = matcher.group();
	                if (isStartElement(g)) {
	                    if (isStartElement(prev)) {
	                        level++;
	                    }
	                    if (prev != null) {
	                        buffer.append("\n");
	                    }
	                    maskingMode = maskingRule.checkMaskingMode(g);
	                    buffer.append(indent(level) + g);
	                } else if (isText(g)) {
	                	if (maskingMode != MaskingMode.NONE) {
	                        g = maskingRule.mask(g, maskingMode);
	                    }
	                    buffer.append(g);
	                } else if (isEmptyElement(g)) {
	                    if (isStartElement(prev)) {
	                        level++;
	                    }
	                    buffer.append("\n" + indent(level) + g);
	                } else if (isEndElement(g)) {
	                    if (isEndElement(prev) || isEmptyElement(prev)) {
	                        level--;
	                        buffer.append("\n" + indent(level));
	                    }
	                    buffer.append(g);
	                }
	                prev = g;
	            }
	        } catch (Throwable e) {
	            return xml;
	        }
	        return buffer.toString();
	    }

	    private static boolean isStartElement(String node) {
	        if (node == null) {
	            return false;
	        }
	        return ELEMENT_START.matcher(node).matches();
	    }

	    private static boolean isEndElement(String node) {
	        if (node == null) {
	            return false;
	        }
	        return ELEMENT_END.matcher(node).matches();
	    }

	    private static boolean isEmptyElement(String node) {
	        if (node == null) {
	            return false;
	        }
	        return ELEMENT_EMPTY.matcher(node).matches();
	    }

	    private static boolean isText(String node) {
	        if (node == null) {
	            return false;
	        }
	        return ELEMENT_TEXT.matcher(node).matches();
	    }

	    private static String indent(int level) {
	        if (level < 1) {
	            return "";
	        }
	        String indent = indentPool.get(level);
	        if (indent == null) {
	            StringBuffer buffer = new StringBuffer();
	            for (int i = 0; i < level; i++) {
	                buffer.append(INDENT);
	            }
	            indent = buffer.toString();
	            indentPool.put(level, indent);
	        }
	        return indent;
	    }
}
