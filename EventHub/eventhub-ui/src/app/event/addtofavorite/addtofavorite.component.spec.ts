import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddtofavoriteComponent } from './addtofavorite.component';

describe('AddtofavoriteComponent', () => {
  let component: AddtofavoriteComponent;
  let fixture: ComponentFixture<AddtofavoriteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddtofavoriteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddtofavoriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
