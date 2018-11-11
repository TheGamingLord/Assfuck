package theblockbox.huntersdream.util.effective_against_transformation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import gnu.trove.map.hash.TObjectFloatHashMap;
import net.minecraft.item.ItemStack;
import theblockbox.huntersdream.util.Transformation;

public class ItemEffectiveAgainstTransformation extends EffectiveAgainstTransformation<ItemStack> {
	private static final Set<ItemEffectiveAgainstTransformation> OBJECTS = new HashSet<>();

	private ItemEffectiveAgainstTransformation(Predicate<ItemStack> isForObject, boolean effectiveAgainstUndead,
			TObjectFloatHashMap<Transformation> values) {
		super(isForObject, effectiveAgainstUndead, values);
	}

	public static ItemEffectiveAgainstTransformation of(Predicate<ItemStack> isForObject,
			boolean effectiveAgainstUndead, TObjectFloatHashMap<Transformation> values) {
		ItemEffectiveAgainstTransformation ieat = new ItemEffectiveAgainstTransformation(isForObject,
				effectiveAgainstUndead, values);
		OBJECTS.add(ieat);
		return ieat;
	}

	@Nullable
	public static ItemEffectiveAgainstTransformation getFromItem(ItemStack stack) {
		for (ItemEffectiveAgainstTransformation ieat : OBJECTS)
			if (ieat.isForObject(stack))
				return ieat;
		return null;
	}

	public static Set<ItemEffectiveAgainstTransformation> getObjects() {
		return Collections.unmodifiableSet(OBJECTS);
	}
}
