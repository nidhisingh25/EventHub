import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddtofavoriteComponent } from './event/addtofavorite/addtofavorite.component';
import { EventdetailsComponent } from './event/eventdetails/eventdetails.component';
import { EventsComponent } from './event/events/events.component';
import { Event } from './models/Event';

const routes: Routes = [

  {
    path: 'home',
    loadChildren: () => import('./event/event.module').then(mod => mod.EventModule)
  },

  {
    path: 'user',
    loadChildren: () => import('./user/user.module').then(mod => mod.UserModule)
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
