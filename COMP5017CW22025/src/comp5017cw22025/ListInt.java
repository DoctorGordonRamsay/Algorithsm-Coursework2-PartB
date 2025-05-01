package comp5017cw22025; // or whatever

/**
 *
 * @author D Lightfoot 2024
 */
public class ListInt  {
    private  int list[];
    private int size; // 0 <= size <= capacity
    private final int capacity;
    /**
     * @param capacity -- maximum capacity of this list
     * @post new list of current size zero has been created
     */
    public ListInt(int capacity) {
        // implements a bounded list of int values
       assert capacity > 0;
         this.capacity = capacity;
        this.size = 0;
        list = new int[capacity];
    }
    public int getCapacity() {return capacity;}
    public int getSize() {return size;}
    
/**
     * @param i index
     * @pre 0 <= i && i < getSize()
     * @return value in list at index i
     */
    public int get(int i) {
        assert 0 <= i && i < getSize();
        return list[i];
    }
    /**
     * @param n node to be added
     * @pre getSize() != getCapacity()
     * @post n has been appended to list
     */
    public void append(int n) {
        assert getSize() != getCapacity();
        list[size++] = n;
    }
public void remove(int val){
    int pos = 0;
    int n = getSize();
    while (pos != n && list[pos] != val) pos++;
    if(pos != n) { // val found at pos
        int j = pos;
        while (j < n - 1) {
          list[j] = list[j+1];
          j++;
        }
        size--;
    }
}
//    public void deleteStudent(int pos) {
//  int i = pos;
//  while (i<numStudents-1) { 
//    students[i] = students[i+1]; // copy down
//    i++;
//  } // i == numModules-1
//  numStudents--; // one less now
//}
    /**
     * @param x -- value to be sought
     * @pre true
     * @return true iff x is in list
     */
    public boolean contains(int x) {
        int i = 0;
        while (i != getSize() && list[i] != x) i++;
        return i != getSize();
    }
    
    @Override
    public String toString() {
       /**
     * @return list as a string
     */
        String s = "";
        int i = 0;
        while (i != size) {
            s += list[i] + ", ";  i++;
        }
        return s;
    }
}

