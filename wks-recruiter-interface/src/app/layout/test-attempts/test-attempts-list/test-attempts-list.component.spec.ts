import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TestAttemptsListComponent } from './test-attempts-list.component';

describe('TestAttemptsListComponent', () => {
  let component: TestAttemptsListComponent;
  let fixture: ComponentFixture<TestAttemptsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestAttemptsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestAttemptsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
