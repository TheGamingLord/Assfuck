package theblockbox.huntersdream.api.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.ArrayUtils;
import theblockbox.huntersdream.api.helpers.GeneralHelper;
import theblockbox.huntersdream.api.interfaces.IAmmunition;
import theblockbox.huntersdream.items.*;
import theblockbox.huntersdream.items.tools.ToolAxe;
import theblockbox.huntersdream.items.tools.ToolPickaxe;
import theblockbox.huntersdream.util.Reference;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<>();

    @GameRegistry.ObjectHolder("huntersdream:silver_ingot")
    public static final Item SILVER_INGOT = null;

    @GameRegistry.ObjectHolder("huntersdream:silver_sword")
    public static final Item SILVER_SWORD = null;

    @GameRegistry.ObjectHolder("huntersdream:hunters_journal")
    public static final Item HUNTERS_JOURNAL = null;

    @GameRegistry.ObjectHolder("huntersdream:tent")
    public static final Item TENT = null;

    @GameRegistry.ObjectHolder("huntersdream:flintlock_pistol")
    public static final Item FLINTLOCK_PISTOL = null;

    @GameRegistry.ObjectHolder("huntersdream:musket_ball")
    public static final Item MUSKET_BALL = null;

    @GameRegistry.ObjectHolder("huntersdream:hunter_hat")
    public static final Item HUNTER_HAT = null;

    @GameRegistry.ObjectHolder("huntersdream:hunter_coat")
    public static final Item HUNTER_COAT = null;

    @GameRegistry.ObjectHolder("huntersdream:hunter_pants")
    public static final Item HUNTER_PANTS = null;

    @GameRegistry.ObjectHolder("huntersdream:hunter_boots")
    public static final Item HUNTER_BOOTS = null;

    // Materials
    public static final Item.ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial(Reference.MODID + ":tool_silver", 3, 60,
            6.0F, 0.0F, 14);
    public static final ItemArmor.ArmorMaterial ARMOR_SILVER = EnumHelper.addArmorMaterial(Reference.MODID + ":armor_silver",
            Reference.MODID + ":silver", 6, new int[]{1, 3, 5, 2}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.2F);
    // TODO: Same durability as chainmail
    public static final ItemArmor.ArmorMaterial ARMOR_HUNTER = EnumHelper.addArmorMaterial(Reference.MODID + ":armor_hunter",
            Reference.MODID + ":hunter", 6, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        ItemInit.ITEMS.forEach(item -> ItemInit.registerItemBlock(item, event));
        Item silverIngot = ItemInit.registerItem(new Item(), "silver_ingot", CreativeTabInit.HUNTERSDREAM_MISC, event);
        ItemInit.TOOL_SILVER.setRepairItem(new ItemStack(silverIngot));
        ItemInit.ARMOR_SILVER.setRepairItem(new ItemStack(silverIngot));
        ItemInit.ARMOR_HUNTER.setRepairItem(new ItemStack(Items.LEATHER));
//        TODO: Uncomment this when the hunter's journal will be worked on
//        ItemInit.registerItem(new ItemHuntersJournal(), "hunters_journal", event);
//        ItemInit.registerItem(new ItemHuntersJournalPage(), "hunters_journal_page", CreativeTabInit.HUNTERSDREAM_MISC, event);
        ItemInit.registerItem(new ItemBestiary(), "bestiary", CreativeTabInit.HUNTERSDREAM_MISC, event);
        ItemInit.registerItem(new ItemTent(), "tent", CreativeTabInit.HUNTERSDREAM_MISC, event);
        ItemInit.registerToolSet("silver", ItemInit.TOOL_SILVER, event);
        ItemInit.registerArmorSet("silver", ItemInit.ARMOR_SILVER, event);
        ItemInit.registerItem(new ItemHunterArmor(1, EntityEquipmentSlot.HEAD), "hunter_hat",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemHunterArmor(1, EntityEquipmentSlot.CHEST), "hunter_coat",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemHunterArmor(2, EntityEquipmentSlot.LEGS), "hunter_pants",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemHunterArmor(1, EntityEquipmentSlot.FEET), "hunter_boots",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemHerbalAconiteWater(), "herbal_aconite_water", event);
        ItemInit.registerItem(new ItemFlintlockGun(10, 3451, 1, 2),
                "flintlock_musket", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemFlintlockGun(6, 3255, 1, 1),
                "flintlock_pistol", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemFlintlockGunBlunderBuss(6, 3465, 5, 6),
                "flintlock_blunderbuss", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemMusketBall(), "musket_ball", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemMusketBall(ArrayUtils.add(ItemMusketBall.AMMUNITION_TYPES,
                IAmmunition.AmmunitionType.SILVER)), "silver_musket_ball", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
    }

    private static Item registerItem(Item item, String name, RegistryEvent.Register<Item> event) {
        return ItemInit.registerItem(item, name,
                item.getCreativeTab() == null ? CreativeTabInit.HUNTERSDREAM_MISC : item.getCreativeTab(), event);
    }

    private static Item registerItem(Item item, String name, CreativeTabs tab, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(item.setRegistryName(GeneralHelper.newResLoc(name))
                .setTranslationKey(Reference.MODID + "." + name).setCreativeTab(tab));
        ItemInit.ITEMS.add(item);
        return item;
    }

    private static Item registerItemBlock(Item itemBlock, RegistryEvent.Register<Item> event) {
        event.getRegistry()
                .register(itemBlock.setTranslationKey(itemBlock.getRegistryName().toString().replace(':', '.')));
        return itemBlock;
    }

    private static void registerToolSet(String name, Item.ToolMaterial toolMaterial, RegistryEvent.Register<Item> event) {
        ItemInit.registerItem(new ItemHoe(toolMaterial), name + "_hoe", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ToolPickaxe(toolMaterial), name + "_pickaxe", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS,
                event);
        ItemInit.registerItem(new ItemSpade(toolMaterial), name + "_shovel", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS,
                event);
        ItemInit.registerItem(new ItemSword(toolMaterial), name + "_sword", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS,
                event);
        ItemInit.registerItem(new ToolAxe(toolMaterial), name + "_axe", CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
    }

    private static void registerArmorSet(String name, ItemArmor.ArmorMaterial material, RegistryEvent.Register<Item> event) {
        ItemInit.registerItem(new ItemArmor(material, 1, EntityEquipmentSlot.HEAD), name + "_helmet",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemArmor(material, 1, EntityEquipmentSlot.CHEST), name + "_chestplate",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemArmor(material, 2, EntityEquipmentSlot.LEGS), name + "_leggings",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
        ItemInit.registerItem(new ItemArmor(material, 1, EntityEquipmentSlot.FEET), name + "_boots",
                CreativeTabInit.HUNTERSDREAM_TOOLS_AND_WEAPONS, event);
    }
}
