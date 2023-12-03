package error_detection;
//import com.sun.xml.internal.ws.api.ha.StickyFeature;
import java.util.*;
import java.io.* ; 
public class Error_detection {
 public static void main(String[] args) throws FileNotFoundException{
      String path =   "C:\\Users\\user\\OneDrive\\Desktop\\XML_example.xml";
      ArrayList<Integer> linesWithErrors =new ArrayList<>(); 
      ArrayList<String> modifiedData = new ArrayList<>();
      ArrayList<Integer> errorsInQueue = new ArrayList<>();
//      linesWithErrors=errorLines(path) ;
//      printArrayListI(linesWithErrors, 0);
      System.out.println("-------------------------------------------");
      modifiedData=modifiedData(path); 
      printArrayListS(modifiedData);
      System.out.println("------------------------------");
      ArrayList<String> finalone = new ArrayList<>();
      finalone = modifiedDataFinal(modifiedData) ;
//      printArrayListS(finalone);
      ArrayList<Integer> finals = new ArrayList<>();
      finals = checkWordEquality(finalone);
      printArrayListI(finals,1);
//      printArrayListS(modifiedData);
//        Queue<String> dataOrganized = new LinkedList<>() ;
//       dataOrganized=createQueue(modifiedData) ; 
//           finalone = modifiedDataFinal(dataOrganized);
//       printQueue(dataOrganized);
//          dataOrganized = deleteInstances(dataOrganized) ;
//          errorsInQueue=checkForSingles(dataOrganized);
//          printArrayListI(errorsInQueue, 1);
 }
 
 public static ArrayList<Integer> errorLines(String PathFile) throws FileNotFoundException{
        String path = PathFile ; 
        File file = new File(path);
        Scanner scan = new Scanner(file) ; 
         String  line = "" ; 
         ArrayList<String> newData = new ArrayList<>() ; 
         int lineNumber = 1  ; 
         ArrayList<Integer> linesContainErrors = new ArrayList<> () ; 
        Stack<String> tag_stack = new Stack<>(); 
        while (scan.hasNextLine()){
                 line = scan.nextLine().trim();
                 String symbol="" ; 
                 if(line.contains("<") ||line.startsWith("</") && tag_stack.isEmpty()){
                     if (line.startsWith("</")) symbol = "</"  ; 
                     else symbol ="<" ;
                     tag_stack.push(symbol)  ;  
                    line = line.replaceFirst(symbol, "") ;
                 }
                  if (line.contains(">") && !tag_stack.isEmpty() && tag_stack.peek() == "<" ){
                     tag_stack.pop() ;
                    line = line.replaceFirst(">", " ") ;
                    }
                  
                 if(line.contains("</") && tag_stack.isEmpty() ){
                     symbol = "</" ;
                     tag_stack.push(symbol) ; 
                   line= line.replaceFirst(symbol," ") ;
                    }
                  if (line.contains(">") && !tag_stack.isEmpty()){
                      if(tag_stack.peek() == "</" ){
                     tag_stack.pop() ;
                    line =  line.replaceFirst(">", "") ;
                    }
                }
                  if(line.contains("<") || line.contains(">") || line.contains("</") )
                  {
                  linesContainErrors.add(lineNumber) ; 
                 line =  line.replaceAll("<", "");
                 line = line.replaceAll(">","")  ; 
                 line = line.replaceAll("</", "")  ;
                  }
                 newData.add(line) ; 
                 
                 lineNumber ++ ; 
                 
    }
printArrayListS(newData);
        printStack(tag_stack);
        printArrayListI(linesContainErrors, 0);
        /*
        System.out.println("----------------------------");
       printArrayListI(linesContainErrors,0);
    displayFile(new File(newPath)) ; 
    Queue<String> queue = new LinkedList<>();
    queue = createQueue(newData) ;
      printQueue(queue);
     System.out.println("--------------------------");
         queue=deleteInstances(queue) ;
     printQueue(queue);
     ArrayList<Integer> arr = checkErrors((List<String>)queue);
         printArrayListI(arr,1);
*/
 return linesContainErrors;
 
 }
 
