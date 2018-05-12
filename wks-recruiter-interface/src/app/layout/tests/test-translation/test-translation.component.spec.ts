import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestTranslationComponent } from './test-translation.component';

describe('TestTranslationComponent', () => {
  let component: TestTranslationComponent;
  let fixture: ComponentFixture<TestTranslationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestTranslationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestTranslationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
