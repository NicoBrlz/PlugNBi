import { Component, OnInit } from '@angular/core';
import {RestFacadeService} from "../service/rest-facade.service";
import {TableInfoDTO, SalesDTO, ListingDTO, UserDTO, VenueDTO, CategoryDTO, DateDTO, EventDTO} from "../ext";

@Component({
  selector: 'app-test-rest',
  templateUrl: './test-rest.component.html'
})
export class TestRestComponent implements OnInit {

  public selectedTable: string = '';

  salesTableInfo: TableInfoDTO = {schema: { fields:[] }};
  salesTableInfoJson= '';
  salesFirstRows: SalesDTO[] = [];
  salesFirstRowsJson = '';

  listingTableInfo: TableInfoDTO = {schema: { fields:[] }};
  listingTableInfoJson= '';
  listingFirstRows: ListingDTO[] = [];
  listingFirstRowsJson = '';

  userTableInfo: TableInfoDTO = {schema: { fields:[] }};
  userTableInfoJson = '';
  userFirstRows: UserDTO[] = [];
  userFirstRowsJson = '';

  venueTableInfo: TableInfoDTO = {schema: { fields:[] }};
  venueTableInfoJson = '';
  venueFirstRows: VenueDTO[] = [];
  venueFirstRowsJson = '';

  categoryTableInfo: TableInfoDTO = {schema: { fields:[] }};
  categoryTableInfoJson= '';
  categoryFirstRows: CategoryDTO[] = [];
  categoryFirstRowsJson = '';

  dateTableInfo: TableInfoDTO = {schema: { fields:[] }};
  dateTableInfoJson= '';
  dateFirstRows: DateDTO[] = [];
  dateFirstRowsJson = '';

  eventTableInfo: TableInfoDTO = {schema: { fields:[] }};
  eventTableInfoJson = '';
  eventFirstRows: EventDTO[] = [];
  eventFirstRowsJson = '';

  fieldNames: string[]= [];

  constructor(private restFacadeService: RestFacadeService) {}

  ngOnInit(): void {
  }

  /*----------------------------------------------------------------------------------------*/
  onClickGetTableInfoSales() {
    this.restFacadeService.getTableInfoSales().subscribe({ next: res => {
        this.salesTableInfo = res;
        this.salesTableInfoJson = JSON.stringify(res, null, 2);
        console.log('done get Sales table info');
        if (res.schema?.fields) {
          this.fieldNames = res.schema.fields!.map(x => x.name!);
          console.log('fieldNames', this.fieldNames);
        }
      }, error: err => {
        console.log('Failed', err)
      }});
  }
  onClickGetTableInfoListing() {
    this.restFacadeService.getTableInfoListing().subscribe({ next: res => {
        this.listingTableInfo = res;
        this.listingTableInfoJson = JSON.stringify(res, null, 2);
        console.log('done get Listing table info');
        if (res.schema?.fields) {
          this.fieldNames = res.schema.fields!.map(x => x.name!);
          console.log('fieldNames', this.fieldNames);
        }
      }, error: err => {
        console.log('Failed', err)
      }});
  }
  onClickGetTableInfoUser() {
    this.restFacadeService.getTableInfoUser().subscribe({ next: res => {
        this.userTableInfoJson = JSON.stringify(res, null, 2);
        console.log('done get User table info');
      }, error: err => {
        console.log('Failed', err)
      }});
  }
  onClickGetTableInfoVenue() {
    this.restFacadeService.getTableInfoVenue().subscribe({ next: res => {
        this.venueTableInfoJson = JSON.stringify(res, null, 2);
        console.log('done get Venue table info');
      }, error: err => {
        console.log('Failed', err)
      }});
  }
  onClickGetTableInfoEvent() {
    this.restFacadeService.getTableInfoEvent().subscribe({ next: res => {
        this.eventTableInfoJson = JSON.stringify(res, null, 2);
        console.log('done get Event table info');
      }, error: err => {
        console.log('Failed', err)
      }});
  }
  onClickGetTableInfoCategory() {
    this.restFacadeService.getTableInfoCategory().subscribe({ next: res => {
        this.categoryTableInfoJson = JSON.stringify(res, null, 2);
        console.log('done get Category table info');
      }, error: err => {
        console.log('Failed', err)
      }});
  }
  onClickGetTableInfoDate() {
    this.restFacadeService.getTableInfoDate().subscribe({ next: res => {
        this.dateTableInfoJson = JSON.stringify(res, null, 2);
        console.log('done get Date table info');
      }, error: err => {
        console.log('Failed', err)
      }});
  }

