import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AppDbRestControllerService } from './api/appDbRestController.service';
import { CategoryRestService } from './api/categoryRest.service';
import { DateRestService } from './api/dateRest.service';
import { DispatchGenericQueryRestControllerService } from './api/dispatchGenericQueryRestController.service';
import { EventRestService } from './api/eventRest.service';
import { ListingRestService } from './api/listingRest.service';
import { QueryRestControllerService } from './api/queryRestController.service';
import { SalesRestService } from './api/salesRest.service';
import { UserRestService } from './api/userRest.service';
import { VenueRestService } from './api/venueRest.service';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports:      [ FormsModule ],
  declarations: [],
  exports:      [],
  providers: [
    AppDbRestControllerService,
    CategoryRestService,
    DateRestService,
    DispatchGenericQueryRestControllerService,
    EventRestService,
    ListingRestService,
    QueryRestControllerService,
    SalesRestService,
    UserRestService,
    VenueRestService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders<ApiModule> {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
