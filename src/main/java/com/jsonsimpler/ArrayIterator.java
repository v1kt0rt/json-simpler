package com.jsonsimpler;

import java.util.Iterator;

final class ArrayIterator implements Iterator<JSON> {
	private final JSON array;
	private int i;
	
	public ArrayIterator(JSON array) {
		this.array = array;
	}
	
	@Override
	public boolean hasNext() {
		return i < array.size();
	}

	@Override
	public JSON next() {
		return array.get(i++);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}