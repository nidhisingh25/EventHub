import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EventRoutingModule } from './event-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';

import {CarouselModule} from 'primeng/carousel';
import {ButtonModule} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import { EventdetailsComponent } from './eventdetails/eventdetails.component';
import { MaterialModule } from '../shared/material/material.module';

@NgModule({
  declarations: [
    DashboardComponent,
    EventdetailsComponent

  ],
  imports: [
    CommonModule,
    EventRoutingModule,
    CarouselModule,
    ButtonModule,
    ToastModule,
    MaterialModule

  ]
})
export class EventModule { }
