import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon'
import {MatListModule} from '@angular/material/list';

import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';

import {MatRadioModule} from '@angular/material/radio';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCheckboxModule} from '@angular/material/checkbox';

import {MatPaginatorModule} from '@angular/material/paginator';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker'

import {MatNativeDateModule} from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';

import { NgxPaginationModule } from 'ngx-pagination';
import {MatRippleModule} from '@angular/material/core';



// Made a list of components to be exported
const MaterialComponents = [
  MatCardModule,
  MatListModule,
  MatButtonModule,
  MatIconModule,
  MatInputModule,
  MatDialogModule,
  MatRadioModule,
  MatFormFieldModule,
  MatCheckboxModule,
  NgxMaterialTimepickerModule,
  MatPaginatorModule,
  MatDatepickerModule,
  NgxPaginationModule,
  MatPaginatorModule,
  MatRippleModule,
  MatNativeDateModule

];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MaterialComponents,
    NgxPaginationModule
  ],
  exports: [
    MaterialComponents,

  ]
})
export class MaterialModule { }
