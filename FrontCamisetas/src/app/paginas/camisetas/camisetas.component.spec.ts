/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CamisetasComponent } from './camisetas.component';

describe('CamisetasComponent', () => {
  let component: CamisetasComponent;
  let fixture: ComponentFixture<CamisetasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CamisetasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CamisetasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
