package comp5017cw22025;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;


/**
 * @author David Lightfoot 2025 sample answer to Coursework 2 Part B
 */
public class NetworkUtils implements INetworkUtils {


    /**
     * @param set of open places
     * @return index of place in open with lowest value in values
     * @pre open must not be empty
     */
    private int openStationWithMinValue(Network network, SetInt open, double[] values) {
        assert open.getSize() != 0 : "open must not be empty";
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
        assert 0 <= minIndex && minIndex < network.getNumPlaces() : " must be a valid station  number";
        return minIndex;
    }

    /**
     * @param network    -- the network
     * @param startIndex -- index of start station
     * @param endIndex   -- index of start station
     * @return list of station indexes in shortest path between startIndex and endIndex
     * @pre startIndex and endIndex are valid station indexes
     * @pre startIndex != endIndex
     */
    public ListInt dijkstraPath(Network network, int startIndex, int endIndex) {
        assert 0 <= startIndex && startIndex < network.getNumPlaces() : " start must be a valid station  number";
        assert 0 <= endIndex && endIndex < network.getNumPlaces() : " end must be a valid station  number";
        assert startIndex != endIndex : "start and index must be different";

        int numplaces = network.getNumPlaces();
        // set Closed to be empty
        SetInt closed = new SetInt(numplaces);

        // add all nodes in the graph to Open.
        SetInt open = new SetInt(numplaces);

        for (int i = 0; i < numplaces; i++) {
            open.include(i);
        }

        // set the g-value of Start to 0, and the g-value of all the other nodes to ∞
        double[] Gvalue = new double[numplaces];

        for (int i = 0; i < numplaces; i++) {
            Gvalue[i] = network.NO_LINK;
        }

        Gvalue[startIndex] = 0;

        // set previous to be none for all nodes.
        int[] previous = new int[numplaces];
        for (int i = 0; i < numplaces; i++) {
            previous[i] = -1;
        }

        // while End is not in Closed do
        while (!closed.contains(endIndex)) {

            // let X be the node in Open  that has the lowest g-value (highest priority)
            int x = openStationWithMinValue(network, open, Gvalue);

            // remove X from Open and add it to Closed.
            open.exclude(x);
            closed.include(x);

            // if X is not equal to End then
            if (x != endIndex) {

                //for each node N that is adjacent to X in the graph, and also in Open do
                for (int N = 0; N < numplaces; N++) {
                    if (network.getDistance(x, N) != network.NO_LINK && open.contains(N)) {
                        //let g’  = g-value of X + cost of edge from X to N
                        double G = Gvalue[x] + network.getDistance(x, N);

                        //if g’ is less than the current g-value of N then
                        if (G < Gvalue[N]) {
                            Gvalue[N] = G;
                            previous[N] = x;
                        }//endif
                    }
                } //endfor

            } // endif
        } // endwhile

        // reconstruct the shortest path from Start to End by following "previous" pointers
        // to find the previous node to End, the previous node to that previous node, and so on
        ListInt path = new ListInt(15);
        int current = endIndex;
    
        while (current != -1) {
            path.append(current);
            current = previous[current];
        }
        
        // Reverse the path to get from start to end
        ListInt result = new ListInt(15);
        for (int i = path.getSize() - 1; i >= 0; i--) {
            result.append(path.get(i));
        }
        
        return result;
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
       assert 0 <= startIndex && startIndex < network.getNumPlaces() : " start must be a valid station  number";
       assert 0 <= endIndex && endIndex < network.getNumPlaces() : " end must be a valid station  number";
       assert startIndex != endIndex : "start and index must be different";

       int numplaces = network.getNumPlaces();

        // set Closed to be empty
        SetInt closed = new SetInt(numplaces);

        // add all nodes in the graph to Open.
        SetInt open = new SetInt(numplaces);
        for (int i = 0; i < numplaces; i++) {
            open.include(i);
        }

        // set the g-value of Start to 0, and the g-value of all the other nodes to ∞
        double[] Gvalue = new double[numplaces];
        for (int i = 0; i < numplaces; i++) {
            Gvalue[i] = network.NO_LINK;
        }
        Gvalue[startIndex] = 0;

        // set previous to be none for all nodes.
        int[] previous = new int[numplaces];
        for (int i = 0; i < numplaces; i++) {
            previous[i] = -1;
        }

        double[] heuristic = new double[numplaces];
        for (int i = 0; i < numplaces; i++) {
            IPlaceInfo place = network.getPlaceInfo(i);
            IPlaceInfo endPlace = network.getPlaceInfo(endIndex);

            double easting = place.getEasting() - endPlace.getEasting();
            double northing = place.getNorthing() - endPlace.getNorthing();
            heuristic[i] = Math.sqrt(easting * easting + northing * northing);
        }

        double[] fvalue = new double[numplaces]; // (f-value is g-value + the heuristic value for that node)
        for (int i = 0; i < numplaces; i++) {
            if (i == startIndex) {
                fvalue[i] = Gvalue[i] + heuristic[i];
            } else {
                fvalue[i] = network.NO_LINK;
            }
        }

        // while End is not in Closed do
        while(!closed.contains(endIndex)) {
    
            // let X be the node in Open  that has the lowest f-value (highest priority) 
            int x = openStationWithMinValue(network, open, fvalue);

        
        
            // remove X from Open and add it to Closed.
            open.exclude(x);
            closed.include(x);

            // if X is not equal to End then
            if(x != endIndex) {

                //for each node N that is adjacent to X in the graph, and also in Open do
                for (int N = 0; N < numplaces; N++) {
                    if (network.getDistance(x, N) != network.NO_LINK && open.contains(N)){
            
                        //let g’  = g-value of X + cost of edge from X to N
                        double G = Gvalue[x] + network.getDistance(x, N);

                        //if g’ is less than the current g-value of N then 
                        if (G < Gvalue[N]) {

                             //change the g-value of N to g’  Update f-value of N
                            Gvalue[N] = G;
                            fvalue[N] = G + heuristic[N];

                             //make its previous pointer point to X
                            previous[N] = x;

                        } // endif
                    }
                } //endfor

            } //endif

        } //endwhile
    
      // reconstruct the shortest path from Start to End by following "previous" pointers
        // to find the previous node to End, the previous node to that previous node, and so on
        ListInt path = new ListInt(15);
        int current = endIndex;
    
        while (current != -1) {
            path.append(current);
            current = previous[current];
        }
        
        // Reverse the path to get from start to end
        ListInt result = new ListInt(15);
        for (int i = path.getSize() - 1; i >= 0; i--) {
            result.append(path.get(i));
        }
        
        return result;
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
