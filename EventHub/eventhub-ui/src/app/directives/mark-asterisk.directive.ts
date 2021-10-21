import { Directive,OnInit, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  selector: '[required]'
})
export class MarkAsteriskDirective implements OnInit{

  constructor(
    private renderer: Renderer2,
    private el: ElementRef
    ) {}

    ngOnInit() {
    }
}
