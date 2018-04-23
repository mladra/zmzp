import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestsCreateComponent } from './tests-create.component';

describe('TestsCreateComponent', () => {
  let component: TestsCreateComponent;
  let fixture: ComponentFixture<TestsCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestsCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestsCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
