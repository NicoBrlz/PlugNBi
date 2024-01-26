import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from "./home/home.component";
import {JoinsRelationshipsComponent} from "./joins-relationships/joins-relationships.component";
import {QueryComponent} from "./query/query.component";
import {TablesComponent} from "./tables/tables.component";
import {TestRestComponent} from "./test-rest/test-rest.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'tables', component: TablesComponent },
  { path: 'joins-relationships', component: JoinsRelationshipsComponent },
  { path: 'query', component: QueryComponent },
  { path: 'test-rest', component: TestRestComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
