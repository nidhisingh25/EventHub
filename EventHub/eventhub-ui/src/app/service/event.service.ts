import { Injectable, OnInit} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable,Subject } from 'rxjs';
import { Event } from '../models/Event';
import { Category } from '../models/Category';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class EventService implements OnInit {

  public enrolledEvenets: Event[]=[];

  saveImage(token:string, image:FormData, eventId: number) {
    const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + token);
    return this.http.post<any>('http://localhost:9191/event/image/'+eventId,image, { 'headers': headers });
  }

  getEventsByCategory(category: string) {
    return this.http.get<Event[]>('http://localhost:9191/event/category/'+category);
  }

  http : HttpClient;
  instantarray: Subject<void> = new Subject<void>();
  constructor(private cookieService: CookieService,http: HttpClient) {
    this.http = http;
  }
  ngOnInit(): void {
    this.getEnrolledEvents();
  }

  register(data: any, token: string): Observable<any> {
    console.log(data);

    const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + token);
    return this.http.post<any>('http://localhost:9191/event/addevent',data, { 'headers': headers });
  }

  Events:Event[]=[];
  Category:Category[]=[];

  findAll(): Observable< Event[]> {
    return this.http.get<Event[]>('http://localhost:9191/event/allevents');
  }

  getCategory(): Observable<Category[]> {
    return this.http.get<Category[]>('http://localhost:9191/event/categories');
  }

  findEventsbyUser(id:any):Observable<any[]>{
    return this.http.get<any[]>('http://localhost:9191/event/getevents/'+id);
  }

  getItems() {
    return this.Events;
  }

  getCategoryItems()
  {
    console.log(this.Category);
    for(let a of this.Category)
      console.log(a);

    return this.Category;
  }

  enroll(data : any, token : any): Observable<any> {
    const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + token);
    return this.http.post<any>('http://localhost:9191/event/enroll',data, { 'headers': headers });
  }

  cancelEnroll(data : any, token : any): Observable<any>  {
    const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + token);
    return this.http.post<any>('http://localhost:9191/event/cancelenroll',data, { 'headers': headers });
  }

  getEnrolledEvents():Observable<any> {
    let headers = new HttpHeaders().set('Authorization',"Bearer " + this.cookieService.get("user-token"));
    if(this.cookieService.check("user-token") == true) {
      headers= new HttpHeaders().set('Authorization',"Bearer " + this.cookieService.get("user-token"));
      let data = this.http.get<any>('http://localhost:9191/event/enroll/user/'+JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub, { 'headers': headers });
      console.log(data);
      return data;
      
    }
    return this.http.get<any>('http://localhost:9191/event/enroll/user/'+JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub, { 'headers': headers });
    
  }

  isAlreadyEnrolled(eventToCheck:Event) {
    for(let event of this.enrolledEvenets) {
      if(event.eventId==eventToCheck.eventId) {
        return true;
      }
    }
    return false;
  }
}
