package fr.rugeri.subtitleconverter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationEvent;
import com.apple.eawt.ApplicationListener;

public class Main {

	public static void main(String[] args) throws IOException {
	    Application.getApplication().addApplicationListener(new ApplicationListener() {
			public void handleReOpenApplication(ApplicationEvent e) {}
			public void handleQuit(ApplicationEvent e) {}
			public void handlePrintFile(ApplicationEvent e) {}
			public void handlePreferences(ApplicationEvent e) {}
			public void handleOpenApplication(ApplicationEvent e) {}
			public void handleAbout(ApplicationEvent e) {}
			public void handleOpenFile(ApplicationEvent e) {
				try {
					Path filePath = Paths.get(e.getFilename());
					convertAndRewriteFile(filePath);
				} catch (IOException exception) {}
			}
		});
	}

	private static void convertAndRewriteFile(Path filePath) throws IOException {
		byte[] bytes = Files.readAllBytes(filePath);
		
		String subtitle = new String(bytes, Charset.forName("ISO-8859-1"));
		if (isUTF8Encoded(subtitle)) {
			subtitle = new String(bytes, Charset.forName("UTF-8"));
		}
		
		subtitle = clean(subtitle);
		
		FileOutputStream fos = new FileOutputStream(filePath.toFile());
		fos.write(subtitle.getBytes(Charset.forName("ISO-8859-1")));
		fos.close();
	}

	private static boolean isUTF8Encoded(String subtitle) {
		return subtitle.contains("Ã"); // if someone has a better suggestion... :)
	}
	
	private static String clean(String subtitle) {
		StringBuilder sb = new StringBuilder();
		String lines[] = subtitle.split("\\r?\\n");
		for (String line : lines) {
			line = cleanLine(line);
			sb.append(line+"\n");
		}
		return sb.toString();
	}

	static String cleanLine(String line) {
		line = line.replaceAll("\\{\\\\pos\\([0-9]+,[0-9]+\\)\\}", ""); // removes pos
		line = line.replaceAll("<.*?>", "");					        // removes tags
		line = line.replaceAll("(^\\p{Blank}*|\\p{Blank}*$)", "");      // removes whitespaces
		return line;
	}
}
