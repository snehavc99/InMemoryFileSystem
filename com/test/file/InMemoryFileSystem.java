package com.test.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sneha Chawathe
 * @class InMemoryFileSystem A class responsible to do basic inmemory file
 *        actions
 * 
 */
/**
 * Create a new folder - Takes a parameter of absolute folder path
 * 
 * Create a new file - Take a parameter of absolute file path
 * 
 * Add content to a file - Take 2 parameters: Content to append to a file;
 * Absolute path to a file
 * 
 * Copy files - Takes 2 parameters: Absolute path to a source file; Absolute
 * path to a destination file (NOTE: If destination file exists, it will be
 * overwritten)
 * 
 * Display file contents - Takes absolute path to a file as an input; Prints out
 * file contents as an output
 * 
 * List folder contents - Takes absolute path to a folder as an input; Prints
 * out folder contents as an output
 * 
 * Search for a file by name - Takes name of a file to find; Prints out list of
 * absolute paths to files with matching names
 * 
 * Search for a file by name - Takes 2 parameters: Absolute path to a starting
 * folder and file name; Outputs list of absolute paths to files with matching
 * names
 * 
 * (Bonus) Copy folders - Takes 2 parameters: Absolute path to source folder,
 * Absolute path to destination folder
 */

public class InMemoryFileSystem {

	private String fileNameToSearch;

	private List<String> result = new ArrayList<String>();

	public String getFileNameToSearch() {

		return fileNameToSearch;

	}

	public void setFileNameToSearch(String fileNameToSearch) {

		this.fileNameToSearch = fileNameToSearch;

	}

	public List<String> getResult() {

		return result;

	}

	public static final String ABSOLUTE_PATH = "C:/Users/sneha/Desktop/comcast";

	public static void main(String[] args) throws IOException {

		/* 1.Create a new folder.......... */

		File f = new File(ABSOLUTE_PATH);
		f.mkdirs();

		try {
			/* 3.Add content to a file.............. */

			String content = "This is the content to write into file";

			/* 2.Create a new file.......... */

			File file = new File(ABSOLUTE_PATH + "/cfile.txt");
			File dfile = new File(ABSOLUTE_PATH + "/dfile.txt");
			dfile.createNewFile();
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();

		}

		/* 4.Copy files *//* 5.Display file contents.............. */

		FileInputStream fs = new FileInputStream(ABSOLUTE_PATH + "/cfile.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		LinkedList<String> ll = new LinkedList<String>();
		String sline;
		while ((sline = br.readLine()) != null) {
			ll.add(sline);

		}
		FileOutputStream fout = new FileOutputStream(ABSOLUTE_PATH
				+ "/d2file.txt");
		BufferedWriter brout = new BufferedWriter(new OutputStreamWriter(fout));
		int i;
		int len = ll.size();
		for (i = 0; i <= len - 1; i++) {

			System.out.println(ll.get(i));

			brout.write(ll.get(i));
			brout.newLine();
			// brout.write("\n");
		}
		brout.close();
		br.close();

		/* 6.List folder contents.............. */

		File f1 = new File(ABSOLUTE_PATH); // current
											// directory
		File[] files = f1.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.print("comcast");
			} else {
				System.out.print("     file:");
			}
			System.out.println(file.getCanonicalPath());
		}

		/* 7.Search for a file by name............... */

		InMemoryFileSystem fileSearch = new InMemoryFileSystem();

		fileSearch.searchDirectory(new File(ABSOLUTE_PATH), "cfile.txt");

		int count = fileSearch.getResult().size();

		if (count == 0) {

			System.out.println("\nNo result found!");

		} else {

			System.out.println("\nFound " + count + " result!\n");

			for (String matched : fileSearch.getResult()) {
			}
		}

	}

	public void searchDirectory(File directory, String fileNameToSearch) {

		setFileNameToSearch(fileNameToSearch);

		if (directory.isDirectory()) {

			search(directory);

		} else {

			System.out.println(directory.getAbsoluteFile()
					+ " is not a directory!");

		}
	}

	private void search(File file) {

		if (file.isDirectory()) {

			System.out.println("Searching directory ... "
					+ file.getAbsoluteFile());

			// do you have permission to read this directory?

			if (file.canRead()) {

				for (File temp : file.listFiles()) {

					if (temp.isDirectory()) {

					} else {

						search(temp);

						if (getFileNameToSearch().equals(
								temp.getName().toLowerCase())) {

							result.add(temp.getAbsoluteFile().toString());

						}

					}

				}

			} else {

				System.out
						.println(file.getAbsoluteFile() + "Permission Denied");

			}
		}

		/* 8.Search for a file by name.................. */

		File dir = new File(ABSOLUTE_PATH);

		System.out.println("Directory is : " + dir);

		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {

				return name.startsWith("H");
			}
		};

		String[] children = dir.list(filter);

		if (children == null) {

			System.out
					.println("Either dir does not exist or is not a directory");

		}

		else {

			System.out.println("Absolute path of matched file/s are: ");

			for (int i = 0; i < children.length; i++) {

				String filename = children[i];

				System.out.println(dir + "\\" + filename);

			}

		}

		/* 9.(Optional) Copy folders................. */

		File srcFolder = new File(ABSOLUTE_PATH);

		File destFolder = new File("C:\\New");

		if (!srcFolder.exists()) {

			System.out.println("Directory does not exist.");

			System.exit(0);

		} else {

			try {

				copyFolder(srcFolder, destFolder);

			} catch (IOException e) {

				e.printStackTrace();

				System.exit(0);

			}
		}

		System.out.println("Done");

	}

	public static void copyFolder(File src, File dest)

	throws IOException {

		if (src.isDirectory()) {

			if (!dest.exists()) {

				dest.mkdir();

				System.out.println("Directory copied from "

				+ src + "  to " + dest);

			}

			String files[] = src.list();

			for (String file : files) {

				File srcFile = new File(src, file);

				File destFile = new File(dest, file);

				copyFolder(srcFile, destFile);

			}

		} else {

			InputStream in = new FileInputStream(src);

			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;

			while ((length = in.read(buffer)) > 0) {

				out.write(buffer, 0, length);

			}

			in.close();

			out.close();

			System.out.println("File copied from " + src + " to " + dest);

		}

	}
}
