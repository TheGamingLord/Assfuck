package theblockbox.huntersdream.util.helpers;

import java.util.Random;

import theblockbox.huntersdream.Main;

public class ChanceHelper {
	public static boolean chanceOf(Random random, int percentage) {
		if (percentage > 100 || percentage < 0) {
			throw new IllegalArgumentException("Percentage can't be over 100 / under 0");
		}

		return ((random.nextInt(100) + 1) <= percentage);
	}

	public static boolean chanceOf(int percentage) {
		return chanceOf(Main.RANDOM, percentage);
	}
}