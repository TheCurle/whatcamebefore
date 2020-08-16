package uk.gemwire.whatcamebefore.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class JournalItem extends Item {

    public JournalItem() {
        super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1));
    }
}
