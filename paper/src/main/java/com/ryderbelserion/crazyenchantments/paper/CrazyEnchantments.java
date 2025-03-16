package com.ryderbelserion.crazyenchantments.paper;

import com.ryderbelserion.crazyenchantments.paper.enchants.EnchantmentRegistry;
import com.ryderbelserion.fusion.paper.Fusion;
import com.ryderbelserion.fusion.paper.FusionApi;
import com.ryderbelserion.vital.paper.VitalPaper;
import org.bukkit.plugin.java.JavaPlugin;

public class CrazyEnchantments extends JavaPlugin {

    private final EnchantmentRegistry registry;
    private final FusionApi api = FusionApi.get();
    private final Fusion fusion = this.api.getFusion();

    public CrazyEnchantments(final VitalPaper vital, final EnchantmentRegistry registry) {
        this.registry = registry;
        this.vital = vital;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public final VitalPaper getVital() {
        return this.vital;
    }

    public final EnchantmentRegistry getRegistry() {
        return this.registry;
    }
}