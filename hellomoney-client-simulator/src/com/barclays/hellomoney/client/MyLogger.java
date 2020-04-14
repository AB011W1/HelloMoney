package com.barclays.hellomoney.client;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.log4j.Logger;

public class MyLogger {
	public Logger logger;
	static   String fileName = "c:/softtag/logs.txt";

	public MyLogger(Logger logger) {
		this.logger = logger;

	}
	public static void main(String[] args) {
		log("Naresh");
	}
	public static void log(String log) {

		// The name of the file to open.

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName,
					true)));
			out.println(new Date() + "\n" + log);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		/*
		 * try { // Assume default encoding. FileWriter fileWriter = new
		 * FileWriter(fileName);
		 *
		 * // Always wrap FileWriter in BufferedWriter. BufferedWriter
		 * bufferedWriter = new BufferedWriter(fileWriter);
		 *
		 * // Note that write() does not automatically // append a newline
		 * character. bufferedWriter.write(log);
		 *
		 * // Always close files. bufferedWriter.close(); } catch(IOException
		 * ex) { System.out.println( "Error writing to file '" + fileName +
		 * "'"); // Or we could just do this: // ex.printStackTrace(); }
		 */
	}
}