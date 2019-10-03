package com.gildedrose;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GildedRoseTest {

    @Test
    public void testNormalItems() {
        Item item = new Item("foo", 1, 3);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(0);
        assertThat(item.quality).isEqualTo(2);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(0);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-2);
        assertThat(item.quality).isEqualTo(0);

        item.quality = 60;
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-3);
        assertThat(item.quality).isEqualTo(58);
    }

    @Test
    public void testAgedBrie() {
        Item item = new Item("Aged Brie", 1, 47);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(0);
        assertThat(item.quality).isEqualTo(48);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(50);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-2);
        assertThat(item.quality).isEqualTo(50);

        item.sellIn = 0;
        item.quality = 49;

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    public void testSulfuras() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 1, 47);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(1);
        assertThat(item.quality).isEqualTo(47);

        item.quality = -5;
        item.sellIn = -1;
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(-5);

        item.quality = 80;
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(80);
    }

    @Test
    public void testBackstagePasses() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 44);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(10);
        assertThat(item.quality).isEqualTo(45);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(9);
        assertThat(item.quality).isEqualTo(47);

        item.sellIn = 6;
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(5);
        assertThat(item.quality).isEqualTo(49);

        item.quality = 44;
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(4);
        assertThat(item.quality).isEqualTo(47);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(3);
        assertThat(item.quality).isEqualTo(50);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(2);
        assertThat(item.quality).isEqualTo(50);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(1);
        assertThat(item.quality).isEqualTo(50);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(0);
        assertThat(item.quality).isEqualTo(50);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(0);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-2);
        assertThat(item.quality).isEqualTo(0);
    }

}