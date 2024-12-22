package hibikero.lobotomplugin.BackEnd.Entities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface ICustomEntity {
    String getIdentifier(); // 获取生物的唯一标识符
    Entity spawn(Location location); // 在指定位置生成生物
} 