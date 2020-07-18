package hello;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("Extracted");
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		for(String dir:directories) {
			new File("Extracted/" + dir+ "/" + "Full Document.txt").delete();
		}
		
		for(String dir:directories) {
			File d = new File("Extracted/" + dir);
			List<String> files = Arrays.asList(d.list(new FilenameFilter() {
				  @Override
				  public boolean accept(File current, String name) {
				    return new File(current, name).isFile();
				  }
				}));			

		    Collections.sort(files, new Comparator<String>() {
		        public int compare(String o1, String o2) {
		        	return extractInt(o1.substring(0,2)) - extractInt(o2.substring(0,2));
		        }

		        int extractInt(String s) {
		            String num = s.replaceAll("\\D", "");
		            // return 0 if no digits found
		            return num.isEmpty() ? 0 : Integer.parseInt(num);
		        }
		    });

		    //files.forEach(System.out::println);
		    String result="";
			System.err.println(dir);
			for(String f:files) {
				//result += f.replace(" - lang_en.srt", "") + "\n";
				result += "<h1>" + f.replace(" - lang_en.srt", "").replace("lang_en_vs1.srt", "") + "</h1>";
				System.out.println();
				StringBuilder contentBuilder = new StringBuilder();
				 
			    File srtFile = new File("Extracted/" + dir+ "/" + f);
			    try (Scanner scanner = new Scanner(srtFile)) {
			        while(scanner.hasNextLine()) {
			        	scanner.nextLine();
			        	if(scanner.hasNext())scanner.nextLine();
			        	String line;
	        			 while (scanner.hasNext() && !(line = scanner.nextLine()).equals("")){
	        				 result+= " " + line;
	        			 }	        					 
			        }
			    }	
			    result+="\n\n";
			}
			String chaperName = dir.replace("+", " ").replace("Subtitles", "");			
			System.out.println("Full Doc:" + chaperName + "\n" + result);			
			System.out.println("#########################");
			result = "<h1>" + chaperName + "</h1></br>" + result;
			//Write to txt
			try {
		      FileWriter myWriter = new FileWriter("Extracted/" + dir+ "/" + "Full Document.txt", false);
		      myWriter.write(result);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		}
		
		
	}
	

}
