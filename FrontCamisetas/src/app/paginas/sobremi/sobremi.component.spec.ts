/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SobremiComponent } from './sobremi.component';

describe('SobremiComponent', () => {
  let component: SobremiComponent;
  let fixture: ComponentFixture<SobremiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SobremiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SobremiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
