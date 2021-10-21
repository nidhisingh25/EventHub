import { Time } from "@angular/common";
import { Category } from "./Category";

export class Event {
  public eventId:number=0;


  constructor(
    public eventName: string,
    public eventImage: string,
    public eventDesc: string,
    public eventRules: string,
    public eventDate: Date,
    public eventStartTime: string,
    public eventEndTime: string,
    public enrollSeats: number,
    public zoomlink: string,
    public category: Category,
    public enrolledSeats: number
  ) {
  }


}

