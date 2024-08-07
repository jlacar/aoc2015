# Day 18: Like a GIF For Your Yar

This problem is basically [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) (GoL) except with a grid of lights that are either on (alive) or off (dead). The grid does not wrap around itself, so you need to consider  cells on the edges of the grid when calculating the number of neighbors of a cell.

## Part 1

A straightforward GoL to find the state after 100 steps (generations). 

## Part 2

This part adds a rule that the cells at four corners of the grid are always on.

## Notes

Anyone who is familiar with Conway's Game of Life will find this problem relatively easy. I solved both parts relatively quickly. Refactoring for clarity was, as always, the most fun part of this exercise.

