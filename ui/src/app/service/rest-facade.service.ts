import { Injectable } from '@angular/core';
import {TableInfoDTO, SalesDTO, SalesRestService, ListingDTO, ListingRestService, UserDTO, UserRestService, VenueDTO, VenueRestService, CategoryDTO, CategoryRestService, DateDTO, DateRestService, EventDTO, EventRestService} from "../ext";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RestFacadeService {

  constructor(
    private salesRestService: SalesRestService,
    private listingRestService: ListingRestService,
    private userRestService: UserRestService,
    private venueRestService: VenueRestService,
    private eventRestService: EventRestService,
    private categoryRestService: CategoryRestService,
    private dateRestService: DateRestService
  ) { }

  /*----------------------------------------------------------------------------------------*/
  getTableInfoSales(): Observable<TableInfoDTO> {
    console.log('Getting info for sales table');
    return this.salesRestService.tableInfo2();
  }
  queryFirstSales(limit?: number): Observable<SalesDTO[]> {
    return this.salesRestService.first2(limit);
  }
  queryAllSales(): Observable<SalesDTO[]> {
    return this.salesRestService.list2();
  }

  /*----------------------------------------------------------------------------------------*/
  getTableInfoListing(): Observable<TableInfoDTO> {
    console.log('Getting info for listing table');
    return this.listingRestService.tableInfo3();
  }
  queryFirstListing(limit?: number): Observable<ListingDTO[]> {
    return this.listingRestService.first3(limit);
  }
  queryAllListing(): Observable<ListingDTO[]> {
    return this.listingRestService.list3();
  }

  /*----------------------------------------------------------------------------------------*/
  getTableInfoUser(): Observable<TableInfoDTO> {
    console.log('Getting info for user table');
    return this.userRestService.tableInfo1();
  }
  queryFirstUser(limit?: number): Observable<UserDTO[]> {
    return this.userRestService.first1(limit);
  }
  queryAllUser(): Observable<UserDTO[]> {
    return this.userRestService.list1();
  }

  /*----------------------------------------------------------------------------------------*/
  getTableInfoVenue(): Observable<TableInfoDTO> {
    console.log('Getting info for venue table');
    return this.venueRestService.tableInfo();
  }
  queryFirstVenue(limit?: number): Observable<VenueDTO[]> {
    return this.venueRestService.first(limit);
  }
  queryAllVenue(): Observable<VenueDTO[]> {
    return this.venueRestService.list();
  }

  /*----------------------------------------------------------------------------------------*/
  getTableInfoEvent(): Observable<TableInfoDTO> {
    console.log('Getting info for event table');
    return this.eventRestService.tableInfo4();
  }
  queryFirstEvent(limit?: number): Observable<EventDTO[]> {
    return this.eventRestService.first4(limit);
  }
  queryAllEvent(): Observable<EventDTO[]> {
    return this.eventRestService.list4();
  }

  /*----------------------------------------------------------------------------------------*/
  getTableInfoCategory(): Observable<TableInfoDTO> {
    console.log('Getting info for category table');
    return this.categoryRestService.tableInfo6();
  }
  queryFirstCategory(limit?: number): Observable<CategoryDTO[]> {
    return this.categoryRestService.first6(limit);
  }
  queryAllCategory(): Observable<CategoryDTO[]> {
    return this.categoryRestService.list6();
  }

  /*----------------------------------------------------------------------------------------*/
  getTableInfoDate(): Observable<TableInfoDTO> {
    console.log('Getting info for date table');
    return this.dateRestService.tableInfo5();
  }
  queryFirstDate(limit?: number): Observable<DateDTO[]> {
    return this.dateRestService.first5(limit);
  }
  queryAllDate(): Observable<DateDTO[]> {
    return this.dateRestService.list5();
  }
}
