import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UserService } from '../../service/user.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup,FormControl, Validators,FormBuilder } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import Swal from 'sweetalert2'
import { EventService } from 'src/app/service/event.service';
import { ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { DatePipe, getLocaleDateTimeFormat } from '@angular/common';
import { Event } from 'src/app/models/Event';
import { Category } from 'src/app/models/Category';
import { ChangeDetectorRef } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { ServiceService } from 'src/app/service/service.service';


const pad = (i: number): string => i < 10 ? `0${i}` : `${i}`;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

 private re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

 isAuthenticated : boolean = false;

 eventcategory:Category[]=[];
   //"Art","Technology","Entertainment","Games"];

   enamePattern = "^[A-Za-z0-9]";

 token: string = "";
 username: string = "";
  @ViewChild('userSpan')
  userSpan!: ElementRef;
  event: Event=new Event("","","","",new Date(),"","",0,"",new Category(),0);
  eventImage: File = new File([],"");

  eventId:number=0;

  editForm = new FormGroup({
    eventname: new FormControl(null, [Validators.required,Validators.pattern(this.enamePattern)]),
    eventimage: new FormControl(null, Validators.required),
    eventdesc: new FormControl(null, Validators.required),
    eventrules: new FormControl(null, Validators.required),
    eventdate: new FormControl(null, Validators.required),
    enrollseats: new FormControl(null, [Validators.required,Validators.pattern('^[0-9]')]),
    eventstarttime:new FormControl(null, Validators.required),
    eventendtime:new FormControl(null, Validators.required),
    zoomlink: new FormControl(null, Validators.required),
    categoryname: new FormControl(null, Validators.required)
  });

  closeResult:string="";
  constructor(
    private service:ServiceService,
    private userService : UserService,
    private modalService: NgbModal,
    private cookieService: CookieService,private fb:FormBuilder,
    private eventService:EventService,private httpClient:HttpClient,
    private datepipe:DatePipe,
    private cdRef:ChangeDetectorRef) {


    }

  loginForm = new FormGroup({
    username: new FormControl(null,
      [Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")]),
    password: new FormControl(null, Validators.required)
  });

  ngOnInit(): void {
    this.eventService.getCategory().subscribe((res) => {
      this.eventcategory=res;
   });
   if(this.cookieService.check("user-token") == true) 
     console.log("inside");
     
    this.username=JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub;
    console.log(this.username);
     
  }

  ngAfterViewInit() : void {
    if(this.cookieService.check("user-token") == true) {
      this.userSpan.nativeElement.innerHTML = JSON
        .parse(atob(this.cookieService.get("user-token")
        .split('.')[1])).sub;
        this.isAuthenticated = true;
        this.token=this.cookieService.get("user-token");
        this.username=JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub;
    }
    this.cdRef.detectChanges();
  }



  navbarCollapsed = true;

  toggleNavbarCollapsing() {
      this.navbarCollapsed = !this.navbarCollapsed;
  }

  authenticate() {
    if(this.isLoginFormValid()) {
      const data = {
        "username" : this.loginForm.value.username,
        "password" : this.loginForm.value.password
      };
      this.loginForm.reset();
      
      this.userService.register(data).subscribe(
        successResponse => {
          if(successResponse.isAuthenticated) {
            this.cookieService.set("user-token", successResponse.token);
            Swal.fire('Hi', successResponse.message, 'success');
            this.userSpan.nativeElement.innerHTML = JSON.parse(atob(successResponse.token.split('.')[1])).sub;
            this.isAuthenticated = true;
          }
        },
        errorResponse => {
          if(errorResponse.error.status == 401) {
            Swal.fire('Oopss!!!', errorResponse.error.message, 'error');
          } else {
            Swal.fire('Oopss!!!', 'Some internal error has ocured', 'error');
          }
        }
      );
    }
  }

  logout(event : Event) {
    this.cookieService.delete("user-token");
    this.userSpan.nativeElement.innerHTML = "Login";
    this.isAuthenticated = false;
    Swal.fire('Logout', 'Succesfully logged out', 'success');
  }

  openBackDropCustomClass(content : any) {
    if(this.re.test(this.userSpan.nativeElement.innerHTML) ==  false) {
      this.modalService.open(content, {backdropClass: 'light-blue-backdrop'});
    }
  }

  private isLoginFormValid() {
    if(this.loginForm.value.username == null || this.loginForm.value.username == '') {
      alert("Username Cannot be null");
      return false;
    }
    if(this.loginForm.value.password == null || this.loginForm.value.password == '') {
      alert("Password Cannot be null");
      return false;
    }
    if(this.re.test(this.loginForm.value.username.toLowerCase()) ==  false) {
      alert("Please enter valid email address");
      return false;
    }
    return true;
  }

  private isEventFormValid() {

    if(this.editForm.value.eventname == null || this.editForm.value.eventname == '') {
      alert("Eventname Cannot be null");
      return false;
    }
    if(this.editForm.value.eventdate == null || this.editForm.value.eventdate == '') {
      alert("Event date Cannot be null");
      return false;
    }
    if(this.editForm.value.eventdate == null || this.editForm.value.eventdate == '') {
      alert("Event date Cannot be null");
      return false;
    }

    return true;
  }

  open(contentEdit:any) {

    this.modalService.open(contentEdit, {size:'lg', ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  opentime(contenttime:any)
  {
    this.modalService.open(contenttime, {backdropClass: 'light-blue-backdrop'});
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

  selection:string=this.getSelect();
  getSelect()
  {
    return this.selection;
  }

  date=new Date();
  latest_date =this.datepipe.transform(this.date, 'yyyy-MM-dd');
  today: number = Date.now();


  openSave(event: Event) {
   this.editForm.value.categoryname=this.selection;
   this.editForm.value.eventdate=this.datepipe.transform(this.editForm.value.eventdate, 'yyyy-MM-dd');
    const data = {
      "eventCreationDate" : this.latest_date,
      "eventName" : this.editForm.value.eventname,
      "eventImageUrl" : this.editForm.value.eventimage,
      "eventDesc" : this.editForm.value.eventdesc,
      "eventRules" : this.editForm.value.eventrules,
      "eventDate" : this.editForm.value.eventdate,
      "eventStartTime": this.editForm.value.eventstarttime,
      "eventEndTime": this.editForm.value.eventendtime,
      "enrollSeats" : this.editForm.value.enrollseats,
      "zoomlink" : this.editForm.value.zoomlink,
      "categoryDto": {
        "categoryName":this.editForm.value.categoryname
       },
      "ownerUsername" : this.username,
      "enrolledSeats":0
    };
    this.editForm.reset();
    this.eventService.register(data,this.token).subscribe(
      successResponse => {
        this.eventId=successResponse;
        this.saveImageOfEvent();
          Swal.fire('Event Added ',successResponse.message, 'success');
      },
      errorResponse => {
        if(errorResponse.error.status == 401) {
          Swal.fire('Oopss!!!', errorResponse.error.message, 'error');
        } else {
          Swal.fire('Oopss!!!', 'Some internal error has ocured', 'error');
        }
      });
      this.service.filter("close");
  }

  storeImage(event:any) {
    this.eventImage = <File>event.target.files[0];
  }

  saveImageOfEvent(){
    const formData = new FormData();
    formData.append('image',this.eventImage);
    this.eventService.saveImage(this.token, formData, this.eventId).subscribe( res => {});
  }
}

