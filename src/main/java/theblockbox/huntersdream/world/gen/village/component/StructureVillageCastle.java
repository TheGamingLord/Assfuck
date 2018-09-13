package theblockbox.huntersdream.world.gen.village.component;

import java.util.List;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import theblockbox.huntersdream.init.StructureInit;

public class StructureVillageCastle extends StructureVillageComponent {
	public static final int SIZE_X = 25;
	public static final int SIZE_Y = 16;
	public static final int SIZE_Z = 25;

	public StructureVillageCastle() {
		super(null, null, StructureInit.CASTLE, SIZE_X, SIZE_Y, SIZE_Z);
	}

	public StructureVillageCastle(StructureBoundingBox boundingBox, EnumFacing facing) {
		super(boundingBox, facing, StructureInit.CASTLE, SIZE_X, SIZE_Y, SIZE_Z);
	}

	public static StructureVillageCastle buildComponent(List<StructureComponent> pieces, int structureMinX,
			int structureMinY, int structureMinZ, EnumFacing facing) {
		StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(structureMinX,
				structureMinY, structureMinZ, 0, 0, 0, SIZE_X, SIZE_Y, SIZE_Z, facing);
		return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(pieces, boundingBox) == null
				? new StructureVillageCastle(boundingBox, facing)
				: null;
	}
}