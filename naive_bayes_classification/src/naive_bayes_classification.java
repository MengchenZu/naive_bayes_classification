import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class naive_bayes_classification {
	static int N = 150;
	static int attri_num = 16;
	static int class_num = 7;
	static String[] name = new String[N];
	static int[][] attri_rec = new int[N][attri_num];
	static int[] class_rec = new int [N];
	
	static int[] attri_test = new int[attri_num];
	static int[] class_test = new int[class_num];
	static int[][] count = new int[class_num][attri_num];
	static int[] count_class_num = new int[class_num];
	static double[] naive_bayes = new double[class_num];
	
	public static void main(String args[]) throws IOException {
		
		// record the train data
		int n = 0;
		for (String line : Files.readAllLines(Paths.get("data/zoo.txt"))) {
		    String[] parts = line.split(",");
		    
		    // transfer the string into int
		    name[n] = parts[0];
		    for(int i = 0;i < attri_num;i++) {
		    	attri_rec[n][i] = parts[i+1].charAt(0) - 48;
		    }
		    class_rec[n] = parts[17].charAt(0) - 48;
		    
		    /*
		    // test input
		    System.out.println(line);
		    System.out.println(name[n] + "," + 
		    		attri_rec[n][0] + "," + attri_rec[n][1] + "," + attri_rec[n][2] + "," + attri_rec[n][3] + "," + 
		    		attri_rec[n][4] + "," + attri_rec[n][5] + "," + attri_rec[n][6] + "," + attri_rec[n][7] + "," + 
		    		attri_rec[n][8] + "," + attri_rec[n][9] + "," + attri_rec[n][10] + "," + attri_rec[n][11] + "," + 
		    		attri_rec[n][12] + "," + attri_rec[n][13] + "," + attri_rec[n][14] + "," + attri_rec[n][15] + "," + 
		    		class_rec[n]);*/
		    
		    n++;
		}
		
		// figure out the class of test data
		for (String line : Files.readAllLines(Paths.get("data/test.txt"))) {
			// transfer the string into int
		    //System.out.println(line);
		    String[] parts = line.split(",");
		    for(int i = 0;i < attri_num;i++) {
		    	attri_test[i] = parts[i+1].charAt(0) - 48;
		    }
		    
		    // initialization
		    for(int i = 0;i < class_num; i++) {
		    	for(int j = 0;j < attri_num; j++) {
		    		count[i][j] = 0;
		    	}
		    }
		    
		    // count the element of naive bayes of attributes
		    for(int i = 0;i < n; i++) {
		    	for(int j = 0;j < attri_num; j++) {
		    		if(attri_rec[i][j] == attri_test[j]) count[class_rec[i]-1][j]++;
		    	}
		    }
		    
		    // count the denominator of naive bayes of attributes
		    for(int i = 0;i < n; i++) {
		    	count_class_num[class_rec[i]-1]++;
		    }
		    
		    /*
		    // test output
		    for(int i = 0;i < class_num; i++) {
		    	for(int j = 0;j < attri_num; j++) {
		    		System.out.print(count[i][j] + ",");
		    	} System.out.println();
		    	System.out.println(count_class_num[i]);
		    }
		    */
		    
		    // calculate the naive bayes
		    
		    // initialization
		    for(int i = 0;i < class_num; i++) {
		    	naive_bayes[i] = 1;
		    }
		    
		    for(int i = 0;i < class_num; i++) {
		    	for(int j = 0;j < attri_num; j++) {
		    		naive_bayes[i]= naive_bayes[i] * count[i][j] / count_class_num[i];
		    	}
		    }
		    
		    /*
		    // test output
		    for(int i = 0;i < class_num; i++) {
		    	System.out.println(naive_bayes[i]);
		    }
		    */
		    
		    // find out the class
		    double max = -1;
		    int classification = -1;
		    for(int i = 0;i < class_num; i++) {
		    	if(naive_bayes[i] > max) {
		    		classification = i+1;
		    		max = naive_bayes[i];
		    	}
		    }
		    System.out.println(classification);
		    
		}
		
	}
	
}