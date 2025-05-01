package comp5017cw22025;

import java.util.ArrayList;

/**
 * @author David Lightfoot 2025 sample answer to Coursework 2 Part B
 */
public class NetworkUtils implements INetworkUtils {

  
   
  /**
     * @pre open must not be empty
     * @param set of open places
     * @return index of place in open with lowest value in values
     */    
     private int openStationWithMinValue(Network network, SetInt open, double[] values) {
       assert open.getSize() != 0: "open must not be empty";
        int minIndex = -1;
        double minValue = network.NO_LINK;
        for (int i = 0; i != network.getNumPlaces(); i++) {
            if (open.contains(i)) {
                if (values[i] < minValue) {
                    minIndex = i;
                    minValue = values[i];
                }
            }
        }
        assert 0 <= minIndex && minIndex < network.getNumPlaces(): " must be a valid station  number";
        return minIndex;
    }
     
     /**
     * @param network -- the network
     * @param startIndex -- index of start station
     * @param endIndex -- index of start station
     * @pre startIndex and endIndex are valid station indexes
     * @pre startIndex != endIndex
     * @return list of station indexes in shortest path between startIndex and endIndex
     */
   public ListInt dijkstraPath(Network network, int startIndex, int endIndex) {
        
        return null;
    }
   
   
   /**
     * @param network -- the network
     * @param startIndex -- index of start station
     * @param endIndex -- index of start station
     * @pre startIndex and endIndex are valid station indexes
     * @pre startIndex != endIndex
     * @return list of station indexes in shortest path between startIndex and endIndex
     */
   public ListInt aStarPath(Network network, int startIndex, int endIndex) {    
        
        return null;
    }
   
   
    private void printAllPathsUtil(ArrayList<ListInt> allPaths, Network network, int u, int d,
            boolean[] isVisited,
            ListInt localPathList) {
       // System.out.println("|Entering printAllAPths");
        if (u == d) {
           // System.out.println(localPathList);
          //  System.out.println(localPathList.getSize());
            ListInt path = new ListInt(100);
            for (int i = 0; i != localPathList.getSize(); i++) 
                path.append(localPathList.get(i));
            allPaths.add(path);
            return;
        }
        isVisited[u] = true;
        for (int i = 0; i != network.getNumPlaces(); i++) {
            if (network.getDistance(i, u) != network.NO_LINK) {
                if (!isVisited[i]) {
                    localPathList.append(i);
                    //System.out.println("Appended " + localPathList);
                    printAllPathsUtil(allPaths, network, i, d, isVisited, localPathList);
                    localPathList.remove(i);
                    //System.out.println("Removed " + localPathList);
                }
            }
        }
        isVisited[u] = false;
    }

    private double pathlength(Network network, ListInt path){
        double len = 0;
        int i = 1;
        while (i != path.getSize()) {
            System.out.println(network.getDistance(path.get(i), path.get(i-1)));
            len += network.getDistance(path.get(i), path.get(i-1)); 
            i++;
        }
        return len;
    }
    
    
    /**
     * @param network -- the network
     * @param startIndex -- index of start station
     * @param endIndex -- index of start station
     * @pre startIndex and endIndex are valid station indexes
     * @pre startIndex != endIndex
     * @return list of station indexes in shortest path between startIndex and
     * endIndex
     */
    public ListInt bruteForce(Network network, int startIndex, int endIndex) {
        assert 0 <= startIndex && startIndex < network.getNumPlaces() : " start must be a valid station  number";
        assert 0 <= endIndex && endIndex < network.getNumPlaces() : " end must be a valid station  number";
        assert startIndex != endIndex : " po,intless route";
        boolean[] isVisited = new boolean[network.getNumPlaces()];
        ArrayList<ListInt> allPaths = new ArrayList<ListInt>();
        ListInt pathList = new ListInt(network.MAX_PLACES * 20);
        System.out.println("Entering printAllAPthsBrute force");
        pathList.append(startIndex);
        printAllPathsUtil(allPaths, network, startIndex, endIndex, isVisited, pathList);
        System.out.println("number of paths " + allPaths.size());
        //System.out.println(allPaths);
        for (int i = 0; i != allPaths.size(); i++) {
            System.out.println(allPaths.get(i).toString());
        }
        double minPathLength = pathlength(network, allPaths.get(0));
        int pos = 0;
        int i = 1;
        while (i < allPaths.size()) {
             System.out.println("path length " + pathlength(network, allPaths.get(i)));
            if (pathlength(network, allPaths.get(i)) < minPathLength) {
                minPathLength = pathlength(network, allPaths.get(i));
               // System.out.println("path length " + minPathLength);
                pos = i;        
            }
            i++;
        }
        return allPaths.get(pos);
    }

    
   } // NetworkUtils