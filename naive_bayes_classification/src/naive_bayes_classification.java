import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class naive_bayes_classification {
	static int data_size = 150;
	static int attri_num = 16;
	static int class_num = 7;
	static int n = 0;
	static int k = 10;
	static String[] name = new String[data_size];
	static int[][] attri_rec = new int[data_size][attri_num];
	static int[] class_rec = new int [data_size];
	
	static int[] attri_test = new int[attri_num];
	static int[] class_test = new int[class_num];
	static int[][] count = new int[class_num][attri_num];
	static int[] count_class_num = new int[class_num];
	static double[] naive_bayes = new double[class_num];
	
	public static void record_train_data(String filename_train) throws Exception {
		
		//formation
		for(int i = 0;i < data_size; i++) {
			name[i] = null;
			class_rec[i] = -1;
		}
		for(int i = 0;i < data_size; i++) {
			for(int j = 0;j < attri_num; j++) {
				attri_rec[i][j] = -1;
			}
		}
		n = 0;
		
		for (String line : Files.readAllLines(Paths.get(filename_train))) {
		    String[] parts = line.split(",");
		    
		    // transfer the string into int
		    name[n] = parts[0];
		    for(int i = 0;i < attri_num;i++) {
		    	attri_rec[n][i] = parts[i+1].charAt(0) - 48;
		    }
		    class_rec[n] = parts[17].charAt(0) - 48;
		    
		    // test input
		    //System.out.println(line);
		    //System.out.println(name[n] + "," + 
		    //		attri_rec[n][0] + "," + attri_rec[n][1] + "," + attri_rec[n][2] + "," + attri_rec[n][3] + "," + 
		    //		attri_rec[n][4] + "," + attri_rec[n][5] + "," + attri_rec[n][6] + "," + attri_rec[n][7] + "," + 
		    //		attri_rec[n][8] + "," + attri_rec[n][9] + "," + attri_rec[n][10] + "," + attri_rec[n][11] + "," + 
		    //		attri_rec[n][12] + "," + attri_rec[n][13] + "," + attri_rec[n][14] + "," + attri_rec[n][15] + "," + 
		    //		class_rec[n]);
		    
		    n++;
		}
	}
	
	public static double classification_validation_data(String filename_validation) throws IOException {
		double accuracy = 0.0;
		double count_correct = 0;
		double validation_number = 0;
		
		//formation
		for(int i = 0;i < attri_num; i++) {
			attri_test[i] = -1;
			for(int j = 0;j < class_num; j++) {
				 count[j][i] = -1;
			}
		}
		for(int i = 0;i < class_num; i++) {
			class_test[i] = -1;
			count_class_num[i] = -1;
			naive_bayes[i] = -1;
		}
		
		for (String line : Files.readAllLines(Paths.get(filename_validation))) {
			// transfer the string into int
			//System.out.println(line);
			
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
		    
		    // test output
		    //for(int i = 0;i < class_num; i++) {
		    //	for(int j = 0;j < attri_num; j++) {
		    //		System.out.print(count[i][j] + ",");
		    //	} System.out.println();
		    //	System.out.println(count_class_num[i]);
		    //}
		    
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
		    
		    // test output
		    //for(int i = 0;i < class_num; i++) {
		    //	System.out.println(naive_bayes[i]);
		    //}
		    
		    // find out the class
		    double max = 0;
		    int classification = -1;
		    for(int i = 0;i < class_num; i++) {
		    	if(naive_bayes[i] > max) {
		    		classification = i+1;
		    		max = naive_bayes[i];
		    	}
		    }
		    //System.out.println(parts[attri_num + 1] + "," + classification);
		    if((parts[attri_num + 1].charAt(0) - 48) == classification) count_correct++;
		    validation_number++;
		}
		
		//System.out.println(count_correct + "," + validation_number);
		accuracy = count_correct / validation_number;
		
		return accuracy;
	}
	
	public static void classification_test_data(String filename_test) throws IOException {
		
		//formation
		for(int i = 0;i < attri_num; i++) {
			attri_test[i] = -1;
			for(int j = 0;j < class_num; j++) {
				 count[j][i] = -1;
			}
		}
		for(int i = 0;i < class_num; i++) {
			class_test[i] = -1;
			count_class_num[i] = -1;
			naive_bayes[i] = -1;
		}
		
		for (String line : Files.readAllLines(Paths.get(filename_test))) {
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
		    
		    // test output
		    //for(int i = 0;i < class_num; i++) {
		    //	for(int j = 0;j < attri_num; j++) {
		    //		System.out.print(count[i][j] + ",");
		    //	} System.out.println();
		    //	System.out.println(count_class_num[i]);
		    //}
		    
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
		    
		    // test output
		    //for(int i = 0;i < class_num; i++) {
		    //	System.out.println(naive_bayes[i]);
		    //}
		    
		    // find out the class
		    double max = 0;
		    int classification = -1;
		    for(int i = 0;i < class_num; i++) {
		    	if(naive_bayes[i] > max) {
		    		classification = i+1;
		    		max = naive_bayes[i];
		    	}
		    }
		    System.out.print(parts[0] + " " + classification);
		    if(classification == 1) System.out.print(" mammalia 哺乳类\n");
		    else if(classification == 2) System.out.print(" bird 鸟类\n");
		    else if(classification == 3) System.out.print(" reptiles 爬行类\n");
		    else if(classification == 4) System.out.print(" fish 鱼类\n");
		    else if(classification == 5) System.out.print(" amphibians 两栖类\n");
		    else if(classification == 6) System.out.print(" insect 昆虫类\n");
		    else if(classification == 7) System.out.print(" arthropod 节肢类\n");
		    else if(classification == -1) System.out.print(" error\n");
		}
	}
	
	public static double use_validation() throws Exception {
		double sum_accuracy = 0.0;
		
		// set the train and validation data
		cross_validation CV = new cross_validation();
		CV.set(k);
		
		for(int i = 0;i < k; i++) {
			// record the train data
			String filename_train = "data/train" + i + ".txt";
			record_train_data(filename_train);
			
			//figure out the accuracy of each validation data
			String filename_validation = "data/validation" + i + ".txt";
			double accuracy = classification_validation_data(filename_validation);
			//System.out.println(accuracy);
			sum_accuracy += accuracy;
		}
		
		sum_accuracy /= k;
		return sum_accuracy;
	}
	
	public static void main(String args[]) throws Exception {
		System.out.println(use_validation());
		
		//String filename_train = "data/zoo.txt";
		//record_train_data(filename_train);
		
		// figure out the class of test data
		//String filename_test = "data/test.txt";
		//classification_test_data(filename_test);
	}
}