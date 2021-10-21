import { Injectable, resolveForwardRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Event } from '../models/Event';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  allEvents: Event[]= [];
  private headers:any;
  url = "http://localhost:9191/event";
 
  constructor(private http:HttpClient) {
    this.headers = [{name: 'Accept', value: 'application/json'}]
  }

  setAllEvents(events:Event[]){
    this.allEvents=events;

  }

  getPopularEvents(): Observable< Event[]> {
    return this.http.get<Event[]>('http://localhost:9191/event/popularevents');
  }

  getUpcomingEvents(): Observable< Event[]> {
    return this.http.get<Event[]>('http://localhost:9191/event/upcomingevents');
  }

  getAllEvents(){
    return this.allEvents;
  }

  getAllEventsFromDb():Observable<any>{
    return this.http.get(this.url+"/allevents");
  }


  getEventByIdFromDb(id:number):Observable<any>{
    return this.http.get("http://localhost:9191/event/eventid/"+id);
  }
  getAllCategoriesFromDb():Observable<any>{
    return this.http.get("http://localhost:9191/event/categories");


  }


  private listeners = new Subject<any>();
  listen(): Observable<any>{
    return this.listeners.asObservable();
  }
  filter(filterBy:string){
    this.listeners.next(filterBy);
  }

}
