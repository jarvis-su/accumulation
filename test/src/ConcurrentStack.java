import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<E> {

	AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();

	private static class Node<E> {
		public Node<E> next;
		public E item;

		public Node(E item) {
			this.item = item;
		}
	}

	public void push(E item) {
		Node<E> newNode = new Node<E>(item);
		Node<E> oldNode = null;
		do {
			oldNode = head.get();
			newNode.next = oldNode;
		} while (!head.compareAndSet(oldNode, newNode));
	}

	public E pop() {
		Node<E> oldNode = null;
		Node<E> newNode = null;
		do {
			oldNode = head.get();
			if (oldNode == null) {
				return null;
			}
			newNode = oldNode.next;
		} while (!head.compareAndSet(oldNode, newNode));
		return oldNode.item;
	}
}
