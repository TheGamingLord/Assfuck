package theblockbox.huntersdream.world.gen.village.handler;

import java.util.Random;

import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public abstract class VillageCreationHandler implements VillagerRegistry.IVillageCreationHandler {
	private Class<? extends StructureVillagePieces.Village> componentClass;
	private int pieceWeight;
	private int maxStructuresInVillage;

	public VillageCreationHandler(Class<? extends StructureVillagePieces.Village> componentClass, int pieceWeight,
			int maxStructuresInVillage) {
		this.componentClass = componentClass;
		this.pieceWeight = pieceWeight;
		this.maxStructuresInVillage = maxStructuresInVillage;
	}

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
		return new StructureVillagePieces.PieceWeight(this.componentClass, this.pieceWeight,
				this.maxStructuresInVillage);
	}

	@Override
	public Class<?> getComponentClass() {
		return componentClass;
	}
}
