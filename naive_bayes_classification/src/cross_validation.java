import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class cross_validation {
	public void set(int k) throws IOException {
		int number = 0;
		
		for (String line : Files.readAllLines(Paths.get("data/zoo.txt"))) {
		    number++;
		}
		
		for(int i =0;i < k; i++) {
			
			int lower = 0, upper = 0;
			lower = number * i / k;
			upper = number * (i + 1) / k;
			
			String filename_train = "data/train" + i + ".txt";
			String filename_validation = "data/validation" + i + ".txt";
			PrintWriter train = new PrintWriter(filename_train, "UTF-8");
			PrintWriter validation = new PrintWriter(filename_validation, "UTF-8");
			
			int n = 0;
			for (String line : Files.readAllLines(Paths.get("data/zoo.txt"))) {
			    if(n >= lower && n < upper) validation.println(line);
			    else train.println(line);
			    
			    n++;
			}
			
			train.close();
			validation.close();
		}
	}
}
