/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { MarcasService } from './marcas.service';

describe('Service: Marcas', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MarcasService]
    });
  });

  it('should ...', inject([MarcasService], (service: MarcasService) => {
    expect(service).toBeTruthy();
  }));
});
