import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
	public static void main(String[] args) {
		// int n = Integer.parseInt(args[0]);
		int n = 6;
		RandomizedQueue<String> rdmQ = new RandomizedQueue<String>();
		for (int i = 0; i < n; i++) {
			String s_in = StdIn.readString();
			rdmQ.enqueue(s_in);
		}
		for (String s : rdmQ) {
			StdOut.print(s+",");
		}
	}
}
