import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { EventService } from '../../service/event.service';
import { Event } from 'src/app/models/Event';
import { ServiceService } from 'src/app/service/service.service';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { AddtofavoriteService } from 'src/app/service/addtofavorite.service';
import { Category } from 'src/app/models/Category';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  events:Event[]=[];
 categoryname : string = "";

  public config: any;
  defaultElevation = 2;
  raisedElevation = 8;
  centered = false;
  disabled = false;
  unbounded = false;
  categories:Category[]=[];

  radius: number=0;
  color: string="";
  length: number = 0;
  public title:string="";
  numofEventsperPage:number=6;

  constructor(private eventService:EventService, private http:HttpClient,
    private route:ActivatedRoute, private modalService: NgbModal,private router:Router,
    private favService:AddtofavoriteService, private service:ServiceService,
    private cdRef:ChangeDetectorRef) {
    this.title="";

  }


  ngOnInit(): void {

    this.service.getAllCategoriesFromDb().subscribe( res => this.categories=res);
    this.categoryname = this.route.snapshot.params["category"];

    if(this.categoryname == "upcoming"){
      this.service.getUpcomingEvents().subscribe( (res) => {
        this.events = res;
      })
    }
    else if(this.categoryname == "popular"){
      this.service.getPopularEvents().subscribe( (res) => {
        this.events = res;
      })
    }
    else{
      this.eventService.getEventsByCategory(this.categoryname).subscribe( (res) => {
        this.events=res;
      })
    }
    this.eventService.instantarray.subscribe(()=>{
      this.events=this.eventService.getItems();
    });
    this.config = {
      itemsPerPage: this.numofEventsperPage,
      currentPage: 1,
      totalItems: this.length
    };

    this.length = this.events.length;
    this.favService.getwishlistItems();
  }


  ngAfterViewInit() : void {
    this.cdRef.detectChanges();
  }

  pageChanged(event: any){
    this.config.currentPage = event;
  }


  Search(){
    if(this.title==""){
      this.ngOnInit();
    }
    else{
      this.events = this.events.filter(res=>{
        return res.eventName.toLocaleLowerCase().match(this.title.toLocaleLowerCase());
      })
    }
  }


  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  doStuff() {
    console.log("i am in dostuff");
  }

  sorttitle:string = 'eventName';

reverse:boolean=false;

sort(prc:string) {
  this.sorttitle=prc;
  this.reverse=!this.reverse;
}

closeResult:string="";
open(contentshare:any) {
  this.modalService.open(contentshare, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
    this.closeResult = `Closed with: ${result}`;
  }, (reason) => {
    this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
  });
}

moveToWishlist(event:any,eventDetail:Event)
{
  this.favService.addToFavourite(eventDetail).subscribe(
    response=> {
      event.target.style.color = 'rgb(223, 50, 19)';
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

removeFromWishlist(event:any,eventDetail:Event)
{
  this.favService.removeFromWishlist(eventDetail).subscribe(
    response=> {
      event.target.style.color = 'rgb(170, 169, 169)';
    },
    error => {
      var errMsg="Some error has occured";
      if(error.error.message != null || error.error.message != undefined) {
        errMsg = error.error.message;
      }
      Swal.fire('Oppss!!', errMsg, 'error');
    }
  );
  this.favService.changeAfterRemove(eventDetail);
}

inWhishList(event:Event)
{
  return this.favService.isPresentinWish(event);
}
routeToUpcoming(){
  // this.router.navigate('/RefreshComponent', ["home/displayEvents/upcoming"]);

  this.router.navigateByUrl('/RefreshComponent', { skipLocationChange: true }).then(() => {
    this.router.navigate(['home/displayEvents/upcoming']);
});
}

routeToPopular(){
  // this.router.navigate('/RefreshComponent', ["home/displayEvents/upcoming"]);

  this.router.navigateByUrl('/RefreshComponent', { skipLocationChange: true }).then(() => {
    this.router.navigate(['home/displayEvents/popular']);
});
}


routeToCategory(category:string){
  // this.router.navigate('/RefreshComponent', ["home/displayEvents/upcoming"]);

  this.router.navigateByUrl('/RefreshComponent', { skipLocationChange: true }).then(() => {
    this.router.navigate(['home/displayEvents/'+category]);
});
}

navigateToEventDetails(id:number){
  // this.router.navigate('/RefreshComponent', ["home/displayEvents/upcoming"]);

  this.router.navigateByUrl('/RefreshComponent', { skipLocationChange: true }).then(() => {
    this.router.navigate(['home/eventDetails/'+id]);
});
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
