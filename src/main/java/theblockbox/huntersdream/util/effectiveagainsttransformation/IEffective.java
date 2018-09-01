package theblockbox.huntersdream.util.effectiveagainsttransformation;

import theblockbox.huntersdream.util.enums.Transformations;

@FunctionalInterface
public interface IEffective {
	/**
	 * Should return the transformation against which the implementing class is
	 * effective
	 */
	public Transformations[] transformations();

	default public boolean effectiveAgainst(Transformations transformation) {
		for (Transformations t : transformations()) {
			if (t == transformation)
				return true;
		}
		return false;
	}
}