package fr.an.exprlib.dto.specific;

import lombok.Data;

@Data
public class UserDTO {

    // PK
    int userId;

    // other
    String userName;
    String firstName;
    String lastName;
    String city;
    String state;
    String email;
    String phone;
    Boolean likeSports;
    Boolean likeTheatre;
    Boolean likeConcerts;
    Boolean likeJazz;
    Boolean likeClassical;
    Boolean likeOpera;
    Boolean likeRock;
    Boolean likeVegas;
    Boolean likeBroadway;
    Boolean likeMusicals;

}
