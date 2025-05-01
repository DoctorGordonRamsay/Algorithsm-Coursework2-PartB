package comp5017cw22025; // or whatever
/**
 * Interface to be implemented by class(es) that represent 
 * information about stations
 *
 * You may change the package name for this, but you should not
 * modify it in any other way.
 * @author D Lightfoot 2024
 */
public interface IPlaceInfo {
  
    /**
     * @return the name of the station
     */
    String getName();

    /**
     * @return easting
     */
    double getEasting();

    /**
     * return northing
     */
    double getNorthing();
    
    String toString();
}


