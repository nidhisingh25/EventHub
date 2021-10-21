import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/service/service.service';
import { ActivatedRoute } from '@angular/router';
import { Event } from 'src/app/models/Event';
import { Category } from 'src/app/models/Category';
import Swal from 'sweetalert2'
import { CookieService } from 'ngx-cookie-service';
import { EventService } from 'src/app/service/event.service';
import { AddtofavoriteService } from 'src/app/service/addtofavorite.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-eventdetails',
  templateUrl: './eventdetails.component.html',
  styleUrls: ['./eventdetails.component.css']
})
export class EventdetailsComponent implements OnInit {

  categoryNameforIDEvent:string="";

  isAddedToFav: boolean = false;
  isEnrolled: boolean = false;

  constructor(private service:ServiceService,
    private route:ActivatedRoute,
    private cookieService: CookieService,
    private eventService : EventService,
    private addToFavService : AddtofavoriteService,
    private cdRef:ChangeDetectorRef) { }

  event:Event=new Event("","","","",new Date(),"","",0,"", new Category(),0);

  ngOnInit(): void {
    let eventId = this.route.snapshot.params["id"];
    this.service.getEventByIdFromDb(eventId).subscribe((data)=>
    {
      this.categoryNameforIDEvent=data.categoryDto.categoryName;
      this.event=data
      console.log(this.event);

    });

    this.addToFavService.getwishlistItems();
    this.eventService.getEnrolledEvents();
    this.service.getEventByIdFromDb(eventId).subscribe((data)=> {
      this.categoryNameforIDEvent=data.categoryDto.categoryName;
      this.event=data;
      this.isAddedToFav = this.addToFavService.isPresentinWish(this.event);
      this.isEnrolled = this.eventService.isAlreadyEnrolled(this.event);
    });
  }

  ngAfterViewInit() : void {
    this.cdRef.detectChanges();
  }

  enroll() {
    if(this.cookieService.check("user-token") == false) {
      Swal.fire('Oppss!!', "You're not logged in.. please log in to continue...", 'error');
    } else {
      const data = {
        "username" : JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub,
        "eventId" : this.event.eventId
      };
      this.eventService.enroll(data, this.cookieService.get("user-token")).subscribe(
        response => {
          this.isEnrolled = true;
          Swal.fire('Enroll', 'Succesfully Enrolled for the event', 'success');
        },
        error => {
          Swal.fire('Oppss!!', error.error.message, 'error');
        }
      );
    }
  }

  addToFav() {
    if(this.cookieService.check("user-token") == false) {
      Swal.fire('Oppss!!', "You're not logged in.. please log in to continue...", 'error');
      return;
    }
    if(this.addToFavService.isPresentinWish(this.event)) {
      Swal.fire('Oppss!!', "Item already added as favourite", 'error');
      return;
    }
    this.addToFavService.addToFavourite(this.event).subscribe(
      response=> {
        this.isAddedToFav=true;
        Swal.fire('Favorite', 'Succesfully added to your favorites', 'success');
      },
      error => {
        console.log(error);
        var errMsg="Some error has occured";
        if(error.error.message != null || error.error.message != undefined) {
          errMsg = error.error.message;
        }
        Swal.fire('Oppss!!', errMsg, 'error');
      }
    );
  }

  removeFromFav() {
    this.addToFavService.removeFromWishlist(this.event).subscribe(
      response=> {
        this.isAddedToFav = false;
      },
      error => {
        var errMsg="Some error has occured";
        if(error.error.message != null || error.error.message != undefined) {
          errMsg = error.error.message;
        }
        Swal.fire('Oppss!!', errMsg, 'error');
      }
    );
  }

  cancelEnrollment() {
    if(this.cookieService.check("user-token") == false) {
      Swal.fire('Oppss!!', "You're not logged in.. please log in to continue...", 'error');
      return;
    } else {
      const data = {
        "username" : JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub,
        "eventId" : this.event.eventId
      };
      this.eventService.cancelEnroll(data, this.cookieService.get("user-token")).subscribe(
        response => {
          this.isEnrolled = false;
        },
        error => {
          Swal.fire('Oppss!!', error.error.message, 'error');
        }
      );
    }

  }

}
