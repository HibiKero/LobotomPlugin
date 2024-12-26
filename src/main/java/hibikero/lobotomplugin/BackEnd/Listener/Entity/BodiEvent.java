package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiData;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class BodiEvent implements Listener {
    private BodiWolf bodiWolf;

    public BodiEvent(BodiWolf bodiWolf) {
        this.bodiWolf = bodiWolf;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        getLogger().severe("\u001B第零层触发");
        if (event.getDamager() instanceof Wolf) {
            getLogger().severe("\u001B第一层触发");
            Wolf wolf = (Wolf) event.getDamager();
            if (wolf.equals(bodiWolf.getWolf())) {
                getLogger().severe("\u001B第二层触发");
                // 波迪攻击逻辑
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    event.setDamage(BodiData.ATTACK_DAMAGE); // 设置攻击伤害为9
                    getLogger().severe("\u001B攻击成功");
                }
            }
        }
    }
}

