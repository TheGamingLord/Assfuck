package theblockbox.huntersdream.util.collection;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import theblockbox.huntersdream.util.Transformation;

/**
 * A set for storing Transformation instances via storing their temporary id
 * obtained from {@link Transformation#getTemporaryID()} in a BitSet. Does not
 * allow null values
 */
public class TransformationSet extends AbstractSet<Transformation> implements Cloneable {
	private final BoolArray delegate;

	public TransformationSet() {
		this(BoolArray.of(Transformation.getRegisteredTransformations()));
	}

	public TransformationSet(Collection<Transformation> collection) {
		this(collection.stream().mapToInt(Transformation::getTemporaryID).collect(
				() -> BoolArray.of(Transformation.getRegisteredTransformations()), BoolArray::set, BoolArray::or));
	}

	public TransformationSet(BoolArray delegate) {
		this.delegate = delegate;
	}

	public TransformationSet(Transformation... transformations) {
		this(Stream.of(transformations).mapToInt(Transformation::getTemporaryID).collect(
				() -> BoolArray.of(Transformation.getRegisteredTransformations()), BoolArray::set, BoolArray::or));
	}

	@Override
	public boolean add(Transformation transformation) {
		int id = transformation.getTemporaryID();
		if (!this.delegate.get(id)) {
			this.delegate.set(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof Transformation) {
			return remove((Transformation) o);
		} else {
			return false;
		}
	}

	public boolean remove(@Nonnull Transformation transformation) {
		int id = transformation.getTemporaryID();
		if (this.delegate.get(id)) {
			this.delegate.set(id, false);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof Transformation) {
			return this.contains((Transformation) o);
		} else {
			return false;
		}
	}

	public boolean contains(@Nonnull Transformation transformation) {
		return this.delegate.get(transformation.getTemporaryID());
	}

	@Override
	public void clear() {
		this.delegate.clear();
	}

	@Override
	public Iterator<Transformation> iterator() {
		return this.stream().iterator();
	}

	@Override
	public int size() {
		return this.delegate.length();
	}

	@Override
	public Transformation[] toArray() {
		return this.stream().toArray(Transformation[]::new).clone();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] array) {
		if (array instanceof Transformation[]) {
			return (T[]) this.toArray();
		}
		return super.toArray(array);
	}

	@Override
	public TransformationSet clone() {
		return new TransformationSet(this.delegate.clone());
	}
}