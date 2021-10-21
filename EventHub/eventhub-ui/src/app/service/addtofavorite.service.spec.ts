import { TestBed } from '@angular/core/testing';

import { AddtofavoriteService } from './addtofavorite.service';

describe('AddtofavoriteService', () => {
  let service: AddtofavoriteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddtofavoriteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
