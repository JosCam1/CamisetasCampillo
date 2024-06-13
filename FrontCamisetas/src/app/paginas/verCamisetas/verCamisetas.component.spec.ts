/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { VerCamisetasComponent } from './verCamisetas.component';

describe('VerCamisetasComponent', () => {
  let component: VerCamisetasComponent;
  let fixture: ComponentFixture<VerCamisetasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerCamisetasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerCamisetasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
