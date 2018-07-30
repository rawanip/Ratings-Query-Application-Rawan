import java.io.File;
import java.util.*;  

public class RatingManager {
   
   private BST<BST<Rating>> usersTree;
   private BST<BST<Rating>> itemsTree;	
  

	// Constructor
   public RatingManager(){
      usersTree=new  BST<BST<Rating>> ();
      itemsTree=new BST<BST<Rating>> ();
   }
   
     //-------------------------------------------------------------------------------------------
	
	/*Read ratings from a file and create a RatingManager object that stores these ratings.
    The ratings must be inserted in their order of appearance in the file.*/
   public static RatingManager read(String fileName){
   
      RatingManager result = new RatingManager();
   
      File file = new File(fileName);
      try {
      
         Scanner sc = new Scanner(file);
      
         while (sc.hasNext()) {
            int user = sc.nextInt();
            int item = sc.nextInt();
            int rating= sc.nextInt();
            sc.nextInt();
            
            Rating r = new Rating(user,item,rating);
            result.addRating(r);
         }
         sc.close();
      } 
      catch (Exception e) {
         e.printStackTrace();
      }
    
      return result;
   }
    
    
    
    
      //-------------------------------------------------------------------------------------------
      //RAZAN
	// Add a rating 
   public void addRating(Rating rating){
      BST<Rating> subTree1= new BST<Rating>();
   //USERTREE
   //userTree is empty
      boolean flag= true;
      if(usersTree.empty ()){
         subTree1.insert(rating.getItemId(), rating);
         usersTree.insert(rating.getUserId(), subTree1);
      }
      //userTree is not enmpty
      else{
         //find user ID
         flag=usersTree.findKey(rating.getUserId());
         //userID is found
         if(flag){
            subTree1=usersTree.retrieve();
            subTree1.insert(rating.getItemId(), rating);
         }
         //userID is not found
         else{
            subTree1.insert(rating.getItemId(), rating);
            usersTree.insert(rating.getUserId(), subTree1);
         }
         
      }//end else
   //itemsTree is empty
   BST<Rating> subTree2= new BST<Rating>();
   
      if(itemsTree.empty ()){
         subTree2.insert(rating.getUserId(), rating);
         itemsTree.insert(rating.getItemId(), subTree2);
      }
      //itemsTree is not enmpty
      else{
         //find item ID
         flag=itemsTree.findKey(rating.getItemId());
         //itemsID is found
         if(flag){
            subTree2=itemsTree.retrieve();
            subTree2.insert(rating.getUserId(), rating);
         }
         //itemsID is not found
         else{
            subTree2.insert(rating.getUserId(), rating);
            itemsTree.insert(rating.getItemId(), subTree2);
         }
         
      }//end else   
   }//end method
   
   //-------------------------------------------------------------------------------------------
   
      //NAWAL
	// Return all ratings given by user i. 
   public LinkedList<Rating> getUserRatings(int i){
   
      if(usersTree.findKey(i))
         return usersTree.retrieve().getAllData();
      else
         return null;
   }
      
      
      //-------------------------------------------------------------------------------------------
      //NAWAL
	// Return all ratings given to item j
   public LinkedList<Rating> getItemRatings(int j){
    
      if(itemsTree.findKey(j))
         return itemsTree.retrieve().getAllData();
      else
         return null;
   }
   
   
   
     //-------------------------------------------------------------------------------------------

