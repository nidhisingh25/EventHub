import { Injectable, OnInit } from '@angular/core';
import { Event } from 'src/app/models/Event';
import { Observable, Subject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AddtofavoriteService implements OnInit {

  public wishlistItems: Event[]=[];
  instantarray: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient,
    private cookieService: CookieService) { }

  ngOnInit(): void {
    this.getwishlistItems();
  }

  addToFavourite(event:Event) {
    this.addToWishlist(event);
    const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + this.cookieService.get("user-token"));
    const data = {
      "eventId" : event.eventId,
      "username":JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub
    }
    return this.http.post<any>('http://localhost:9191/event/addtofavourite',data, { 'headers': headers });

  }

  addToWishlist(event:Event) {
    this.wishlistItems.push(event);
    this.instantarray.next();
  }

  isPresentinWish(event:Event)
  {
    for(let wishcourse of this.wishlistItems){
      if(wishcourse.eventId==event.eventId){
        return true;
      }
    }

    return false;

  }
  getwishlistItems():Observable<any> {
    if(this.cookieService.check("user-token") == true) {
      const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + this.cookieService.get("user-token"));
      return this.http.get<any>('http://localhost:9191/event/favourite/user/'+JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub, { 'headers': headers })
    }
    return new Observable();
  }

  removeFromWishlist(event:Event) {
    const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + this.cookieService.get("user-token"));
    const data = {
      "eventId" : event.eventId,
      "username":JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub
    }
    return this.http.post<any>('http://localhost:9191/event/removefavourite',data, { 'headers': headers });
  }

  getCreatedEvents(){
    if(this.cookieService.check("user-token") == true) {
      const headers= new HttpHeaders()
      .set('Authorization',"Bearer " + this.cookieService.get("user-token"));
      return this.http.get<any>('http://localhost:9191/event/created/'+JSON.parse(atob(this.cookieService.get("user-token").split('.')[1])).sub, { 'headers': headers })
    }
    return new Observable();
    
  }

  changeAfterRemove(event:Event) {
    var index: number = -1;
    for (var i = 0; i < this.wishlistItems.length; i++) {
      if (this.wishlistItems[i].eventId === event.eventId) {
          index = i;
      }
    }
    if (index !== -1) {
      this.wishlistItems.splice(index, 1);
    }
    this.instantarray.next();
  }
}
