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
        for (Item currentItem : items) {
            if (currentItem.name.equals(SPECIAL_ITEM_NAME_SULFURAS)) {
                // Legendary items don't lose quality over time.
                // By fire be purged!
                continue;
            }

            processDefaultQualityLoss(currentItem);

            // Another day passes...
            currentItem.sellIn--;

            processQualityLossDueToSellInDate(currentItem);
        }
    }

    private void processDefaultQualityLoss(Item currentItem) {
        if (!currentItem.name.equals(SPECIAL_ITEM_NAME_AGED_BRIE)
                && !currentItem.name.equals(SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS)) {
            modifyQuality(currentItem, -1);
        } else {
            modifyQuality(currentItem, 1);
            if (currentItem.name.equals(SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS)) {
                if (currentItem.sellIn < 11) {
                    modifyQuality(currentItem, 1);
                }

                if (currentItem.sellIn < 6) {
                    modifyQuality(currentItem, 1);
                }
            }
        }
    }

    private void processQualityLossDueToSellInDate(Item currentItem) {
        if (currentItem.sellIn < 0) {
            if (currentItem.name.equals(SPECIAL_ITEM_NAME_AGED_BRIE)) {
                modifyQuality(currentItem, 1);
            } else if (currentItem.name.equals(SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS)) {
                // You missed the concert!
                modifyQuality(currentItem, -50);
            } else {
                modifyQuality(currentItem, -1);
            }
        }
    }

    private void modifyQuality(Item item, int delta) {
        if (item == null) {
            return;
        }

        item.quality = Math.clamp(item.quality + delta, 0, CURRENT_EXPANSION_QUALITY_CAP);
    }
}