 public static ArrayList<String> modifiedData(String PathFile)throws FileNotFoundException{
        ArrayList<String> newData = new ArrayList<>(); 
        String path = PathFile ; 
        File file = new File(path);
        Scanner scan = new Scanner(file) ;
         String  line = "" ; 
         while(scan.hasNextLine()){
         line = scan.nextLine().trim();
         if(line.startsWith("</")){
         line=line.replaceAll("<", "") ; 
         line=line.replaceAll("/", " ") ;
         line=line.replaceAll(">", "");
         newData.add(line.trim()); 
         }
         else{
         line=line.replaceAll("/", " ") ;
         line = line.replaceAll("<", "") ; 
         line = line.replaceAll(">", " ") ; 
         newData.add(line); 
            }
         }
         
         return newData; 
 }
 public static ArrayList<String> modifiedDataFinal(ArrayList<String> data) { 
            ArrayList<String> finalForm = new ArrayList<>();

  for(String element : data) {

    String[] words = element.split(" ");

    if(words.length == 1) {
      finalForm.add(words[0]);
    }
    else {
      String firstWord = words[0];
      String lastWord = words[words.length-1];

      String combined = firstWord + " " + lastWord;

      finalForm.add(combined);
    }

  }

  return finalForm;

 
 }
 public static ArrayList<Integer> checkWordEquality(ArrayList<String> finalForm) {

  ArrayList<Integer> indices = new ArrayList<>();

  for(int i = 0; i < finalForm.size(); i++) {

    String line = finalForm.get(i);

    if(line.contains("<")) {
      // Split on space and > to get tag name
      String[] parts = line.split(" |>");  
      String tag = parts[0];
      
      boolean found = false;
      
      for(int j=0; j<finalForm.size(); j++) {
        
        if(i != j) {
          String otherLine = finalForm.get(j);
          
          if(otherLine.contains(tag)) {
            found = true;
            break; 
          }
        }
      }
      
      if(!found) {
        indices.add(i);
      }
      
    } else { // for non-tags
      
      String[] words = line.split(" ");

      // existing single/double word logic
      
    }

  }

  return indices;
}
/*
 public static ArrayList<Integer> checkForSingles(Queue<String> queue) {

  ArrayList<Integer> singleIndices = new ArrayList<>();

  Map<String, Integer> counts = new HashMap<>();

  int i = 0;
  while(!queue.isEmpty()) {

    String element = queue.remove();
    
    counts.put(element, counts.getOrDefault(element, 0) + 1);
    
    i++;
  }

  int j = 0;
  while(!queue.isEmpty()) {

    String element = queue.remove();

    if(counts.get(element) == 1) {
      singleIndices.add(j);
    }

    j++;
  }

  return singleIndices;
}

  
 
 
 public static Queue<String> deleteInstances(Queue<String> queue) {
        Map<String, Integer> countMap = new HashMap<>();

        // Count occurrences of each element in the queue
        for (String element : queue) {
            countMap.put(element, countMap.getOrDefault(element, 0) + 1);
        }

        // Create a new queue to store the updated elements
        Queue<String> updatedQueue = new LinkedList<>();

        // Remove instances of elements that occur more than once
        while (!queue.isEmpty()) {
            String element = queue.poll();
            if (countMap.get(element) == 1) {
                updatedQueue.offer(element);
            }
        }

        return updatedQueue;
    }
    
  public static Queue<String> createQueue(List<String> data) {
            Queue<String> queue = new ArrayDeque<>();
//        ArrayList<String elements = new ArrayList<>();
        String elements[] ; 
        for(String element  : data) {
         elements =element.split(" ") ; 
            queue.offer(elements[0]); 
            if(elements.length>2)
            queue.offer(elements[elements.length-1]) ;
           elements = new String[0]; 
        }

        return queue;
    }
 
 public static void printQueue(Queue<String> queue) {
        System.out.println("Queue contents:");
        for (String element : queue) {
            System.out.println(element);
        }
    }
  private static void writeToFile(String filePath, String data, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            // Write the data to the file
            writer.write(data);
            writer.newLine() ; 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */ // may used latter   
    private static <String> void printStack(Stack<String> stack) {
        System.out.println("Stack elements:");

        Object[] array = stack.toArray();
        for (Object element : array) {
            System.out.println(element);
        }
    }
     public static void printArrayListI(ArrayList<Integer> list , int errorSource) {
        if(errorSource==0){
         System.out.println("Eror in line (due to error in < or >) :");
        for (Integer element : list) {
            System.out.println(element);
        }
        }
        else if(errorSource==1){
             System.out.println("Eror in line (due to error mismatch int tag name) :");
        for (Integer element : list) {
            System.out.println(element);
                }
        }
}
     public static void printArrayListS(ArrayList<String> list) {
//        System.out.println("Eror in line :");
        
        for (String element : list) {
            if(element.contains("/"))
                element.replaceAll("/", "");
            System.out.println(element);
        }
    }
    public static void displayFile(File file )throws FileNotFoundException{
    Scanner scan = new Scanner(file) ; 
    String line = "" ;
    while(scan.hasNextLine()){
        line = scan.nextLine().trim();
        System.out.println(line);
            }
    }
     private static void clearFileContents(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Writing an empty string to the file effectively clears its contents
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
}
