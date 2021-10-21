import { Component, OnInit } from '@angular/core';
import { Event } from 'src/app/models/Event';
import { ServiceService } from '../../service/service.service';

import { EventService } from 'src/app/service/event.service';
import { Category } from 'src/app/models/Category';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

    upcomingEvents: Event[] = [];
    popularEvents: Event[] = [];

    responsiveOptions : any = [];

    categories:Category[]=[];

    constructor(private service:ServiceService, private eventService:EventService, private router:Router)
    {

      this.service.listen().subscribe((m:any) =>this.ngOnInit() )
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
      this.service.getPopularEvents().subscribe(data => {this.popularEvents=data;});
      this.service.getUpcomingEvents().subscribe(data => {this.upcomingEvents=data;});
      this.service.getAllCategoriesFromDb().subscribe(data => {this.categories=data;});
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

    reloadCurrentRoute() {
      let currentUrl = this.router.url;
      this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
          this.router.navigate([currentUrl]);
      });
  }

  }