  /*----------------------------------------------------------------------------------------*/
  //TOUTE CETTE PARTIE EST INUTILE??
  onClickQueryUser() {
    this.restFacadeService.queryFirstUser().subscribe({ next: res => {
      this.userFirstRows = res;
      this.userFirstRowsJson = JSON.stringify(res, null, 2);
      console.log('done query User first rows')
    }, error: err => {
      console.log('Failed', err)
    }});
  }
  onClickQueryVenue() {
    this.restFacadeService.queryFirstVenue().subscribe({ next: res => {
      this.venueFirstRows = res;
      this.venueFirstRowsJson = JSON.stringify(res, null, 2);
      console.log('done query Venue first rows')
    }, error: err => {
      console.log('Failed', err)
    }});
  }


  /*----------------------------------------------------------------------------------------*/
  onClickGetTableInfo() {
    switch(this.selectedTable) {
      case 'sales':
        this.restFacadeService.getTableInfoSales().subscribe({ next: res => {
          this.salesTableInfoJson = JSON.stringify(res, null, 2);
          console.log('done get Sales table info');
        }, error: err => {
          console.log('Failed', err)
        }});
        break;
      case 'listing':
        this.restFacadeService.getTableInfoListing().subscribe({ next: res => {
            this.listingTableInfoJson = JSON.stringify(res, null, 2);
            console.log('done get Listing table info');
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'user':
        this.restFacadeService.getTableInfoUser().subscribe({ next: res => {
          this.userTableInfoJson = JSON.stringify(res, null, 2);
          console.log('done get User table info');
        }, error: err => {
          console.log('Failed', err)
        }});
        break;
      case 'venue':
        this.restFacadeService.getTableInfoVenue().subscribe({ next: res => {
          this.venueTableInfoJson = JSON.stringify(res, null, 2);
          console.log('done get Venue table info');
        }, error: err => {
          console.log('Failed', err)
        }});
        break;
      case 'event':
        this.restFacadeService.getTableInfoEvent().subscribe({ next: res => {
            this.eventTableInfoJson = JSON.stringify(res, null, 2);
            console.log('done get Event table info');
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'category':
        this.restFacadeService.getTableInfoCategory().subscribe({ next: res => {
            this.categoryTableInfoJson = JSON.stringify(res, null, 2);
            console.log('done get Category table info');
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'date':
        this.restFacadeService.getTableInfoDate().subscribe({ next: res => {
            this.dateTableInfoJson = JSON.stringify(res, null, 2);
            console.log('done get Sales table info');
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
    }
  }
  onClickQuery() {
    switch(this.selectedTable) {
      case 'sales':
        this.restFacadeService.queryFirstSales().subscribe({ next: res => {
            this.salesFirstRows = res;
            this.salesFirstRowsJson = JSON.stringify(res, null, 2);
            console.log('done query Sales first rows')
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'listing':
        this.restFacadeService.queryFirstListing().subscribe({ next: res => {
            this.listingFirstRows = res;
            this.listingFirstRowsJson = JSON.stringify(res, null, 2);
            console.log('done query Listing first rows')
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'user':
        this.restFacadeService.queryFirstUser().subscribe({ next: res => {
            this.userFirstRows = res;
            this.userFirstRowsJson = JSON.stringify(res, null, 2);
            console.log('done query User first rows')
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'venue':
        this.restFacadeService.queryFirstVenue().subscribe({ next: res => {
            this.venueFirstRows = res;
            this.venueFirstRowsJson = JSON.stringify(res, null, 2);
            console.log('done query Venue first rows')
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'event':
        this.restFacadeService.queryFirstEvent().subscribe({ next: res => {
            this.eventFirstRows = res;
            this.eventFirstRowsJson = JSON.stringify(res, null, 2);
            console.log('done query Event first rows')
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'category':
        this.restFacadeService.queryFirstCategory().subscribe({ next: res => {
            this.categoryFirstRows = res;
            this.categoryFirstRowsJson = JSON.stringify(res, null, 2);
            console.log('done query Category first rows')
          }, error: err => {
            console.log('Failed', err)
          }});
        break;
      case 'date':
        this.restFacadeService.queryFirstDate().subscribe({ next: res => {
            this.dateFirstRows = res;
            this.dateFirstRowsJson = JSON.stringify(res, null, 2);
            console.log('done query Date first rows')
          }, error: err => {
            console.log('Failed', err)
          }});
        break;

    }
  }
}
