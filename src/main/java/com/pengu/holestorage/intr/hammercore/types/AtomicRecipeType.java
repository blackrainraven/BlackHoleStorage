package com.pengu.holestorage.intr.hammercore.types;

import java.math.BigInteger;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

import com.pengu.hammercore.recipeAPI.IRecipeType;
import com.pengu.hammercore.recipeAPI.IRecipeType.RecipeParseException;
import com.pengu.holestorage.api.atomictransformer.AtomicTransformerRecipes;
import com.pengu.holestorage.api.atomictransformer.SimpleTransformerRecipe;

public class AtomicRecipeType implements IRecipeType<SimpleTransformerRecipe>
{
	@Override
	public boolean isJeiSupported(SimpleTransformerRecipe recipe)
	{
		return true;
	}
	
	@Override
	public Object getJeiRecipeFor(SimpleTransformerRecipe recipe, boolean remove)
	{
		return recipe;
	}
	
	@Override
	public String getTypeId()
	{
		return "blackholestorage:atomic_transformer";
	}
	
	@Override
	public SimpleTransformerRecipe createRecipe(NBTTagCompound json) throws RecipeParseException
	{
		SimpleTransformerRecipe recipe = null;
		if(!json.hasKey("cost", NBT.TAG_STRING))
			throw new RecipeParseException("\"cost\" not defined or is not acceptable!");
		if(json.hasKey("input", NBT.TAG_COMPOUND))
		{
			ItemStack input = loadStack(json.getCompoundTag("input"));
			if(json.hasKey("output", NBT.TAG_COMPOUND))
			{
				ItemStack output = loadStack(json.getCompoundTag("output"));
				BigInteger cost = new BigInteger(json.getString("cost"));
				recipe = new SimpleTransformerRecipe(input, output, cost);
			} else
				throw new RecipeParseException("\"output\" not defined!");
		} else if(json.hasKey("input", NBT.TAG_STRING))
		{
			String input = json.getString("input");
			if(json.hasKey("output", NBT.TAG_COMPOUND))
			{
				ItemStack output = loadStack(json.getCompoundTag("output"));
				BigInteger cost = new BigInteger(json.getString("cost"));
				recipe = new SimpleTransformerRecipe(input, output, cost);
			} else
				throw new RecipeParseException("\"output\" not defined!");
		} else
			throw new RecipeParseException("\"input\" not defined or is not acceptable!");
		return recipe;
	}
	
	@Override
	public void addRecipe(SimpleTransformerRecipe recipe)
	{
		AtomicTransformerRecipes.register(recipe);
	}
	
	@Override
	public void removeRecipe(SimpleTransformerRecipe recipe)
	{
		AtomicTransformerRecipes.getRecipes().remove(recipe);
	}
}