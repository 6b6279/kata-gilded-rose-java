package com.gildedrose;

// Everyone's favourite store in Stormwind!
class GildedRose {

    private final static String SPECIAL_ITEM_NAME_SULFURAS = "Sulfuras, Hand of Ragnaros";
    private final static String SPECIAL_ITEM_NAME_AGED_BRIE = "Aged Brie";
    private final static String SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private final static int MIN_QUALITY = 0;
    private final static int MAX_QUALITY = 50;

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

            processQualityLossBeforeClosing(currentItem);

            // Another day passes...
            currentItem.sellIn--;

            processQualityLossAfterClosing(currentItem);
        }
    }

    private void processQualityLossBeforeClosing(Item currentItem) {
        switch (currentItem.name) {
            case SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS:
                if (currentItem.sellIn < 6) {
                    modifyQuality(currentItem, 2);
                } else if (currentItem.sellIn < 11) {
                    modifyQuality(currentItem, 1);
                }
            case SPECIAL_ITEM_NAME_AGED_BRIE:
                modifyQuality(currentItem, 1);
                break;
            default:
                modifyQuality(currentItem, -1);
        }
    }

    private void processQualityLossAfterClosing(Item currentItem) {
        if (currentItem.sellIn < 0) {
            if (currentItem.name.equals(SPECIAL_ITEM_NAME_AGED_BRIE)) {
                modifyQuality(currentItem, 1);
            } else if (currentItem.name.equals(SPECIAL_ITEM_NAME_80ETC_BACKSTAGE_PASS)) {
                // You missed the concert... the pass is worthless.
                setQuality(currentItem, 0);
            } else {
                modifyQuality(currentItem, -1);
            }
        }
    }

    private void modifyQuality(Item item, int delta) {
        if (item == null) {
            return;
        }

        item.quality = Math.clamp(item.quality + delta, MIN_QUALITY, MAX_QUALITY);
    }

    private void setQuality(Item item, int setTo) {
        if (item == null) {
            return;
        }

        item.quality = Math.clamp(setTo, MIN_QUALITY, MAX_QUALITY);
    }
}