      //NAWAL
	// Return the list of highest rated items 
   public LinkedList<Integer> getHighestRatedItems(){
   
      LinkedList<BST<Rating>> items = new LinkedList<BST<Rating>>();
      LinkedList<Integer> highest = new LinkedList<Integer>();
   
      items = itemsTree.getAllData();
      items.findFirst();
    
      while(!items.last())
      {    
         LinkedList<Rating> temp= items.retrieve().getAllData();
        
         while(!temp.last())
         {
            highest.insert(temp.retrieve().getItemId());
            temp.findNext();
         }
         highest.insert(temp.retrieve().getItemId());
         items.findNext();
      
      }
        LinkedList<Rating> temp= items.retrieve().getAllData();
        
         while(!temp.last())
         {
            highest.insert(temp.retrieve().getItemId());
            temp.findNext();
         }
         highest.insert(temp.retrieve().getItemId());

     
      double max =0;
      double num=0;
          
      highest.findFirst();
      while(!highest.last())
      { 
         num =getAverageItemRating(highest.retrieve());
         if(num> max)
            max=num;
      
         highest.findNext();
      }
   
      num =getAverageItemRating(highest.retrieve());
      if(num> max)
         max=num;
   
       //Find the Items that have the Avrege rating Max      
      highest.findFirst();
      while(!highest.last()){
      
         if(getAverageItemRating(highest.retrieve()) != max)
         {
            highest.remove();
            continue;
         }
            
         highest.findNext();
         
      }//end while
      if(getAverageItemRating(highest.retrieve()) != max)
         highest.remove();
   
      return highest; 
   
   }
      
      
      
        //-------------------------------------------------------------------------------------------
      //RAWAN
	// Return the average rating of item j. If i has no ratings, -1 is returned 
   public double getAverageItemRating(int j){
      double sum=0,count=0;
      LinkedList<Rating> itemRating;
      if(itemsTree.findKey(j))
         itemRating =itemsTree.retrieve().getAllData();
      else 
         return -1.0;
      itemRating.findFirst();
      
      if (itemRating.empty())
         return -1.0;
         
      while(!itemRating.last())
      {
         sum+=itemRating.retrieve().getValue();
         count++;
         itemRating.findNext();
      }
      sum+=itemRating.retrieve().getValue();
      count++;
   	
      return sum/count;
   
   }




      
  //-------------------------------------------------------------------------------------------
      //RAWAN
	// Return the average rating given by user i. If i has no ratings, -1 is returned 
   public double getAverageUserRating(int i){
   
      double sum=0,count=0;
      LinkedList<Rating> userRating;
   
      if(usersTree.findKey(i))
         userRating=usersTree.retrieve().getAllData();
      else 
         return -1.0;
         
      userRating.findFirst();
      
      if (userRating.empty())
         return -1.0;
         
      while(!userRating.last())
      {
         sum+=userRating.retrieve().getValue();
         count++;
         userRating.findNext();
      }
      sum+=userRating.retrieve().getValue();
      count++;
   	
      return sum/count;
   }
	
      
      
        //-------------------------------------------------------------------------------------------
      //RAZAN
	// Return the rating of user i for item j. If there is no rating, -1 is returned.
   public int getRating(int i, int j){
      if(usersTree.findKey(i)){
         BST<Rating> subTree=usersTree.retrieve(); 
         if(subTree.findKey(j))
            return subTree.retrieve().getValue();
      }
      return -1;
   }
   
   
     //-------------------------------------------------------------------------------------------
      //RAZAN
	// Return the number of keys to compare with in order to find the rating of user i for item j.
   public int nbComp(int i, int j){
   
      if(usersTree.findKey(i))
      {
      int numUserTree=usersTree.nbComp(i);
      
         BST<Rating> subTree=usersTree.retrieve(); 
         subTree.findKey(j);
         
            int numItemTree=usersTree.retrieve().nbComp(j);
            return numUserTree+numItemTree;
            
            }
            else
            return usersTree.nbComp(i);
         
      }
     



