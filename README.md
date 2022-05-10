# GameofLife
Concurrent and Event-Based Programming - upt
http://labs.cs.upt.ro/labs/pcbe/html/proiecte/1/evolutie.txt

Simulate a cell population. All of them want to eat or spawn.
There is limited number of nutrition units which are collected by cells. One nutrition unit is enough for the time T_full, after the cell will be hungry again. If can't eat for another timp T_starve, the cell dies becoming nutrition units between 1 and 5.
After a cell eats 10 times, it will spawn before being hungry again. There are two cell types: sexual and asexual. The asexual cells divide, as a result there will be two hungry cells.
The sexual cells need a pair for spawn, which is ready to spawn searching a pair. The result is a third cell, which is not hungry.

In the simulation the cells are presented as independent threads.
