import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { AgGridModule } from 'ag-grid-angular';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { ApiModule } from './ext';
import { Configuration } from './ext/configuration';
import { HomeComponent } from './home/home.component';
import { TablesComponent } from './tables/tables.component';
import { JoinsRelationshipsComponent } from './joins-relationships/joins-relationships.component';
import { QueryComponent } from './query/query.component';
import { TestRestComponent } from './test-rest/test-rest.component';
import {RestFacadeService} from "./service/rest-facade.service";

let baseHref = window.location.protocol + '//' + window.location.host;
console.log('baseHref (for redirecting Rest api calls): ' + baseHref);

let apiConfiguration = new Configuration({
  // calls to 'http://localhost:4200/api/*' are redirected to 'http://localhost:8080/api/*'
  // cf proxy.conf.json
  // otherwise direct calls fails with cors origin problem
  basePath: baseHref // 'http://localhost:4200'
});


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    TablesComponent,
    JoinsRelationshipsComponent,
    QueryComponent,
    TestRestComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule, ReactiveFormsModule,
    NgbModule,
    AgGridModule,
    ApiModule.forRoot(() => apiConfiguration),
  ],
  providers: [
    RestFacadeService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
