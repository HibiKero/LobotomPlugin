package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Entities.ICustomEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomEntityManager {
    private static final Map<String, ICustomEntity> registeredEntities = new HashMap<>();

    public static void registerEntity(ICustomEntity entity) {
        registeredEntities.put(entity.getIdentifier().toLowerCase(), entity);
    }

    public static ICustomEntity getEntity(String identifier) {
        return registeredEntities.get(identifier.toLowerCase());
    }

    public static boolean hasEntity(String identifier) {
        return registeredEntities.containsKey(identifier.toLowerCase());
    }
} 