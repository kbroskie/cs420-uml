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

	//************************************************************************
	// Instance variables
	
	private final HashSet<E> collection;
	
	
	//************************************************************************
	// Constructors
	
	/*************************************************************************
	 * Initializes a new, empty, ImmutableSet. 
	 *************************************************************************/
	// Empty Constructor
	public ImmutableSet(){
		collection = new HashSet<E>();
	}
	
	// Composition Constructor - used by mutator methods.
	private ImmutableSet(HashSet<E> oldSet) {
		this.collection = new HashSet<E>(oldSet);
	}
	
	//************************************************************************
	// Observers
	
	/*************************************************************************
	 * Returns the number of items in the set.
	 */
	public int size() {
		return collection.size();
	}

	/*************************************************************************
	 * Returns true if there are no items contained in the set. 
	 * Returns false otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/*************************************************************************
	 * Returns true if Item d is contained within this collection.
	 */
	public boolean contains(E d) {
		return collection.contains(d);
	}

	
	//************************************************************************
	// Mutators
	
	/*************************************************************************
	 * Creates a new set containing all of the elements in this collection
	 * plus an additional item.
	 * @param newItem The item to be added to the new set.
	 * @return The new collection.
	 */
	public ImmutableSet<E> add(E newItem){
		HashSet<E> newCollection= new HashSet<E>(collection);
		newCollection.add(newItem);
		return new ImmutableSet<E>(newCollection);
	}
	
	/*************************************************************************
	 * Creates a new set containing all of the elements in this collection 
	 * except for the supplied item, if it exists.
	 * @param oldItem The item to be removed.
	 * @return The new collection.
	 */
	public ImmutableSet<E> remove(Object oldItem){
		HashSet<E> newCollection = new HashSet<E>(collection);
		newCollection.remove(oldItem);
		return new ImmutableSet<E>(newCollection);
	}
	
	//************************************************************************
	// Producers
	
	/*************************************************************************
	 * Returns a const iterator to this collection. iterator.remove() is
	 * unsupported.
	 * @return The new iterator.
	 */
	public Iterator<E> iterator() {
		return new ConstIterator<E>(collection.iterator());
	}

	
	/*************************************************************************
	 * An iterator wrapper which prevents removal.
	 * @author Daniel Rabiega
	 *
	 * @param <D> The type of objects returned by this iterator.
	 */
	
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
