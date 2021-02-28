package uk.gemwire.whatcamebefore.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import uk.gemwire.whatcamebefore.client.ClientThingDoer;

public class JournalItem extends Item {

    public JournalItem() {
        super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote)
            ClientThingDoer.openJournalGui();

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
