import { Injectable } from '@angular/core';
import { HttpClient,HttpResponse,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  http : HttpClient;

  constructor(http: HttpClient) {
    this.http = http;
  }

  register(data: any): Observable<any> {
    return this.http.post<any>('http://localhost:9191/user/authenticate',data);
  }

}
