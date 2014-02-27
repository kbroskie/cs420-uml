package edu.millersville.cs.segfault.immutable;

import java.util.HashSet;
import java.util.Iterator;

/*****************************************************************************
 * A set which can not be changed after construction.
 * @author Daniel Rabiega
 *
 * @param <E> The type of items stored in the set.
 *****************************************************************************/


public class ImmutableSet<E> {

	private HashSet<E> collection;
	
	/*************************************************************************
	 * Initializes a new, empty, ImmutableSet. 
	 *************************************************************************/
	public ImmutableSet(){
		collection = new HashSet<E>();
	}
	
	
	public ImmutableSet(ImmutableSet<E> oldSet){
		Iterator<E> iter = oldSet.iterator();
		this.collection = new HashSet<E>();
		while (iter.hasNext()) {
			this.collection.add(iter.next());
		}
	}
	
	public ImmutableSet(HashSet<E> oldSet) {
		this.collection = new HashSet<E>(oldSet);
	}
	
	public ImmutableSet<E> add(E newItem){
		HashSet<E> newCollection= new HashSet<E>(collection);
		newCollection.add(newItem);
		return new ImmutableSet<E>(newCollection);
	}
	
	public ImmutableSet<E> remove(Object oldItem){
		HashSet<E> newCollection = new HashSet<E>(collection);
		newCollection.remove(oldItem);
		return new ImmutableSet<E>(newCollection);
	}
	
	public Iterator<E> iterator() {
		return new ConstIterator<E>(collection.iterator());
	}

	
	public int size() {
		return collection.size();
	}

	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public boolean contains(E d) {
		return collection.contains(d);
	}

	private class ConstIterator<D> implements Iterator<D> {

		private Iterator<D> iter;
		
		public ConstIterator(Iterator<D> iter) {
			this.iter = iter;
		}
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public D next() {
			return iter.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Set it Immutable.");
			
		}
		
	}
	
}
