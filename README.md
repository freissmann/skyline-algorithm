# Skyline-Algorithm
This is a possible example of the skyline algorithm based on a sorted binary tree written in Kotlin.

# Problem
You are given a series of buildings and only the outer most line should of all buildings should be drawn. A building is defined by a string with the format [x1, x2, y1] where x1 is the start- and x2 the end-coordinate and y1 is the height.

For instance, the sequence of the buildings B1 to B7 results in the following skyline:

* B1 = Building(1, 4, 1)
* B2 = Building(2, 3, 2)
* B3 = Building(7, 8, 1.5)
* B4 = Building(5.5, 6.5, 1)
* B5 = Building(5, 7, 3)
* B6 = Building(3.5, 6, 1.5)
* B7 = Building(6, 9, 0.5)

![Example Skyline](/images/skyline-b1-to-b7.png)

And if added in the listed order, the following tree will be created:
![Example Skyline](/images/skyline-b1-to-b7-tree.png)


# Algorithm implemented
For every added building it is recursively propagated down the tree. Either as one part or split.

* Do nothing if the added building is empty (height = 0 or width = 0)

or

* Set the added building as the node-value if there was non yet

or

* If the added building is far left of the node-value let the left node handle the addition

or

* Far right buildings will be propagated to the right node

or

* (The added building is overlapping either right, left or both) Split the added building in three parts. And propagate the left-part to the left-node and the right-part to the right node. Only handle the intersecting part inside the current node. See in depth behaviour below:

1.1. In this example B1 is the root node and   we will add B2 to it.
![1.1](/images/algorithm-1.1.png)

1.2. The left part of both nodes, which is not intersecting will be propagated to the left node. And as there is no left node yet, just add it.
![1.2](/images/algorithm-1.3.png)


1.3. Same behaviour for the right part as in 1.2.
![1.3](/images/algorithm-1.3.png)


1.4. Now both parts to the left and right have been handled correctly, it is time to set the current node to the intersecting part with the maximum height.
![1.4](/images/algorithm-1.4.png)

# Open points
* Tree re-balancing mechanism, as in worst case the tree is actually only a linked-list
* Needs test to ensure that all possible permutations of the addition order always result in same skyline

# License
![MIT License](LICENSE)
