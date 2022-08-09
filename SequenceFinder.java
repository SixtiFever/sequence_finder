import java.io.*;

// a program that searches an external fil for a sequence of characters that is input via the console
class SequenceFinder {
	public static void main(String[] args){

		Finder f = new Finder("text.txt");
		char ch; // to iterate file
		String seq; // to store user query

		// read from console
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
			System.out.print("Enter query: ");
			seq = reader.readLine();
			f.findSequence(seq, f.fileName);
						
		} catch (IOException e){
			System.out.println(e);
		}	
	}
}

// creates instances of finder objects, dedicated to finding sequences in a specified file
class Finder {

	String fileName;

	Finder(String fname){
		this.fileName = fname;
		checkFileExists(fname);
	}


	static void findSequence(String sequence, String fileName){
		int i = 0;
		char chFromArg = sequence.charAt(i);
		char chFromFile;

		try(FileInputStream fin = new FileInputStream(fileName); RandomAccessFile raf = new RandomAccessFile(fileName, "r")){

			do {
				chFromFile = (char) fin.read();

				if ( chFromFile == chFromArg ){
					int k;
					char localchFromFile;
					for( int j = 1; j < sequence.length(); j++ ){
						k = j;  // assign j to k to avoid double incrementation from both loop argument and sequence.charAt()
						chFromArg = sequence.charAt(k++);
						localchFromFile = (char) fin.read();
						System.out.println(chFromArg + " " + localchFromFile);
						if( localchFromFile == chFromArg ){
							if( j == sequence.length() -1 ){
								System.out.println("Sequence found!");
								return;
							}
							continue;
						} else {
							System.out.println("Mistmatch: " + chFromArg + localchFromFile);
							break;
						}
					}
				chFromArg = sequence.charAt(0);
				}
				
			} while( chFromFile != (char) -1);

		} catch (IOException e){
			System.out.println(e);
		}

		
	}



	// method to ensure that the file can be found
	static boolean checkFileExists(String fname){
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(fname);
		} catch (FileNotFoundException e){
			System.out.println(e);
		} finally {
			try {
				fin.close();
				System.out.println("File Input Stream has been closed.");
				return true;
			} catch(IOException e){
				System.out.println(e);
			}
		}
		return false;
	}

}