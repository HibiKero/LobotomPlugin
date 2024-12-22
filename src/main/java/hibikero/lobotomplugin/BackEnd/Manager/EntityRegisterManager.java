package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiEntity;
import hibikero.lobotomplugin.BackEnd.Entities.CustomEntityManager;

public class EntityRegisterManager {
    public static void registerEntities() {
        // 注册自定义生物
        CustomEntityManager.registerEntity(new BodiEntity());
    }
} 