import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TestAttemptsComponent } from './test-attempts.component';

describe('TestAttemptsComponent', () => {
  let component: TestAttemptsComponent;
  let fixture: ComponentFixture<TestAttemptsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestAttemptsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestAttemptsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
