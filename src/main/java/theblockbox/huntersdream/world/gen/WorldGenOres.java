package theblockbox.huntersdream.world.gen;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import theblockbox.huntersdream.Main;
import theblockbox.huntersdream.blocks.BlockOre;
import theblockbox.huntersdream.init.BlockInit;
import theblockbox.huntersdream.util.exceptions.UnexpectedBehaviorException;
import theblockbox.huntersdream.util.handlers.ConfigHandler;

public class WorldGenOres implements IWorldGenerator {

	public WorldGenOres() {
		for (BlockOre blockOreBase : BlockInit.ORES) {
			blockOreBase.setWorldGenMinable(new WorldGenMinable(blockOreBase.getDefaultState(),
					ConfigHandler.server.ores.veinSize, BlockMatcher.forBlock(blockOreBase.SPAWN_ON)));
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {

		BlockInit.ORES.stream().filter(e -> e.DIMENSION == world.provider.getDimension())
				.filter(e -> e == BlockInit.ORE_SILVER ? ConfigHandler.server.ores.generateSilverOre : true)
				.forEach(e -> {
					if (e.getWorldGenMinable() == null)
						Main.getLogger().error(
								"An ore's WorldGenMinable object hasn't been initialized and therefore the ore couldn't be generated. If you see this message please report it!");
					else
						runGenerator(e.getWorldGenMinable(), world, random, chunkX, chunkZ, e.CHANCE, e.MIN_HEIGHT,
								e.MAX_HEIGHT);
				});
	}

	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance,
			int minHeight, int maxHeight) {
		if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256)
			throw new UnexpectedBehaviorException("Ore generated out of bounds");
		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chance; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);

			gen.generate(world, rand, new BlockPos(x, y, z));
		}
	}
}