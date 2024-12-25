package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

public class BodiData {
    public static final long LOCK_DURATION = 100; // 5秒（每20个tick）
    public static final double LOCK_DISTANCE = 10.0; // 10米
    public static final double MAX_HEALTH = 60.0; // 波迪的最大生命值
    public static final double ATTACK_DAMAGE = 9.0; // 每次攻击造成的伤害
    public static final long SHEAR_COOLDOWN = 36000; // 6分钟冷却时间（3600 ticks）
}
