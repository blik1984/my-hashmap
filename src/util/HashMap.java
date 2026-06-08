package util;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class HashMap<K, V> implements Map<K, V> {

	private Node<K, V>[] mapa;
	private int size;
	private int border;

	{
		mapa = new Node[16];
		border = mapa.length * 2 / 3;
	}

	public boolean put(K key, V value) {
		boolean result = insert(key, value);
		if (size > border) {
			resize();
		}
		return result;
	}

	public V get(K key) {
		int hash = getHash(key);
		int position = getPosition(hash);
		Node<K, V> node = mapa[position];
		while (node != null) {
			if (node.hash == hash && Objects.equals(key, node.key)) {
				return node.value;
			}
			node = node.next;
		}
		return null;
	}

	public V remove(K key) {
		int hash = getHash(key);
		int position = getPosition(hash);
		if (mapa[position] == null) {
			return null;
		}
		Node<K, V> currentNode = mapa[position];
		Node<K, V> beforeNode = null;

		while (currentNode != null) {
			if (currentNode.hash == hash && Objects.equals(key, currentNode.key)) {
				if (beforeNode == null) {
					mapa[position] = currentNode.next;
					size--;
					return currentNode.value;
				} else {
					beforeNode.next = currentNode.next;
					size--;
					return currentNode.value;
				}
			}
			beforeNode = currentNode;
			currentNode = currentNode.next;
		}
		return null;
	}

	private boolean insert(K key, V value) {
		int hash = getHash(key);
		int position = getPosition(hash);

		if (mapa[position] == null) {
			mapa[position] = new Node<K, V>(hash, key, value);
			size++;
			return true;
		} else {
			Node<K, V> now = mapa[position];

			while (now != null) {
				if (now.hash == hash && Objects.equals(now.key, key)) {
					now.value = value;
					return true;
				}
				now = now.next;
			}
			mapa[position] = new Node<K, V>(hash, key, value, mapa[position]);
			size++;
			return true;
		}
	}
	
	private int getHash(K key) {
		int hash;
		if (key == null) {
			hash = 0;
		} else {
			hash = key.hashCode();
		}
		return hash;
	}
	
	private int getPosition(int hash) {
		return Math.abs(hash % mapa.length);
	}

	private void resize() {
		int newSize = mapa.length * 2;
		this.border = newSize * 2 / 3;
		Node<K, V>[] oldMapa = this.mapa;
		this.mapa = new Node[newSize];
		this.size = 0;
		for (int i = 0; i < oldMapa.length; i++) {
			Node<K, V> node = oldMapa[i];
			while (node != null) {
				put(node.key, node.value);
				node = node.next;
			}
		}
	}

	static class Node<K, V> {
		final int hash;
		final K key;
		V value;
		Node<K, V> next;

		Node(int hash, K key, V value) {
			this.hash = hash;
			this.key = key;
			this.value = value;
		}

		Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		boolean addNodeToChain(Node<K, V> next) {
			this.next = next;
			return true;
		}
	}
}
