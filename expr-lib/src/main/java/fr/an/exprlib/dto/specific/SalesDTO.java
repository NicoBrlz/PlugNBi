package fr.an.exprlib.dto.specific;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class SalesDTO {

    public int salesId;

    public int listId;
    public int sellerId;
    public int buyerId;
    public int eventId;
    public int dateId;
    public int qtySold;
    public double pricePaid;
    public double commission;
    public LocalDateTime saleTime;

}
