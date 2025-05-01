package comp5017cw22025;

/**
 *
 * @author D Lightfoot 2025-01
 */
public class PlaceInfo implements IPlaceInfo{
     private final String name;
     private final double easting;
     private final double northing;
     
     public PlaceInfo(String name, double easting, double y){
         this.name = name; this.easting = easting; this.northing = y;
     }
     /**
     * @return the name of the station
     */
    @Override
    public String getName() {return name;}

    /**
     * @return easting 
     */
     @Override
    public double getEasting(){return easting;}

    /**
     * @return northing position 6
     */
     @Override
     public double getNorthing()
     {return northing;}
     
     @Override 
     public String toString(){
       return name + " " + easting + ", " + northing;
     }
}
