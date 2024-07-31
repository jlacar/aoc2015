# Day 16: Aunt Sue

This was a relatively easy problem to solve. The main operation in the solution comparing the values in one map (Aunt Sue's things) to the values in another (the trace analysis). Part 2 just added a few more ways to compare the quantities to determine a match.

Pretty much the only tricky part to this problem was that the list items (all the different Aunt Sue) were assigned a 1-based identification number. Since Kotlin has 0-based indices, I needed to adjust for that in both parts of the solution, hence the `+ 1` term at the end.

# Part 1 - Exact matches for quantities found in the trace report

use of `all` 

# Part 2 - Additional match logic for inexact matches

use of `when` to handle different match criteria, using `else` as the fall-through

# Other stuff