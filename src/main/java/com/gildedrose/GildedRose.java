package com.gildedrose;

class GildedRose {

    private final static String SPECIAL_ITEM_NAME_SULFURAS = "Sulfuras, Hand of Ragnaros";
    private final static String SPECIAL_ITEM_NAME_AGED_BRIE = "Aged Brie";
    private final static String SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private final static int CURRENT_EXPANSION_QUALITY_CAP = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].name.equals(SPECIAL_ITEM_NAME_SULFURAS)) {
                // Legendary items don't lose quality over time...
                // By fire be purged!
                continue;
            }

            if (!items[i].name.equals(SPECIAL_ITEM_NAME_AGED_BRIE)
                    && !items[i].name.equals(SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS)) {
                    modifyQuality(items[i], -1);
            } else {
                modifyQuality(items[i], 1);
                if (items[i].name.equals(SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS)) {
                        if (items[i].sellIn < 11) {
                            modifyQuality(items[i], 1);
                        }

                        if (items[i].sellIn < 6) {
                            modifyQuality(items[i], 1);
                        }
                    }
            }

            items[i].sellIn--;

            if (items[i].sellIn < 0) {
                if (items[i].name.equals(SPECIAL_ITEM_NAME_AGED_BRIE)) {
                    modifyQuality(items[i], 1);
                } else if (items[i].name.equals(SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS)) {
                    // You missed the concert!
                    modifyQuality(items[i], -50);
                } else {
                    modifyQuality(items[i], -1);
                }
            }
        }
    }

    private void modifyQuality(Item item, int steps) {
        if (item == null) {
            return;
        }

        item.quality = Math.clamp(item.quality + steps, 0, CURRENT_EXPANSION_QUALITY_CAP);
    }
}