  //-------------------------------------------------------------------------------------------
   //NAWAL
	/* Compute the distance between the two users ui and uj. 
  If ui and uj have no common item in their ratings,
   then Double.POSITIVE_INFINITY is returned.*/ 
   public double getDist(int ui, int uj){
   
      BST<Rating> ri=null;
      BST<Rating> rj=null;
      
      int count =0;
      double result=0.0;
   
      if(usersTree.findKey(ui))
         ri = usersTree.retrieve();
      if(usersTree.findKey(uj))  
         rj = usersTree.retrieve();
    
      if(ri==null || rj==null)
         return 0.0;
    
      LinkedList<Rating> list_ri = ri.getAllData();
      LinkedList<Rating> list_rj = rj.getAllData();
    
    
     list_ri.findFirst();
     list_rj.findFirst();
      while(!list_ri.last())
      {
         Rating cur = list_ri.retrieve();
         if(rj.findKey(cur.getItemId()))
         {
            result+= Math.pow(rj.retrieve().getValue()-cur.getValue(),2);
            count++;
         }
         list_ri.findNext();
      }
      
       Rating cur = list_ri.retrieve();
         if(rj.findKey(cur.getItemId()))
         {
            result+= Math.pow(rj.retrieve().getValue()-cur.getValue(),2);
            count++;
         }


      if (result != 0.0)
         return Math.sqrt(result)/count;
      else
         return Double.POSITIVE_INFINITY;
    
   }
      
      
      
      
        //-------------------------------------------------------------------------------------------
      //RAWAN
	/* Return a list of at most k nearest neighbors to user i from a list of users.
    User i and users at infinite distance should not be included
     (the number of users returned can therefore be less than k).*/ 
   public LinkedList<Integer> kNNUsers(int i, LinkedList<Integer> users, int k){
   int c=0,m=0;
   double value=0;
   users.findFirst();
   while(!users.last()){
   c++;
   users.findNext();
   }
   c++;
   PQ<Integer> queue= new PQ<Integer>(c);
   LinkedList<Integer> list=new LinkedList<Integer>();
      users.findFirst();
    while(!users.last()){
  value=getDist(i,users.retrieve());
  if(value!=Double.POSITIVE_INFINITY){
  queue.enqueue(value,users.retrieve());
  m++;
  }
   users.findNext();
   }
    if(value!=Double.POSITIVE_INFINITY)
    {
    queue.enqueue(value,users.retrieve());
     m++;
     }
     
     
while (m>k && queue.length()!=0 ){
queue.serve();
m--;
}

while(k>0 && queue.length()!=0 )
{
list.insert(queue.serve().data);
      k--;
      }
      return list;}


     //-------------------------------------------------------------------------------------------

      //RAWAN
	/* Return the average rating given to item j by a list of users. 
   If the list users is empty or non of the users it contains rated item j, 
   then the global average rating of item j (as computed by getAverageItemRating(j)) is returned.*/ 
   public double getAverageRating(int j, LinkedList<Integer> users){
   double sum=0,count=0;
   int value=0;
   LinkedList<Integer> list=new LinkedList<Integer>();
   if (users.empty()){
   return getAverageItemRating(j);
   }
  

  
   users.findFirst();
   while(!users.last()){
   value = getRating(users.retrieve(),j);
   if (value!=-1)
   {list.insert(value);}
 
   users.findNext();
   }
   value = getRating(users.retrieve(),j);
  if (value!=-1)
   list.insert(value);
 
   if(list.empty())
   return getAverageItemRating(j);
   else
   {
   list.findFirst();
   while(!list.last()){
   sum+=list.retrieve();
   count++;
   list.findNext();
   }
    sum+=list.retrieve();
   count++;
      return sum/count;}
   }

   
     //-------------------------------------------------------------------------------------------
     
	// Return an estimation of the rating given by user i for item j using k nearest neighbor users.
   public double getEstimatedRating(int i, int j, int k) 
   {
      int r = getRating(i, j);
      if (r != -1) {
         return r;
      }
      LinkedList<Rating> ratings = getItemRatings(j);
      LinkedList<Integer> users = new LinkedList<Integer>();
      if ((ratings != null) && !ratings.empty()) {
         ratings.findFirst();
         while (!ratings.last()) {
            users.insert(ratings.retrieve().getUserId());
            ratings.findNext();
         }
         users.insert(ratings.retrieve().getUserId());
      }
      LinkedList<Integer> knn = kNNUsers(i, users, k);
      return getAverageRating(j, knn);
   }
}
