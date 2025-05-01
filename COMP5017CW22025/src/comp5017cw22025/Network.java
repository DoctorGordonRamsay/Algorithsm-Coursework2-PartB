package comp5017cw22025;

/**
 *
 * @author D Lightfoot 2025-01
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Network {
    public  final double NO_LINK = Double.MAX_VALUE; // "infinity"
    public  final int MAX_PLACES;
    private   int numPlaces; // 0 <= numPlaces <= MAX_PLACES
    private  final IPlaceInfo [] places ;
    private  final double [][] distances;
    
    /**
     * @param x0, y0, x1, y1
     * @pre true
     * @return straight-line distance between (x0, y0) and (x1, y1)
     */
    public  double pythogoras(double x0, double y0, double x1, double y1){
     return Math.sqrt((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0));
  }
  
 public Network (int capacity ){
    MAX_PLACES = capacity;
    places = new PlaceInfo[MAX_PLACES];
    numPlaces = 0;
    distances = new double[MAX_PLACES][MAX_PLACES];
   // make all distances initially NO_LINK
        for (int i = 0; i != MAX_PLACES; i++) {
            for (int j = 0; j != MAX_PLACES; j++) {
                distances[i][j] = NO_LINK;
            }
        }
 }
 
 /**
     * @param none
     * @pre true
     * @return number of places
     */public int getNumPlaces(){return numPlaces;}
 
/**
     * @param i the index in the place array
     * @pre i must be in range 0..getNumStations()-1
     * @return placeInfo at index i
     */
 public IPlaceInfo getPlaceInfo(int i) {
    assert 0 <= i && i < numPlaces;
   return places[i];
 }
   /**
     * @param name the place name to locate
     * @pre name is not null and not empty string
     * @return index of name in list of place or numPlaces if not found
     */
     public int indexOfPlace(String name) {
       assert name != null & !name.trim().equals(""): " name must not be null or empty string";
        int i = 0;
        while (i != numPlaces && !name.equals(places[i].getName())) i++;
        return i;
    }
     
 protected void readFileStream(FileInputStream fStream) throws IOException {
        Scanner scan = new Scanner(fStream);
        while (scan.hasNext()) {
            String word = scan.next();

            if (word.equals("place")) {
                if (numPlaces == MAX_PLACES) {
                    throw new IOException("too many places");
                }
                String name = scan.next();
                double easting = scan.nextDouble();
                double northing = scan.nextDouble();
//                if (!(0 <= easting && x < 256 && 0 <= y && y < 256)) {
//                    throw new IOException(" x and/or y not in range");
//                }
//                if (indexOfPlace(name) != numPlaces) {
//                    throw new IOException("place name " + name + " already defined");
//                }
                PlaceInfo s = new PlaceInfo(name, easting, northing);
                places[numPlaces] = s;
                numPlaces++;

            } else if (word.equals("link")) {
                String fromName = scan.next();
                int fromIndex = indexOfPlace(fromName);
                if (fromIndex == numPlaces) {
                    throw new IOException("place name " + fromName + " not defined");
                }
                String toName = scan.next();
                int toIndex = indexOfPlace(toName);
                if (toIndex == numPlaces) {
                    throw new IOException("place name " + toName + " not defined");
                }
                if (fromIndex == toIndex) {
                    throw new IOException("loop for place " + toName);
                }
                double distance = scan.nextDouble();
                if (distance <= 0.0) {
                    throw new IOException("distance must not be negative");
                }
                distances[fromIndex][toIndex] = distance;
                distances[toIndex][fromIndex] = distance;
                System.out.println(fromName + " -> "+ toName + ": " + distance);

            } else {
                throw new IOException("Syntax Error in File");
            }
        }
    }
    
  /**
     * @param filename -- name of input text file
     * @pre filename is a valid well formed file of network information
     * @post network has been loaded from file
     */
   public void readNetwork(String filename){
        try {
            System.out.println("Reading file " + filename);
            readFileStream(new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("reading of file " + filename + " failed");
            System.out.print(e);
        }
  }
  
 /**
     * @post shows information held in network
     */
   public void display( ) {
     System.out.println("places");
        for (int i = 0; i != numPlaces; i++) {
            System.out.println(i + ": " + places[i].toString());
        }
        System.out.println("links");
        
    for (int i = 0; i != numPlaces; i++) {
            for (int j = 0; j != numPlaces; j++) {
                System.out.print(" " + i + " " + j + " ");
                if (distances[i][j] == NO_LINK) {
                    System.out.print("--  ");
                } else {
                    System.out.print(distances[i][j]);
                }
            }
            System.out.println();
        }
 }
 
   /**
     * @param fromIndex, toIndex indexes in the place array
     * @pre fromIndex, toIndex must be in range 0..getNumStations()-1
     * @return recorded distance between place at fromIndex and place at toIndex
     */
     public double getDistance(int fromIndex, int toIndex) {
      assert 0 <= fromIndex && fromIndex < numPlaces
              && 0 <= toIndex && toIndex < numPlaces;
      return distances[fromIndex][toIndex];
   }
}