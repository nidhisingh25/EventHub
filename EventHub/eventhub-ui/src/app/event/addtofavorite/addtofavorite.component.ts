import { Component, OnInit } from '@angular/core';
import { AddtofavoriteService } from 'src/app/service/addtofavorite.service';
import { Event } from 'src/app/models/Event';
import { SharedModule } from 'src/app/shared/shared.module';
import { Subject } from 'rxjs';
@Component({
  selector: 'app-addtofavorite',
  templateUrl: './addtofavorite.component.html',
  styleUrls: ['./addtofavorite.component.css']
})
export class AddtofavoriteComponent implements OnInit {

  favItems:Event[]=[];
  constructor(private service:AddtofavoriteService) { }

  instantarray: Subject<void> = new Subject<void>();
  ngOnInit(): void {

    this.service.getwishlistItems().subscribe( res => this.favItems=res);
  

    console.log(this.favItems);
  }


  removeFromWishlist(event:Event)
  {
      this.service.removeFromWishlist(event);

  }


}
