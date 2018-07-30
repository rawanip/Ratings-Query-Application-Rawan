# Ratings-Query-Application-Rawan
A high performance program that collect costumer’s ratings and store them efficiently in memory by using an efficient data structure.
Done by: Rawan Alsagheer, Nawal Saleh, Razan Aldossari.


The way addRating() works is by grouping the Ratings which share the same user ID and insert them in a BST (usersTree) and the data of each node is a BST which have the same item ID. The same applies to itemsTree, instead, we group the Rating witch have the same Item ID.

first we check if the two trees are empty. If the condition was true we initialize a new sub tree in the usersTree and insert the rating in it.if not , we search in the usersTree and compare each userID in Rating object in the usersTree to the userID in the parameter Rating object.

If the condition was true we insert that specific Rating in the usersTree. we do the same thing to fill the itemsTree but instead we checked the Item ID .

For the methods getUserRatings and getItemRatings, we use findKey() method to get all the rating for a specific user/item.
getUserRatings : The if condition is (usersTree.findKey(j)) getItemRatings: The if consition is (itemsTree.findKey(j)) and if the conditions is true it will return the current subtree.

For getAverageItemRating and getAverageUserRating, we call the previous getUserRatings and getItemRatings ,So we can get a linked list with all the ratings for the specific Item of by the specific user . Then to get the Average we looped over the returned linked list and added each rating to a sum variable. And increment the counter. finally, the result of the sum divided by the counter is returned.

In getHighestRatedItems() method we created a linked list that includes only the item IDs for all the Ratings using a while loop over an ItemList , then We initialized two variables equals to zero : Max which supposed to hold the value of the height rating and Num which
holds the current rating in the loop. We started a while loop over item IDs list and in every iteration we called the method getAverageItemRating and assign the returned value to Num, Then we compared the Num to Max in This Condition : if(num > max) , if the condition was true we assign the Num value to Max. By the end of the loop we will have the the highest average rating in the max variable. Next We search in while loop over the items list for the Items that doesn’t have the averge rating equals to the highest average rating and remove it from the items IDs list and return the list.
For the method nbComp, we use findkey method to check that the user exists and then we created a BST to get the sub tree of that key. after that we used the method findkey again to find the item j of the user I . the number of user keys + the number of items key is returned.
For getDist, first we searched for the two users ui and uj and retrieve the sub tree for each of them. After that we check if the tree is not empty. And then we get all of the data of each subtree and compute the distance between them.

In kNNUsers method we return a list of the most K nearest neighbors to user i from a list of users. User i and users at infinite distance should not be included. We create a priority queue to sort the ratings increasingly that we got from getDist so we can know which is the nearest to the user I.

The last method is getEstimatedRating, we used the method getRating to get the rating of specific user I for item j. After that we created a linked list to get all the ratings of item j and we create a linked list of integers to get the ID’s for all users of that item. And then we create another linked list to store the result of kNNUsers and use it to return the average rating of that item.

getRating(int i, int j, Rating result)
Requires: usersTree is not empty.

Input: i , j
Results: Return result which is the rating of user i for item j. If there is no rating, -1 is returned.
Output: result

nbComp(int i, int j, int result)
Requires: usersTree is not empty
Input: i , j
Results: Return result which is the number of keys to compare with in order to find the rating of user i for item j.
Output: result

getDist(int ui, int uj , int result)
Requires: usersTree is not empty
Input: ui , uj
Results: Compute the distance between the two users ui and uj and assigned it to result. If ui and uj have no common item in their ratings, then Double.POSITIVE_INFINITY is returned
Output: result

kNNUsers(int i, LinkedList<Integer> users, int k, LinkedList<Integer> result)
Requires: none
Input: users , k.
Results: Return result which is a list of at most k nearest neighbors to user i from a list of users. User i and users at infinite distance should not be included. If the users list is empty then an empty list is returned.
Output: result

getAverageRating(int j, LinkedList<Integer> users , double result)
Requires: none
Input: j , users
Results: Return result which is the average rating given to item j by a list of users. If the list users is empty or non of the users it contains rated item j, then the global average rating of item j (as computed by getAverageItemRating(j)) is returned.
Output: result.

getEstimatedRating(int i, int j, int k, double result)
Requires: none
Input: i, j , k
Results: Return result which is an estimation of the rating given by user i for item j using k nearest neighbor users. Output: result
