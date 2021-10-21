import { Component, OnInit } from '@angular/core';
import { EventService } from '../../service/event.service';
import { Event } from 'src/app/models/Event';
import { PageEvent } from '@angular/material/paginator';
import { ServiceService } from 'src/app/service/service.service';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { AddtofavoriteService } from 'src/app/service/addtofavorite.service';

@Component({
  selector: 'app-userevents',
  templateUrl: './userevents.component.html',
  styleUrls: ['./userevents.component.css']
})
export class UsereventsComponent implements OnInit {

  enrolled: Event[] = [];
  wishlist: Event[] = [];
  created: Event[]=[];

  responsiveOptions : any = [];


  constructor(private eventService:EventService,private wishlistService:AddtofavoriteService)
  {
    this.responsiveOptions = [
      {
        breakpoint: '1024px',
        numVisible: 3,
        numScroll: 3,

      },
      {
        breakpoint: '768px',
        numVisible: 2,
        numScroll: 2,
      },
      {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1,
      },
    ];

  }

  ngOnInit() {
    this.eventService.getEnrolledEvents().subscribe(res => {this.enrolled=res

    });
    this.wishlistService.getwishlistItems().subscribe(res => this.wishlist=res);
 
    this.wishlistService.getCreatedEvents().subscribe(res => this.created=res);

  }

  navigateToEventDeatilsPage(){

  }

  getRegistrationStatus(event:Event){
      
    if(event.eventDate < new Date())
      return "Event closed";
    else if(event.enrollSeats==event.enrolledSeats)
      return "All seats filled"
    else
      return "Registration Open"
  }
}
