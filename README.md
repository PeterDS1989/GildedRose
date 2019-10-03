# GildedRose

Steps:
- GildedRoseTest adapted to test existing functionality
- Change existing function updateQuality
- GildedRoseTest adapted to add test for new functionality
- Add code for new functionality

Pom file contains maven compiler version and additional UT library.

Assumptions:
- GildedRose is properly initialized: items array is never null.
- Item name is never null. Otherwise, method 'categorizeItem' needs to be adapted.
- The original code does not have dependencies between items directly, 
    but I changed this in the new implementation. 
    Normally, I would check this with an analyst or business before I start.