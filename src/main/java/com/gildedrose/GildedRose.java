package com.gildedrose;

import java.text.MessageFormat;
import java.util.Arrays;

class GildedRose {

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> {
            ItemCategory itemCategory = categorizeItem(item);
            item.quality = getNewQuality(itemCategory, item.quality, item.sellIn);
            item.sellIn = getNewSellIn(itemCategory, item.sellIn);
        });
    }

    /**
     * Derives the new quality for this item.
     * @param itemCategory category of the item
     * @param currentQuality current quality value of the item
     * @param currentSellIn current sell in value of the item
     * @return new quality value
     */
    private int getNewQuality(ItemCategory itemCategory, int currentQuality, int currentSellIn) {
        int qualityDiff;
        switch (itemCategory) {
            case LEGENDARY:
                //Unmodifiable category
                qualityDiff = 0;
                break;
            case CHEESE:
                qualityDiff = calculateCheeseQualityDiff(currentSellIn);
                break;
            case BACKSTAGE_PASSES:
                qualityDiff = calculateBackstagePassesQualityDiff(currentQuality, currentSellIn);
                break;
            case CONJURED:
                qualityDiff = calculateConjuredQualityDiff(currentSellIn);
                break;
            case NORMAL:
                qualityDiff = calculateNormalQualityDiff(currentSellIn);
                break;
            default:
                throw new IllegalArgumentException(MessageFormat.format("Item category {0} not supported for new quality calculation", itemCategory));
        }
        int newQuality = currentQuality + qualityDiff;
        //No update if quality diff = 0
        if(qualityDiff < 0) {
            newQuality = Math.max(MIN_QUALITY, newQuality);
        } else if(qualityDiff > 0) {
            newQuality = Math.min(MAX_QUALITY, newQuality);
        }
        return newQuality;
    }

    /**
     * Calculates the quality difference for the conjured item category
     * @param currentSellIn current sell in value
     * @return twice the result of method calculateNormalQualityDiff
     */
    private int calculateConjuredQualityDiff(int currentSellIn) {
        return calculateNormalQualityDiff(currentSellIn) * 2;
    }

    /**
     * Calculates the quality difference for the normal item category
     * @param currentSellIn current sell in value
     * @return -2 of current sell in <= 0, -1 otherwise
     */
    private int calculateNormalQualityDiff(int currentSellIn) {
        return currentSellIn <= 0 ? -2 : -1;
    }

    /**
     * Calculates the quality difference for the backstage passes item category
     * @param currentQuality current quality value
     * @param currentSellIn current sell in value
     * @return 1 if current sell in > 10, 2 if 10 >= current sell in > 5, 3 if 5 >= current sell in >= 0, -currentQuality otherwise
     */
    private int calculateBackstagePassesQualityDiff(int currentQuality, int currentSellIn) {
        int qualityDiff;
        if(currentSellIn > 10) {
            qualityDiff = 1;
        } else if(currentSellIn > 5) {
            qualityDiff = 2;
        } else if(currentSellIn > 0) {
            qualityDiff = 3;
        } else {
            qualityDiff = -currentQuality;
        }
        return qualityDiff;
    }

    /**
     * Calculates the quality difference for the cheese item category
     * @param currentSellIn current sell in value
     * @return result of method calculateNormalQualityDiff with inversed sign
     */
    private int calculateCheeseQualityDiff(int currentSellIn) {
        return calculateNormalQualityDiff(currentSellIn) * -1;
    }

    /**
     * Derives the new sell in value. For non-legendary category, current sell in -1. Otherwise, current sell in.
     * @param itemCategory category of the item
     * @param currentSellIn current sell in value of the item
     * @return new sell in value
     */
    private int getNewSellIn(ItemCategory itemCategory, int currentSellIn) {
        int newSellIn = currentSellIn;
        if(itemCategory != ItemCategory.LEGENDARY) {
            newSellIn--;
        }
        return newSellIn;
    }

    /**
     * Categorizes an item by its name (assumed not null)
     * @param item item
     * @return item category
     */
    private ItemCategory categorizeItem(Item item) {
        String itemName = item.name.toLowerCase();
        ItemCategory itemCategory;
        switch (itemName) {
            case "aged brie":
                itemCategory = ItemCategory.CHEESE;
                break;
            case "backstage passes to a tafkal80etc concert":
                itemCategory = ItemCategory.BACKSTAGE_PASSES;
                break;
            case "sulfuras, hand of ragnaros":
                itemCategory = ItemCategory.LEGENDARY;
                break;
            case "conjured mana cake":
                itemCategory = ItemCategory.CONJURED;
                break;
            default:
                itemCategory = ItemCategory.NORMAL;
                break;
        }
        return itemCategory;
    }
}