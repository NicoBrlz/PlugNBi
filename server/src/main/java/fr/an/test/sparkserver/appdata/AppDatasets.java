package fr.an.test.sparkserver.appdata;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public record AppDatasets(
        Dataset<Row> userDs,
        Dataset<Row> eventDs,
        Dataset<Row> categoryDs,
        Dataset<Row> dateDs,
        Dataset<Row> venueDs,
        Dataset<Row> listingDs,
        Dataset<Row> salesDs
) {

    public Dataset<Row> datasetByName(String name) {
        switch(name) {
            case "User":
                return userDs;
            case "Event":
                return eventDs;
            case "Category":
                return categoryDs;
            case "Date":
                return dateDs;
            case "Venue":
                return venueDs;
            case "Listing":
                return listingDs;
            case "Sales":
                return salesDs;
            default:
                throw new IllegalArgumentException("dataset not found by name '" + name + "'"
                        + ", expecting one of {User,Event,Category,Date,Venue,Listing,Sales}");
        }
    }

}
