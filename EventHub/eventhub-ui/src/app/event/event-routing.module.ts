import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EventdetailsComponent } from './eventdetails/eventdetails.component';
import { EventsComponent } from './events/events.component';

const routes: Routes = [
  {path:'', component:DashboardComponent},
  {path:'eventDetails/:id', component:EventdetailsComponent, pathMatch:'full'},
  {path:'displayEvents/:category',component:EventsComponent, pathMatch:'full'},
  {path:'dashboard',component:DashboardComponent, pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventRoutingModule { }
