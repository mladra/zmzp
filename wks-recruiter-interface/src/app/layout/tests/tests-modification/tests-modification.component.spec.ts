import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestsModificationComponent } from './tests-modification.component';

describe('TestsModificationComponent', () => {
  let component: TestsModificationComponent;
  let fixture: ComponentFixture<TestsModificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestsModificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestsModificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
