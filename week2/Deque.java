import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	// memory useage 16 + 8 * 2 + 8 + (4 + 4)+ 32 + ( 16 + 8 * 3 ) * N = 48N +
	// 80 bytes
	private Node<Item> first, last;
	private int N;

	@SuppressWarnings("hiding")
	private class Node<Item> {
		Item item;
		Node<Item> next;
		Node<Item> prev;
	}

	public Deque() {
		// construct an empty deque
		N = 0;
	}

	public boolean isEmpty() {
		// is the deque empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the deque
		return N;
	}

	public void addFirst(Item item) {
		// add the item to the front
		if (item == null) {
			throw new NullPointerException();
		}
		Node<Item> node = new Node<Item>();
		node.item = item;
		N++;
		if (first != null) {
			first.prev = node;
			node.next = first;
			first = node;
		} else {
			first = node;
			last = node;
		}
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		// add the item to the end
		Node<Item> node = new Node<Item>();
		node.item = item;
		N++;
		if (first != null) {
			last.next = node;
			node.prev = last;
			last = node;
		} else {
			first = node;
			last = node;
		}
	}

	public Item removeFirst() {

		Node<Item> oldFirst = first;
		if (N == 0) {
			throw new NoSuchElementException();
		}
		N--;
		if (first.next == null) {
			first = null;
		} else {
			first = first.next;
			first.prev = null;
		}
		return oldFirst.item;
	}

	public Item removeLast() {
		// remove and return the item from the end
		Node<Item> oldLast = last;
		if (N == 0) {
			throw new NoSuchElementException();
		}
		N--;
		if (last.prev == null) {
			last = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		return oldLast.item;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		// 8 + 16 + 8 = 32 bytes
		return new Iterator<Item>() {

			private Node<Item> pointer;
			// 匿名内部类的构造 直接写构造代码块。
			{
				pointer = first;
			}

			@Override
			public boolean hasNext() {
				// 通过结果看来，链表结构的实现，迭代器中的指针只要不为空就表示有下一个。
				return pointer != null;
			}

			@Override
			public Item next() {
				// 如果有下一个就返回这一个
				// ，同时移动指针，没有的情况有两种，可能是刚到末尾，也可能就没了，判断方法就是判断当前pointer是否为null
				if(pointer!=null){
					if (hasNext()) {
						// 有下一个，pointer不为空，
						Node<Item> oldPointer = pointer;
						pointer = pointer.next;
						return oldPointer.item;
					}else{
						// 没有下一个就直接返回pointer.item
						return pointer.item;
					}
				}else{
					throw new NoSuchElementException();
				}
			}

			/**
			 * 不使用这个方法
			 */
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static void main(String[] args) {
		Deque<String> deque = new Deque<String>();
		deque.addFirst("1");
		deque.addFirst("2");
		deque.addFirst("3");
		deque.addFirst("4");
		System.out.println("-------------------------------------");
		System.out.println(deque.size());
		System.out.println(deque.removeLast());
		System.out.println(deque.removeLast());
		System.out.println(deque.removeFirst());
		System.out.println(deque.removeFirst());
		System.out.println(deque.size());
	}

}
