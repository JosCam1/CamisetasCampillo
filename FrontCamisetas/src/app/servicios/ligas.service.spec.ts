/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { LigasService } from './ligas.service';

describe('Service: Ligas', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LigasService]
    });
  });

  it('should ...', inject([LigasService], (service: LigasService) => {
    expect(service).toBeTruthy();
  }));
});
