import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './event/dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EventModule } from './event/event.module';
import { UserService } from './service/user.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EventService } from './service/event.service';
import { EventsComponent } from './event/events/events.component';
import { MaterialModule } from './shared/material/material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DatePipe } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { SharedModule } from './shared/shared.module';
import { Ng2OrderModule } from 'ng2-order-pipe';
import { AddtofavoriteComponent } from './event/addtofavorite/addtofavorite.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    EventsComponent,
    AddtofavoriteComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    EventModule,
    HttpClientModule,
    NoopAnimationsModule,
    FormsModule,
    MaterialModule,
    ReactiveFormsModule,
    NgbModule,
    MatInputModule,
    MaterialModule,
    MatFormFieldModule,
    FlexLayoutModule,
    Ng2OrderModule,
    MatButtonModule
  ],
  providers: [UserService,EventService,DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
