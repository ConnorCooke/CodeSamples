package Sorting;

public class SortValuesMessage {
    private Integer[] values;

    /**
     * Creates a SortValueMessage object
     * @param vals the values to be stored
     */
    public SortValuesMessage(Integer[] vals){
        this.values = vals;
    }

    /**
     * getter for values
     * @return values
     */
    public Integer[] getValues(){
        return this.values;
    }

}
