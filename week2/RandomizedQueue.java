import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {

	// 实现随机获取其中的参数，使用数组?使用数组，类似ResizingArrayStack，不过在remove的
	// 时候随机调换，末位元素与0到N-1元素中的随机一个
	private Item[] a = (Item[]) new Object[1];
	private int N = 0;

	public RandomizedQueue() {
		// construct an empty randomized queue
	}

	public boolean isEmpty() {
		// is the queue empty?
		return N == 0;
	}

	public int size() {
		// return the number of Ts on the queue
		return N;
	}

	public void enqueue(Item t) {
		// add the Item
		if (t == null) {
			throw new NullPointerException();
		}
		a[N] = t;
		N ++;
		if (N == a.length) {
			resize(a.length * 2);
		}
		
	}

	public Item dequeue() {
		// remove and return a random T
		if (N == 0) {
			throw new NullPointerException();
		}
		// r[0,N) ,例如 N = 3 ,[0,3)
		int r = StdRandom.uniform(N);
		Item temp = a[r];
		// 所有被移除元素后面的元素向前移动一位，
		for (int i = r; i < N - 1; i++) {
			a[i] = a[i + 1];
		}
		a[N-1] = null;
		N--;
		if (N > 0 && N == a.length / 4) {
			resize(a.length / 2);
		}
		return temp;
	}

	public Item sample() {
		// return (but do not remove) a random T
		if (N == 0) {
			throw new NullPointerException();
		}
		return a[StdRandom.uniform(N)];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over Ts in random order
		return new RandomizedIterator<Item>();
	}

	private void resize(int max) {
		Item[] new_a = (Item[]) new Object[max];
		for (int i = 0; i < N; i++) {
			new_a[i] = a[i];
		}
		a = new_a;
	}

	public static void main(String[] args) {
		// unit testing
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		queue.enqueue("----1----");
		queue.enqueue("----2----");
		queue.enqueue("----3----");
		queue.enqueue("----4----");
		queue.enqueue("----5----");
		queue.enqueue("----6----");
		System.out.println(queue.size());
		for (String str : queue) {
			System.out.println(str);
		}
	}

	@SuppressWarnings("hiding")
	private class RandomizedIterator<Item> implements Iterator<Item> {

		private Item[] p = (Item[]) a;
		private int pointer = N;

		@Override
		public boolean hasNext() {
			return pointer > 0;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}else{
				//pointer = N ,[0,N)
				int r = StdRandom.uniform(pointer);
				// 然后换位置
				Item temp = p[pointer-1];
				p[pointer-1] = p[r];
				p[r] = temp;
				return p[--pointer];
			}
			
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
}
