## Algorithm description:

Algorithm takes a look at all possible collections of userPair list (see ComputationService.getUserPairListCollection())
to select one with the highest strength of connection. It is necessary because our userPair list depends on first
selected pair, then It affects next chain of possible pairs.

When We get all possible collections of userPair list, we need to review collections with max count of UserPair.
There can be several lists with max count of UserPair.

After that we need to compare UserPair list for selecting one with the highest strength of connection. I suggested
to map our UserPair collections to some 'number'. 'Number' is built in next way:
* Map all elements of collection to number (UserPair -> countCommmontInterests)
* Descending sorting collection
* Union collection of number to one number. (see Points to consider: 2 point)
* Select max result. That number will correspond necessary UserPair collection.

## Points to consider:
- Performance of process can be impoved by using async execution of building userPairListCollection
(see ComputationService.getUserPairListCollection())
- Map UserPair collection to number can limit us because result number can be tto big. In this case We can separate and compare our
numbers by parts
