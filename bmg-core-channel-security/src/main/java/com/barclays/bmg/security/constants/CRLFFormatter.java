package com.barclays.bmg.security.constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CRLFFormatter {
    final static String regexPattern = "\\b(http?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static boolean isValidUrl(String text) {
	try {
	    Pattern patt = Pattern.compile(regexPattern);
	    Matcher matcher = patt.matcher(text);
	    return matcher.matches();
	} catch (RuntimeException e) {
	    return false;
	}

    }

    /*
     * public static boolean format(String userInput) { userInput = StringEscapeUtils.escapeHtml(userInput); // Add this to check the user input if
     * (Pattern.matches("[-a-zA-Z0-9+&@#/%=~_|]", userInput)) { // Unsanitized user input // logger.error("Invalid Input"); // return error return
     * true; } return false; }
     */

    /*
     * public static void main(String[] args) { System.out .println(CRLFFormatter.format(
     * "\r \n !¬!££$¬¬¬¬¬ only throws MalformedURLException if the port < 0 or it doesn't understand the protocol. Other than that anything goes. It won't catch: 1.2.3. 1.2.3.4.5 1.2,3.4.5: etc –  David Newcomb Jun 9 '11 at 15:12 "
     * ));
     * 
     * String text = "http://google.com"; System.out.println("" + CRLFFormatter.isValidUrl(text));
     */
    // System.out.println("" + CRLFFormatter.isValidUrl("ftp://cwe.mitre.org/data/definitions/501.html"));
    /* } */
}
