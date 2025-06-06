package comp5017cw22025; // or whatever
import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author David Lightfoot 2025-01
 */
public class COMP5017CW22025 {
   
   static  Scanner kb = new Scanner(System.in);
  
/**
     * @return null, -non-empty string
     * @post non-empty string has been read
     */
   private static String readNonEmpty() {
      // read non-null, non-empty string;
      String s = kb.nextLine().trim();
      while (s == null || s.equals("")) {
         System.out.print("must not be blank -- try again: ");
         s = kb.nextLine().trim();
      }
      assert s != null && !s.trim().equals("");
      return s;
   }
   
/**
     * @param nt -- the network
     * @param path -- a list of place indexes
     * @pre length of path >= 2;
     * @post list of place names displayed
     */
   private static void showList(Network nt, ListInt path) {
      assert path.getSize() >= 2 : 
              " showList: path must contain at least two places";
      IPlaceInfo st;
      for (int i = 0; i < path.getSize() - 1; i++) {
         int pos = path.get(i);
         st = nt.getPlaceInfo(pos);
         System.out.print(st.getName() + " --> ");
      }
       int pos = path.get(path.getSize() - 1);
       st = nt.getPlaceInfo(pos );
       System.out.println(st.getName());
   }
   
   /**
     * @param nt -- the network
     * @param path -- a list of place indexes
     * @pre length of path >= 2;
     * @return length of route given by path
     */
   private static double routeLength(Network nt, ListInt path) {
      assert path.getSize() >= 2: 
              " routeLength: path must contain at least two places";
      double length = 0;
      for (int step = 1; step != path.getSize(); step++) {
         length += nt.getDistance(path.get(step - 1), path.get(step));
      }
      return length;
   }
         
   public static void main(String[] args){
    Network network = new Network(/*number of places*/ 10);
    NetworkUtils netUtilObject = new NetworkUtils();
     String option, startName,endName;
     int startIndex, endIndex;
     ListInt result;
     File f = getDataFile(".");
     try {network.readFileStream(new FileInputStream(f));}
     catch (IOException ex) {
            System.out.println("Can't open chosen file" );
        }
     
     
     System.out.print("P)rint dijK)stra A)star bruteF)orce Q)uit? ");
    option = readNonEmpty();
    while(option.charAt(0)!= 'Q' && option.charAt(0)!= 'q') {
        switch (option.charAt(0)) {
            case 'P': case'p':  // print network
              network.display();
              break;
           
           
           case 'K':
           case 'k':  // Dijkstra path
              System.out.print("Dijkstra path ");
              System.out.print("start place? ");
              int numStations = network.getNumPlaces();
              startName = readNonEmpty();
              startIndex = network.indexOfPlace(startName);
              if (startIndex == numStations) {
                 System.out.println("unknown start place");
              } else {
                 System.out.print("end place? ");
                 endName = readNonEmpty();
                 endIndex = network.indexOfPlace(endName);
                 if (endIndex == numStations) {
                    System.out.println("unknown end place");
                 } else if (startIndex == endIndex) {
                    System.out.println("pointless route " + startName + " to " + endName);
                 } else {
                    result = netUtilObject.dijkstraPath(network, startIndex, endIndex);
                    showList(network, result);
                     System.out.println("Dijkstra route length = " + routeLength(network, result));
                 }
              }
              break;

           case 'A':
           case 'a':  // A* path
              System.out.print("A* path ");
              System.out.print("start place? ");
              numStations = network.getNumPlaces();
              startName = readNonEmpty();
              startIndex = network.indexOfPlace(startName);
              if (startIndex == numStations) {
                 System.out.println("unknown start place");
              } else {
                 System.out.print("end place? ");
                 endName = readNonEmpty();
                 endIndex = network.indexOfPlace(endName);
                 if (endIndex == numStations) {
                    System.out.println("unknown end place");
                 } else if (startIndex == endIndex) {
                    System.out.println("pointless route " + startName + " to " + endName);
                 } else {
                    result = netUtilObject.aStarPath(network, startIndex, endIndex);
                    showList(network, result);
                    System.out.println("A* route length = " + routeLength(network, result));
                 }
              }
              break;
           case 'F':
           case 'f': // Brute-forcc path
              System.out.print("Brute-force path ");
              System.out.print("start place? ");
              numStations = network.getNumPlaces();
              startName = readNonEmpty();
              startIndex = network.indexOfPlace(startName);
              if (startIndex == numStations) {
                 System.out.println("unknown start place");
              } else {
                 System.out.print("end palla? ");
                 endName = readNonEmpty();
                 endIndex = network.indexOfPlace(endName);
                 if (endIndex == numStations) {
                    System.out.println("unknown end palce");
                 } else if (startIndex == endIndex) {
                    System.out.println("pointless route " + startName + " to " + endName);
                 } else {    
                    result = netUtilObject.bruteForce(network, startIndex, endIndex);
                    showList(network, result);
                    System.out.println("Brute-force route length = " + routeLength(network, result));
                 }
              }
              break;
            default: //?
              System.out.println("unknown option");

        } // switch
        System.out.print("P)rint dijK)stra A)star bruteF)orce Q)uit? ");
        option = readNonEmpty();                    
     } // while
  }
   /**
     * Allows the user to select a file containing a description of a graph. If
     * no file is selected, the program terminates.
     *
     * @param startFolder the folder that the file chooser should start from
     * @return the file that was selected
     */
    public static File getDataFile(String startFolder) {
        File f;
        // setting up a dialogue box,
        // to select a file containing data
        JFileChooser fc = new JFileChooser(startFolder);
        fc.setDialogTitle("Choose a file containing network data ");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int fcReturnValue = fc.showOpenDialog(null);

        // now, which file did the user select?
        if (fcReturnValue != JFileChooser.APPROVE_OPTION) {
            // user must have cancelled, or an error occurred
            System.out.println("No file selected.");
            f = null;
           //System.exit(0);
            // f = fc.getSelectedFile();
        } else { // user selected a file ok
            System.out.println("input from file.");
            f = fc.getSelectedFile();
        }
        return f;
    }
}
