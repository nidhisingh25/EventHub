import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsereventsComponent } from './userevents/userevents.component';

const routes: Routes = [
  {path:'', component:UsereventsComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
