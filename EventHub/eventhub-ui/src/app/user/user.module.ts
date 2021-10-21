import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UsereventsComponent } from './userevents/userevents.component';

import {CarouselModule} from 'primeng/carousel';
import {ButtonModule} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import { MaterialModule } from '../shared/material/material.module';


@NgModule({
  declarations: [
    UsereventsComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    CarouselModule,
    ButtonModule,
    ToastModule,
    MaterialModule
  ]
})
export class UserModule { }
