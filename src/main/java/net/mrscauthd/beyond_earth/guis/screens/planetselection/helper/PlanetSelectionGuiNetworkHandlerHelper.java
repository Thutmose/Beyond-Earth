package net.mrscauthd.beyond_earth.guis.screens.planetselection.helper;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.mrscauthd.beyond_earth.crafting.IngredientStack;
import net.mrscauthd.beyond_earth.crafting.SpaceStationRecipe;
import net.mrscauthd.beyond_earth.events.Methods;

public abstract class PlanetSelectionGuiNetworkHandlerHelper {

    /** SET EVERYTHING BACK AS BEFORE THE SCREEN OPEN */
    public void defaultOptions(Player player) {
        if (!player.level.isClientSide) {
            player.setNoGravity(false);
            player.getAbilities().mayfly = false;
        }

        Methods.holdSpaceMessage(player);
        player.closeContainer();
    }

    /** DELETE ITEMS FOR THE SPACE STATION */
    public void deleteItems(Player player) {
        if (player.getAbilities().instabuild || player.isSpectator()) {
            return;
        }

        Inventory inv = player.getInventory();
        SpaceStationRecipe recipe = (SpaceStationRecipe) player.level.getRecipeManager().byKey(SpaceStationRecipe.KEY).orElse(null);

        for (IngredientStack ingredientStack : recipe.getIngredientStacks()) {
            inv.clearOrCountMatchingItems(ingredientStack::testWithoutCount, ingredientStack.getCount(), inv);
        }
    }
}
