package polari_stars.wild_wind.the_wild_update.registry.tag;

public interface TwuTags {
    static void init() {
        TwuBiomeTags.init();
        TwuBlockTags.init();
        TwuItemTags.init();
        TwuEntityTypeTags.init();
    }
}
