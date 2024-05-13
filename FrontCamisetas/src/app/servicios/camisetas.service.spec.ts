/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CamisetasService } from './camisetas.service';

describe('Service: Camisetas', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CamisetasService]
    });
  });

  it('should ...', inject([CamisetasService], (service: CamisetasService) => {
    expect(service).toBeTruthy();
  }));
});
