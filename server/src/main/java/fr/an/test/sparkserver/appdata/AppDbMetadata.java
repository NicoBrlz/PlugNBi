package fr.an.test.sparkserver.appdata;


import fr.an.exprlib.dto.specific.*;
import fr.an.exprlib.metadata.DatabaseInfo;
import fr.an.exprlib.metadata.TableInfo;
import fr.an.exprlib.metadata.TableInfoBuilder;

import java.util.Arrays;

public class AppDbMetadata {

    public static final TableInfo<UserDTO> User_ = new TableInfoBuilder<>("User", UserDTO.class,
            cols -> {
                // PrimaryKey column(s)
                cols.addInt("userId", UserDTO::getUserId);
                // othercolumns
                cols.addString("userName", UserDTO::getUserName);
                cols.addString("firstName", UserDTO::getFirstName);
                cols.addString("lastName", UserDTO::getLastName);
                cols.addString("city", UserDTO::getCity);
                cols.addString("state", UserDTO::getState);
                cols.addString("email", UserDTO::getEmail);
                cols.addString("phone", UserDTO::getPhone);
                cols.addBool("likeSports", UserDTO::getLikeSports);
                cols.addBool("likeTheatre", UserDTO::getLikeTheatre);
                cols.addBool("likeConcerts", UserDTO::getLikeConcerts);
                cols.addBool("likeJazz", UserDTO::getLikeJazz);
                cols.addBool("likeClassical", UserDTO::getLikeClassical);
                cols.addBool("likeOpera", UserDTO::getLikeOpera);
                cols.addBool("likeRock", UserDTO::getLikeRock);
                cols.addBool("likeVegas", UserDTO::getLikeVegas);
                cols.addBool("likeBroadway", UserDTO::getLikeBroadway);
                cols.addBool("likeMusicals", UserDTO::getLikeMusicals);
            },
            "",
            fks -> {}
    ).build();


    public static final TableInfo<EventDTO> Event_ = new TableInfoBuilder<>("Event", EventDTO.class,
            cols -> {
                // PrimaryKey column(s)
                cols.addInt("eventId", EventDTO::getEventId);
                // ForeignKey (dimension) columns
                cols.addInt("venueId", EventDTO::getVenueId);
                cols.addInt("catId", EventDTO::getCatId);
                cols.addInt("dateId", EventDTO::getDateId);
                // other columns
                cols.addString("eventName", EventDTO::getEventName);
                cols.addDateTime("startTime", EventDTO::getStartTime);
            },
            "",
            fks -> {
                fks.fk("venueId", "Venue", "venueId");
                fks.fk("catId", "Category", "catId");
                fks.fk("dateId", "Date", "dateId");
            }
    ).build();

    public static final TableInfo<CategoryDTO> Category_ = new TableInfoBuilder<>("Category", CategoryDTO.class,
            cols -> {
                // PrimaryKey column(s)
                cols.addInt("catId", CategoryDTO::getCatId);
                // other columns
                cols.addString("catGroup", CategoryDTO::getCatGroup);
                cols.addString("catName", CategoryDTO::getCatName);
                cols.addString("catDesc", CategoryDTO::getCatDesc);
            },
            "catId",
            fks -> {}
    ).build();

    public static final TableInfo<DateDTO> Date_ = new TableInfoBuilder<>("Date", DateDTO.class,
            cols -> {
                // PrimaryKey column(s)
                cols.addInt("dateId", DateDTO::getDateId);
                // other columns
                cols.addLocalDate("calDate", DateDTO::getCalDate);
                cols.addString("day", DateDTO::getDay);
                cols.addInt("week", DateDTO::getWeek);
                cols.addString("month", DateDTO::getMonth);
                cols.addString("qtr", DateDTO::getQtr);
                cols.addInt("year", DateDTO::getYear);
                cols.addBool("holiday", DateDTO::isHoliday);
            },
            "dateId",
            fks -> {}
    ).build();

    public static final TableInfo<VenueDTO> Venue_ = new TableInfoBuilder<>("Venue", VenueDTO.class,
            cols -> {
                // PrimaryKey column(s)
                cols.addInt("venueId", VenueDTO::getVenueId);
                // other columns
                cols.addString("venueName", VenueDTO::getVenueName);
                cols.addString("venueCity", VenueDTO::getVenueCity);
                cols.addString("venueState", VenueDTO::getVenueState);
                cols.addInt("venueSeats", VenueDTO::getVenueSeats);
            },
            "venueId",
            fks -> {}
    ).build();

    public static final TableInfo<ListingDTO> Listing_ = new TableInfoBuilder<>("Listing", ListingDTO.class,
            cols -> {
                // PrimaryKey column(s)
                cols.addInt("listId", ListingDTO::getListId);
                // FK columns
                cols.addInt("sellerId", ListingDTO::getSellerId);
                cols.addInt("eventId", ListingDTO::getEventId);
                cols.addInt("dateId", ListingDTO::getDateId);
                // other
                cols.addInt("numTickets", ListingDTO::getNumTickets);
                cols.addDouble("pricePerTicket", ListingDTO::getPricePerTicket);
                cols.addDouble("totalPrice", ListingDTO::getTotalPrice);
                cols.addDateTime("listTime", ListingDTO::getListTime);
            },
            "listId",
            fks -> {
                fks.fk("sellerId", "User", "userId");
                fks.fk("eventId", "Event", "eventId");
                fks.fk("dateId", "Date", "dateId");
            }
    ).build();

    public static final TableInfo<SalesDTO> Sales_ = new TableInfoBuilder<>("Sales", SalesDTO.class,
            cols -> {
                // PrimaryKey column(s)
                cols.addInt("salesId", SalesDTO::getSalesId);
                // ForeignKey (dimension) columns
                cols.addInt("listId", SalesDTO::getListId);
                cols.addInt("sellerId", SalesDTO::getSellerId);
                cols.addInt("buyerId", SalesDTO::getBuyerId);
                cols.addInt("eventId", SalesDTO::getEventId);
                cols.addInt("dateId", SalesDTO::getDateId);
                // measures columns
                cols.addInt("qtySold", SalesDTO::getQtySold);
                cols.addDouble("pricePaid", SalesDTO::getPricePaid);
                cols.addDouble("commission", SalesDTO::getCommission);
                cols.addDateTime("saleTime", SalesDTO::getSaleTime);
            },
            "salesId",
            fks -> {
                fks.fk("listId", "Listing", "listId");
                fks.fk("sellerId", "User", "userId");
                fks.fk("buyerId", "User", "userId");
                fks.fk("eventId", "Event", "eventId");
                fks.fk("dateId", "Date", "dateId");
            }
    ).build();

    public static final DatabaseInfo DB = new DatabaseInfo(
            Arrays.asList(
                    User_, Event_, Category_, Date_, Venue_, // dimension tables
                    Listing_, Sales_ // fact tables
            ),
            Arrays.asList()
    );

}
