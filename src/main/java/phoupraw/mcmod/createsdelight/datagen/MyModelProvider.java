package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.block.CopperTunnelBlock;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyFluids;
import phoupraw.mcmod.createsdelight.registry.MyItems;

import static net.minecraft.data.client.VariantSettings.MODEL;
public class MyModelProvider extends FabricModelProvider {
    public MyModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    /**
     * {@link BlockStateModelGenerator#registerSimpleState(Block)}会自动生成物品模型。
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleState(MyBlocks.PAN);
        generator.registerSimpleState(MyBlocks.GRILL);
        generator.registerSimpleState(MyBlocks.SPRINKLER);
        generator.registerSimpleState(MyBlocks.BAMBOO_STEAMER);
//        generator.registerSimpleState(MyBlocks.SMART_DRAIN);
        generator.blockStateCollector.accept(MultipartBlockStateSupplier.create(MyBlocks.SMART_DRAIN)
          .with(BlockStateVariant.create()
            .put(MODEL, ModelIds.getBlockModelId(MyBlocks.SMART_DRAIN)))
          .with(When.create().set(Properties.LIT, true), BlockStateVariant.create()
            .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.SMART_DRAIN, "_fire"))));
        {
            var sup = MultipartBlockStateSupplier.create(MyBlocks.COPPER_TUNNEL)
              .with(BlockStateVariant.create()
                .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.COPPER_TUNNEL, "_frame")));
            for (Direction side : Direction.Type.HORIZONTAL) {
                EnumProperty<CopperTunnelBlock.Model> property = CopperTunnelBlock.Model.HORIZONTALS.get(side);
                VariantSettings.Rotation rotation = VariantSettings.Rotation.values()[side.getHorizontal()];
                sup.with(When.create().set(property, CopperTunnelBlock.Model.GLASS), BlockStateVariant.create()
                    .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.COPPER_TUNNEL, "_glass"))
                    .put(VariantSettings.Y, rotation))
                  .with(When.create().set(property, CopperTunnelBlock.Model.COPPER), BlockStateVariant.create()
                    .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.COPPER_TUNNEL, "_copper"))
                    .put(VariantSettings.Y, rotation));
            }
            generator.blockStateCollector.accept(sup);
        }
        generator.excludeFromSimpleItemModelGeneration(MyBlocks.COPPER_TUNNEL);

        for (Item item : new Item[]{MyFluids.SUNFLOWER_OIL.getBucketItem(), MyFluids.SUNFLOWER_OIL.getBottle(), MyItems.PAN_FRIED_BEEF_PATTY, MyItems.THICK_PORK_SLICE, MyItems.PAN_FRIED_PORK_SLICE, MyItems.THIN_PORK_SLICE, MyItems.GRILLED_PORK_SLICE, MyItems.SUGAR_PORK, MyItems.LEAVES_RICE, MyItems.VANILLA, MyItems.VANILLA_SWEET_ROLL, MyItems.STEAMED_BUNS}) {
            generator.registerItemModel(item);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }
}
