package com.jsonsimpler;

import java.util.Iterator;

final class SingleItemIterator implements Iterator<JSON> {
	private final JSON json;
	private boolean hasNext;
	
	public SingleItemIterator(JSON json) {
		this.json = json;
		this.hasNext = !json.isNull();
	}
	
	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public JSON next() {
		if(!hasNext) {
			throw new IllegalStateException();
		}
		hasNext = false;
		return json;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}