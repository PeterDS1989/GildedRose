package com.gildedrose;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GildedRoseTest {

    @Test
    public void testNormalItems() {
        Item item = new Item("foo", 1, 3);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);

        //Minus one for sell date not passed
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(0);
        assertThat(item.quality).isEqualTo(2);

        //Minus two for sell date passed
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(0);

        //Minimum of 0 quality
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-2);
        assertThat(item.quality).isEqualTo(0);

        //Max limit of 50 does not apply when downgrading quality
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

        //Quality increases by 1 if sell date not passed
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(0);
        assertThat(item.quality).isEqualTo(48);

        //increases by 2 if sell date passed
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(50);

        //Max 50 quality
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-2);
        assertThat(item.quality).isEqualTo(50);

        item.sellIn = 0;
        item.quality = 49;

        //Max 50 quality
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    public void testSulfuras() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 1, 47);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);

        //Unchanged
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(1);
        assertThat(item.quality).isEqualTo(47);

        //Unchanged
        item.quality = -5;
        item.sellIn = -1;
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(-5);

        //Unchanged
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

        //+1 quality if sell date > 10
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(10);
        assertThat(item.quality).isEqualTo(45);

        //+2 quality if 6 <= sell date <= 10
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(9);
        assertThat(item.quality).isEqualTo(47);

        //+2 quality if 6 <= sell date <= 10
        item.sellIn = 6;
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(5);
        assertThat(item.quality).isEqualTo(49);

        //+3 quality if 1 <= sell date <= 5, max 50
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

        //0 quality when sell date passed
        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(0);

        app.updateQuality();
        assertThat(item.sellIn).isEqualTo(-2);
        assertThat(item.quality).isEqualTo(0);
    }

}